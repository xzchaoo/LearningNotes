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



# 服务发现 #
需要引入一个新的依赖, 以前竟然一直没发现...
InstanceFilter 接口, 用于判断该实例是否可用
DownInstancePolicy 封装了实例失效后的处理信息, 比如一个实例失效5秒之后自动回复到可用列表里

## DownInstanceManager ##
管理了失效的实例, 这个失效是在客户端管理的, 当客户端觉得某个实例可能失效了就调用某方法标记为失效.

add:
将该服务实例添加到失败的实例中, 并且增加失败次数计数器
当添加一个实例到该manager的时候就会进行purge操作

purge
判断时间 如果近期才purge过 那么就跳过purge操作
使用CAS来防止并发
for每个失败的实例, 如果已经失效超过一定时间, 那么就移除它(也就是它不再失效了)

## InstanceProvider ##
非常抽象的一个接口, 用于提供一个服务的所有实例
```
public List<ServiceInstance<T>>      getInstances() throws Exception;
```

## FilteredInstanceProvider ##
对 InstanceProvider 的包装, 过滤掉了所有不满足过滤器的实例

## InstanceSerializer ##
用于实例的序列化和反序列化
现有的实现是 JsonInstanceSerializer

## ProviderStrategy ##
用于从 InstanceProvider 根据负载均衡策略选出一个实例
随机 粘性 轮流


## ServiceCache ##
服务缓存
实现了 InstanceProvider 接口
每次对 InstanceProvider 的调用都是直接访问zk的, 浪费性能
这个就可以用于缓存
可以添加监听器 监听可用实例的变化

### ServiceCacheBuilder ###
用于构建 ServiceCache
需要指定服务的名字
可以指定线程池, 用于内部的 PathChildrenCache 使用

## ServiceProvider ##
外观模式, 封装了大部分功能, 因此功能和上面提到的类或接口重复
它的实现类内部持有 ServiceCache InstanceProvider DownInstanceManager ProviderStrategy ServiceDiscoveryImpl

内部的 instanceProvider 是一个 FilteredInstanceProvider, 包装了ServiceCache, 使用用户提供的过滤器和两个内置的过滤器对实例进行过滤

getAllInstances 返回返回 instanceProvider 的 getAllInstances
getInstance 方法使用 ProviderStrategy 对 instanceProvider 选择一个实例返回


start 启动缓存
close 关闭缓存

## ServiceDiscovery ##
一个重要的接口

regsiterService 注册服务
updateService 更新服务
unregisterService 注销服务

下面的方法直接访问zk
queryForNames zk - 查询所有服务
queryForInstances zk - 查询某个服务的所有实例
queryForInstance zk - 根据服务名字和id查询一个实例
因为服务发现的zk结构是这样的:
/root/service1/id1
/root/service1/id2
/root/service2/id1
/root/service2/id3

serviceCacheBuilder 用于构建 serviceCache
serviceProviderBuilder 用于构建 serviceProvider, 因为serviceProvider 包含比 serviceCache 更丰富的功能, 所以大部分情况直接使用 serviceProvider


# 服务发现使用方法 #

## 提供者 ##
一个提供者可以注册多个服务

1. 构建 CuratorFramework
2. 构建 ServiceDiscovery
3. 构建 ServiceInstance 1, 描述了服务1的信息
4. 构建 ServiceInstance 2, 描述了服务2的信息
5. 调用 ServiceDiscovery.registerService 方法注册两个服务
