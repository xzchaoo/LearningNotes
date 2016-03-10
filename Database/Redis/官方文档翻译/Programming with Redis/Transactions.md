# Transactions #
http://www.redis.io/topics/transactions
multi exec discard watch unwatch
1. 一个事务里的命令整体具有原子性
2. 无法回滚

## 用法 ##
watch ticket //监控ticket的值
watch ...
multi //一旦启动, ticket的值会被记下来
语句1;
语句2;
语句3;
desc ticket
...
exec //一旦执行, 会比对此时此刻的ticket的值于multi时记录的值是否一样, 如果不一样就会出现一个错误
exec执行之后会自动unwatch
假设语句3是错误的, 比如对一个不是数值的字符串执行incr;
那么语句1和语句2是无法回滚的.
如果在exec之前就发生错误了(比如你语法错误), redis会记住, 当你exec的时候, redis会不让你执行, 而是让你discard

