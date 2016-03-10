这里只介绍一些我懂的

1. mysqld
	1. 服务器程序
	2. /etc/my.cnf /etc/mysql/my.cnf /usr/etc/my.cnf
2. mysql
	1. 客户端
	2. mysql -h host --port port -u user -p 执行后会提示你输入密码
3. mysqladmin
	1. 可以进行比较多的管理员指令
	2. 创建和删除数据库
	3. 重新加载grant表
	4. 将表刷到磁盘
	5. 日志文件的操作
	6. 修改密码
	7. 查看服务器运行状态
4. mysqlcheck
5. mysqldump
6. mysqlimport
7. mysqlpump
8. mysqlshow
	1. 查看数据库 表 字段 和索引
9. mysqlslap 
10. mysql_config
11. mysql_config_editor
12. mysqld_multi
	1. 用于管理多个mysqld进程