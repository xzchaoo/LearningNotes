# Index Introduction #
1. 默认_id有一个唯一索引
2. 单字段索引
3. 复合索引
	1. 要注意符合索引的顺序和方向, 如果有必要 对于2个字段 要建立2个索引
	2. 索引的前缀也可以做索引
4. Multikey Index 用于处理数组作为索引
	1. 实际上就是有这个文档有多个key可以索引到
	2. 不支持查询覆盖
	3. 常见的场景是, 给一些帖子加tags, 然后按照某个tag 找出符合的帖子
5. 地理位置索引
6. 文本索引
7. 哈希索引, 提供非常强的随机访问能力, 但是不适合范围访问
	1. 但是允许对同一个字段同时建立哈希和非哈希索引, 因此你可以建立一个普通的索引用于范围访问

## 索引的属性 ##
1. Unique Index 是否唯一
	1. 只允许一个文档缺少索引字段, 如果有多个 那就会出错
	2. 新版似乎支持多个文档缺少索引字段
	3. {unique:true}
2. Partial Index(new in 3.2)
	1. 只对满足某个表达式的文档做索引
	2. 比稀疏索引更强大
	3. https://docs.mongodb.org/manual/core/index-partial/
	4. 用一个partialFilterExpression参数来指定
	5. 该索引有时候不能用于排序等操作, 因为它只对部分文档进行索引, 因此除非保证你已经先过滤好了数据使得数据在该PI索引之内
	6. 当PI与unique一起使用的时候 要注意 unique 是对于满足条件的文档来说的, 对于不满足条件的文档, 他们可能就不unique了
3. Sparse Index 稀疏索引
	1. 对于那些索引键不存在的文档不做索引
	2. 显然功能比 第二个索引还要弱一点
	3. 新版本建议使用PI
4. TTL索引, 可以一个Date字段创建一个TTL索引, 当超过这个字段指定的时间n秒后, 这个文档就会被删除(但不是立即)

## 覆盖查询 ##
当查询的条件和要返回的字段 是 所用的索引的子集的时候 就称之为覆盖查询
此时非常高效, 因为直接从索引就可以返回所需要的数据了, 而索引一般是放在内存里的

## 索引交叉 ##
如果可能的话MongoDB会使用多个索引一起工作
但这是否会提高性能需要看情况, 有可能还需要合并操作

## Index Createion ##
background 后台
sparse 稀疏
unique 唯一
name 指定索引的名字, 否则是自动生成的
db.collection.getIndexes()查看这个集合的所有索引
也可以使用
db.system.indexes.find({ns:'你的集合的全名'})

# 文本索引 #
支持字符串或字符串数组的属性
但一个集合只能有一个文本索引, 一个文本索引可以包含多个字段
```
db.collection.createIndex(
   {
     content: "text",
     "users.comments": "text",
     "users.profiles": "text"
   },
   {
     name: "MyTextIndex",
	weights:{
	content:1,//默认就是1
	xxx:2
}
   }
)
```
这样 subject(字符串) comments(字符串数组) 一起构成文本索引了
createIndex的第二个参数可以用于指定索引的名字, 这样要删除索引的时候你就有名字了

## 星号匹配 ##
db.collection.createIndex( { "$**": "text" } )
这会导致mongodb为所有字符串类型或字符串数组类型的属性创建文本索引

还可以这样用
```
db.collection.createIndex( { a: 1, "$**": "text" } )
```
但是注意, 如果此时要进行文本搜索, where条件里必须包含a!


# 在副本集上建立索引 #
如果主节点完成建立了索引, 那么备份节点随后也会跟着备份索引
在集群环境,mongos会将建立索引这个命令发送给每个分片的primary, 等primary完成后, 才会发给每个分片赌赢的副本集的备份节点

https://docs.mongodb.org/manual/tutorial/build-indexes-on-replica-sets/

# 杂 #
db.user.reIndex()重新建立索引
db.currentOp(), db.killOp()
explain hint 评估索引

创建索引
	后台创建索引 db.users.ensureIndex({'username':1},{background:true})
	db.currentOp() db.killOp()
	内嵌属性作为索引
	唯一索引(分片的时候有障碍)
重建索引
	reindex 集合大量删除的时候, 索引的空间并不会被回收, 产生了大量的碎片 重建索引提高空间效率
获得索引
删除索引
检查是否使用索引
	explain hint(提示使用索引)

Profilier
-profile=级别
db.setProfilingLevel(级别)
db.getProfilingLevel()

0 不开启
1 记录
2 记录所有命令



