http://developer.baidu.com/wiki/index.php?title=docs/oauth#.E6.8E.88.E6.9D.83.E6.9D.83.E9.99.90.E5.88.97.E8.A1.A8

到 http://developer.baidu.com/ 注册账号 并申请应用
获得
client_id=API Key
client_secret=Secret Key
点击安全配置设置毁掉地址

# 服务端进行认证 #
让用户访问:
```
http://openapi.baidu.com/oauth/2.0/authorize?
response_type=code 固定的
client_id=你的api key
redirect_uri=http://t.xzchaoo.com/callback
scope=basic 根据需要的权限
display=popup 这个是百度特殊定制的
```

当用户点击了授权之后, 就会重订向用户到:
http://t.xzchaoo.com/callback?code=CODE
带过来一个code, code有一定时限, 并且只能被使用一次

接着服务端发起一个http get请求
```
GET https://openapi.baidu.com/oauth/2.0/token
grant_type=authorization_code 固定
code=CODE 你刚才获得的code
client_id=你的api key
client_secret=你的api secret
redirect_uri=http://t.xzchaoo.com/callback
```

结果形如:
```
status code=200
{
	"expires_in": 2592000,
	"refresh_token": "22.1357ab5c71f08ff36b1afbaed8bbacde.315360000.1805102292.2114079514-9411929",
	"access_token": "21.5940de0ca5a376241e16f923d036a70f.2592000.1492334292.2114079514-9411929",
	"session_secret": "70e70b2076f3253c531e2fc0831b0488",
	"session_key": "9mnRcamX6Z3E11jg9iDE0od6UjB9Isx6/9uWZiZCC5nXpQA79P5rpJ5zPHlkIeOLAU3ca++LxsBAZMYKSwr324M+dkYgh2ru7A==",
	"scope": "basic"
}
```

```
status code=400
{"error":"invalid_grant","error_description":"invalid code , expired or revoked"}
```


# 错误码 #
http://developer.baidu.com/wiki/index.php?title=docs/oauth/error
http://developer.baidu.com/wiki/index.php?title=%E7%99%BE%E5%BA%A6Open_API%E9%94%99%E8%AF%AF%E7%A0%81%E5%AE%9A%E4%B9%89

# 开放API接口 #
http://developer.baidu.com/wiki/index.php?title=docs/oauth/rest/file_data_apis_list

大多数API都支持GET和POST, REST服务端支持GZIP压缩
所有字段应该进行url encode

通过HTTPS协议发送请求的时候只需要携带 access_token 和 callback 和其他的的参数就行

```
GET https://openapi.baidu.com/rest/2.0/passport/users/getInfo?access_token=1.a6b7dbd428f731035f771b8d15063f61.86400.1292922000-2346678-124328
```
