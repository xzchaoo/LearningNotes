https://github.com/FRiCKLE/ngx_cache_purge

使得nginx支持purge功能

```
http{
	proxy_cache_path /usr/local/tengine/proxy_cache levels=1:2 keys_zone=z1:10m inactive=60s;
	server {
		...
		location /cache {
			proxy_pass http://192.168.157.1:8080;
			proxy_cache z1;
			proxy_cache_key $uri; 注意这里
			proxy_cache_purge PURGE from all; 这个指令是该模块提供的
			proxy_cache_valid 200 2m;
		}
	}
}
```
proxy_cache_purge PURGE from all;的意思是 对于所有的ip, 当它对该地址(比如/cache/1.txt)发PURGE方法的时候, 就清除该请求对应的代理缓存.
根据nginx文档, proxy_cache_key不写的话默认是:$scheme$proxy_host$request_uri
而$request_uri的格式是 GET / HTTP/1.1, 因此当你发PURGE的时候, 两者的key对不上号, 导致找不到缓存(会返回一个404)
因此必须重写proxy_cache_key, 比如这里使用$uri代替

也可以用下面的指令消除其他location的cache
proxy_cache_purge
syntax: proxy_cache_purge zone_name key
default: none
context: location
Sets area and key used for purging selected pages from proxy's cache.

```
location ~ ^/purge(/.*)$ {
	allow some.ip.add.ress;
	deny all;
	proxy_cache_purge z1 $1$is_args$args; 这个模式刚好和$request_uri匹配
}
```
如果成功返回200, 失败则返回404
失败通常是key对应的缓存不存在.