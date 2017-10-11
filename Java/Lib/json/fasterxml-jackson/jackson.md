# 20170615 #
貌似官网已经进不去了
http://wiki.fasterxml.com/
现在文档在 https://github.com/FasterXML/jackson-docs 上

# 文档的解析方式 #
有3种解析方式
1. 流
	1. 类似xml的流式解析
2. 树
	1. 通常json对象可以当做一棵树
3. 对象
	1. 这是最长用的
	2. 处理泛型的时候可能会用到 TypeReference


# 树模型 #
readTree 方法返回的是 JsonNode, 可以强转
```
ObjectNode root = mapper.readTree("stuff.json");
```

String name = root.get("name").asText();


# 支持的特性 #
是否缩进
SerializationFeature.INDENT_OUTPUT

默认将时间写成时间戳
WRITE_DATES_AS_TIMESTAMPS

ALLOW_UNQUOTED_FIELD_NAMES 允许key不带引号
ALLOW_SINGLE_QUOTES 允许key用单引号
ESCAPE_NON_ASCII 转义飞ascii字符

ALLOW_COMMENTS 允许注释, 这个一般不要允许


通常需要disable掉下面的特性:
解析到未知的属性则失败
DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES

FAIL_ON_EMPTY_BEANS 空对象是否失败

# JsonFactory #
线程安全, 尽量保持单例, 除非需要不同的配置
用于创建 JsonParser 和 JsonGenerator
可以用于修改json的特性配置

# SerializerFactory #



# jackson #

官方文档遛一遛即可
https://github.com/FasterXML/jackson-docs
https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations

我们重点是要掌握 annotation 的用法

由好多个子项目组成

Core 包含了 json的解析和生成
可选的:
	Mapper 提供数据绑定功能
		TreeMapper 建立一些树, 包含了JSON节点	
		Object与JSON的相互转化
	jax-rs 不知道什么鬼
	xc xml相关
	
一般使用 data binding (可以支持基本数据类型和Java类 与 json字符串的互转)

对象转字符串
ObjectMapper om = new ObjectMapper();
User user = new User(1, "xzchaoo", new Date(), 1077, Arrays.asList("c1", "c2"));
String s = om.writeValueAsString(user);
		
字符串转对象同理 readValue(str,User.class)

默认情况下 Date -> long

遇到复杂类型可以使用 TypeReference
userList = om.readValue(s, new TypeReference<List<User>>() {
});
System.out.println(userList);

JsonInclude
JsonIgnore 加在字段上表示忽略这个字段
JsonIgnoreType 加在类上 表示忽略这个类
JsonIgnoreProperties 加在类上, 用于配置该类的忽略
JsonView
JsonFormat

# 常见annotation #
@JsonProperty
标记一个属性为json属性, 可以修改它对应的名字
index属性可以用于调整ProtoBuf的序号
一般加在字段上, 也可以加在get方法上

@JsonIgnore
忽略该属性

@JsonIgnoreProperties
放在类上, 指明要忽略的属性
ignoreUnknown 当json字符串提供了未知的参数的时候是否忽略, 这个值默认是false, 感觉true会比较好一点

@JsonIgnoreType
加在某个类上, 表示该类被忽略(当该类作为别的类的属性的时候)

@JsonInclude(JsonInclude.Include.NON_NULL)
这样序列化的时候只会序列化非空字段

@JsonFormat(pattern = "yyyy-MM-dd")
private Date birthday;
会影响序列化的结果, 但是反序列化的时候似乎没有这个限制(即不要求提供的日期字符串也是这个格式, 甚至还可以是long)

@JsonUnwrapped
扁平化
将一个复杂对象的属性"摊"到外层对象上
可以指定前缀和后缀
比如 A a有个属性B b, b有个属性 C c
如果在A中将B标记为@JsonUnwrapped, 那么最终的结果C就会被放到A里

@JsonView
将一个属性标记为属于某些View, 这样在 read 或 write 的时候, 只有相关的View是激活的 才能读写该属性

@JsonAnySetter
用于标记一个方法, 多余的属性将会注入这个方法
```
@JsonAnySetter
public void 名字随便起(String name, Object value) {
	others.put(name, value);
}
```

@JsonEnumDefaultValue
定义枚举类型的默认值, 当反序列化的时候, 如果某个枚举类型的属性没有提供正确的值, 就使用这个默认值

@JsonPropertyDescription
给属性加描述, 对结果没有影响
https://github.com/FasterXML/jackson-module-jsonSchema

@JsonDeserialize
能定制一个对象的反序列化方式

@JsonPropertyOrder
加在类上, 用于控制json的属性顺序, 没有提到的属性将会放在最后
如果一个属性有别名(以为使用了 JsonProperty ), 那么两个名字都是可以用的

@JsonRawValue
用于标记某个属性是否不需要进行转义, 如果你有一个本身就是json格式的字符串的时候就很有用

@JsonAutoDetect
用于控制field或getter或isXxx的可见性

@JsonValue
标记一个方法, 以它的字符串返回值作为序列化的结果

用于标记某个类的方法(一般是toString()), 用它的返回值作为你序列化的结果
```
@JsonValue
@Override
public String toString() {
	return "content====" + content;
}
"desc":"content====asdf"
```

@JsonCreator
用于标记构造器或静态工厂方法, 这样反序列化的时候就会使用这个构造器来初始化, 否则就需要提供无参构造函数


@JsonRootName("kk")
class User{...}
om.enable(SerializationFeature.WRAP_ROOT_VALUE);
可以创建一个包围的属性
这样结果是 {"kk":{"password":"70862045","username":"xzchaoo","asdf":77.0}}


JsonSubTypes
JsonTypeId
JsonTypeInfo
JsonTypeName

JsonManagedReference
JsonBackReference
JsonIdentityInfo

JacksonAnnotation
JacksonAnnotationsInside

JacksonInject
JsonCreator
JsonSetter
JsonGetter
JsonAnyGetter
跟那个setter差不多



# 特性 #
https://github.com/FasterXML/jackson-databind/wiki/Deserialization-Features#value-conversions-coercion


# Tree Model #
ObjectMapper mapper = new ObjectMapper();
JsonNode rootNode = mapper.readValue(src, JsonNode.class);
这样就拿到了字符串表示了

可能会返回 MissingNode , 作为null的标记

get(int index) 数组, 基于0, 越界则返回null
如果该node不是数组, 就返回null
如果显式写的value=null, 则返回NullNode

get(String property)
如果属性不存在或该节点不是一个Object则返回null
如果显式写的value=null, 则返回NullNode

path(index / property)
跟get类似, 不管null还是NullNode 都会返回MissingNode
比较安全, 不会因为null而产生异常

with(int index)
跟path()类似, 但是会创建和添加ObjectNode
再创建值的时候很有用
root.with("object").with("attrs").put("firstAttribute", 1);


idNode.isMissingNode()

数组可以直接遍历
```
for (JsonNode node : root.path("array")) {
	System.out.println("Entry: "+node.toString());
}
```

JsonNode rootNode = mapper.createObjectNode(); // will be of type ObjectNode
((ObjectNode) rootNode).put("name", "Tatu");

MyBean bean = mapper.treeToValue(node, MyBean.class);

# ObjectMapper #
copy
SerializationConfig
DeserializationConfig
DeserializationContext

SerializerFactory
DefaultSerializerProvider
SerializerProvider

PropertyNamingStrategy

JsonInclude.Include

PrettyPrinter

启动和关闭各种特性
DateFormat

TimeZone
MapperFeature

DeserializationFeature

将 字符串, 流, 字节数组, 文件 , URL, JsonParse, TreeNode 转成一个Java类
支持简单的Class<T> 或 TypeReference

ResolvedType
JavaType

将 字符串, JsonParse 转成一个 JsonNode

createObjectNode
createArrayNode

对象 -> TreeNode

convertValue
用于两个对象的转换
但是是基于属性的
跟 BeanUtils.copyProperties 类似

writerWithView

valueToTree
java对象 -> JsonNode

treeToValue
JsonNode -> java对象

# JsonNode #
获取各种基本数据类型的值
判断是否有field
fieldNames 获得所有的field
findPath
findParent
findValue

with
withArray

deepCopy 深复制


# ContainerNode : JsonNode #
转成各种类型的node

asText
asToken
get 获得一个field
path 跟get类型, 但是显式的null会返回NullNode
with 如果对象不存在的时就会创建一个新对象, 具体见代码你就知道了 用于构建的时候方便
withArray


# ObjectNode #
使用 put 方法可以创建简单的属性 对象 数组
put 各种基本数据类型
putPOJO 直接放一个java对象的json进去
putArray(fieldName) 创建一个数组, 并返回该数组
putObject(fieldName) 创建一个对象, 并返回该对象
putNull(fieldName) 显式创建一个null

set 跟put类似, 不过操作的是Node类型的值
setAll 可以将一个Map set进来
setAll(ObjectNode) 可以将另外一个ON属性赋值过来

without 方法可以删除fields, 返回 this
remove 移除, 返回移除的值
retain 只保留指定的属性, 移除其他的属性

# ArrayNode #
addAll
insert
add
remove

addArray
addObject
addPOJO
addNull
add各种基本数据类型

