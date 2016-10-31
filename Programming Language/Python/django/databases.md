CONN_MAX_AGE 控制一个数据库连接可以用多久
如果设置成0 的话那么用完就会真的关闭
如果设置成30 那么一个连接最多可以用30秒 如果现在还不足30秒就会重复利用
如果设置成None 相当于是时间无穷大

在每个请求结束之后 django 检查连接是否已经超过上面指定的时间 如果是就关闭它

# mysql #
MySQLdb

```
DATABASES = {
    'mysql': {
        'ENGINE': 'django.db.backends.mysql',
		'NAME':'bilibili',
		'USER':'xzchaoo',
		'PORT'3306,,
		'HOST':'mysql1.cngzps6tvbak.us-west-2.rds.amazonaws.com',
		'PASSWORD':'xzc7086204511',
    }
}
```
