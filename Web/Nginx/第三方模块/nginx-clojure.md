# 剪枝流弊 #
https://github.com/nginx-clojure/nginx-clojure
官方文档多看几遍应该就会了
可以让nginx集合java代码
可以用java代码来处理一些问题 [ 验证权限, 增删改查headers, 修改body, 直接处理请求, 修改变量(可以用自己的逻辑来做负载均衡) ]
每个工作者线程会有一个JVM, 提供了工作者线程之间的一些通信方式(订阅, 广播)
