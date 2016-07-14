# 为一个容器命名 #
docker run ...其他参数 --name 容器的名字(比如 tomcat-1) ...其他参数
docker ps -a 就可以看到容器的名字, 如果不指定的话, 会有默认的名字

docker stop/rm 等操作就可以接受一个容器的名字, 否则你需要使用 那个蛮长的id

# 在默认的网络上启动容器 #
bridge overlay 两种驱动

docker network ls 列出所有安装的驱动

docker network inspect bridge

断开容器的网络
docker network disconnect bridge 容器

# 创建自己的 桥接网络 #
桥接网络被限制在单台的 Docker Engine撒花姑 娘
一个 overlay 网络可以包含多个主机, 这是一个高级话题.

docker network create -d bridge my-bridge-network
-d DRIVER 指定驱动类型

再次执行 docker network ls:
615d565d498c        my-bridge-network   bridge

查看配置
docker network inspect my-bridge-network
如果嫌它内容太多的话可以这样:
docker inspect --format='{{json .NetworkSettings.Networks}}'  db
或使用 grep 命令

将容器加入到该网络中
docker run -d --net=my-bridge-network --name db training/postgres
如果不指定 --net的话, 默认是加入 default 网络组

执行 docker inspect 你就会看到你的容器的网络配置, 确实是加入到了该网络中

再启动一个容器
$ docker run -d --name web training/webapp python app.py
他们不在同一个网络中

将web加入网络中
docker network connect my-bridge-network web
ping通了, 加入两个网络意味着 web(容器的名字) 会有2个网卡 不信你去看看 然后他们就可以通信了


一台机器加入了多个网络的话就会有多个网卡
每个网卡上的地址都是不冲突的
比如第一个网卡的ip地址是 172.17.0.2
第二个网卡的ip地址是 172.18.0.3
