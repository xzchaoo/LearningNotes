eureka-server
eureka-service
eureka-client

# Client #
1. 导入相关jar包
2. @EnableEurekaClient 加在App上
	1. 使得该App具备Client的功能
	2. 使得该App将自己注册到 eureka server上
3. 或使用 @EnableDiscoveryClient 可以去看 @EnableEurekaClient 的定义, 你就会发现他们两的区别了
	1. 使得该App具备Client的功能
	2. 但是我发现它还是把自己注册到 es 上了
4.  在 application.yml 里配置
```
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/ 这里当然要换成你的地址
```
这里的defaultZone就是一个默认值, 不过我目前还不知道如何利用默认值之外的值
配置地址的时候支持 BASIC 验证
http://username:password@xxx.com 的格式

可以参考:
EurekaInstanceConfigBean EurekaClientConfigBean
DiscoveryClientOptionalArgs ClientFilter


每个instance(基本上就是每个应用了) 可以有自己的 /info /status 页面

```
eureka:
  instance:
    statusPageUrlPath: ${management.context-path}/info
    healthCheckUrlPath: ${management.context-path}/health
```
默认已经配好了 基本不用改 除非有需要
好像如果你修改了 management.context-path 你就必须像上面那样配置 否则不行...

通过配置下面的选项, 可以配置成https协议
eureka.instance.nonSecurePortEnabled=false
eureka.instance.securePortEnabled=true

```
eureka:
  instance:
    statusPageUrl: https://${eureka.hostname}/info
    healthCheckUrl: https://${eureka.hostname}/health
    homePageUrl: https://${eureka.hostname}/
```

> 	If your app is running behind a proxy, and the SSL termination is in the proxy (e.g. if you run in Cloud Foundry or other platforms as a service) then you will need to ensure that the proxy "forwarded" headers are intercepted and handled by the application. An embedded Tomcat container in a Spring Boot app does this automatically if it has explicit configuration for the 'X-Forwarded-\*` headers. A sign that you got this wrong will be that the links rendered by your app to itself will be wrong (the wrong host, port or protocol).


> By default, Eureka uses the client heartbeat to determine if a client is up. Unless specified otherwise the Discovery Client will not propagate the current health check status of the application per the Spring Boot Actuator. Which means that after successful registration Eureka will always announce that the application is in 'UP' state. This behaviour can be altered by enabling Eureka health checks, which results in propagating application status to Eureka. As a consequence every other application won’t be sending traffic to application in state other then 'UP'.

```
eureka:
  client:
    healthcheck:
      enabled: true
```

如果需要更详细的控制可以实现:
com.netflix.appinfo.HealthCheckHandler

## 元数据 ##
通过 eureka.instance.metadataMap 可以携带元数据

## 修改实例id ##
默认的实例id是: ${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id:${server.port}}}
ruguo
如果有需要可以修改 eureka.instance.instanceId


## 使用EurekaClient ##
直接注入使用就行, 接下来就是 EurekaClient API的事情了, 去他们的官网看

不要在@PostConstruct 里调用 EurekaClient
因为对 EurekaClient 的初始化是在 SmartLifeCycle 的start里做的, 他的phase=0
因此你要在一个phase>0的start里做就安全了


## 使用DiscoveryClient ##
这个 DiscoveryClient 是Spring提供的, 内容比较通用, 没 EurekaClient 详细

# Server #
@EnableEurekaServer 可以使得一个app具有eureka服务器的功能
暴露了 /eureka url

## 独立模式 ##
```
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```
地址指向了自己

## 集群模式 ##


<client>.ribbon.listOfServers



CloudEurekaTransportConfig

## EurekaClientConfigBean ##
registryFetchIntervalSeconds 多久从server获取一次数据
instanceInfoReplicationIntervalSeconds
initialInstanceInfoReplicationIntervalSeconds
eurekaServiceUrlPollIntervalSeconds
http代理的 服务器 端口 用户名 密码
server读超时 默认8秒
server连接超时
backupRegistryImpl
eurekaServerTotalConnections 默认最多有200个连接
eurekaServerTotalConnectionsPerHost 默认每个Host主机最多连接50个
eurekaServerURLContext
eurekaServerPort
eurekaServerDNSName

heartbeatExecutorThreadPoolSize

是否用gzip压缩内容
serviceUrl 是一个map
value可以用逗号隔开!

registerWithEureka=true 是否注册实例
preferSameZoneEureka

# Ribbon #
客户端的负载均衡

# 待学习 #
Turbine 
Ribbon

