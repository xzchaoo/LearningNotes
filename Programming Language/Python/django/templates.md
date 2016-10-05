https://docs.djangoproject.com/en/1.10/topics/templates/
https://docs.djangoproject.com/en/1.10/ref/templates/language/

新版django支持多种模板语言, 但一般默认的就OK了
django.template.backends.django.DjangoTemplates

# 配置文件 #
看配置文件
DIRS 表示要搜索的tempaltes路径
APP_DIRS=True 表示要搜索每个app下的templates文件夹

# 使用 #
django.template.loader 定义了加载模板的方法

get_template(tempalte_name,using=None) 将会返回一个 Template 对象
可能抛出 TemplateDoesNotExist 或 TemplateSyntaxError

select_template(模板名字列表) 和上面类似, 但是是找到第一个可用的


渲染成字符串
render_to_string(模板名字,context=None,request=None,using=None)

动态生成模板
```
from django.template import engines

django_engine = engines['django']
template = django_engine.from_string("Hello {{ name }}!")
```

# DjangoTemplates 支持的参数 #
autoescape : 是否自动转义, 默认是True, 如果为False 那么需要手动转义 {{ data|safe }}
context_processors : 上下文处理器的列表
debug : 是否调试
loaders
string_if_invalid 当对象非法的时候 是否输出空白字符串
file_charset
libraries


# tags #

## for ... empty ##

## extend ##
模板继承

block 定义块 可以被覆盖


if else elif endif
```
{% if athlete_list %}
    Number of athletes: {{ athlete_list|length }}
{% elif athlete_in_locker_room_list %}
    Athletes should be out of the locker room soon!
{% else %}
    No athletes.
{% endif %}
```

for
{% cycle 'odd' 'even' %}


autoescape 被抱起来的范围都进行或不进行转义, 该元素可以嵌套使用
```
{% autoescape off %}
    Hello {{ name }}
{% endautoescape %}
```

## csrf_token ##
放到form元素里即可

## cycle ##
不停地循环返回元素
```
{% for o in some_list %}
    <tr class="{% cycle 'row1' 'row2' %}">
        ...
    </tr>
{% endfor %}

或 使用 as 创建一个别名省得每次都写那么多
<tr>
    <td class="{% cycle 'row1' 'row2' as rowcolors %}">...</td>
    <td class="{{ rowcolors }}">...</td>
</tr>
<tr>
    <td class="{% cycle rowcolors %}">...</td>
    <td class="{{ rowcolors }}">...</td>
</tr>

```

## firstof ##
## include ##
{% include "name_snippet.html" with person="Jane" greeting="Hello" %}
{% include "name_snippet.html" with greeting="Hi" only %}
## load ##

## lorem ##
产生一堆随机字符 https://docs.djangoproject.com/en/1.10/ref/templates/builtins/
{% lorem [count] [method] [random] %}

## now ##
It is {% now "jS F Y H:i" %}
{% now "Y" as current_year %}

## regroup ##
进行分组
```
cities = [
    {'name': 'Mumbai', 'population': '19,000,000', 'country': 'India'},
    {'name': 'Calcutta', 'population': '15,000,000', 'country': 'India'},
    {'name': 'New York', 'population': '20,000,000', 'country': 'USA'},
    {'name': 'Chicago', 'population': '7,000,000', 'country': 'USA'},
    {'name': 'Tokyo', 'population': '33,000,000', 'country': 'Japan'},
]

{% regroup cities by country as country_list %}

<ul>
{% for country in country_list %}
    <li>{{ country.grouper }}
    <ul>
        {% for city in country.list %}
          <li>{{ city.name }}: {{ city.population }}</li>
        {% endfor %}
    </ul>
    </li>
{% endfor %}
</ul>
```

## spaceless ##
移除多余的空格
```
{% spaceless %}
    <p>
        <a href="foo/">Foo</a>
    </p>
{% endspaceless %}
```



## comment ##
```
{% comment "Optional note" %}
被包住的内容不会被渲染
    <p>Commented out text with {{ create_date|date:"c" }}</p>
{% endcomment %}

```

## url ##
生成url


## 过滤器 ##
{{ django | title }}
{{ my_date|date:"Y-m-d" }}


### default ###
如果变量是 False 之类的值, 那么就返回 nothing
{{ value|default:"nothing" }}

### safe ###
{{ data|safe }} 转义

### date ###
日期格式化
年 [Y 长度4,y 长度2]
月 [m,n]
日 [d,j]
时 [H,h,G,g]
分 [i]
秒 [s]
这个月一共有几天 [t]
星期几 w
第几周 W
年里的第几天 [z]

### dictsort/dictsortreversed ###
字典排序
xxx_dict|dictsort:"name" 按照name来排序
字典要长成这样
```
[
    {'name': 'zed', 'age': 19},
    {'name': 'amy', 'age': 22},
    {'name': 'joe', 'age': 31},
]

{% for book in books|dictsort:"author.age" %}
    * {{ book.title }} ({{ book.author.name }})
{% endfor %}
```

使用
```
{{ value|dictsort:0 }}可以排序
[
    ('a', '42'),
    ('c', 'string'),
    ('b', 'foo'),
]
```

### escape/force_escape ###
html的转义
比如 < -> &lt;
默认情况下对于同一条管道, escape 只会被调用一次, 而且是放在最后调用(即使你把它写在中间了)
要处理这种情况可以使用 force_escape 强制执行

> 根据文档的说法, escape 的 lazy 特性被移除了, 现在要使用 conditional_escape() 才能有该特性

### floatformat ###
浮点数格式化
```
34.23234	{{ value|floatformat:3 }}	34.232
34.00000	{{ value|floatformat:"-3" }}	34
-3表示只有当小数部分全都非零才有效果 否则就成为整数
```

### urlencode ###
urlize

### 杂 ###
join:',' list用,串起来
get_digit:n 获得倒数第n个数字
first/last 返回列表的第/最后一个元素
length 字符串长度
length_is:n 判断长度是否是n
escapejs 转义js
filesizeformat 人类可读的大小
captfirst 首字母大写
center:"15" 空格填充15个字符并居中
cut:" " 移除所有空格
default_if_none 只有value严格是None才生效
divisibleby 是否除得尽
iriencode
linebreaks/linebreaksbr 将字符串里的\n替换成\<br\/\> 但前者可能会加入额外的p 要注意
linenumbers 添加行号的功能
lower/upper 大小写
ljust/rjust:n 左右填充
make_list 尽量将value做成一个list
slice 切片操作
safe 标记一个变量为不需要转义
safeseq 作用于列表的每个值
stringformat printf的格式化
pluralize 用于处理复数问题 you have message(s)
striptags 将value里的html标签移除
time 时间格式化
timesince/timeuntil 计算时间差
wordcount 统计单词个数
wordwrap 限制每行的长度, 换行
random
pprint
title 标题化 每个单词首字母大写
truncatechars/truncatechars_html 控制字符串的最大长度, 超过就显示 ...
truncatewords 控制单词个数


## 注释 ##
{# this won't be rendered #}

```
models.py
class Task(models.Model):
    def foo(self):
        return "bar"
template.html
{{ task.foo }}
```
# 自定义tag/filter #
仿照 django.contrib.humanize

# 杂 #
1. {% load humanize %} 不会被继承


# i18n #
设置 USE_I18N=True 
并且在模板里 {% load i18n %}

# l10n #
USE_L10N to True
{% load l10n %}

# 杂 #
django.contrib.humanize
static
