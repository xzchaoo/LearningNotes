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



useradd --system -M nginx
./configure \
--with-http_stub_status_module \
--user=nginx \ 
--group=nginx


make
make install