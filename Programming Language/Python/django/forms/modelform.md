# 简介 #
通常你会发现你的Form和Model几乎一样, 这样就带来了冗余
此时可以用ModelForm, 直接根据一个Model 生成对应的Form

```
class ArticleForm(ModelForm):
	class Meta:
		model = Article
		fields = ['pub_date', 'headline', 'content', 'reporter']
```

## 根据model构造form ##
form = ArticleForm(instance=article)

## 根据form创建或保存model ##
form.save()

model = form.save(commit=False)
model. ...
model.save()

当你使用 commit=True 的版本的时候 多对多(m2m) 关系也会跟着保存的
如果你是
```
model = form.save(commit=False)
...
model.save()
那么你需要手动调用 form.save_m2m()
```

## model的field与form的field的对应关系 ##
常见的field肯定都时可以自动对应的, 这里需要关注几个情况

1. model.CharField 默认对应 forms.CharField, 默认显示成input元素, 如果我想要显示成 textarea 该怎么做.
2. 外键或一对一 ->　ModelChoiceField
3. 多对多　-> ModelMultipleChoiceField
4. verbose_name -> label
5. blank=True -> reuqired=False, 否则为True
6. 如果model的field是choices 那么就显示成select

## 选择使用哪些field ##
```
from django.forms import ModelForm

class AuthorForm(ModelForm):
    class Meta:
        model = Author
        fields = '__all__'
```
特殊字符串 '__all__' 表示所有字段
可以使用 fields 或 exclude 来指定
默认情况下 所有 editable 都会自动排除掉

默认情况下会根据 model 创建一个对应的 form, field之间有一定的映射关系
它可以通过下面的方式做一些定制

```
class AuthorForm(ModelForm):
	slug = CharField(validators=[validate_slug]) 不会自动生成slug字段了, 因为你提供了
    class Meta:
        model = Author
        fields = ('name', 'title', 'birth_date')
		localized_fields = ('birth_date',) 允许这个字段的本地化
        widgets = {
            'name': Textarea(attrs={'cols': 80, 'rows': 20}),
        }
        labels = {
            'name': _('Writer'),
        }
        help_texts = {
            'name': _('Some useful help text.'),
        }
        error_messages = {
            'name': {
                'max_length': _("This writer's name is too long."),
            },
        }
```

## 支持表单继承 ##
注意Meta类也要继承相应的类
```
class EnhancedArticleForm(ArticleForm):
	def clean_pub_date(self):
		...
class RestrictedArticleForm(EnhancedArticleForm):
	class Meta(ArticleForm.Meta):
		exclude = ('body',)
```

继承不仅可以添加额外的字段还可以删除字段, 比如放到 exclude() 里 如果无效的话直接 field_name = None 就行

## modelform_factory工厂函数 ##
```
>>> from django.forms import modelform_factory
>>> from myapp.models import Book
>>> BookForm = modelform_factory(Book, fields=("author", "title"))
```

## ModelFormSet ##
```
>>> from django.forms import modelformset_factory
>>> from myapp.models import Author
>>> AuthorFormSet = modelformset_factory(Author, fields=('name', 'title'))
```

