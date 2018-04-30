# SSH key #
## 公钥 ##
公钥会被复制到SSH服务器上. 当SSH服务器收到一个用户的公钥, 如果SSH认为它是可信的, 那么会将它加入 authorized_keys 这个文件
``~/.ssh/authorized_keys``
下次这个用户再连接时, 将会直接成功


## 私钥 ##
私钥由用户自行保管, 持有这个私钥是用户身份的证明.
持有这个私钥的用户可以和持有对应的私钥的远程SSH服务器成功认证.
用户需要小心维护私钥. 
私钥叫做 identity keys


## 准备ssh环境 ##
1. 用户通过ssh-keygen生成key
2. 通过ssh-copy-id将公钥发送到远程服务器
	1. 远程服务器会存储该公钥为  authorized_keys
	2. 第一次连接到远程主机的时候, 需要将其标记为可信主机 ``~/.ssh/known_hosts``
3. 持有对应私钥的用户可以和远程服务器成功认证

## 管理私钥 ##
私钥可以有一个密码保护, passphrase.  
效果是用户持有的私钥是被加密的, 需要输入相应的密码才能进行解密.  
可以利用 ssh-agent 进行自动的解密工作
但通常情况下私钥不会被加密, 否则一些脚本和程序就无法全自动工作了

# 命令 #
## ssh ##
ssh [user@]host [command]

1. 登录名可以向上面那样指定, 或用 -l 指定, 如果不指定就是你的本次用户名
2. -p 端口
3. -i 指定芈月文件, 默认是 ~/.ssh/id_rsa

# ssh-agent #
可以将用户解密后的私有key放在内存里, 后续使用时不用在输入passphase
另外可以开启 AllowAgentForwarding(服务端) ForwardAgent(客户端)
是的私钥可以传播, 你连接到服务器1, 服务器1连接到服务器2, 此时你的key可以传播到服务器2

ssh-agent $SHELL 进入agent模式
直接执行ssh-agent的话会启动一个后台的agent, 还需要手动连接到后台才行


## ssh-add ##
进入 ssh-agent 模式之后, 可以用ssh-add添加私钥到环境里
必要的时候需要输入密码

ssh-add 默认会添加 ~/.ssh/id_rsa
ssh-add ~/.ssh/foo_rsa 也可以手动指定一个私钥


# scp #
scp 本地文件 远程文件
scp 远程文件 本地文件


ssh -P 22 -i ~/.ssh/id_rsa localPath user@host:/remotePath
ssh -P 22 -i ~/.ssh/id_rsa user@host:/remotePath localPath 

甚至可以在2台ssh服务器之间传输文件(这貌似需要ssh-agent的配合)


# ssh-keygen #
-t 指定算法, 默认是 rsa
-b 指定长度, 默认是 1024?
-P 指定密码
-C 指定 comment
-q 安静模式
-f 指定文件 默认是 ~/.ssh/id_rsa

还可以用于更换密码
ssh-keygen -p -P旧密码 -N新密码 -f 私钥


# ssh-copy-id #
现在本地利用 ssh-keygen 生成密钥对
然后执行
ssh-copy-id -f -i ~/.ssh/foo.pub -p 远程ssh端口号 [user@]host

-f 表示强制模式, 注意-f必须要写在靠前的地方, 即使没有找到私钥(~/.ssh/foo), 也允许你添加公钥
-i 可以指定公钥文件, 默认是 ~/.ssh/id_rsa.pub
-p 指定远程ssh端口, 默认是22, 可以不写
user默认和本地用户一样

第一次添加的时候, 通常你需要将其标记为可信任主机并且用用密码进行登录
添加完成之后, 远程服务器上的 ~/.ssh/authorized_keys 里就会有新增一行你的公钥

## 确保ssh目录的权限 ##
chmod 755 ~/.ssh
chmod 644 ~/.ssh/authorized_keys

authorized_keys 的每一行的格式:
选项 公钥 注释
ssh-rsa ASDFJKLSDFJ我是公钥SLDJFKSDFJ foo@foo.com

# sshd配置 #
如何只允许某些账号ssh上来?
如何只允许某些ip ssh上来?

是否允许Root登录
PermitRootLogin

如何检查对方的主机key
StrictHostKeyChecking
yes
no
ask

Port 修改端口


# ssh客户端配置 #
在 ~/.ssh/config 里


常见配置, 对某些主机使用特定的私钥
主机名支持 * ? 通配符

	Host foo.com
		HostName foo.com
		IdentityFile ~/.ssh/foo_id_rsa
		IdentityFile ~/.ssh/foo2_id_rsa
		User 默认用户名
		其他配置 其他值

如果指定了 HostName, 那么它用来表示实际的主机名
Host上的 foo.com 此时只是一个昵称

IdentityFile可以指定多次 会依次检测


ssh启动时候加入-v进入详细模式

# 转发 #

本地转发
ssh -L101:HOST:3306 remoteHost

远程转发
ssh -R2001:HOST:143 remoteHost

本地给自己的101发数据, 可以经由remoteHost转发给 HOST:3306

给remoteHost的2001发数据, 可以经由本地转发给HOST:143

ssh -R1234:localhost:3389 yourServer

这应该是需要服务端支持的


AllowTcpForwarding yes


## 端口反射 ##
远程转发
被动模式
