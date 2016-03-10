http://nginx.org/en/docs/http/ngx_http_core_module.html

1. aio
	1. aio off;
	1. 是否使用异步IO
	2. 具体在看下配置
2. alias [location]
	1. 将location的参数与alias指定的目录对应起来
3. chunked_transfer_encoding
	1. 是否支持chunked
4. client_body_buffer_size
	1. 客户端请求的body的buffer
	2. 当请求太大的时候就会被写入到临时文件
	3. 默认=2倍 内存页
5. client_body_in_file_only
	1. 是否要将client的body写入到文件
6. client_body_in_single_buffer
	1. 是否将整个body保存在单个buffer里
	2. 当使用$request_body变量的时候就有帮助了, 不然的话到时还要读磁盘
7. client_body_temp_path
	1. 指定一个临时目录
	2. client_body_temp_path path [level1 [level2 [level3]]]
8. client_body_timeout
	1. 读取body的超时时间
	2. 408 Request Time-out
9. client_header_buffer_size
	1. 默认是1KB
	2. 但是如果客户端带有比较大的头的时候, 就会使用large_client_header_buffers
0. large_client_header_buffers
	1. 如果还超过就414 Request-URI Too Large, 或400 Bad Request
10. client_header_timeout
	1. 类似client_body_timeout
11. client_max_body_size
	1. body的最大大小, 默认是1MB
	2. 413 Requset Entity Too Large
12. default_type
	1. 默认的mime类型
13. directio
	1. 当要读取一个大小超过size的文件的时候, 会采用某种特殊的方式进行读取
14. directio_alignment
15. disable_symlinks
	1. 符号链接是否被当成文件, 默认是关的
16. error_page
	1. error 400 401 402 403 = /40x.html
		1. 意思是当发生了400等错误的时候, 就将页面重定向到 /40x.html, 并以/40x.html的statusCode作为返回的statusCode
		2. 如果写=号, 那么statusCode不变
	2. error 400 401 402 403 =302 /40x.html
		1. 还可以这样主动指定statusCode
	3. 还可以这样用
		```
		location / {
		    error_page 404 = @fallback;
		}
		location @fallback {
		    proxy_pass http://backend;
		}
		```
- if_modified_since [h, s, l] exact
	1. 当服务端带来一个If-Modified-Since的时候, 该如何处理
	2. off 忽略
	3. exact 要正好匹配
	4. before 文件的时间<= If-Modified-Since的时间就行

- ignore_invalid_headers [h, s] on
	1. 是否忽略不合法的头
	2. 合法的头只能由 letters digits hyphens(减号)
	3. 下划线是否合法要看 underscores_in_headers
	4. 
- listen
- location

server_name
server_name_in_redirect
server_names_hash_bucket_size
server_names_hash_max_size
types_hash_bucket_size
types_hash_max_size

try_files uri1 uri2 uri3 ... =code

types
```
types {
    text/html  html;
    image/gif  gif;
    image/jpeg jpg;
}
```
0. open_file_cache 配置一个缓存用于记录:
	1. 打开的fd, 它的大小和修改时间
	2. information on existence of directories
	3. file lookup errors, such as “file not found”, “no read permission”, and so on.
	4. 可以设置最多记录几个, 还有过期时间
0. open_file_cache_errors
1. open_file_cache_min_uses
2. open_file_cache_valid

# limit系列 #
- limit_except [l]
	1. 只允许哪些方法 和 ip
	2. 例子
	```
		limit_except GET {
			allow 192.168.1.0/32;
			deny  all;
		}
	```
- limit_rate [h, s, l, if]
	1. 限制每个连接最大速度
	2. 0表示不限速
- limit_rate_after [h, s, l, if]
	1. 当该连接的下载量超过多少的时候, 才启动上面的限速
	2. 0表示不限速

# log相关 #
log_not_found [h,s,l] on, 文件没找到的时候是否记录日志
log_subrequest [h,s,l] off, 对子请求(比如ESI发出的)是否记录日志


# 简单 #
etag [http, server, location] on, 是否自动生成etag
internal [location] 表示这个location仅有内部使用, 外部无法访问
keepalive_disable 对于某些浏览器禁止keepalive
keepalive_requests 一个keepalive连接最多可以处理几个请求
keepalive_timeout 一次keepalive能够持续的时间
merge_slashes 是否合并两个相邻的斜杠
port_in_redirect 重定向的时候是否要带上端口号
recursive_error_pages off; 错误页面如果发生错误会怎样
root
satisfy
server_tokens on 是否在返回头里显示一个Server头
underscores_in_headers 是否允许HEADER里面有下划线

# 杂 #
12. connection_pool_size
lingering_close
lingering_time
lingering_timeout
max_ranges
msie_padding
msie_refresh
output_buffers 当读取文件进行输出的时候的buffer
postpone_output
read_ahead
request_pool_size
reset_timedout_connection
resolver
resolver_timeout
sendfile
send_timeout
sendfile_max_chunk
send_lowat
tcp_nodelay 启动TCP_NODELAY选项
tcp_nopush

variables_hash_bucket_size
variables_hash_max_size

# 内置变量 #
$arg_name
$http_cookie
$http_user_agent
$binary_remote_addr
$args
$content_length
$content_type
$cookie_xxxid
$host
$uri
$hostname
$http_name
$is_args
$https
$limit_rate
$remote_addr
$remote_port
$remote_user
$request 整个请求行
$request_method
$server_name
$server_port
$status
