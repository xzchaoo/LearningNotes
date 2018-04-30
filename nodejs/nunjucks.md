# 介绍 #
一个JS的模板引擎
语法和jinja2类似

总体来说还行, 但是WEBSTORM对其没有直接的语法支持, WS对Twig的模板语言有支持, 先安装它的插件, 然后修改File Types里, 添加对后缀.nkj的支持, 可以造成让WS把我们的模板文件当做Twig的模板文件处理, 但是两者的语法并不是完全一样, 导致有一些 指令 WS 不认识, 从而又导致了格式化后代码混乱


上一段代码

	{% extends "base.html" %}
	
	{% block header %}
	<h1>{{ title }}</h1>
	{% endblock %}
	
	{% block content %}
	<ul>
	  {% for name, item in items %}
	  <li>{{ name }}: {{ item }}</li>
	  {% endfor %}
	</ul>
	{% endblock %}


# 初始化 #

	var app = express();
	
	nunjucks.configure('views', {
	    autoescape: true,
	    express: app
	});
	
	app.get('/', function(req, res) {
	    res.render('index.html');
	});

# 模板语法 #
插值
	
	{{ username }}
	{{ user.name }}
	{{ user['name']}}

过滤器

	{{ foo | title }}
	{{ names | join(',') }}
	{{ foo | replace("foo", "bar") | capitalize }}
