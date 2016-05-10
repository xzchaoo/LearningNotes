# Replication #
http://www.redis.io/topics/replication
1. slave定期从副本集流中接受一部分数据
2. 一个master可以有多个slave
3. slave可以接受来自其他slave的链接, 因此所有服务器可以线性连接
4. slave的一些同步操作不会导致master阻塞
5. 可以用于支持大量读取 或 仅仅只是备份而已
6. 从服务器一般是只读的
7. 建议master打开persistence
8. 如果master关闭persistence, 那么要保证master不会自动重启

## How Redis replication works ##
1. master开始一个后台保存(bgsave 产生新的RDB)
2. 在后台保存结束之前收到的所有写请求都会被保存到一个buffer里
3. 后台保存完成的时候,master将这些文件发功给slave, slave将它保存到底盘上, 然后load到内存.
4. master将buffer里的写请求发送给slave.
5. 当master与slave之间的连接段了的时候, slave可以自动重新连接
6. 如果有多个slave请求master同步数据, master也只会开一个后台处理而已.
7. 当master与slave重连接之后, 有可能会进行数据的重新同步, 如果没办法重新同步, 那么很有可能会进行一次完全的重新同步(slave删掉数据再来).

## Partial resynchronization ##
1. 在已有数据的基础上进行同步
2. 在mster处会建立一个在内存的backlog
3. master与每个slave会有一个 replication offset 和  master run id
4. 当master与slave重新建立连接后, slave会用这两个依据请求继续同步.
5. 如果 master run id 没变, 并且offset也是合法的, 那么就虎子继续同步.
6. 否则就需要一个完全的同步了
 
## Diskless replication ##
1. 完全同步会让master创建一个RDB文件在磁盘上, 然后将它发给slave
2. 新版本会支持diskless replication: 开一个后台进程直接将RDB数据发功给slave, 而不会生成一个RDB文件作为间接存储
3. The feature is currently considered experimental.
4. 相关参数 repl-diskless-sync, repl-diskless-sync-delay

## Configuration ##
1. 只需要配置 slaveof ip port 和 slave-read-only 即可, 表明这个redis是某个redis的slave, 还可以连接到slave执行slaveof命令
2. 一般来说master不需要打开RDB, 但需要打开AOF, 因为备份服务器带来的效果大于RDB
3. slave需要打开RDB, AOF一般不用了(因为没有master全), 或者只需要有一个slave的RDB打开就行了
4. 跟MongoDB类似, 一般由3个成员组成即可, 甚至2个就行了, 1+2 或 1+1

## Read-only slave ##
1. slave默认是readonly
2. slave-read-only 选项
3. 也可以在运行时进行修改
4. readonly的slave拒绝写请求, 但是其他的命令比如 config 依然可以执行, 这意味着不安全.

## Setting a slave to authenticate to a master ##
如果你的master需要密码验证
通过redis-cli执行config set masterauth <password>
或通过配置文件
masterauth <password>

## Allow writes only with N attached replicas ##
1. 允许master在至少有n个slave的情况下才接受写请求
2. slave每秒ping master一次
3. master记住每个slave 上一次ping的时间
4. 用户配置一个值M, 表示在M秒内有过ping的slave认为是存活的.
min-slaves-to-write <number of slaves>
min-slaves-max-lag <number of seconds>


一般master关闭RDB和AOF
从服务器可以启动RDB或AOF
