通过特定的扩展点使得可以在 bootstrap 阶段加入新的配置项, 融入到env里

cloud 通过扩展点新增了 Bootstrap  Application Context
他是我们的ac的父亲
bootstrap阶段使用 bootstrap-{profile}.yml 配置文件

最终它会新增两个PropertySource
1. 一个是 bootstrap 这个优先级最高
2. 一个是来自 bootstrap.yml 里的配置项, 这个跟上面虽然名字有点像 但其实是不同的, 这个优先级很低 基本可以认为是默认配置了



TODO 有空试试下面3个选项
spring.cloud.config.allowOverride=true
spring.cloud.config.overrideNone=true
spring.cloud.config.overrideSystemProperties=false

关闭 spring cloud config

默认回去配置服务器拿 /{application}/{default} 配置文件
这里还有很多种变形, 具体去官网看, 一般通过看打印日志就行了!

通过 spring.cloud.config.uri 配置客户端所用的配置服务器的地址


eureka.client.serviceUrl.defaultZone
服务器的url也可以不写死, 而是通过服务发现来发现它!
要设置 spring.cloud.config.discovery.enabled=true
服务器id默认是 configserver, 可以通过 spring.cloud.config.discovery.serviceId 进行修改

如果不能连接到服务器就快速失败
spring.cloud.config.failFast=true 默认是false
还可以配置重试次数


具体见这个章节 Spring Cloud Config Client
由于现在还不是很常用 所以暂时不展开

在安全上支持BASIC验证



