http://www.redis.io/topics/mass-insert
1. 普通方法大量插入数据不行
2. 要用Generating Redis Protocol


用如下方式编码你的操作
编码方式见: http://www.redis.io/topics/protocol
*<args><cr><lf>
$<len><cr><lf>
<arg0><cr><lf>
<arg1><cr><lf>
...
<argN><cr><lf>

*3<cr><lf>
$3<cr><lf>
SET<cr><lf>
$3<cr><lf>
key<cr><lf>
$5<cr><lf>
value<cr><lf>

"*3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n"

ruby proto.rb | redis-cli --pipe
然后喂给 redis-cli --pipe
1. redis-cli --pipe 尽量快的发送数据给服务器
2. 当它收到返回时, 就尝试解析它
3. 当数据发送完毕时, 它会发送一条 echo 20个字节的随机字符串
4. 当它收到返回里有一个我们刚才发的随机字符串后, 就认为我们已经完成了
5. 并且顺便可以统计一下我们发了多少个请求