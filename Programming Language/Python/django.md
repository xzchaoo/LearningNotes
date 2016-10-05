# model #
models.CharField(max_length=30)

如果不提供id就会自动添加一个id主键 但不会自增

https://docs.djangoproject.com/en/1.10/topics/db/models/


# 安装 #
pip install Django

import django
django.VERSION
(1, 7, 6, 'final', 0)


# 创建一个项目 #
假设项目叫做d
django-admin startproject d

## 项目结构 ##


```
d/
	d/
		urls.py
		settings.py 各种设置
		wsgi.py
	a1/
		admin.py
		apps.py
		models.py
		tests.py
		views.py
	templates/ 模板
	manage.py 管理脚本
	
```

# 新建app #
manage.py startapp a1

# 运行服务器 #
manage.py runserver 8080
仅仅用于开发阶段


# manage.py #
清空数据库
manage.py flush

创建管理员
manage.py createsuperuser

manage.py shell
dbshell


# 获取url参数 #
```
def j1(req):
    a = int(req.GET.get('a', 1))
    b = int(req.GET.get('b', 2))
    c = a + b
    return JsonResponse({
        'id': 1,
        'name': '许志超',
        'c': c
    })
```

路径参数
url(r'^j2/(\d+)/(\d+)$', a1_view.j2, name='j2'),
name 可以用于引用

# 模板 #
如果 APP_DIRS=True 那么每个app下的templates也会被搜索

下面3个地方可以放模板文件
根目录下的 templates
app目录下的 templates
app目录下的 templates/app的名字/



{{name}}

{% block title %}默认标题{% endblock %}
block 可以被子模板替换掉

{% include 'nav.html' %}
引入其他文件

{% url name 其他参数 %}

{% extends 'base.html' %}
继承模板

if
```
{% if not xxx%}yyy{% else %}zzz{% endif %}
```
数学表达式 and, not, or, in, not in


for循环
```
{% for i in TutorialList %}
{{ i }}
{% endfor %}
```
for循环的变量
forloop.counter 从1开始
forloop.counter0 从0开始
forloop.revcounter	索引从最大长度到 1
forloop.revcounter0	索引从最大长度到 0
forloop.first	当遍历的元素为第一项时为真
forloop.last	当遍历的元素为最后一项时为真
forloop.parentloop

空的情况
```
{% for athlete in athlete_list %}
    <li>{{ athlete.name }}</li>
{% empty %}
    <li>抱歉，列表为空</li>
{% endfor %}
```

字典遍历
```
{% for key, value in info_dict.items %}
    {{ key }}: {{ value }}
{% endfor %}
```

获取url
```
{% url 'some-url-name' arg arg2 as the_url %}
```
有as部分的话就会被保存到 the_url 这个变量里

{% static "images/xxx.png" %}

## 过滤器 ##
xxx_date | date:"格式"


## csrf ##
{% csrf_token %} 直接放到 form 元素下就行




# 上下文处理器 #

# request #
.GET
.POST
.session['id']
.META['REMOTE_ADDR']

# 模型 #
保存对象的几种方式
1. User.objects.create(...)
2. u = User(...) u.save()
3. User.objects.get_or_create()

获取对象的方式
1. User.objects.all() 支持切片
2. User.objects.get(name='xzc') 获取一个对象
3. User.objects.filter(name__contains='x',age=22).exclude(gender='男')


name__contains
name__icontains
name__regex
name__iregex

检查是否有对象存在
Entry.objects.all().exists()

查询数量
Entry.objects.count()

Author.objects.all().order_by('name')
Author.objects.all().order_by('-name') # 在 column name 前加一个负号，可以实现倒序

Person.objects.all()[:10] 切片操作，前10条
Person.objects.all()[-10:] 会报错！！！
 
1. 使用 reverse() 解决
Person.objects.all().reverse()[:2] # 最后两条
Person.objects.all().reverse()[0] # 最后一条
 
2. 使用 order_by，在栏目名（column name）前加一个负号
Author.objects.order_by('-id')[:20] # id最大的20条

方法
distinct
order_by('-register_at') -表示倒序
all
reverse
filter
exclude


表结构发生改变之后
manage.py makemigrations
manage.py migrate


# 特殊的View #
django.views.generic.base.RedirectView 重订向 默认是301

url(r'^go-to-django/$', RedirectView.as_view(url='http://djangoproject.com'), name='go-to-django'),
url(r'^go-to-ziqiangxuetang/$', RedirectView.as_view(url='http://www.ziqiangxuetang.com',permant=False), name='go-to-zqxt'), 设置成False 则是302

http://www.ziqiangxuetang.com/django/django-generic-views.html

# 响应 #
1. 重订向
from django.http import HttpResponseRedirect



# 上下文渲染器 #
平时要提供上下文是通过render的第三个参数
往 context_processors 里添加你自己的处理器(其实是一个方法, 它接受一个request, 然后返回一个dict, 这个dict会被合并到最终的上下文里)

# 中间件 #
跟过滤器作用类似吧

方式1
一个类 提供两个特殊方法
```
class CommonMiddleware(object):
    def process_request(self, request):
如果返回None 那么继续其他过滤器 如果返回 一个 Response 就以它为结果
        return None
 
    def process_response(self, request, response):
最终的结果
        return response
```

方式2, 在1.10 之后
有办法做到新版本旧版本兼容, http://www.ziqiangxuetang.com/django/django-middleware.html
```
class SimpleMiddleware(object):
    def __init__(self, get_response):
        self.get_response = get_response
        # One-time configuration and initialization.
 
    def __call__(self, request):
        # Code to be executed for each request before
        # the view (and later middleware) are called.
        # 调用 view 之前的代码
 
        response = self.get_response(request)
 
        # Code to be executed for each request/response after
        # the view is called.
        # 调用 view 之后的代码
 
        return response
```

# Model #
default=默认值
max_length=最大长度
null=True/False

DateField()
CharField(max_length=255)
TextField()
ForeignKey(另外的类,on_delete=models.CASCADE)



# 配置数据库 #
在 settings.py 里搜索 database
ENGINE
NAME
USER
PASSWORD
HOST


# urls.py #
url(r'^(?P<question_id>[0-9]+)/$', views.detail, name='detail'),
?P<name> 可以用于设置捕获的名字

```
url(r'^polls/', include('polls.urls')), 所有polls/开头的请求都路由到 polls.urls 脚本里去处理
注意前面的正则表达式没有$ , 使用include的时候, django会将已经匹配的部分删除掉 然后继续匹配
比如访问 polls/a/b/c 那么 会将 a/b/c 到 pools.urls 里继续匹配
```

```
extra_patterns = [
    url(r'^reports/$', credit_views.report),
    url(r'^reports/(?P<id>[0-9]+)/$', credit_views.report),
    url(r'^charge/$', credit_views.charge),
]

urlpatterns = [
    url(r'^$', main_views.homepage),
    url(r'^help/', include('apps.help.urls')),
    url(r'^credit/', include(extra_patterns)), include 一个数组 相当于为这个数组里的匹配添加了前缀
]
url 本身也可以携带额外的参数 但似乎不是很常用!?
```

反向生成url
{% url 'news-year-archive' 2012 %}



# 杂 #
1. 这里介绍了一个打开 django shell 的方法 https://docs.djangoproject.com/en/1.10/intro/tutorial02/



这里使用loader加载了一个template, 然后直接渲染
```
...
from django.template import loader
def index(request):
    latest_question_list = Question.objects.order_by('-pub_date')[:5]
    template = loader.get_template('polls/index.html')
    context = {
        'latest_question_list': latest_question_list,
    }
    return HttpResponse(template.render(context, request))
```

这里返回了一个404页面
```
from django.http import Http404
def detail(request, question_id):
    try:
        question = Question.objects.get(pk=question_id)
    except Question.DoesNotExist:
        raise Http404("Question does not exist")
    return render(request, 'polls/detail.html', {'question': question})
```

触发404
```
question = get_object_or_404(Question, pk=question_id)
```


```
from django.shortcuts import get_object_or_404, render
from django.http import HttpResponseRedirect, HttpResponse
from django.urls import reverse

from .models import Choice, Question
# ...
def vote(request, question_id):
    question = get_object_or_404(Question, pk=question_id)
    try:
        selected_choice = question.choice_set.get(pk=request.POST['choice'])
    except (KeyError, Choice.DoesNotExist):
        # Redisplay the question voting form.
        return render(request, 'polls/detail.html', {
            'question': question,
            'error_message': "You didn't select a choice.",
        })
    else:
        selected_choice.votes += 1
        selected_choice.save()
        # Always return an HttpResponseRedirect after successfully dealing
        # with POST data. This prevents data from being posted twice if a
        # user hits the Back button.
        return HttpResponseRedirect(reverse('polls:results', args=(question.id,)))
```

```
规范一下增删该查的名字吧
增 add
删 delete
改 update 
查 find
```

ListView DetailView
DetailView期望url上捕获一个pk 作为主键
模型将会以 model 的名字进行暴露
默认会使用 <app-name>/<model-name>_detail.html <app-name>/<model-name>_list.html 这些模板
你可以使用 template_name 强制指定
```
class IndexView(generic.ListView):
    template_name = 'polls/index.html'
    context_object_name = 'latest_question_list'

    def get_queryset(self):
        """Return the last five published questions."""
        return Question.objects.order_by('-pub_date')[:5]


class DetailView(generic.DetailView):
    model = Question
    template_name = 'polls/detail.html'


class ResultsView(generic.DetailView):
    model = Question
    template_name = 'polls/results.html'
```
