往 /etc/resolvconf/resolv.conf.d/base 里面新增配置

然后刷新配置
resolvconf -u

观察 /etc/resolve.conf 它的内容被修改了


```
TODO 直接修改不知道有没有效果
修改配置  /etc/resolve.conf
nameserver 1.2.4.8 之类...
```

