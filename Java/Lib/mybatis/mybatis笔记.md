# mybatis的几个建议 #


# 学习mybatis #
1. SqlSession 跟 Hibernate的Session/JPA的EntityManager的管理范围大概是一样的, 每个线程或请求要有独立的SS, 它是非线程安全的.
2. 要保持 SqlSessionFactory 的单例, 当然如果需要操作多个数据库的话, 是可以有多个SSF

1. 依赖
```
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.4.1</version>
</dependency>
```

2. 添加 mybatis 配置文件, 放在 resources目录下
3. 创建 SqlSessionFactory(ssf)
```
InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
ssf= new SqlSessionFactoryBuilder().build(inputStream); 这样就获得一个ssf了
ss = ssf.openSession(); 获得一个 session, 然后就通过session做各种增删改查的工作
```


# 配置文件 #
## properties ##
可以将属性外置到一个properties文件里
```
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/> 覆盖属性
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
...
<dataSource type="POOLED">
  <property name="driver" value="${driver}"/> 引用属性
	...
</dataSource>
```

## settings ##
用于控制 ``mybatis`` 的一些行为
具体见文档 http://www.mybatis.org/mybatis-3/zh/configuration.html
主要是: 控制缓存 控制懒加载 自动主键生成 超时时间 命名规则 session级别缓存
学习初期, 大部分保持默认配置即可.

## typeAliases ##
这个是用于简化配置 完全可以不用

类型别名是为 Java 类型设置一个短的名字. 否则在很多地方你需要写类全名
当使用基于 annotation 的方式的时候, 就需要使用 @Alias("别名") 这样的用法
MyBatis 已经对 一些常见的java类和基本数据类型做了映射, 具体见文档

## typeHandlers ##
最常使用到 TypeHandler 的情况是要将一个 enum 保存成数据库里的int, 默认是 string
其他用途目前比较少

当要将一个对象设置到 PreparedStatement , 或从 ResultSet 的一个字段取出一个对象时 的处理器.
对于基本数据类型和常见类型都已经有了, 可以自定义
官方文档有一个例子, 主要是继承 BaseTypeHandler, 实现/重写各种方法, 最后在配置文件上注册就行
```
<typeHandlers>
  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
</typeHandlers>
```

每个元素都有 javaType 和 jdbcType
对于常见的类型都有默认的配置

将所有的处理器放早一个包下, 然后:
```
<typeHandlers>
  <package name="org.mybatis.example"/>
</typeHandlers>
```

### 处理枚举类型 ###
EnumTypeHandler EnumOrdinalTypeHandler

我的demo项目里已经放了一个 自定义的 类型处理器的例子了

这样用, 对于每个属性你都要制定
```
<result column="USER_STATUS" property="status" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler"/>
```

或这样用, 所有的UserStatus都会运用该handler
```
<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"
		             javaType="org.xzc.learn.mybatis.entity.UserStatus"/>
```

## mappers ##
这个是需要重点学习的部分!

# Mapper文件 #
## cache ##
## cache-ref ##

## resultMap ##
1. 用于建立 column 与 javabean 的属性的映射
2. 可以使用 extends 继承配置
3. type写的是实体类型

select的时候, 如果你的列名与属性名完全一致, 那么不需要额外的操作(这是因为默认是支持自动的column->property):
```
<select id="selectUsers" resultType="User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

如果列名与属性名不一致, 那么需要显式:
```
<select id="selectUsers" resultType="User">
  select
    user_id             as "id",
    user_name233           as "userName",
    CLM_hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</select>
```

一旦情况多了, 第二种方法就显得麻烦了!
这时候就需要 ``resultMap``

利用 ``resultMap`` 元素, 可以事先定义好一个一条记录如何与一个Java对象映射.
```
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```

定义好了之后, 就可以在 select 的 resultMap 属性上使用它了
```
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

### 支持的子元素 ###
1. constructor 用于将属性注入构造器, 这样你就可以不用暴露公共的set方法
	1. idArg
	2. arg
2. id 用于将记录的id标出, 有助于提升性能
3. result 用于映射基本属性类型和常见java类
	1. property column
	2. javaType jdbcType typeHandler 很多情况下这些属性都不用指定
	3. 复杂的类也可以使用result, 但是要有相应的 TypeHandler
4. association 关联关系
	1. property column
	2. javaType jdbcType typeHandler
	3. columnPrefix 指定一个前缀, 则它相关联的column全都会自动加上这个前缀
	4. select, 表明是先拿到记录之后, 再发一条请求到数据库取数据, 这称为嵌套查询, 如果不使用select的话就要使用 resultMap 和 columnPrefix了
	5. fetchType
5. collection
	1. 用于处理一对多
	2. 支持的参数和association类似
6. discriminator
	1. case 

### 利用 resultMap 处理关联关系 ###
再发一个查询去取回关联对象
```
<resultMap id="card_withUser" type="org.xzc.learn.mybatis.entity.Card">
	<id column="CARD_ID" property="id"/>
	<result column="CARD_MONEY" property="money"/>
	<association property="owner" column="CARD_OWNER_ID" select="selectUser"/>
</resultMap>

<select id="selectCard" resultMap="card_withUser">
	select * from MB1_CARD where CARD_ID = #{id}
</select>
```
这种方法可能会出现 N+1 问题

此时就要使用关联的嵌套结果了(使用 join)
```
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>

<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>

```

### collection ###
1. 拿到结果之后, 再发一条指令去取关联的集合
 
<collection property="cards" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>

javaType可以不写 可以自动推断出
ofType控制的是泛型的类型
select = ... 表明该对象被取出来之后, 将会再发一个select到数据库将相关的posts都取出来
column 是当前取出的记录的哪个字段

2. 使用 join 将关联集合一起拿出来
	1. 在select里你要使用join语句, 然后最好 column 要有前缀的区分
	2. 在 resultMap 里 放一个 collection
		1. 设置一个resultMap 和 columnPrefix
		2. collection也可以直接嵌套 id result 等元素, 而不再使用 resultMap

### discriminator ###
当用到类继承的时候会使用到
TODO 
```
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultType="carResult">
      <result property="doorCount" column="door_count" />
    </case>
    <case value="2" resultType="truckResult">
      <result property="boxSize" column="box_size" />
      <result property="extendedCab" column="extended_cab" />
    </case>
    <case value="3" resultType="vanResult">
      <result property="powerSlidingDoor" column="power_sliding_door" />
    </case>
    <case value="4" resultType="suvResult">
      <result property="allWheelDrive" column="all_wheel_drive" />
    </case>
  </discriminator>
</resultMap>
```

### 自动映射 ###
1. 如果你的属性和列名完全一致, 那么久可以自动映射
```
<select id="selectTeacher" resultType="org.xzc.learn.mybatis.entity.Teacher">
	select * from MB1_TEACHER where id = #{id}
</select>
```

2. 通常数据库列使用大写单词命名，单词间用下划线分隔；而java属性一般遵循驼峰命名法。 为了在这两种命名方式之间启用自动映射，需要将 mapUnderscoreToCamelCase设置为true。
	1. 属性 hashedPassword 对应 HASHED_PASSWORD

3. 自动处理完后还没被处理掉的
4. 可以在resultMap 上 使用 autoMapping=false 关掉自动映射

## sql ##
这个元素可以被用来定义可重用的 SQL 代码段，可以包含在其他语句中。它可以被静态地(在加载参数) 参数化. 不同的属性值通过包含的实例变化. 比如：

```
${expression} 这个表达式的话会原封不动的替换字符串

<sql id="sql1">
	${alias}.id,${alias}.username,${alias}.password
</sql>
<select ...>
	select
		<include refid="sql1">
			<property name="alias" value="u1"/>	
		</include>
		,
		<include refid="sql1">
			<property name="alias" value="u2"/>	
		</include>
	from USER as u1 left join USER as u2 on u1.parent_id = u2.id
</select>
```

## select ##
```
<select id="标识" resultType="hashmap">
	select * from XXX where id = #{id}
</select>
```
resultType="hashmap" 表明结果用一个HashMap来封装
resultMap 表明结果用一个<resultMap/>来映射
此外还有 超时 缓存等选项
上面两个不能同时使用

flushCache, 该语句被调用则清空本地缓存和二级缓存, 默认是false
useCache 该语句的结果进行二级缓存, 对于select, 默认是true

## insert/update/delete ##
支持的参数和上面的select大致一样
insert需要指明id, 和是否自增 
```
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</insert>
```

如果数据库支持多行插入的话, 那么:
```
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>
```

可以控制使用第三张表来放自增主键
使用 <selectKey/>, 大致的操作我懂, 但是怎么更新表???


## 缓存配置 ##
<cache/>
默认有如下的配置:
1. 缓存所有select语句, 是缓存语句本身还是结果??? TODO
2. insert update delete 等操作也会影响缓存
3. 缓存使用LRU算法来回收.
4. 根据时间表(比如 no Flush Interval,没有刷新间隔), 缓存不会以任何时间顺序 来刷新。
5. 缓存会存储列表集合或对象(无论查询方法返回什么)的 1024 个引用。
6. 缓存会被视为是 read/write(可读/可写)的缓存,意味着对象检索不是共享的,而 且 可以安全地被调用者修改,而不干扰其他调用者或线程所做的潜在修改。

支持的属性
1. eviction
	1. LRU(默认) FIFO LRU或FIFO算法
	2. SOFT WEAK 通过控制引用类型, 让gc来回收
2. flushInterval 毫秒, 默认没有设置, 即不生效
3. size, 默认1024, 最多可以缓存多少个对象(包括实体和列表集合)
4. readOnly 默认是false, 如果设置成只读的话, 那么被缓存的对象的引用就会直接返回给用户, 如果是false的话, 那么会通过序列化拷贝一个新对象进行返回

### 增删改查与缓存 ###
```
<select ... flushCache="false" useCache="true"/>
<insert ... flushCache="true"/>
<update ... flushCache="true"/>
<delete ... flushCache="true"/>
```

### 自定义缓存 ###
org.mybatis.cache.Cache 接口
<cache type="com.domain.something.MyCustomCache">
  <property name="cacheFile" value="/tmp/my-custom-cache.tmp"/>
</cache>

# 动态SQL #
如果不适用动态SQL的话, 就无法达到某些效果: 现在有10个where条件, 只有当用户有提供这些参数, 对应的条件才会被加入到最终的SQL里, 手动拼凑的话非常难!

## if ##
下面是几个例子, 可以通过在where添加一个 1 = 1 解决 第一个 and 的问题 
```
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```

## choose, when, otherwise ##
```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

## trim, where, set ##
这个可以用来消除我刚才提到的 需要在where前添加1==1的问题

下面的例子可以在where条件为空的时候, 不加入 "where" 到SQL, 并且他还可以消除对于的AND 或 OR
```
<where> 
<if test="state != null">
     state = #{state}
</if> 
<if test="title != null">
    AND title like #{title}
</if>
<if test="author != null and author.name != null">
    AND author_name like #{author.name}
</if>
</where>
```

set的用法
```
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```


trim比where和set更强大
```
下面的例子可以用于消除多余的AND或OR
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ... 
</trim>

<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```



## foreach ##
常用语构造 in 语句
```
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

## bind ##
```
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```

# SqlSessionFactory #
用来 openSession, 有多个重载版本, 需要考虑:
1. 是否需要事务支持? 隔离级别?
2. 是否自行提供 Connection 实例
3. 是否复用预处理的语句, 是否批量处理更新

默认的 openSession
1. 打开一个事务(也就是不自动提交)
2. 从数据库连接池获得 Connection
3. 使用驱动默认的隔离级别
4. 不复用预处理语句 也不会批量处理更新

没有接触过的参数是 ExecutorType
1. SIMPLE 不做任何特殊的事情
2. REUSE 复用预处理语句
3. BATCH 会批量执行所有更新语句

# SqlSession #
是我们最常直接用到的类

## select ##
1. selectOne
2. selectList 有一个分页版本
3. selectMap 跟 selectList 类似, 但是接受一个额外的mapKey用于表示用哪个属性作为key
4. selectCursor 返回一个cursor, 然后做遍历, 这个Cursor是一个迭代器
5. 还可以自己提供一个 ResultHandler, 这个目前感觉不是很有用

## insert/update/delete ##
一般只有两个重载版本, 接受 statement 和 parameter

## 提交 ##
commit 刷出批处理 并且 提交, 如果在这个连接没有调用过任何写操作的语句, 那么久不会提交
commit(force) 为true的话就强制提交, 即使你没有 写操作的语句

rollback 跟 commit 类似
rollback(force)

## 杂 ##
List<BatchResult> flushStatements()
将缓存的批处理刷出去
close
clearCache
getMapper
getConnection

# 用 annotation 的方式配置 Mapper #
写一个借口, 然后给方法添加各种annotation, 运行时会产生一个代理的实例, 帮你完成相应的工作.

@Results @Result
@Select
@ConstructorArgs @Arg
@Flush SqlSession#flushStatements()

不过由于 annotation 本身的限制, 它并不能提供和 基于xml配置一样强大的功能

annotation 和 xml 可以混用 但要保证 最终的限定名不冲突, 否则报错


# SQL语句构建起 #
http://www.mybatis.org/mybatis-3/zh/statement-builders.html
目前感觉还用不到, 因此先大概看一下就好, 等有需要用到再去学习.
可以让你以编程的方式生成SQL语句
当然它提供了一些方法, 帮助你构建 而不是纯粹通过拼凑字符串

# 日志 #
```
<setting name="logImpl" value="LOG4J"/>
```
当你调用 SqlSession 的方法的时候, 
会以Mapper的具体方法的名义记录日志
比如: DEBUG 2016-06-29 14:20:57 [main] org.xzc.learn.mybatis.entity.UserMapper.selectUser ==>  Preparing: select * from MB1_USER where USER_ID = ? 



# 纯JAVA的配置方式 #
```
DataSource dataSource = BaseDataTest.createBlogDataSource();
TransactionFactory transactionFactory = new JdbcTransactionFactory();

Environment environment = new Environment("development", transactionFactory, dataSource);

Configuration configuration = new Configuration(environment);
configuration.setLazyLoadingEnabled(true);
configuration.setEnhancementEnabled(true);
configuration.getTypeAliasRegistry().registerAlias(Blog.class);
configuration.getTypeAliasRegistry().registerAlias(Post.class);
configuration.getTypeAliasRegistry().registerAlias(Author.class);
configuration.addMapper(BoundBlogMapper.class);
configuration.addMapper(BoundAuthorMapper.class);

SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(configuration);
```
