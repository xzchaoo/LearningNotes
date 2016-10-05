# 服务端例子 #
docker run --name mysql \
-e MYSQL_ROOT_PASSWORD=70862045 \
-v /root/mysql_init:/docker-entrypoint-initdb.d \
-v /root/my.cnf:/etc/mysql/conf.d/my.cnf \
-v /root/mysql_data:/var/lib/mysql \
-p 3306:3306 -d mysql:5.7

# 客户端例子 #
docker run -it --rm --link mysql:mysql mysql mysql -hmysql -uroot -p

# 防乱码配置 #
```
[client]
default-character-set=utf8mb4
[mysql]
default-character-set=utf8mb4
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_general_ci
```

https://hub.docker.com/_/mysql/

docker run --name mysql -e MYSQL_ROOT_PASSWORD=70862045 -d mysql:5.7


需要指定 mysql 密码
数据卷

其他容器可以用 --link 链接到该mysql
--link some-mysql:mysql


mysql客户端
docker run -it --link some-mysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'

docker run -it --rm mysql mysql -hsome.mysql.host -usome-mysql-user -p

连接到该容器执行bash
docker exec -it mysql bash

看日志
docker logs some-mysql

# 定制配置 #
/etc/mysql/my.cnf 是默认的配置文件
他会将
/etc/mysql/conf.d 目录下的所有 *.cnf 文件的配置也纳入到配置文件的范围, 并且它们的优先级更高, 所以你可以通过挂数据卷的方式提供配置

$ docker run --name some-mysql -v /my/custom:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag

将本地的 /my/custom 挂到 /etc/mysql/conf.d

不嫌麻烦的话 也可以在命令行上提供参数
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci


# 初始化 #
/docker-entrypoint-initdb.d 这个目录下的 ``*.sh *.sql *.sql.gz`` 都会被执行(按照名字字典序)

# 设置数据的存放位置 #
-v /my/own/datadir:/var/lib/mysql
