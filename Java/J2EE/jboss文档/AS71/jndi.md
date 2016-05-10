https://docs.jboss.org/author/display/AS71/Documentation

在 eap6.4 的 quickstart 目录下有一些例子项目, 这些项目都是用maven构建的.
因此你可以到这些项目里找, 如果你想远程连接jboss的jndi那么该加入什么包.
这样做的一个好处是, 你可以防止版本错误.
否则一个对象序列化之后传到客户端可能由于版本错误而发生问题!

在classpath下放一个 jndi.properties 就可以取代 InitialContext 的默认配置
比如

```
java.naming.factory.initial=org.jboss.naming.remote.client.InitialContextFactory
java.naming.provider.url=remote://localhost:4547
java.naming.factory.url.pkgs=org.jboss.ejb.client.naming
```
像上面这么配置, 就可以让你同时使用 java: 和 ejb:





1. 推荐使用 Client JNDI 而不是传统的远程JNDI, 不过这是啥?
	1. 不过命名需要满足一定的规则, 目前只支持使用 ejb:命名空间, 未来会增加对jms的支持
2. 传统的远程JNDI

提供了 java:jboss/exported/ 上下文, 使得可以被远程访问, 只有这里的资源是可以被远程访问的, 其他的资源都要在同一个jvm内
The Java EE platform specification defines the following JNDI contexts:
java:comp/ - The namespace is scoped to the current component (i.e. EJB)
java:module/ - Scoped to the current module
java:app/ - Scoped to the current application
java:global/ - Scoped to the application server

In addition to the standard namespaces, AS7 also provides the following two global namespaces:
java:jboss/
java:/

# 如何绑定资源到JNDI #

使用EJB可以让一个Bean暴露额外的名字
```
@Stateless
@EJB(name = "java:global/MyBean", beanInterface = MyBean.class)
public class MyBean...
```

使用 web.xml
```
<web-app version="3.0"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
    <env-entry>
        <env-entry-name>java:global/mystring</env-entry-name>
        <env-entry-type>java.lang.String</env-entry-type>
        <env-entry-value>Hello World</env-entry-value>
    </env-entry>
    <env-entry>
        <env-entry-name>hello</env-entry-name>
        <env-entry-type>java.lang.String</env-entry-type>
        <env-entry-value>Hello Module</env-entry-value>
    </env-entry>
</web-app>
```
不过这样似乎只能绑定简单的值对象?

在jboss的配置文件中
```

<subsystem xmlns="urn:jboss:domain:naming:1.1" >
  <bindings>
    <simple name="java:global/a" value="100" type="int" />
    <object-factory name="java:global/b" module="com.acme" class="org.acme.MyObjectFactory" />
    <lookup name="java:global/c" lookup="java:global/b" />
 </bindings>
</subsystem>
```

远程客户端访问
```
<dependency>
  <groupId>org.jboss.as</groupId>
  <artifactId>jboss-as-ejb-client-bom</artifactId>
  <version>7.1.1.Final</version>
  <type>pom</type>
  <scope>compile</scope>
</dependency>
```
添加这个依赖之后, 你就具有了远程客户端连接jboss的jndi所需要的相关jar包, 擦 早知道就好了.

使用ejb命名空间的话, 有如下的格式:
ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>
ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>?stateful

如果你的项目是使用 ear 方式部署上去的, 那么app-name就是你的ear名
然后ejb和war等才是你的module-name

如果你只是以简单的jar或war部署上去的, 那么 app-name=''
