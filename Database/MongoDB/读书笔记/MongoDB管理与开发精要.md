1. 对一致性要求不高
2. 实时读写要求高
3. 忌讳大表的关联查询
4. 最好限制在单表内


基于文档
MongdbDB
提供比较高的并发读写的情况下保证海量数据存储 保证良好查询性能

基于key/value
redis
Tokyo Cabinet/ Tokyo Tyrant
提供超高性能的并发读写

基于图形
Neo4J


运行在便宜的PC服务器集群上
没有过多的操作
比较好的性能

