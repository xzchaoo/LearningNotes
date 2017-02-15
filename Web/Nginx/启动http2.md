需要 openssl 1.0.2 如果你装的是 openssl 1.0.1 那么http2无效
到 https://www.openssl.org/source/ 下载最新版本
假设我在 /root 目录
wget https://www.openssl.org/source/openssl-1.0.2k.tar.gz
tar -zxf openssl-1.0.2k.tar.gz

nginx 重新编译
--with-http_v2_module --with-openssl=/root/openssl-1.0.2k
修改nginx的配置
listen 80 http2;
即可

之后可能需要关掉nginx再打开, 仅仅 -s reload 可能不够

访问 chrome://net-internals/#http2 然后刷新你网站 就会看到这里有条关于你的网站的记录

加了这些之后编译速度很慢...
