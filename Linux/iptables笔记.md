推荐看这个 http://www.zsythink.net/archives/1493

http://blog.chinaunix.net/uid-26495963-id-3279216.html

# 前提 #
## ICMP ##


有的系统使用 iptables 来管理防火墙
很多系统默认安装了 iptables, 如果没有则 apt-get install iptables

iptables -L 查看防火墙配置

作者一共在内核空间中选择了5个位置，
1. 内核空间中：从一个网络接口进来，到另一个网络接口去的
2. 数据包从内核流入用户空间的
3. 数据包从用户空间流出的
4. 进入/离开本机的外网接口
5. 进入/离开本机的内网接口


这五个位置也被称为五个钩子函数（hook functions）,也叫五个规则链。
1. PREROUTING (路由前)
2. INPUT (数据包流入口)
3. FORWARD (转发管卡)
4. OUTPUT(数据包出口)
5. POSTROUTING（路由后）
这是NetFilter规定的五个规则链，任何一个数据包，只要经过本机，必将经过这五个链中的其中一个链。   


对于filter来讲一般只能做在3个链上：INPUT ，FORWARD ，OUTPUT
对于nat来讲一般也只能做在3个链上：PREROUTING ，OUTPUT ，POSTROUTING

iptables -t filter -A INPUT -s a.b.c.d/e -p tcp --dport 53 -j DROP

DROP
ACTION

# 链管理命令 #
定义默认策略 是通还是堵
iptables -P INPUT (DROP|ACCEPT)

清除nat表的PREROUTING链
iptables -t nat -F PREROUTING

# 规则管理命令 #
-A 追加一条规则
-Inum 插入一条规则
-R num 替换一条规则
-D num 删除一条规则

# 匹配规则 #
-s 源地址
IP 或 IP/MASK 或 0.0.0.0/0.0.0.0 表示全部
IP地址还可以取反, 在最前面加一个!

-d 匹配目标地址, 规则和-s一样
-p 协议 tcp udp icmp
-i 网卡名称 指定流入网卡
-o 网卡名称 指定流出网卡

## 隐式扩展 ##
对于 tcp或udp 可以设置
--dport 目标端口
--dport 21 或 --dport 21-25 表示25端口或21~25端口

--sport 源端口 和 目标端口类似

## 显示扩展 ##
-m multiport
之后我们就可以启用比如 --dports 21,23,80

# ACTION #
DROP: 悄悄丢弃, 一般我们多用DROP来隐藏我们的身份，以及隐藏我们的链表
REJECT: 明确表示拒绝
ACCEPT: 接受

DNAT
SNAT
MASQUERADE：源地址伪装
REDIRECT：重定向：主要用于实现端口重定向
MARK：打防火墙标记的
RETURN：返回
在自定义链执行完毕后使用返回，来返回原规则链。

iptables -t filter -A INPUT -s 172.16.0.0/16 -d 172.16.100.1 -p tcp --dport=22 -j ACCEPT
将默认策略改成DROP:
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

iptables

