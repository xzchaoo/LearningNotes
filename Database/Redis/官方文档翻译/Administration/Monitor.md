http://www.redis.io/topics/latency-monitor
# Redis latency monitoring framework #

Redis是单线程的

# Latency Monitoring #
For all this reasons, Redis 2.8.13 introduced a new feature called Latency Monitoring, that helps the user to check and troubleshoot possible latency problems. Latency monitoring is composed of the following conceptual parts:
Latency hooks that sample different latency sensitive code paths.
Time series recording of latency spikes split by different event.
Reporting engine to fetch raw data from the time series.
Analysis engine to provide human readable reports and hints according to the measurements.

配置阀值
CONFIG SET latency-monitor-threshold 100
一旦有事件的耗时超过这个时间, 它就会被统计下来
将其设置成0 会禁用监视功能

latency latest 会返回当前的统计
latency history event-name 返回这类事件的最近160元素, 每个元素的组成(发生的时间,耗费的时间)
latency reset [event-name, event-name] 可以清除这些类事件的统计
latency graph event-name 可以画出一个简单的图像, 横坐标是时间序列, 纵坐标是耗费的时间
latency doctor 让redis给你优化的建议

慢日志
slowlog-log-slower-than 10000 超过10000微秒的都认为是慢查询
slowlog-max-len 128 最多保存128条慢日志
slowlog get 会返回一些比较慢的命令