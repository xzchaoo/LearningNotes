# jackson #
http://wiki.fasterxml.com/
官方文档遛一遛即可
https://github.com/FasterXML/jackson-docs

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
