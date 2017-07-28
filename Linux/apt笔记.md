# 安装add-apt-repository #
apt-get install python-software-properties
apt-get install software-properties-common

http://wiki.ubuntu.org.cn/%E6%A8%A1%E6%9D%BF:16.04source
在旧版里要用 apt-get 新版可以用 apt

安装包
apt-get install package1 package2 package3 ...

apt-cache:
search 搜索
show 包的信息

apt-get:

remove 卸载
--purge 删除包 相关配置文件等
update 更新源
upgrade 更新所有已安装的包

http://mirrors.163.com/.help/ubuntu.html

http://wiki.ubuntu.org.cn/%E6%A8%A1%E6%9D%BF:Ubuntu_source

deb http://ftp.sjtu.edu.cn/ubuntu/ precise main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ precise-security main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ precise-updates main restricted universe multiverse
deb http://ftp.sjtu.edu.cn/ubuntu/ precise-backports main restricted universe multiverse
##测试版源
deb http://ftp.sjtu.edu.cn/ubuntu/ precise-proposed main restricted universe multiverse
# 源码
deb-src http://ftp.sjtu.edu.cn/ubuntu/ precise main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ precise-security main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ precise-updates main restricted universe multiverse
deb-src http://ftp.sjtu.edu.cn/ubuntu/ precise-backports main restricted universe multiverse
##测试版源
deb-src http://ftp.sjtu.edu.cn/ubuntu/ precise-proposed main restricted universe multiverse

# Canonical 合作伙伴和附加
deb http://archive.canonical.com/ubuntu/ precise partner
deb http://extras.ubuntu.com/ubuntu/ precise main

add-apt-repository
