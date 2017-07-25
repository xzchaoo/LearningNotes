https://github.com/taylorhakes/fecha

# 介绍 #
JS的时间格式化和解析, 相比 [moment](http://momentjs.cn/) 更简单  
只能用于时间的 格式化 解析, 支持I18N, 不支持时间的修改操作

# 用法 #
**注意构造函数中Date的月份是以0开始的**

fecha.format(<Date Object>, <String Format>);


fecha.parse('February 3rd, 2014', 'MMMM Do, YYYY'); // new Date(2014, 1, 3)

