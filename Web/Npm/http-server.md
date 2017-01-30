npm install -g http-server

-p 端口 默认8080
-a 地址 默认0.0.0.0
--cors
-c 设置缓存时间 默认是 3600 建议开发的时候 -c-1 表示不缓存
-P/--proxy 把所有本地不能解析的请求代理到某个地址 比如  -P http://agg.xzchaoo.com

```
"start": "http-server dist -c-1"
```
