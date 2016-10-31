# filter参数 #

## 处理字符串 ##
如果我们的过滤器专注于处理字符串输入
那么可以用 django.template.defaultfilters.stringfilter 修饰它, django 会自动将参数转成字符串, 我们的函数应该要指是单参数的吧!?

1. 原始字符串, str 或 unicode
2. 安全字符串, 被标记为安全的字符串, SafeBytes SafeText 它们都是 SafeData 的自雷



## is_safe ##
默认是 False

is_safe=True 表明我自己保证返回值一定是安全的
如果你的过滤器不会使得字符串变地不安全
比如不引入 <>'"& 等符号

is_safe=True 如果一个安全字符串被传进来, 那么结果也是安全的, 如果一个不安全的字符串被传进来, django 会先escape它, 然后再传进来
当你处于 autoescape=True 的环境时, 你的返回值总是会被escape, 即使你的输入值是escape的

=True 那么返回值会被自动转成 SafeText

如果你的返回值是 int , 那么必须 is_safe=False 否则得到的不是int

django.utils.safestring.mark_safe().



## expects_localtime ##
如果你期待输入是一个 datetime
那么通常需要设置 expects_localtime = True
如果第一个参数是一个 timezone aware datetime, django 就会自动将它转成本地时间

## needs_autoescape ##
默认是False
设置成True之后, 当该过滤器被调用的时候, 就会传入一个额外的参数autoescape 表明当前的autoescape状态

通常推荐将 autoescape的默认是设置成 True, 这样你作为一个普通的python方法调用的时候也不会有太多问题

> It is recommended to set the default of the autoescape parameter to True, so that if you call the function from Python code it will have escaping enabled by default.


```
@register.filter(needs_autoescape=True)
def initial_letter_filter(text, autoescape=True):
    first, other = text[0], text[1:]
    if autoescape:
        esc = conditional_escape
    else:
        esc = lambda x: x
    result = '<strong>%s</strong>%s' % (esc(first), esc(other))
    return mark_safe(result)
```

django.utils.html.conditional_escape 如果传入的字符串已经不是一个安全版本 那么就转成安全的

```
from django.template.defaultfilters import linebreaksbr, urlize

@register.filter(needs_autoescape=True)
def urlize_and_linebreaks(text, autoescape=True):
    return linebreaksbr(
        urlize(text, autoescape=autoescape),
        autoescape=autoescape
    )
```

# 自定义tag #
tag通常会接收 字符串 和 模板变量

## simple_tag ##
simple_tag 会将字符串和变量自动解析然后传进来, 然后你处理完之后返回一个字符串作为结果

```
import datetime
from django import template

register = template.Library()

@register.simple_tag
def current_time(format_string):
    return datetime.datetime.now().strftime(format_string)
```





format_html() 如果你产生了一些html字符串, 那么就用它包装一下返回值
mark_safe() 如果你确信你的结果是绝对安全的就用它包装一下返回值


takes_context=True 表明你的tag需要访问context, context会以kwargs的方式传进来, 你要有一个参数叫做 context
```
@register.simple_tag(takes_context=True)
def current_time(context, format_string):
    timezone = context['timezone']
    return your_get_current_time_method(timezone, format_string)
```

# 高级定制tag #
基于Node, Node组成了一棵树
