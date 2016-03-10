http://www.redis.io/topics/memory-optimization
2016-02-26

http://www.cnblogs.com/good90/p/3486732.html

从v2.2以后, 对于常见的数据结构, 当它里面的元素比较少的时候, 会采用一种特殊的数据结构, 这样可以使得更少的内存.
这是对用户透明的, 这是CPU和内存的trade off.
有如下的参数可以调整:

entries 表示的是数量
value 表示的是单个元素的最大大小, 应该是字节?

hash-max-zipmap-entries 512 (hash-max-ziplist-entries for Redis >= 2.6)
hash-max-zipmap-value 64  (hash-max-ziplist-value for Redis >= 2.6)
list-max-ziplist-entries 512
list-max-ziplist-value 64
zset-max-ziplist-entries 128
zset-max-ziplist-value 64

set的优化只会发生在你的元素长得像int64以内的整数时
set-max-intset-entries 512

Redis是用32位的目标编译的, 为了减少内存的使用, 比如指针更小.
但是这样的话内存使用就限制在4GB了
RDB和AOF文件是32和64兼容的.

二进制安全的字符串可以用于存储大量用户的性别, 每个用户对应一个bit.

小的hash可以被压缩得使用很少的空间, 因此推荐使用, 但怎样算是少并没有说.
比如一个用户的信息, name nickname email password ... 就可以作为一个hash保存

# Using hashes to abstract a very memory efficient plain key-value store on top of Redis #
一个事实:一个key对应一个hash(value都是字符串) 使用的内存 比 多个key对应多个字符串 少得多.
当一个hash的key比较少的时候, 内部其实是使用一个数组来代替的, 而不是真正的hash结构
因为key少的时候, 这样反而比较快(比如由于CPU cache)
但是hash的字段就不能带有过期设置了
所以用一个hash来去表示一个对象是很内存高效的

Imagine we want to use Redis as a cache for many small objects, that can be JSON encoded objects, small HTML fragments, simple key -> boolean values and so forth. Basically anything is a string -> string map with small keys and values.

As you can see every hash will end containing 100 fields, that is an optimal compromise between CPU and memory saved.

1. Redis不会轻易释放内存, 当你删掉一个key之后, 它对应的内存不一定会释放.
2. 按照你的数据的峰值来设置 maxmemory
3. Resident Set Size (RSS), 指的是该程序消耗的内存
4. fragmentation ratio 有时是不可信的, 当你的峰值内存比当前使用的内存大很多的时候, fr=redis已经分片的内存 / RSS
通常建议设置一个maxmemory, 否则Redis会尝试吃掉尽量多的内存.

object:1234 -> object:12 34
object:12是一个哈希 34是一个key
这样每个hash就会保存100个entry
那 object: 1234 怎么样呢?
就是总是将object:Number拆成两部分
使得每个hash保存最多100个元素, 这样节省的空间大概是10倍!

将maxmemory-policy设置为noeviction会使得内存满的时候返回一个错误
而不会驱逐元素
