Express 使用 path-to-regexp 匹配路由路径

# body-parser #
是一个解析body的中间件
比如解析
x-www-form-urlencoded的body

# nodemon #
监听文件的变化 自动重启
https://github.com/remy/nodemon#nodemon

## 用法 ##
npm install -g nodemon

nodemon [your node app]
用nodemon代替node原本的位置就行

配置文件 nodemon.json
```
{
  "verbose": true,
  "ignore": ["*.test.js", "fixtures/*"],
  "execMap": {
    "rb": "ruby",
    "pde": "processing --sketch={{pwd}} --run"
  }
}
```

# art-template #
npm install --save art-template
npm install --save express-art-template
app.engine('art', require('express-art-template'))
app.set('view engine', 'art')
