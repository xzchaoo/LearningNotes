# 设置vim #
/etc/vim/vimrc
set tabstop=4
set shiftwidth=4
set smartident
set cindent

# vim #
i 插入
o 新插入一行 同时进入插入模式
q 退出
q! 退出不保存
wq 保存并退出
yy 复制该行
dd 删除该行
10dd 连续删除10行
u 撤销

gg 跳转到第一行
2gg 跳转到第二行
G 跳转到最后一行


/pattern n下一个 N上一个

# 修改运行级别 #
在 /etc/inittab 里
```
id:3:initdefault:
```

有的系统不是这昂:

# shell初始化顺序 #
## /etc/profile ##
加载 /etc/bash.bashrc
修改 PS1

加载 /etc/profile.d/*.sh


unset i 清除变量i的值

PS1可以用来判断是否运行于交互式

# 导出变量 #
export PATH

顺便可以赋值一下
export PATH=$PATH:/xxx

# 常见环境变量 #
HOME
PATH
PS1
BASH
HOSTNAME
LANG
PWD
UID

## 特殊变量 ##
$0 脚本的名称
$$ 当前shell的pid
$# 参数个数
$@ 参数数组 "$@" 依旧是数组 但有点区别, 当输入是 "张 三" 李四 的时候 $@ 的结果是 "张" "三" "李四" 而 "$@" 的结果是 "张 三" "李四"
$* 所有参数作为一个字符串

$_

$1 第1个参数
$2 第2个参数
${10} 第10个参数

# 数组 #
a=(1 2 3)
echo ${a[0]} 下标从0开始
echo ${a[1]}
a[2]=222
a[3]=333
a[7]=777
echo ${a[2]} 输出222 而不是3
echo ${a[3]} 输出333
echo ${a[4]} 输出空白
echo ${a[7]} 输出 777
echo ${a[*]} 输出 1 2 444 213 777


echo ${a[*]} 输出整个数组
另外for循环的时候需要这样:

按照上面的代码, 下面的代码将会输出 1\n2\n444\n213\n777\n
```
for i in ${a[*]}; do 
	echo $i
done
```

# test命令 #
test可以用方括号表达式代替

-r 可读
-w 可写
-x 可执行
-e 存在
-d 目录
-f 文件
-z 字符串长度为0
-n 字符串长度不为0

字符串1=字符串2


表达式1 -a 表达式2 and
表达式1 -o 表达式2 or

参数1 -OP 参数2 只支持数字
OP可以是 eq ne lt le gt ge

支持 && || 1!

# 函数 #
```
function myecho(){
	echo $1;
}

myecho sdf
```

# if/elif/else/fi #
如果 command 的返回值是0 那么久是成功
```
if command1 ; then
	...
elif command2 ; then
	...
else
	...
fi
```

```
if echo ceshi | grep ceshi > /dev/null ; then
	...
else
	...
fi
```

command 通常用 test 的 方括号表达式版本

```
if [ -r $file -a -w $file ]; then
	echo $file is readable and writeable
fi
```

1. 注意什么时候需要引号什么时候不用引号
2. 方括号里注意要有空格 否则报错!

# case #

```
a=1

case $a in
1)
	echo 1
;;
2|3)#甚至可以是 [Bb]l? 这样的通配符
	echo 2 or 3
;;
*)
	echo default
;;
esac
```


# for #

注意for里的变量不用$
```
for fruit in apple orange pear; do
	echo $fruit
done
```

# while #

```
a=1

while [ $a -lt 10 ]; do
	echo $a
	a=`expr $a + 1`
done
```

# until #
```

```




# shell选项设置 #
TODO
set shopt
P184


# 内置命令 #
P194

alias unalias

dirs pushd popd

# delcare #
声明一个变量的类型
比如声明a为int类型, 那么 a=1+1 结果就是2 而不是 "1+1" 并且当你弄错类型的时候也会报错, 并且该值会有该类型的默认值

-i 整型

declare -i a 则a=0
也可以手动指定初始值 declare -i a="0"
a=1+1 2
a=$a+3 则5, 注意这里比较严格 不可以有空格
a=" $a       +     3" 如果是字符串的话就可以有任意的空格了 但=号左右依旧不能有空格
a=1.5 报错了 但似乎不会终止脚本的运行


# read #
read ans 整行读为ans
read first_name last_name 一空格或换行为标准 读入两个值
read -a arr 将一行读成一个数组
-p 提示符

# let #
可以使用 ((语句)) 来代替 let "语句"

用于执行整数的数学运算
let a=a+3
let "a=a+b"
let "a+=5"

# 关于括号 #
## () ##
命令组
(date;date;)

初始化数组
a=(1 2 3)

$(date) 执行命令 相当于 `date`

## [[]] ##
[[ $a == z* ]] 如果$a以z开头
[[ $a == "z*" ]] 如果$a 等于z星号 这里星号就不会被解释了

## [] ##
是test的简化版
[ $a == z* ]
[ $a == "z*" ] 字面相等

## (()) ##
是let的简化版
((语句)) = let "语句"

括号里的变量都不需要加$(似乎你加了也不算错), 但是当你需要引用 $1 的时候你还是得要用$1

while ((...)) ; do # 这里的变量不用加$
done

for ((...;...;...)); do #之类的变量不用加$
done

括号里的执行结果
True -> 0
False -> 1
非0则1 0还是0

c=$((a+b)) 这个跟 c=((a+b)) 不一样 后者是错的 这是一个赋值语句
效果和 ((c=a+b)) 一样的


# 检查null值 #
[ "$name" =="" ] 或 [ -z "$name" ] 或 [ ! "$name" ]

# shift #
弹出第一个参数

# 简单 #
break continue 依旧... 不过他们支持 break n; continue n; 来跳出n层
local 标记为本地变量 否则默认是全局变量
函数的范围值返回是0~255

source 或 . 用于加载其他的sh文件

# 杂 #
eval
getopts

