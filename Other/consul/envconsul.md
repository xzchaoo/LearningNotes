# 介绍 #
https://github.com/hashicorp/envconsul
这是官方的一个衍生项目
envconsul从consul获得KV作为环境变量, 然后通过子进程的形式启动一个程序, 这样这个程序就可以继承相关的环境变量, 通过这种方式来达到设置程序的配置
当配置变化的时候, 可以 给 程序发送一个信号, 或 重启程序
因此不是任何配置都适合这样做的!

# 用法 #
envconsul -配置项 程序命令 args
如果程序结束运行 那么envconsul也会跟着结束

# 支持的配置项 #
*表示必填

auth basic认证, username:password
consul* IP:port
max-stale 默认值是0(none) 貌似是一个查询的最大超时时间? 如果超过这个时间那么Consul就会将请求发给所有的servers而不是只有leader
token 没有默认值
kill-signal 杀死子进程时用的信号 默认是 SIGTERM, 支持 SIGHUP,SIGTERM,SIGINT,SIGQUIT,SIGUSR1,SIGUSR2
wait:min(:max) 比如 1s:4s max是可选的, 默认是min的4倍, 表示收到改变的消息之后不要马上重启程序
而是等待系统达到稳定或max达到才会重启程序, 没太懂原文
```
If you have a large number of services that are in flux, you may want to specify a quiescence timer. This will prevent commands from running until a stable state is reached (or a maximum timeout you specify). You can specify the quiescence interval using the -wait flag on the command line:

envconsul -wait "10s:50s"
This tells envconsul to wait for a period of 10 seconds while we do not have data before running/restarting the command, but to wait no more than 50 seconds.
```

prefix 指定一个前缀, 比如 apps/user-service/ 这样该程序就会监听这个前缀下的kv的变化
这个配置可以指定多次, 后者覆盖前者, 比如先获得全局配置, 再取得特定app的配置
如果你的prefix以/开头 那么会导致整个路径被保留作为环境变量, 比如:
/redis 会出现  redis/a=1 的环境变量
redis 会出现 a=1 的环境变量


secret
splay The maximum time to wait before restarting the program, from which a random value is chosen.
config 指定一个配置文件 或 配置目录, 可以将配置项写在里面
log-level 日志等级

pristine 只是用从Consul获得的KV, 不使用默认继承下来的环境变量
upcase 将变量进行大写
sanitize 将不合法的变量变成下划线

once 只能用在命令行 表示只会监听一次

# 例子 #
./envconsul -consul 192.168.56.102:8500 -prefix=test -pristine env

