http://www.redis.io/topics/cluster-tutorial

使用集群可以获得什么?
1. 自动拆分数据集到多个节点上
2. 当一些节点挂掉的时候可以继续工作

每个集群节点需要两个TCP连接, 6379作为普通的服务器端口, 加上10000, 16379, 用于集群总线的交流

集群总线用于检测节点是否失败, 配置是否更新, failover authorization.
客户端不必连接16379, 这是集群自己内部使用的.

集群总线的端口总是比普通端口大10000, 似乎没法改.

Note that for a Redis Cluster to work properly you need, for each node:
The normal client communication port (usually 6379) used to communicate with clients to be open to all the clients that need to reach the cluster, plus all the other cluster nodes (that use the client port for keys migrations).
The cluster bus port (the client port + 10000) must be reachable from all the other cluster nodes.


# Redis Cluster and Docker #
目前的集群环境不支持NAT环境和端口映射之类的.
当部署在Docker上的时候需要特殊的处理.

一致性哈希
# Redis Cluster data sharding #
集群并不是用一致性哈希, 而是使用另外一种方式, 所有的key都概念上是 hash slot的一部分
一共有16384个 hash slot 在Redis集群上, 要知道一个key在哪个slot上, 需要对这个 key的CRC16 取模
每一个节点就负责16384个slot的一部分:
Every node in a Redis Cluster is responsible for a subset of the hash slots, so for example you may have a cluster with 3 nodes, where:
Node A contains hash slots from 0 to 5500.
Node B contains hash slots from 5501 to 11000.
Node C contains hash slots from 11001 to 16384.

在节点之间移动slot不会导致停机

集群支持 多key操作, 只要这些key包含在一个单条指令执行里(或事务或lua脚本), 并且这些key属于同一个slot.
用户可以强制使得 multiple keys在同一个slot里, 通过指定一个 hash tags.
hash tags的要点是 如果你的key里包含 {xxx} (用两个花括号围起来一部分字符串), 那么只有xxx会被会被hash计算
这样就会使得你可以控制某些key落在同一个slot里

# Redis Cluster master-slave model #
每个slot可以有若干个备份.
当一个负责一个slot的master挂了之后, 它对应的slave就可以顶上

如果一个slot的所有备份都挂了, 那么整个集群都不可操作还是只有那一部分不可操作?

# Redis Cluster consistency guarantees #
集群没法保证非常强的一致性, 意思就是在某些条件下集群会丢失已经确认的写操作.
主要是由于异步备份的原因:
1. 你写数据到A, 并且达到了OK响应
2. A还没来得及将它的数据备份到A1, A就挂了
3. 集群让A1作为它那一部分的master
4. A1里没有刚才的数据

性能和一致性的tradeoff

# Redis Cluster configuration parameters #
1. cluster-enabled <yes/no>
2. cluster-config-file <filename>
	1. 制定一个地方用于存放集群配置文件, 这不是一个用户可以修改的配置文件, 而是用于集群保存自身配置的一个地方,
	为了能让集群在重启的时候还可以读取它.
	2. 这个文件里面列出了其他节点的信息.
3. cluster-node-timeout <mills>
	1. 一旦一个节点超过这段时间都没有响应的话, 就被认为是失败了
	2. 如果一个节点超过这段时间自己无法连接大多数的人, 那它也会认为自己失败了, 从而拒绝接受请求.
4. cluster-slave-validity-factor <factory>
	1. 设置成0, a slave will always try to failover a master, regardless of the amount of time the link between the master and the slave remained disconnected.
	2. 如果>0, 那么就是 node-timeout * factory
		1. For example if the node timeout is set to 5 seconds, and the validity factor is set to 10, a slave disconnected from the master for more than 50 seconds will not try to failover its master.
		2. 意思应该就是如果一个slave与master失去连接超过了这个时间, 那么就不会让这个slave去failover这个master(当master失败的时候, 这个slave不会去顶替它, 因为它们已经好久没联系了)
5. cluster-migration-barrier <count>
	1. 一个master会与最少多少个slave保持连接
6. cluster-require-full-coverage <yes/no>
	1. 1. true(默认), 如果某些slot负责的节点全挂了, 那么就停止接受写请求

# Creating and using a Redis Cluster #
1. 需要有一些空的Redis实例, 运行在cluster mode.
2. 意思就是说集群不是由普通的Redis实例组成的, 而是需要一个特殊模式.
3. 如下是一个最小的集群的配置
```
port 7000
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```
4. 每个实例都会含有一个nodes.conf(上面的配置指定的)文件用于保存集群的配置数据
5. 最小的集群至少包含3个master node, 建议测试的时候3个master, 3个slave
6. 建立如下的目录结构
	1. cluster
		1. 7010 A
		2. 7011 A'
		3. 7020 B
		4. 7021 B'
		5. 7030 C
		6. 7031 C'
7. 在每个文件夹里放一个redis.conf文件
	1. 为了测试方便你应该只用尽量少的参数
	2. 记得修改他们对应的端口
8. 复制redis-server到cluster目录下
9. 对每70xx子目录依次执行: 个cd 7010; ../redis-server ./redis.conf
	1. 执行之后你会看到如下的信息
	```
	[82462] 26 Nov 11:56:55.329 * No cluster configuration found, I'm 97a3a64667477371c4479320d683e4c8db5858b1
	```
	2. 这个id号用于标识这个节点
10. 需要redis-trib的帮忙
	1. ./redis-trib.rb create --replicas 1 127.0.0.1:7010 127.0.0.1:7011 ... 6个ip:port

http://redisdoc.com/index.html

