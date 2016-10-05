## const ##
action 指示该参数要干嘛:
1. store 这是默认值 表示要存储参数提供的值
2. store_const 表示要存储一个常数, 这个常数由 const 参数提供
3. store_true 相当于 store_const + const=True
4. store_false 类似...
5. append 追加到数组
6. append_const
7. count 统计该参数出现得次数 -vvv 的时候特别有用
8. help 打印帮助信息 
9. version 打印版本信息 版本信息由version指定 

## args ##
nargs用于控制参数个数 整数表示刚刚好的个数 ?表示0或1个 +表示1或多个 * 表示0个或多个 argparse.REMAINDER表示所有剩下的参数
当使用?的时候, 会产生这样的参数需求: [-d [d]], 因此有3种形式:
1. 完全省略掉, 此时会采用default的只
2. -d 此时会采用const的值
3. -d 2 此时会采用2

一个比较好的场景是:
```
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('infile', nargs='?', type=argparse.FileType('r'),
...                     default=sys.stdin)
>>> parser.add_argument('outfile', nargs='?', type=argparse.FileType('w'),
...                     default=sys.stdout)
>>> parser.parse_args(['input.txt', 'output.txt'])
Namespace(infile=<_io.TextIOWrapper name='input.txt' encoding='UTF-8'>,
          outfile=<_io.TextIOWrapper name='output.txt' encoding='UTF-8'>)
>>> parser.parse_args([])
Namespace(infile=<_io.TextIOWrapper name='<stdin>' encoding='UTF-8'>,
          outfile=<_io.TextIOWrapper name='<stdout>' encoding='UTF-8'>)

```

如果nargs=1 那么会产生一个大小为1的列表 这和不指定的行为还是有点不一样的


## const/default ##
用于持有一个常数 当用户不提供的时候就采用它, 它和default有点小小的区别
对于 [-d [d]], 有3种可能的配置:
1. 完全省略掉, 此时会采用default的值
2. -d 此时会采用const的值
3. -d 2 此时会采用2

当 default=argparse.SUPPRESS 的时候, 则表示当没有为该参数提供值地时候 namespace 里就没有该key
假设不设置这个选项, 那么namespace里可能有该key, 只不过它对应的值是None

## type ##
期待接受一个函数 用于字符串转换到具体类型
常见: float int open bool

要特别注意bool, 所有的非空字符串都会被当成True, 就算字符串是 'False'

```
def to_tuple(str):
    return tuple(map(int, str.strip().split('-')))

p.add_argument('-t', type=to_tuple, metavar='t', required=True)

-t 1-6 得到的是 (1,6)的元组

```

## choices ##
提供一些枚举


## required ##
必须提供该参数
default和required同时用的时候, default失效

## 杂 ##
help
metavar 显示help的时候通常会有 -d D metavar就是用来修改那个'D'的
dest 指示该值要存储到哪里, 当你指定名字为 -d 时, 默认是存到d属性的
 