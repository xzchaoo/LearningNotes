# bound or unbound #
如果是有初始化数据的就是bound 没有就是unbound

is_bound

# validate #
is_valid()
重写clean方法, 定制自己的逻辑


add_error(field,error) field=None 则是 non_field_errors
has_error()
non_field_errors()


initial 初始值 是一个字典 定义了字段的初始值



在clean()方法里可以添加自己的逻辑


当你调用is_valid() 的时候会触发一些列的检查
1. 检查每个字段
2. 检查表单


changed_data/has_changed 判断表单是否改变过

cleaned_data 里存放的是净化后的数据

prefix 用于定制form的字段的name的前缀

# 指定表单的初始值 #
```
1. 在定义表单的时候 name = forms.CharField(initial='123', max_length=12), 缺点是完全静态
2. 在创建表单的时候 f1 = a1.forms.F1(initial={'name': '123'}) 优先级比上面更高

```


# 访问所有字段 #
form.fields 是一个 OrderedDict
f.fields['name'] CharField

# 定制ModelChoiceField #
account = forms.ModelChoiceField(Account.objects.all()) #这里是第一个可以定制qs的地方
接着在 form 的构造函数里可以通过
self.fields['account'].queryset ... 定制qs

如果是在相关的View里, 那么可以等form创建完之后再用上面的方法进行修改

to_field_name 可以定制 哪个属性用来做optoin的value
