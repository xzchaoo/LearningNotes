客户端通常会带来一堆的http头来告诉服务端自己的一些信息

If-modified-since or If-unmodified-since

跟服务器的对应

ETag Last-Modified

ps: CommonMiddleware 会自动帮你设置 ETag

django.views.decorators.http.condition 这个装饰器的作用是 指导django快速计算响应内容的 etag 和 last-modified
而不是采用传统的方式, 比如对于etag, 采用传统的方式的话你可能需要对全部的内容做哈希
而如果你明确指导所有的内容在第一行肯定不一样 那么完全就可以只用第一行来做哈希

condition(etag_func=None, last_modified_func=None)
etag(etag_func)
last_modified(last_modified_func)

``` 和 处理函数 接收一样的参数
def latest_entry(request, blog_id):
    return Entry.objects.filter(blog=blog_id).latest("published").published

@last_modified(latest_entry)
def front_page(request, blog_id):
    ...

```

