https://github.com/aui/art-template

会暴露全局对象 template
var html = tamplate(id, data); 如果没有data那么就返回渲染函数 否则返回结果
var renderFunc = template.compile(source, options); 返回渲染函数
template.render(source,options) 返回渲染结果

修改选项
template.config(name, value)


{{content}} 插值 会自动转义
{{#content}} 插值 不会自动转义

if表达式
```
{{if admin}}
	<p>admin</p>
{{else if code > 0}}
	<p>master</p>
{{else}}
    <p>error!</p>
{{/if}}
```

遍历
```
{{each list as value index}}
    <li>{{index}} - {{value.user}}</li>
{{/each}}

自动有$value $index属性
{{each list}}
    <li>{{$index}} - {{$value.user}}</li>
{{/each}}
```

引入其他模板
```
{{include 'template_name'}}
{{include 'template_name' news_list}}
```

注册辅助方法
```
template.helper('dateFormat', function (date, format) {
    // ..
    return value;
});
```

使用辅助方法
```
{{time | dateFormat:'yyyy-MM-dd hh:mm:ss'}}
{{time | say:'cd' | ubb | link}}

```


