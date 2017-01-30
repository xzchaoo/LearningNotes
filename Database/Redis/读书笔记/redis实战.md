利用redis存储统计数据: 一些数据的 min max sum count 方差
利用一个有序集合来存储, 下面会解释为什么不是用一个hash来存储
min:xxx
max:xxx
sum:xxx
count:xxx
方差:xxx

当要往统计数据里添加一条数据的时候
min的更新只需要比较一下取最小值 max也类似 sum是累加 count是+1 方差无法直接计算 所以保存的是平方和 相当于也是只需要追加就行
由于redis可以保证单个操作的原子性 所以 sum count 平方和 都可以简单处理 用 zincrby就行了
但是 min max 的话就稍微麻烦一些:建立两个临时的有续集p1,p2 p1里只有一个元素 min:新加的值 p2只有一个元素 max:新加的值
然后
zunionstore(原有key名,[原有key名,p1],aggregate='min')
zunionstore(原有key名,[原有key名,p2],aggregate='max')
删除 p1 p2

利用这个策略就可以在不将值读入到内存的情况下做完更新
使用hash的话就没法做到了

想要作曲统计数据的话就使用 zrange(key,0,-1,withscores=True) 取出所有记录
然后member是统计的名称 score是统计的值


# 分布式锁 #
锁的粒度

1. 简易锁 P118
setnx 创建锁 + 超时
释放所的时候利用一个事务 判断的锁是否是自己持有



zookeeper 问题
1. 没有专门的人去维护, 没有支持
2. 靠心跳来维持通信, 每次发布的时候需要进行切换

redis
1. 主从

setnx
ttl
随机数校验删除

redis 信号量 P127
信号量其实和锁很类似 需要考虑超时问题
但有个数限制 所以不能直接使用string

这里考虑使用zset来实现
key是一个随机字符串, value是拿到信号量的时间, 假设1分钟是超时时间
当有人想要拿信号量的时候
判断一下当前信号量的数量, 如果足够则加信号量
如果不够, 检查一下最早(或前几)拿信号量的元素, 它是否已经超过1分钟了,
如果是, 那么就强制移除它
当然了需要使用事务来保证!

zset.remove(时间<当前时间 - 阈值) 无条件移除那些已经超时的信号量拥有者
zset.add(自己)
rank = zset.获得自己的排名
if rank < 100:
	自己在所有人的排名<100 成功获得锁
else:
	zset.remove(自己)

利用这个方式可以避免事务!

但它假设每个人的时间都是相同的
为了解决这个问题, 采用一个计数器

myCounter = 分布式计数器++
zset.remove(时间<当前时间 - 阈值) 无条件移除那些已经超时的信号量拥有者
zset.add(自己)
rank = zset.获得自己的排名
if rank < 100:
	自己在所有人的排名<100 成功获得锁
else:
	zset.remove(自己)



