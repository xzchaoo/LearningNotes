用于收集机器的各项指标然后输出到influxdb(其他也是可以的)
https://docs.influxdata.com/telegraf/v1.2/introduction/getting_started/

curl -sL https://repos.influxdata.com/influxdb.key | sudo apt-key add -
source /etc/lsb-release
echo "deb https://repos.influxdata.com/${DISTRIB_ID,,} ${DISTRIB_CODENAME} stable" | sudo tee /etc/apt/sources.list.d/influxdb.list


# 下载 #
https://portal.influxdata.com/downloads
wget https://dl.influxdata.com/telegraf/releases/telegraf_1.2.1_amd64.deb
sudo dpkg -i telegraf_1.2.1_amd64.deb

Network Time Protocol(NTP)

# 生成配置文件 #
原有的配置文件里内容太多了 不方便, 可以使用下面的命令生成配置
```
telegraf -sample-config -input-filter cpu:mem -output-filter influxdb > telegraf.2.conf
```
意思是 输入插件有2个 cpu和mem, 输出插件有1个 influxdb
-sample-config 应该是表示这是要用于生成配置文件的吧?

# 启动 #
service telegraf start
systemctl start telegraf

telegraf -config xxx.conf



# 概念 #

## 输入 ##
可以从各个地方读取指标
比如 cpu mem mysql nginx redis 等, 这些都是内置支持的

输入还可以来自:
1. 一个脚本的输出结果
2. 一个http请求的响应
3. 


## 处理 ##
输入的指标会被扔给 "处理" 进行处理
目前官方的 处理插件 貌似只有一个 printer, 就是将收到的指标打印一下, 貌似没什么用途

## 聚合 ##
可以在这里做聚合, 然后再提交 降低频率

## 输出 ##
输出到influxdb(或其他)

# 配置 #
https://docs.influxdata.com/telegraf/v1.2/administration/configuration/
/etc/telegraf/telegraf.conf

## 环境变量 ##
在配置文件里的额任何地方都可以使用环境变量
语法是 "$XXX" 记住一定要双引号

## 所有的输入插件都支持 ##
interval="10s" 表示每10秒收集一次, 会覆盖全局的默认配置

name_override 用于覆盖默认的 measurement 名字
比如 cpu input 的默认measurement名字是 cpu, 你可以覆盖它为 ccppuu

measurement的前缀和后缀, 默认是空白
name_prefix name_suffix

自动加入tags
tags={"France":"Paris","Italy":"Rome","Japan":"Tokyo","India":"New Delhi"}


## global_tags ##
节里存放全局的tag, 这些tag会被自动加到指标里
所有的value都要用双引号, 否则会导致语法错误(不过我也找不到错误输出的日志), 从而无法收集数据
```
dc="sh" 上海数据中心
server="$XXX_IP" 本机的IP
```
目前貌似只能通过这个手段给指标加上tag, 否则你无法分辨哪个tag来自哪台服务器
个人感觉 "处理" 这个阶段貌似也行 不过目前没有相关的插件

## agent ##
数据收集间隔时间
批量输出大小
批量输出buffer大小
睡觉时长 (防止指标收集太频繁 影响系统性能)

设置日志文件, 手机 telegra 本身的日志
debug quiet logfile

telegra 会自动将 主机名 作为tag加入
主机名的获取是通过 os.getHostname() 估计是go语言里的一个方法
在agent节里也可以手动覆盖这个值, 不过貌似不支持方法调用
hostname="你的主机名", 这里推荐还是使用环境变量吧, 貌似没有其他办法可以取得IP
omit_hostname=false 是否主机名不加入到tags里

## output ##
每个output小节配置了一个输出地

我们最常使用 influxdb 和 file(配合stdout测试用)
```
[[outputs.influxdb]]
urls=["http://localhost:8086"] 这里可以填写多个地址 到时候会轮流使用
database="telegraf"
retention_policy=""
#username=""
#password=""
timeout="5s"
write_consistency=""
```

```
[[outputs.file]]
files=["stdout","/tmp/metrics.out"]
data_format="influx"
```

不过要注意 由于 telegraf 都是后台运行 所以你写了stdout 也没用

## 聚合配置 ##
period="30s" 聚合每30秒内的数据
delay="5s" 延后多少时间内聚合, 官方说是用于处理一种极端情况: (系统内部的时间应该是一样的, 比如大家都是5s运行一次的话 那么很大机会会重叠 或者无法判断谁先运行) 数据收集和聚合的时间间隔是一样的, 这样会导致有的时候没有可以聚合的值, 因为你不知道聚合或数据收集谁在前谁在后
不过后来仔细想想"数据收集和聚合的时间间隔设置成一样的不合理啊!"

## 处理器配置 ##
order 用于控制处理顺序 如果不指定那么是随机的

namepass=["glob表达式",...] 如果指标名字满足其中一个表达式 那么就能通过这个处理器
fieldpass= field名数组 和上面一样 用于field而已
fielddrop= field名数组 和上面类似, 如果满足期中一个表达式那么就删除这个字段
tagdrop/tagpass tag名数组 和上面类似, 不过不通过, 那么整个记录都不会被继续传递, 而不像fieldpass fielddrop 只是忽略这个field
tagexclude taginclude 仅仅决定tag是否保留 不会影响整个记录

tagdrop tagpass 可以根据tag名和tag值一起做出判断的 具体看文档

# 支持的输入格式 #
1. influx 行协议
2. json
3. graphite
4. value
	1. 这个比较特殊, 统计的指标仅仅具有一个值, 你需要自己制定measurement名 数据类型 tags 其他的fields 等
5. nagios

# 支持的输出格式 #
用于 output.xxx 节里的 data_format
1. influx 行协议
2. json
3. graphite

我们现在使用的是 influx 全家桶, 所以当然选第一个了

# 常用的输入 #
1. exec 以脚本的输出结果作为输入
2. http_response, 以一个http请求的响应的 响应码 消耗时间 作为输入, 注意响应内容并没有! 
3. httpjson, 以一个http请求的json响应作为输入
4. mysql
5. nginx, nginx需要 ngx_http_stub_status_modules 模块
6. ping
7. redis
8. system
	1. cpu mem disk kernel NETSTAT

# 常用的输出 #
1. influxdb 输出到influxdb, 需要配置 地址 数据库 rp 用户名 密码等
2. file 输出到stdout或文件 调试用
