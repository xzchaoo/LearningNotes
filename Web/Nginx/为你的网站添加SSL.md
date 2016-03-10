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
```
server {
	listen 443;
	ssl on;
	ssl_certificate c:/k/fullchain1.pem;
	ssl_certificate_key c:/k/privkey1.pem;
	server_name xzchaoo.ml www.xzchaoo.ml;
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

