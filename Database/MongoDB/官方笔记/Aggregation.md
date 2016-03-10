https://docs.mongodb.org/manual/aggregation/

按照流水线的方式对数据进行处理
聚合可以在分片集合上执行

简单的聚合:
count
distinct
group
复杂的聚合:
aggregate
mapReduce



# Pipeline #
## 表达式 ##


## $match ##
执行过滤操作, 满足条件的才能留下来
## $project ##
1. 创建新字段, 字段重命名
2. 利用表达式计算给新字段赋值
3.

# MapReduce #
db.orders.mapReduce(mapFunc,reduceFunc,{query:query, out:'输出到哪个集合'});
## Map-Reduce and Sharded Collections ##
### 以分片集合为输入 ###
支持分片集合作为输入, MR操作会被分发到各个分片上, 最后再合并

### 以分片集合为输出 ###
支持写入到分片集合, 如果集合不存在那么会进行创建, 并且以_id作为片键

## Map Reduce Concurrency ##
1. 读取数据阶段, 每读取100个文档就会释放一次锁
2. MR过程中可能需要临时保存一些数据到临时集合, 每一个写操作都会拿一次锁
3. 如果输出集合不存在, 那么会拿一个锁来创建这个集合
4. 如果输出集合已经存在, 那么会拿一个锁用来将集合数据清空并写入新数据

## Map-Reduce Examples ##
map阶段可以有多个发射 emit(key, obj)
每次发射的key可以不同
reduce阶段合并完后的对象必须和obj具有相同的结构
比如map阶段的obj格式是 {tag:'转载', count:1}
那么reduce返回的结果必须要 {tag:'转载', count:100}, 可能reduce阶段对count进行的累加, 但总体结构必须一样, 原因你懂的

# Perform Incremental Map-Reduce #
增长式的Map-Reduce
 db.collection.mapReduce()
 基本思路是, 现在做一次MR, 然后将结果保存一下, 下一次要做MR的时候, 只需要对新数据做MR, 然后将两次的数据合并
To perform incremental map-reduce:
1. Run a map-reduce job over the current collection and output the result to a separate collection.
2. When you have more data to process, run subsequent map-reduce job with:
	1. the query parameter that specifies conditions that match only the new documents.
	2. the out parameter that specifies the reduce action to merge the new results into the existing output collection

```
db.sessions.mapReduce( mapFunction,
                       reduceFunction,
                       {
                         query: { ts: { $gt: ISODate('2011-11-05 00:00:00') } },
                         out: { reduce: "session_stat" },
                         finalize: finalizeFunction
                       }
                     );
```

# Aggregation Reference #
https://docs.mongodb.org/manual/meta/aggregation-quick-reference/#aggregation-expressions
## Aggregation Pipeline Quick Reference ##
### 阶段 ###
$match $limit $skip $sort 这几个简单, 不讲了
$project 可以新建或删除字段, 还可以进行表达式计算
$redact 接受一个表达式, 如果表达式返回$$DESCEND, 那么就会返回所有的顶级字段, 如果返回$$PRUNE就跳过这个文档, 如果返回$$KEEP	就保留这个文档
	因此$redcat 可以做$match的事
$unwind 可以将数组"摊开"
$group 进行分组, 分组后可以进行一些累加操作 sum, avg之类
$sample 指定一个数量n, 如果n超过总数量的5%,那么就会进行稽核扫描,排序,然后选 top n 的文档, 如果比5%小, 不同引擎有不同的处理方法, 带代价比较小
$geoNear
$lookup new in v3.2 可以执行一个join操作
$out 必须是最后一个阶段, 将结果写入到一个集合里
$indexStats 返回所有的索引的使用情况

```
假设books里的文档有一个字段叫做author, 里面放了这本书的作者的名字, 假设作者名字不重复

db.users.aggregate([
{$lookup:{
	from:'books',
	localField:'name',
	foreignField:'author',
	as:'books'
}}
]);
$lookup操作就会在每个user文档里添加一个字段books, 然后里面放的是所有的以该user为author的book


找出所有tags里包含 STLW和G 的文档(只需要顶级字段)
var userAccess = [ "STLW", "G" ];
db.forecasts.aggregate(
   [
     { $match: { year: 2014 } },
     { $redact: {
        $cond: {
           if: { $gt: [ { $size: { $setIntersection: [ "$tags", userAccess ] } }, 0 ] },
           then: "$$DESCEND",
           else: "$$PRUNE"
         }
       }
     }
   ]
);
```

Expressions的组成
1. 系统变量
2. 字段路径
3. 字面值常量
4. 表达式对象
5. 操作符表达式

### 系统变量 ###
使用$$<variable>.<field>的方式进行访问
https://docs.mongodb.org/manual/reference/aggregation-variables/#agg-system-variables

### 字段路径 ###
CURRENT表示了当前根对象
使用$开头, 加上一个字段的路径, 就可以起到引用这个字段的功能
$author.name 等价于 $$CURRENT.author.name
使用$let和$map可以添加用户自定义的变量, 需要使用$$开头来引用他们

### 字面值常量 ###
大部分字面值常量直接写就行了, 但是MongoDB认为以$开头的字符串要特殊处理, 因此可以使用$literal表达式

### 表达式对象 ###
{ <field1>: <expression1>, ... }

### 操作符表达式 ###
{ <operator>: [ <argument1>, <argument2> ... ] }
{ <operator>: <argument> }
注意有些表达式是新版本才有的

#### 布尔表达式 ####
$and $or $not
$not似乎要接受一个大小为1的数组?

#### 集合表达式 ####
$setEquals 集合相等, 会把两个数组当做集合处理
$setIntersection 返回集合的交叉部分
$setUnion 并集
$setDifference 差集
$setIsSubset 是否是子集
$anyElementTrue 是否包含有真值元素
$allElementsTrue 全为真值

#### 比较表达式 ####
$cmp:[exp1,exp2] <则-1 =则0 >则1
$eq $gt $gte $lt $lte $ne

#### 算术表达式 ####
$abs $add $ceil $divide $exp $floor $ln $log $log10 $mod
$multiply $pow $sqrt $substract $trunc

#### 字符串表达式 ####
注意有些表达式值只适用于ascii字符
$concat 相加
$substr:[<string>,<start>,<length>] 如果start是负数 那么返回"" 如果legnth是负数 那么就返回剩余的全部
$toLower
$toUpper
$strcasecmp 忽略大小写的比较

#### 数组表达式 ####
$arrayElemAt:[array,index] 返回第i个元素
$concatArrays 连接数组
$filter 过滤数组元素
$isArray
$size
$slice

```
{
  $filter: {
     input: [ 1, "a", 2, null, 3.1, NumberLong(4), "5" ],
     as: "num",
     cond: { $and: [
        { $gte: [ "$$num", NumberLong("-9223372036854775807") ] },
        { $lte: [ "$$num", NumberLong("9223372036854775807") ] }
      ] }
  }
}
```

#### 变量表达式 ####
$let $map
$map就是对数组的每个元素执行map操作
```
$let相当于创造了一个上下文, vars就是指定上下文变量的
 $let:
     {
       vars: { <var1>: <expression>, ... },
       in: <expression>
     }
```

#### 字面值表达式 ####
$literal

#### 日期表达式 ####
$dayOfYear
$dayOfMonth
$dayOfWeek
$year
$month
$week
$hour
$minute	Returns
$second
$millisecond
$dateToString

#### 条件表达式 ####
$cond
$ifNull

#### 积累器 ####
$sum $avg $first $last $max $min $push $addToSet $stdDevPop $stdDevSamp
3.2版本以前积累器只能用于$group, 后来也可以用于$project


## Aggregation Commands ##
聚合的命令
aggregate count distinct group mapReduce

## Aggragation Commands Comparason ##
https://docs.mongodb.org/manual/reference/aggregation-commands-comparison/
aggregate group mapReduce的对比

## SQL to Aggregation Mapping Chart ##
大部分SQL的操作与MongoDB的对应
https://docs.mongodb.org/manual/reference/sql-aggregation-comparison/

