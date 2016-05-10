阻塞队列
发布/订阅

利用Redis对数值型的原子性可以实现自动递增的id
或处理瞬间高并发问题

比如 00:00 准时开放100张门票的抢购.
可以先再redis里设置一个值为100的属性
然后每次 (-1 and get) 保证这个动作是原子性的
如果get到的值>=0 那么说明你抢到票了
这个适用于有多台端服务器, 当然如果你只有一台应用服务器的话, 那么直接在内存里使用一个原子性的Integer就行了引用

事务 不能回滚

先监控某些值
watch ...
watch ...

其他的操作
...

开启事务
multi

设置某些值
set ...
其他操作
...
提交事务
exec

当且仅当被监控的值只在multi-exec之间被修改, 该事务才能开始执行
也就是exec一执行, 就会检查所有被watch的值是否还是当时的值, 如果不是, 那么这个事务连执行都不!

执行exec之后就会取消所有的监控了
unwatch 也可以主动取消所有的监控



驱逐策略
maxmemory-policy
volatile-lru 只对含有ttl的键
allkeys-lru
volatile-random 只对含有ttl的键
allkeys-random
volatile-ttl
noeviction 不驱逐 这会返回一个错误

redis有sort命令?
可以按照数值或字母排序
支持DESC
sort tag:ruby:posts desc limit 1 2

BY参数
sort tag:ruby:posts by post:*->time DESC
sort sortbylist by timescore:* desc

GET参数 与 BY类似 不过是用于控制返回的值

优先队列
brpop key1 key2 ... 作用是阻塞性的从key1 key2 ... 所对应的列表右边弹出元素
会从左向右进行检测
如果你有比较重要的任务就可以放在靠左边

可以实现生产者消费者模式

发布订阅
publish channel message
subscribe channel

几种类型的消息
subscribe
message
ubsubscribe

模式订阅
psubscribe news.sports.?*
这个订阅必须通过 punsubscribe news.sports.?* 才能取消订阅


# 管道 #
将一些指令先queue起来(在redis端), 然后最终一起执行, 这样省得你每次网络来回


OBJECT ENCODING foo
可以查看foo对象的内部编码方式
由此你可以观察到当数量比较少的时候, 某些数据结构是有不同的实现的

散列类型
REDIS_ENCODING_HT REDIS_ENCODING_ZIPLIST
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
当字段数小于512 并且 字段值的长度都小于 64的时候就会使用ZIPLIST来实现散列
否则就会使用HT

列表类型
REDIS_ENCODING_LINKEDLIST REDIS_ENCODING_ZIPLIST
list-max-ziplist-entries 512
list-max-ziplist-value 64
当元素数量<=512 并且每个元素大小 <=64 的时候就使用ZIPLIST
否则使用普通的双向列表

集合
REDIS_ENCODING_HT REDIS_ENCODING_INTSET

set-max-intset-entires 512
只有当集合里的元素都是整数的时候才会使用这个优化
一开始支持长度为2字节的整数, 当你超过这个限制之后, 自动升级为4字节, 乃至8字节, 最终不行了就变成HT了

一般升级是单向的, 没办法退化

zset-max-ziplist-entries 128
zet-max-ziplist-value 64

SKIPLIST ZIPLIST

副本集
一般master关闭RDB, 如何关闭? 将所有 save 开头的注释掉就行了.
slave启动RDB, 如何启动?

Lua脚本


# 安全 #
requirepass 你的密码
连接到redis之后, 执行 auth 密码 来进行认证, 只有通过认证了之后才可以执行具体的操作

从服务器连主服务器的时候需要 设置 masterauth 密码
考虑到使用sentinel的时候, master 可能会发生变化, 因此这一个圈子里的节点的密码都应该是一样的?

# 持久化 #

## RDB ##
RDB是井整个内存快照保存到 下面参数的文件中
dbfilename dump.rdb
当redis启动的时候 将会恢复rdb到当前的内存里

另外需要指定规则, 如果要禁用rdb, 只需要将save ...都注释掉就行了
save 900 1
save 300 10
save 60 10000

意思是60秒内如果发生了10000次修改, 就dump一次
300秒内如果10次修改就dump一次
900秒内如果1次...

过程如下:
1. dump被触发 (比如60秒内10000次...)
2. fork出一个子进程, 进行dump到一个临时文件中
3. 主进程继续处理
4. 子进程dump完毕之后重命名到相应的rdb文件

rdbcompression yes 是否压缩RDB
rdbchecksum yes 校验

相关命令
save 阻塞性的进行备份RDP
bgsave 在后台进行save

## AOF ##
启动:
appendonly yes
appendfilename appendonly.aof

AOF是通过: 将每一个写请求都记录到日志文件里, 然后通过"重播"的方式就可以进行数据的恢复.
比如执行
set a 1
set b 2
set c 3
set a 4
这些操作就都会被写入到AOF文件中(当然它保存的格式不一样), 每次都是往最后面进行追加的
当然执行完一条语句之后并不会马上写日志, 好歹也是要稍微批处理一下的

当AOF文件太大的时候, 还会进行重写AOF文件:
它的作用是以当前的内存里的数据为依据, 重新生成AOF文件
比如执行完上面4句话之后, 我又做了一堆得修改, 使得我的内存现在是
a 11
b 22
c 33
那么显然 我只需要保存
set a 11
set b 22
set c 33
就行了, 其他的语句根本没必要保存, 就算保存了他们也会被后面的语句给覆盖掉.
这种方式就可以减小AOF文件的大小

auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
当AOF文件至少64mb的时候才允许重写, 并且要求当前的AOF文件大小比上一次AOF文件大小大 100%(即是上一次的2倍大)
也可以使用 bgrewriteaof 手动触发一次重写

刚才说到, 日志并不是每条指令都会马上写日志的, 下面是几个策略:
appendfsync everysec 每秒写一次日志
always 每条指令写一次策略
no 依靠操作系统来做, 默认30秒一次

RDB与AOF可以同时使用, 当启动REDIS的时候, 会使用AOF来恢复, 因为AOF里的数据肯定比RDB里的数据好


