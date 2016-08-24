# TODO #
弄清楚spring data redis的count是如何实现的

#  #
RedisConnection 连接到redis的接口
RedisConnectionFactory 产生 连接的工厂
	它同时还作为 PersistenceExceptionTranslator 

RedisTemplate 线程安全, 可以被共享使用
默认它是使用java的serializer机制

因为大多情况下 key/value 都是字符串
因此提供了一个简单的模板 StringRedisTemplate
如果有特殊需求也可以使员工 execute 拿到比较底层的方法

使用StringSerializer的话, 对象会被保存成如下的格式

# StringSerializer #
序列化器
基于字符串, 基于jdk自带的, 基于xml 基于json


# 仓库 #
```
@RedisHash("persons")
public class Person {
  @Id String id;
  String firstname;
  String lastname;
  Address address;
}
```

RedisMappingContext 
@Indexed

@TimeToLive

@Reference

public interface PersonRepository extends CrudRepository<Person, String> {
  List<Person> findByFirstname(String firstname);
}

但这要求Person的firstname属性必须有@Index

Query methods for Redis repositories support only queries for entities and collections of entities with paging.

它支持的关键字也比较少
and or is/equals

Define and pin keyspaces via `@RedisHash("{yourkeyspace}") to specific slots when using Redis cluster.

做集群的时候, 默认是使用你的key作为哈希的依据, 但这样会导致 users:1 users:2 ... 基本上都被分布到不同的槽里去了
这样的话, 做某些操作就需要遍历所有的槽, 这样会有性能损失
redis允许你指定进行哈希的关键字, 比如你的key 长这样:  {users}:1 这样 "users" 就被认为是哈希的关键字


# 事务 #
默认情况下 Template 不启动事务支持
你需要手动
setEnableTransactionSupport(true)


# TODO #
Redis Sentinel Support
RedisSerializer 
StringRedisSerializer and the JdkSerializationRedisSerializer
JacksonJsonRedisSerializer


发消息
// send message through connection RedisConnection con = ...
byte[] msg = ...
byte[] channel = ...
con.publish(msg, channel); // send message through RedisTemplate
RedisTemplate template = ...
template.convertAndSend("hello!", "world");

At the low-level, RedisConnection offers subscribe and pSubscribe methods that map the Redis commands for subscribing by channel respectively by pattern. 
但这似乎会直接导致阻塞

实现接口 MessageListener

RedisMessageListenerContainer
它用于接受信息, 然后将信息传递给给相应的listener
它还会负责这些操作相关的线程处理
资源获得和释放
允许线程共享 TaskExecutor
动态改变配置


MessageListenerAdapter
public class DefaultMessageDelegate implements MessageDelegate {
  // implementation elided for clarity...
}



<bean id="messageListener" class="org.springframework.data.redis.listener.adapter.MessageListenerAdapter">
  <constructor-arg>
    <bean class="redisexample.DefaultMessageDelegate"/>
  </constructor-arg>
</bean>

<bean id="redisContainer" class="org.springframework.data.redis.listener.RedisMessageListenerContainer">
  <property name="connectionFactory" ref="connectionFactory"/>
  <property name="messageListeners">
    <map>
      <entry key-ref="messageListener">
        <bean class="org.springframework.data.redis.listener.ChannelTopic">
          <constructor-arg value="chatroom">
        </bean>
      </entry>
    </map>
  </property>
</bean>



  <bean id="queue" class="org.springframework.data.redis.support.collections.DefaultRedisList">
    <constructor-arg ref="redisTemplate"/>
    <constructor-arg value="queue-key"/>
  </bean>


默认情况下使用的是java的序列化机制

# RedisTemplate #

# 支持类 #

# Spring Cache 整合 #
<!-- declare Redis Cache Manager -->
<bean id="cacheManager" class="org.springframework.data.redis.cache.RedisCacheManager" c:template-ref="redisTemplate"/>

# Data #
@RedisHash
@TimeToLive
@Id
@Indexed
@Reference

# 支持二级索引 #