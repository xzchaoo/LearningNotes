http://play.grafana.org/dashboard/db/1-promcon-dashboard

只能使用influxDB的开源版, 单机版, 不支持集群, 不能扩展

# 注意 #
1. 查询的时候 字符串要用 单引号 不要用双引号


# InfluxDB #
https://github.com/influxdata/influxdb
https://portal.influxdata.com/downloads#influxdb

## 安装 ##
https://portal.influxdata.com/downloads#influxdb
https://docs.influxdata.com/influxdb/v1.2/introduction/installation/


```
wget https://dl.influxdata.com/influxdb/releases/influxdb_1.2.1_amd64.deb
sudo dpkg -i influxdb_1.2.1_amd64.deb
```

## 启动 ##
service influxdb start
8086 端口用于 http api
8088 端口用于RPC服务 back restore

influxdb -config xxx.conf

INFLUXDB_CONFIG_PATH 也可以用于控制配置文件的路径

influxd config 打印出默认配置

## 配置 ##
/etc/influedb/influxdb.conf


## 开始学习 ##

默认绑定端口到 localhost:8086

### 创建数据库 ###
在安装了 influxdb 的机器上执行 influx 进入一个特殊的命令行
执行:
```
CREATE DATABASE mydb
创建数据库

SHOW DATABASES
显示所有数据库

use mydb
切换到mydb数据库

```

### 写数据 ###
数据由时间序列组织而成, 每个数据包含:
1. 1个测量值, 比如 CPU使用率 温度
2. 0个或多个点
	1. 每个点描述了一个指标的样本
	2. 每个点包含一个 time 时间戳
	3. 每个点包含一个 measurement 测量名, 比如 "CPU使用率"
	4. 每个点包含至少一个 key/value 对, 比如 value=3.14
	5. 每个点包含0个或多个tag

概念上认为:
measurement = SQL Table
tags/fields = SQL Column
tag具有索引的特性 fields没有索引的特性

不需要提前定义schema

每个点写入``InfluxDB``的时候使用行协议, 具有如下的格式:
```
<measurement>[,tag-key1=tag-value1...] <field-key1>=<field-value1>,[<field-key2>=<field-value2>...] [unix-nano-timestamp]
```
如果不提供时间就是以服务器当前的本地时间

几个例子
```
cpu,host=serverA,region=us_west value=0.64
cpu是测量名
host和region是tag
value=0.64 是field

payment,device=mobile,product=Notepad,method=credit billed=33,licenses=3i 1434067467100293230
stock,symbol=AAPL bid=127.46,ask=127.48
temperature,machine=unit42,type=assembly external=25,internal=37 1434067467000000000
```

#### 使用命令行写数据 ####
INSERT <measurement>,tags fields
如果不提供时间就是以服务器当前的本地时间

### 查询数据 ###
```
select "host", "region", "value" from "cpu";
SELECT * FROM "temperature"
```

# 核心概念 #
https://docs.influxdata.com/influxdb/v1.2/concepts/glossary/
https://docs.influxdata.com/influxdb/v1.2/concepts/key_concepts/

1. aggregation, 一个InfluxQL的函数, 可以用于返回一系列点的聚合结果
2. batch, 通过HTTP API, 一次性提交多个点, 提高效率, 每一行用0X0A隔开
3. continues query (CQ), 通过CQ可以定期重新聚合数据
4. database, 逻辑划分数据
5. duration, 是 retention policy 的一个属性, 数据可以存放多久, 过期的数据会被删除
6. field, 没有索引, 通常不会根据它们进行搜索, 只会根据它们进行聚合运算
7. field key, 字符串
8. field set, 一个点上所有的 field keys 和 field values
9. field value, 字符串 浮点数 整数 布尔型, 一个 field value 总是和一个时间戳相关联
10. function, 聚合函数 选择函数 转换函数
11. line protocol 将数据插入InfluxDB时用到的文本格式
12. measurement, 可以类比数据库的表的概念
13. node 一个独立的 influxd 进程
14. now()
15. point, 是 influxdb 的一个数据结构, 它包含了在同一个 series 里的多个field, 每个point由时间戳和series唯一标识, 如果你在后来往数据库里写入了一个具有相同标志的point的话, 那么他们会被合并union
	1. 简单的说, 插入influxdb 的一条记录就是一个point
16. query
18. series, 指的是具有相同 measurement, tag set 和 retntion policy 的数据的集合
19. selector 具备从多个点中选出一个点的能力, 比如选第一个或最后一个点
20. schema 描述数据是如何在influxdb组织的
21. series cardinality, series的cardinality
22. retention policy 用于描述这个数据保存多久 多少份副本 等, 开源版只能有一个节点, 也就只能有一个副本
23. tag
24. tag key
25. tag set
26. tag value 
27. timestamp 
28. user
	1. 管理员具有最高权限
	2. 非管理员
transformation



## point ##
插入的一条数据称为一个点
一个点由 measurement, tag set, 时间戳 唯一标识
如果你提交了两个具有相同标识的点, 那么fields会被``union``, 如果有重复的field 那么后面的覆盖前面的



## 索引 ##
fields 没有索引, tags有索引
如果你将field用于where条件语句, 那么会导致一次全表扫描

# 杂 #
默认情况下每个 measurement 属于一个 autogen retention eplicy
autogen使得数据无限时间保存, 副本集为1个
series是 具有相同 retention policy, measurement, tag set 的数据

InfluxDB uses HTTP solely as a convenient and widely supported data transfer protocol. Modern web APIs have settled on REST because it addresses a common need. As the number of endpoints grows the need for an organizing system becomes pressing. REST is the industry agreed style for organizing large numbers of endpoints. This consistency is good for those developing and consuming the API: everyone involved knows what to expect. REST, however, is a convention. InfluxDB makes do with three API endpoints. This simple, easy to understand system uses HTTP as a transfer method for InfluxQL. The InfluxDB API makes no attempt to be RESTful.
HTTP response summary

# 使用HTTP API #

## 常用的参数 ##
支持http basic 认证

查询参数:
db 用于指定数据库
u和p可以用于指定用户名和密码
pretty=true 是否缩进
epoch=ns u ms s m h
chunked=true
chunk_size
rp=使用哪个RP

请求体:
q=语句
语句可以用;隔开 这样就可以传送多条语句了

请求提支持占位符
--data-urlencode="q=select * from xxx where t1 = $t1"
--data-urlencode='params={"t1":"asdf"}'


## 创建数据库 ##
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"

## 写数据 ##
遵守行协议
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000'

## 批量写入 ##
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server02 value=0.67
cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257
cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257'
用0x0A隔开 换行符


支持的时间:
unix time 纳秒时间
可以省略 默认使用服务器的当前时间

## 查询支持的参数 ##
pretty = true 有缩进
db 使用哪个数据库
q 查询语句
epoch=s 表示时间格式是 unix 时间戳 单位秒, 默认的时间格式是 RFC3339, 2015-08-04T19:05:14.318570484Z
chunked=true chunk_size=2000 可以使用chunk的方式来返回数据, TODO 这里不太懂

如果响应的记录超过1W条, 那么响应会有 partial:true 表示这是一部分
1W条可以通过 [http] 下的 max-row-limit的配置来修改


## 查数据 ##
本质是发一个post请求到/query端口
可以通过查询参数或表单数据携带配置

curl -G 'http://localhost:8086/query?pretty=true' --data-urlencode "db=mydb" --data-urlencode "q=SELECT \"value\" FROM \"cpu_load_short\" WHERE \"region\"='us-west'"

结果形如
```
{
    "results": [
        {
            "statement_id": 0,
            "series": [
                {
                    "name": "cpu_load_short",
                    "columns": [
                        "time",
                        "value"
                    ],
                    "values": [
                        [
                            "2015-01-29T21:55:43.702900257Z",
                            2
                        ],
                        [
                            "2015-01-29T21:55:43.702900257Z",
                            0.55
                        ],
                        [
                            "2015-06-11T20:46:02Z",
                            0.64
                        ]
                    ]
                }
            ]
        }
    ]
}
```
注意到results是一个数组, 因为支持下面提到的多个查询

## 多个查询 ##
curl -G 'http://localhost:8086/query?pretty=true' --data-urlencode "db=mydb" --data-urlencode "q=SELECT \"value\" FROM \"cpu_load_short\" WHERE \"region\"='us-west';SELECT count(\"value\") FROM \"cpu_load_short\" WHERE \"region\"='us-west'"
主要是q的内容用分号隔开



## HTTP响应 ##
2xx 成功
4xx 请求错误, 比如你的行协议错误
5xx 服务端错误

## API ##
https://docs.influxdata.com/influxdb/v1.2/tools/api/#query
/ping /query /write

### /query ###
由一些命令需要GET 有一些需要POST

### /write ###


# 认证与授权 #
https://docs.influxdata.com/influxdb/v1.2/query_language/authentication_and_authorization/

# InfluxQL #

## 数据探索 ##

create database db1;
show databases;
use xxxDB;

### select ###
from 可以接受多个 measurements

当没有合适的时间可以返回的时候就会返回时间戳0

select mean(load) from "cpu" group by "host","db"
```
> select mean(load) from "cpu" group by "host","dc"
name: cpu
tags: dc=bj, host=agg
time mean
---- ----
0    62.5

name: cpu
tags: dc=sh, host=xzc
time mean
---- ----
0    13.5


```
还可以有 group by *

### where ###
fields 和 time 支持 大部分的数学运算 = <> != > < >= <=
tags 只支持 = <> !=

支持 and or 括号



### group by ###
通常用于:
group by 某个tag
group by time(1d) 表示每天作为一个组

也支持多个分组指标: 指定的熟悉怒不重要
group by t1, t2, t3, time(1m)

特殊的
group by * 表示用所有的tag参与group

按照时间间隔排序
group by time(时间间隔比如5m [,offset_interval])
如果你使用了group by time() 那么你必须提供一个 where 包含了time
offset_interval用于控制时间分组的偏移

```
select ... from ... where time > '2017-03-15'
group by time(10m) fill(none)
```
意思是每10分钟分为一组, 有可能出现这样的情况: 某个10分钟内没有任何数据, 这时fill就派上用场了
使用none选项, 也就是完全删除这一行记录
null选项的话 这一行记录留空
linear的话会使用线性插值
previous 会使用上一个值来代替
还可以提供一个常数, 表示用这个常数来填充

但是要注意下面的情况:
如果没有任何数据满足where的条件, 那么返回结果是空的

### into ###
与 select 一起使用
用于将结果保存到某个 measurement 里
```
SELECT_clause INTO <measurement_name> FROM_clause [WHERE_clause] [GROUP_BY_clause]
```

into <database_name>.<retention_policy>.<measurement_name>

用途:
1. 缩减像素采样
2. 生成每天的备份, 避免每次查询都需要进行 逻辑操作
3.
 
### order ###
默认是按照时间增序
ORDER BY time DESC

### limit/slimit ###
limit 1
限制每个series的结果1条

slimit 用于限制series的个数
当你group之后会产生多余一个series 这时就可以用slimit

如果不执行group的话一般只会返回一个series

```
SELECT MEAN("water_level") FROM "h2o_feet" WHERE time >= '2015-08-18T00:00:00Z' AND time <= '2015-08-18T00:42:00Z' GROUP BY *,time(12m) SLIMIT 1
```
group by *
表示根据所有的tags的组合进行分组
time(12) 表示对于上面的每一组再进行12m的分组

### offset/soffset ###
和 limit/slimit 配合使用 用于跳过


## schema探索 ##
show databases
show retention policies [on <database>]
SHOW SERIES [ON <database_name>] [FROM_clause] [WHERE <tag_key> <operator> [ '<tag_value>' | <regular_expression>]] [LIMIT_clause] [OFFSET_clause]

measurement,tag set, retention policy完全一样就算是同一个 series

show measurements [on....]

通过下面的语句就可以查看一个 measurement 的定义
show tag keys
show tag values
show field keys

## 数据库管理 ##
create database "name";
use <name>
drop database "name"

CREATE DATABASE <database_name> [WITH [DURATION <duration>] [REPLICATION <n>] [SHARD DURATION <duration>] [NAME <retention-policy-name>]]

可以指定 duration (保存数据的时间)
副本集的数量 默认是1, 单机版的话不需要考虑这个问题

drop series from "某个measurement" where ...
drop series where ...
delete from 某个measurement
delete where ...
与drop不同 它不会删除measurement本身的定义

drop measurement 名字

drop shard

### Retention Policy 管理 ###
CREATE RETENTION POLICY <retention_policy_name> ON <database_name> DURATION <duration> REPLICATION <n> [SHARD DURATION <duration>] [DEFAULT]
duration 决定数据库保存这个数据多久, INF表示无限, 最小值是1h
DEFAULT 表示这个RP是否成为指定数据库的默认RP

alert retention policy ...
drop retention policy

create retention policy "rp_7d" on "telegraf" duration 7d replication 1 default;



## Continues Query ##
自动的定期的运行, 并且会将执行结果保存到某个measurement里
```
CREATE CONTINUOUS QUERY <cq_name> ON <database_name>
BEGIN
  <cq_query>
END
```
对cq_query有一些要求:
1. 使用function
2. 使用into语句
3. 有 group by time() 语句
```
SELECT <function[s]> INTO <destination_measurement> FROM <measurement> [WHERE <stuff>] GROUP BY time(<interval>)[,<tag_key[s]>]
```
cq可以不用携带where语句, 因为会自动的加入time相关的判断来保证数据不重叠

用途:
使用cq来对数据进行采集
```
CREATE CONTINUOUS QUERY "cq_basic" ON "transportation"
BEGIN
  SELECT mean("passengers") INTO "average_passengers" FROM "bus_data" GROUP BY time(1h)
END
```

使用cq来downsample数据到另外一个RP里
```
CREATE CONTINUOUS QUERY "cq_basic_rp" ON "transportation"
BEGIN
  SELECT mean("passengers") INTO "transportation"."three_weeks"."average_passengers" FROM "bus_data" GROUP BY time(1h)
END
```
一个RP可以用于决定数据存活多久, 比如我们先规定:
直接收到的数据保存6小时, 将每个小时内的数据进行downsample操作, 保存7天
将每天内的数据进行downsample操作, 保存30天
将每个月内的数据进行downsample操作, 保存1年

直接收到的数据保存到: xdb.xis_hours.xdata
小时精度的数据保存到: xdb.seven_days.xdata
天精度的数据保存到: xdb.one_month.xdata
月精度的数据保存到: xdb.one_year.xdata

SHOW CONTINUOUS QUERIES
DROP CONTINUOUS QUERY <cq_name> ON <database_name>
想要修改的花必须drop再create


# 时间格式 #
rfc3339_date_time_string
rfc3339_like_date_time_string
epoch_time

时间支持简单的数学运算
比如 now()-1d

启动命令行的时候可以指定时间的格式, 方便浏览
influxdb precision rfc3339

# Downsampling and Data Retention #
https://docs.influxdata.com/influxdb/v1.2/guides/downsampling_and_retention/
Downsampling 缩减像素采样
Data Retention 数据保留

主要用于解决数据不断增长的问题, 可以对旧数据做采样, 保留低精度的聚合结果, 比如每10分钟的数据合并为1个

InfluxDB 提供了两个特性: Continues Queries (CQ), Retention Policies (RP)
他们可以自动化执行 缩减像素采样 和 删除过期数据

## CQ的定义 ##
Continues Query 是一个InfluxQL的查询, 它会自动的定期的执行, CQ要求select里有一个函数, 并且有 group by time()

```
create continuous query "cq_1h" on "job" begin
	select mean("avg_duration") as "duration", count("duration") as "count"
	into "rp_7d"."job.count"
	from "job.count"
	group by time(1h)
end
```

1. 直接插入的数据保存1天
2. 下面的数据保存7天, 每1小时生成一次
create continuous query "cq_1h" on "job" begin select mean("duration") as "duration", count("duration") as "count" into "rp_7d"."7d.job.count" from "rp_1d"."1d.job.count" group by *, time(1h) end
3. 下面的数据保存30天, 每1天生成一次
create continuous query "cq_1d" on "job" begin select mean("duration") as "duration", sum("count") as "count" into "rp_30d"."30d.job.count" from "rp_7d"."7d.job.count" group by *, time(1d) end
4. 下面的数据保存52周, 每7天生成一次
create continuous query "cq_1w" on "job" begin select mean("duration") as "duration", sum("count") as "count" into "rp_52w"."52w.job.count" from "rp_30d"."30d.job.count" group by *, time(1w) end


insert into rp_1d 1d.job.count,userId=1,jobId=1,result=success duration=60
insert into rp_1d 1d.job.count,userId=1,jobId=1,result=success duration=90
insert into rp_1d 1d.job.count,userId=1,jobId=1,result=error duration=120
insert into rp_1d 1d.job.count,userId=1,jobId=1,result=error duration=150

select mean("duration") as "duration", count("duration") as "count" into "rp_7d"."7d.job.count" from "rp_1d"."1d.job.count" group by *, time(1h)

select mean("duration") as "duration", count("duration") as "count" into "rp_7d"."job.count" from "rp_1d"."job.count" group by *, time(1h)

select mean("duration") as "duration", count("duration") as "count" into "rp_7d"."job.count" from "rp_1d"."job.count" where time>=now() -1h and time<now() group by *, time(1h)


## RP定义 ##
retrntion policy 是 influxdb 数据结构的一部分, 可以用于描述influxdb保存这个数据多久
influxdb比较本地服务器的时间戳和你的数据的时间戳
一个服务器可以有多个RP, 每个RP必须是唯一的

https://docs.influxdata.com/influxdb/v1.2/query_language/continuous_queries/
https://docs.influxdata.com/influxdb/v1.2/query_language/database_management/#retention-policy-management

create retention policy rp_1d duration 1d replication 1

## 例子 ##
假设有如下的数据
```
name: orders
------------
time			               phone	 website
2016-05-10T23:18:00Z	 10 	   30
2016-05-10T23:18:10Z	 12 	   39
2016-05-10T23:18:20Z	 11 	   56
```

Goal Assume that, in the long run, we’re only interested in the average number of orders by phone and by website at 30 minute intervals. In the next steps, we use RPs and CQs to:
1. Automatically aggregate the ten-second resolution data to 30-minute resolution data
2. Automatically delete the raw, ten-second resolution data that are older than two hours
3. Automatically delete the 30-minute resolution data that are older than 52 weeks

# 常见函数 #
count

# 行协议 #
https://docs.influxdata.com/influxdb/v1.2/write_protocols/line_protocol_reference/#line-protocol
<measurement>,[tags] field,[fields] [timestamp]

fields 是没有索引的
tags是有索引的

支持的数据类型
float 1 1.0 1e+78
integer 123i, 千万注意需要带i结尾
string 最长64kb
boolean t T true True TRUE f F false False FALSE
timestamp 2001-01-01T01:01:01.85475806Z

插入的是 float
m value=1
m value=1.0

插入的是 integer
m value=1i

插入的是 字符串
value="我是字符串"

插入boolean
value=true

field的类型不能冲突, 如果之前插入了一个float 那么以后就不能插入string

所有双引号括起来的都是字符串

特殊字符
```
,
=
空格
```
需要用\进行转义

# 与SQL的对比 #
now()

支持特殊的时间语法
w 周
d 天
h 小时
m 分
s 秒



## count ##
计算非空的记录数量
count(fiekd-key1)
count(*) 这个会导致返回多个column, 然后每个column的值是自己的column的count的结果

注意和 fill 一起用的时候会出现的空值问题

## mean ##
平均值

## median ##
中位数, 相当于 precentile(field-key,50)

## precentile ##
百分位数
用于描述: 所有数据从小到大排序, 排在第n%的是

## mode ##
返回频数最大的值

## spread ##
极差

sum 求和
stddev 标准差
first/last(fkey[,tkey]) 按照时间戳的第一个值
max min

precentile(fkey, N)
sample(key, N) 随机返回N个值

特殊:
top
bottom(fkey, n) 返回最小的n个

bottom(fkey, tkey, n) 假设有x个tags, 那么返回的结果条数只可能是x或n
它的作用是将数据按照给定的tkey进行分组, 然后返回每组的bottom(fkey,1), 总体最多n组

cumulative_sum(fkey)

cumulative_sum(func(fkey))
func可以是 count mean median mode sum first last min max percentile

derivative(fkey, unit) 返回该key的变化率, 导数

DIFFERENCE
返回两个连续值的的变化情况
假设原来的值是:
```
1
2
2
1
```
那么结果是
```
1
1
-1
```
第一个值没有对应的变化




## 杂 ##
distinct
elapsed 返回两个记录的时间差
moving_average()
holt_winters 是一种预测算法

# 命令行 #
https://docs.influxdata.com/influxdb/v1.2/tools/shell/
influx

参数
-host
-port
-username
-password

-precision 'rfc3339|h|m|s|ms|u|ns' 时间精度
-version

-ssl

-database 'db' 初始化连接的数据库
-execute 'command' 连接并执行语句然后退出
-format 'json|csv|column' 结果的格式
-pretty 用于json格式


导入文件
-compressed
-import
-path

## 导入文件 ##
influx -import -path=xxx.txt -precision=s

## 命令 ##
show databases;
use <db>;

insert ...
insert into ...
select ...

exit

format <format> 用于修改响应设置
precision <format> 用于修改时间精度

settings 打印出当前的命令行的一袭而配置

# 客户端 #
influxdb为很多语言都提供了客户端
https://github.com/influxdata/influxdb-java
如果没有对应的客户端那就用http客户端自己做一个吧...


# schema设计 #
1. 经常用于查询的字段放到tag里
2. tag的可选值最好是有限的, 比如 中国的34个省
3. tag总是被理解为string
4. tag支持索引
5. field不能索引, 但是可以有多重数据类型, 并且可以用于函数参数
5. 不要在measurement的名字上编码数据
6. 比如不要这样: a.b.c.d.user.102 表示用户102相关的指标 , 而是要这样, a.b.c.d.user 加上 tag: user=102 这样
7. 一个tag里不要存放复合数据, 比如要存放经纬度, 那么最好分成两个

# 认证与授权 #
https://docs.influxdata.com/influxdb/v1.2/query_language/authentication_and_authorization/

启动 http basic 认证
/etc/influxdb/influxdb.conf
auth-enabled=true

可以使用 basic 认证 或 将 用户名和密码房到 u和p 的查询参数里
也可以将u和p放到body里

命令行认证
1. 
export INFLUX_USERNAME=...
export INFLUX_PASSWORD=...
2. 
或使用 -username -password 选项
3. 
进入命令行之后使用auth

## 授权 ##
密码需要用单引号括起来
管理员用户 非管理员用户

创建一个管理员
create user <username> with password <password> with all privileges;

授予权限
grant all privileges to <username>
grant [read,write,all] on <database> to <username>

取消权限
revoke all privileges from <username>
revoke [read, write, all] on <database> from <username>

显示所有用户
show users

创建一个普通用户
create user <username> with password <password>

显示一个用户有的权限
show grants for <username>

修改用户的密码
set password for <username> = '<password>'

删除用户
drop user <username>

认证
auth <username> <password>
或者执行 auth, 然后再按照提示输入用户名和密码

http认证
curl "http://localhost:8086/query?pretty=true&db=NOAA_water_database" -u xzc:xzc -d "q=select * from h2o_feet limit 1"
-u username:password 会转换成basic认证
