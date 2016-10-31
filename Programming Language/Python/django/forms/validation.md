form validation 发生在对数据进行 clean 的时候

有几个地方会触发validation:
1. 调用 is_valid() 这个最常用
2. 调用 full_clean()
3. 访问 errors 属性

# Validator #
ValidationError 

# Form的验证执行顺序 #
调用顺序
1. 你调用 form.is_valid()
2. 发现还没有clean过, 调用 self.full_clean()
	1. _clean_fields
		1. for 每个field
			1. 获取它的value
			2. 调用field.clean(value,初始值) 获得claned后的结果 放到 cleaned_data 里
				1. value = self.to_python(value)
				2. self.validate(value) 进行一些required empty之类的检查
				3. self.run_validators(value) 运行验证器
			3. 如果form存在 clean_<fieldname> 方法就调用它 它的返回值会覆盖之前的返回值 放到 cleaned_data 里
			4. 如果发生VE add_error
	2. _clean_form
		1. self.clean() 如果返回值不为空 那么就用它代替 self.cleaned_data
		2. self.clean() 方法目前什么都不做 直接返回 self.claned_data
	3. _post_clean
		1. 钩子函数 目前没有作用
3. 如果有错误就返回False, 否则True

下面是利用了 2.1.1.3 步, 在这里是对邮箱进行验证
```
class ContactForm(forms.Form):
    # Everything as before.
    ...

    def clean_recipients(self):
        data = self.cleaned_data['recipients']
        if "fred@example.com" not in data:
            raise forms.ValidationError("You have forgotten about Fred!")

        # Always return the cleaned data, whether you have changed it or
        # not.
        return data
```


下面利用了2.2.1步
```

class ContactForm(forms.Form):
    # Everything as before.
    ...

    def clean(self):
        cleaned_data = super(ContactForm, self).clean()
        cc_myself = cleaned_data.get("cc_myself")
        subject = cleaned_data.get("subject")

        if cc_myself and subject:
            # Only do something if both fields are valid so far.
            if "help" not in subject:
                raise forms.ValidationError(
                    "Did not send for 'help' in the subject despite "
                    "CC'ing yourself."
                )
```

```
class ContactForm(forms.Form):
    # Everything as before.
    ...

    def clean(self):
        cleaned_data = super(ContactForm, self).clean()
        cc_myself = cleaned_data.get("cc_myself")
        subject = cleaned_data.get("subject")

        if cc_myself and subject and "help" not in subject:
            msg = "Must put 'help' in subject when cc'ing yourself."
            self.add_error('cc_myself', msg)
            self.add_error('subject', msg)
```



# ValidationError #
```
raise ValidationError(
    _('Invalid value: %(value)s'),
    code='invalid',
    params={'value': '42'},
)
```
建议使用 _ 也就是 gettext 实现国际化
充分利用code, 利用params来做插值

## 一次性触发多个错误 ##
```
raise ValidationError([
    ValidationError(_('Error 1'), code='error1'),
    ValidationError(_('Error 2'), code='error2'),
])
```

## 使用 validators ##
设置字段默认的验证器
```
from django.forms import CharField
from django.core import validators

class SlugField(CharField):
    default_validators = [validators.validate_slug]
```

在定义form的时候设置验证器
```
slug = forms.CharField(validators=[validators.validate_slug])
```

