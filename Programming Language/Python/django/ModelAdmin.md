# ModelAdmin #
这个一个非常重量级的对象, 有非常多的配置, 不过我们只关注最常用的
它用于定制管理员界面如何显示该Model

## fields/exclude ##
包含和排除哪些字段

通过下面的方式可以让多个字段显示在同一行
```
fieldsets = (
    (None, {
        'fields': ('account', 'test_task', 'threads', 'status')
    }), ('时间控制', {
        'fields': (('start_at', 'end_at'),)
    }), ('验证码', {
        'fields': ('vcode', 'vcode_data',)
    }),
)
None的话就不会显示这个节的名字, 通常是一些最基本的配置
```
每个dict除了fields字段外 还可以接受:
classes=元组 用来控制fieldset的class
description=描述 会显示的一些文本来描述这个fieldset


## fieldsets ##
用于控制字段的布局
比如你可以将一些字段归类到 "高级选项" 里去

## inlines ##

## date_hierarchy ##
填写你的一个时间字段 然后列表页面就会有一个关于时间的导航
比如显示2016年的数据 2016年2月的数据 这样...

## empty_value_display ##
控制空值将会显示成什么样
empty_value_display = '-empty-'
还可以用这样的方式控制某个字段的空值显示:
```
class AuthorAdmin(admin.ModelAdmin):
    fields = ('name', 'title', 'view_birth_date')

    def view_birth_date(self, obj):
        return obj.birth_date

    view_birth_date.empty_value_display = '???'
```

还可以在不同范围指定
```
admin.site.empty_value_display = '(None)'

class PersonAdmin(admin.ModelAdmin):
    empty_value_display = 'unknown'

```


## list_display ##
控制在列表界面里会显示的字段
```
list_display = ('group', 'account', 'test_task', 'start_at', 'status')
```

如果填写的是一个外键, 那么默认是调用关联对象的str方法

支持虚拟属性, 比如上面的group不是Model的一个字段, 而是当前ModelAdmin的一个方法
```
    def group(self, task):
        return task.account.group
	group.short_description = '组'
```

并且使用group.short_description来修改默认展示的名字(group)

也可以利用Model的虚拟属性
```
class Person(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)
    color_code = models.CharField(max_length=6)

    def colored_name(self):
        return format_html(
            '<span style="color: #{};">{} {}</span>',
            self.color_code,
            self.first_name,
            self.last_name,
        )

class PersonAdmin(admin.ModelAdmin):
    list_display = ('first_name', 'last_name', 'colored_name')
```

虚拟属性的排序, 因为使用的是数据库级别的排序, 所以虚拟属性是无法参与排序的
但你可以将虚拟属性的位置当作某个值来排序
```
    def colored_first_name(self):
        return format_html(
            '<span style="color: #{};">{}</span>',
            self.color_code,
            self.first_name,
        )

    colored_first_name.admin_order_field = 'first_name'
```
这样当要以 colored_first_name 排序的时候 实际上就会以 first_name 来排序
还可以这样
```
	colored_first_name.admin_order_field = '-first_name'
```

按照某个关联对象的属性排序
```
class BlogAdmin(admin.ModelAdmin):
    list_display = ('title', 'author', 'author_first_name')

    def author_first_name(self, obj):
        return obj.author.first_name

    author_first_name.admin_order_field = 'author__first_name'
```

如果一个虚拟属性是boolean
```
    def born_in_fifties(self):
        return self.birthday.strftime('%Y')[:3] == '195'
    born_in_fifties.boolean = True
```

list_display的解析顺序
```
Django will try to interpret every element of list_display in this order:

A field of the model.
A callable.
A string representing a ModelAdmin attribute.
A string representing a model attribute.
For example if you have first_name as a model field and as a ModelAdmin attribute, the model field will be used.
```

## list_display_links ##
在列表界面里会显示的字段, 默认情况下第一个字段是一个超链接
通过设置 list_display_links=('account',) 就可以强制让account字段变成超链接
如果设置成None则都没有超链接

## list_editable ##
list_editable=('status',) 则status字段在 列表页就可以修改

## list_filter ##
list_filter=('status',) 可以按照status的取值进行筛选, 通常 choice , choice, boolean 会有比较好的效果

### 自定义list_filter ###
1. 继承 admin.SimpleListFilter, 实现以下2个方法, 两个方法里就可以访问到request
2. 实现 lookup 方法, 返回一个元组, 元组的元素是2元组, 第一个元素是value, 第二个元素是text, 两个元素用于形成一个html的option元素, 如果返回None, 则表示它不筛选, 这里返回的值可以是动态的, 比如性别有3个选项 男,女,保密 但数据库里目前还没有女性用户, 所以就只返回 男 和 保密
3. 实现 queryset 方法, 会传给你一个queryset, 你需要在它基础上根据 self.value() 的值进行过滤, 如果返回None则表示空结果?

另外 当一个filter只有一个选项的时候 那么它就会被隐藏


然后这样用
```
class PersonAdmin(admin.ModelAdmin):
    list_filter = (DecadeBornListFilter,) 这里就不再是写字符串了
```


## list_per_page/ ##
list_per_page 每页大小
list_max_show_all 页最大大小, 有一个 "show all" 按钮, 点击了之后最多显示200条

## list_select_related ##
默认是False
可以是True或False或一个字符串元组
用于数据库查询时的 select_related 参数
用得好的话可以带来性能提升


## ordering/get_ordring ##
ordering=('-aid') 排序
get_ordring() 动态排序规则


## radio_fields ##
默认情况下, 当需要选择外键的时候, 是显示一个select元素

```
class PersonAdmin(admin.ModelAdmin):
    radio_fields = {"group": admin.VERTICAL}
```
这样当要选择group的时候就会呈现一个 垂直的 radio 组
当然了最好保证元素比较好 不然显示效果不好

## raw_id_fields ##
默认情况下, 当需要选择外键的时候, 是显示一个select元素
使用这个选项的话就直接让用户对这一个input元素输入id列表了
比如 input user_id 然后内容是 1

## readonly_fields ##
标记一些字段为只读的
默认情况下 editable=False 的元素是不会被显示的
如果你是用了 fields 或 fieldsets 那么其他的无关的元素也不会被显示
使用这个的话就可以显示其他元素为只读元素了

## search_fields ##
要搜索的字段 支持外键 比如 foreign_key__related_fieldname
如果用户输入的是john lennon", 那么会产生这样的where语句
```
WHERE (first_name ILIKE '%john%' OR last_name ILIKE '%john%')
AND (first_name ILIKE '%lennon%' OR last_name ILIKE '%lennon%')
```
注意那个and, 这要就每个单词都至少出现一次
你可以指定 '^last_name' 那么是表示 以这个单词开头的
```
WHERE (first_name ILIKE 'john%' OR last_name ILIKE 'john%')
AND (first_name ILIKE 'lennon%' OR last_name ILIKE 'lennon%')
```
还可以用 '=first_name' 表示严格相等
还可以用 '@first_name' 表示使用数据库的全文搜索引擎

## show_full_result_count ##
默认是True
和 list filter 配合使用的时候 是否显示总的记录数(指的是如果不使用filter, 那么总条数是多少)
当然了这会产生一个额外的请求到数据库
基本上这个请求应该是没有任何where语句的
```
select count(*) from ...
```

## view_on_site  ##
默认是True, 这个属性也可以是一个函数
如果你自己的站点对该模型也有一个detail界面, 通过 get_absolute_url() 指定, 那么会显示一个 "view on lite" 的链接

## save_as ##
默认是False
save_as按钮是否可用, 会以当前的对象为基础创建一个新的对象

## save_as_continue ##
默认是True 即save完之后还是回到该页面 而不是list页面


## actions ##
在你的ModelAdmin里
```
    actions = ['make_canceled']
    def make_canceled(self, request, queryset):
        count = queryset.update(status=tasks.models.TASK_STATUS_CANCELED)
		self.message_user(request, "你修改了%d个对象" % count)

    make_canceled.short_description = '取消执行'
```
这样列表界面上就会出现一个操作是 "取消执行" 你批量选中一些对象 然后执行该动作

## 杂 ##
actions_on_top/actions_on_bottom 控制action选项所在的位置
filter_horizontal/filter_vertical 控制 多对多 的元素的渲染
form 关联的Form类 感觉会很少去定制

## 定制模板 ##
可以定制 添加 修改 列表 删除确认 等的模板

# ModelAdmin的方法 #
上面介绍的大部分是属性, 这里来介绍一些方法

## save_model ##
当用户点击了save的时候讲会触发这个函数
save_model(self, request, obj, form, change)
change 表明是修改还是创建

## delete_model ##
delete_model(self, request, obj)

## get_ordering ##
动态排序规则

## save_formset ##
TODO 我现在还不太懂formset是啥

## get_search_results ##
当允许搜索的时候 会调用该方法获取 queryset
下面的例子定制了一个方法, 使得会对age字段进行搜索(如果传入的值可以被解释为一个int)
```
class PersonAdmin(admin.ModelAdmin):
    list_display = ('name', 'age')
    search_fields = ('name',)

    def get_search_results(self, request, queryset, search_term):
        queryset, use_distinct = super(PersonAdmin, self).get_search_results(request, queryset, search_term)
        try:
            search_term_as_int = int(search_term)
        except ValueError:
            pass
        else:
            queryset |= self.model.objects.filter(age=search_term_as_int)
        return queryset, use_distinct
```

## save_related ##
TODO

## get_queryset ##
重写该方法以提供自己的过滤逻辑

## message_user ##
给用户通知

## 很多的属性都有对应的函数版本 ##
这里就不给出他们了 因为比较多
一般情况下你认为它有对应的函数版本 它就真的有对应的函数版本

formfield_for_foreignkey
formfield_for_manytomany
formfield_for_choice_field
has_add_permission
has_change_permission
has_delete_permission
has_module_permission


几个view的处理函数:
add_view
change_view
changelist_view
delete_view
histroy_view

可以去看看文档


# inline #
InlineModelAdmin 和普通的 ModelAdmin 很像, 有共同的父类 BaseModelAdmin
自己提供了一些额外的属性:
model = 要内联哪个模型
fk_name 一般会自动推断, 但如果你和model对应的模型不止有一个关系的时候就需要显式指定了, 这里有一个例子 https://docs.djangoproject.com/en/1.10/ref/contrib/admin/
formset form classes extra

max_num
min_num
raw_id_fields
verbose_name
can_delete
show_change_link=False 是否显示内联对象的 change link (点了之后就跳转到该内联对象的修改界面了)


TabularInline
StackedInline

假设一个作者对应多个书本
```
from django.contrib import admin

class BookInline(admin.TabularInline):
    model = Book

class AuthorAdmin(admin.ModelAdmin):
    inlines = [
        BookInline,
    ]
```
那么你就可以在作者的页面, 编辑他对应的书本, "把书本内联到作者页面"


对于 多对多 的情况, 这里也有例子 https://docs.djangoproject.com/en/1.10/ref/contrib/admin/

