proxy_buffer_size 
用于从服务器读取的第一个缓存

proxy_buffering
是否启动缓存功能
1. 如果启动了, 那么会先读取到 buffer_size 和 buffers里
2. 如果禁用了, 那么只会使用proxy_buffer_size

proxy_buffers number size;
设置缓存

proxy_busy_buffers_size 
设置正在用于向客户端发送数据的buffer的最大总大小

proxy_bind  绑定的地址

proxy_cache zone;
定义了一个 shared memory zone 用于缓存.

proxy_cache_bypass string...
怎么的连接会bypass
```
proxy_cache_bypass $cookie_nocache $arg_nocache$arg_comment;
proxy_cache_bypass $http_pragma    $http_authorization;
```
只要有一个条件 非false 非空 非零 就会bypass

proxy_cache_convert_head
是否将head转成get方法, 以便于进行缓存
否则 cache_key 应该含有$request_method

proxy_cache_key
定义用于缓存的kye
proxy_cache_key "$host$request_uri $cookie_user"
proxy_cache_key $scheme$proxy_host$uri$is_args$args;

proxy_cache_lock
如果启动
当有多个请求同时访问一个连接, 而这个连接并没有缓存的时候,
就会只允许一个请求到后台去拿数据, 而其他人是锁住的

proxy_cache_lock_age
如果一个cache_key锁了超过一段时间, 那么就允许另外一个连接也发请求到服务器拿数据
If the last request passed to the proxied server for populating a new cache element has not completed for the specified time, one more request may be passed to the proxied server.

proxy_cache_lock_timeout
如果一个请求因为cache_lock的原因被锁了超过一段时间, 那么这个请求就会直接转发给服务器, 但是不会进行缓存

proxy_cache_methods
允许怎样的方法进行缓存
默认GET HEAD总是有的

proxy_cache_min_uses number;
一个请求至少被正常返回几次之后 才缓存
默认是1

proxy_cache_path path [levels=levels] [use_temp_path=on|off] keys_zone=name:size [inactive=time] [max_size=size] [loader_files=number] [loader_sleep=time] [loader_threshold=time] [purger=on|off] [purger_files=number] [purger_sleep=time] [purger_threshold=time];
缓存的响应是被保存到文件的

```
proxy_cache_path /data/nginx/cache levels=1:2 keys_zone=one:10m;
/data/nginx/cache/c/29/b7f54b2df7773722d382f4809d65029c
```
1. 缓存的响应会先写入到一个临时文件, 然后再rename
2. use_temp_path proxy_temp_path
3. 如果不适用临时文件的话, 那么会直接在缓存对应的文件路径生成文件
4. inactive 多久没访问到就删除缓存文件
5. cache manager, 会监控整个缓存的大小, 如果超过max_size, 它就根据LRU移除文件
6. cache manager启动一分钟后, cache loader就被激活, It loads information about previously cached data stored on file system into a cache zone.
	1. loader_threshold
	2. loader_sleep
	3. loader_files

purger_sleep=number
purger_files=number
purger_threshold=number
purger=on|off
 
purge似乎是NginxPlus才能使用?
proxy_cache_purge string ...
哪些请求被被认为是purge请求
如果指定的值非0, 那么就执行purge操作


proxy_cache_use_stale
proxy_cache_use_stale error | timeout | invalid_header | updating | http_500 | http_502 | http_503 | http_504 | http_403 | http_404 | off ...;
在后台服务器返回什么的时候允许使用stale的缓存

proxy_cache_valid
设置每种statusCode的缓存时间
比较复杂 需要再看

proxy_cookie_domain
修改服务端返回的set-cookie的domain
支持变量和正则

proxy_hide_header
指定的头将不会发给客户端

proxy_http_version
默认是1.0
如果要keepalive就要1.1

proxy_ignore_headers
当服务端返回某些头的时候, nginx 会做对应的处理比如 X-Accel-Redirect
使用这个指令之后, 就可以让nginx忽略他们
```
Disables processing of certain response header fields from the proxied server. The following fields can be ignored: “X-Accel-Redirect”, “X-Accel-Expires”, “X-Accel-Limit-Rate” (1.1.6), “X-Accel-Buffering” (1.1.6), “X-Accel-Charset” (1.1.6), “Expires”, “Cache-Control”, “Set-Cookie” (0.8.44), and “Vary” (1.7.7).
If not disabled, processing of these header fields has the following effect:
“X-Accel-Expires”, “Expires”, “Cache-Control”, “Set-Cookie”, and “Vary” set the parameters of response caching;
“X-Accel-Redirect” performs an internal redirect to the specified URI;
“X-Accel-Limit-Rate” sets the rate limit for transmission of a response to a client;
“X-Accel-Buffering” enables or disables buffering of a response;
“X-Accel-Charset” sets the desired charset of a response.
```

proxy_limit_rate
限制连接的速度

proxy_method
向代理发送请求的方法, 而不是客户端的方法
就是为了覆盖?

proxy_max_temp_file_size
当buffer不够用的时候 会将部分数据写入tempfile
用于限制文件最大大小

proxy_next_upstream
proxy_next_upstream error | timeout | invalid_header | http_500 | http_502 | http_503 | http_504 | http_403 | http_404 | off ...;
什么情况下要将请求发给下一个 server

proxy_next_upstream_timeout
当超时的时候就发到下一个nexts tream
似乎没解释好啊

proxy_next_upstream_tries
重试几次

proxy_no_cache
满足条件的就no_cache
```
proxy_no_cache $cookie_nocache $arg_nocache$arg_comment;
proxy_no_cache $http_pragma    $http_authorization;
```

proxy_pass
单ip
upstream
unix
```
proxy_pass http://$host$uri;
or even like this:
proxy_pass $request;
```
proxy_pass_request_body
是否允许request的body传给服务器

proxy_pass_request_headers
是否允许request的headers传给服务器

proxy_redirect
当服务器在Location, Refresh里写了一个名字, 把它修改成对应的名字
```
proxy_redirect http://localhost:8000/two/ http://frontend/one/;
proxy_redirect http://localhost:8000/two/ /;
```

proxy_request_buffering
如果启动了先缓存客户的请求, 再发给服务器
如果关闭了
从客户端读的同时发给服务器
就无法使用next_upstream, 因为你没有保存客户的请求

proxy_send_lowat
proxy_send_timeout
设置将请求发给服务器的超时时间
这个超时时间是指两次写操作的将额时间
因为一个请求可能要分多次写

proxy_set_body
修改发给服务器的body

proxy_set_header
设置发给服务器的header
```
proxy_set_header Host       $proxy_host;
proxy_set_header Connection close;
```

proxy_store
http://www.brentron.com/safe/system/6804.html
似乎是将文件永久保存在本地了!?
文件的修改时间根据Last-Modified响应头来设定

proxy_store_access
proxy_temp_file_write_size
设置每次写temp文件的大小, 这样可以减少写的次数
proxy_temp_path

proxy_ssl_verify_depth
proxy_ssl_trusted_certificate
proxy_ssl_server_name
proxy_ssl_session_reuse
proxy_ssl_crl
proxy_ssl_name
proxy_ssl_password_file
proxy_ssl_protocols
proxy_ssl_verify
proxy_ssl_ciphers
proxy_ssl_certificate_key
proxy_ssl_certificate
proxy_pass_header
proxy_read_timeout
proxy_intercept_errors
proxy_ignore_client_abort
proxy_force_ranges
proxy_headers_hash_bucket_size
proxy_headers_hash_max_size
proxy_cookie_path
proxy_connect_timeout
proxy_cache_revalidate


# 变量 #
$proxy_add_x_forwarded_for
$proxy_port
$proxy_host