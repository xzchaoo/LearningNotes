@Configuration
public class Config{
	//运行的时候这个方法将会被重写,实际上只会返回一个单例
	@Bean
	public Bar bar(){
		return new Bar();
	}
	
	@Bean
	public Foo foo(){
		Foo f=new Foo();
		f.setBar(bar());//虽然这里感觉上会产生多个bar对象,但实际上是单例的,因为bar方法已经被重写了
		return f;
	}
}

常用Annotation
@Bean(name={多个别名})
@Description就是加上一些解释而已,没有实际意义
@Import加在@Configuration类上导入其他的配置类
@ImportResource导入配置文件的信息
@Value

```java
public CommandManager commandManager() {
// return new anonymous implementation of CommandManager with command() overridden
// to return a new prototype Command object
return new CommandManager() {
protected Command createCommand() {
return asyncCommand();
}
```
在Configuration类里也可以进行依赖注入

EmbeddedDatabaseBuilder

@Profile("dev")与@Configuration或方法一起使用,当处于dev状态的时候这个config就会生效
这也可以在xml里进行配置
在beans的profile属性里!



```xml
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:jdbc="http://www.springframework.org/schema/jdbc"
xmlns:jee="http://www.springframework.org/schema/jee"
xsi:schemaLocation="...">
<!-- other bean definitions -->
<beans profile="dev">
<jdbc:embedded-database id="dataSource">
<jdbc:script location="classpath:com/bank/config/sql/schema.sql"/>
<jdbc:script location="classpath:com/bank/config/sql/test-data.sql"/>
</jdbc:embedded-database>
</beans>
<beans profile="production">
<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>
</beans>
</beans>
```

```java
ctx.getEnvironment().setActiveProfiles("dev");
-Dspring.profiles.active="profile1,profile2"
```

这个文件里有author = xzc
@PropertySource("classpath:/com/myco/app.properties")
@Autowired
Environment env;
env.getProperty("author")
@EnableLoadTimeWeaving
