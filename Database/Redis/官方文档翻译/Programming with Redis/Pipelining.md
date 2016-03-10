# Request/Response protocols and RTT #
http://www.redis.io/topics/pipelining

1. 基本的原理是 : 不等待响应, 一直发送请求, 最后一口气接受响应.

假设你和服务器一趟来回要250ms, 那么你单线程的话每秒最多4次请求, 这显然不能让服务器充分发挥性能.

Client: INCR X
Client: INCR X
Client: INCR X
Client: INCR X
Server: 1
Server: 2
Server: 3
Server: 4

1. 但是这样服务器需要有一个队列, 来缓存响应, 假设你有100k的相应, 那么就需要100k大小的队列.
2. 有这样1个策略可以缓解一下:
	1. 利用批量处理, 100k 拆成 10组10k
	2. 每次处理10k, 处理10次
	3. 这样队列只需要10k的大小

Pipelining VS Scripting
有时候用脚本性能更好.
脚本的优势:
1. 可以在服务端快速执行 读 算 写, 管道的话做不到
2. Sometimes the application may also want to send EVAL or EVALSHA commands in a pipeline. This is entirely possible and Redis explicitly supports it with the SCRIPT LOAD command (it guarantees that EVALSHA can be called without the risk of failing).

