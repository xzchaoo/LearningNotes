镜像是容器的基础, docker run 的时候需要指定一个镜像

docker images
查看本机上的镜像
你可以得到几个比较重要的信息: 仓库的名字, TAG, 镜像的id(这里是短id, 真正的id很长)

仓库的名字通常是: 拥有者的名字/仓库的名字
TAG: 通常用作版本号

很多情况下如果不指定TAG, 那么默认是使用latest, 即最新版


使用 docker pull IMAGE_NAME 可以将一个镜像下载下来
IMAGE_NAME = repo[:TAG]
比如 docker pull training/sinatra

可以到 https://hub.docker.com/ 寻找一些现成的镜像
也可以使用 docker search KEYWORD 进行搜索

# 创建你自己的镜像 #
1. 首先你要启动一个容器
2. 然后对这个容器执行各种操作(也就是对容器进行各种配置)
3. 然后退出容器(exit)
4. 你的容器会有一个版本号, 可以通过 docker ps -a 来查询到, 其实很多地方都会有提示容器的版本号的, 这只是方法之一
5. docker commit -m "写一个消息, 跟git的提交类似" -a "作者的名字, 如果你已经 docker login 过了 就不用了" 容器的id 仓库:TAG
6. 5.的意思就是将本地的 "容器的id" 对应的容器镜像 做成一个镜像, 并且保存在本地
7. 接着你可以使用形如 docker push ouruser/sinatra 的命令将这个镜像push到hub上


# 使用 Dockerfile #
1. 这是另外一种创建镜像的方式
2. 写一个配置文件, 在这个配置文件里将你要做的事情(在容器里执行的各种指令)都做了
3. 对 Linux 的指令要有比较好的理解
4. 井号用于注释 

FROM ubuntu:16.04 #上游版本
MAINTAINER xzchaoo 70862045@qq.com #这个镜像的维护人的信息

# 给镜像设置tag #
tag的概念和git, svn上的差不多
git tag 5db5f... xzchaoo/xxxApp:1.0
然后你再去查看: docker images 就会发现多了一个镜像了, tag只是别名而已, 你注意看 image_id 就会发现有些镜像的 id 是一模一样的, 他们的内容实际上是一样的, 只是名字不一样而已.

# 移除镜像 #
docker rmi image_id|镜像的名字(形如 xzchaoo/webapp:1.0 的名字)

