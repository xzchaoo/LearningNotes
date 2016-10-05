# text索引 #

db.stores.createIndex( { name: "text", description: "text" } )

name属性是一个字符串或字符串数组

一个集合只能有一个文本索引
但这个文本索引可以包含多个字段

# $text #

$text会进行分词, 然后在搜索 java 或 coffee 或 shop
db.stores.find( { $text: { $search: "java coffee shop" } } )
如果是精确的词组, 需要用双引号
db.stores.find( { $text: { $search: "java \"coffee shop\"" } } )
这样就是搜索 java 和  "coffee shop"

你会发现, find的时候, 不需要提供字段
因为一个集合最多只有一个文本索引, 因此 $text 作用于改文本索引包含的所有字段上


如果单词开头是- 那么表示排除
比如 -java

# 得分 #
```
db.stores.find(
   { $text: { $search: "java coffee shop" } },
   { score: { $meta: "textScore" } }
).sort( { score: { $meta: "textScore" } } )
```

# 多语言支持 #
https://docs.mongodb.com/manual/reference/text-search-languages/
```
db.articles.aggregate(
   [
     { $match: { $text: { $search: "saber -claro", $language: "es" } } },
     { $group: { _id: null, views: { $sum: "$views" } } }
   ]
)

db.quotes.createIndex(
   { content : "text" },
   { default_language: "spanish" }
)
```
default设置了默认的语言, 分词的时候有用

在text字段的同级放一个language字段, 用于指示该text字段的语言类型
这里有个例子
https://docs.mongodb.com/manual/tutorial/specify-language-for-text-index/

如果不喜欢这个字段就叫做language, 而是要用特定的名字, 可以用下面的方式
```
db.quotes.createIndex( { quote : "text" },
                       { language_override: "idioma" } )
```
这样就会用 idioma 字段来判别文本属性的语言了




# 用于管道时 #
```
db.articles.aggregate(
   [
     { $match: { $text: { $search: "cake" } } },
     { $group: { _id: null, views: { $sum: "$views" } } }
   ]
)
```
```
db.articles.aggregate(
   [
     { $match: { $text: { $search: "cake tea" } } },
     { $sort: { score: { $meta: "textScore" } } },
     { $project: { title: 1, _id: 0 } }
   ]
)
```
```
db.articles.aggregate(
   [
     { $match: { $text: { $search: "cake tea" } } },
     { $project: { title: 1, _id: 0, score: { $meta: "textScore" } } },
     { $match: { score: { $gt: 1.0 } } }
   ]
)
```
