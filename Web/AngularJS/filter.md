https://github.com/a8m/angular-filter

# 集合 #
e in a1 | concat: a2 两个数组追加

unique 属性相同的元素只保留一个

filterBy:['id']:1 相当于 where id = 1
filterBy:['id','id2']:1 相当于 where id = 1 or id2 = 1
filterBy:['id + id2']:1 相当于 where id + id2 = 1

first 集合里的第一个元素
first:n 集合的前n个元素
first:n:'bool表达式' 前n个满足该表达式的元素
还有last版本

flatten 将数组元素平坦化

join:',' 逗号隔开

fuzzy 模糊搜索, 和 fuzzyBy 不一样, 它将会搜索该对象的所有属性, 而 fuzzyBy 只能一个
fuzzyBy:'属性':关键字:是否大小写敏感

groupBy countBy
```
<ul>
	<li ng-repeat="(key, value) in a | groupBy: 'sex'">
		Sex: {{key}}
		<ul>
			<li ng-repeat="u in value">
				{{u.id}} {{u.name}}
			</li>
		</ul>
	</li>
</ul>
```


chunkBy:n 每n个元素分为一组
```
<ul>
	<li ng-repeat="(key, value) in a | chunkBy: 3">
		第 {{key}} 组
		<ul>
			<li ng-repeat="u in value">{{u}}</li>
		</ul>
	</li>
</ul>
```

default:默认值 用于指定默认值

where:{id:1} where id = 1
omit:表达式 满足表达式的元素被忽略
pick:表达式 满足表达式的元素被保留
remove: i1:i2:i3... 从数组里移除这些元素
removeWith:{ id: 1, name: 'foo' }

数组反转

获取前n个或后n个元素
数组是否为空
是否包含某元素

# 字符串 #
首字母大写
进行 uriEncode uriComponentEncode
startsWith endsWith
reverse split
trim ltrim rtrim

'foo'|repeat:3:'-'
foo-foo-foo


# 数学 #
min max abs
percent
sum
