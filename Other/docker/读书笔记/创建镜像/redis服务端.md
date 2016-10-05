它的 Dockerfile

https://github.com/docker-library/redis/blob/bc3ca850f4a2a2e9e5d4b3b8272e5536b4d21d24/3.0/alpine/Dockerfile

docker run -d -P -v /data:/data --name redis redis:3.0.7-alpine

客户端
docker run -it --rm --link redis:redis redis:3.0.7-alpine redis-cli -h redis -p 6379

配置 redis.conf

```
方法1
FROM redis
COPY redis.conf /usr/local/etc/redis/redis.conf
CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
```

方法2
```
docker run -v /myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf --name myredis redis redis-server /usr/local/etc/redis/redis.conf
```

当你创建网络的时候, 你的宿主机器上就会出现一个新的网卡
iptables -t nat -L -n

介绍 --link 语法和作用

--link只不过是在路由表里添加了一个项目而已, 你完全可以通过ping的方式找出你所在的网段的活动的机器
可以设置使得 "只有显示指定 --link 参数, 否则你完全无法访问对应的机器"

link之后
观察 /etc/hosts 文件
会有形如 "172.17.0.4 db" 的配置
这样就将 db 这个名字解析到了对应的IP
但是我们编程或配置的时候是按照db这个名字来配置的, 因此他对应的IP是可以动态指定的

docker run 的时候可以使用  -h 或 --hostname 为容器指定一个主机名, 否则容器的主机名默认是容器的 短id

link之后
观察 env 命令的输出
你会发现有很多 DB_ 开头的环境变量
```
root@811bd6d588cb:/# env
HOSTNAME=811bd6d588cb
DB_NAME=/webapp/db
DB_PORT_6379_TCP_PORT=6379
DB_PORT=tcp://172.17.0.31:6379
DB_PORT_6379_TCP=tcp://172.17.0.31:6379
DB_ENV_REFRESHED_AT=2014-06-01
DB_PORT_6379_TCP_ADDR=172.17.0.31
DB_PORT_6379_TCP_PROTO=tcp
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
REFRESHED_AT=2014-06-01
```
db的Dockerfile里的配置, 比如 ENV EXPOSE 都会体现在上面的输出里

通常 DB_PORT=tcp://172.17.0.31:6379 是最有用的

默认容器的DNS服务器应该是跟主机相关的
你可以在 docker run 的时候使用 --dns 和 --dns-search 对容器的DNS服务器进行配置

持续集成
http://jenkins-ci.org/


--volumes-from

docker port 容器 端口

对数据卷做备份

docker run --rm -v $(pwd):/backup --volumes-from 数据卷容器 ubuntu tar -cvf /backup/backup.tar.gz /你要备份的目录

主机: 将 容器=mysql 上的 /data 数据卷备份到当前目录的mysql.jar.gz里
docker run --rm -v $(pwd):/backup --volumes-from mysql ubuntu tar -cvf /bakup/mysql.tar.gz /data
--rm 容器退出时自动移除
-v 数据卷映射
--volumes-from 容器数据卷映射

kill
