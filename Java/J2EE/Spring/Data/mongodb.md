# New #
1. 相关的annotation id field indexed compundIndex GeoSpatiaindexed textindeded query meta
2. 支持投影
3. 支持 Query By Example
4. 支持SpEL表达式在@Query中

MongoTemplate, 可以用于对象映射, 异常转换
Criteria.* 的方法可以用于构建条件语句, 比如where("name").is("aaa")

使用 MongoClientFactoryBean 构建mongodb客户端
好处是它将抛出的异常转成了dao相关的异常
当然也可以使用mongo命名空间

MongoDbFactory, Spring提供的一个接口, 用于屏蔽具体客户端的实现细节
常见的实现 SimpleMongoDbFactory

MongoConverter
SimpleMappingConverter
MongoMappingConverter 默认的选择

MongoTypeMapper
用于映射实际的java类型, 其策略是添加一个字段 _class 用于保存改记录的真实java类型
@TypeAlias 可以给类起一个短的别名, 这样就不用保存全名了

为了精调文档与对象的映射, 可以:
MappingMongoConverter, for example Converter<Person, DBObject> and Converter<DBObject, Person>.

# template #
一个java类名的小写作为他的集合名
可以通过 @Document 显式定义

findOne findAll findById find findAndRemove
save 如果id已经存在则覆盖对象
insert 插入对象, 如果id冲突则异常
updateFirst updateMulti upsert findAndModify remove
辅助类 Query(用于定位元素) Update(用于定义要做什么修改, $set 之类...)

@Version 提供了和JPA里一样的意思
这要求Using MongoDB driver version 3 requires to set the WriteConcern to
ACKNOWLEDGED. Otherwise OptimisticLockingFailureException can be silently
swallowed.

## Query/BasicQuery ##
BasicQuery query = new BasicQuery("{ age : { $lt : 50 }, accounts.balance : { $gt
: 1000.00 }}");
query(where("age").lt(50)
.and("accounts.balance").gt(1000.00d)
where等方法是 Criteria 的静态方法

addCriteria
fields 指定要返回的内容
skip limit 分页
with(Sort对象) 用于排序

## Criteria ##
all and andOperator elemMatch exists gt gte in in is lt lte 等方法


提供了: is lt gt 等用于构建表达式
## Update ##
addToSet inc pop pull pullAll push pushAll rename set unset



# 例子 #

```
Circle circle = new Circle(-73.99171, 40.738868, 0.01);
List<Venue> venues =
template.find(new Query(Criteria.where("location").within(circle)), Venue.class);
```

```
db.foo.createIndex(
{
title : "text",
content : "text"
},
{
weights : {
title : 3
}
} )
```

```
Query query = TextQuery.searching(new TextCriteria().matchingAny("coffee", "cake"))
.sortByScore();
List<Document> page = template.find(query, Document.class);
```

```
ExampleMatcher matcher = ExampleMatcher.matching() 
.withIgnorePaths("lastname") 
.withIncludeNullValues() 
.withStringMatcherEnding();
Example<Person> example = Example.of(person, matcher); 
```



# Query By Example (QBE) #
ExampleMatcher 可以用于构建QBE的一些规则, 比如忽略某些属性参与QBE
Example

需要让你的仓库实现 QueryByExampleExecutor 接口

# MapReduce的用法 #
具体见文档
```
MapReduceResults<ValueObject> results = mongoOperations.mapReduce("jmr1",
"classpath:map.js", "classpath:reduce.js", ValueObject.class);
for (ValueObject valueObject : results) {
System.out.println(valueObject);
}
```
其中map.js reduce.js是这两个函数所在的位置
ValueObject岁最终的结果

# 注册脚本 #
见9.9 Script Operations

# Group #
# 聚合框架 #
Aggregation类
AggregationOperation
AggregationResults
```
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
Aggregation agg = newAggregation(
pipelineOP1(),
pipelineOP2(),
pipelineOPn()
);
AggregationResults<OutputType> results = mongoTemplate.aggregate(agg,
"INPUT_COLLECTION_NAME", OutputType.class);
List<OutputType> mappedResult = results.getMappedResults();
```

Pipeline project skip limit lookup unwind group sort geoNear
Group addToSet first last max min avg push sum 
Boolean add subtract multiply divide mod
Comparison eq gt gte lt lte ne
Arthmetic
String
Date
Conditional
Lookup

支持用SpEL表达式写数学表达式
```
1 + (q + 1) / (q - 1)

{ "$add" : [ 1, {
"$divide" : [ {
"$add":["$q", 1]}, {
"$subtract":[ "$q", 1]}
]
}]}
```

# GridFS #


# 配置 #
@EnableMongoRepositories
AbstractMongoConfiguration
MongoRepository

@Query("{ 'firstname' : ?0 }")
List<Person> findByThePersonsFirstname(String firstname);

@Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
List<Person> findByThePersonsFirstname(String firstname);


public interface PersonRepository extends MongoRepository<Person, String>,
QueryDslPredicateExecutor<Person> {
// additional finder methods go here
}

Id Document DBRef Indexed
CompundIndex
GeoSpatialIndexed
TextIndexed
Language
Transient
PersistenceConstructor
Value
@Field
@Version



# P120左右描述了一个好像不错的东西 #
10.3.6. Projections

