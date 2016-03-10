underscore

集合的函数
	遍历/过滤/map
	查找位置
	最大/最小
	every/some
	reduce操作
	排序
	groupBy/countBy
	shuffle打乱/sample(随机样本)
	partition 将满足的元素放在ret[0],不满足的放在ret[1]
数组的函数
	first/initial/last/rest 前n个/排除最后n个/后n个/排除前n个
	compact 去掉所有false值
	flatten 将数组摊开一维
	without 数组作差,使用===判断
	union 数组并集 会自动去掉重复的
	intersection 相交
	uniq 排除重复元素
	zip和unzip
	indexOf/sortedIndexOf/lastIndexOf/findIndex/findLastIndex
	range(start,stop,step)

与函数有关的函数
	bind(func,context,*params)
	delay 延迟若干时间之后再调用真的函数
	defer 类似setTimeout(0) 等当前调用栈清空的时候
	throttle(function,wait,options) 节流阀 wait之内最多触发一次
	debounce 调用之后只有当指定的时间内都没有再次调用才会调用真的函数
	once 只调用一次
	after 只有在调用了count次之后才会执行真的函数
	before 只能调用count次
	wrap 对函数进行包装

与对象有关的函数
	keys/allKeys/values
	mapObject 对对象的每个属性进行map
	pair 将对象变成[[key1,value1],[key2,value2]]
	invert 使得对象键值相反
	functions 返回对象的所有方法名
	findKey(obj,pred) 对对象的key进行遍历查找
	extend
	pick 返回一个副本:只保留一个对象的指定属性
	omit 返回一个副本:删除一个对象的指定属性
	clone 浅复制
	has 是否包含key
	isXXX的判断
杂
	noop 不操作的函数
	random(min,max)
	mixin 可以为underscore添加自定义函数
	iteratee(value)
		相当于是生成了一个这样的函数:
		function(item){return item.value;}
	uniqueId(prefix) 生成一个唯一的id
	escape 转义html字符
	unescape 反转义
	now 当前的时间戳
	
	template模板操作
		这个具体再去看一下教程

链式语法
	_.chain(obj)...do...something...value()

underscore.string
https://github.com/epeli/underscore.string
	npm install underscore.string
	var s = require("underscore.string");
	s("   epeli  ").trim().capitalize().value();

	如果想和lodash一起用
	那么 npm install underscore.string.fp
	var s = require('underscore.string.fp');

	数值格式化
	首字母大写
	字符串分解:
		chop("whitespace", 3);
		// => ["whi", "tes", "pac", "e"]
	clean(str) 将前后空白都删除 中间的空白只保留一个
	字符串变成字符数组
	是否包含子串/子串出现的次数
	转义html字符/反转义
	字符串的插入/替换 操作
	join操作
	一个字符串->数组 用\n分割
	wrap 
	reverse
	splice操作
	startsWith/endsWith
	前驱后继
	标题化/骆驼化/类名化
	trim/ltrim/rtrim
	truncate(str,maxLength,[truncateString='...'])将字符截短
	words 从字符串里提取单词
	sprintf 不多了
	pad(str,length,[padChar,type]) 操作
		type为left right both
	lpad/rpad/lrpad
	toNumber(str,小数位数)
	strRight/strRightBack/strLeft/strLeftBack
		从字符串的左边开始搜索一个pattern, 然后返回匹配这个pattern的子串之后的字符串
	toSentence(array,[delimiter,lastdelimiter]) 类似join
		toSentence([1,2,3],', ',' and ')
		1, 2 and 3
	repeat(str,count,[seperator])
		repeat('a',3,'b')
		ababa
	quote/unquote/q (str,quoteChar) 加引号 反加引号

		




