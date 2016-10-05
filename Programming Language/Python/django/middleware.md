```
def simple_middleware(get_response):
    # One-time configuration and initialization.

    def middleware(request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.

        response = get_response(request)

        # Code to be executed for each request/response after
        # the view is called.

        return response

    return middleware
```

```
class SimpleMiddleware(object):
    def __init__(self, get_response):
        self.get_response = get_response
		如果抛出了 MiddlewareNotUsed. 异常 那么该中间件就不生效
        # One-time configuration and initialization.

    def __call__(self, request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.

        response = self.get_response(request)

        # Code to be executed for each request/response after
        # the view is called.

        return response
```


中间件的顺序 顺序错误的话会导致某些中间件不生效
https://docs.djangoproject.com/en/1.10/ref/middleware/


引擎有4种:
1. 基于cookie
	1. SESSION_ENGINE='django.contrib.sessions.backends.signed_cookies'
	2. 数据会进行签名 防止篡改
2. 基于文件 指定存储的位置
	1.  SESSION_ENGINE='django.contrib.sessions.backends.file'
	2.  SESSION_FILE_PATH 文件存储位置 这应该是个目录吧?
3. 基于缓存
4. 基于db (默认) django_session 表 

可以调整
cookie的有效期
httponly
名字
安全
序列化的方式 SESSION_SERIALIZER 默认是使用json格式
另外session的key只能是字符串, 如果你 request.session[0]=123
虽然好像key是一个int, 但实际上当下一个请求的时候, 你必须通过
request.session['0'] 来获取 切记切记!

如果key不存在则会有 KeyError


# SessionStore  #
exists()
create()
save()
delete()
load()
clear_expired()


# 在View之外使用session #
```
from importlib import import_module
from django.conf import settings
SessionStore = import_module(settings.SESSION_ENGINE).SessionStore

这3行是为了获得 SessionStore, 拿到它之后就可以用上面的方法了
```

# 触发session的修改 #
request.session['foo']['bar'] = 'baz' 是不会触发session的修改的! 千万记得
一定要 request.session['foo'] = ... 才会

当然你也可以手动设置 request.session.modified = True
这样中间件会认为session修改过了

当设置 SESSION_SAVE_EVERY_REQUEST=True (默认是False) 的时候 每个请求无论session有没有改变都会触发session保存, cookie也会每个请求都发送
 

