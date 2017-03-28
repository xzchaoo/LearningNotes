# 参考 #
1. https://linux.cn/article-5926-1.html
2. http://blog.jobbole.com/97248/
3. http://www.linuxidc.com/Linux/2014-07/104487.htm
4. http://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html


操作服务
start 启动
restart 重启
stop 停止
reload
status 状态
kill
show 显示配置

随着系统启动的服务
enable
disable
is-active


systemd
systemd-sysv-install

屏蔽
mask
unmask

列出当前的运行等级
systemctl get-default

重启
systemctl reboot

关机
systemctl halt


```
Runlevel 0 : 关闭系统
Runlevel 1 : 救援？维护模式
Runlevel 3 : 多用户，无图形系统
Runlevel 4 : 多用户，无图形系统
Runlevel 5 : 多用户，图形化系统
Runlevel 6 : 关闭并重启机器
```


分析启动
systemd-analyze

分析启动时间消耗
systemd-analyze blame

分析启动关键链
systemd-analyze critical-chain

列出所有可用单元
systemctl list-unit-files
service mount socket device 都算是单元
列出所有服务
systemctl list-unit-files --type=service


列出运行中的单元
systemctl list-units

检查某个单元是否启动
systemctl is-enabled crond.service

是否正在运行
systemctl is-active crond.service

/usr/lib/systemd/system/httpd.service


以属性方式列出正在运行的进程
systemd-cgls


单元的状态
enabled 开机启动
disabled 开机不启动
static 这里的 static 它是指对应的 Unit 文件中没有定义[Install]区域，因此无法配置为开机启动服务
masked

/etc/systemd/system/multi-user.target.wants/
/usr/lib/systemd/system/
/lib/systemd/system/

启动服务就是在当前的运行等级目录:
/etc/systemd/system/multi-user.target.wants/
下建立
/usr/lib/systemd/system/
或
/lib/systemd/system/
的软链接

禁止服务就是删除这个软链接

systemctl脚本放在
/usr/lib/systemd/systemd/
/usr/lib/systemd/user/
下
