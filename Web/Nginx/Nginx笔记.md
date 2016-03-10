Nginx是一个自由的,开源的,高性能的HTTP服务器和反向代理, 同时也是一个IMAP/POP3的代理服务器.

核心模块 邮件模块 HTTP服务模块


CoreModule And EventsModule


1. 高并发
2. 节省资源
3. 代理, 快速解析静态语言, 将后台语言传递到后台的服务


# 控制命令 #
nginx -s stop 停止守护进程 TERM信号
nginx -squit 温和的停止守护进程 STOP信号
nginx -s reopen 重新打开日志文件
nginx -s reload 重新载入配置文件
nginx -t -c xxx.conf 测试一下配置文件是否有效


1. ./configure
2. make
3. make install
4. cd /usr/local/nginx/sbin
5. ./nginx
	1. 提示找不到libpcre.so.1
	2. 按网上的说法 cd /lib; ln -s libpcre.so.0.0.1 libpcre.so.1;即可

./nginx -t -c .../ngnix_1.conf测试一下nginx_1文件是否合法

将nginx添加为系统服务
写一个脚本文件放在/etc/init.d里
```bash
```
再chkconfig进行配置


# 配置文件 #
默认在 /usr/local/nginx/conf 目录下

user nginx group
worker_processes 1; 表明只有一个工作者进程
include 引入其他的配置文件到当前位置, 支持通配符


mime.types 一个文件扩展列表的配置
sites.conf 配置nginx提供的网站 虚拟主机 相关
proxy.conf 代理相关

指令快
events{
	...
}
某些情况下, 不同区段可以相互嵌套

# 核心模块指令 #
deamon on/off; 在后台运行
env name=value; 重新定义环境变量
error_log <file> <level>
	debug info notice warn error crit
	默认值${prefix}/logs/error.log
ssl_engine
user username [groupname]
	用于worker进程
worker_processes 工作者进程数
worker-priority 工作者进程优先级

# Events没偶快 #
可以用来配置网络机制
一下命令要放在events区块的内部
use 在有效的模型中选择event的模型类型
	select poll kqueue 等...
worke_connections 1024
	一个worker进程能够同时连接的受凉

# Configuration模块 #
它提供的include指令能够将其他文件包含在nginx配置文件中
include abc.conf
include sites/*.conf
相对目录则认为与配置文件在同一目录.



推荐新建一个用户和组 然后修改配置文件为:
user nginx nginx;

评估服务器的性能
httperf
autobench
openwebload

nginx的平滑升级

# 模块变量 #
少量预定义好的变量
$http_host 客户端设法要达到的主机的主机名
$http_user_agent 客户端的useragent
$http_referer 客户端请求头的referer
$http_via 客户端请求头的via
$http_x_forwarded_for 客户端请求头的referer
$http_cookie 客户端请求头的referer
还有类似的

还可以获取相应头
$sent_http_content_type

$arg_XXX 允许访问get方法过来的参数XXX
$args 整个queryString
$cookie_XXX
$host
$remote_addr
$request_method
$uri

# Location区段 #
指定一个模式与客户端请求的URI相匹配
location /admin/ {
}
语法
location [=|~|~*|^~|@] pattern {...}

修饰符|描述
:-:|:-:
=|必须精确匹配, pattern不能使用正则表达式, 不用匹配查询字符串
无|必须以pattern开头, pattern不是正则表达式, 不用匹配查询字符串
~|使用正则表达式, 区分大小写
~*|使用正则表达式, 不区分大小
^~|如果指定字符串与uri开始匹配, 那就使用它
@|定义命名的location区段, 这些区段的客户端不能访问, 只可以由内部的请求产生来访问.

## 匹配优先级 ##
= 无-精确匹配 ^~ ~/~* 无-以开头

location ~* ^/(downloads|files)/(.*)$ {
	add_header Capture1 $1;
	add_header Capture2 $2;
}

内部重定向, 重定向客户端的请求.
子请求

错误代码页:
error_page 404 /errors/404.html

```
server{
	server_name ...;
	root /var/www/;
	location /storage/{
		internal;
		alias /var/www/storage/;
	}
	location /documents/ {
		rewrite ^/documents/(.*)$ /storage/$1
	}
}
```
<!--# include file="xxx.html" -->
<!--# include virtual="/xxx.html" -->

if ($request_method = POST){
[...]
}

= != 
if ($string) 如果数据不为空或以0开始的字符串 则为真
-f !-f 文件是否存在
-d !-d 目录是否存在
-e !-e 文件/目录/符号链接 是否存在
-x !-x 是否可执行
~ 大小写敏感正则匹配
~* 大小写不敏感正则匹配
!~ ~的取反
!~* ~*的取反

## 指令 ##
rewrite rexexp replacement [flag];
对于flag
1. last 本次rewrite是当前上下文中最后一次rewrite;
2. break 不知道在说啥
3. redirect 返回302, 让浏览器去重定向
4. permanent 返回301
5. 如果你的replacement是 http://... 那么自动为redirect
6. 请求的参数默认会自动加上, 如果你不需要的话, 可以在replacement最后面加上一个?

break; 用于阻止进一步的rewrite指令

return code; 中断处理过程, 返回一个code

set $var "some text"; 设置一个变量, 有些变量你无法设置值, nginx使用的
set $var $1$2;

## 重写的例子 ##
http://www.abc.com/search/keyword
http://www.abc.com/search.php?q=keyword
rewrite ^/search/(.*)$ /search?php?q=$1?;

## ssi ##
在location里写
ssi on/off
要求请求的页面以 shtml 结尾
然后在代码里添加命令

### include ###
<!--# include -->
file="" 直接包含文件
virtual="" 包含一个请求页面
wait="yes/no" 是否等待请求完成之后才移到其他的include

### echo ###
<!--# echo var="var_name" --> 显示一个参数的内容
比如 你的IP是 <!--# echo var="remote_addr" -->
1. var 指定变量名称
2. default 如果变量为空, 那么就显示这个值
3. ncoding 字符串的编码方式
	1. none 没有特定的编码
	2. url, URL编码 空格->20%
	3. entity, html编码 &->^amp;

### set ###
set var="var_name" value="your_value"

set var="ip" value="$remote_addr"
set var="ip" value="remote_addr"
两者的区别在于前者会插值

### if/else/elif/endif ###
<!--# if expr="..." -->
[...]
<!--# elif expr="..." -->
[...]
<!--# else -->
[...]
<!--# endif -->

$var $var不空就为真
$var = nihao 测试字符串相等
$var != nihao 上面的否定
$var = /pattern/ 正则表达式匹配
$var != /pattern/ 上面的否定

## config ##
config errmsg="发生了一些错误"
config timefmt="" 请参考 strftime 的文档

# index模块 #
index file1 file2 [absolute_file]
如果客户端的请求没有找到, 那么就来这里找

# Autoindex #
如果启动了autoindex, 如果nginx不能为请求的目录提供一个index网页, 那么就用autoindex来生成一个页面, 它列出了该目录下的所有文件.

# Access模块 #
allow ip; 允许该ip访问;
deny all; 禁止所有ip访问;
处理的顺序从上到下.

# lmit zone 模块 #
# lmit request 模块 #

headers模块

addition模块

substitution 模块
可以对内容进行字符串替换

charset filter
memcached模块

Map模块


```
location /images/ {
	root /data;
}
```
的意思是 所有以 /images/ 开头的请求 将会被映射到 /data目录,
如果我请求是 /images/1.jpg, 那么实际打开的文件是 /data/images/1.jpg 切记切记!

如果有多个server映射到同一个ip:port, 那么server_name 就派上用场了, servername 支持普通名字 或 通配符 , 甚至正则表达式
如果连http头没有带Host, 那么就虎路由到一个默认服务器
listen 80 default_server;表明该服务器是默认服务器

set map geo 指令
return

/rwf/user/123
/rwt/user.jsp?id=123


# 处理错误 #
error_page code... [=[response]] uri
error_page 404 /404.html
error_page 501 502 503 504 /50x.html
使用=200 可以修改 return code;
如果只用= 那么return code就等于 uri 返回的return cdoe
error_page 404 =301 http://www.baidu.com/404.html 重定向到某个网址

提供一个fallabck
```
location / {
	...
	error_page 404 = @fallback;
}
location @fallback {
	proxy_pass http://backend;
}
```
意思是如果在本机找不到的话, 那么就通过backend指定的upstream里去找
```
location /old/path.html {
    error_page 404 =301 http:/example.com/new/path.html;
}
```

try_files $uri /images/default.gif 如果uri对应的文件不存在, 那么就使用这个default.gif

try_files $uri $uri/ $uri.html =404; 如果没有一个uri是匹配的, 那么就404

先尝试 uri 再 uri/ 不行再去找代理
```
location / {
    try_files $uri $uri/ @backend;
}

location @backend {
    proxy_pass http://backend.example.com;
}
```

sendfile on;
sendfile_max_chunk 1m; 为了防止一个非常快的连接占旭了整个工作者进程, 可以为了设置一个最大的chunk
tcp_nodelay 一般是on, 如果为off的话, 那么会将多个小包合成一个大包然后发送, 这在网络比较差的时候比较有效

# Optimizing the Backlog Queue #
当有socket来的时候, 会先放到一个队列里, 然后再被消费.
netstat -Lan
Current listen queue sizes (qlen/incqlen/maxqlen)
Listen         Local Address         
0/0/128        *.12345            
10/0/128        *.80       
0/0/128        *.8080
10表示当前有10个socket未被accept, 最大连接限制是128, 说明情况正常.

192/0/128        *.80       
这说明情况不正常, 负荷太大

Increase the value of the net.core.somaxconn key from its default value (128) to the value high enough to be able to handle a high burst of traffic:
For Linux, run the command:
sudo sysctl -w net.core.somaxconn=4096
Open the file: /etc/sysctl.conf
vi   /etc/sysctl.conf
Add the line to the file and save the file:
net.core.somaxconn = 4096
server {
    listen 80 backlog 4096;
    \#The rest of server configuration
}


# 反向代理 #
Proxying is typically used to distribute the load among several servers, seamlessly show content from different websites, or pass requests for processing to application servers over protocols other than HTTP.
```
location ~ \.php$ {
	//将这两个头带给代理服务器, 不然有些时候代理服务器需要客户端的ip, 但是代理服务器只能知道nginx所在的服务器的ip
	proxy_set_header Host $host;
	proxy_set_header X-Real-IP $remote_addr;
	proxy_pass http://www.example.com/link/; 某PHP服务器
}
```
## Buffer ##
默认情况下nginx会缓存服务器返回的数据先放在buffer里, 等全部数据返回完毕, 再将buffer发给客户端.
对于慢的客户端, buffer可以提升性能
当时当buffer打开之后, 会允许代理服务器很快的产生相应, nginx将它保存在buffer里, 客户端需要等很久才能下载他们.
利用 proxy_buffering 选项就可以禁用或打开buffer功能.
禁用: proxy_buffering off; 如果禁用了, 那nginx就只会使用由proxy_buffer_size配置的那个特殊的buffer了.
 
1. proxy_buffers
	1. 控制了分配给一个请求的buffer的个数和大小
	2. 代理服务器相应的数据会先被放到 另外一个特殊的buffer里, 大小由 proxy_buffer_size 指定.
	3. 这个特殊的buffer里通常是比较小的相应, 比如头

如果你的nginx所在的服务器有多个网卡的话, 并且 为了连接到代理服务器需要特殊的ip的话:
proxy_bind 127.0.0.1;

# 数据压缩 #
https://www.nginx.com/resources/admin-guide/compression-and-decompression/
1. 一般情况下 nginx 将数据发给客户端之前就会做压缩.
2. gzip on;
3. 默认只会对 text/html 压缩
4. gzip
5. 其他MIME类型的压缩 gzip_types text/plain application/xml;
6. gzip_min_length 设置需要压缩的内容需要有多少的最小长度.
7. gzip_
```
server {
    gzip on;
    gzip_types      text/plain application/xml;
    gzip_proxied    no-cache no-store private expired auth;
    gzip_min_length 1000;
    ...
}
```

# 缓存 #
1. 当有缓存的时候就可以不用去找代理服务器了.

```
# Define a content cache location on disk
proxy_cache_path /tmp/cache keys_zone=mycache:10m inactive=60m;

server {
    listen 80;
    server_name localhost;
 
    location / {
        proxy_pass http://localhost:8080;
 
       # reference the cache in a location that uses proxy_pass
       proxy_cache mycache;
    }
}
```

## proxy_cache_path ##
proxy_cache_path path [levels=levels] [use_temp_path=on|off] keys_zone=name:size [inactive=time] [max_size=size] [loader_files=number] [loader_sleep=time] [loader_threshold=time] [purger=on|off] [purger_files=number] [purger_sleep=time] [purger_threshold=time];

keys_zone=名字:大小
	1. 缓存项的key和size信息是放在内存里的, size指定了多大的内存用来放这些信息, 1MB大概可以放8000个key信息
	2. inactive时间内没有访问的的文件就会被删除, 不管新鲜度如何


