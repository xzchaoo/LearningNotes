有的时候需要在一个页面里编辑多个对象 就可以用formset来解决
前端其实还是只有一个form元素, 但是通过一些命名规则 这些数据传递到后端来之后就可以被解析为多个form了

将一个普通的Form制作成FormSet
```
from django.forms import formset_factory
ArticleFormSet = formset_factory(ArticleForm)
```


## formset_factory ##
extra=2 表明有2个额外的form 加上一个初始化的 一共会显示3个form
max_num=1 表示纵的只有一个form, 此时extra就失效了

validate_max 表示是否要验证提交的表单数量不超过 max_num
validate_min 表示是否要验证提交的表单数量不小于 min_num

can_order 是否允许改变表单的顺序, 具体看文档
can_delete 是否可以移除form

## 给formset添加字段 ##
formset 不仅是多个form的集合 本身也可以有一些字段

```
class BaseArticleFormSet(BaseFormSet):
	def add_fields(self, form, index):
		super(BaseArticleFormSet, self).add_fields(form, index)
		form.fields["my_field"] = forms.CharField()

ArticleFormSet = formset_factory(ArticleForm, formset=BaseArticleFormSet)
formset = ArticleFormSet()
```

## 给form传递参数 ##
```
class MyArticleForm(ArticleForm):
    def __init__(self, *args, **kwargs):
        self.user = kwargs.pop('user')
        super(MyArticleForm, self).__init__(*args, **kwargs)
ArticleFormSet = formset_factory(MyArticleForm)
formset = ArticleFormSet(form_kwargs={'user': request.user})
```

或, 重写你的FormSet的方法, 就可以在构造传递个Form构造器的kwargs
```
class BaseArticleFormSet(BaseFormSet):
     def get_form_kwargs(self, index):
         kwargs = super(BaseArticleFormSet, self).get_form_kwargs(index)
         kwargs['custom_kwarg'] = index
         return kwargs
```


## 渲染formset ##
```
{{ formset }}
```
或
```
<form method="post" action="">
    {{ formset.management_form }}
    <table>
        {% for form in formset %}
        {{ form }}
        {% endfor %}
    </table>
</form>
```

```
<form method="post" action="">
    {{ formset.management_form }}
    {% for form in formset %}
        <ul>
            <li>{{ form.title }}</li>
            <li>{{ form.pub_date }}</li>
            {% if formset.can_delete %}
                <li>{{ form.DELETE }}</li>
            {% endif %}
        </ul>
    {% endfor %}
</form>
```

## 在一个view里使用多个formset ##
form应该也有类似的办法吧 主要是通过prefix
```
from django.forms import formset_factory
from django.shortcuts import render
from myapp.forms import ArticleForm, BookForm

def manage_articles(request):
    ArticleFormSet = formset_factory(ArticleForm)
    BookFormSet = formset_factory(BookForm)
    if request.method == 'POST':
        article_formset = ArticleFormSet(request.POST, request.FILES, prefix='articles')
        book_formset = BookFormSet(request.POST, request.FILES, prefix='books')
        if article_formset.is_valid() and book_formset.is_valid():
            # do something with the cleaned_data on the formsets.
            pass
    else:
        article_formset = ArticleFormSet(prefix='articles')
        book_formset = BookFormSet(prefix='books')
    return render(request, 'manage_articles.html', {
        'article_formset': article_formset,
        'book_formset': book_formset,
    })
```






# validation #
当所有的form都验证通过之后 才会去验证formset
比如当你允许同时创建多个文章的时候, 你可以在formset里保证title是唯一的



