http://www.redis.io/topics/indexes

- 可以用排序集合创建一个二级索引 by id或其他的数值字段
- Sorted sets with lexicographical ranges for creating more advanced secondary indexes, composite indexes and graph traversal indexes.
- Sets for creating random indexes.
- Lists for creating simple iterable indexes and last N items indexes.

Implementing and maintaining indexes with Redis is an advanced topic, so most users that need to perform complex queries on data should understand if they are better served by a relational store. However often, especially in caching scenarios, there is the explicit need to store indexed data into Redis in order to speedup common queries which require some form of indexing in order to be executed.

假设你用一个hash来存放用户的数据, 用用户的id作为key
1. 那么根据key(用户id)来检索用户是很快的
2. 如果想要根据邮箱来检索用户, 可以这样做:
	1. 再建立一个哈希, 这是 邮箱地址->用户id 的映射
	2. 然后就可以间接达到想要的效果了
3. 假设每个用户都有一个score
	1. 如果想根据score来检索用户的话, 比如查询某一分数段的人
	2. 可以用有序集合 建立一个 (score,member)=(score,userid) 的有序集合
	3. 然后就可以用范围查询 zrangebyscore 之类的方法 返回用户的id, 然后再用id去找用户
	4. 如果用户的score发生变化, 记得要更新一下有序集合
	5. 可以用事务来包装这样一个变化


有序集合的score支持的范围
However what is interesting for indexing purposes is that the score is always able to represent without any error numbers between -9007199254740992 and 9007199254740992, which is -/+ 2^53.


多维排序


# Lexicographical indexes #
当score相同的时候, 就会让member以按lex排序 , 使员工的是memcmp函数, 每一位byte进行比较


ZRANGEBYLEX myindex "[bit" "[bit\xff"
可以找出所有以bit开头的member

可以让member携带额外的信息 比如 member以这样的格式: key:name
如果想让member支持类似数值的排序, 那么需要补零, 并且长度固定
对浮点数也是这样

复合索引
保证每个单个索引都是长度固定的, 然后用:隔开, 比如我的索引是 int,int,int
假设每个int都是4个长度, 那么 1234:6547:9963:value 这样的格式
其中value是你的值, 1234:6574:9963是你的复合key, 一般来说value是一个对象的id
将它以score=0, 加入到一个有序集合里

为了防止一个对象的索引每次都要拼凑
你可以将它的索引保存起来, 比如用一个哈希
id->index

MULTI
ZADD indexToUserId 0 0056:0028.44:90
HSET userIdToIndex 90 0056:0028.44:90
EXEC

user哈希存放 userid -> user
userIdToIndex 存放 userid -> userindex
indexToUserId 存放 userindex -> userid

要存储认识关系, 假设认识关系是单方向的
a认识b, 但b不一定认识a
人给两个人a和b, 问
1. a是否认识b
2. b是否认识a
3. 所有a认识的人
4. 所有认识b的人

1. 问题1和2的解决方法很简单, 如果a认识b的话就将 a:b 放到一个集合里就行
2. 问题3和4, 可以这样解决, 用一个有序列表A保存 a:b 表示a认识b, 用另一个有序列表B保存 a:b 表示b认识a
	1. 3的回答是 zrangebylex A "[a:" "[a:\xff"
	2. 4的回答是 zrangebylex B "[a:" "[a:\xff"
	3. 你可以考虑将ab两个集合合并起来, 就是在最前面再加个前缀用于区分, 比如
		1. A:a:b 表示a认识b
		2. B:a:b 表示b认识a
		3. 3的回答是: zrangebylex C "A:[a:" "A:[a:\xff"
		4. 4的回答是: zrangebylex C "B:[a:" "B:[a:\xff"
		5. 当然你不要起名字诸如 A B C, 换一点有意义的名字, 这里只是解说方便

多维索引和复合索引不一样
多维索引是二维的, 符合索引还是一维的
多维索引可以进行矩形范围查找

给你平面的1w个点, 问你介于 (0,0)->(100,50) 这个矩形内的点有几个?或列出他们?
...