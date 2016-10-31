# 简介 #
ubuntu下的用于简化 iptables 操作的一个工具

# 参数解释 #
--dry-run 干跑
insert NUM 规则是有顺序的, 你可以往指定创建的规则的位置, 如果不指定就是放最后

# 启动 #
ufw enable
ufw disable
ufw reload

# 添加规则 #
ufw [--dry-run] [insert NUM] allow|deny|reject|limit [in|out] [log|log-all] PORT[/PROTOCOL]

ufw allow in 81/tcp
允许: 任何以81/TCP为目标端口的数据

ufw allow in from 192.168.1.0/24 to 0.0.0.0
允许: 从 192.168.1.0/24 到 0.0.0.0 的数据包

ufw allow proto tcp from any to any port 80,443,8080:8090
允许: 任意地址到 80,443,8080~8089 TCP协议的数据包

ufw deny proto tcp to any port 80
拒绝: 拒绝指向任意地址的80端口的TCP协议数据进入本机

ufw deny proto tcp from 10.0.0.0/8 to 192.168.0.1 port 25
拒绝: 从 10.0.0.0/8的任意端口 到 192.168.0.1的25端口的TCP协议的数据


# 删除规则 #
ufw [--dry-run] [rule] [delete] [insert NUM] allow|deny|reject|limit [in|out [on INTERFACE]] [log|log-all]  [proto  PROTOCOL]  [from  ADDRESS  [port  PORT]]  [to  ADDRESS [port PORT]]

ufw  [--dry-run]  route [delete] [insert NUM] allow|deny|reject|limit [in|out on  INTERFACE] [log|log-all] [proto PROTOCOL] [from ADDRESS [port PORT]] [to ADDRESS [port PORT]]

ufw [--dry-run] app list|info|default|update

删除第NUM条规则 ufw [--dry-run] delete NUM

# 查看规则 #
ufw status
ufw status verbose 查看更多信息
ufw status numbered
残念 verbose 和 numbered 不能一起用

# 日志级别 #
