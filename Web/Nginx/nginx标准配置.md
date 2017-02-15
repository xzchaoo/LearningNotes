https://github.com/h5bp/server-configs-nginx
初次使用的话还是先熟悉一下目录结构, 否则会一头雾水, 效率还不如不用...

h5bp/ 包含了很多脚本片段 用于组合网站配置
sites-available/ 所有可用的网站的配置
sites-enabled/ 所有激活的网站的配置, 这个目录下的配置会被自动加载 可以考虑将sites-available/ 里激活的网站做个软链接过来
```
cd /etc/nginx/sites-enabled
ln -s ../sites-available/newproject.com .
如果要禁用的话直接rm就行了
```

# nginx.conf #
它是配置的入口
1. 将 user 设置成了 www
2. 修改了 pid 文件的位置, 将会导致有些软件失效, 因为pid位置变了! nginx的pid默认是放在 logs/nginx.pid 的
3. 配置了日志格式/位置
4. 启动了gzip
5. 引入 sites-enabled/*

# sites-enabled/ #
在这个目录下的配置文件会被引入, 可以认为这里是激活的配置
通常配置会放在 sites-available 里, 然后做一个软引用过来

# sites-available/ #
某个具体的站点的配置, 顶级的元素应该是 server (可以有多个)

https://example.com
```
server {
	listen [::]:80;
	listen 80;
	server_name example.com www.example.com;
	# http->https
	return 301 https://www.example.com$request_uri;
}
server {
	listen [::]:443 ssl http2;
	listen 443 ssl http2;
	server_name example.com;
	include h5bp/directive-only/ssl.conf;
 	return 301 https://www.example.com$request_uri;
}
server {
	listen [::]:443 ssl http2;
	listen 443 ssl http2;
	include h5bp/directive-only/ssl.conf;
	
	#你的配置:

  # Path for static files
  root /var/www/example.com/public;

  #Specify a charset
  charset utf-8;

  # Custom 404 page
  error_page 404 /404.html;

  # Include the basic h5bp config set
  include h5bp/basic.conf;

}
```

# h5bp #
## directive-only/ ##
放了一些有用的指令
1. 基础的ssl配置
2. CORS配置

## location/ ##
常见的缓存配置, 比如对于图片文件的缓存

## basic.conf ##
默认导入了一些上述两个目录里的配置

# 安装 #
https://github.com/h5bp/server-configs-nginx
