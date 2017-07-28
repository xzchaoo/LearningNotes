# 介绍 #
N是一个工具, 用于管理机器集群,运行程序在它们上面.
N抽象了机器和程序的位置,允许用户声明想运行什么,和该在哪里运行,和怎么运行.

# 特性 #
支持docker
操作简单
多数据中心和多区域支持
灵活的workloads
可扩展

# 用例 #
微服务平台
混合云部署

# 和其他软件对比 #
N是一个机群管理和调度器. 有很多相关的类别: 集群管理器, 资源管理器, workload managers 和 调度器
每个类别现在都有一些其他的工具

## AWS ECS ##
Amazon Web Servics(AWS) 提供了 EC2 Container Service (ECS), 是一个集群管理器
ECS只能用于AWS并且只能支持docker workdloads
Amazon提供用户agent, 应在装载ec2上了, server则是有AWS负责提供的. [反正你只是用这个服务而已]

N的客户端和服务端都是开源的.
ECS客户端开源

ECS只能在AWS内用
N则没有这个限制, 支持公有云和私有云, 可以跨多个数据中心和区域, 跨云服务器提供商


ECS只支持docker容器
N则支持更多通用的目的
Nomad supports virtualized, containerized, and standalone applications, including Docker. Nomad is designed with extensible drivers and support will be extended to all common drivers.

