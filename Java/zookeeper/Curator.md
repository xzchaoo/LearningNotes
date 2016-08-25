# 简单说明 #


# Recipes #
它实现了一些很常用的 Recipe
选举
锁:共享可重入锁 共享锁 共享可重入读写锁 共享信号量 多共享锁
Barriers:用于堵住一堆进程直到某条件满足
计数器
缓存
节点
队列

## InterProcessMutex##
保证同一时刻只有一个客户端可以拿到锁 可重入
**它是可以重复使用的, 不要一直创建新的实例**

acquire 获得锁, 一直阻塞, 有超时版本
release 释放锁

## InterProcessSemaphoreMutex ##
 跟上面类似 不可重入

## InterProcessReadWriteLock ##
可重入的读写锁
写可以转成读
但读不能转写 永远不成功

## InterProcessSemaphoreV2 ##
信号量

## SharedCount ##
计数器
public SharedCount(CuratorFramework client, String path, int seedValue)
启动时候需要调用 start 方法 不用的时候调用close方法
set/getCount 设置/获得当前值
可以添加监听器

trySetCount(newCount)
尝试修改count为newCount
只有当自从上次读取以来都没变过才能成功

## DistributedAtomicLong ##
乐观锁版本
public DistributedAtomicLong(CuratorFramework client, String counterPath, RetryPolicy retryPolicy)

悲观锁版本
public DistributedAtomicLong(CuratorFramework client, String counterPath, RetryPolicy retryPolicy,PromotedToLock promotedToLock)
get()
increment()
decrement()
add()
subtract()

## 缓存 ##
对某个节点的缓存 或 对子节点的缓存, 一旦有变化就自动更新
还可以对一个子树进行缓存(代价比较大)


## 队列 ##


