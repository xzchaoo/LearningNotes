from django.http import HttpResponse, HttpResponseNotFound,Http404 


HttpResponse(内容,status=201)
HttpResponseNotFound('<h1>not found</h1>)
raise Http404('<h1>not found</h1>')


自定义错误页面
https://docs.djangoproject.com/en/1.10/topics/http/views/


# DetailView #
```
class A2UserDetailView(DetailView):
    model = A2User
    template_name = 'a2/detail.html'

url(r'^detail/(?P<pk>\d+)$', a2.views.A2UserDetailView.as_view(), name='detail'),
```

# ListView #
```
class A2UserListView(ListView):
    model = A2User
    template_name = 'a2/list.html'

url(r'^list/$', a2.views.A2UserListView.as_view(), name='list'),
```