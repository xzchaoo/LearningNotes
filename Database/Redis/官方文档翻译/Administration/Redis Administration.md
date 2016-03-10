http://www.redis.io/topics/admin

1. 建议linux系统
2. Make sure to set the Linux kernel overcommit memory setting to 1. Add vm.overcommit_memory = 1 to /etc/sysctl.conf and then reboot or run the command sysctl vm.overcommit_memory=1 for this to take effect immediately.
3. Make sure to disable Linux kernel feature transparent huge pages, it will affect greatly both memory usage and latency in a negative way. This is accomplished with the following command: echo never > /sys/kernel/mm/transparent_hugepage/enabled.
4. Make sure to setup some swap in your system (we suggest as much as swap as memory). If Linux does not have swap and your Redis instance accidentally consumes too much memory, either Redis will crash for out of memory or the Linux kernel OOM killer will kill the Redis process.


Redis被设计为长期运行
1. 一些参数支持config set key value的方式动态配置, 省得重启
2. 当需要对某台服务器下机处理的时候, 为了不停机可以这样:
	1. 开另外一个Redis, 作为该服务器的slave
	2. 等待同步完成, 并且保证数据一直
	3. 允许去slave读取(slave-read-only no), 让你的所有客户端都去slave读取
	4. 配置slave slaveof no one
	5. 处理原先的服务器
	6. 用相同的方式转回来