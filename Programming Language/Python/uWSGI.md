https://uwsgi-docs.readthedocs.io/en/latest/WSGIquickstart.html


uwsgi --http :9090 --wsgi-file foobar.py --master --processes 4 --threads 2 --stats 127.0.0.1:9191

```
location / {
    include uwsgi_params;
    uwsgi_pass 127.0.0.1:3031;
}
```


# 提供静态内容 #
static-map = /images=/var/html/images

apt-get install libpcre3-dev
apt-get install zlib1g-dev

./configure
make 
make install
 

# 配置 #
http://uwsgi-docs.readthedocs.io/en/latest/Options.html

参数|描述
:-:|:-:
set|设置一个占位符或选项
daemonize|后台运行
daemonize2|当app 加载完毕之后才后台运行
stop|停止实例
reload|重新加载实例
pause/suspend/resume|
set-placeholder|设置占位符
master|.
buffer-size|作为http头的buffer, 默认是4k, 如果不够的话需要增加, 否则会出现警告
memory-report|内存报告
processes/workers|同义词 分裂的进程数
threads|.
socket|将默认协议绑定到哪个端口, 例 :9090, 相当于0.0.0.0:9090
uwsgi-socket| 类似上面, 对于uwsgi协议
suwsgi-socket| 类似上面, 对于uwsgi协议 + ssl
http-socket| http 类似上面
https-socket| https 类似上面
protocol|默认协议
socket-protocol|默认的套接字协议
python|. 
chdir|.
limit-as|.
harakiri|
enable-threads


thunder-lock


支持类似的环境变量 比如 UWSGI_MASTER=1



支持占位符 %(name)
%(port+1) 这样都行

@(file_path) 可以将文件的内容导入进来

## 动态调整参数 ##
nginx 将请求转给 uwsgi 的时候可以提供参数
uwsgi_param <name> <value>;
可以参考 nginx 自带的 uwsgi_params 文件的内容


## 配置逻辑 ##
http://uwsgi-docs.readthedocs.io/en/latest/ConfigLogic.html

if-env if-exists if-file if-dir if opt
for

## 配置解析顺序 ##
一般是从上往下, 尽快展开, 全部展开完毕之后再来替换占位符

