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

