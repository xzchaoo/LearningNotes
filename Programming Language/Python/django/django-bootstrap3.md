pip install django-bootstrap3

bootstrap3

```
{% load bootstrap3 %}

{# Display a form #}

<form action="/url/to/submit/" method="post" class="form">
    {% csrf_token %}
    {% bootstrap_form form %}
    {% buttons %}
        <button type="submit" class="btn btn-primary">
            {% bootstrap_icon "star" %} Submit
        </button>
    {% endbuttons %}
</form>
```

```
{# Load the tag library #}
{% load bootstrap3 %}

{# Load CSS and JavaScript #}
{% bootstrap_css %}
{% bootstrap_javascript %}

{# Display django.contrib.messages as Bootstrap alerts #}
{% bootstrap_messages %}

{# Display a form #}
<form action="/url/to/submit/" method="post" class="form">
  {% csrf_token %}
  {% bootstrap_form form %}
  {% buttons %}
    <button type="submit" class="btn btn-primary">
      {% bootstrap_icon "star" %} Submit
    </button>
  {% endbuttons %}
</form>

{# Read the documentation for more information #}
```


http://django-bootstrap3.readthedocs.io/en/latest/templatetags.html

# bootstrap_form #
显示表单
layout='inline'

# bootstrap_form_errors #
显示错误信息
type = all 或 fields 或 non_fields

# bootstrap_formset #
# bootstrap_formset_errors #

# bootstrap_field #
渲染单个字段, 它可以从 bootstrap_form 之类的元素那里继承很多配置

field=要渲染的字段 比如 form.username
layout = horizontal inline
field_class = 包住该input的那个div的class
form_group_class = 包住该input和label的那个div的class 默认是 form-group
label_class = 对应的label的class, 默认是 control-label
show_help = True 是否要显示help信息
show_label = True 是否要显示label
exclude = [] 要排出渲染的字段
set_required = True 是否设置元素的require属性
set_disabled = False 是否要设置元素的disabled属性
size = 控制div.form-group的大小 small medium large
horizontal_label_class = 当layout=horizontal的时, 设置为label添加class:col-md-3
horizontal_field_class = 类似上面
addon_before
addon_after
error_css_class = has-error
required_css_class
bound_css_class = has-success


# bootstrap_label #
content = 标签内容
label_for
label_class
label_title

# bootstrap_button #
content
button_type submit reset button link
icon bootstrap的图标名称
button_class 额外的css类
size xs sm small md medium lg large
href 如果指定了这个属性那么会渲染成a
name
value

# bootstrap_icon #
{% bootstrap_icon "star" %}

# bootstrap_alert #
显示一个alert
content
alert_type = info warning danger success
dismissable = True 该 Alert 是否可以关闭
 
# buttons #
{% buttons submit='OK' reset="Cancel" %}{% endbuttons %}
简单的渲染两个按钮

# bootstrap_messages #
渲染消息系统的内容

{% bootstrap_messages %} 即可

# bootstrap_pagination #
渲染分页
page 引用page对象
page_to_show 总共只会显示几个页的按钮 对于的可能就隐藏起来了
url  
size 分页按钮的大小 
extra url上要携带的额外参数 比如 {keyword='xzc'} 用于生成分页按钮的url
parameter_name = page


# bootstrap_jquery_url  #
# bootstrap_javascript_url #
# bootstrap_css_url #
# bootstrap_css #
# bootstrap_javascript #

# 设置 #
在 settings.py 里
```
# Default settings
BOOTSTRAP3 = {

    # The URL to the jQuery JavaScript file
    'jquery_url': '//code.jquery.com/jquery.min.js',

    # The Bootstrap base URL
    'base_url': '//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/',

    # The complete URL to the Bootstrap CSS file (None means derive it from base_url)
    'css_url': None,

    # The complete URL to the Bootstrap CSS file (None means no theme)
    'theme_url': None,

    # The complete URL to the Bootstrap JavaScript file (None means derive it from base_url)
    'javascript_url': None,

    # Put JavaScript in the HEAD section of the HTML document (only relevant if you use bootstrap3.html)
    'javascript_in_head': False,

    # Include jQuery with Bootstrap JavaScript (affects django-bootstrap3 template tags)
    'include_jquery': False,

    # Label class to use in horizontal forms
    'horizontal_label_class': 'col-md-3',

    # Field class to use in horizontal forms
    'horizontal_field_class': 'col-md-9',

    # Set HTML required attribute on required fields
    'set_required': True,

    # Set HTML disabled attribute on disabled fields
    'set_disabled': False,

    # Set placeholder attributes to label if no placeholder is provided
    'set_placeholder': True,

    # Class to indicate required (better to set this in your Django form)
    'required_css_class': '',

    # Class to indicate error (better to set this in your Django form)
    'error_css_class': 'has-error',

    # Class to indicate success, meaning the field has valid input (better to set this in your Django form)
    'success_css_class': 'has-success',

    # Renderers (only set these if you have studied the source and understand the inner workings)
    'formset_renderers':{
        'default': 'bootstrap3.renderers.FormsetRenderer',
    },
    'form_renderers': {
        'default': 'bootstrap3.renderers.FormRenderer',
    },
    'field_renderers': {
        'default': 'bootstrap3.renderers.FieldRenderer',
        'inline': 'bootstrap3.renderers.InlineFieldRenderer',
    },
}
```