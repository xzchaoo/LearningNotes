# 简介 #
将redis的功能进行封装 提供了很多便利的类
比如 信号量 读写锁 List Map 阻塞队列 等

# Config #
配置可以编程配置 或 来自 JSON YAML 文件

codec 默认是 org.redisson.codec.JsonJacksonCodec
threads RTopic RRemoteService RExecutorService 等的共享线程数数量
nettyThreads Redisson用到的redis客户端的所有线程大小
executor 你可以指定一个Executor 用于 RTopic RRemoteService RExecutorService 这样 上面的threads 应该就不适用了

根据是 集群 还是 单节点 还是主从 有不同的配置 具体再看了
目前我们肯定是采用单节点模式的

# 操作执行 #
retryAttempts 默认是3 用于控制重试次数
retryInterval 默认是1000毫秒 用于控制重试间隔

Redisson 和 Redisson Objects 都是线程安全的

几乎每个方法都有同步和异步的版本
