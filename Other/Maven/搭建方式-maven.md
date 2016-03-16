1 首先建立一个maven项目,不用任何骨架
2 建立src/main/webapp/WEB-INF/web.xml文件,并填写相应的内容,servlet 3.0
3 建立webapp/index.jsp 里面随便写点内容
4 将pom.xml里的packaging改为war
5 导入

```xml
<dependency>
		<groupId>javax.servlet.jsp.jstl</groupId>
		<artifactId>javax.servlet.jsp.jstl-api</artifactId>
		<version>1.2.1</version>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
		<version>3.0.1</version>
		<scope>provided</scope>
	</dependency>
```

6 为项目添加artifact 并且 部署到Tomcat
7 试运行一下 没问题的话 应该是可以访问到index.jsp的
8 加入springmvc所需要的依赖

```xml
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-core</artifactId>
	<version>${spring.version}</version>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-beans</artifactId>
	<version>${spring.version}</version>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-context</artifactId>
	<version>${spring.version}</version>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-web</artifactId>
	<version>${spring.version}</version>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-webmvc</artifactId>
	<version>${spring.version}</version>
</dependency>
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-test</artifactId>
	<version>${spring.version}</version>
	<scope>test</scope>
</dependency>
```

具体需要多少依赖视情况而定,一般来说springmvc一个就搞定了,如果还需要其他功能,比如tx,orm等,那么就再加依赖
9 为SpringMVC配置web.xml的内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app
	version="3.0"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	>
	<servlet>
		<servlet-name>dispatcher</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>dispatcher</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>forceEncoding</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<servlet-name>dispatcher</servlet-name>
	</filter-mapping>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

</web-app>
```

10 添加applicationContext.xml和dispatcher-servlet.xml
dispatcher-servlet.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
       ">

	<!--组件扫描-->
	<context:component-scan base-package="org.xzc.mvc"/>

	<!--view解析器-->
	<mvc:view-resolvers>
		<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
		      p:viewClass="org.springframework.web.servlet.view.JstlView"
		      p:prefix="/WEB-INF/jsp/"
		      p:suffix=".jsp"
			/>
	</mvc:view-resolvers>
	
	<!--mvc Annotation支持-->
	<mvc:annotation-driven
		ignore-default-model-on-redirect="true"
		enable-matrix-variables="false"
		>
		<mvc:message-converters register-defaults="true">
			<!--编码修改-->
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
				<constructor-arg index="0" value="utf-8"/>
			</bean>
			<!--json-->
			<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
		</mvc:message-converters>
	</mvc:annotation-driven>
	<!--其他元素视情况而定-->
</beans>
```

11 建立包org.xzc.mvc.controller, 并随便添加一个Conroller,随便写点逻辑,然后部署到tomcat,进行访问,没问题的话一切都OK了



# 静态资源映射 #
<mvc:resources mapping="/public/**" location="/public2/"/>
这样当通过url访问 /public/a/1.txt的时候 静态资源的路径是 webapp/public2/a/1.txt

# 国际化 #
在applicationContext.xml(注意是这个文件!)配置MessageSource
```
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource"
	>
	<property name="basename" value="msg"/>
</bean>
```
然后在resources文件夹里存放msg_zh_CN.properties等国际化文件即可
使用的时候:
```jsp
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<title><s:message code="title"/></title>
```
	