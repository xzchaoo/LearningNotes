这里详细介绍了 docker run 的各个参数

docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]

你可以做什么设置:
1. 前后台运行
2. 容器名字
3. 网络设置
4. 限制CPU和内存
5. 提供环境变量, 覆盖 Dockerfile 里的一些配置
6. 数据卷配置

运行 docker run 需要 sudo 或 让你的用户加入 docker 组, adduser USER GROUP(将一个已存在的用户加入到已存在的组)

-d 后台运行, 不指定的话就是前台运行
-t 提供一个伪终端
-i 保持STDIN打开, 即使它已经被detach了
-a 设置绑定的3个流(stdin stdout stderr)

--name 容器命名, 容器还有长id和短id(是长id的一个短前缀而已)

指定镜像
Image[:tag]
Image[@digest]

--net=连接道德网络(默认是bridge)
也可以指定你自定义的网络
--ip="..."显示指定容器的IP
--add-host="host:IP的格式" 会在/etc/hosts里加一条记录

--net none 完全禁用网络
host模式 会共享主机的网络栈 和 所有的接口, 这些对于容器都是可用的, 这会有更好的网络性能
于此同时一些相关的配置就会失效, 比如--dns等
bridege还是要经过一层虚拟化

当 "--net container:名字或id" 的时候, 该容器就会共享另外一个容器的网络栈.
于此同时一些相关的配置就会失效, 比如--dns等
```
docker run -d --name redis example/redis --bind 127.0.0.1
docker run --rm -it --net container:redis example/redis-cli -h 127.0.0.1 共享网络栈
```

自定义网络
docker network create -d bridge my-net
docker run --net=my-net -itd --name=container3 busybox

此时主机上就会多出一块网卡, 注意看这个网卡的ip地址
然后你创建的 container3 由于使用了你自定义的网络, 因此他的IP也会跟你的那个新增的网卡的IP是同一个网段的

--restart 当你的进程不是以 status=0 退出的时候就会重启该容器
有一些策略:比如 always on-faile:10 表示最多尝试10次

docker本身的命令也会返回一些状态码, 大多时候用不到.

--rm 当容器退出的时候自动移除


还可以限制内存和CPU等, 具体看官网了.
https://docs.docker.com/engine/reference/run/

CMD ENTRYPOINT EXPOSE ENV VOLUME WORKDIR USER 可以被重写

