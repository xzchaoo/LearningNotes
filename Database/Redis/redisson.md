# 集群模式 #
你的redis需要处于集群模式
可以配置的参数
从哪里读, 默认是从从节点读
负载均衡: 权重轮流 轮流 随机
还有各种连接数的大小
各种超时时间
重试次数 重试间隔
密码

针对aws的Elasticache 有特殊支持

# 单节点模式 #
地址
订阅连接最小空闲数 1
订阅连接池大小 50
连接最小空闲数 10
连接池大小 64
dns监控 false 是否监控DNS配置的变化
dnsMonitoringInterval 5000

idleConnectionTimeout 10000 如果一个连接已经 idleConnectionTimeout 时间没用了, 并且当前的连接数量超过最小连接池大小, 那么这个连接就会被彻底关闭
连接超时 10000 连接到redis的超时时间
timeout 3000 redis响应超时时间 从命令成功发送给redis后开始计算
retryAttempts/retryInterval
reconnectionTimeout/failedAttempts
database 使用哪个数据库
subscriptionsPerConnection 限制每个redis连接的订阅数
password
clientName

# redis sentinel #
https://segmentfault.com/a/1190000002680804
redis的主从不具备自动切换的功能
sentinel是一套独立的进程, 用于监控多个主从, 一旦主挂了, 它就将从进行提升
好处就不说了, sentinel本身也可以做成一套集群 保证高可用
客户端可以连接任意一个 sentinel节点来获取整个集群的信息
sentinel本身占用的资源是非常少的

```
redis-sentinel /path/to/sentinel.conf
```

配置
```
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

sentinel monitor resque 192.168.1.3 6380 4
sentinel down-after-milliseconds resque 10000
sentinel failover-timeout resque 180000
sentinel parallel-syncs resque 5
```
这两段配置的意思是, 现在有两个主从对,mymaster 和 resque 名字是随意的
mymaster的master是 127.0.0.1:6379, 末尾的2表示至少2个的sentinel认为该master挂了 那么该master才真的挂了 防止误判
resque的master是 192.168.1.:6380
down-after-milliseconds s会定期向master发ping, 如果60000毫秒没有收到心跳结果,那么该s会主管地认为该master挂了 如果有2个(如上面第一条)s认为m挂了, 那么m就真的挂了
failover-timeout 故障转移
parallel-syncs TODO 当发生master切换的时候, 其余的slave需要重新进行同步, 这个值用于知识有多少个slave可以同时进行同步
