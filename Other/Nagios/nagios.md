Nagios可以:
1. 监控桌面和服务器操作系统: 系统指标, 服务状态, 进程状态, 性能计数器, 实践日志, 应用程序, 支持windows linux等操作系统


https://assets.nagios.com/downloads/nagioscore/releases/nagios-4.3.1.tar.gz#_ga=1.99727774.1411877755.1489723715
https://nagios-plugins.org/download/nagios-plugins-2.1.4.tar.gz#_ga=1.90200474.1411877755.1489723715


在server上安装check_prne
wget https://github.com/NagiosEnterprises/nrpe/archive/3.0.1.tar.gz
tar -zxf ...
cd nrpe...
./configure
make check_nrpe
make install-plugin

```
/usr/local/nagios/libexec/check_nrpe -H 192.168.234.129
NRPE v3.0.1
```


# 主机 #
在主机上安装 NRPE

# 对象定义 #
https://assets.nagios.com/downloads/nagioscore/docs/nagioscore/4/en/objectdefinitions.html

## host ##
描述了一台主机
通常需要提供它的IP

host_name 填写主机的名字, 以后引用主机的时候就用这个名字了
alias
address 对方的ip
parents 指定这个主机的父主机(都好隔开), 如果父主机挂了, 那么这台主机也不用检查了
hostgroups 逗号分隔
check_command

max_check_attempts 最大尝试次数
check_interval 检查间隔时间
retry_interval 重试间隔时间


## host group ##
一个组里包含多个主机

hostgroup_name 名字
alias
members 逗号隔开
hostgroup_members 其他组的名字, 逗号隔开, 用于包含其他组的所有成员
 

## service ##
描述了一个监控逻辑
```
define service {
	use generic_service 使用模板
	host_name ... 用于某个主机
	service_descriptin 服务描述, 会显示在web上
	check_command	check_http!-p 8080
}
```

host_name 必填
hostgroup_name
service_description
parents
check_command



## service group ##
一个组可以包含多个服务
servicegroup_name
alias
members
servicegroup_members

## contact ##
定义了一个联系人
contact_name
email
addressN

## contact group ##
联系人组

## timeperiod ##
可以规定在某个时间段里才进行某些检查
```
define timeperiod {
    timeperiod_name     nonworkhours
    alias               Non-Work Hours
    sunday              00:00-24:00                 ; Every Sunday of every week
    monday              00:00-09:00,17:00-24:00     ; Every Monday of every week
    tuesday             00:00-09:00,17:00-24:00     ; Every Tuesday of every week
    wednesday           00:00-09:00,17:00-24:00     ; Every Wednesday of every week
    thursday            00:00-09:00,17:00-24:00     ; Every Thursday of every week
    friday              00:00-09:00,17:00-24:00     ; Every Friday of every week
    saturday            00:00-24:00                 ; Every Saturday of every week
}
```


## command ##
描述如何执行检查逻辑

# 对象继承 #
在对象定义里使用 use 语法 进行继承


# check_http #
-p端口
-u --uri --url 指定路径
-H $HOSTADDRESS$
-s 指定期待的响应内容
-P/--post=data 要post的数据 用url编码格式
-j --method=GET POST 等
-a --authorization=用户名:密码
-k --header


# 杂 #
## 检查配置文件是否正确 ##
/usr/local/nagios/bin/nagios -v /usr/local/nagios/etc/nagios.cfg

## 包含文件 ##
include_file=...
include_dir=...


# 主配置文件 #
https://assets.nagios.com/downloads/nagioscore/docs/nagioscore/4/en/configmain.html

配置日志位置
加载其他的对象配置
用户和组
主机和服务检查的并发数
可以配置是否: 主机检查失败 服务就不检查了
还有其他的各种开关

# cgi配置文件 #
在里面引用了主配置文件
和其他的html素材
