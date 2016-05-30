几个关键的类
CacheManager 有一个名字, 在JVM范围内要唯一, create方法总是返回唯一的对象

# Cache #
线程安全

# Element #
name 名字

maxElementsInMemory
内存中最多的个数

maxElementsOnDisk
硬盘中最多的个数

eternal
元素是不是永久的, 如果是永久的话, 那么一些超时之类的参数就会被忽略

timeToIdleSeconds
如果元素不是永久的, 那么它距离上次被访问多久之后就会被删除

timeToLiveSeconds
如果元素不是永久的, 那么它距离被创建后多久就会被删除

overflowToDisk
是否溢出到硬盘上

diskPersistent
硬盘是否持久化
如果为true的话, 那么重启之后会使用硬盘上的数据依旧可以作为缓存
否则会被清空重来, 缓存冷掉了

memoryStoreEvictionPolicy
溢出策略
LRU
FIFO
LFU

diskExpiryThreadIntervalSeconds
对于溢出到硬盘上的对象, 多久检查一次过期时间, 默认是120秒

maxEntriesLocalHeap
maxBytesLocalHeap

maxEntriesLocalDisk
maxBytesLocalDisk

maxElementsInMemory="1"
maxElementsOnDisk="5"


设置数值的地方都支持 20% 2k 5m 1g 之类的表达式

可以在 CacheManager 级别上设置 各种缓存的大小
这样它管理的所有Cache的各种缓存大小都不能超过它设置的大小
并且可以使用 20%之类的表达式

如果有cache没有指定大小, 那么哪些没有指定大小的cache平分剩下的缓存
如果大小超过了就会扔出异常

# 数据刷新检查 #
怎么知道数据库里的数据已经刷新了? 这样缓存里的数据才能跟着更新, 才能保证一致性.

1. 采用过期策略, 比如说10分钟过期, 那么10分钟后数据就可以保持一致了
2. 消息总线: 将对数据的修改限制在几个 方法, 乃至程序里
	1. 因为可以发生修改的地方是有限个的, 并且是被限制的
	2. 因此当发生修改了之后, 可以通过各种消息通知的方式通知其他节点某些数据被修改了
3. 数据库触发器
	1. 可能会导致数据库负担太大

# 可以引用的一些变量 #
user.home
user.dir
java.io.tmpdir
ehcache.disk.store.dir
java -Dehcache.disk.store.dir=/u01/myapp/
diskdir.




# 使用模式 #
## cache-aside ##
在你的应有程序里这样做:
如果有cache就先用cache, 否则去取数据, 然后放到cache里

## cache-as-sor ##
类似 cache-aside, 但是当get到null的时候, 由cache去取数据, 而不是你的应用程序

## read-through ##
当get到null的时候, 由缓存去数据库获取数据
SelfPopulatingCache

## write-through ##
写入到缓存里, 同时也写入到数据库里

## write-behind ##
先标记为脏数据, 以后再写入到数据库里

# Copy Cache #
copyOnRead
copyOnWrite

指的是当你往cache里put或get一个元素的时候, 要如何复制
如果你不复制的话, 那么用的是引用

很有可能你会修改在缓存里的元素
CopyStrategy


# 可重启和持久化 #
开源版本提供了一个有限的实现, 完整版需要 BigMemory Go , BigMemory Max

# Compatibility with Previous Versions #
介绍了如何持久化内存
