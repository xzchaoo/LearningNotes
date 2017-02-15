# 14.04 #
刚装完的系统

apt-get install make gcc libpcre3 libpcre3-dev zlib1g-dev
make && make install


## 配置启动脚本 ##
按照这个教程就行
https://github.com/JasonGiedymin/nginx-init-ubuntu

防火墙用 ufw


useradd --system -M nginx
id nginx
