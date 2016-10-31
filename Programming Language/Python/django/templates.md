https://docs.djangoproject.com/en/1.10/topics/templates/
https://docs.djangoproject.com/en/1.10/ref/templates/language/

新版django支持多种模板语言, 但一般默认的就OK了
django.template.backends.django.DjangoTemplates

# 变量的解析顺序 #
a.b:
1. 如果a是map, 则b作为b
2. 如果1.没找到或不满足 那么作为方法
3. 属性

# 好消息 #
{{ block.super }} 可以在子模板里引用父模板的内容


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


# 指令/tags #

## for ... empty ##

## extend ##
模板继承


{{ block.super }} 可以在子模板里引用父模板的内容


## block ##
block 定义块 可以被覆盖


## if else elif endif ##
支持:
1. and or not in, 但是似乎不支持括号 晕了...
2. 数学比较符
3. 过滤器 if username|length > 10 

不支持数学运算符, 加减乘除等都不行!

```
{% if athlete_list %}
    Number of athletes: {{ athlete_list|length }}
{% elif athlete_in_locker_room_list %}
    Athletes should be out of the locker room soon!
{% else %}
    No athletes.
{% endif %}
```

## for ##
{% for a in list %}
{% endfor %}

还可以反向
{% for obj in list reversed %}. 反向迭代

```
forloop.counter	The current iteration of the loop (1-indexed)
forloop.counter0	The current iteration of the loop (0-indexed)
forloop.revcounter	The number of iterations from the end of the loop (1-indexed)
forloop.revcounter0	The number of iterations from the end of the loop (0-indexed)
forloop.first	True if this is the first time through the loop
forloop.last	True if this is the last time through the loop
forloop.parentloop	For nested loops, this is the loop surrounding the current one
```

遍历map, 这里有一个特殊之处, data.items 的解析顺序是 data['items'] data.items() 如果你刚好有个key叫做'items' 那就跪了...
```
{% for key, value in data.items %}
    {{ key }}: {{ value }}
{% endfor %}
```

for + empty
```
<ul>
{% for athlete in athlete_list %}
    <li>{{ athlete.name }}</li>
{% empty %}
    <li>Sorry, no athletes in this list.</li>
{% endfor %}
</ul>
```

## ifchanged/else ##
具体看文档
用于判断一个变量的值较上次迭代是否改变过
比如, 想做一个通讯录, 按照名字字典序升序, 利用 ifchange 就可以做到一个2级list的效果
```
A
	A1
	A2
	A2
B
	B1
	B2
	B3
...
```


## filter ##
被包围住的内容会进行filter
```
{% filter force_escape|lower %}
    This text will be HTML-escaped, and will appear in all lowercase.
{% endfilter %}
```


## autoescape ##
autoescape on/off 被抱起来的范围都进行或不进行转义, 该元素可以嵌套使用
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
```
{% firstof v1 v2 v3 %} 返回第一个非False的值
 
```
## include ##
引入别的文件, 文件名支持变量或字面值常量
{% include "name_snippet.html" with person="Jane" greeting="Hello" %}
{% include "name_snippet.html" with greeting="Hi" only %}

## load ##
用来tag set, 这样就引入了更多的指令和过滤器了


## lorem ##
产生一堆随机字符 https://docs.djangoproject.com/en/1.10/ref/templates/builtins/
{% lorem [count] [method] [random] %}

## now ##
当前的时间并且格式化
It is {% now "jS F Y H:i" %}
{% now "Y" as current_year %}

```
{% now "Y" as current_year %} 将年份保存成变量
{% blocktrans %}Copyright {{ current_year }}{% endblocktrans %}
```

## with ##
临时创建变量, 这些变量只在被包住的范围里有效果
```
{% with total=business.employees.count %}
    {{ total }} employee{{ total|pluralize }}
{% endwith %}

{% with alpha=1 beta=2 %}
    ...
{% endwith %}
```


## regroup ##
注意 输入的数据必须是有序的, 否则会出问题, 所以要和 dictsort 结合使用
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
    <li>{{ country.grouper }} 分组的key
    <ul>
        {% for city in country.list %} 每个key对应的列表
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
被包住的内容不会被渲染 我是注释
    <p>Commented out text with {{ create_date|date:"c" }}</p>
{% endcomment %}

```

## url ##
生成url
```
{% url 'app-views-client' client.id %}
```
但要求拼凑的url都是 通过 urls.py 里定义过的
不能让你自由拼凑, 也不能用于拼凑 QueryString

## verbatim  ##
让 django 不解释被包围住的内容, 当django和angular一起用的时候就派上用场了


# 常用过滤器 #
https://docs.djangoproject.com/en/1.10/ref/templates/builtins/#ref-templates-builtins-filters

length 获取长度
add 数字加法 或 字符串拼接 或 列表拼接
date 日期格式化
default 当输入是False之类的值时, 提供默认值
default_if_none 当输入是None时, 提供默认值
safe 表明该值不需要被转义
addslashes 在引号前面添加反斜杠
capfirst 首字母大写
center 用空白填充字符串, 使得使得字符串居中
cut 砍掉字符串左右两边的空白(也可以是其它字符)
dictsort/dictsortreversed 让一个数组按照元素的字段排序
divisibleby 是否可以整除

### date ###
注意格式化的参数用的是 PHP 的占位符 不是python的占位符
日期格式化, 不需要%
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
常和 autoescape off 一起用
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
iriencode 将IRI进行转义, 使得它可以被放在URL上作为value
linebreaks/linebreaksbr 将字符串里的\n替换成\<br\/\> 但前者可能会加入额外的p 要注意
linenumbers 添加行号的功能
lower/upper 大小写
ljust/rjust:n 左右填充
make_list 尽量将value做成一个list
slice 切片操作
safe 标记一个变量为不需要转义
safeseq 作用于列表的每个值
stringformat 对输入进行类似 printf 的格式化 但似乎只支持一个值?
pluralize 用于处理复数问题 you have message(s)
striptags 将value里的html标签移除
time 时间格式化
timesince/timeuntil 计算时间差
wordcount 统计单词个数
wordwrap 限制每行的长度, 换行
random 输入是一个list, 随机返回一个元素
pprint pprint.pprint() 调试用
title 标题化 每个单词首字母大写
truncatechars/truncatechars_html 控制字符串的最大长度, 超过就显示 ..., truncatechars_html版本的话懂得是别html元素
truncatewords 类似上面, 不过是控制单词个数

urlencode
urlize
urlizetrunc
wordcount 统计单词数量
wordwrap 如果行长度超过一定值就换行 但不会打断单词

yesno value|yes:"是,否,不确定" True->"是" False->"否" None->"不确定"



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

# static #
load static
static 'images/a.jpg'

{% get_static_prefix %}

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




# 自定义tag/filter #
仿照 django.contrib.humanize

1. 在某个app(这个app必须被安装哦)目录下 创建目录 templatetags, 记得要有 __init__.py 来保证它是一个包
2. 然后在在目录下, 创建一个 xxx.py 脚本
3. 编写xxx.py, 必须满足一定的规则
```
from django import template
register = template.Library() #该模块必须要有这么一个属性


# 这样进行注册 名字默认就是函数名
@register.filter
def foo(value, arg):
    return value + arg

# register.filter('foo', foo) 这样也是可以的

```
4. 导入 {% load xxx %}
5. 使用

>Development server won’t automatically restart
After adding the templatetags module, you will need to restart your server before you can use the tags or filters in templates.


对于那些期待输入是一个字符串的filter, 可以:
```
from django import template
from django.template.defaultfilters import stringfilter

register = template.Library()

@register.filter
@stringfilter #这个过滤器会自动将输入转成字符串
def lower(value):
    return value.lower()
```

