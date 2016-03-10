js语法

.mongorc.js 文件
这个文件放在当前用户的home里, 会在你的mongo.exe启动之后自动执行, 可以放一些初始化代码,使用--norc启动mongo.exe就不会加载该文件

edit a 可以打开记事本对a变量进行修改, 记事本程序通过EDITOR环境变量指定

ctrl+c 或 quit() 退出

# Configure the mongo Shell #

修改prompt
将prompt变量设置成形如
```js
function (){
	return 'xzc>'
}
```
就会使得prompt为 xzc>

修改batch大小
DBQuery.shellBatchSize =10 
这样find()默认只会打出10条记录


# Access the mongo Shell Help #
输入help就会有一些常用功能的提示
show dbs; 显示所有的数据库
show collections; 显示当前数据库里的所有集合
db.help();查看数据库级别的帮助
db.foo.help()查看集合级别的帮助
db.foo.find().help()查看迭代器的帮助
db.foo.find().toArray()


# Write Scripts for the mongo Shell #
新建Mongo对象
```
new Mongo()
new Mongo(<host>)
new Mongo(<host:port>)
conn = new Mongo();
db = conn.getDB("myDatabase");
db.getMongo()可以获得这个db对应的Mongo对象
```
辅助函数
connect(url,user,pwd)可以帮助快速创建一个新的db
在命令行输入conenct可以查看函数源代码


# Data Types in the mongo Shell #
mydoc._id instanceof ObjectId
typeof
数字默认是number类型, 包含了整形和浮点型
除非使用NumberLong(1), NumberInt(2) 这样的方式特别声明

# mongo Shell Quick Reference #
https://docs.mongodb.org/manual/reference/mongo-shell/
显示了一堆常见的启动参数 和 辅助方法
各种快捷键

db.users.save()是insert或update的意思
