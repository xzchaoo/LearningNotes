# Using Redis as an LRU cache #
http://www.redis.io/topics/lru-cache
将Redis作为一个LRU cache使用

当将Redis作为一个cache使用的时候, 有时候我们希望它自动的将旧值驱逐出去, 然后加入新值. 这种行为是memcached的默认行为.

LRU是事实上唯一支持的驱逐方法.
这篇文档介绍了更多Redis的maxmemory指令, 这个志林用于限制内存使用, 并且深入介绍了LRU算法.

## Maxmemory configuration directive ##
### 修改方法 ###
1. 在redis.conf里 修改maxmemory的值
2. 运行时使用 config set命令

### 例子 ###
 maxmemory 100mb
 设置成0 表示没有限制
 对于64位系统默认值就是0 而32位默认值是3GB
 
 当达到这个阀值的时候, redis会采取一些策略.
 比如简单的返回一个错误说内存已满; 或删除一些数据.
 
 ## Eviction policies ##
 修改maxmemory-policy选项
 1. noeviction
	 1. 返回一个错误信息
2. allkeys-lru
	1. 驱逐最不常用的keys
3. volatile-lru
	1. 同上, 但是只会驱逐带有 expire set的key
4. allkeys-random
	1. 随机
5. volatile-random
	1. 随机, 但是只会驱逐带有 expire set的key
6. volatile-ttl
	1. 只会驱逐带有 expire set的key, 然后驱逐ttl尽量小的key
当你将单个实例用于缓存和存储,allkeys-lru和volatile-random是最有用的
但是通常运行两个实例是个好主意.

## How the eviction process works ##
1. 一个客户端运行了一个命令, 导致更多数据被添加
2. Redis发现内存不够用了, 它会驱逐一些keys
3. 命令继续执行

## Approximated LRU algorithm ##
Redis的LRU算法并不是一个准确的实现, Redis不会挑出最佳的候选者进行删除, 因为要这样的话需要维护挺多信息.
Redis会运行一个大概的LRUSuanfa,它会对一部分的key进行采样, 然后只驱逐这部分的key里最旧的.
你可以通过设置"maxmemory-samples"的值来干涉这一行为
maxmemory-samples 5 每次对5个key进行采样
提高这个值会使得算法效率降低, 然后更接近真正的LRU算法


