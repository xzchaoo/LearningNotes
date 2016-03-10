# http://www.redis.io/commands/expire #
1. 对一个key设置一个时间, 一旦超时, 这个key就被移除
2. del set getset 和所有的 *store 方法会导致这个key上的timeout被移除
3. incr lpush等操作不会移除timeout
4. rename from to 之后, to的timeout是原来from的timeout
5. 再次执行expire之后将会重新设置超时时间
6. persist 将会删除超时时间

1. 一个key会在被访问的时候检查它是否超时
2. 每秒做10次如下操作:
	1. 随机选择20个带有ttl标记的key
	2. 删除超时的key
	3. 如果超时的key超过25%, 再次执行1
当一个key超时的时候, 会在AOF里自动添加一个del操作.
