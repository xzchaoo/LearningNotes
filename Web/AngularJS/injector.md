相当于spring里的容器

```
var http = $injector.get('$http');

var result = $injector.invoke(ceshi,{这里设置的是函数的this},{other:2});
result==123
function ceshi($q, other){
$q会被注入
other会被注入
return 123;
}
```