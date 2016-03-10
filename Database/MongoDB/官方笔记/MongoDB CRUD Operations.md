1. 了解bson的概念
2. 与json的区别(主要是二进制)
3. bson支持更多类型

# MongoDB CRUD Concepts #

## Read Operations ##

### Read Operations Overview ###
db.users.find({条件},{投影}).sort({排序}).skip(1).limit(2)
findOne
理解投影的作用

### Cursor ###
通过db.serverStatus().metrics.cursor可以查看迭代器的配置信息
修改batch大小
DBQuery.shellBatchSize =10 
这样find()默认只会打出10条记录

### Query Optimization ###
1. 创建索引(普通索引, 哈希索引, 其他)
2. 查询覆盖(指的是你最终要返回的字段都包含在索引里了)
3. 使用explain()分析查询的性能

### Query Plans ###
### Distributed Queries ###


##  Write Operations  ##

### Write Operations Overview ###
insert(对象或数组), insertOne({...}), insertMany([{...},...])
update updateOne updateMany replaceOne
findOneAndUpdate()
deleteOne deleteMany remove
save 根据_id执行insert或update
db.collection.bulkWrite() 
```
db.collection.bulkWrite(
   [
      { insertOne : { "document" : { name : "sue", age : 26 } } },
      { insertOne : { "document" : { name : "joe", age : 24 } } },
      { insertOne : { "document" : { name : "ann", age : 25 } } },
      { insertOne : { "document" : { name : "bob", age : 27 } } },
      { updateMany: {
         "filter" : { age : { $gt : 25} },
         "update" : { $set : { "status" : "enrolled" } }
         }
      },
      { deleteMany : { "filter" : { "status" : { $exists : true } } } }
   ]
)
bulkWrite还分有序和无序执行
```
https://docs.mongodb.org/manual/reference/method/db.collection.bulkWrite/#bulkwrite-example-unordered-bulk-write

### Atomicity and Transactions ###
文档级别是原子性的
使用$isolated可以使得如果一个操作影响了多个文档的话, 那么他们总体是原子性的
$isolated does not work with sharded clusters.
```
db.foo.update(
    { status : "A" , $isolated : 1 },
    { $inc : { count : 1 } },
    { multi: true }
)
```
两阶段提交 提供了一种模仿事务的方法

### Distributed Write Operations ###
分片集群

### Write Operation Performance ###
### Bulk Write Operations ###


##  MongoDB CRUD Tutorials ##
### Insert Documents ###
插入文档后会返回
WriteResult({ "nInserted" : 1 })
表示成功插入了多少, 如果发生错误, 它会包含错误信息

### Query Documents ###
没太多好讲的
特别注意数组元素该使用的查询方法
$all $elemMatch 之类
内嵌文档的查询方式
https://docs.mongodb.org/manual/tutorial/query-documents/

### Modify Documents ###
findAndModify
mutil upsert
主要是涉及到大量的修改器
$set $currentDate
```
db.inventory.update(
    { item: "MNO2" },
    {
      $set: {
        category: "apparel",
        details: { model: "14Q3", manufacturer: "XYZ Company" }
      },
      $currentDate: { lastModified: true }
    }
)
$currentDate用于将lastModified设置成当前时间
```

### Remove Documents ###
没啥好说的

### Limit Fields to Return from a Query ###
1. 投影
2. field : 1 or true 返回该字段
field : 0 or false 不返回该字段
3. _id特殊
4. 投影对内嵌文档的字段也有效
5. 使用$slice限制数组返回的大小

### Limit Number of Elements in an Array after an Update ###
在更新之后限制一个数组的大小
$push $each $sort $slice
https://docs.mongodb.org/manual/tutorial/limit-number-of-elements-in-updated-array/

$push:{
	qqs:{
		$each:[{score:1},{score:2}],
		$sort:{score:1}
		$slice:-3
	}
}

### Iterate a Cursor in the mongo Shell ###
默认输出20个
toArray方法可以转成数组
手动迭代

### Analyze Query Performance ###
cursor.explain()
COLLSCAN 表示整个集合扫描
IXSCAN 表示通过索引扫描
explain(true) 将verbose设置为true, 输出更信息的信息
hint
createIndex
ensureIndex

### Perform Two Phase Commits ###
### Update Document if Current ###
一般来说要更新一个文档之前 我们会先查询这个文档
于是在更新的时候就 需要判断一下要更新的对象是否还是你刚才取出来的对象
因为有可能它已经被别人更改了, 而你再做修改就可能导致错误

### Create Tailable Cursor ###
### Create an Auto-Incrementing Sequence Field ###
思路是使用一个counters集合
然后配合 findAndModify $inc
https://docs.mongodb.org/manual/tutorial/create-an-auto-incrementing-field/
```
db.counters.insert(
   {
      _id: "userid",
      seq: 0
   }
)
function getNextSequence(name) {
   var ret = db.counters.findAndModify(
          {
            query: { _id: name },
            update: { $inc: { seq: 1 } },
            new: true,
            upsert: true //这里使用了upsert 更方便一些
          }
   );

   return ret.seq;
}
db.users.insert(
   {
     _id: getNextSequence("userid"),
     name: "Sarah C."
   }
)

db.users.insert(
   {
     _id: getNextSequence("userid"),
     name: "Bob D."
   }
)
```

### Perform Quorum Reads on Replica Sets ###
主要是一个WriteConcern的问题
```
var updatedDocument = db.products.findAndModify(
   {
     query: { sku: "abc123" },
     update: { $inc: { _dummy_field: 1 } },
     new: true,
     writeConcern: { w: "majority", wtimeout: 5000 }
   },
);
```
https://docs.mongodb.org/manual/tutorial/perform-findAndModify-quorum-reads/

##  MongoDB CRUD Reference ##
### Write Concern ###
w:"策略名" 或 int值
	int值表示至少要把这个write写入到多少个副本集成员才算成功
	w=0的话 立即返回(但如果j=true的话 还是会写硬盘的)
	w=1的话 就只接入到主节点, 这是默认值
	"majority":要写入到副本集的大多数成员才算成功, 在3.2版本之后它蕴含了j=true
	其他的string, 作为tag值, 保证该写操作已经传播到了一个带有该tag的成员
j:true 会使得积累的日志马上被写到硬盘
	3.2之前的j=true只保证主节点写入日志即可
	3.2之后的j=true要保证总共有w个节点写入日志才行
	For replica sets using protocolVersion: 1, w: "majority" implies j: true, if journaling is enabled. Journaling is enabled by default.
wtimeout 超时时间
	如果超过这个时间这个操作还没有完成, 那么就会返回一个错误, 但其实数据可能是写入成功的.
https://docs.mongodb.org/manual/reference/write-concern/
### Read Concern ###
