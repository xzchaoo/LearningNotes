# 参考 #
http://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html

http://blog.csdn.net/linuxnews/article/details/51870098


service <service-name> start/stop/status/restart

systemctl start/stop/status/restart/kill/reload/show/daemon-reload <name>[.<unit>]

例子
systemctrl status influxdb

列出Unit的依赖
systemctl list-dependencies influxdb.service

# Unit配置文件 #
每个Unit都有一个配置文件, 告诉Systemd怎么启动它
默认在 ``/etc/systemd/system/``, 这个目录放了link
真实的文件在 ``/usr/lib/systemd/system``

激活unit
```
systemctl enable xxx.service
相当于
ln -s '/usr/lib/systemd/system/xxx.service' '/etc/systemd/system/multi-user.target.wants/xxx.service'
```
禁用unit
systemctl disable xxx.service

列出所有的service类型的unit文件
systemctl list-unit-files --type=service





```
[Unit]
Description=...
Documentation=...
After=network-online.target

[Service]
User=influxdb
Group=influxdb
LimitNOFILE=65536
EnvironmentFile=-/etc/default/influxdb
ExecStart=/usr/bin/influxdb -config /etc/influxdb/influxdb.conf ${INFLUXD_OPTS}
KillMode=control-group
Restart=on-failure

[Install]
WantedBy=multi-user.target
Alias=influxd.service
```