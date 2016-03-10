redis-benchmark可以模拟 n 个客户端同时发送一共 m 请求.
redis-benchmark
-h host
-p port
-a password
-c clients 客户端数量 默认50
-n requets 总的请求数量 默认100000
-d size 用于get/set操作的数据大小 默认2字节
-dbnum db 选择哪个db
-k boolean 是否keep alive
-l 循环一直测
-q 安静
--csv 输出csv文件
-P num, 将多少个请求合成一个pipeline, 默认是1, 即不启动pipeline
-r keyspacelen
-t names 只对names指定的命令进行测试 set,get
-s

http://fallabs.com/kyotocabinet/

Redis is a server: all commands involve network or IPC round trips. It is meaningless to compare it to embedded data stores such as SQLite, Berkeley DB, Tokyo/Kyoto Cabinet, etc ... because the cost of most operations is primarily in network/protocol management.
Redis 是单线程的!


Depending on the platform, unix domain sockets can achieve around 50% more throughput than the TCP/IP loopback (on Linux for instance).