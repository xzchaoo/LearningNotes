https://developer.github.com/v3/oauth/

到 ``https://github.com/settings/applications/new`` 填写基本信息 申请一个app
得到
1. client id
2. client secret



```
GET https://github.com/login/oauth/authorize
client_id 你上面获得的client id
redirect_uri 可选 申请app时候填写的回调地址
scope 可选
state 可选
allow_signup 可选
```

用户授权之后, 会重定向到 你提供的redirect_uri?code=...&state=...
这里的state就是你刚才提供的state, 没有改变

```
POST https://github.com/login/oauth/access_token
client_id 你上面获得的client id
client_secret 你上面获得的client secret
code 收到的code
redirect_uri 要和上面用到的uri一样
state 要和上面提到的state一样 
```

默认情况下响应是
```
access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&scope=user%2Cgist&token_type=bearer
```
你可以加个头
```
Accept: application/json

然后就可以得到
{"access_token":"e72e16c7e42f292c6912e7710c838347ae178b4a", "scope":"repo,gist", "token_type":"bearer"} 的结果

xml也是支持的
Accept: application/xml
<OAuth>
  <token_type>bearer</token_type>
  <scope>repo,gist</scope>
  <access_token>e72e16c7e42f292c6912e7710c838347ae178b4a</access_token>
</OAuth>
```

# 常用的scope #
github的scope格式为 a:b 或 a
a:b是a的一个子项 如果a有了 那么 a:* 都有了

```
GET https://api.github.com/user?access_token=...
还可以将TOKEN放到头里
Authorization: token OAUTH-TOKEN
```







