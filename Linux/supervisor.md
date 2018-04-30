# 描述 #
是一个docker ui, 可以让你管理本地或远程的docker
https://github.com/portainer/portainer

# 安装 #
如果你要管理本地docker
docker run -d -p 9000:9000 --restart always -v /var/run/docker.sock:/var/run/docker.sock -v /opt/portainer:/data portainer/portainer

如果你要管理远程docker, 那么不需要制定上面的sock映射
docker run -d -p 9000:9000 --restart always -v /opt/portainer:/data portainer/portainer

上述是用docker本身来部署一个portainer, 当然也可以不用docker, 从官网下载压缩包, 解压, 执行...

之后访问9000端口, 初始化管理员账号密码

# 使用 #
Dashboard页面, 展示了当前机器的基本情况, 机器配置, 容器数量, 镜像数量
Containers页面, 展示了所有容器的情况, 你可以对容器 start stop kill restart pause resume remove add 等操作, 还可以查看容器日志, 直接ssh上去等
Images页面, 展示了所有的镜像情况, 可以对镜像进行管理, 还可以在线生成一个镜像(你需要提供dockerfile, 或一个tar包, 或一个url地址)
Networks页面, 展示了所有的网卡情况
Volumes页面, 展示了挂载卷的情况
Events页面, 展示了一些日志信息
Engine页面, 展示了docker的信息
Endpoints页面, 用于管理连接的docker信息, 你可以配置多个endpoints, 这样就可以在多个docker管理之间切换
Registeries页面用于管理仓库的信息

