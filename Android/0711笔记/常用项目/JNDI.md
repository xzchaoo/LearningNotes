# JNDI #

# Local #
配置在<Context/>元素下
<Context>
<Resource auth="container" name="jms/ConnectionFactory" type="..." description="描述" factory="..." brokerUrl="..." brokerName="..."/>
...
</Context>
其中<Context/>元素可以出现在
	1. 项目的META-INF文件夹下
	2. tomcat的servers.xml里

