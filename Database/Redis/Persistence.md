http://www.redis.io/topics/persistence

Redis提供了不同范围的持久化选项
1. RDB持久化提供了 point-in-time 快照 每间隔一段时间.
2. AOF持久化记录每次写操作, 下次服务器启动的时候会执行来构建出数据集.
3. 可以混合RDB和AOF

# RDB #
每隔n分钟或n次写操作之后, 从内存dump数据形成rdb文件, 压缩放到备份文件.
1. 写快照
2. fork子进程来做备份操作
3. 比AOF快
4. 但是数据丢失可能性比AOF大
5. 因为它是间隔一段时间备份的, 所以可能丢失一段时间内的数据
6. RDB在大量写的情况下可以保证一个最大的latency
7. 要启动RDB只需要打开配置文件里的 save n m 就行了
# AOF #
1. 更持久
2. 刷新间隔更快
3. 写日志还是挺快的, 因为是append模式
4. 支持在后台rewrite AOF, 当日志文件太大
	1. 这个行为对redis不会有啥太大的影响
5. AOF格式良好, 人可以看懂
6. AOF文件通常比较大, 比RDB文件大
7. AOF一般比RDB慢
	1. 主要是跟fsync策略有关
8. 如果你对一个值修改了100次
	1. 那么最后只有一个值留在数据库里
	2. 但是日志文件里却记录了100次的修改
9. 当AOF文件太大的时候, 会启动一个进程对AOF文件进行重写
	1. AOF太大是因为有很多无用的记录, 比如第8条带来的影响
	2. AOF会做的优化就是将多个命令合并成一条命令, 这样可以消除第8条的影响
	
建议单独使用 RDB 或 混合两个用

save 60 1000
的意思是60秒内如果发生了1000次修改, 那么就生成一次快照
1. forks, 产生一个子进程
2. 子进程开始将数据集写入到一个临时的RDB文件
3. 写完后, 子进程将这个临时的RDB文件重命名以替换旧的备份文件

http://www.redis.io/topics/persistence#how-durable-is-the-append-only-file
如何选择RDB或AOF

使用RDB模式
配置save n m即可

使用AOF模式
appendonly yes , 设置为yes就打开了AOF功能了
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
appendfsync always
appendfsync everysec
appendfsync no