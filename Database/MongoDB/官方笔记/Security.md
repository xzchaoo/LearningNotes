1. 默认mongodb是不用验证的, 可以用 mongoed --auth 要求启动验证
2. 先创建一个管理员, 然后创建其他用户
3. 通信加密
4. 限制网络 bindIP
5. 加密数据
6. 用专门的系统用户运行mongod


# Authentication #
切换到某个数据库
db.auth(账号,密码)
https://docs.mongodb.org/manual/tutorial/enable-authentication/

## Users ##
db.createUser(user, writeConcern)
https://docs.mongodb.org/manual/reference/method/db.createUser/#db.createUser
创建一个用户的时候, 需要指定一个数据库, 但一个用户的访问权限可以不局限在这个数据库中
这样一个作用是命名空间的作业, 再来是让某个用户不能对某个用户进行修改
所有账号信息都保存在admin数据库的system.users

集群用户的数据存放在配置服务器的admin数据库中

有些操作需要直接连接到mongoed服务器上进行操作, 而不能在mongos上进行操作
这时候你需要用这个mongoed上的用户进行登录

v3.0以后 从本机连接到本机的数据库, 也需要验证, 除非你没有启动--auth, 或启动了--auth 但没有任何用户




常见权限
clusterAdmin
readAnyDatabase
read
write
readWrite
dbAdmin

## Roels ##
roles允许它对应的用户对一些资源进行一些操作

权限是对一项资源的一个操作的允许, 比较细节
角色可以继承, 然后就会拥有父亲的所有权限

### 内置的角色 ###
#### Database User Roles ####
read 允许对所有非系统集合进行读操作
readWrite 允许对所有非系统集合进行对写操作
#### Database Administration Roles ####
dbAdmin 任务 索引 统计信息
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
这几个角色都是可以访问一个集群里的所有数据库的
readAnyDatabase
readWriteAnyDatabase
userAdminAnyDatabase 
dbAdminAnyDatabase
#### Superuser Roles ####
root 所有权利

### 用户自定义角色 ###
db.createRole() 只能作用于当前数据库, 除非是创建于admin数据库的角色
一个admin数据库里的角色 可以包含对其他数据库的权限
角色数据保存在system.roles
自定义权限可以细节到对哪个集合可以执行find操作 之类...

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
mongo --port 27017 -u "myUserAdmin" -p "abc123" --authenticationDatabase "admin"

use admin
db.auth("myUserAdmin", "abc123" )

# 增加用户 #
https://docs.mongodb.org/manual/tutorial/manage-users-and-roles/
执行该操作的用户本身要具有一些权限
用户可以携带额外的信息

# 增加角色 #
db.createRole()
```
use admin
db.createRole(
   {
     role: "manageOpRole",
     privileges: [
       { resource: { cluster: true }, actions: [ "killop", "inprog" ] },
       { resource: { db: "", collection: "" }, actions: [ "killCursors" ] }
     ],
     roles: []
   }
)
```
