# 简介

*The Netty project* is an effort to provide an asynchronous event-driven network application framework and tooling for the rapid development of maintainable high-performance · high-scalability protocol servers and clients.





## 分段问题

因为发送数据的时候我们是以流的方式发的, 假设你一共发了100个byte的数据, 但是你接受的时候可能会依次收到 64 32 4 byte的数组, 组合起来是100个byte, 这样对我们的变成方式带来的很大的挑战和麻烦

你不得不主动缓存数据, 等数据>=100byte 再做解析



### netty的解决方案

利用 ChannelPipiline

ByteToMessageDecoder(BTMD) 实现了 ChannelInboundHandler 接口 用于处理分段问题

重写 decode方法, 每次一有数据到达, BTMD会积累它所收到的所有byte, 然后调用decode方法

如果你在decode方法里不消费byte, 那么byte就会继续积累, 如果你消费了4个byte, 那么这4个byte就没了, 剩下的byte会继续被积累



ReplyingDecoder 是 BTMD 的一个实现类, 可以很大程度上简化编程 但是有危险!



## 使用POJO而不是ByteBuf





# ChannelHandler

handlerAdded/handlerRemoved 当该handler被加入到某个channel时调用 会将ctx传进来



## IdleStateHandler

如果一段时间内没有任何读或写操作, 那么就会触发 IdleStateEvent 事件

一定程度上可以用来做心跳



```
SimpleChannelInboundHandler
```



# Netty优化

1. 如果Handler没有状态, 那么可以使用 @Shareable 修饰
2. 尽量使用write而不是writeAndFlush, 最好积累足够多的数据再flush
3. 有一些Handler仅仅是一次性的, 一旦不再需要的话可以移除它们, 增加性能
4. 使用 EpollSocketChannel 可以带来性能提升
5. 尽早关闭socket
6. 重写channelWritabilityChanged并且利用 channel.isWritable() 来判断当前是否合适进行写操作, 否则写太多而对方消耗太慢会导致内存溢出
7. 配置 WRITE_BUFFER_HIGH_WATER_MARK 和 WRITE_BUFFER_LOW_WATER_MARK
8. 尽量使用 pooled heap
9. 写大文件的时候使用: DefaultFileRegion DefaultFileRegion NioChunkedFile
10. 不要阻塞!
11. 尽量重用 EventLoopGroup
12. 尽量使用 ctx.write 系列方法 而不是 ctx.channel().write系列方法



# 常用handler

1. ChannelTrafficShapingHandler 用于限速
2. StringEncoder StringDecoder 字符串编码

# 网络编程考虑的问题 #
1. 半包读/写
2. 网络闪断


TCP粘包拆包


# 杂 #
SO_BACKLOG 的解释
SO_TIMEOUT
SO_SNDBUF
SO_RCVBUF
SO_REUSEADDR
TCP_NODELAY
CONNECT_TIMEOUT_MILLS

bootstrap的attr可以携带额外的参数 这些额外的参数最终将会被放到socketchannel的attr里

# ByteBuf #
duplicate 返回原有的buf的一个复制对象, 这个对象和原有buf共享底层的buf, 但自己维护自己的索引
copy 复制一个buf对象 索引和底层buf都是新的
slice 返回原有buf的一个子集, 内容共享, 独立维护索引

支持 顺序读写 和 随机读写

堆内存 分配和回收快 收到JVM管理 进行socket读写时 需要做一次额外的内存复制到内核channel中
非堆内存 分配和回收速度更慢 但是它可以直接用于socket channel

最佳实践:
1. 在IO通信线程的读写缓冲区使用DirectByteBuf
2. 后续业务消息的编解码使用HeapByteBuf

buf会分为两派 一类是 普通的 另一类是 unsafe的
因为底层实际上是一个字节数组, 当需要读取一个long的时候, 必须 byte[] -> long
如果是 普通的 那么需要使用工具类 将 8个 byte 转成一个long
如果是 unsafe的 那么直接用sun提供的 Unsafe 读取一个long

buf会分类两派 一类是 unpooled 另一类是 pooled
其实就是看说byte有没有池化 没有池化的 总是 new 一个 池化的会先从池里拿

在满足性能的情况下推荐使用 UnpooledHeapByteBuf 减少管理的麻烦

pooled: 先预先申请一块大内存, 然后在这块大内存里进行buf的分配
管理更加麻烦 实现也是 当然我们只需要用

知道 bytebuf 内部维护里两个索引就行了

通过"自旋"的方式对int进行加减
```
while(true){
	if(xxx.compareAndSet(oldValue,newValue)){
		break;
	}
}
```


# ChannelHandler #
被Skip注解的方法不会被执行 会被直接跳过
Shareable的表示这个类可以共享 这个类必须是无状态的

ByteToMessageDecoder
MessageToByteEncoder
MessageToMessageDecoder 称为2次解码器 比如 先用一个解码器解码出http请求 再用一个解码器将该http请求解码成pojo对象
这样也可以很大程度的复用已有的解码器 采用组合的方式就行!

## LengthFieldBasedFrameDecoder ##
神器
几个概念:
长度指的是实际内容的长度, 不包含长度本身的字节数, 比如"abc" 那么长度是3, 单由于长度本身页要占一定的字节数, 比如2, 那么最终发送的应该是5个字节
0x0 0x3 a b c
注意前2个字节表示实际内容的长度 3, 不是总长度5

lengthFieldOffset 长度字段的偏移位置
lengthFieldLength 长度字段的字节数
lengthAdjustment 将会导致 开头前 initialBytesToStrip 个字节后的 length + lengthAdjustment 的字节成为实际内容
initialBytesToStrip 表示解码后的内容需要跳过整个帧开头的几个字节

几个组合
(0,2,0,0) 帧开头2字节表示实际内容的长度 解码后的结果包含了2个字节的长度
(0,2,0,2) 帧开头2字节表示实际内容的长度 解码后的结果不包含2个字节的长度 只包含实际内容
(0,2,2,0) 帧开头2字节表示整个帧的长度 解码后的结果包含了2个字节的长度

假设我们的消息这样设计
4个字节的幻数
4个字节的长度
2个字节的版本号
变长的实际内容

那么应该采用如下的配置
(4,4,0,10) 帧开头4字节后的4字节表示实际内容的长度 解码后的结果只包含实际内容

## LengthFieldPrepender ##
和 LengthFieldBasedFrameDecoder 功能相反 当你write数据的时候会自动给你加上length字段
但是灵活性不高 复杂的情况下最好模仿它的思想自己定义一个类, 否则你没办法实现上述的自定义头


