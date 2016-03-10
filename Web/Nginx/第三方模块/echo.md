https://github.com/openresty/echo-nginx-module#readme
引入了echo sleep time exec等功能

1. echo
	1. 用法和bash的echo一样
	2. 默认会有换行
	3. 如果不需要换行则 echo -n text
2. echo_duplicate count string
	1. 重复输出count次string
	2. 不会有换行

3. echo_flush
	1. 将结果刷出去
	2. 在浏览器上可能没法先出一部分内容, 而是等了很久才全部出来
	3. 你可以配合echo_sleep和curl看效果
4. echo_sleep seconds
	1. 不会阻塞住整个工作者进程

5. echo_blocking_sleep seconds
	1. 会阻塞工作者进程
6. echo_reset_timer
	1. 重置计时器
7. $echo_timer_elapsed
	1. 查看经过的时间, 用于统计耗时
8.  echo_read_request_body
	1.  显示请求的body
9. echo_location_async location [args]
	1. 异步显示location对应的内容
	2. 比如同时执行 ..._async /u1;
	3. ..._async/u2; 那么谁先出结果是不知道的, 并且下一个的指令会马上执行, 不会阻塞住.
	```
	location /main {
	     echo_reset_timer;
	     echo_location_async /sub1;
	     echo_location_async /sub2;
	     echo "took $echo_timer_elapsed sec for total.";
	 }
	 location /sub1 {
	     echo_sleep 2; # sleeps 2 sec
	     echo hello;
	 }
	 location /sub2 {
	     echo_sleep 1; # sleeps 1 sec
	     echo world;
	 }
	```
10. echo_location
	1. 同上一个, 阻塞版本	
11. echo_subrequest_async method location -q args -b body -f body_file_path
	1. -q 指定url的参数
	2. -b 指定body内容
	3. -f 指定含有body内容的文件
12. echo_subrequest
	1. 同上一个, 阻塞版本
13. echo_foreach_split
```
location /loop {
 echo_foreach_split ',' $arg_list;
   echo "item: $echo_it";
 echo_end;
}
```
14. echo_request_body
	1. 显示请求的body
15. echo_exec location args
	1. 做一个内部的重定向
	2. echo_exec /search keyword=ceshi
16. echo_status
	1. 控制statusCode
17. echo_before_body
18. echo_after_body
	1. 这两个都支持-n和--选项

# 变量 #
$echo_timer_elapsed 经过的时间
$echo_request_body
$echo_request_method
$echo_client_request_method
$echo_request_uri
$echo_client_request_headers
$echo_it 迭代器
$echo_cacheable_request_uri
$echo_incr
$echo_response_status