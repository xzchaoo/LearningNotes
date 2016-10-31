# F表达式 #
用于引用一个字段 可以用于 save update delete filter 时

先去出来 再修改回去
```
reporter = Reporters.objects.get(name='Tintin')
reporter.stories_filed += 1
reporter.save()
```
这样的更新是 set stories_filed = 某个定值

```
reporter = Reporters.objects.get(name='Tintin')
reporter.stories_filed = F('stories_filed') + 1
reporter.save()
```
这样的更新是 set stories_filed = stories_filed + 1

```
company = Company.objects.annotate(
    chairs_needed=F('num_employees') - F('num_chairs'))
```


# Func #
用于调用数据库的一个函数来装饰该字段
```
queryset.annotate(field_lower=Func(F('field'), function='LOWER'))
```

## 已经提供好的一些函数 ##
这些函数都是 Func 的子类
这样你就可以 LOWER('field') 而不是 Func(F('field'), function='LOWER')
LOWER LENGTH Coalesce

# Aggregate #
聚合
COUNT SUM AVG MIN MAX

# RawSQL #
queryset.annotate(val=RawSQL("select col from sometable where othercol = %s", (someparam,)))

# 条件表达式 #
假设有这样的模型
```
class Client(models.Model):
    REGULAR = 'R'
    GOLD = 'G'
    PLATINUM = 'P'
    ACCOUNT_TYPE_CHOICES = (
        (REGULAR, 'Regular'),
        (GOLD, 'Gold'),
        (PLATINUM, 'Platinum'),
    )
    name = models.CharField(max_length=50)
    registered_on = models.DateField()
    account_type = models.CharField(
        max_length=1,
        choices=ACCOUNT_TYPE_CHOICES,
        default=REGULAR,
    )
```

```
>>> from django.db.models import When, F, Q
>>> # String arguments refer to fields; the following two examples are equivalent:
>>> When(account_type=Client.GOLD, then='name')
>>> When(account_type=Client.GOLD, then=F('name'))
>>> # You can use field lookups in the condition
>>> from datetime import date
>>> When(registered_on__gt=date(2014, 1, 1),
...      registered_on__lt=date(2015, 1, 1),
...      then='account_type')
>>> # Complex conditions can be created using Q objects
>>> When(Q(name__startswith="John") | Q(name__startswith="Paul"),
...      then='name')
```

```
>>> Client.objects.annotate(
...     discount=Case(
...         When(account_type=Client.GOLD, then=Value('5%')),
...         When(account_type=Client.PLATINUM, then=Value('10%')),
...         default=Value('0%'),
...         output_field=CharField(),
...     ),
... ).values_list('name', 'discount')
```

根据字段的值 进行不同的更新
```
>>> a_month_ago = date.today() - timedelta(days=30)
>>> a_year_ago = date.today() - timedelta(days=365)
>>> # Update the account_type for each Client from the registration date
>>> Client.objects.update(
...     account_type=Case(
...         When(registered_on__lte=a_year_ago,
...              then=Value(Client.PLATINUM)),
...         When(registered_on__lte=a_month_ago,
...              then=Value(Client.GOLD)),
...         default=Value(Client.REGULAR)
...     ),
... )
>>> Client.objects.values_list('name', 'account_type')
```

聚合的字段也可以使用 case 表达式
```
>> from django.db.models import IntegerField, Sum
>>> Client.objects.aggregate(
...     regular=Sum(
...         Case(When(account_type=Client.REGULAR, then=1),
...              output_field=IntegerField())
...     ),
...     gold=Sum(
...         Case(When(account_type=Client.GOLD, then=1),
...              output_field=IntegerField())
...     ),
...     platinum=Sum(
...         Case(When(account_type=Client.PLATINUM, then=1),
...              output_field=IntegerField())
...     )
... )
```





# 简单 #
Value('asdf') 很少直接用的它 将一个字面值常量封装一下而已


Count
Length

按照字符串的长度来排序
```
Company.objects.order_by(Length('name').asc())
```

# 刷新对象 #
reporter.refresh_from_db() 这样可以重新从数据库获取该对象

# 数据库函数 #
## Cast ##
类型转换
```
value = Value.objects.annotate(as_float=Cast('integer', FloatField())).get()
```
## Coalesce ##
返回第一个非空的值
```
>>> author = Author.objects.annotate(
...    screen_name=Coalesce('alias', 'goes_by', 'name')).get()
>>> print(author.screen_name)
```
这里要注意 Coalesce 接受的字符串全都会被认为是字段名 而不是字面值常量
通过Value 可以做到字面值常量:
```
from django.db.models import Sum, Value as V

>>> aggregated = Author.objects.aggregate(
...    combined_age=Coalesce(Sum('age'), V(0)),
...    combined_age_default=Sum('age'))
>>> print(aggregated['combined_age'])
```

## Concat ##
```

...    screen_name=Concat('name', V(' ('), 'goes_by', V(')'),
...    output_field=CharField())).get()

```

## Greatest/Least ##
数据库里的取最大值函数 和 聚合函数max不同哦

## Extract ##
用于提取日期的字段

```
>>> experiment = Experiment.objects.annotate(
...    start_year=Extract('start_datetime', 'year')).get()
```

它有很多简单的子类 ExtractYear ExtractMonth ExtractDay ExtractWeekDay
ExtractHour ExtractMinute ExtractSecond

## Trunc ##
将日期截到一定精度, 同样的它有很多子类

```
>>> experiments = Experiment.objects.annotate(
...    start_day=Trunc('start_datetime', 'day', output_field=DateTimeField())
... ).filter(start_day=datetime(2015, 6, 15))
>>> for exp in experiments:
...     print(exp.start_datetime)
...
2015-06-15 14:30:50.000321
2015-06-15 14:40:02.000123
```


## 简单 ##
Length() 取得长度, 这里指的应该是字符长度
Lower()/Upper() 大小写
Now() 取当前时间
Substr 子字符串


