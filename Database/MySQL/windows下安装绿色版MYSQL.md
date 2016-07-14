1. 下载绿色版, 解压到某个目录
2. 添加 my.ini 配置文件, 最简单的配置如下即可
```
[mysqld]
port = 3306
basedir=D:\xzc\dev\mysql\mysql-5.7.13-winx64
datadir=D:\xzc\dev\mysql\mysql-5.7.13-winx64\data
```
3. 按如下顺序初始化
```
	mysqld --initilize-insecure
	mysql --install
	net start mysql
```
