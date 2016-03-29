# 6. JDBC #
配置mysql数据源
先在standalone.xml里添加driver
```
<driver name="mysql" module="com.mysql">
    <driver-class>com.mysql.jdbc.Driver</driver-class>
    <xa-datasource-class>com.mysql.jdbc.jdb2.optional.MysqlXADataSource</xa-datasource-class>
</driver>
```
然后启动jboss, 再去配置数据源, 此时就可以看到mysql的驱动被识别出来了!


# 7. 模块 #
一个模块是一组类

## 静态模块 ##
在EAP_HOME/modules/下
每个子目录代表了一个模块 , 每个模块的目录的main子目录要包含一个module.xml和相关的jar包.
module.xml里定义了模块的名字
比如在EAP_HOME/modules/com/mysql/main下建立module.xml, 在这里目录同时放上:mysql-connector-java-5.1.15.jar
```
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.0" name="com.mysql">
  <resources>
    <resource-root path="mysql-connector-java-5.1.15.jar"/>
  </resources>
  <dependencies>
    <module name="javax.api"/>
    <module name="javax.transaction.api"/>
  </dependencies>
</module>
```
这样就算添加了一个静态模块

## 动态模块 ##


