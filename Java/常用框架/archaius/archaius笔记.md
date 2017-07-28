# 默认配置 #

默认情况下会加载 classpath 下的 config.properties 作为程序配置

```
可以通过 archaius.configurationSource.additionalUrls 系统属性进行修改
-Darchaius.configurationSource.additionalUrls=file:///apps/myapp/application.properties

支持 file http 协议

放在后面的配置文件优先级越高
```

在程序里可以这样发现classpath下的配置文件

```
StringBuilder sb = new StringBuilder();
sb.append(getClass().getClassLoader().getResource("config.properties"));
sb.append(',');
sb.append(getClass().getClassLoader().getResource("config2.properties"));
System.setProperty("archaius.configurationSource.additionalUrls", sb.toString());
DynamicPropertyFactory f = DynamicPropertyFactory.getInstance();


```

可以修改的默认配置相关的配置
archaius.configurationSource.defaultFileName classpath下的默认的配置文件的名字, 默认是 config.properties
archaius.fixedDelayPollingScheduler.initialDelayMills 初始化延迟
archaius.fixedDelayPollingScheduler.delayMills 之后的延迟

# 配置源 #

PolledConfigurationSource 来这里实现你的配置的拉取
AbstractScheduler 在这里启动你的配置的拉取定时

创建一个 DynamicConfiguration 实例, 将上述两个对象作为参数传入构造器, 这样就创建了一个动态配置

ConfigurationManager.install(configuration); 将你的配置注册到全局

通过下面的方式就可以拿到你的配置
DynamicStringProperty myprop = DynamicPropertyFactory.getInstance().getStringProperty(...);

# 部署上下文 #
即你的部署环境, 比如 test dev prod

## 在不同的部署上下文下加载不同的配置 ##
比如
在 prod 环境下加载 application-prod.properties


# 其他配置 #
archaius.dynamicPropertyFactory.registerConfigWithJMX 是否注册JMX

archaius.default.deploymentContext.class 指定配置上下文的实现类 如果不指定 那么会使用默认的实现类
这个默认的实现类会个根据下面的属性来判断:
```
archaius.deployment.environment
archaius.deployment.region
archaius.deployment.datacenter
archaius.deployment.applicationId
archaius.deployment.serverId
archaius.deployment.stack
```


