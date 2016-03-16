Spring.pdf看到73页

配置ViewResolver的时候可以使用如下的格式
```xml
<mvc:view-resolvers>
	<!--用于处理普通的JSP-->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
	      p:prefix="/WEB-INF/jsp/"
	      p:suffix=".jsp"
		/>
</mvc:view-resolvers>
```

或者不写在<mvc:view-resolvers>里也是行的.

## ResponseBody ##
当处理的方法标记为@ResponseBody的时候,它的返回值可以是任意类型
这时候根据方法的返回类型将会使用不同的HttpMessageConverter来转换成最终的响应信息.
要注册转换器的话去找RequestMappingHandlerAdapter类的messageConverters属性
或者直接使用mvc:message-converters的标签,但是注意如果使用该标签,并且你还定义了自己的RequestMappingHandlerAdapter这个Bean.那么你的配置可能会不起作用,这时候可能需要改变一下Bean的声明顺序或者使用直接修改messageConverters属性的方式

### StringHttpMessageConverter ###
如果你返回了一个字符串,那么这个字符串最终将会写入到流中
```java
@RequestMapping("/test1")
@ResponseBody
public String test1() {
	return "你好";
}
```
但是这样会出现乱码,因为StringHttpMessageConverter默认的编码是ISO-8859-1, 需要这样做:
```xml
<mvc:annotation-driven
	ignore-default-model-on-redirect="true"
	enable-matrix-variables="false"
	>
	<mvc:message-converters register-defaults="true">
		<bean class="org.springframework.http.converter.StringHttpMessageConverter">
			<constructor-arg index="0" value="utf-8"/>
		</bean>
	</mvc:message-converters>
</mvc:annotation-driven>
```
**注意register-defaults="true"不要忘记**

### 对象->json字符串 ###
```java
@RequestMapping(value = "/test2")
@ResponseBody
public User test2() {
	return new User();
}
```
好像我这个版本的SpringMVC已经自动注册了将对象转成json的转换器了,但是我不知道这个动作是有哪个类做的

#### 支持不同场景的属性过滤的json ####
Jackson Serialization View Support
**注意**:需要
配置
```xml
<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
```
并且导入jar包
```
com.fasterxml.jackson.core
	jackson-core
	jackson-annotations
	jackson-databind
```

```java
@RequestMapping(value = "/test4")
@ResponseBody
@JsonView(User.Simple.class)
public User test4() {
	return new User();
}
@RequestMapping(value = "/test4")
@ResponseBody
@JsonView(User.Simple.class)
public User test4() {
	return new User();
}
```
```java
public class User {
	public interface Simple {
	}

	private int id;
	private String name;
	private List<String> list;
	private Map<String, String> map;

	public User() {
		id = 1;
		name = "测试用";
		list = Arrays.asList("欢", "迎", "光", "临");
		map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
	}


	@JsonView(Simple.class)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonView(Simple.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
}
```
### 对象->XML ###


### 异常处理 ###
HandlerExceptionResolver

SimpleMappingExceptionResolver
	支持@ExceptionHandler

DefaultHandlerExceptionResolver
	这个类同时还会将相应的异常与status code对应起来(即发生了某些异常时,就返回相应的status code)
	当然这些异常仅限于SpringMVC自身发生的异常,比如找不到Handler就映射到404
	
当@ExceptionHandler放在一个@Controller的方法上,那么它会处理这个Controller的相应的异常

```java
@ExceptionHandler(IOException.class)
public ResponseEntity<String> handleIOException(IOException ex) {
	// prepare responseEntity
	return responseEntity;
}
```

当@ExceptionHandler放在一个@ControllerAdvice的方法上,那么它会处理所有Controller的相应的异常
这样当所有的Controller发生了Ex3异常的时候都会到这里进行处理
注意要保证这个类被Spring扫到

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {Ex3.class})
	@ResponseBody
	public String ex2(RuntimeException e) {
		return "三222";
	}

}

**注意**:优先级当然还是Controller类里的异常处理器比较高!

## 杂 ##
ModelAndView.addObject方法 如果没有指定name,
如果是一个User[] 那么名字是userList
ArrayList<User> -> userList
HashSet<User> -> userList
	这些集合如果大小为空的话就不会被添加


## RequestToViewNameTranslator ##
不用显式指定view的名字,按照约定来
```java
@RequestMapping("/test6/test66")
public void test6() {
	System.out.println("test6");
}
```
这个默认的name就是test6/test66
由于我配置了jsp的VR
所以结果是/WEB-INF/jsp/test6/test66.jsp

