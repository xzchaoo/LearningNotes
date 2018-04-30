# 方案1 #
使用这个 Docker 镜像快速搭建 IPsec VPN 服务器。支持 IPsec/L2TP 和 Cisco IPsec 协议。

按照这里的教程  
https://github.com/hwdsl2/docker-ipsec-vpn-server/blob/master/README-zh.md

设置完客户端之后, 记得将 "在远程网络上使用默认网关" 去掉, 否则所有的流量都会走该vpn

一旦取消之后, 有一些流量反而就不会走该vpn了, 但我们想让这些流量走该vpn, 这时候需要手动调整路由表, 见另一个教程 "windows路由配置.md"

# 方案2 #
https://github.com/StreisandEffect/streisand/blob/master/README-chs.md


# 方案3 #
