https://docs.djangoproject.com/en/1.10/ref/forms/fields/

required 是否必填
label 人可读的标签
initial 初始值
null 可否为null
blank 可否空字符串

widget 关联的widget

help_text 帮助内容
disabled 是否禁用

error_messages
可以让你定制错误消息
```
name = forms.CharField(error_messages={'required': 'Please enter your name'})
```

validators 这个字段用到的验证器


is_valid()
clean()
has_changed() 跟初始值相比是否改变过



杂
1. localize 关于本地化

常见类型
CharField TextInput empty='' validators=[min_length, max_length] 默认: strip=True
BooleanField CheckboxInput empty=False required=False
ChoiceField [Select] empty='' 需要指定choices 默认required=True 净化后的类型是字符串
TypedChoiceField 跟上面类似 接受 coerce 参数 尾 int,float,bool 之类 用于净化后的类型
MultipleChoiceField TypedMultipleChoiceField 多选


DateField 净化后的类型是 datetime.date 可以指定 input_formats 设置支持的格式
DateTimeField datetime.datetime
DecimalField min_value max_value max_digits最多的有效数字
DurationField datetime.timedelta
TimeField SplitDateTimeField


EmailField FloatField ImageField(图片选择) IntegerField RegexField(要求文本满足正则表达式) URLField(是一个url)
FileField max_length
FilePathField 允许用户从一个特定的目录选取文件 recursive=False 是否递归 matc 匹配该正则表达式的文件就会显示 allow_files是否允许选中文件 allow_folders是否允许选中目录
GenericIPAddressField ip选择

SlugField 要求文本只包含字母数字下划线和横线(-)



ComboField
MultiValueField 将多个field聚合成一个fiield
ModelChoiceField
ModelMultipleChoiceField
