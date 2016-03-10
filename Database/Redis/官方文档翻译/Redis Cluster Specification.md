http://www.redis.io/topics/cluster-spec

集群的目标
1. 高性能, 可以伸缩到1000个节点, 不需要代理, 异步备份, 也不用对值进行merge操作.
2. write安全在可接受内. 系统尽力将客户端的写请求保留在大多数的master节点上. 这通常会有一个丢失数据的窗口. 当分片比较少的时候, 这个窗口就会比较大.
3. 当每个分片都至少有一个可用的master的时候, 整个集群就是可用的.
4. 使用 replicas migration, 那些没有slave的master, 可以从拥有比较多slave的master那领取一个slave过来.

# 实现子集 #
1. 大部分单key的命令都支持.
2. 类似于集合类型的 交集 并集 操作, 只有在所有的key都在同一个节点上的时候才支持.
3. 支持一个叫做 hash tags的东西, 拥有相同hash tags的key会被放到同一个节点上
4. 集群的时候不支持多数据库.

# Clients and Servers roles in the Redis Cluster protocol #
1. 集群节点负责存放数据, 并且留意集群的状态(包括映射键到正确的节点)
2. 及诶单可以自动发现其他节点, 发现不工作的节点, 提升slave为master当它对应的master挂了的时候.
3. 集群节点之间用 Redis Cluster Bus 进行交流
	1. 用于是的整个集群支持pub/sub
	2. failover
4. However clients that are able to cache the map between keys and nodes can improve the performance in a sensible way.
5. In a cluster of N nodes, every node has N-1 outgoing TCP connections, and N-1 incoming connections.

# Redirection and resharding #
## MOVED Redirection ##
1. 客户端可以向任何一个节点发出请求
	1. 当这个请求在这个节点可以被处理时, 它就马上被处理了.
	2. 当不可以被处理时, 它就会产生一个类似:-MOVED 3999 127.0.0.1:6381的结果.
	3. 接着客户端会重新发一个请求到新的目的地
2. 每个客户端的实现都会自动的将 hash slots -> ip:port 记下来, 以提高效率
	1. 就算记错了(可能是由于过时)也没关系
	2. 原因可以看第1条