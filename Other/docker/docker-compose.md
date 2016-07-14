# docker-compose #
-p 指定项目的名字, 默认是路径名

支持的命令
build
create
down
config 检查配置是否合法

变量替换
${VARIABLE}

https://docs.docker.com/compose/compose-file
文件元素
links:
command
ports
environment 指定环境变量
```
environment:
  RACK_ENV: development
  SHOW: 'true'
  SESSION_SECRET:

environment:
  - RACK_ENV=development
  - SHOW=true
  - SESSION_SECRET
```
image 指定镜像
build 指定构建的目录
extends

labels 打标签, 加元数据, 而已.
```
labels:
  com.example.description: "Accounting webapp"
  com.example.department: "Finance"
  com.example.label-with-empty-value: ""

labels:
  - "com.example.description=Accounting webapp"
  - "com.example.department=Finance"
  - "com.example.label-with-empty-value"
```

links
和--link效果一样, 按官方的说法, 它和depends_on作用应该是一样的
```
web:
  links:
   - db
   - db:database
   - redis
```

net
```
net: "bridge"
net: "host"
net: "none"
net: "container:[service name or container name/id]"
```

aliases
和--net-alias效果一样
在该容器所在的网络暴露名字:
```
jboss:
  some-network:
    aliases:
      - alias1
      - alias3
  other-network:
    aliases:
      - alias2
```







extra_hosts
和 --add-host 类似
```
extra_hosts:
 - "somehost:162.242.195.82"
 - "otherhost:50.31.209.229"
 - 
然后在你的/etc/hosts里就会有:
162.242.195.82  somehost
50.31.209.229   otherhost
```

```
expose:
 - "3000"
 - "8000"
```

ports 也用于暴露端口
```
ports:
 - "3000"
 - "3000-3005"
 - "8000:8000"
 - "9090-9091:8080-8081"
 - "49100:22"
 - "127.0.0.1:8001:8001"
 - "127.0.0.1:5000-5010:5000-5010"
```



build和image不能一起用

container_name: 容器的名字

depends_on
可以用于控制启动顺序, 当执行 docker-compose up SERVICE 的时候, 它依赖的所有service都会启动
并且依赖似乎会被自动 link ?
但是它并不会等到 依赖已经 "ready" 才启动.

entrypoint 覆盖ep







```
version: '2'
services:
  mysql:
    image: xzchaoo/bs2-mysql:1.0
  jboss:
    image: xzchaoo/bs2-jboss:1.3
    depends_on:
      - mysql
  tomcat:
    image: xzchaoo/bs2-tomcat:1.1
    depends_on:
      - jboss
   mysql初始化?
  
```

```
version: '2'
services:
  web:
    build: .
    command: php -S 0.0.0.0:8000 -t /code/wordpress/
    ports:
      - "8000:8000"
    depends_on:
      - db
    volumes:
      - .:/code
  db:
    image: orchardup/mysql
    environment:
      MYSQL_DATABASE: wordpress
```




当执行了 docker-compose up 的时候
会创建一个 myapp_default 的网络(其中 myapp 是你的项目的名字, 默认是当前目录的名字)
然后每个服务都有在这个网络里暴露自己的名字(也就是服务的名字)
没必要使用 links, 因为默认每个服务都是互相可达的.

容器启动的时候, 会根据 depends_on, links, volumes_from and network_mode: "service:...". 这些参数来确定启动顺序.

However, Compose will not wait until a container is “ready” (whatever that means for your particular application) - only until it’s running. There’s a good reason for this.

比如mysql容器是启动了没错, 但还没有准备好, 可能mysql的脚本要执行30秒才完全可用.
这时候你的jboss就连不上去mysql了.

