https://docs.djangoproject.com/en/1.10/topics/forms/
https://docs.djangoproject.com/en/1.10/ref/forms/fields

{{form}} 渲染form

{{form.as_xxx}} 渲染form为xxx形式 xxx可以是table之类

{{form.xxx_field}} 这里是字段的名字 会帮你渲染响应的input元素

{{form.xxx_field.id_for_label}} 这里可以拿到元素的id


```
class AddForm(forms.Form):
    username = forms.CharField(label='用户名', max_length=32, initial='我是初始值', error_messages={'required': 'Please enter your name'})
    password = forms.CharField(label='密码', max_length=32)
    desc = forms.CharField(widget=forms.Textarea, required=False, max_length=4096)


def add(request):
    if request.method == 'POST':
        form = AddForm(request.POST)
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            desc = form.cleaned_data['desc']
            if A2User.objects.filter(username=username).count():
                form.add_error('username', '用户名重复')
            else:
                a2u = A2User.objects.create(username=username, password=password, desc=desc)
                return redirect('/a2/detail/' + str(a2u.pk))
    else:
        form = AddForm()
    return render(request, 'a2/add.html', {'form': form})

```

字段一些方法
has_changed() 与初始值相比是否改变过


CharField TextInput [min_length, max_length, strip]
BooleanField CheckboxInput False
IntegerField FloatField
FileField/ImageField [max_length]
GenericIPAddressField
RegexField 给定的字段是否满足正则表达式
URLField


# bound 和 unbound #
如果是从一个 dict 里构造出的Form 就是 bound 否则是 unbound
通过 is_bound 属性可以查看

# 验证数据 #
调用 is_valid 方法

## 查看错误 ##
errors Form.errors.as_data() 还有 as_json(escape_html=True)
f.errors.as_json()
{"sender": [{"message": "Enter a valid email address.", "code": "invalid"}],
"subject": [{"message": "This field is required.", "code": "required"}]}
Form.has_error(字段名, code=None) 查看是否有该错误

## 非字段错误 ##
Form.non_field_errors()
Form.add_error(None, "...") 添加非字段错误

## 字段错误 ##
form.字段名.errors
Form.add_error(字段名, 错误字符串 或 ValidationError实例)

## Form的属性 ##
has_changed() 与初始值相比是否改变过
changed_data 改变过的属性的名字
fields 所有的字段
auto_id 控制是否要自动为元素产生id 默认是true ContactForm(auto_id='id_for_%s')



# ModelForm #
https://docs.djangoproject.com/en/1.10/topics/forms/modelforms/

```
class Meta:
	model = XXXModel

	#fields= ['username','password'] 当然默认也不会包含 editable=False 的字段  
	#或exclude = ['password']
	fields = '__all__'
```

# save #
form.save() 默认是 form.save(commit=True) 即自动提交的数据库
如果是 form.save(commit=False) 那么只是帮你构造出Model对象
你需要自己调用Model.save方法

# 覆盖 #
数据库的 XXXField 有对应的 form 的 XXXField, 但有的时候 我们不想显示一个 input type="text" 而是想要 textarea
这时候就可以:
```
class AuthorForm(ModelForm):
    class Meta:
        model = Author
        fields = ('name', 'title', 'birth_date')
        widgets = {
            'name': Textarea(attrs={'cols': 80, 'rows': 20}),
        }
```

当然还可以直接就在From里定义相应的字段 覆盖默认实现



