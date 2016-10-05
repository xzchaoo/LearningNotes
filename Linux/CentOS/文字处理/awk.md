awk 'instructions' files
awk -f scriptfile files

语句块

字段
$0 整行
$1 第一个字段

```
selector{
	做动作
}
```

selector是一个正则表达式

# 参数 #
-f 指定脚本
-F , 将分隔符改成逗号
-v var=value 设置变量

转义

\n n=1~9 表示引用


基本的正则表达式
grep sed

扩展的正则表达式
egrep awk

比如其他语言的文字, 很多不是普通的英文字母

字符类 [::]
整理符合 [..]
等价类 [==]
他们只能出现在方括号中:

[[:alnum:]]+
alpha
blank
digit





s/pattern/replacement/flags

# awk #
FS=":" 设置字段的分隔符
也可以通过 -F 指定

OFS 输出字段分隔符
FIELDWIDTHS 用空格隔开的数组, 表示每个字段的长度
FS 输入字段的分隔符
RS 输入记录的分隔符
OFS 输出字段的分隔符
OPS 输出记录的分隔符

ARGC 
ARGIND
ARGV
CONVFMT 数字的转换格式
ENVIRON ENVIRON["HOME"] 引用环境变量
FILIENAME
FNR
IGNORECASE 如果非0就忽略大小写
NF 数据文件中数据字段的个数
NR 已处理的输入记录个数
OFMT 显示数字的输出格式

可以在BEGIN中定义变量
处理过程中也可以修改变量

定义数组变量
array[index]=value
index可以是字符串

qqs["xzchaoo"] = "70862045"

for循环
for (var in array){
	array[var]
}

删除元素
delete array[index]

```
/PATTERN/{
	语句
}
```
当整行匹配 PATTERN 时

```
$2 ~ /PATTERN/ {
	语句
}
```
当$2 匹配 PATTERN 时

```
$3 == 0 {
	语句
}
```

== <= < >= >
他们是精确匹配的

# if语句 #
if($1>20){
	x=1
	print(...)
}else{
	x=2
	print ...
}


# while 语句 #
```
while(condition){
	statements
}
```
支持 break continue

# for #
```
for(初始化;条件;迭代){
	语句
}
```

# print #
print $2 打印第二列

# printf #
printf "format", var1, var2, var3
格式
%[modifier]control-letter
c 字符
d 整数
e 科学计数法
f 浮点数
s 文本
- 左对齐(默认是右对齐)
10.2 宽度为10, 精度为2

tail -n +2
表示从第二行读到尾

sed '1d' 2.txt
删除第一行

gawk 提供了一些用于字符串处理的函数
asort(array[, dest]) 将数组排序(字符串的排序), 结果可以放到一个dest里
asorti(array[, dest])

length(str) 长度
index(str,substr) 索引
match(string, regexp)

tolower toupper
substr(s, offset [,length])
sprintf(format,vars) 格式化生成一个字符串
sub(regexp, s, t) 在t里搜索 第一个满足 regexp 的子串, 将其替换成s
gsub 同上, 全局搜索
split(s, a[, r]) 利用FS或正则表达式r, 将s拆分成数组a, 返回字段的个数


时间函数
有需要再查
mktime
strftime
systime

自定义函数

```
function name([vars]){
...
return int(2*rand())
}
```
函数必须出现在 BEGIN块之前

将函数都放在一个函数库里
然后 gawk -f 我的库 -f 脚本 data

