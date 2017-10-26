程序名(service id), 虚拟主机名 都是由 spring.application.name 决定的
不安全的 por t是由 server.port 决定的

See EurekaInstanceConfigBean and EurekaClientConfigBean for more details of the configurable options.

默认情况下会将自己作为 instance 和 client 连接到 server.

暴露两个端点:
1. 状态 /info
2. 健康 /health

ssl相关配置

利用 eureka.instance.metadataMap 可以设置实例的元数据


实例id是由
${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id:${server.port}}}.  确定的

# ab的用法 #
-n 总的请求数
-c 并发数

ab -n 1000 -c 10 http://localhost:9001/user/1


# zuul #
用于将请求直接路由到某个服务
省得你直接写一个mvc, 再在controller里调用服务
可以做反向代理

# 颜色 #
颜色|描述
:-:|:-:
绿|正常
蓝|被短路
橙|执行超时
紫|线程池拒绝
红|错误/异常

# 配置 #
使用Archaius1来实现配置管理
https://github.com/Netflix/Hystrix/wiki/Configuration

## 配置的来源 ##

### 1. 全局默认配置 ###

### 2. 动态全局默认配置 ###

### 3. 实例默认配置 ###
在创建实例的时候, 构造函数里提供的参数

### 4. 动态实例配置 ###
通过如下的属性, 可以动态调整对应的Command的配置
hystrix.command.${HystrixCommandKey}.execution.isolation.thread.timeoutInMilliseconds

## Command配置 ##
execution.isolation.strategy 表示隔离策略 THREAD 或 SEMAPHORE
默认情况下 HystrixCommand 的子类使用线程池 HystrixObservableCommand 的子类使用信号量

没有太好的理由的话都用线程池, 如果这个操作是要访问一些内存数据结构的话, 那么可以考虑用信号量, 因此此时线程切换带来的负面影响比较大


在一个窗口里, 要有 circuitBreaker.requestVolumeThreshold 个error就会触发熔断

当错误率超过这个阀值的时候 circuitBreaker.errorThresholdPercentage 就会开始熔断


circuitBreaker.sleepWindowInMilliseconds 指定了一个时间量
当开始熔断的时候, 过了这个时间量之后, 会尝试放过一个请求去尝试一下, 如果请求依旧失败, 那么继续熔断, 如果请求成功, 那么解除熔断

circuitBreaker.forceOpen 当它为true的时候就会强制开始熔断
circuitBreaker.forceClosed 类似上面


## Metrics ##
metrics.rollingStats.timeInMilliseconds 窗口的大小, 很多统计都是在这个时间窗口内进行计算的, 默认是10秒
metrics.rollingStats.numBuckets, 表示将窗口分成多少格子, 默认是10, 要求必须能整除时间窗口
在默认配置下, 10秒分成10格, 1格表示1秒
每过去1秒, 第一个秒的数据就丢了, 然后新一秒的数据就加进去, 如此反复...

metrics.rollingPercentile.enabled
表示是否统计某些指标的百分数

metrics.rollingPercentile.timeInMilliseconds 统计百分位数的话也需要一个时间窗口, 默认是60秒
metrics.rollingPercentile.numBuckets bucket数, 概念和上面提到的类似
metrics.rollingPercentile.bucketSize 每个bucket里可以放多少个值, 这个值只在构造函数时有用

默认情况下会保存最近60秒的执行耗时等数据, 将时间平均分成bucket段, 每段最多保存bucketSize个数据, 如果超过这个数据量, 那么只会取最后的bucketSize个

metrics.healthSnapshot.intervalInMilliseconds 每间隔多少时间, 进行一次健康快照, 用于计算成功率和失败率, 并且调整熔断器的状态

## Requset Context ##
requestCache.enabled 是否启动缓存
requestLog.enabled 是否启动日志

## Collapser ##
请求折叠
maxRequestsInBatch 最多多少个请求可以合为一个批次

timerDelayInMilliseconds 在一个batch创建之后多少时间, 这个batch必须被执行
requestCache.enabled 是否启动缓存, 这个配置和上面提到的不是同一个, 它们的前缀不一样, 这个是hystrix.collapser....

## ThreadPool ##
默认每个线程池会有10个大小, 如果不想用默认值, 可以用下面的估算方法
线程池数量 = 每秒峰值请求数 x 99分位耗时(单位秒) + some breathing room
线程池可以带Queue, 来防止突发情况

> Netflix API has 30+ of its threadpools set at 10, two at 20, and one at 25.


coreSize = maximumSize = 10
maxQueueSize = -1 这个参数必须重新构造线程池, 因此几乎认为是不可变的

queueSizeRejectionThreshold 当queue大小超过这个值的时候就会开始拒绝执行
这个值存在的意义是因为maxQueueSize无法动态调整, 而这个值可以, 当然这个值无法非常精确地保证Queue大小一定就不超过queueSizeRejectionThreshold, 但够用了

keepAliveTimeMinutes 当core<max时有效, 控制线程idle多少时间就释放

allowMaximumSizeToDivergeFromCoreSize 默认情况下core=max 当设置为true的时候就允许两个值不一样

metrics.rollingStats.timeInMilliseconds 线程池的metrics会保存多久
metrics.rollingStats.numBuckets

