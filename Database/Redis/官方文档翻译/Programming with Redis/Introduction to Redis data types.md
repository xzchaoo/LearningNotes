# An introduction to Redis data types and abstractions #
http://www.redis.io/topics/data-types-intro
Redis的List是LinkedList
使用一个lpush + ltrim可以实现保留最新的n个xx这样的要求, 可能要考虑使用事务, 其实不用事务也行

当对一个聚合的数据结构使用添加操作的时候, 如果数据结构不存在会进行创建, 如果数据结构变成空的话 那么会自动删除它对应的key

abc:1 可以作为key名
因此你可以这样设计你的key名
user:100
topic:101
