推荐使用新的WiredTiger存储引擎, 这也是3.2以后的新引擎
mongod --storageEngine wiredTiger

db.createCollection(
   "users",
   { storageEngine: { wiredTiger: { configString: "<option>=<setting>" } } }
)


https://docs.mongodb.org/manual/tutorial/change-standalone-wiredtiger/
mongodump --out <exportDataDestination>
mongod --storageEngine wiredTiger --dbpath <newWiredTigerDBPath>
mongorestore <exportDataDestination>

# mongodump #
用于从mongod,mongos导出数据, 适用于单机数据库
特点:
1. 数据排除
	1. 不导出local数据库
	2. 不导出索引
2. 版本兼容

--host=ip:port
--db/-d=数据库的名字 如果不指定的话就是所有的数据库
--collection/-c=集合的名字 如果不指定的话就是所有的集合
--query/-q=json字符串 用于过滤要导出的文档
--queryFile=json文件路径 适用于json太大太复杂的情况
--forceTableScan
--gzip 压缩
--out/-o=路径 导出的路径
--archive=输出到一个文件
--repair 只会输出合法的数据 如果数据是坏的 那么不会输出它 只能用于MMAPv1引擎 WiredTiger的话要使用mongod --repair
--oplog 启动oplog用于存放 从开始备份到备份结束时 数据库进行的写入, 因为有可能你备份的时候又有人写入数据了
--dumpDbUsersAndRoles 导出用户信息
--excludeCollection array of strings
--excludeCollectionsWithPrefix array of strings¶

mongodump --host=127.0.0.1:27302 --db=msg --out=c:/tmp
mongodump --host=127.0.0.1:27302 --db=msg --archive=c:/tmp/1.archive 新版才行

# mongostore #
https://docs.mongodb.org/manual/reference/program/mongorestore/
mongorestore --host=127.0.0.1:27302  --db=msg --objcheck --drop c:/tmp/msg
--dir 好像也可以用于指定路径 但不要同时使用

# 将单个服务器转成WiredTiger #
1. 用mongodump备份
2. 删除数据目录
3. 以WiredTiger为引擎启动一个mongoed
4. 用mongorestore恢复

# 将副本集转换成WiredTiger #
副本集本身支持存储引擎不同的成员
所以你可以挨个成员转换
https://docs.mongodb.org/manual/tutorial/change-replica-set-wiredtiger/
先降下一个备份节点, 删除它的所有数据
然后以 WiredTiger 数据引擎的方式启动
由于没有数据因此会开始一个初始化同步过程, 等这个过程完毕之后再对下一台做....

# 将分片集群转换成WiredTiger #
https://docs.mongodb.org/manual/tutorial/change-sharded-cluster-wiredtiger/

## 将配置服务器转换成WiredTiger ##
其实配置服务器继续使用MMAPv1也是行的
一旦你将配置服务器转换成WiredTiger的话, 那么就要求 单台服务器, 副本集, 配置服务器都是WiredTiger
假设有三台配置服务器a,b,c, a,b,c是按照你在 --configDB里指定的顺序的!
https://docs.mongodb.org/manual/tutorial/change-config-server-wiredtiger/
这个页面介绍了如何将配置服务器转成WiredTiger, 但是过程十分麻烦
其重点是 维持2台配置服务器在运行中, 这样会使得配置服务器的数据是只读的, 因为是只读的 这样就不会对我们产生干扰
1. 关了c
2. 处理b
3. 关了b, 打开c
4. 处理c
5. 处理a
6. 打开b

# 日志 #
1. 寻找最后一个checkpoint
2. 在日志中搜索这个checkpoint对应的位置
3. 从checkpoint以后开始恢复

v3.2以后
With journaling, WiredTiger creates a log record per transaction.
以下情况会把缓存的log buffer写入到磁盘
1. 每100毫秒
2. MongoDB sets checkpoints to occur in WiredTiger on user data at an interval of 60 seconds or when 2 GB of journal data has been written, whichever occurs first.
3. WriteConcern有j=true
4. 每个日志文件不超过100MB

Journaling and the MMAPv1 Storage Engine

