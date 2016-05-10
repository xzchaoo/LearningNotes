1. 默认mongodb是不用验证的, 可以用 mongoed --auth 要求启动验证
2. 先创建一个管理员, 然后创建其他用户
3. 通信加密
4. 限制网络 bindIP
5. 加密数据
6. 用专门的系统用户运行mongod

# Authentication #
切换到某个数据库, 用下面的方式进行认证
db.auth(账号,密码)
db.logout() 注销登陆
https://docs.mongodb.org/manual/tutorial/enable-authentication/

支持一系列的认证机制
副本集和集群之间的认证呢?

直接连接到mongos, 在mongos添加账号, 然后在mongos处做验证.
但是有一些操作, 比如rs.reconfig() 需要直接连接到 该副本集所在的master上去做操作
这直接要求你连接到该master, 并且使用该节点的账号信息做认证
称该账号为 shard local administrative user
因为用户数据是保存在local数据库里的, 它是不会被其他节点共享的

同一个副本集的节点们应该共享相同的账号密码

2.6之后, MongoDB将分片集群的用户信息保存在config server的admins数据库里

## Users ##
db.createUser(user, writeConcern)
https://docs.mongodb.org/manual/reference/method/db.createUser/#db.createUser
创建一个用户的时候, 需要指定一个数据库, 但一个用户的访问权限可以不局限在这个数据库中
这样一个作用是命名空间的作用, 再来是让某个用户不能对某个用户进行修改

所有账号信息都保存在admin数据库的system.users

集群用户的数据存放在配置服务器的admin数据库中

有些操作需要直接连接到mongoed服务器上进行操作, 而不能在mongos上进行操作
这时候你需要用这个mongoed上的用户进行登录

v3.0以后 从本机连接到本机的数据库, 也需要验证, 除非你没有启动--auth, 或启动了--auth 但没有任何用户

一般情况下一个用户在哪个数据库里被创建, 它就只能拥有这个数据库相关的权限
当然也可以设置成拥有其他数据库的权限

用户的唯一标识符 = 数据库名 + 用户名
创建一个用户 + 有权限处理各种数据库 而不是在每个数据库都创建相同的账号

## Roels ##
roles允许它对应的用户对一些资源进行一些操作
简单的说一个角色就是一堆权限的集合

权限是对一项资源的一个操作的允许, 比较细节
角色可以继承, 然后就会拥有父亲的所有权限
资源可以是 数据库 或 集合 或 集群

一个角色可以从当前数据库的其他角色进行继承
admin数据库里的角色可以从任何数据库里的角色进行集成

rolesInfo showPrivileges showBuiltinRoles

常见权限
https://docs.mongodb.org/manual/reference/privilege-actions/#security-user-actions

### 内置的角色 ###
MongoDB provides the built-in database user and database administration roles on every database. 
默认情况下, 每个数据库都会有这些内置的角色, 你无法干预他们.

#### Database User Roles ####
read 允许对所有非系统集合进行读操作, 不过 system.indexes system.js system.namespaces 还是允许读的
readWrite 允许对所有非系统集合进行读写操作, 并且允许 system.js的写操作

#### Database Administration Roles ####
dbAdmin 任务 索引 统计信息 它并没有权限做 用户/角色 的管理
dbOwner 可以对这个数据库执行任何操作 相当于是 readWrite + dbAdmin + userAdmin
userAdmin 可以对这个数据库的用户进行管理, 并且可以授予任何用户任何权限
	admin数据库中的userAdmin可以访问任何集合的用户
	
#### Cluster Administration Roles ####
clusterAdmin 集群的大部分操作
clusterManager 访问config和local数据库
clustermonitor
hostManager

#### Backup and Restoration Roles ####
backup
restore

#### All-Database Roles ####
这几个角色都是可以访问所有数据库的, 这些角色是放在admin数据库里的
readAnyDatabase
readWriteAnyDatabase
userAdminAnyDatabase 
dbAdminAnyDatabase

#### Superuser Roles ####
root 所有权利

### 用户自定义角色 ###
角色的唯一标识符 = 数据库 + 角色名
db.createRole() 只能作用于当前数据库, 除非是创建于admin数据库的角色
一个admin数据库里的角色 可以包含对其他数据库的权限
角色数据保存在admin数据库的system.roles集合里
自定义权限可以细节到对哪个集合可以执行find操作 之类...


# 集合级别的权限 #
```
privileges: [
	{ resource: { db: "products", collection: "inventory" }, actions: [ "find", "update", "insert" ] },
	{ resource: { db: "products", collection: "orders" }, actions: [ "find" ] }
]
```


# 安全增强 #
bind_ip
防火墙

启动--auth 或 security.authorization 选项
```
use admin
db.createUser(
  {
    user: "myUserAdmin",
    pwd: "abc123",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)
```
认证的两种方式

客户端连接的时候:
mongo --port 27017 -u "myUserAdmin" -p "abc123" --authenticationDatabase "admin"

或连接后进行验证
use admin
db.auth("myUserAdmin", "abc123" )

# 用户相关的操作 #

## 增加用户 ##
https://docs.mongodb.org/manual/tutorial/manage-users-and-roles/
执行该操作的用户本身要具有一些权限
用户可以携带额外的信息

## 添加用户 ##
执行这个动作本身需要一定的权限

https://docs.mongodb.org/manual/reference/method/db.createUser/#db.createUser
use blog
db.createUser({
	账号 密码 定制数据 角色
})

## 更新用户 ##
执行这个动作本身需要一定的权限

use blog
db.updateUser(username,{
	密码 定制数据 角色
})
这样是会覆盖原有的用户的

## 修改角色 ##
执行这个动作本身需要一定的权限

如果是要添加/删除角色的话可以使用
db.grantRolesToUser()
db.revokeRolesFromUser()

db.grantRolesToUser(username, roles, writeConcern)

## 修改密码 ##
执行这个动作本身需要一定的权限

use products
db.changeUserPassword("accountUser", "SOh3TbYhx8ypJPxmt1oOfL")

# 角色相关的操作 #
https://docs.mongodb.org/manual/reference/method/js-role-management/

## 增加角色 ##
db.createRole()
```
use admin
db.createRole(
   {
     role: "manageOpRole",
     privileges: [
       { resource: { cluster: true }, actions: [ "killop", "inprog" ] },
       { resource: { db: "a", collection: "" }, actions: [ "killCursors" ] } 集合为空 表示对于所有集合 db如果为''的话表示对于所有数据库
     ],
     roles: []//继承了哪些角色
   }
)
```

```
use blog
db.createRole(
   {
     role: "myRole1",
     privileges: [
       { resource: { db: "blog", collection: "" }, actions: [ "changeOwnPassword" ] } 集合为空 表示对于所有集合 db如果为''的话表示对于所有数据库
     ],
     roles: []//没有继承其他的角色
   }
)

db.grantRolesToUser('xzchaoo',['myRole1'])
这里 第二个参数直接使用一个字符串数组了, 这样的话默认是当前数据库(blog)里的myRole1角色
否则需要{role:'myRole1',db:'blog'}这样的格式
```
## 删除角色 ##
db.dropUser(rolename)



# Resource Document #
用于授权的时候指定资源
资源可以是 数据库, 集合, 集群.

{ db: "products", collection: "inventory" } products数据库下的inventory结合

{ db: "test", collection: "" } test数据库
当这样做的时候, 实际上一些系统产生的数据库是不被包含在它里面的, 你需要显式指定
{ db: "test", collection: "system.js" } system.js 是一个系统用的适合
下面几个集合都需要显示指定
<database>.system.profile
<database>.system.js
system.users Collection in the admin database
system.roles Collection in the admin database

{ db: "", collection: "accounts" } 这样就可以跨db, 所有db下的accounts集合
只有位于admin下的角色才能设置拥有 db:"" 的权限

{ db: "", collection: "" }
只有位于admin下的角色才能设置拥有 db:"" 的权限

{ cluster : true }
比如某条权限:
{ resource: { cluster : true }, actions: [ "shutdown" ] }

更多详情见
https://docs.mongodb.org/manual/reference/security/



# 副本集的安全 #
节点之间使用  Internal Authentication 来认证
比如指定一个共同的keyFile, 其他方式比如 X509 证书
security:
  authorization: enabled
  keyFile: E:\MongdoDB\k.key
只要大家的 k.key 内容一致就认为认证成功

可以使用这样的方式生成一个key文件, 长度必须介于6~1024
openssl rand -base64 755 > <path-to-keyfile>
chmod 400 <path-to-keyfile>

配置
security:
  keyFile: <path-to-keyfile>
  

而对于外界的客户端, 就要走普通的认证方式

如果你通过 localhost 的IP地址连接到了master, 并且当前没有任何的账号.
并且这个行为会传播到其他的节点上
那么你有权可以创建一个 管理员账号(我一般是创建具有root权限的账号, 不知道其他类型的行不行)
一旦创建了, 你就没办法再创建了, 你必须认证后才会有权限
The first user must have privileges to create other users, such as a user with the userAdminAnyDatabase. This ensures that you can create additional users after the Localhost Exception closes.
创建的第一个用户必须要有可以创建其他用户的权力
看这里 https://docs.mongodb.org/manual/core/security-users/#localhost-exception

X509之类的由于不会就没有深入研究了


# 传输加密 TLS/SSL #
