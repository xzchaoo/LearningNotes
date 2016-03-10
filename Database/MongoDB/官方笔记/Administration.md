# Administration Concepts #
## Operational Strategies ##
### MongoDB Backup Methods ###
1. 直接复制底层的数据文件
2. 使用mongodump和mongostore工具
	1. 对于小型数据库很有效, 可以有选择性的备份和恢复
	2. 不会保存索引, 因此恢复之后需要重建索引
	3. 不必关闭mongod.exe
3. MongoDB Cloud Manager Backup
	1. The MongoDB Cloud Manager supports the backing up and restoring of MongoDB deployments.
	2. MongoDB Cloud Manager continually backs up MongoDB replica sets and sharded clusters by reading the oplog data from your MongoDB deployment.
4. Ops Manager Backup Software

### Monitoring for MongoDB ###
策略
1. MongoDB自带了一些工具可以实时汇报数据库的情况
	1. mongostat
	2. mongotop
	3. 
2. 数据库命令也可以对数据库进行一些统计
	1. db.serverStatus() 可以查看 硬盘使用 内存使用 连接情况 日志信息 索引信息
	2. db.stats() 可以反映出存储的使用情况
	3. db.users.stats() 可以查看users集合的存储情况
	4. rs.status() sh.status()可以查看副本集和分片的情况
3. MongoDB Cloud Manager, Ops Manager 这两个工具 和 第三方工具
	1. https://docs.mongodb.org/manual/administration/monitoring/


# mongostat #
https://docs.mongodb.org/manual/reference/program/mongostat/#bin.mongostat
mongostat --host=ip:port --username=... --password=... --authenticationDatabase=... 5
5所在的位置是<sleeptime>, 5表示每5秒输出一次 默认是1秒
--json以json格式
--all 显示所有字段
返回的数据表明了过去<sleeptime>期间每秒的平均值
各个字段的意义去官网看
mongostat --help查看帮助

# mongotop #
用法与mongostat一样
可以提供花费在每个集合上的读和写的时间


## Run-time Database Configuration ##
https://docs.mongodb.org/manual/administration/configuration/

## Production Notes ##
不同的存储引擎
Read/Write Concern
Manage Connection Pool Sizes¶


# Capped Collections #
createCollection('集合名',{capped:true, size:10000, max:100})
建立一个大小为10000字节的固定结合, 最多存放100个元素, max可选
一般情况下集合的文档就是一个紧接着一个
1. 固定集合的_id默认有索引
2. 如果你需要执行update操作, 那么记得建立索引, 一面扫描整个结合
3. 使用MMAPv1引擎, 你只能做原地级别的修改(就是不能使得文档大小增大, 否则会导致更新失败)
4. 使用自然顺序去访问固定集合效率很高
5. 聚合的$out阶段不能输出到一个固定集合
6. 使用自然索引 db.cappedCollection.find().sort( { $natural: -1 } ) 这是按照插入顺序
7. 检查集合是否是capped的 db.collection.isCapped()
8. 普通集合转成固定集合 db.runCommand({"convertToCapped": "mycoll", size: 100000});


Replica Sets with MMAPv1 Secondaries
如果你在主节点将一个文档的大小弄小了, 然后备份节点会用分配一个比较小的空间来存放这个文档, 等你以后再把这个文档大小弄大, 对于主节点来说, 它没有任何问题, 因为还有额外的空间可以存放, 但是备份节点就会出问题了
https://docs.mongodb.org/manual/core/capped-collections/

# TTL 集合 #
db.log_events.createIndex( { "createdAt": 1 }, { expireAfterSeconds: 3600 } )
超时后这个文档会被删除

## 在一个特定的时间过时 ##
比如文档自己有一个属性叫做 expireAt
db.log_events.createIndex( { "expireAt": 1 }, { expireAfterSeconds: 0 } )
这个这个文档就会在时间超过该文档的expireAt属性的时间后失效从而被删除
可能这个文档会在超过这个时间后的若干秒后才删除 不会马上就删除

# 锁性能 #
db.serverStatus()
https://docs.mongodb.org/manual/reference/command/serverStatus/#dbcmd.serverStatus
globalLock.currentQueue.total如果很高的话, 说明当前的并发很高
globalLock.totalTime如果比uptime搞的话, 那说明数据库长时间处于锁状态

# Analyzing MongoDB Performance #
https://docs.mongodb.org/manual/administration/analyzing-mongodb-performance/

# Evaluate Performance of Current Operations #
评估当前操作的性能
db.currentOp()
explain(true)

# Optimize Query Performance #
优化查询性能
1. 索引
2. 用好limit sort skip
3. 只返回需要的字段
4. 使用hint强制指定索引
5. 使用$inc 而不是写入新值

# Design Notes #
