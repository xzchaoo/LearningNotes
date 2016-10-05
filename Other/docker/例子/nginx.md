# 例子 #
docker run \
--name nginx \
-v /root/html:/usr/share/nginx/html:ro \
-v /root/default.conf:/etc/nginx/conf.d/default.conf:ro \
-p 80:80 \
-d nginx

# 挂载内容 #
-v /some/content:/usr/share/nginx/html:ro

# 配置文件 #
-v /some/nginx.conf:/etc/nginx/nginx.conf:ro
注意记得包含 daemon off
查看该文件可以发现, 默认他已经将 /etc/nginx/conf.d/*.conf 都包含到http里了


# TODO #
学习 nginx 的标准配置方式

# linux的envsubst命令 #
可以进行替换${NGINX_PORT}
这样你可以先写一个 template 文件, 里面放了一堆 ${xxx}
然后执行该命令 这样就会用环境变量去修改它 并产生新的输出

server {
	listen 80;
	server_name localhost;
	charset utf8;
	location / {
		root /usr/share/nginx/html;
		index index.html;
	}
}

