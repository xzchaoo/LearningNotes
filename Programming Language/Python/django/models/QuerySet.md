# QuerySet #
1. lazy, 一旦你触发某些行为就会真正到数据库里去找数据, 注意当你调用len(qs)的时候会将该qs对应的数据都取出来 这个跟 调用 qs.count() 效果是不一样的
2. 可迭代 支持切片 其实也就是 limit 语句
3. 一定缓存机制, 避免一直访问数据库, 可以被 pickle
4. 通过 list(qs) 可以转成一个list (此时qs数据也被取出来了)

# 属性 #
ordered 是否有序


db 关联的db

## 链式调用 ##
filter()/exclude() 涉及到 Q 和 F 表达式

### order_by ###
控制排序
order_by('-pub_date', 'headline')
order_by('?') 去掉order语句, 性能可能损失或提升
还可以按照关联对象的属性来排序
```
Entry.objects.order_by('blog__name')
```

注意 blog_id 和 blog__id 的值是一样的 但在行为上是不一样的

```
Entry.objects.order_by(Coalesce('summary', 'headline').desc())

Entry.objects.order_by(Lower('headline').desc())
```

order_by不会累加, 下面的例子只有pub_date会生效
```
Entry.objects.order_by('headline').order_by('pub_date')
```

### reverse ###
要和 order_by一起使用 获得反向结果, 当然你也可以直接在 order_by 的时候就自己设置好顺序这样就不用再反向了

### values ###
使得返回的是字典而不是模型
如果指定了参数, 那么只返回这些字段

如果有外键关联user, 那么得到的是 user_id

支持引用关联对象的属性
```
Blog.objects.values('name', 'entry__headline')
```

## values_list ##
和values类似, 返回的是元组而不是字典
flat=True 可以在元组**只有一个元素**的时候 将它摊开成一个元素
```
>>> Entry.objects.values_list('id', flat=True).order_by('id')
[1, 2, 3, ...]
```

获取主键为1的标题
```
Entry.objects.values_list('headline', flat=True).get(pk=1)
'First entry'
```

### dates ####
如果你的模型里有 DateField 字段
dates('pub_date','year',order='ASC') 会返回你的数据库里有几种不同的pub_date取值 精确到year
如果你的数据库里只有2015~2016的数据, 那么返回值是
[datetime.date(2015,1,1),datetime.date(2016,1,1)]

支持 year month day

### datetimes ###
和上面类似
支持 year month day hour minute second
同时可以考虑一下时区问题

### select_related ###
基于 join 语句, 只支持 外键 或 一对一的情况

将关联的对象取出来, 只支持1的情况哦
如果不给参数 那么就是全部取出来
select_related(None) 的话就是清除之前的设置
select_related('foo', 'bar') 和 select_related('foo').select_related('bar') 是一样的

还可以这样用: Book 多对一 Author 多对一 hometown
```
Book.objects.select_related('author__hometown').get(id=4)
```

### prefetch_related ###
和 select_related 想要做得事情是一样的 但是策略不一样
先把id取回来, 然后再发请求过去查询(通常是select ... where id in (...)), 然后再在 python 中做join操作
支持多对多的情况

```
class Restaurant(models.Model):
    pizzas = models.ManyToManyField(Pizza, related_name='restaurants')
    best_pizza = models.ForeignKey(Pizza, related_name='championed_by')

Restaurant.objects.prefetch_related('pizzas__toppings') 导致3个查询
Restaurant.objects.prefetch_related('best_pizza__toppings') 导致3个查询
Restaurant.objects.select_related('best_pizza').prefetch_related('best_pizza__toppings') 导致2个查询, 因为 抓取 best_pizza__toppings 的时候会发现 best_pizza 已经抓取回来了
```

```
Restaurant.objects.prefetch_related('pizzas__toppings')
等价于
Restaurant.objects.prefetch_related(Prefetch('pizzas__toppings'))

可以进一步定制
Restaurant.objects.prefetch_related(
...     Prefetch('pizzas__toppings', queryset=Toppings.objects.order_by('name')))

>>> Pizza.objects.prefetch_related(
...     Prefetch('restaurants', queryset=Restaurant.objects.select_related('best_pizza')))

>>> Restaurant.objects.prefetch_related(
...     Prefetch('pizzas', to_attr='menu'),
...     Prefetch('pizzas', queryset=vegetarian_pizzas, to_attr='vegetarian_menu'))
```

to_attr 因为有这个to_attr 所以这些取回的结果会作为一个list放到to_attr指定的属性里
```
>>> # Recommended:
>>> restaurants = Restaurant.objects.prefetch_related(
...     Prefetch('pizzas', queryset=queryset, to_attr='vegetarian_pizzas'))
>>> vegetarian_pizzas = restaurants[0].vegetarian_pizzas 这样的话就是直接拿到对应的list
>>>
>>> # Not recommended:
>>> restaurants = Restaurant.objects.prefetch_related(
...     Prefetch('pizzas', queryset=queryset))
>>> vegetarian_pizzas = restaurants[0].pizzas.all() 这样做的话相当于还需要一个查询 因为你已经是创建了不同的queryset了 (还记得all的作用么?)
>
```

### extra ###
最后的手段, 有需要再去看文档吧
```
Entry.objects.extra(select={'is_recent': "pub_date > '2006-01-01'"})
SELECT blog_entry.*, (pub_date > '2006-01-01') AS is_recent FROM blog_entry;
```

```
select={'entry_count': 'SELECT COUNT(*) FROM blog_entry WHERE blog_entry.blog_id = blog_blog.id'},
SELECT blog_blog.*, (SELECT COUNT(*) FROM blog_entry WHERE blog_entry.blog_id = blog_blog.id) AS entry_count FROM blog_blog;
```

### defer ###
推迟一些字段的加载 比如大字单
所以我就想知道了 那些二进制字段默认是会???

在 https://docs.djangoproject.com/en/1.10/ref/models/querysets/ 搜索关键字 CommonlyUsedModel 介绍了一个很有趣的特性

## only ##
跟 defer 相反 它只会加载特定的字段
与 values 不同的是only依旧返回模型 而values返回字典

## select_for_update(nowait=False) ##
产生 select ... for update 语句
```
entries = Entry.objects.select_for_update().filter(author=request.user)
```
> All matched entries will be locked until the end of the transaction block, meaning that other transactions will be prevented from changing or acquiring locks on them.

有些数据库不支持 nowait=True

因为sqlite不支持 select_for_update 所以即使你用了也不会有任何效果或抛异常

### raw ###
原生sql 有需要再去看吧

### 简单 ###
all() 返回一个当前qs的副本, 因为 qs 是不可变的, 但是qs会缓存查询后的数据, 如果你不新建一个qs, 那么你会一直用到旧数据
distinct()
none() 这会使得该qs没有数据
using() 使用哪个数据库 当有多数据库的情况


### annotate ###
对每一个元素的关联元素做聚合
比如一个Blog对应了多个Entry
现在要获Blog名字和Entry数

```
>>> q = Blog.objects.annotate(number_of_entries=Count('entry'))
# The number of entries on the first blog, using the name provided
>>> q[0].number_of_entries
42
# The name of the first blog
>>> q[0].name
'Blogasaurus'
```

## 非链式调用 ##
### get ###
get(这里填写过滤条件 和 filter 的类似) 期待获得一个结果 如果是0个或多余1个都会抛异常
DoesNotExist 继承了 django.core.exceptions.ObjectDoesNotExist

### create ###
创建对象

### get_or_create ###
get_or_create(defaults=None, **kwargs)
defaults应该是一个字典
和get一样 如果不存在就创建, 如果太多就抛异常

```
obj, created = Person.objects.get_or_create(
    first_name='John',
    last_name='Lennon',
    defaults={'birthday': date(1940, 10, 9)},
)
```
相当于
```
try:
    obj = Person.objects.get(first_name='John', last_name='Lennon')
except Person.DoesNotExist:
    obj = Person(first_name='John', last_name='Lennon', birthday=date(1940, 10, 9))
    obj.save()
```
注意到kwargs也被加入到最终的创建语句了
kwargs 中 所有key不包含 __ 的都被加入最终的创建语句了


看看下面想要表达得意思
```
Foo.objects.get_or_create(defaults__exact='bar', defaults={'defaults': 'baz'})
```

当get_or_create和多对多一起使用的时候:

```
>>> book = Book.objects.create(title="Ulysses")
>>> book.chapters.get_or_create(title="Telemachus")
(<Chapter: Telemachus>, True)
>>> book.chapters.get_or_create(title="Telemachus")
(<Chapter: Telemachus>, False)
>>> Chapter.objects.create(title="Chapter 1")
<Chapter: Chapter 1>
>>> book.chapters.get_or_create(title="Chapter 1")
# Raises IntegrityError
```

因为多对多的情况默认对一些数据进行缓存了, 而数据库的变动它是不知道的 所以导致最后一条语句出错

### update_or_create ###
update_or_create(defaults=None, **kwargs)
defaults应该是一个字典
和上面的 get_or_create 类似

### bulk_create ###
通过 ```insert ... values(...),(...)``` 这样的方式插入
可以通过 batch_size 控制多少个元素一批

有几个但需要注意:
1. 对象的 save 方法不会被调用, pre_save post_save也不会受到信号
2. 不支持多表继承相关的模型
3. 无法取回主键, 所以你的模型的pk还是为None, 但 PostgreSQL 支持这一特性
4. 不支持多对多的关系


### 简单 ###
count() 注意他和 len(qs) 的区别 len(qs) 会导致 qs 被取出数据 而 count() 是执行 select count(...) 不一样
in_bulk(id_list) 在原有qs的基础上按照id进行过滤 结果是一个字典, key是id
iterator() 解析qs, 并且返回一个迭代器
latest earliest
first() last()
update()
delete()


# 一对一 #
```
from django.core.exceptions import ObjectDoesNotExist
try:
	p2.restaurant 因为外键是放在 restaurant 那, 所以这个表达式有可能会触发异常(当关联对象不存在的时候)
except ObjectDoesNotExist:
	print("There is no restaurant here.")

```
也可以使用 hasattr 来判断
hasattr(p2, 'restaurant')

