# java驱动 #

# Morphia #
官方出品的MongoDB ORM

## 注解 ##
### @Entity ###
可以定制集合的名字, 否则将会使用简单类名

### @Id ###
每个顶级文档必须要有一个Id
如果Id类型是ObjectId, 那么插入的时候会自动生成, 这是一个分布式的递增id, 不过位数有点大
如果Id类型非ObjectId, 那么可能需要自己设置id, 再进行插入
如果内嵌文档也定义了id, 那么它会被忽略, 因为内嵌文档没必要有id

### @Indexes/@Index ###
可以用于定制复合索引
需要指定索引名称, 字段名, 索引方式, 排序方式, 权重等, 有些属性仅对某些索引有效
可以对内嵌属性进行索引哦

是否在后台建立索引, 否则建立索引的时候会阻塞很多操作
新版MongoDB似乎可以建立TTL索引, 当时间到了的时候会自动删除文档, 可以了解一下
是否唯一索引
partialFilter 可以控制当某些条件成立的时候, 才会对该对象进行索引


### @Property ###
默认情况下, 被@Entity标记的类的所有属性都自动映射到文档的字段上
可以用 @Property 注解定制属性, 比如字段名

### @Transitent ###
表示要忽略这个字段

### @Indexed ###
用在属性上, 表示建立一个单字段的索引, 配置的参数和复合索引类似

### @Embedded ###
用在类上或属性上, 表示这是一个内嵌文档

### @Version ###
乐观锁
文档没有详细说明, 不过应该是这么用
```
这个version属性不需要显式赋值
@Version
private int version;
```

### @Reference ###
用在属性上, 表示引用一个顶级文档
DBRef

### @Validation ###
TODO 这个没怎么用过
定义验证逻辑
http://docs.mongodb.org/manual/core/document-validation/

### @NotSaved ###
表示这个字段不保存, 但是要加载

### @AlsoLoad ###
表示其他几个字段可以映射成当前属性, 当你想要修改一个字段名的时候, 在过渡期间, 这个注解就可以发挥作用

比如之前用户名的属性叫做, name, 后来改成了username, 但数据库里的还都是name
在过渡期间, 你可以这样写
```
@Property("username")
@AlsoLoad("name")
private String username;
```
这样当你query的时候, username肯定是不存在的, 这时候可以将name映射成username, 而当你保存的时候, 还是按username去保存
> 此处需要注意, 不能有2个相同的字段映射到不同的属性上


### @Serialized ###
表示这个属性要通过JDK序列化成byte[], 然后保存, 不推荐





## 插入数据 ##
调用Datastore的save系列方法, 这里介绍最复杂的save方法
save(Iterable<T> iter, insertOptions)
iter表示要插入的实体列表
insertOptions用于控制本次插入的参数

参数|描述
:-:|:-:
WriteConcern|控制本次操作的ack, 具体看mongodb文档, 简单地说是"本次操作要执行到什么地步才算成功"

当 WriteConcern 设置为 UNACKNOWLEDGED 时, 请求直接扔给MongoDB, 并且不会等它的响应就返回了
因此操作几乎都是成功的, 即使mongodb最终报了个错

save方法同时具备更新的能力, 这是通过检查id是否为null来判断的

## 查询 ##

```
类比
select * from users where username contains 'xzc' and password is not null

此处field("")是快捷方法, 产生的where语句都是and连接的
List<User> list = datastore.find(User.class)
    .field("username").contains("xzc")
    .field("password").exists()
    .asList();
System.out.println(list);
```

常见的操作符都是有的

### 投影 ###
```
//投影username
User list = datastore.find(User.class)
    .project("username", true)
    .get();//只获取第一个
System.out.println(list);
```


## 更新 ##
有一种方法是先从数据库里获取实体 t, 然后利用 save(t) 方法, 这是全量更新

### 复杂更新 ###
update(查询条件, 更新操作)
查询条件用于表示你要更新哪些文档, 这和query时的查询条件是一样的
更新操作则描述了你要如何更新这些文档

```
给所有男员工加1元工资

update users set salary = salary + 1 where gender = 'MALE'

UpdateOperations<User> incSalary = datastore.createUpdateOperations(User.class)
            .inc("salary", 1)
            .set("foo", "foo")//设置foo属性
            .unset("bar");//删除bar属性
Query<User> maleUsers = datastore.createQuery(User.class).field("gender").equal("MALE");
datastore.update(maleUsers, incSalary,new UpdateOptions());
```

## 删除 ##
delete(entity) 或 delete(query)

## 生命周期回调 ##
```
@PreLoad - Called before mapping the datastore object to the entity (POJO); the DBObject is passed as an argument (you can add/remove/change values)
@PostLoad - Called after mapping to the entity
@PrePersist - Called before save, it can return a DBObject in place of an empty one.
@PreSave - Called before the save call to the datastore
@PostPersist - Called after the save call to the datastore
```

可以在对象发生这几个操作的时候进行一些回调

# 整合querydsl #
最新版的querydsl也已经是1年前的版本了, 现在还不支持最新的morphia
不过可以通过重写一个来解决这个问题, 但我不知道有没有其他不兼容的问题
因为jar包之间的不兼容, 只能等到运行时才能发现...

```
package com.xzchaoo.learn.db.mongodb;

import com.google.common.base.Function;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.querydsl.core.types.EntityPath;
import com.querydsl.mongodb.AbstractMongodbQuery;
import com.querydsl.mongodb.morphia.MorphiaSerializer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.cache.DefaultEntityCache;

/**
 * TODO querydsl似乎好久没更新了 并没有支持新版的Morphia 因此需要重新实现以下这个类 不知道用起来会不会有冲突 小心为好
 *
 * @param <K>
 */
public class MyMongodbQuery<K> extends AbstractMongodbQuery<K, MyMongodbQuery<K>> {

    private final Datastore datastore;
    private final DefaultEntityCache cache;

    public MyMongodbQuery(Morphia morphia, Datastore datastore, EntityPath<K> entityPath) {
        this(morphia, datastore, new DefaultEntityCache(), entityPath);
    }

    public MyMongodbQuery(Morphia morphia, Datastore datastore, DefaultEntityCache cache, EntityPath<K> entityPath) {
        this(morphia, datastore, cache, (Class<K>) entityPath.getType());
    }

    public MyMongodbQuery(Morphia morphia, Datastore datastore, DefaultEntityCache cache, Class<K> entityType) {
        super(datastore.getCollection(entityType), new Function<DBObject, K>() {
            @Override
            public K apply(DBObject dbObject) {
                return morphia.fromDBObject(datastore, entityType, dbObject, cache);
            }
        }, new MorphiaSerializer(morphia));
        this.datastore = datastore;
        this.cache = cache;
    }

    @Override
    protected DBCollection getCollection(Class<?> type) {
        return null;
    }
}

```

