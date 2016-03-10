数据指尖的关系 : 一对一 一对多 多对多
内嵌文档 或 放引用

# 文档检查 #
在db.createCollection()时指定一个validator
可以对集合里的文档做检查
比如要求name是一个string
这个行为发生在insert update时
当你对一个集合添加 检查 时, 所有的不合法的数据并不会怎样, 直到你对这些数据进行修改

```
Issue the following command to add a validator to the contacts collection:
db.runCommand( {
collMod: "contacts",
validator: { $or: [ { phone: { $exists: true } }, { email: { $exists: true } } ] },
validationLevel: "moderate"
} )

validationAction 决定如何处理不合法的文档: 接受, 拒绝, 警告?
db.createCollection( "contacts",
{
validator: { $or:
[
{ phone: { $type: "string" } },
{ email: { $regex: /@mongodb\.com$/ } },
{ status: { $in: [ "Unknown", "Incomplete" ] } }
]
},
validationAction: "warn"
}
)
```
插入或更新时使用bypassDocumentValidation跳过扫描

# Data Models #
范式(放id)
反范式(内嵌)

## 文档增长 ##
## Atomicity ##

如果你的类型比较少
那么可以
创建logs_debug logs_dev两个集合以加快速度

一般集合多一点反而性能会好
单一集合对高吞吐量批处理很好

<database>.ns里存放了集合的元数据

# 4.3 Data Modeling Concepts #
内嵌文档
存放引用
文档级别的原子性
添加索引对写操作有一点点坏的影响

具有TTL功能的集合

# 4.4 #
A与B是一对一关系
	如果B有从属于A的特征, 那么B就内嵌于A, 否则应该放引用
A与B是一对多关系
	同上, 也可以考虑在B处放A的引用

# 4.4.2 #
树形结构该如何存储
1. 存放父亲的引用
2. 存放所有祖先的引用
3. 存放到根的字符串路径
4. 在父亲放儿子的引用
5. 每个节点维护一个left/right

# Data Model Examples and Patterns #
Model Tree Structures

# 4.4.3 Model Specific Application Contexts #
## Model Data for Atomic Operations ##
将一些数据放在同一个文档里, 这样这些数据就会有原子性

## Model Data to Support Keyword Search ##
给文档建立一个tags:["数据库","网络"]
然后为tags建立一个索引
以后就可以以"数据库","网络"为关键字进行搜索相关的文档(常用语论坛的帖子, 给帖子加上标签(tag) )

## Model Monetary Data ##
数值默认是number类型 其实是浮点型
如果要使用 int 或 long 则要 NumberInt NumberLong

## Model Time Data ##
用UTC格式存数据


# 4.5Data Model Reference #
DBRefs 可以用于表示文档之间的引用关系
ObjectId的组成

eclipse+tomcat/jboss
glassfish
