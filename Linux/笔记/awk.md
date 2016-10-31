# 参数 #
-F 指定分隔符
-f 脚本文件

# awk的脚本文件 #
首先会执行BEGIN块 最终会执行END块
然后对于每一行, 如果他满足patternN 就执行对应的块

BEGIN{
}
pattern1{
}
pattern2{
}
{这个块对于每行总是执行
}
END{
}

# 变量 #
变量在哪个块里定义都行, 作用于都是全局的, 但是不能在块外定义(自己试试就知道了)

a = 1
a = b + c
x++
y+=1

## 初始值 ##
当变量第一次被用到的时候 根据他的场景有不同的初始值, 如果能推断是个int, 那么默认就是0
  

## 数组 ##
## 关联数组 ##
跟数组的养发一样
count 关联数组自动创建 count["张"] 自动创建
```
/^张/{
	++count["张"]
}
/^李/{
	++count["李"]
}
```

# pattern #
pattern可以是:
	1. /regexp/ 该行要匹配该正则表达式
	2. $2 ~ /regexp/ 第2列要满足正则表达式
	3. $5 > 100 第5列的值(解释为整型) 大于100
	4. $1*$2 > 100
	5. 其它布尔表达式



# 语句 #
支持
1. 基本的 数学运算 赋值 比较, 支持 2^3 2的3次方, 支持逻辑运算
2. 三目运算符 c = a > b ? a : b
3. if for while break continue 跟你想的完全一样 不描述了
4. next 跳过该行的处理
5. exit 停止运行 但END依旧执行

## for ##
for(i in names){
	print(names[i]) i是索引 可能是一个数字或字符串
}

# 函数 #
## sub ##
sub(/regexp/,replacement)
sub(/regexp/,replacement, $1) 对第一列进行替换 默认是$0

## gsub ##
sub的全局版本

## index ##
index(str,substr) 下标基于1

## length ##
length(str) 返回长度

## substr ##
substr(str, offset[, length])

## match ##
result = match(str, /regexp/)

## split ##
split(str, array[, splitor])
默认用FS(默认是空格)来拆分
将拆分结果保存到array里

## sprintf ##
result = sprintf(format, var1 ,var2)

## toupper/tolower ##


## 杂 ##
systime() 时间戳 秒
strftime() 时间格式化

## 内置数学函数 ##
log sin sqrt
srand() 时间作为种子
rand() [0,1) 随机数
int 用于类型转换


## print ##

## printf ##
s 字符串
d 十进制数
f 实数
e 科学计数法
x 十六进制数

%-15s: 宽度15 左对齐 字符串

## 自定义函数 ##
```
function 函数名 (参数1, 参数2, ...){
	...
	return ...
}
```




# 特殊变量 #
$0 整行
$1 第一列
NR 原本的行号
NF 该行的列数

# 杂 #
1. 井号可以注释
2. "date" | getline d;print d;
3. print $1, $2 | "sort -r"
4.

# 命令行参数 #
 