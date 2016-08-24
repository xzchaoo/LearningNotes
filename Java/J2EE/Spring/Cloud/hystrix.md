程序名(service id), 虚拟主机名 都是由 spring.application.name 决定的
不安全的 por t是由 server.port 决定的

See EurekaInstanceConfigBean and EurekaClientConfigBean for more details of the configurable options.

默认情况下会将自己作为 instance 和 client 连接到 server.

暴露两个端点:
1. 状态 /info
2. 健康 /health

ssl相关配置

利用 eureka.instance.metadataMap 可以设置实例的元数据


实例id是由
${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id:${server.port}}}.  确定的

# ab的用法 #
-n 总的请求数
-c 并发数

ab -n 1000 -c 10 http://localhost:9001/user/1


# zuul #
用于将请求直接路由到某个服务
省得你直接写一个mvc, 再在controller里调用服务
可以做反向代理
