软防火墙

iptables由table组成
每个table由filter组成

# filter表 #
用于信息包过滤
INPUT 对外部数据进入Linux进行过滤
OUTPUT 内部LInux系统要发出去的包进行过滤
FORWARD 将外面过来的数据包传递到内部计算机中

# 管理防火墙内部主机NAT表 #
用于网络地址转换
PREROUTING 通过防火墙改变了访问的目的地址 使得数据包可以重定向到指定的主机
POSTROUTING 在包就要离开防火墙之前改变其源地址 
OUTPUT 改变本地产生的包的目的地址 

改变不同包和包头内容的mangle表


第一条匹配的规则将会生效!

查看状态
service iptables start
systemctl status iptables

启动服务
systemctl start iptables


iptables -t tables -L -vn -FXZ
-t 后面接一个表名
-L 列出当前所有表的配置规则
-v 详细
-n 输出结果用IP表示
-F 清空某个指定表中所有链的规则设定
-X 删除使用者自定义的表
-Z 计数器归零

-P 定义策略

-A 追加一条规则
-I 插入一条规则
-i 指定网卡
-p 指定协议

iptables -A INPUT -i eth0 -s 192.168.0.3 -j DROP
iptables -A INPUT -i eth0 -s 192.168.0.0/24 -j ACCEPT
