# url转发 #
在 urls.py 里
url(r'正则表达式', app.views.handler, name='名字')
url(r'正则表达式', include(app.urls))

url方法的额外参数会被传给handler

命名捕获
(?P<pk>\d+)


```
extra_patterns = [
    url(r'^reports/$', credit_views.report),
    url(r'^reports/(?P<id>[0-9]+)/$', credit_views.report),
    url(r'^charge/$', credit_views.charge),
]
url(r'^credit/', include(extra_patterns)
```

# View #
1. 方法: 接受request 返回response
2. DetailView ListView

# 返回错误 #
1. return HttpResponseNotFound
2. raise Http404
3. handler404='mysite.views.my_custom_page_not_found_view' 这样错误就会交给这个view来显示

# View装饰器 #

装饰方法
require_http_methods(['GET','POST'])
require_GET/POST


gzip
cache

# 文件上传 #
简单例子1
```
def f7(request):
    if request.method == 'GET':
        return render(request, 'a3/f7.html')
    else:
        username = request.POST['username']
        file = request.FILES['file']
        return JsonResponse({'username': username, 'file': dir(file), 'file_': {
            'size': file.size,
            'name': file.name,
            'content_type': file.content_type
        }})
```

```
class UploadForm(forms.Form):
    username = forms.CharField(max_length=32)
    file = forms.FileField()

def f7(request):
    if request.method == 'GET':
        form = UploadForm()
        return render(request, 'a3/f7.html',{'form':form})
    else:
        form = UploadForm(request.POST, request.FILES)
        if form.is_valid():
            file = request.FILES['file']
            return JsonResponse({'username': form.cleaned_data['username'], 'file': dir(file), 'file_': {
                'size': file.size,
                'name': file.name,
                'content_type': file.content_type
            }})
        else:
            return JsonResponse({'code': 'bad'})


```

```
def handle_uploaded_file(f):
    with open('some/file/name.txt', 'wb+') as destination:
        for chunk in f.chunks():
            destination.write(chunk)
```

## 多文件上传 ##
```

file_field = forms.FileField(widget=forms.ClearableFileInput(attrs={'multiple': True}))

files = request.FILES.getlist('file_field')

```

# 快捷方法 #
render(request, template_name, context=None, content_type=None, status=None)

redirect(to,permanent=False)
to可以是
1. 绝对url /ceshi https://example.com/
2. model 将会调用模型的 get_absolute_url() 方法, model
3. view名 'a-b-c'

get_object_or_404(class, \*args, \*\*kwargs)
如果拿不到就触发一个 404
```
my_object = get_object_or_404(MyModel, pk=1)
相当于
    try:
        my_object = MyModel.objects.get(pk=1)
    except MyModel.DoesNotExist:
        raise Http404("No MyModel matches the given query.")
```

get_list_or_404 和上面类似

# 通用View #
https://docs.djangoproject.com/en/1.10/ref/class-based-views/generic-editing

