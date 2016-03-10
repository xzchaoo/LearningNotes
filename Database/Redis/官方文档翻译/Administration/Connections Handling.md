1. 一个客户端的socket备份昂到一个 non-blocking state 因为redis使用的是  multiplexing and non-blocking I/O
2. 设置 TCP_NODELAY 选项为了确保连接上不会延迟
3. A readable file event is created so that Redis is able to collect the client queries as soon as new data is available to be read on the socket.
4. 如果超过 maxclients, 那么就会返回一个错误
5. 客户端服务的顺序: The order is determined by a combination of the client socket file descriptor number and order in which the kernel reports events, so the order is to be considered as unspecified.
6.

However Redis does the following two things when serving clients:
It only performs a single read() system call every time there is something new to read from the client socket, in order to ensure that if we have multiple clients connected, and a few are very demanding clients sending queries at an high rate, other clients are not penalized and will not experience a bad latency figure.
However once new data is read from a client, all the queries contained in the current buffers are processed sequentially. This improves locality and does not need iterating a second time to see if there are clients that need some processing time.

1. hard limit, 当buffer达到一定的大小时, redis会尽快关掉这个连接
2. soft limit, 如果每10秒32MB, 意思是 一个连接有超过32MB的output buffer超过10秒的话就关了它

对于不同类型的客户端会采取不同类型的限制
1. 普通客户端 没有任何限制, 因为普通客户端都是采用 blocking 方式的, 因此outputbuffer不会太大
2. pub/sub (32) (8,60)
3. slaves (256), (64,60)
4. 这可以通过redis.conf文件修改的


Query buffer hard limit
Every client is also subject to a query buffer limit. This is a non-configurable hard limit that will close the connection when the client query buffer (that is the buffer we use to accumulate commands from the client) reaches 1 GB, and is actually only an extreme limit to avoid a server crash in case of client or server software bugs.


# 客户端超时 #
默认情况下如果客户端空闲很久, redis也不会关掉连接, 因此连接会一直开着
通过配置 timeout 参数, 可以使得客户端闲置了多久就关了它的连接
但这不适用于pub/sub 客户端
但是超时并不会控制得非常精确, 误差个几秒还是可以

适用场景:
1. 客户端有bug, 经常忘记关, 那就设置一个超时来个UAN掉它

执行 client list 可以列出当前的素有客户端, 以及他们的一些情况, 比如idle了多久, 最近执行的命令等
