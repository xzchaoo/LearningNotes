https://github.com/h5bp/server-configs-nginx

h5bp/ 包含了很多脚本片段 用于组合网站配置
sites-available/ 所有可用的网站的配置
sites-enabled/ 所有激活的网站的配置, 这个目录下的配置会被自动加载 可以考虑将sites-available/ 里激活的网站做个软链接过来
```
cd /etc/nginx/sites-enabled
ln -s ../sites-available/newproject.com .
如果要禁用的话直接rm就行了
```

mime.types
nginx.conf

