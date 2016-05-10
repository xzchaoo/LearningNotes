# ActiveMQ的目标 #
提供一个就标准的 面向消息的程序整合 跨多种语言和平台是可能的

# Feature #
JMS, Connectivity(支持各种各样的协议), Pluggable persistence and security
Buiding messaging applications with Java, 和应用服务器整合, 提供客户端API
Broker集群

发送者不在乎如何分发, 接收者不在乎信息如何到来(协议等)

# Persistence #

kahaDB
http://activemq.apache.org/kahadb.html

mKahaDB
	m是multiple的意思

http://activemq.apache.org/leveldb-store.html

支持异步也支持同步
不同语言的支持 作为RPC的一个代替

# Selector #
客户端告诉MOM,它想要接受怎样的消息,在session.createConsumer(destination," MARK = 'XZC' ") 的时候指定
比如上面那个的筛选条件就是MARK='XZC'
selector表达式 字面值常量 常见运算符, header或property变量, 语法规则和SQL的where部分类似

# 支持的MessageBody #
Message 只有头
TextMessage 带简单的文本
MapMessage 键值对
BytesMessage byte[]
StreamMessage 流
ObjectMessage 对象

Queue
Topic publish subscribe
Durable subscriptions allow for subscriber disconnection without missing any messages.
队列里的消息只会被1个人取走
在Queue模式下,消息会被暂存在queue中,因此producer不必早于consumer启动, consumer也不会丢失信息
除非消息过期了


Toplic里的消息会发给所有订阅者



# 配置 #

## JMS broker ##
broker的角色是为客户端程序提供交流的设施 为此AMQ提供了 `connectors`,这是一个连通机制,提供客户端到broker的交流(使用transport connectors),和broker到broker的交流(使用network connectors)

## transport connectors for client-to-broker ##
tcp nio udp ssl http(s) vm
使用URI建立连接
tcp://localhost:61616
通过 transportConnectors 元素进行配置
```xml
<transportConnectors>
	<transportConnector name="openwire" uri="tcp://localhost:61616" discoveryUri="multicast://default"/>
	<transportConnector name="ssl" uri="ssl://localhost:61617"/>
	<transportConnector name="stomp" uri="stomp://localhost:61613"/>
	<transportConnector name="xmpp" uri="xmpp://localhost:61222"/>
</transportConnectors>
```
可以使用URI的query部分进行参数配置

## TCP ##
OpenWire	
tcp://hostname:port?key=value&key2=value2
<transportConnector name="tcp" uri="tcp://localhost:61616?trace=true"/>
优势:
高效 可用 可靠

## NIO ##
New I/O Java SE 1.4之后引进的 允许 selectors和非阻塞式IO编程
nio相对于tcp来说启动更少的线程
适用于:
	大量客户端要连接到broker
	连接到broker的网络很拥挤
nio://hostname:port?key=value
<transportConnector name="nio" uri="nio://localhost:61618?trace=true"/>

## UDP ##
不保证顺序 所以可能重复 乱序 丢包
适用于
	需要快速的相应 并且不太在意重复 乱序 丢包
	tcp被防火墙墙掉了,只能使用udp
udp://hostname:port?key=value
<transportConnector name="udp" uri="udp://localhost:61620?trace=true"/>

## SSL ##
TODO 还不太懂SSL
<sslContext/>
ssl://hostname:port?key=value
<transportConnector name="ssl" uri="ssl://localhost:61620?trace=true"/>

## HTTP/HTTPS ##
http://hostname:port?key=value
https://hostname:port?key=value
另外需要添加org.apache.activemq:activemq-optional这个依赖

## VM ##
vm://brokerName?key=value
要求客户端和broker就在一个JVM下
比如producer和broker就在同一个jvm下



# network connectors #
用于将一个broker接收到的消息发送给另外一个broker forwarding bridge
duplex connector 将两个broker双向连接起来

Static networks
	static:(uri1,uri2,...)	?key=value
```xml
<networkConnectors>
	<networkConnector name="local network" uri="static://(tcp://remotehost1:61616,tcp://remotehost2:61616)"/>
</networkConnectors>
```
failover:(uri1,uri2,...)?key=value




持久化的信息
非持久化的信息: 用于发送实时通知(没接到就算了)
Queue: 先进先出 一个消息只会被发送给一个consumer(当那个消息被consumed并且ack之后,就会从queue中删除该消息)
Topic:
	durable subscrber: 所有的订阅者都会收到消息,如果一个订阅者在一段时间内没有订阅,之后再订阅,那么这一段时间内发送的消息在他订阅之后会马上收到
	


需求分析 详细设计
在ftp上有一些资料

AMQ会为所有topic(除了temporary topic,ActiveMQ advisory topics)
queue会自己保存消息


需求分析 软件架构设计 详细分析 软件测试 设计
信息工程

软件工程
	计算机科学数学
	管理学
	工程学

信息系统的基本原理:
基本前提
	1. 数据位于现在数据处理的中心
	2. 数据是稳定的 处理是多变的
	3. 用户必须真正参加开发工作
三大部分:
	1. 企业信息系统战略规划的方法!!
	2. 信息系统设计实现的方法
	3.      

自顶向下的规划 自底向上的设计

企业模型

建立企业模型 建立实体关系图 建立主题数据库 子系统之间的数据流
CU矩阵: 块表示对应的过程都用到或产生同样的数据


JAAS


# 4 #
配置transportConnector 各种协议
	用于暴露接口
	
配置networkConnector
	用于将消息发送到别的broker
	比如A将消息发送到B,如果B处有一个人订阅了topic t,那么当有人在A端发布topic t的时候,B处订阅的人也会收到
	注意,默认情况下是单向的,并且只能传导一次,如果 A将消息发给B,B可以将消息发给C 表示为A->B->C
	那么A的消息不会发给C
可以通过
```xml
<amq:networkConnector
name="test1"
uri="static:(tcp://127.0.0.1:61617)"
duplex="true" />
```
duplex属性变成双向


在客户端配置failover可以让客户端连接到多个broker
failover:(uri1,uri2,...)?key=value
默认会随机选择一个uri进行连接,如果失败了就换别的,
如果对于同一个一直失败,那么重新连接它的时间间隔会变大,具体有参数可以控制
即使你只有一个uri,你也可以使用failover,这样就提供了重新连接的能力了(当然会阻塞住)

## Dynamic networks ##
配置的时候,使用discoveryUri="multicast://default",将这个transportConnector暴露到discoveryUri指定的地方
```xml
	<amq:transportConnector
	name="tcp"
	uri="tcp://127.0.0.1:61618"
	discoveryUri="multicast://default" />
```
然后在别的broker里面:
```xml
<amq:networkConnector
name="bt-to-others"
uri="multicast://default" />
```
这样就会自动搜索到别的broker,但是不能跨出路由器范围,
一旦有新的broker,通过discoveryUri暴露自己,它都会搜索到
一旦broker挂掉了,它也会知晓

一旦两台broker互相连接,就有可能消费彼此的信息

# 5 消息存储 #
对于Queue 并且producer是Persistent的
	A发送了10个消息到某个队列,然后服务器重启了,但是10个消息依然被保存住
	如果producer不是Persistent的,那么重启之后,消息就丢失了
	Persistent消息保证,一旦它被投递到broker,就一定会得到处理(除非超时了)

类型: 内存, 基于文件, 关系型数据库
Queue和Topic的存储机制不一样
Queue
	先进先出,一个消息会被发送给一个消费者,只有当这个消息被消费者acknowledged之后,它才会从broker的message store里删除,或显式过期
	
P98
>For durable subscribers to a topic, each consumer gets a copy of the message. In
order to save storage space, only one copy of a message is stored by the broker. A durable subscriber object in the store maintains a pointer to its next stored message and
dispatches a copy of it to its consumer as shown in figure 5.2. The message store is
implemented in this manner because each durable subscriber could be consuming
messages at different rates or they may not all be running at the same time.

## KahaDB ##
P101
基于文件 综合性能挺好
<amq:kahaDB directory="ddbb11" journalMaxFileLength="64mb">
这将会在$ACTIVEMQ_HOME$下建立一个文件夹ddbb11用于存放数据

## 基于JDBC ##
```xml
<amq:persistenceAdapter>
	<amq:jdbcPersistenceAdapter dataSource="#dataSource" />
</amq:persistenceAdapter>
<bean
	id="dataSource"
	class="org.apache.commons.dbcp.BasicDataSource"
	destroy-method="close"
>
	<property
		name="driverClassName"
		value="com.mysql.jdbc.Driver" />
	<property
		name="url"
		value="jdbc:mysql://localhost:3306/activemq?relaxAutoCommit=true" />
	<property
		name="username"
		value="root" />
	<property
		name="password"
		value="70862045" />
	<property
		name="maxActive"
		value="200" />
	<property
		name="poolPreparedStatements"
		value="true" />
</bean>
```
如果没有更多的配置,那将会自动建表

### 一个改进 Journal + JDBC ###
将上面的persistenceAdapter改成下面 将会为JDBC增加缓冲写功能 根目录的activemq-data将会用于jdbc的缓冲写数据的存放点
```xml
<persistenceFactory>
<journalPersistenceAdapterFactory
journalLogFiles="4"
journalLogFileSize="32768"
useJournal="true"
useQuickJournal="true"
dataSource="#derby-ds"
dataDirectory="activemq-data" />
</persistenceFactory>
```

## 5.6 直接在Broker处进行缓存 ##
	对于Topic,有些数据很快就过期了,或很快就会被消费,或者即使丢失了也不要紧,那么就不必放到数据库里,因为这样很慢
Broker会为有追溯能力的topic建立缓存,注意这个缓存级别是Broker的,如果你使用的是 主从JDBC模式 那么其他的broker是不会缓存的!
```java
Topic topic = session.createTopic("TEST.TOPIC?consumer.retroactive=true"); 这里千万不能少!
```
并且需要为Broker配置 subscriptionRecoveryPolicy
FixedSizeSubscriptionRecoveryPolicy 限制一个总的大小(总消息的大小而不是数量)
还有 FixedCount (限制数量) Query-Based 在一定时间内的
还有1个叫做  LAST IMAGE , 就是会记录住最后的一条msg对于每个topic
配置方式:通过destinationPolicy
```xml
<amq:destinationPolicy>
	<amq:policyMap>
		<amq:policyEntries>
			<amq:policyEntry topic="t.b1112.>">
				<amq:subscriptionRecoveryPolicy >
					<amq:lastImageSubscriptionRecoveryPolicy />
				</amq:subscriptionRecoveryPolicy>
			</amq:policyEntry>
		</amq:policyEntries>
	</amq:policyMap>
</amq:destinationPolicy>
```

# 6 安全 #
通过插件实现
简单的验证 和 JAAS验证
编写插件: 2个实现类 BrokerPlugin 和 BrokerFilter
	然后在xml文件里面配置plugin 并且将jar包放入 activemq的lib

# 7 内嵌 #
TempQueue
和Spring整合 P165
JmsTemplate DefaultMessageListenerContainer
amq命名空间 amq:topic,queue,connectFactory
JmsTemplate
	send receive
消息驱动Bean
jms空间


## Spring JMS ##

>The package org.springframework.jms.support provides JMSException translation functionality.
The package org.springframework.jms.support.converter provides a MessageConverter abstraction to convert between Java objects and JMS messages.
The package org.springframework.jms.support.destination provides various strategies for managing JMS destinations, such as providing a service locator for destinations stored in JNDI.
The package org.springframework.jms.annotation provides the necessary infrastructure to support annotation-driven listener endpoints using @JmsListener.
The package org.springframework.jms.config provides the parser implementation for the jms namespace as well the java config support to configure listener containers and create listener endpoints.
Finally, the package org.springframework.jms.connection provides an implementation of the ConnectionFactory suitable for use in standalone applications. It also contains an implementation of Spring’s PlatformTransactionManager for JMS (the cunningly named JmsTransactionManager). This allows for seamless integration of JMS as a transactional resource into Spring’s transaction management mechanisms.

JmsTemplate是核心(线程安全) 利用MessageCreator对于一个给定的Session创建Message
JmsMessagingTemplate建立在JmsTemplate之上
convertAndSend receiveAndConvert
SimpleMessageConverter supports conversion between String and TextMessage, byte[] and BytesMesssage, and java.util.Map and MapMessage
MessagePostProcessor
@JmsListener
SessionCallback and ProducerCallback

SessionAwareMessageListener相比MessageListener可以知道Session

利用 MessageListenerAdapter 可以创建一个POJO监听器

# 8 与应用服务器整合 #
JNDI jee命名空间
jee:jndi-lookup
<Resource/>配置
<resource-ref/>配置
jms-webapp



# 第10章 #
## 高可用性 ##
### 主从模式 ###
不共享(这个模式在最近版已经被移除了,因为不合适)
	http://activemq.apache.org/pure-master-slave.html
	master收到的msg都会仍一份到slave,然后再继续处理这个msg,如果master挂了,那么slave可以选择关闭自己或称为master(在此之前slave不会暴露任何网络接口)
	slave只能有1个,slave不能再有salve
	在数据存储的时候会慢一点,因为数据同时需要给slave发一份
	The shared nothing broker configuration should only be used when you want to ensure that you don’t lose
	messages for your application, but you can afford to have some down time to attach a
	new slave after the master has failed and the old slave has become the master
	master挂了之后slave 要么 1. 取代master 2.安全关闭自己
	配置方式:
		在客户端使用failover://(tcp://master:61616,tcp://slave:61617)?randomize=false
		
		
共享
	master和slave必须共享数据存储(通过文件或DB)

## JDBC Master Slave ##
http://activemq.apache.org/jdbc-master-slave.html
	配置方式 只要所有的master/slave都使用同一个数据库即可
```xml
<amq:persistenceAdapter>
		<amq:jdbcPersistenceAdapter dataSource="#dataSource" />
	</amq:persistenceAdapter>
	<bean
		id="dataSource"
		class="org.apache.commons.dbcp.BasicDataSource"
		destroy-method="close"
	>
		<property
			name="driverClassName"
			value="com.mysql.jdbc.Driver" />
		<property
			name="url"
			value="jdbc:mysql://localhost:3306/a1?relaxAutoCommit=true" />
		<property
			name="username"
			value="root" />
		<property
			name="password"
			value="70862045" />
		<property
			name="maxActive"
			value="200" />
		<property
			name="poolPreparedStatements"
			value="true" />
	</bean>
```
谁能成功拿到数据库的排它锁,就会成为master
客户端的publisher和consumer使用,failover://(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=false
这些服务器之间不需要有额外的互相连接的配置

## Network Connector ##
一般是单向 可以使用duplex属性变成双向 或者从另外一方再单向连接过来

### 组播技术 multicast ###
缺点: 无法控制哪个broker可以被发现 基本上是在局域网段 因为无法跨出路由器
### 静态 ###
uri="static:(tcp://remote-master:61617,tcp://remote-slave:61617)"/>

这些参数要加在uri上

属性|默认值|描述
:--|:--|:--
initialReconnectDelay|1000|重新连接间隔的时间,当useExponentialBackOff为false时生效
maxReconnectDelay|30000|最大的重新连接时间,当useExponentialBackOff为true时生效
useExponentialBackOff|true|连接失败后,下一次的重新连接间隔时间会增大
backOffMultiplier|2|每次增大的时间是抢一次的2倍

这些参数用xml的属性来指定

属性|默认值|描述
:--|:--|:--
dynamicOnly|
prefetchSize|1000|影响发送给forwarding consumer的消息
conduitSubscriptions||
decreaseNetworkConsumerPriority|false|降低通过其他网络Consumer的优先级
路径排除/包含|0|
networkTTL|1|好像是用于信息可以转几次

excludedDestinations
staticallyIncludedDestinations,
dynamicallyIncludedDestinations

对于A->B,B会将自己的订阅消息发送给A,然后A根据B的订阅消息,代替B在自己处订阅消息,一旦有消息就转发给B,这个过程需要通过broker之间的advisory来实现
注意broker的name要唯一

advisorySupport需要被启动

## 10.3 Vertical Scaling 提升单个broker的性能 ##
1. 使用nio
2. 并且将系统属性org.apache.activemq.UseDedicatedTaskRunner设置为false, 启动线程池
3. 保证有操作系统系统, jvm
ACTIVEMQ_OPTS="-Xmx1024M \
-Dorg.apache.activemq.UseDedicatedTaskRunner=false"
4. 保证ActiveMQ有足够的内存
```xml
<systemUsage>
	<systemUsage>
		<memoryUsage>
			<memoryUsage limit="512 mb"/>
		</memoryUsage>
		<storeUsage>
			<storeUsage limit="10 gb" name="foo"/>
		</storeUsage>
		<tempUsage>
			<tempUsage limit="1 gb"/>
		</tempUsage>
	</systemUsage>
</systemUsage>
```
5. 如果使用的是OpenWire禁用tight encoding, 这个比较耗CPU
String uri = "failover://(tcp://localhost:61616?"
+ wireFormat.tightEncodingEnabled=false)";
6. 默认情况下, queue 使用一个单独的线程来 paging messages from the message store into the queue to be dispatched to interested message consumers. 当queue数量比较多的时候,推荐将optimizeDispatch=true设置到所有queue上
```xml
<destinationPolicy>
<policyMap>
<policyEntries>
<policyEntry queue=">" optimizedDispatch="true"
/>
</policyEntries>
</policyMap>
</destinationPolicy>
```
7. 使用JDBC 或 KahaDB

## Horizontal Scaling ##


# 11 #
## 11.1 通配符 ##
*代表1个占位符, >代表0个或多个占位符
```
*.*.xzc
a.b.>
```
这样就可以从多个地点接受消息
但是发布消息的时候不能使用这种格式,而是要使用复合目的地
Queue ordersDestination = session.createQueue("store.orders, topic://store.orders");
这样消息发送的时候也会同时发布到另外一个topic上

## 11.2 Advisory Messages ##
这些消息是用于broker之间交流用的(通过topic的形式),它也可以用于向客户端发送broker本身的状态信息

每个AM,有如下属性
originBrokerId,OriginBrokerName,OriginBrokerURL
AdvisorySupport里有一些定义好的常量和辅助方法
帮助你构造出你想要的AdvisoryTopic的地址

有一些advisory默认是不会发送的,
比如消息传递,慢consumer,快producer
需要启动:
```xml
<destinationPolicy>
<policyMap><policyEntries>
<policyEntry queue=">" advisoryForSlowConsumers="true" />
</policyEntries></policyMap>
</destinationPolicy>
```

connection start/stop
queue created/destroyed
消息超时
慢 queue consumer
快 queue producer
producer start/stop on a queue
producer start/stop on a topic
...


## 11.3 虚拟Topic ##
企图将topic和queue的性质结合起来
比如我们想要发消息给所有人(topic的性质),但是想保证所有人都接受得到或者说该信息要得到持久化(queue的性质)

durable subscriber to a topic
1. 使用一个唯一的jms client id 和 subscriber name
2. 对于一个给定的jms client id 和 subscriber name, 只有一个消费者可以进行消费
3. 当一个这样consumer意外挂了,没办法fail over到其他的consumer上
4. 并且无法做load balance
 
使用queue
1. 可以fail over到别的consumer上
2. 容易load balance
3. 还可以使用消息组机制

虚拟Topic
	可以让发布者发布消息到topic上,而消费者通过queue进行消费
	虚拟的topic,必须形如VirtualTopic.<topic name>
	而消费者要连接到queue:   Consumer.<consumer name>.VirtualTopic.<topic name>
	
	当发布一个消息到topic上的时候,该消息会被转发到多个queue上(这些queue根据consumer name来区分)
	假设我发布了3个消息到topic abc上,
	那么队列Consumer.foo1.VirtualTopic.abc和队列Consumer.foo2.VirtualTopic.abc就都会有3个消息
	然后对于每个队列你就可以采用queue的方式去消费它,这样就是可以让多个consumer去消费它
	
## 11.4 ##
	如果要求信息被快速消耗,那么请关闭persistence,缺点是可能会丢失消息
	AMQ有能力可以缓存一定数量的消息在一个topic上
	配置,consumer需要说自己是具有追溯能力的,broker也需要有相应的RecoveryPolicy策略,这个缓存级别是在broker的,并不能跨broker
	
### 让一个consumer有追溯能力 ###
session.createTopic("soccer.division1.leeds?consumer.retroactive=true");
默认情况下一个topic可以缓存多少数据是由fixedSizedSubscriptionRecoveryPolicy这个策略决定的
```xml
<destinationPolicy>
<policyMap>
<policyEntries>
<policyEntry topic=">">
<subscriptionRecoveryPolicy>
Message redelivery and dead-letter queues 287
<fixedSizedSubscriptionRecoveryPolicy maximumSize="8mb"/>
</subscriptionRecoveryPolicy>
</policyEntry>
</policyEntries>
</policyMap>
</destinationPolicy>
```

## 11.5 ##
当一个消息过期的时候 它会被移动到 dead-letter queue(DLQ), 以便可以让管理员进行查看
当消息进入DLQ,会有一个 Advisory消息被发出
信息会重新被发送到client,如果
	1. consumer在事务中,使用rollback
	2. consumer在事务中,没有提交就关闭
	3. consumer在CLIENT_ACKNOWLEDGE模式中,使用session.recover()

可以配置
	1. broker在重新发送某个消息之前要等待多久
	2. 每次等待的时间是否增加
	3. 超过多少次重新发送失败,就将该消息扔入 dead-letter queue.
```java
RedeliveryPolicy policy = connection.getRedeliveryPolicy();
policy.setInitialRedeliveryDelay(500);
policy.setBackOffMultiplier(2);
policy.setUseExponentialBackOff(true);
policy.setMaximumRedeliveries(2);
```
默认情况下,dead-letter queue(DLQ)只有一个, 可以用于所有的消息
可以配置不同的消息使用不同的DLQ
```xml
<destinationPolicy>
	<policyMap>
		<policyEntries>
			<policyEntry queue=">">
				<deadLetterStrategy>
					<individualDeadLetterStrategy
					queuePrefix="DLQ."
					useQueueForQueueMessages="true"
					processExpired="false"
					processNonPersistent="false"/>
				</deadLetterStrategy>
			</policyEntry>
		</policyEntries>
	</policyMap>
</destinationPolicy>
```



## 11.6 通过拦截器插件扩展功能 ##

## 11.7 Apache Camel ##


# 12 #
## 12.1 排他的Consumer ##
queue = new ActiveMQQueue("TEST.QUEUE?consumer.exclusive=true");

可以用于实现分布式锁:
想一下有10个人要对同一个资源进行修改
那么只有获得这个排他队列的所有权的人可以进行修改,这就实现了分布式锁的功能
配置 首先是SESSION必须是Session.CLIENT_ACKNOWLEDGE的
先把目的地构造出来
Queue q = this.session.createQueue("q?consumer.exclusive=true");
然后建立一个producer往q里扔一个空消息, 空消息只是作为一个信号而已
s.createProducer(q).send(s.createMessage())
然后自己再去订阅该queue,
mc=s.createConsumer(q);
mc.setListener({
	能收到消息 表明你现在已经获得了锁
	当你确定你处理完了之后
	你需要message.acknowledge();表明你已经处理完毕(这样你但你处理失败,该消息依旧存在,下一次仍会被处理一次)
	然后就是停止订阅该消息:
	mc.close();
})
如果你的情况简单一些的话,也可以不是用CLIENT_ACKNOWLEDGE,自己想想这会造成什么问题,如果这些问题你能容忍,那么就可以使用.
 
## 12.2 消息组 ##
通过JMSXGroupID指定一个id
组id相同的会被扔给同一个consumer,
注意如果组数量大于consumer的数量,那么会将组尽量平均分配给每个consumer
如果这个consumer挂了,那么任务会被发给另外一个人处理
在发送消息的时候指定组名,consumer不要配置排他属性
```java
	tm.setStringProperty( "JMSXGroupID", "g" + ( i % 2 ) );
	mp.send( tm );
```
于此同时ActiveMQ的broker会自动给具有组功能的消息一些额外的属性:
JMXSGroupSeq, 从1开始的int, 表示注释第几个该组的消息

```xml
<destinationPolicy>
<policyMap>
<policyEntries>
<policyEntry queue=">"
consumersBeforeDispatchStarts="2"
timeBeforeDispatchStarts="5000"/>
</policyEntries>
</policyMap>
</destinationPolicy>
```
使用上面的代码可以达到这样的效果:
	1. 所有队列在它的消费者数量没有达到2之前是不会进行消息派送的
	2. 一旦超过5000毫秒还没有开始派送,那么就强制开始派送


## 12.4 Blob ##
这是ActivemMQ的一个增强
必须使用ActiveMQ的类
ActiveMQSession session = (ActiveMQSession)
connection.createSession(...);
BlobMessage message =
session.createBlobMessage(...);

## 12.5 failover ##
默认是随机选的,如果不需要,则randomize=false
通过ActiveMQConnection.addTransportListener进行监听
可以在uri上指定的参数
useExponentialBackOff
backOffMultiplier=1.5
initialReconnectDelay=1000
maxReconnectDelay

failover:(
	tcp://host1:61616?wireFormat.maxInactivityDuration=1000,
	tcp://host2:61616?wireFormat.maxInactivityDuration=1000
)
非持久化信息 + failover的trackMessages=true + maxCacheSize

使用failover, 当一个broker失败之后, 会自动连接到另外一个broker
但是这个过程也是需要花费一定时间的
使用backup=true,backupPoolSize=2 可以准备连个备用的broker

updateClusterClients on the TransportConnector



## 12.6 ##
延迟 重复 定时 CRON表达式
ScheduledMessage
MessageProducer producer = session.createProducer(destination);
TextMessage message = session.createTextMessage("test msg");
long delayTime = 5 * 60 * 1000;
message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
producer.send(message);

# 13 优化技巧 #
1. 尽量使用nonpersistent,这个需要显式指定
2. 使用事务,做大批量处理
3. 内嵌broker,提高传输速度 vm://...
4. cf.setCopyMessageOnSend(false)
5. tightEncodingEnabled
6. cf.setUseAsyncSend(true);
7. cf.setAlwaysSessionAsync(false);
8. cf.setOptimizeAcknowledge(true);
9. 适当prefetchSize,Topic topic = session.createTopic("test.topic?consumer.prefetchSize=32766");


## nonpersitent ##
发送消息的时候,消息默认是persistent的
nonpersitent消息是异步发送的 不用等待broker返回确认消息
并且由于不用存储到 message store 因此更快
mp.setDeliveryMode( DeliveryMode.NON_PERSISTENT );

## 事务 ##
Session session =
connection.createSession(true, Session.SESSION_TRANSACTED);
session.commit() rollback recover


```java
//		Session s = c.createSession( false, Session.AUTO_ACKNOWLEDGE );
Session s = c.createSession( true, Session.SESSION_TRANSACTED );

//Queue q = s.createQueue( "q.b1112" );
Topic t = s.createTopic( "t.b1112" );
MessageProducer mp = s.createProducer( t );
mp.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
for (int i = 0; i < 10; ++i) {
	String text = "消息" + i;
	System.out.println( "发送消息 : " + text );
	mp.send( s.createTextMessage( text ) );
}
Thread.sleep( 3000 );
s.commit();
```
Producer flow control
当有太多的消息没有被消费的时候就会阻塞生产者
这个功能应该被启动以防止服务器被填满
这个功能对于持久化信息默认是启动的
但是对于 异步发送的信息(包括持久信息和费池就信息,或连接被配置为sendAsync)需要显式指定
几个可以调的参数	
1. producerWindowSize, cf.setProducerWindowSize(1024000);
	生产生的send buffer达到多大的时候,就必须等待服务器的通知说允许生产者继续生产
2. 2

```xml
<destinationPolicy>
<policyMap>
<policyEntries>
<policyEntry topic="FOO.>"
producerFlowControl="false"
memoryLimit="10mb" />
</policyEntries>
</policyMap>
</destinationPolicy>
```
当把这个功能关闭的时候,慢消费者的消息就会被存放到temporary storage

```
<systemUsage>
<systemUsage sendFailIfNoSpace="true" sendFailIfNoSpaceAfterTimeout="5000">
<memoryUsage>
<memoryUsage limit="128 mb"/>
</memoryUsage>
</systemUsage>
</systemUsage>
```


## 对于消费者 ##
## 13.3 Prefetch Limit ##
prefetchSize用于控制多少信息可以被发送到消费者在broker得到一个ack之前.
大概的情况是这样的:
	broker会提交将一些msg发送给一个consumer,
	然后这个consumer处理完一个msg之后就要给服务器发送一个ack
	如果你使用auto_ack... 那么这个过程就不需要你来操作
	如果你没有返回ack(或其他操作,显式表明你操作失败), 那么过了一段时间broker认为你处理失败,那么这个消息再被发送给其他的consumer去处理,此时,那些被提前拿到的msg就浪费时间了
	一个consumer可以提前拿到多少msg,是由prefetchSize决定的
	如果一个 慢consumer提前拿到了很多msg, 那还不如给快consumer去处理
	如果一个consumer提前拿到一批msg,然后成功处理完第1个之后就关闭了订阅,那么剩下的的未处理的msg就会由broker再去发送给别人处理
	因此一个queue的消息的处理顺序不一定按照它的进队顺序
	
```
这是数量不是总的大小
There are different default prefetch sizes for different consumers:
 Queue consumer default prefetch size = 1000
 Queue browser consumer default prefetch size = 500
 Persistent topic consumer default prefetch size = 100
 Nonpersistent topic consumer default prefetch size = 32766
```
设置的方式1
```
ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
Properties props = new Properties();
props.setProperty("prefetchPolicy.queuePrefetch", "1000");
props.setProperty("prefetchPolicy.queueBrowserPrefetch", "500");
props.setProperty("prefetchPolicy.durableTopicPrefetch", "100");
props.setProperty("prefetchPolicy.topicPrefetch", "32766");
cf.setProperties(props);
```
设置的方式2
```
Queue queue = new ActiveMQQueue("TEST.QUEUE?consumer.prefetchSize=10");
MessageConsumer consumer = session.createConsumer(queue);
```

optimizeAcknowledge和Session.DUPS_OK_ACKNOWLEDGE可以一次返回 "我已经成功处理了一批msg" 这样的话提前拿n个msg, 就不一定要返回n
个ack了,可以返回更少一点的ack

Session.DUPS_OK_ACKNOWLEDGE会发送ack,当prefetch达到了50%满的程度, 这是最快的消费信息的标准方式
optimizeAcknowledge与AUTO_ACKNOWLEDGE搭配使用, 65%满的时候才发ack

这些批量ack的缺点就是可能信息丢失 场景自己想...

## 13.3.3 Asynchronous dispatch ##
好像是这个意思:消息到达客户端的额时候,会被扔入session里的一个队列,然后session会有一个线程不断去消费这个队列,将消息发送给相应的consumer
意思就是说一个session,每时每刻只能有一个消费者在消费. 因为他们都是使用同一个线程来处理的.

setAlwaysSessionAsync(false)
	this allow messages to be passed directly from the transport to the message consumer.
	就是消息到的时候直接去处理,而不是扔到队列再去处理?
	好处就是

更多优化策略见13章