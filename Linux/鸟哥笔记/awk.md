表达式可以接受多个如下的语句:
condition{语句} 或 condition{语句}condition{语句}condition{语句}
condition表示对应的语句块的执行条件, 有2个特殊的执行条件BEGIN和END, 只会在程序的开始和结束的时候执行
如果条件不写的话, 那么对于每一行, 语句块都会执行


$0表示整行, $1表示字一个字段
字段默认是用 空白 隔开的, 可以用 -F=":" 来指定新的分隔符为":", 或在BEGIN阶段里执行: FS=":"

用括号括起来的内容会进行表达式计算
