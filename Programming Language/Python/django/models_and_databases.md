# 定义模型 #
1. 继承 models.Model
2. 默认id字段会字段添加, 自增 integer

## 常见字段 ##
1. CharField
2. IntegerField
3. DateField DateTimeField
4. URLField
5. EmailField
6. TextField

每个字段可以指定一个名字(第一个参数) 用于展示用


## 常见属性 ##
1. max_length 最打长度
2. null=True 是否允许为空
3. blank 是否允许为空字符串
4. choices=((0,'保密'),(1,'男'),(2,'女')) 数据库里保存的是0 1 2 但展示的是后面的字符串
5. default 默认只
6. help_text 帮助文档
7. primary_key 标记该字段为主键
8. unique
9. label 表单呈现时用

## Meta ##
使用内部类Meta
```
class Meta:
	abstract = False
	ordering = ['-aid'] 默认按照aid排列 
```

当调用order_by()却不提供参数的时候, 就是不排序

# 关联关系 #
假设多个学生对一个老师

一对多或多对多的关联对象的Manager有特殊方法:
```
add(obj1, obj2, ...)
create(**kwargs)
remove(obj1, obj2, ...)
clear()
set(objs)
````
## 一对多 ##
在多的一方使用
teacher = models.ForeignKey('a3.Teacher', null=True)

这样学生就会有 teacher 属性关联到老师
每个老师也会有一个 student_set (这个是自动生成的) 属性关联到其对应的学生的QuerySet

双方都可以控制关联关系 不过一般建议还是通过一的一方来控制

### 多的一方 ###
a = A.objects.get(pk=1)
a.b 获得a关联的b

print(a.b) 发一个数据库请求
print(a.b) 使用缓存的版本

a.b = b2 更新关联关系 或 a.b=None 删除关联关系

a.save()


### 一的一方 ###
b = B.objects.get(pk=1)
b.entry_set.all()
b.entry_set.filter(...).count()

add(obj1,obj2,...)
create(**kw)
remove(obj1,obj2,...)
clear()
set(objs_list)

## 多对多 ##
ManyToManyField

## 一对一 ##
和一对多类似
其中一方使用 teacher = models.OneToOneField('a3.Teacher', null=True)
然后对方(Teacher)就会有Student的引用, 即 student 属性
可以用 related_name 控制 该属性叫什么名字, 即我在对方那里叫的名字
如果 related_name='+' 那么关系就是单向的 对方那里不会有我的引用

related_query_name 是用于反向filter时叫的名字, 默认他和 related_name是一样的 
主要是要处理一个问题就是复数问题
比如 tags 那么再 filter 的时候是 tags__name__contains=... 比较好看还是 tag__name__contains=... 呢
就是用于处理这个问题了


多个学生对应一个班级
```
class Student(models.Model):
	clazz = models.ForeignKey(Clazz)
``` 

与Poll多对一
```
poll = models.ForeignKey(
    Poll,
    on_delete=models.CASCADE,
    verbose_name="the related poll",
)
```

与Place一对一
```
place = models.OneToOneField(
    Place,
    on_delete=models.CASCADE,
    verbose_name="related place",
)
```

与Site多对多
```
sites = models.ManyToManyField(Site, verbose_name="list of sites")
```



## 外键 ##
```
class User(models.Model):
	card = ForeignKey(Card,on_delete=models.CASCADE)
```

# 查询 #
QuerySet 是 lazy 的

id和pk是一样的, 可以混用, 当然一般我们不会有一个属性叫做pk

Entry.objects.all() 返回的是一个迭代
filter(过滤条件)
exclude(排出条件)
order_by(排序)

过滤条件虽然是key/value形式, 但是可以有复杂的形式 主要是利用特殊格式的key

QuerySet 支持切片
[:5] 相当于 limit 5
[5:10] 相当于 limit 5, 5
不支持负向索引 需要自己先sort再切片

## get ##
get(pk=1) 期待返回一个对象 如果数量是0或大于1都不行
DoesNotExist MultipleObjectsReturned

## filter ##
xxx_lte='2006-01-01'
__in
__icontains
__exact
__iexact

```
SELECT * FROM blog_entry WHERE pub_date <= '2006-01-01';
```

## Q表达式 ##
用于构建复杂的查询条件, 单纯使用 filter 或 exclude 的话很难表达 not and or 混用的情况, 用Q表达式就可以
```
from django.db.models import Q
Q(question__startswith='What')

Poll.objects.get(
    Q(question__startswith='Who'),
    Q(pub_date=date(2005, 5, 2)) | Q(pub_date=date(2005, 5, 6))
)
SELECT * from polls WHERE question LIKE 'Who%'
    AND (pub_date = '2005-05-02' OR pub_date = '2005-05-06')

```

```
Article.objects.filter(Q(headline__startswith='Hello') | Q(headline__startswith='Goodbye'))
Article.objects.filter(headline__startswith='Hello') | Article.objects.filter(headline__startswith='Goodbye') 原来的 QuerySet 似乎也支持表达式重载 来构建or查询
```

## values() ##
会使得QuerySet返回字典而不是model实例
*args是一个字符串数组 指定要返回哪些字段

对于外键, 直接使用 blog 或 blog_id 也行


# CRUD #
## 增加 ##
1. XXX.objects.create()
2. 创建对象然后 save 方法

## 删除 ##
1. 查询到之后调用delete方法 Blog.objects.get(pk=1).delete()
2. Entry.objects.filter(pub_date__year=2005).delete()


## 修改 ##
1. 修改完之后调用 save 方法
2. 批量更新
	1. ```Entry.objects.filter(pub_date__year=2007).update(headline='Everything is the same')```
	2. ```Entry.objects.all().update(blog=b)``` 将所有对象的blog更新为b

可能会用到F()
比如当 update xxx set money = money + 1 ...
的时候就需要 money = F('money')+1 

## 查询 ##
get filter exclude all order_by [切片]

## 关联的对象 ##
同是取出与该对象关联的对象, 有时有助于性能提升
select_related


# 复制对象 #
e = XXX.objects.get(pk=1)
e.pk=None
e.save() 这样就创建了一个新对象

# 杂 #
1. 模型所在的模块要包含在 INSTALLSED_APPS
2. select_related(fields) 用于指定哪些关联也一起取出来, 默认是lazy的, 关联只有用到才回去出来
3. 对象用于 one-to-one 之类的关系的时候 需要先保存再用

# F Q #

# 聚合 #
```
from django.db.models import Avg, Max, Min, Sum, Count

r = Student.objects.all().aggregate(Avg('age'))

r['age__avg'] 就是平均值


可以这样
r = Student.objects.all().aggregate(a1=Avg('age'),b2=Sum('age'))
r['a1'] r['b2']


```

与聚合类似的还有 annotate
它用于聚合一对多和多对多的情况
```
>>> q = Book.objects.annotate(Count('authors', distinct=True), Count('store', distinct=True))
>>> q[0].authors__count
2
>>> q[0].store__count
3
```

## 连接 ##
可以使用 __ 进行导航





# 杂 #
1. 为你的Model对象实现 get_absolute_url 方法, 这样你的Model对象就可以用于redirect
```
def get_absolute_url(self):
    from django.urls import reverse
    return reverse('people.views.details', args=[str(self.id)])

<a href="{{ object.get_absolute_url }}">{{ object.name }}</a>

return redirect(a2u)
```
2. Model.get_FOO_display()

# 常见异常 #
1. Model.DoesNotExist get的时候 如果对象不存在



# 事务 #
默认行为是每个请求都会立即提交到数据库, 除非一个事务是激活的

一种常见的策略是每个请求一个事务, 设置 ATOMIC_REQUESTS=True 来激活这个行为
在调用view函数之前, 就会启动一个事务, 如果view函数返回一个response, 那么久提交事务, 否则回滚事务
支持子事务
但为每个view函数打开事务还是很浪费的 对性能有影响

> 当view函数返回StreamingHttpResponse的时候, 会不断产生输出, 但此时已经不是原来的线程了, 脱离了原来的事务, 通常返回这种流式响应的时候不推荐使用事务

注意中间件和渲染view的时候并不在事务的范围里!

```
from django.db import transaction

#显式标记一个view函数为不用事务
@transaction.non_atomic_requests
def my_view(request):
    do_stuff()

@transaction.non_atomic_requests(using='other')
def my_other_view(request):
    do_stuff_on_the_other_database()




from django.db import transaction

标明这个view是原子的 即 有事务支持
@transaction.atomic
def viewfunc(request):
    # This code executes inside a transaction.
    do_stuff()


还可以这样用
from django.db import transaction

def viewfunc(request):
    # This code executes in autocommit mode (Django's default).
    do_stuff()

    with transaction.atomic():
        # This code executes inside a transaction.
        do_more_stuff()
```

一旦使用 atomic, 就不能在其块里使用一些操作事务的API, 比如提交回滚, 因为这些行为是自动控制的
当然了启动一个子事务是允许的

using参数标明要使用哪个数据库

在事务提交之后做点什么
```
from django.db import transaction

def do_something():
    pass  # send a mail, invalidate a cache, fire off a Celery task, etc.

transaction.on_commit(do_something)


transaction.on_commit(lambda: some_celery_task.delay('arg1'))

```

**注意** 调用 on_commit 的时候 如果你处在一个事务里, 那么当这个事务提交之后你注册的函数就会被调用, 如果回滚, 那么你的函数就会被丢弃, 不会调用; 如果你没有处在一个事务里, 那么会马上调用.


内嵌事务时候的 on_commit
bar会在最外层的事务提交之后才执行
```
with transaction.atomic():  # Outer atomic, start a new transaction
    transaction.on_commit(foo)

    with transaction.atomic():  # Inner atomic block, create a savepoint
        transaction.on_commit(bar)

```

如果你注册了多个函数,那么它们是按照注册的顺序执行的, 如果某个函数执行的时候发生了错误, 那么后面的函数都不会执行了

## 低级api ##
commit()
rollback()
get_autocommit()
set_autocommit()
savepoint()
savepoint_commit()
savepoint_rollback()
clean_savepoints()

```
from django.db import transaction

# open a transaction
@transaction.atomic
def viewfunc(request):

    a.save()
    # transaction now contains a.save()

    sid = transaction.savepoint()

    b.save()
    # transaction now contains a.save() and b.save()

    if want_to_keep_b:
        transaction.savepoint_commit(sid)
        # open transaction still contains a.save() and b.save()
    else:
        transaction.savepoint_rollback(sid)
        # open transaction now contains only a.save()
```


# 杂 #
有一个情况
User和Post是一对多

当引用的属性是"多"的时候, 
 这个条件是 and!

User.objects.filter(post__name__contains='1', post__pub_at=2008)
select * from user as u left join post as p on p.user_id = u.id where p.name like '%1%' and p.pub_at = 2008 


因为post属于多的一方才会处理成or, 否则如果是普通的属性的话 还是按照and来处理的
User.objects.filter(post__name__contains='1').filter(post__pub_at=2008)
select * from user as u left join post as p on p.user_id = u.id where p.name like '%1%' or p.pub_at = 2008

filter链式调用是在上一个的基础上进行过滤的, 对于 一对多或多对多的关联关系 进行了特殊处理


# F表达式 #
https://docs.djangoproject.com/en/1.10/ref/models/expressions/#django.db.models.F
可以让你引用一个字段, 而不是你提供确切的值
```
Entry.objects.filter(n_comments__gt=F('n_pingbacks'))
Entry.objects.filter(rating__lt=F('n_comments') + F('n_pingbacks'))
Entry.objects.filter(mod_date__gt=F('pub_date') + timedelta(days=3))
```

