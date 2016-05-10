副本集
一台主节点 多台备份节点 主节点挂掉的时候 备份节点顶上
在不考虑数据一致性的情况下, 可以将读请求路由到备份节点上, 这样可以增加服务器的性能
写请求会被发给主节点, 

裁决者, 只用于投票, 并不保存数据
延迟备份

客户端可以设置 Read Preference 从而可以从备份节点读取数据
https://docs.mongodb.org/manual/core/read-preference/
db.getMongo().setSlaveOk()


# Replica Set Members #
副本集成员是一堆的mongod进程, 他们通常运行在不同的服务器上
其中一个mongod是主节点, 其他的成为备份节点
备份节点会复制主节点的操作, 来维护自己的数据与主节点相同
仲裁者:不备份数据, 只适用于投票
一般的配置是 一个主节点 两个备份节点
v3.0.0之后 一个副本集可以有50个成员, 但最多只有7个成员具有投票权(否则可能会很难决策出谁能成为主节点, 并且会消耗大量的网络)
## 其他属性 ##
优先级, 优先级越高越容易成为master, 如果当前的master优先级不是最高的(有可能是因为优先级最高的那个节点才刚刚起来), 那么等优先级最高的那个节点的数据已经和master一样的时候, 就会触发选举
隐藏
延迟

# 主节点 #
主节点上的写操作会被记录到oplog里
备份节点复制主节点的oplog, 然后重播它们的操作

# 备份节点 #
除了很普通的备份节点(他们有机会成为master)外, 有几种特殊的备份节点

## 优先级=0的节点 ##
优先级=0的节点不能成为主节点, 优先级高的节点在选举中有优先权(当然它的数据要足够新)
使用场景:
1. 弱服务器用于纯粹的备份
2. 让能力强的服务器成为主节点
3. 一般这种节点不会具有投票权, 在人数足够多的情况下
4. 一般是hidden
 
## 隐藏的节点 ##
1. 对客户端程序不可见
2. 要求优先级=0
3. db.isMaster()不会显示隐藏节点的信息
4. 隐藏成员在选举中可以投票
5. 延迟的节点应该是隐藏的, 因为它的数据不够新

## 延迟的节点 ##
1. 总是落后主节点一定时间, 这样一些操作可能来得及回滚
2. 必须是hidden=true priority=0
3. 可以对primary投票
4. 延迟的时间要根据你的需求和oplog来决定
5. 在分片环境中不太有用, 因为分片经常会有块的移动, 而延迟的节点没法体现出来

## 仲裁者 ##
1. 不保存数据, 仅具有投票权
2. 一般在偶数个成员的情况下才需要一个额外的仲裁者
	1. 因为4个成员的时候需要3个人投票才能成为primary
	2. 而4+1仲裁者, 也是需要3个人投票才能成为primary

# Replica Set Deployment Architectures #
1. 一般3个成员就行了, 1一个主节点, 2个普通的备份节点, 总之最好是奇数个成员
2. hidden和delayed成员用于特殊用途
3. 如果对读取要求非常高, 那么可以将读路由到备份节点
4. 让大多数成员位于同一个数据中心
5. 可以使用成员的tag, 来保证写操作已经被写入到了某一些节点上

# 部署情况 #
master + salve + arbiter

## 1+2 ##
1. 提供了不错的备份
2. 提供了不错的可用性
 
## 1+1+1 ##
1. 穷的时候用

## 4个或4个以上的时候 ##
https://docs.mongodb.org/manual/core/replica-set-architecture-four-members/
1. 奇数个成员具有投票权, 如果有需要可以使用仲裁者
2. 让可以投票的人数尽量少, 最多只能有7个
3. 将大多数的服务器集中于一个数据中心
4. 弱服务器使用priority=0

# Geographically Distributed Replica Sets #
考虑地理分布

3台:2台在一起, 并且优先级=1, 另外一台在另外一个地方, 优先级=0
这样就保证了只有前2台才有可能成为主节点, 而且可以一定的容错性

# Replica Set High Availability #
使用自动 failover 提供了高可用性
Changed in version 3.2: MongoDB introduces a version 1 of the replication protocol (protocolVersion: 1) to reduce replica set failover time and accelerates the detection of multiple simultaneous primaries.

## 选举 ##
发生在:
1. 初始化副本集时
2. 主节点退位, 可能是通过 stepDown()函数 或者是 出现了其他问题
3. 如果主节点自己无法访问大多数成员, 那么主节点会自动退位
修改votes属性=0, 可以让一个节点不能进行投票

## 回滚 ##
回滚发生在, 主节点接受了一些写操作, 但是还没来得及同步到备份服务器, 它就挂了.
此时某个备份服务器成为主节点, 后来刚才挂的主节点进来了, 但此时它已经不是主节点了.
并且它会发现, 自己有一部分数据是当前的主节点没有的, 这部分数据就是刚才的来不及同步的数据
因此这部分数据会被回滚, 并且保存到一个临时的文件(<database>.<collection>.<timestamp>.bson)里, 如果有需要, 手动进行恢复.
当回滚的数据超过300MB的时候, 也会出现错误, 需要手动解决.

## 避免回滚 ##
即要保证数据不仅写入到主节点, 至少也写入到一个备份服务器, 当然这也不一定能保证, 只是降低了回滚的几率.
指定 w=2 或 w=majority 或其他自定义的值
w=2的意思是至少写入2个节点才算成功(当然master已经算在里面了, 所以至少还要写入到一个slave上才算成功)

# Replica Set Read and Write Semantics #
## Write Concern for Replica Sets ##
如果w是一个int, 那么会保证至少写入w台服务器(包括主节点在内)
如果w是一个string
	如果=majority, 那么会写入大部分(总数/2+1)的服务器才算成功
	否则的话就认为是一个自定义的规则, 如果这个规则没有找到就发生错误.

wtimeout可以指定一个超时时间, 如果超时了就会返回一个错误, 但是有可能超时了数据依然写入数据库
修改默认的WriteConcern
```
var cfg = rs.conf()
//cfg.settings = {}
cfg.settings.getLastErrorDefaults = { w: "majority", wtimeout: 5000 };//5秒内要写入到大多数的节点才算成功
rs.reconfig(cfg)
```
注意这里的成功是指返回给客户端的结果, 有可能在5秒内已经写了4个节点, 因此被判断为失败
但这并不意味着这个操作就失败了, 有可能过了一会儿之后, 这个操作已经被同步到了所有节点上.


## Read Preference ##
https://docs.mongodb.org/manual/core/read-preference/
客户端可以用这个选项来控制自己允许从哪里读取数据(primary优先还是secondary优先)
local
majority
nearest
primaryPreferred
不太建议
 secondary and secondaryPreferred 

## 自定义tag ##
https://docs.mongodb.org/manual/tutorial/configure-replica-set-tag-sets/
为每个成员建立一个tag字段
conf.members[0].tags = { "dc_va": "rack1"}
conf.members[1].tags = { "dc_va": "rack2"}
conf.members[2].tags = { "dc_gto": "rack1"}
conf.members[3].tags = { "dc_gto": "rack2"}
conf.members[4].tags = { "dc_va": "rack1"}
conf.settings = { getLastErrorModes: { MultipleDC : { "dc_va": 1, "dc_gto": 1 } } }
rs.reconfig(conf)
这样写操作就会被写入到 至少一台dc_va 和 至少一台dc_gto才算成功



# Read Preference Processes #
当你选择了 non-primary 的读取方式
用以下的过程来决定要路由到哪个节点

## Member Selection ##
1. 收集所有合适的成员的信息
2. 如果指定了tag, 那么就用tag来过滤
3. 根据某项指标, 选择表现最优的节点
4. 找出所有ping距离在指定值以内的节点
5. 从上面找出来的随机选一个

### Request Association ###
指的是当你选定了一个节点, 并从它开始读取数据, 那么一段时间内, 你都会一直从这个节点读取数据, 不然你一会儿到这里, 一会儿到那里读取, 可能导致数据不会.
这种限制在以下几种情况下会解除:
1. 线程结束
2. 程序使用了不同的 读选项
3. 客户端收到套接字错误
4. 选举了新的主节点

## Auto-Retry ##

1. 尽量重复利用一个到 mongod 的 connection
2. 重新连接到一个新的节点的时候也会遵循之前的读选项
3. 重试3次
4. 检测到一个failover发生之后, 驱动会尽快的刷新副本集的状态

Changed in version 3.0.0: mongos instances take a slightly different approach. mongos instances return connections to secondaries to the connection pool after every request. As a result, the mongos reevaluates read preference for every operation.

## Read Preference in Sharded Clusters ##
似乎在分片集合的时候, mongos 自己维护了一个到个分片的连接池,
然后你客户端给的 read preference 可能不会生效
https://docs.mongodb.org/manual/core/read-preference-mechanics/

# Replica Set Oplog #
oplog是一个特殊的固定集合, 它保存了所有写操作的记录
所有的成员将oplog保存在local.oplog.rs集合里
可以使用配置文件里的oplogSizeMB来设置默认的oplog大小

## Workloads that Might Require a Larger Oplog Size ##
需要很大oplog的场景
1. 一次性更新很多文档
2. 删除几乎所有数据
3. 很多的原地修改
rs.printReplicationInfo() 
db.getReplicationInfo()


## 手动修改oplog大小 ##
https://docs.mongodb.org/manual/tutorial/change-oplog-size/
分配5%磁盘的可用空间, 但至少是1个g, 永远不超过50g
步骤
1. 以单机模式启动该节点
2. 保存最后一条记录, 在 local数据库下的oplog.rs集合里, db.temp.save( db.oplog.rs.find( { }, { ts: 1, h: 1 } ).sort( {$natural : -1} ).limit(1).next() )
3. 删除集合, 如果担心的话可以先做一下备份
4. 重建集合, 并设置想要的大小
5. 加入最后一条记录作为种子
6. 重新加入副本集


# Replica Set Data Synchronization #
1. 初始化同步
	1. 如果一个节点的数据是空的 那么它就会进行初始化同步
	2. 如果自己的数据落下太远, 那么也会进行初始化同步, 因为此时即使有oplog也没法恢复.
2. 在已有数据的情况下进行同步
	1. 会避开延迟节点和隐藏节点
	2. 一般情况下会从主节点进行同步
	3. 根据情况(ping值等)也可能从其他成员进行同步, 此时要求两个成员的buildIndexes的值是相同的

# Deploy a Replica Set #
你要保证每个节点能够被你的DNS服务器解析, 必要的时候加入到你的 /etc/host 里
这是用于产品阶段的部署方法
描述如何建立一个3个成员的副本集
1. 启动每个节点 重点是带有 --replSet rs0, 其中rs0是你们这个副本集的名字
2. 随便连接到其中一个节点, 然后执行rs.initiate(), 这个步骤可能会消耗一点时间, 具体你可以看输出的日志, 似乎是预先申请了一些磁盘空间, 并且填充了0数据, 像我一执行完毕之后, 这个节点对应的目录下就多了3个2G的local.1 local.2 local.3文件
3. 用 rs.conf() 检查一下初始的配置, 你会发现当前节点已经加入了该副本集
4. 通过rs.add('ip:port')的方式将其他的节点加入进来, 加的过程中可能会引发选举, 从而导致你连接的不再是主节点, 这要注意, 这个操作会使得加入进来的节点所在的机器进行类似2.的磁盘操作
5. 用rs.status()检查状态, 完成

# Deploy a Replica Set for Testing and Development #
1. test的时候,你可以将mongod都跑在一个机器上
2. mongod --port 27017 --dbpath /srv/mongodb/rs0-0 --replSet rs0 --smallfiles --oplogSize 128
	1. 这样的配置方式创建出来的数据文件夹会比较小, 因为是测试, 没必要太大
	2. 这条语句用不同的参数执行3次, 创建出3个mongod
	3. --smallfiles额--oplogSize可以减少磁盘占用, 反正现在是测试阶段
3. 随便连接到一个mongoed
4. 建立一个config对象, 里面的member只有你自己, 将它扔给rs.initiate()
5. 检查rs.conf()的结果
6. rs.add("ip:port")添加其他节点

# 对于仲裁者 #
1. 它占用的资源并不多, 所以可以和其他的比如应用服务器跑在一起
2. 设置 smallFiles=true journal.enabled=false
3. mongod --port 30000 --dbpath /data/arb --replSet rs, 仲裁者启动时依然需要一个dbpath

# 单个服务器转成副本集 #
1. 关闭mongod
2. mongod --port 27017 --dbpath /srv/mongodb/db0 --replSet rs0
3. rs.initiate()
4. rs.conf()
5. rs.status()
4. https://docs.mongodb.org/manual/tutorial/convert-standalone-to-replica-set/

# 当只有很少的成员可用的时候, 对副本集进行配置 #
https://docs.mongodb.org/manual/tutorial/reconfigure-replica-set-with-unavailable-members/
因为只有很少的成员可用, 所以副本集的数据基本上是只读的, 并且由于此时没有主节点, 因此你无法对副本集进行配置(配置只能在主节点上进行)

# 链式复制 #
https://docs.mongodb.org/manual/tutorial/manage-chained-replication/

# 要修改一个副本集的所有成员的host地址 #
https://docs.mongodb.org/manual/tutorial/change-hostnames-in-a-replica-set/
这里介绍了两个方法, 第一个方法可以在不停机的情况下进行, 有点麻烦, 但一了解原理后就很简单了.
第二种方法比较暴力, 直接全部停机, 然后对每一台服务器, 以单机模式启动它, 然后修改local数据库的system.replset文档里的host地址
然后再次重启所有服务器

# 强制指定一个节点的同步源 #
默认情况下同步元是主节点, 如果太忙或其他原因(比如根据ping值), 那么可能会从其他的备份节点进行同步(但这要求两个备份节点具有想同的buildIndexes值)
https://docs.mongodb.org/manual/tutorial/configure-replica-set-secondary-sync-target/
用rs.syncFrom('ip:port')修改默认的同步源, 这此时暂时的, 一旦发生下面的情况, 又会切换回primary
1. mongod重启后
2. 与同步源的链接关闭了
syncFrom在初始化同步的时候是无效的

同步源的要求
1. 数据比我新
2. Build indexes with the members[n].buildIndexes setting.


# 错误排查 #
https://docs.mongodb.org/manual/tutorial/troubleshoot-replica-sets/
rs.status() 副本集的情况
rs.printSlaveReplicationInfo() 所有成员的同步情况
rs.printReplicationInfo() 检查oplog的使用情况
rs.conf() 副本集的配置
rs.freeze()一段时间内防止当前成员进行选举
rs.reconfig()	
rs.stepDown(30) 将自己(master)降级, 并且30秒内自己无法成为master, 30秒后的事情就不知道了

keyFile
local.oplog.rs
oplogSize 控制 大小

local.system.replset.find()


1. 主节点会接受所有的写请求, 并且记录在oplog上
2. 主不断将oplog发给从, 让这个操作在从上"播放"

客户端通过 read preference 可以选择自己从哪里读取数据, 是主还是从.

# 杂情况 #
由于网络隔离的原因, 有可能一个副本集中2个节点都认为自己是primary
但是无论怎么样, 只有一个primary可以完成 {w:"majority"} 这个操作, 能完成这个操作的primary 才是真正的primary
另一个 "primary" 它只不过是还没有意识到自己的失败罢了.
客户端如果连接到一个 假的primary, 那么它发送的写操作有可能会被回滚.
而且它读取到的数据可能不是新的, 即使你用了 primary 优先


应该避免直接修改local.system.replset, 而是使用 rs.conf() + rs.reconfig()
