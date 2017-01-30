1. apt-get install openssl libssl-dev
2. --with-http_ssl_module 重新编译
3. 停止nginx服务
4.
wget https://dl.eff.org/certbot-auto
chmod a+x ./certbot-auto
./certbot-auto --help

./certbot-auto certonly --standalone -d xzchaoo.com www.xzchaoo.com test.xzchaoo.com

这里采用 standalone 模式
这里要保证:
1. 80 和 443 端口没有被占用并且可以被外界访问
2. 用 -d 指定的域名被解析到当前的机器上, -d 可以多次使用 指定多个域名

5.
ssl on;
ssl_certificate ...fullchain1.pem;
ssl_certificate_key ...privkey1.pem;


6. 定期更新一下
./cerbot-auto renew

./certbot-auto renew --pre-hook "service nginx stop" --post-hook "service nginx start"

./certbot-auto renew --pre-hook "/usr/local/nginx/sbin/nginx -s stop" --post-hook "/usr/local/nginx/sbin/nginx"

https://letsencrypt.org/how-it-works/



--email 手动指定email 否则会让你输入
--agree-tos 同意条约

--standalone 独立模式, 需要使用80个443端口, 并且保证相关的域名被解析到该服务器上
--webroot 如果你已经有服务器运行在80和443端口了, 你不想停止他们, 并且你又权限在网站的根目录下放文件, 那么久可以使用webroot模式, 此时certbot会在 ${webroot-path}/.well-known/acme-challenge目录下 生成一些数据
然后通过 GET

--force-renewal 强制获取一个新的证书 即使你提供的域名已经有证书了(保存在本机), 仔细观察 live 目录下放的是 archive 目录的符号链接
--duplicate  
--expand 用于往一个证书添加额外的域名

如果你的证书只用于 xzchaoo.com, 但是没有用于 a.xzchaoo.com 那么a.xzchaoo.com 是不能使用xzchaoo.com的证书的



renew 尝试更新所有将要过期的证书(1个月)
这是个大问题, 如果我们是使用 standalone 模式的话, 它将会使用 80 和 443 端口, 我们不得不关闭我们的nginx.
这时候就可以使用 --post-hook --pre-hook PRE_HOOK 
https://certbot.eff.org/docs/using.html



/etc/letsencrypt/archive 和 /etc/letsencrypt/keys 保存着所有的历史数据
/etc/letsencrypt/live 是最新版本的符号链接
privkey.pem 密钥! 用于 ssl_certificate_key
cert.pem
chaim.pem
fullchain.pem = cert.pem + chain.pem
