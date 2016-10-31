# cron #

# crontab #
-l [user] 列出该用户的定时任务
-e [user] 编辑这个用户的定时任务
-d [user] 删除该用户的定时任务

格式
M H D m d CMD
M 分钟
H 小时
D 天
m 月
d 星期几

```
* 每个单位
1-5 1到5
1,2,3,4,5 枚举
```


/usr/lib/cron/cron.allow 表示谁能使用crontab命令
/usr/lib/cron/cron.deny 谁不能使用这个命令

几个例子
```
30 21 * * * /usr/local/etc/rc.d/lighttpd restart 
```

