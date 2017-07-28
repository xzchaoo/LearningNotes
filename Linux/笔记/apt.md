通常认为 apt 和 apt-get 一样, 并且 apt 比 apt-get 更强
apt update 更新源
apt upgrade 升级所有软件

apt install ...
-y 自动确认

apt search keyword

apt remove 包名

apt autoremove

apt install software-properties-common
add-apt-repository

安装java8
add-apt-repository ppa:webupd8team/java 
apt update
apt install oracle-java8-installer
update-alternatives --config java

使用apt-fast代替apt-get大幅度提升下载速度
http://www.linuxdiyf.com/linux/9905.html


设置apt源, 这样速度更快
备份 /etc/apt/sources.list, 然后替换它为
```
deb http://ftp.sjtu.edu.cn/ubuntu/ xenial main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ xenial-security main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ xenial-updates main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ xenial-proposed main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ xenial-backports main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ xenial main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ xenial-security main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ xenial-updates main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ xenial-proposed main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ xenial-backports main restricted universe multiverse
```

16.04的代号是xenial
