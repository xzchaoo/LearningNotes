import re

# 注意要 ^$ 否则你看第二条也匹配成功了

print(re.match('\d+\.\d+\.\d+\.\d+', '192.168.1.1'))
print(re.match('\d+\.\d+\.\d+\.\d+', '192.168.1.1.a'))
print(re.match('^\d+\.\d+\.\d+\.\d+$', '192.168.1.1.a'))

s = 'hey, xzc is sub of xzchaoo, xz is not xzchaoo'
# 一次性 这样相当于只找第一个
m = re.match('(xzc?)', s)
if not m:
    print('fail to match')
else:
    print(m.group(0))
    print(m.span(0))

# 先编译再使用
c = re.compile('(xzc?)')

# 招到所有的匹配项
# 没个m都是一个MatchObject 包含了比较丰富的信息 比如 子字符串的位置
for m in c.finditer(s):
    print(m)

# 下面这些只有匹配的子字符串
print(c.findall(s))

# results = c.search(s)
# print(results)

template = '''
你好, {name}
你的语文成绩是 {chinese}
你的数学成绩是 {math}
你的英语成绩是 {english}
'''

c = re.compile('\{(\w+)\}')
d = {
    'name': '张三',
    'chinese': 97,
    'math': 96,
    'english': 95
}

lastIndex = 0
subs = []
for i in c.finditer(template):
    key = i.group(1)
    value = str(d[key])
    subs.append(template[lastIndex:i.span(0)[0]])
    subs.append(value)
    lastIndex = i.span(0)[1]


def tihuan(m):
    return str(d[m.group(1)])

print('==')
print(c.sub(tihuan, template))

(?=) 后匹配断言
(?!...) 后不匹配断言
(?<=...) 前匹配断言
(?<!...) 前不匹配断言

反向引用 \1 \2 \3 比如 (.+) \1 匹配 '55 55'

\b 字符边界
\B 非字符边界
\d 数字
\D 非数字
\w 字符
\W 非字符
\s 空白
\S 非空白


1. compile
2. match
3. search
4. fullmatch
5. split
6. findall
7. finditer
8. sub
9. subn
10. escape
11. purge


如果是一次性的就直接 re.match(pattern,string) 就行了

# match object #
expand(template)
group(0) 整个匹配的字符串
group(1) 第一个匹配组
groups() 所有组
groupdict()
start([group]) 这个组的起点
end([group]) 这个组的终点
span([group]) == (start([group]),end([group]))

```
m = re.match(r"(?P<first_name>\w+) (?P<last_name>\w+)", "Malcolm Reynolds")
m.groupdict()
{'first_name': 'Malcolm', 'last_name': 'Reynolds'}
```