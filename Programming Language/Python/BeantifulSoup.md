# 常见需求 #
建议将常用到的一些操作的例子记录在这里.

+33
23+14

56






```
import requests as req
from bs4 import BeautifulSoup as jsoup

html = """
<body id="b">
<div id="a">
    <span>
    1
    2
    3
    </span>
</div>
<!-- asdf -->
<div id="ok">111</div>
<div>222</div>
</body>
"""

# content = req.get('http://www.bilibili.com/').text
d = jsoup(html, 'html.parser')
#print(d.find('div', {'id': 'ok2'}))
print(list(d.span.string))
print('=')
print(list(d.span.strings))
print('=')
print(list(d.span.stripped_strings))

# print(d.body.find_all('div'))
# print(d.title.text)
# print('title=' + str(d.title.text))
# print(d.body.find('div'))
# print(d.body.contents)

```

# 概念 #
将html文档转成一个树形结构, 每个节点是一个python对象, 有4种: Tag, NavigableString, BeautifulSoup, Comment.

# Tag #
对应了html里的一个元素

## 属性 ##
一个tag可以被当做字典使用, 用来修改它的属性

name tag的名字, 比如 div

tag['class']
tag.attrs 的结果是属性的字典

有些属性是多值属性, 比如 class, 那么它们的值可以是一个数组

# NavigableString #
被包含在tag内的字符串, 比如
```
<div>ceshi
<span>啊哈哈</span>
</div>
```
在这个例子里以供有3个字符串, 第一个是 "ceshi" 第二个是 "换行" 第三个是 "换行"

# BeautifulSoup #
是一个特殊的对象, 表示了一个文档的全部内容, 他可以被当做Tag使用

## Comment ##
注释, 一般没用...


# 用法 #
from bs4 import BeautifulSoup
d = BeautifulSoup('一段html...','html.parser') 其实有很多解释器, 这里用的是 html.parser, 具体可以去官网看
构造器还可以接受文件对象
d是一个 上面提到的 BeautifulSoup 对象, 你可以认为它就是根元素, 反正用起来确实差不多

# 访问父节点 #
parent 直接父节点
parents 所有的祖先 从下往上, 是一个迭代器

比如下面的例子, d3的parents是 迭代器[d2,d1,document] document就是那个特殊的 BeautifulSoup 对象!
```
<div id="d1">
<div id="d2">
<div id="d3">
</div>
</div>
</div>
```

# 兄弟节点 #
会访问到 String 和 Comment ...
next_sibling
previous_sibling

next_siblings 所有的(方向)兄弟节点 迭代器形式 
previous_siblings

感觉下面的属性不常用啊!?
previous_element
next_element
next_elements 
previous_elements

用于指向下一个或上一个将会被解析的对象(字符串或tag)
所以如果你现在在父元素, 然后你访问next_element 那么结果通常就是你的第一个子元素

# 获取子节点 #
contents 以数组的形式返回所有类型的子元素, 包括 Tag NavigableString Comment
children 和 contents 类似 不过是迭代器版本

descendants 获得子孙元素, 迭代器形式

## 属性 ##
string 如果tag只包含一个String, 那么就可以用它来获取, 比如 title
strings 如果包含多个个字符串
如下面的第一个div就包含3个字符串 ['换行1换行', '...' '换行2换行']
```
<div>
1
<div>...</div>
2
</div>
```

stripped_strings 跟strings类似 但是去掉了换行 结果是 ['1', '...', '2']


## 复杂 ##
推荐直接使用 css 选择器, 省得学一些乱七八糟的...

使用 d.xxx 的方式将会获取第一个 xxx 元素, 比如 d.title, 但这样灵活性很有限

## find_all ##
结果是一个列表
find_all('a') 只能按照tag名来找
第一个参数是
一个字符串 或 正则表达式 或 字符串列表
或 True(返回所有的**子孙节点**, 只有tag 没有其他)
或 一个函数, 这个函数接受一个tag对象, 返回True表示也要接受它
用于匹配tag名

soup.find(id="link1"))
soup.find(text=re.compile("sisters"))
soup.find_all(href=re.compile("elsie"))
data_soup.find_all(attrs={"data-foo": "value"}) data- 之类的属性要特殊对待

soup.find_all("a", class_="sister") class要特殊对待, class是多值属性, 这行代码只需要class里有包含sister就行
soup.find_all("a", attrs={"class": "sister"}) 这个要求class严格等于sister

text 参数可以是 字符串 正则表达式 列表 True
```
soup.find_all(text="Elsie")
# [u'Elsie']

soup.find_all(text=["Tillie", "Elsie", "Lacie"])
# [u'Elsie', u'Lacie', u'Tillie']

soup.find_all(text=re.compile("Dormouse"))
[u"The Dormouse's story", u"The Dormouse's story"]

def is_the_only_string_within_a_tag(s):
    ""Return True if this string is the only child of its parent tag.""
    return (s == s.parent.string)

soup.find_all(text=is_the_only_string_within_a_tag)
# [u"The Dormouse's story", u"The Dormouse's story", u'Elsie', u'Lacie', u'Tillie', u'...']
```

limit=2 限制结果最多2个
recursive=False 默认情况下搜索的是子孙 可以设置成儿子

tag 本身可以备作为函数来调用, 效果和执行 fidn_all 一样
```
soup.find_all("a")
soup("a")
```

## find ##
等价于 find_all 的 limit=1 的版本
并且结果不是列表 而是直接返回结果 如果不存在则None

## find_parent/find_parents ##
用于搜索父节点
支持的参数和 find_all() 类似

## find_xxxnext_sibling/find_next_siblings find_previous_sibling/find_previous_siblings ##

find_next_sibling/find_next_siblings 返回第一个/全部满足条件的 next兄弟节点
find_previous_sibling/find_previous_siblings

find_next/find_all_next 返回第一个/全部满足条件的 next_elements 节点
find_previous/find_all_previous 类似上面

## get ##
用于获取属性
a.get('href')

## get_text ##
获取该元素包含的所有文本


# tag的方法 #
has_attr('href') 是否有属性

# css选择器 #
select('css选择器') 返回列表
select_one() 直接返回一个对象 可能为None

# 对文档进行修改 #
1. tag对象是可以当做dict使用的, 这样修改的是它的是属性
2. tag的string属性用于修改它包含的内容, 相当于是jquery里的 html('...')
3. tag.append() 用于在tag里追加一个元素
4. BeautifulSoupn.ew_string() .new_tag() 用于创建新元素
5. insert insert_before insert_after
	1. soup.b.string.insert_before(tag) 注意 是 b.string 也就是在它的 "内容" 的前面插
6. clear
7. extract 将当前 节点 从文档树里移除并且返回
8. decompose 将当前节点移除并且完全销毁
9. replace_with
10. wrap/unwrap


# 杂 #
1. soup.prettify()
2. get_text