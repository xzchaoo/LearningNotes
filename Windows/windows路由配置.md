# windows路由表 #
route print -4 打印ipv4的路由表

# 辅助工具 #
https://www.nirsoft.net/utils/network_route_view.html 最下面有中文版!
纯用命令行会疯的!

# 术语 #
Active Routes:活动路由:当前的路由规则
Network Destination:网络目标:是一个IP地址, 需要和子网掩码配合使用, 用于确定满足条件的IP地址
Netmask:子网掩码:
Gateway:网关地址:满足上述条件的IP的包将会发给该网关
Interface:接口地址: 默认可以不写, 一旦网关确定之后, 该值通常也是确定的
Metrics:度量值:跳数: 相同条件下, 条数越少优先级越高
Default Gateway:默认网关:当找不到路由规则直接满足该IP地址的话, 就使用默认网关来处理
永久, 通过-p参数指定, 机器重启之后也会继续存在!

如果一个IP同时满足多个条件, 则会选择子网掩码最长的那个进行匹配

# 例子 #
## 例子2 ##
某机器装了2个网卡, 一个连接公司内部网络, 一个连接外网, 但是发现只能ping通公司网络, 不能ping通外网.
很有可能是默认网关设置成了公司内网网卡的网关, 将其设置成外网网卡的网卡即可


# 参考 #
https://blog.csdn.net/tao546377318/article/details/52485627