最好给出一个例子来

给定一篇文章的每一行, 找出出现次数最多的单词, 前10个吧
假设文章只有英文字母和空格没有其他符号.

```
map:function(){
	假设 this.line 保存着当前这一行
	
	for(word : this.line){将 this.line 拆分成单词数组 之类并没有给出具体实现
		emit(word,{count:1});
	}
},
reduce:function(key,values){
	var ret={count:0};
	for(value : values){
		ret.count+=value.count;
	}
	return ret;
}
```

