# 注意 #
1. Channel 不是线程安全的

# 发送消息 #

如果一个消息是 mandatory 的, 但却找不到接受者
那么该消息会被退回来
见 setReturnListener


# 取回消息 #
Distinct Consumers on the same Channel must have distinct consumer tags.

订阅 basicConsume
取消 channel.setReturnListener ReturnListener


## Consumer 接口 ##
避免在 consumer 里做重活 会导致 派发线程阻塞
handleConsumeOk 当该 consumer 注册成功的时候的回调
handleCancelOk 当该 consumer 取消成功的时候的回调
handleCancel
handleShutdownSignal
handleRecoverOk

handleDelivery 这是我们需要实现的主要方法

## DefaultConsumer ##
最常使用的一个实现, 构造函数接收一个 Channel
大部分方法提供了空实现

## QueueingConsumer ##
继承了 DefaultConsumer
会将收到的消息放到一个 BlockingQueue 里

# Shutdown Protocol #
一个连接和cahnnel有如下几个状态:
1. open 处于可用状态
2. clsing 该对象已经被告知要关闭 现在还在关闭中
3. closed 已经完全关闭了

最终的状态都是closed, 不管关闭的原因(程序主动请求关闭, 网络失败 等)

shutdown 相关的方法:
1. addShutdownListener/removeShutdownListener(ShutdownListener )
2. getCloseReason
3. isOpen
4. close(int closeCode, String closeMessage)

