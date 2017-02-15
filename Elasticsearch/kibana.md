./bin/kibana

# 配置 #
配置文件 ./config/kibana.yml
https://www.elastic.co/guide/en/kibana/current/settings.html
端口 主机 basePath
elasticsearch的url
 


# 导入样本数据 #
https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html
按方法做就行

查看索引
curl 'localhost:9200/_cat/indices?v'


# Virsualize #
选择数据

进行分桶 buckets 比如工资介于 0~1500 的分一组 1500以上的分一组 , 组=桶
然后定义每一个组的指标, 比如 count, 意思是统计每一组的数量, 如果设置成sum, 那么就是求和
再可以在某个桶下再分桶 更加细化

一个可视化相当于是一个组件

# Dashboard #
可以整合多个组件在一起


# Discover #
在这个页面, 你可以选择一些index, 然后输入一个query, 然后查看查询结果
对于具有时间索引的数据, 可以选中一段时间
比如 最近n天的数据, 2016~2017 的数据

query可以是一个Lucene query或完整的json格式的查询
详情见 query语法 和 json格式的查询文档
status:200 = where status = 200
status:[400 TO 499] = where status between 400 and 499
status:[400 TO 499] AND (extension:php OR extension:html)

