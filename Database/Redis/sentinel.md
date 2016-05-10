例子场景:
有3台机器A,B,C
B,C是A的副本

当A挂了之后
我们希望B成为master, C成为B的slave

手动版:
让a shutdown
让b slaveof no one
让c slaveof b的地址和接口

info Replication 可以查看当前节点相关的副本信息

sentinel monitor def_master 127.0.0.1 6379 2 失效几次就认为是失败,这里设置的是2次
如果设置了多个哨兵节点的话, 每次有一个哨兵检测到失效的话就会累计一次
def_master 是自己可以定义的一个名字, 只要和下面的配置保持一致就行了 def_master

sentinel auth-pass def_master 012_345^678-90

sentinel down-after-milliseconds def_master 30000 30秒内没有相应没有响应就失效

sentinel can-failover def_master yes 如果sentinel监控到master失效了, 是否允许该sentinel设置一个新的slave为master

sentinel parallel-syncs def_master 1

sentinel failover-timeout ...
