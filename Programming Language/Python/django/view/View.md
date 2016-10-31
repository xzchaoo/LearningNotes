https://docs.djangoproject.com/en/1.10/ref/class-based-views/base/

http_method_names = [支持的方法]

dispatch 方法负责根据当前的method 调用不同的方法 比如 get或post

http_method_not_allowed 方法不允许的时候调用该方法

TemplateView


get_context_data() 重写该方法提供自己的上下文数据

RedirectView 用于重定向
	get_redirect_url()

# DetailView #

# SingleObjectMixin #
该View与单个Object有关
model 指定了如何搜索对象, 使用model的话相当于时 queryset = XXXModel.objects.all(), 因此 queryset 更强大
queryset 指定了如何搜索对象
context_object_name 该object 在context里的名字 一般是 object 和 model类名的小写
get_object
方法版本的都比字段版本优先级更高

# SingleObjectTemplateResponseMixin #
template_name 和 上面类似

# MultipleObjectMixin #
支持多个对象
model 模型
queryset 

ordering 排序 将会应用到最终的qs上

allow_empty 默认是true, 是否允许空, 如果不允许, 则空的时候会404
paginate_by 分页大小
page_kwarg 默认是page 可以修改 支持URLConf 上的page参数作为页数

# MultipleObjectTemplateResponseMixin #
template_name

# FormMixin #
initial 指定表单的初始值
form_class
prefix 表单字段的前缀
success_url

get_form()
get_form_kwargs() 这里的返回值会被作为kwargs喂给form的构造器

form_valid() 默认行为是重订向到 success_url
form_invalid() 默认行为是返回之前的页面

# ModelFormMixin #
继承了 FormMixin SingleObjectMixin

model
fields

form_invalid() 默认行为是先保存 然后再跳转到success_url

# DeletionMixin #
用于支持以下的场景:
GET某个url, 表示要删除某个对象, 会显示一个页面询问你是否真的要删除
发一个POST请求 表示确实要删除

# ProcessFormView #
用于支持如下的工作流: GET某个页面, 然后发过来一个POST请求, 处理完之后重订向到某个页面

# ListView #


# CreateView #

