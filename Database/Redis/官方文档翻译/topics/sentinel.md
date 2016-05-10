1. 允许出错的时候不用人工介入, 可以自行切换master

它可以
1. 监控 master 和 slave 是否正常工作
2. 通知 管理员, 通过 程序 或 API, 某个节点出错了.
3. 自动 failover / 故障切换
4. 用于提供配置, 客户端连接到sentinel上, 然后一旦master发生了改变, 客户端就能知道

Sentinel的分布式特性
1. 几个S聚在一起, 最终确认某个master不再可用, 避免误判
2. 并不要求所有的S都正常工作

# 启动方式 #
redis-server /path/to/sentinel.conf --sentinel

最小的配置
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

sentinel monitor resque 192.168.1.3 6380 4
sentinel down-after-milliseconds resque 10000
sentinel failover-timeout resque 180000
sentinel parallel-syncs resque 5

# 配置 #
sentinel monitor <master-group-name> <ip> <port> <quorum>
表示要最ip:port这台master进行监控
一旦有quorum个S认为他挂了, 那么它就真的挂了.

In practical terms this means during failures Sentinel never starts a failover if the majority of Sentinel processes are unable to talk (aka no failover in the minority partition).


down-after-milliseconds
多少毫秒没反应就认为失败

parallel-syncs



看到这里 sentinel <option_name> <master_name> <option_value>

min-slaves-to-write 1 意味着这个master必须要有1个可写的slave才能够接受写请求
min-slaves-max-lag 10 slave在10秒内没有答复的话就认为他坏掉了
缺点就是如果slave都挂了, master还活着, 那master也不能用了.

# 官方上的例子 #
## 例子1 ##
这是的反面例子
因为M1的机器崩了之后, 另外R1的机器没法成为master
因为没有达到"大多数个S"的要求.

## 例子2 ##
假设M1 与 M2,M3之间的网络断开了
客户段还会继续向M1写数据, 因为它还不知道
而S2和S3决定将R2提升为M2
将网络恢复之后, M1就降级为 R1, 并且这段时间内写的数据都丢失了
可以使用下面的参数缓解这个问题:
min-slaves-to-write 1
min-slaves-max-lag 10

## 例子3 ##

#  #

sentinel master mymaster 查询这个集合的master情况
SENTINEL slaves mymaster
SENTINEL sentinels mymaster


redis-cli -p 6379 DEBUG sleep 30

# API #
http://redis.io/topics/sentinel
查看 master slave sentinel 的情况

一旦配置发生了变化就会被写回配置文件

sentinel monitor name ip port quorum 动态添加一个监视(在一台S上执行这个动作)
remove name

sentinel set name option value
跟 config set 类似, 但是是用于修改name对应的master的配置
SENTINEL SET objects-cache-master down-after-milliseconds 1000

动态添加 sentinel
只要确保 S的配置文件中中的 sentinel monitor mymaster 127.0.0.1 6000 2 能够连上去就行了
127.0.0.1:6000必须是存活的, 不管他是master还是slave
这样这个S就可以融入这个圈子了


默认情况下一个S会记下所有曾经是这个圈子的节点, 即使很长时间过去了, 这样有助于在节点恢复之后重新发现节点
使用 SENTINEL RESET mastername, 就会清除S记下的所有节点, 而仅保留当前圈子里的节点信息

# 事件 #
S上也提供了一些消息服务, 用于通知某个节点up/down 之类的

sdown 表示主观上认为down, 只有你单个S认为down
odown 表示客观上down, 大多数人也认为down

# 其他 #
如果一个slave的优先级=0, 那它永远不能成为master
优先级越低越容易成为master

Quorum: the number of Sentinel processes that need to detect an error condition in order for a master to be flagged as ODOWN.
The failover is triggered by the ODOWN state.
Once the failover is triggered, the Sentinel trying to failover is required to ask for authorization to a majority of Sentinels (or more than the majority if the quorum is set to a number greater than the majority).
The difference may seem subtle but is actually quite simple to understand and use. For example if you have 5 Sentinel instances, and the quorum is set to 2, a failover will be triggered as soon as 2 Sentinels believe that the master is not reachable, however one of the two Sentinels will be able to failover only if it gets authorization at least from 3 Sentinels.

Quorum, 只有至少 Quorum 个S认为master down了, 那么master才被标记为down
只有 至少 max(Quorum,超过当前S个数/2的最小整数) 个S同意, 某个S才能够进行一次failover 操作


# 安全 #
requirepass in the master, in order to set the authentication password, and to make sure the instance will not process requests for non authenticated clients.
masterauth in the slaves in order for the slaves to authenticate with the master in order to correctly replicate data from it.
