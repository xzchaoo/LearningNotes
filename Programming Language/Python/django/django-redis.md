利用 redis 作为 cache
目前官方不支持 redis 作为cache 所有有这个项目
http://niwinz.github.io/django-redis/latest/


基于 redis-py

# 安装和配置 #
pip install django-redis

```
CACHES = {
    "default": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://127.0.0.1:6379/1",
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
        }
    }
}
```

## 配置为session ##
SESSION_ENGINE = "django.contrib.sessions.backends.cache"
SESSION_CACHE_ALIAS = "default"


## 配置 ##
PICKLE_VERSION pickle的协议版本号
SOCKET_CONNECT_TIMEOUT 连接超时
SOCKET_TIMEOUT 读写取超时
"COMPRESSOR": "django_redis.compressors.zlib.ZlibCompressor" 压缩
IGNORE_EXCEPTIONS:True
DJANGO_REDIS_IGNORE_EXCEPTIONS:True

DJANGO_REDIS_LOG_IGNORED_EXCEPTIONS = True 忽略的异常 记录日志
DJANGO_REDIS_LOGGER = 'some.specified.logger' 使用哪个logger

# 用法 #
from django.core.cache import cache
cache.set("foo", "value", timeout=25)
cache.ttl("foo")
25
cache.ttl("not-existent")
0

cache.set("key", "value", timeout=None) 永不过期 默认的配置
cache.persist("foo") 去除ttl
cache.expire("foo", timeout=5) 设置ttl
cache.ttl("foo") 获得ttl

分布式锁
```
with cache.lock("somekey"):
    do_some_thing()
```

key的模糊匹配
```
from django.core.cache import cache
cache.keys("foo_*")
["foo_1", "foo_2"]
```


key的模糊匹配 迭代器
```
>>> from django.core.cache import cache
>>> cache.iter_keys("foo_*")
<generator object algo at 0x7ffa9c2713a8>
>>> next(cache.iter_keys("foo_*"))
"foo_1"
```

批量删除
>>> from django.core.cache import cache
>>> cache.delete_pattern("foo_*")

set方法支持一些redis原生参数
```
>>> from django.core.cache import cache
>>> cache.set("key", "value1", nx=True)
True
>>> cache.set("key", "value2", nx=True)
False
>>> cache.get("key")
"value1"
```

获得原生连接
```
>>> from django_redis import get_redis_connection
>>> con = get_redis_connection("default")
>>> con
<redis.client.StrictRedis object at 0x2dc4510>
```


可以配置连接池
3.13.1. Configure default connection pool

mvn gcloud:run 运行测试

mvn gcloud:deploy 部署

http://speedy-realm-133106.appspot.com/
