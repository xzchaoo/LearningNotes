将数据存放在多台机器上来应付大量数据, 大量读写

# 垂直扩展 #
增加每个节点的能力

# 水平扩展 #
增加节点数量


# 分片的目的 #
1. 提高吞吐量
2. 工作集给单台机器的RAM/CPU等带来压力
3. 减多少了每台机器的压力


分片集群涉及到的概念:

分片集群的使用方式:
客户端(比如mongo), 连接到mongos, mongos负责将你的请求路由给相应的分片
一个分片可以是一个单台服务器(dev的时候用)或是一个副本集, 如果有需要的话, mongos会将请求发给多个服务器, 然后合并它们的结果再返回
同时还需要几台"配置服务器"用来存放一些配置信息
mongos本身是不用存放数据的
Changed in version 3.2: Starting in MongoDB 3.2, config servers for sharded clusters can be deployed as a replica set. The replica set config servers must run the WiredTiger storage engine. MongoDB 3.2 deprecates the use of three mirrored mongod instances for config servers.
强烈建议使用v3.2的做法, 因为旧版的做法需要3台额外的配置服务器
而这3台配置服务器管理起来挺麻烦的

# Data Partitioning #
## Shard Key ##
片键/shard key是这个文档要放在哪里的依据, 比如我有10个分片, 那我现在要插入一个文档, 那我插入的这个文档到底要放哪呢, 就跟这个shard key有关
如果shard key是一个数值类型, 那么很可能:
1. 一开始, 由于数据量很小, 所有的文档都放在同一个块里
2. 后来这个块越来越大, 于是这个块被拆分成两个块
3. 以此类推
4. 每个块的范围都是不相交的

### 基于hash的分片 ###
有很好的随机访问能力, 但对范围访问无能为力
shard key 可以是一个 hash index

# 分片的tag #
通过分片的tag, 可以使得某些文档只保存在具有某个tag的分片上

# 维护数据分布平衡 #
情况:
1. 某个分片里的块特别多
2. 某个块特别大

策略:
1. 拆分大块为小块, 块是有一个最大值的, 当超过最大值后就会开始进行拆分
2. 移动, 会尝试将一个块从一个分片移动到另一个分片, 以取得平衡

# 添加和删除分片 #
1. 添加一个新的分片会导致不平衡, 因为这个分片里没有块, 因此需要花些时间平衡一下
2. 当删除一个分片的时候, 会将这个分片的块都移动到别的分片, 这个过程也要花时间

# 组成 #
## Shards ##
用于存放数据
可以是一个mongod实例 或 一个副本集

## Config Servers ##
用于存放元数据
v3.2以后推荐使用一个副本集
之前需要有3台配置服务器组成

## mongos ##
用于路由请求到相应的分片


# Shards #
在这里我们认为分片都是一个副本集, 单个mongod也可以设法搞成副本集

每个被分片的数据库都会有一个primary shard用于存放 un-sharded 集合
可以用 movePrimary 来改变这个分片

当你将添加一个分片, 而这个分片还保有以前的数据, 那么这些数据不会被删除.
sh.status() 可以查看集群的情况. 会显示每个数据库的primary

# Config Servers #
v3.2之后 推荐使用一个副本集作为配置数据库
这个副本集有如下特征:
1. 没有仲裁者
2. 没有延迟节点
3. 必须建立索引
v3.2以前的版本要求要有3个mongod服务器作为配置服务器

配置服务器上存放了元数据, 一般来说不要直接修改它, 而是要通过mongos
块的移动或拆分会引起配置服务器的写操作(有w=majority)
mongos第一次启动 或 mongos重启 或 集群元数据改变 会使得mongos读取配置服务器的数据

如果副本集出现了问题, 那么集群的元数据会变成只读的
由于mongos缓存了配置服务器的数据, 因此在一定时间内 mongos依旧可以正常工作
但是不会发生 块的拆分或移动

# Sharded Cluster Requirements #
如下场景要使用分片集群:
1. 你的数据集接近或超过单个mongodb的处理能力
2. 单个mongod容易耗尽内存
3. 单个mongod处理不了大量的读写

否则分片集群只会给你带来复杂度而没有任何好处.
但是, 要将一个mongod转成一个分片集群, 也是需要麻烦的处理的
如果你认为你的数据在不久的将来就会达到非常大的地步, 那么一开始就可以使用分片集群

默认的块大小是64M, 一般够用了


# Production Cluster Architecture #
通常只会启动一个mongos在每个应用服务器
如果有多个mongos, 那么通常需要和负载均衡器一起使用

# Sharded Cluster Test Architecture #
测试的时候:
1. 一个mongod作为一个副本集作为一个配置服务器
2. 至少一个分片
3. 一个mongos实例


# Shard Keys #
它决定了这个文档会被放在哪个分片上
1. 范围 要求这个key具有普通的索引
2. 哈希 要求这个key具有hashed索引

特点:
1. 通常具有较高的势
2. 随机均匀分布
3. 其他特点跟索引差不多
4. 如果这个字段的势比较低, 那么需要来一个第二字段, 构成复合片键
5. 使得写尽量均匀(是在不行的话可以不考虑这点)
6. 使得查询隔离(尽量导致请求只发送给一个shard, 即相关性大的数据尽量放在一起)
7. 势要求高, 势高并不意味着一定 查询隔离 和 写均匀.

# mongos崩溃了 #
https://docs.mongodb.org/manual/core/sharded-cluster-high-availability/
没事 重启一下就行了, 因为mongos本身并没有保存什么状态, 重启一下就会从配置服务器那里重新获取数据

# Sharded Cluster Query Routing #
mongos根据config server的信息(mongos会缓存这一信息)就可以知道哪些数据在哪个分片上, 如果实在不知道那就只好全部找一遍了.
通常来说mongos和你的应用服务器放在同一机器上

## Routing Process ##
根据一个查询如何决定到哪个分片?
1. 找出这个查询所需要的分片的列表
2. 在所有目标分片上建立一个游标

有时候查询的条件就是shard key 或 其前缀 那么就可以马上知道目标文档在哪个分片上了
当查询需要 排序 跳过 限制的时候mongos会做一定的优化.

## 检查你连接的是不是一个mongos ##
执行db.isMaster()如果msg=isdbgrid 那么就是一个mongos

# Broadcast Operations and Targeted Operations #
通常来说, 集群环境下的操作有:
1. 广播所有分片, 让这些分片做某事
	1. 更新/删除多个文档
2. 让特定的分片或分片集合做某事
	1. 比如插入/更新/删除一个文档

我们应该尽量利用shard key使得尽量是第二种操作

## Sharded and Non-Sharded Data ##
有些数据库或集合没有分片:
1. 可能是因为太小了


# Sharded Collection Balancing #
balancer负责在集群之间平衡各个分片的块.
一个mongos会定时变身为一个balancer, 此时mongos会请求一个锁通过修改locks文档
平衡器一次只会移动一个块
平衡器只会在两个分片的块数差距超过一个阀值的时候才会移动块到别的分片
https://docs.mongodb.org/manual/core/sharding-balancing/#sharding-migration-thresholds

默认每个分片会使用完全部的磁盘, 但是每个分片也是可以有一个最大大小的.
https://docs.mongodb.org/manual/core/sharding-balancing/

# Chunk Migration Across Shards #
1. 一般来说由balancer自动在块数不均匀的时候进行平衡
2. 也可以手动移动, 特殊情况

## Chunk Migration Procedure ##
块的迁移步骤

将块c从from分片移动到to分片
1. balancer将moveChunk命令发送给from分片
2. from分片开始执行内部的moveChunk命令, 此时该块对应的读写请求仍然会被发给from分片
3. to分片开始建立索引
4. to分片开始接受数据
5. to分片开始一个同步过程以确保在数据传递过程中发生的修改也被复制到to分片
6. 当同步完成之后, to分片联系配置服务器, 将该块的位置更改为to分片, 此后对c块的请求就会被发给to分片了, 此步骤可能会阻塞from上的写操作, 短时间内.
7. 当from分片的c块没有任何游标引用之后, 它就会被删除
8. 第7步可能是在一段时间之后才会发生, 所以实际上第6步的时候就已经完成了

当块的大小或文档数量超过了一定的值, 就会进行拆分
如果拆分失败, 就会将该块标记为 jumbo=true 以避免一直不断的去拆分它
https://docs.mongodb.org/manual/core/sharding-chunk-migration/

# 拆分 #
默认的chunk size是64mb
当块大小超过一定值或文档数量超过一定值的时候就会进行拆分
https://docs.mongodb.org/manual/core/sharding-chunk-splitting/#sharding-chunk-size
1. 小块更均匀, 但是会经常的移动, 这增加了query routing layer的代价
2. 大块会减少移动次数, 并且更高效, 但是容易造成不均匀分布
3. Chunk size affects the Maximum Number of Documents Per Chunk to Migrate.

什么时候需要修改块大小:
1. 自动拆分只会发生在插入或更新时, 如果你降低了块大小, 可能需要花一些时间才能将所有块拆成小块
2. 拆分不能undone, 一旦拆成小的, 你就需要让这个块加大, 然后才能再次拆分

# Shard Key Indexes #
必须以一个索引作为shard key, 可以是一个复合key
当你执行 对一个不存在的或者空的集合执行 shardCollection 时候 会自动创建对应的索引


# Sharded Cluster Metadata #
配置服务器保存了元数据, 反应了分片数据集合和系统的状态和组织
这些信息包含了 每个块的范围和shard key 和分片信息
使用config数据库
use config; //似乎不用连接到配置服务器也行
一般只可看不要改它.

# 部署分片服务器 #
## 部署配置服务器 ##
v3.2 以副本集的方式
这个副本集有特殊的要求:
1. 没有仲裁者
2. 没有延迟节点
3. 必须建立索引


1. 以这样的方式:mongod --configsvr --replSet configReplSet --port <port> --dbpath <path> 启动若干个mongod
2. 随便连接一台服务器, 执行
```
rs.initiate( {
   _id: "configReplSet",
   configsvr: true,
   members: [
      { _id: 0, host: "<host1>:<port1>" },
      { _id: 1, host: "<host2>:<port2>" },
      { _id: 2, host: "<host3>:<port3>" }
   ]
} )
```
## 启动mongos ##
mongos很轻量级, 不需要dbpath, 通常mongos和你的应用程序服务器跑在一起
mongos --configdb configReplSet/<cfgsvr1:port1>,<cfgsvr2:port2>,<cfgsvr3:port3>

## 添加分片到集群里 ##
1. 连接到mongos
2. sh.addShard( "rs1/mongodb0.example.net:27017" ), 字符串指的是一个副本集 rs1会作为这个分片的名字

## 对数据库启动分片 ##
1. 连接到mongos
2. sh.enableSharding('msg') 对msg数据库启动分片, 这样msg数据库上的集合就不一定只会放在一个分片上了

## 对集合进行分片 ##
1. 连接到mongos
2. sh.shardCollection('msg.users',{name:1}) 以name为shard key, 对msg数据库下的users集合进行分片
	1. name:'hashed' 会建立hashed shard key, 这要求具有name的hashed索引
	2. 一个字段可以同时有普通索引和hashed索引
	3. 参考shard key的选择

# 使用hashed片键 #
当你对一个空集合执行 sh.shardCollection(...,{a:'hashed'})就对它创建了一个hashed片键
默认情况下MongoDB会马上在每个分片上产生两个空的chunk文件.
看到 https://docs.mongodb.org/manual/tutorial/shard-collection-with-a-hashed-shard-key/


# Upgrade Config Servers to Replica Set #
https://docs.mongodb.org/manual/tutorial/upgrade-config-servers-to-replica-set/
旧版本的配置服务器一般要求3台, 一旦有一台不可访问, 那么元数据就会变成只读.
将旧版本的配置服务器升级为副本集, 这个过程有点麻烦, 好像没有简单点的方法啊
假设有3台配置服务器a,b,c
步骤:
1. 关闭平衡器
2. 连接到a, 执行:
```
rs.initiate( {
   _id: "csReplSet",
   version: 1,
   configsvr: true,
   members: [ { _id: 0, host: "<host>:<port>" } ]
} )
```
这样a自己就在一个副本集了
3. 重启a服务器, 并使用如下的参数
```
mongod --configsvr --replSet csReplSet --configsvrMode=sccc --storageEngine <storageEngine> ...
```
使用 --configsvrMode=sccc 是为了兼容这种旧版本的配置模式, 此时整个集群应该还是正常工作的
4. 启动新的mongod实例, 添加到副本集, 这些实例必须使用WiredTiger存储引擎
	1. 如果a是 MMAPv1 的话, 那么就添加3台mongod
	2. 如果a是 WiredTIger 的话, 那么就添加2台mongod
	3. 用如下的方式启动每个mongod实例:
		```
		mongod --configsvr --replSet csReplSet --port <port> --dbpath <path>
		```
5. 连接到服务器a, 将其他3台/2台添加到副本集  
	1. 并且让他们初始化为:
		1. 优先级=0
		2. 投票=0
6. 确保
	1. 新的节点完成初始化同步, 并且达到secondary状态
	2. 你可以使用rs.status()是否达到了相应的状态
7. 关闭服务器b, 此时集群元数据变成只读的!!!
8. 将所有的副本集成员设置成:
	1. 优先级=1
	2. 投票=1
9. 让a退位: rs.stepDown()
10. 依次关闭:
	1. mongos
	2. shards
	3. c
11. 关闭服务器a, 如果a是MMAPv1, 那就从副本集里移除它
12. 如果a是WiredTiger, 那么就重启它就行了, 注意此时重启参数不用带 -configsvrMode=sccc
13. 启动shards
14. 启动mongos
	1. mongos --configdb csReplSet/<rsconfigsver1:port1>,<rsconfigsver2:port2>,<rsconfigsver3:port3>
15. 启动平衡器

# 将只有一个分片的集群转成副本集 #
1. 只要让你的程序不再连接到mongos, 而是副本集就行了, --shardsrv 选项可以考虑移除.
2. 然后就可以关闭你的mongos和配置服务器了

# 将有多分片的集群转成一个副本集 #
也就是要将所有数据都合并到一个副本集上
1. 新建一个副本集, 必须保证他的容量足够大
2. 停止写入到集群
	1. 可以直接将你的应用程序关了
	2. 将mongos关了, 如果是这样的话, 你的应用程序那里就会出错, 你最好要有应对方法
3. 使用mongodump mongostore将所有分片的数据导入到新的副本集
4. 配置应用程序连接到副本集

# 查看集群的配置 #
查看启动了分片的数据库
use config
db.databases.find( { "partitioned": true } )

db.printShardingStatus()
sh.status()


# 如何将整个集群移动到新的硬件环境 #
https://docs.mongodb.org/manual/tutorial/migrate-sharded-cluster-to-new-hardware/

# 配置集群的平衡器 #
https://docs.mongodb.org/manual/tutorial/configure-sharded-cluster-balancer/
https://docs.mongodb.org/manual/tutorial/manage-sharded-cluster-balancer/

设置其可以运行的时间, 称之为activeWindow
https://docs.mongodb.org/manual/tutorial/manage-sharded-cluster-balancer/#sharding-schedule-balancing-window

sh.stopBalancer()
sh.disableBalancing()/sh.enableBalancing()
db.getSiblingDB("config").collections.findOne({_id : "students.grades"}).noBalance;
sh.setBalancerState(false)
db.settings.update( { _id: "balancer" }, { $set : { stopped: true } } ,  { upsert: true } )
sh.setBalancerState(true)
sh.getBalancerState()
New in version 3.0.0: You can also see if the balancer is enabled using sh.status(). The currently-enabled field indicates if the balancer is enabled.
设置块大小
设置每个分片的最大大小
可以设置进行块移动的一个阀值(当两个分片的块数相差超过这个值的时候就会启动)

Secondary Throttle 用于控制在块移动过程中, 如果还有写入数据, 那么这些数据要写入到哪里

# 如何删除一个分片 #
https://docs.mongodb.org/manual/tutorial/remove-shards-from-cluster/
1. 保证平衡器启动
2. 确定分片的名字
3. 
```
use admin
db.runCommand( { removeShard: "mongodb0" } )
再次执行这个语句就可以看到执行的过程汇报, 比如还剩余多少个块 多少个数据库
```
4. 如果你的分片是某个数据库的primary shard, 那么还要执行movePrimary操作
5. 再次执行3, 可以看到成功的信息

# 合并块 #
v2.6 提供了mergeChunks 似乎只能将一个空块和另外一个块合并起来?
https://docs.mongodb.org/manual/tutorial/merge-chunks-in-sharded-cluster/

# 拆分快 #
https://docs.mongodb.org/manual/tutorial/split-chunks-in-sharded-cluster/
splitFind
splitAt

# 移动块 #
https://docs.mongodb.org/manual/tutorial/migrate-chunks-in-sharded-cluster/
```
db.adminCommand( { moveChunk : "myapp.users",
                   find : {username : "smith"},
                   to : "mongodb-shard3.example.net" } )
```

# 集群环境下的唯一键 #
https://docs.mongodb.org/manual/tutorial/enforce-unique-keys-for-sharded-collections/
1. 对shard key是可以做到保证它的唯一性的, 因此你可以让你的唯一键作为shard key
	1. db.runCommand( { shardCollection : "test.users" , key : { email : 1 } , unique : true } );
2. 建立一个集合, 这个集合只有一个字段_id, 这个_id就用于存放你的唯一键
	1. 每次你要插入一个文档的时候, 先将你的_id插入到这个集合, 如果没有发生错误, 那么说明你的唯一键真的是唯一的
	2. 然后你就可以安全的将这个文档插入到集群了
3. 用其他方式保证你的唯一键
	1. 比如uuid, 或递增序列
	2. 应用服务器使用redis来管理id?




# Query Isolation #
1. 递增的shard key 会导致所有的插入操作都发生在一个shard上
2. 如果shard key是完全随机的, 很多时候就不能将操作完全限制在一个shard上, 而是要发到所有shard上去操作, 然后再合并结果
	1. mongos 需要经请求发给多个分片去处理, 然后合并它们的结果
	2. 如果需要排序的话, 可能会采用归并排序, 想想归并排序的原理吧

选择shard key的标准:
1. 经常被用来查询的字段
2. 性能最相关
3. 不同的取值要多(如果取值确实少的话, 可以采用复合 shard key 的方法)
4. 尽量均匀分布, 使得查询能被隔离
5. 产生之后通常不会变化

# 高可用 #
1. 应用服务器一般会自己跑一台mongos
2. 应用程序完全和mongos交互


mongos会从配置服务器那里获取一个当前元信息的副本, 由此它就可以知道哪些shard key的取值在哪个shard上

当mongos收到一个查询的时候:
1. 决定所有必须收到查询的shard
2. 为上面的shard建立一个游标

通常如果你的查询里使用 shard key 来作为过滤条件, 那么mongos通常可以将这个请求只发送给一部分的shard
否则就要发给全部的shard了

如果你的shard key:
{ zipcode: 1, u_id: 1, c_date: 1 }

那么像下面的查询是可以的: 这是不是跟普通的index一样???
{ zipcode: 1 }
{ zipcode: 1, u_id: 1 }
{ zipcode: 1, u_id: 1, c_date: 1 }

这些查询条件必须要是 shard key 的前缀 顺序不同要紧么?
比如 {  u_id: 1, zipcode: 1} 能被过滤么? 
答案是可以的, 你可以自己试试
只要你的查询里包含了 shard key 的前缀就行
{cdate:2, zipcode:3} 因为包含了前缀 zipcode 因此是可以的

下面的查询也是OK的 显然只要发给 zipcode=1的那些shard去过滤就行了
{ zipcode: 1, username:'abc' }


# 并不是所有的集合都需要分片 #
可以对数据库进行分片, 这样会导致它的集合不一定在同一个节点上
可以对集合进行分片, 这样会导致它的文档不一定在同一个节点上

有些集合比较小, 就没有必要进行分片了

# Tag Aware Sharding #
给一个shard打上一个tag标记, 然后这些tag就会收到所有的插入操作,在这个tag所指定的范围内.

# Cluster Balancer #
每个mongos都有可能变身成一个balancer, 然后它会请求config server上的一把锁, 用于锁住config server上的集合
然后检查数据分布是否均衡, 如果均衡, 它就退出balancer模式, 回归普通的mongos.
如果不均衡, 那么它可能会考虑开始一次块的迁移操作直到平衡(一次只会对一个块进行操作) 或 拆分.

1. 一次执行移动一个chunk, 见 chunk migration queuing
2. 当拥有最多chunk的shard与拥有最小chunk的shard的chunk数量相差超过一定阀值时
3. 做某些操作的时候你可能需要将balancer关掉, 以免影响你
4. schedule the balancing window

在PDF文档里搜索 Migration Thresholds 可以看到对应的阀值
比如当chunk数量大于80的时候, 要求极差为8才会触发块的迁移, 这是为了减少不必要的迁移

# remove一个shard之前要考虑的 #
一个shard可能是某个database的primary shard
In a cluster, a database with unsharded collections stores those collections only on a single shard. That shard becomes
the primary shard for that database. (Different databases in a cluster can have different primary shards.)

movePrimary 之后, 确定没有任何的db以该shard为primary
然后才可以安全的 sh.remove(该shard)

清除jumbo
