# 注意 #
建议使用下面的 其他的试过了都不好用...
https://github.com/JasonGiedymin/nginx-init-ubuntu


https://github.com/Fleshgrinder/nginx-sysvinit-script

git clone https://github.com/Fleshgrinder/nginx-sysvinit-script.git
cd nginx-sysvinit-script
make

然后你做一个软件接

ln -s /usr/local/nginx/sbin/nginx /usr/local/sbin/nginx

service nginx status
service nginx start
service nginx status