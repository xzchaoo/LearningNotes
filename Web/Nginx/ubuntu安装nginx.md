# 14.04 #
刚装完的系统

通常是因为 libpcre3-dev 没装

依赖 openssl : libssl-dev
依赖 pcre : libpcre3 libpcre3-dev
依赖 zlib : zlib1g-dev

apt-get install make gcc libpcre3 libpcre3-dev zlib1g-dev
make && make install


## 配置启动脚本 ##
按照这个教程就行
https://github.com/JasonGiedymin/nginx-init-ubuntu

防火墙用 ufw

16.04
下载解压nginx
执行 ./configure 会提示缺少pcre
apt install libpcre3 libpcre3-dev
再次执行./configure 会提示缺少zlib
apt install zlib1g-dev
再次./configure 可以通过 不缺东西了

openssl version 版本是 1.0.2g

useradd --system -M nginx
./configure \
--with-http_stub_status_module \
--with-http_ssl_module \ 
--with-http_v2_moduoe \
--user=nginx \ 
--group=nginx

会提示 缺少ssl lib
安装 apt install libssl-dev

make
make install

总结
apt install libssl-dev libpcre3 libpcre3-dev zlib1g-dev
useradd --system -M nginx
./configure \
--with-http_stub_status_module \
--with-http_ssl_module \
--with-http_v2_module \
--user=nginx \
--group=nginx

make
make install

安装初始化脚本
https://www.nginx.com/resources/wiki/start/topics/examples/systemd/

https://github.com/h5bp/server-configs-nginx
