# 注意 #
你的nginx要配置ssl模块

编译的时候
--with-http_ssl_module



https://letsencrypt.readthedocs.org/en/latest/using.html#installation
软件下载之后
执行

启动你的web服务器, 假设目录是 /var/html
./letsencrypt-auto certonly --webroot -d xzchaoo.ml -w /var/html
然后就开始验证了
这个工具会在 /var/html/.well-know里产生一些东西
对方的服务器将会到
http://xzchaoo.ml/.well-know/... 
这个目录去找一些东西
两者一对起来就验证成功了
成功之后会在/etc/letsencrypt下产生一些东西
然后在你的nginx:

/etc/letsencrypt/archive/xzchaoo.win

```
server {
	listen 80;
	server_name xzchaoo.win www.xzchaoo.win;
	rewrite ^(.*) https://$server_name$1 permanent;
}
server {
	listen 443;
	server_name xzchaoo.ml www.xzchaoo.ml;
	
	#主要是这3行
	ssl on;
	ssl_certificate c:/k/fullchain1.pem;
	ssl_certificate_key c:/k/privkey1.pem;
	

	location / {
		proxy_pass http://127.0.0.1:7180;
	}
```
Let's Encrypt

--test-cert

/etc/letsencrypt/live/xzchaoo.ml/fullchain.pem

privkey.pem
	私钥, 必须保证他的安全
	这个用用于 ssl_certificate_key
cert.pem
	Server certificate
chain.pem
	除了Server certificate外的所有certificate
	ssl_trusted_certificate
fullchain.pem
	包含所有certificate
	ssl_certificate.

必须要使用
chain.pem or fullchain.pem

# 20161020 #
发现它似乎改名, 叫做 certbot 了

安装辅助工具
```
user@webserver:~$ wget https://dl.eff.org/certbot-auto
user@webserver:~$ chmod a+x ./certbot-auto
user@webserver:~$ ./certbot-auto --help
```
这样你就获得了一个 cerbot-auto 的工具

./certbot-auto certonly --webroot -w /var/www/example -d xzchaoo.win-d www.xzchaoo.win -w /usr/local/nginx/html

./certbot-auto certonly --standalone -d xzchaoo.win -d www.xzchaoo.win
记得将本机的80和443端口打开 aws 用户记得到控制台上去配置

./cerbot-auto renew

--webroot 意思是: 你现在拥有一台web服务器的根目录操作权限, 该脚本将会在web的根目录产生一个随机文件, 然后对方的服务器会来读这个文件, 如果能读到, 那么就证明通了!
--standalone 作用跟 webroot 类似, 但是它会自己帮你起一个非常简单的web服务器来做上面的事情, 所以你本机不能占用80和443端口(你要先关了你的nginx)s

--nginx 支持nginx的自动配置

整数只有90天有效期, 过期前的一个月内允许续期
certbot renew
certbot renew --pre-hook "service nginx stop" --post-hook "service nginx start"

