https://github.com/atomx/nginx-http-auth-digest

```
auth_digest_user_file /opt/httpd/conf/passwd.digest;
location /private {
	auth_digest 'xzchaoo'; # set the realm for this location block
}
```
密码文件用htdigest.py工具生成
https://github.com/samizdatco/nginx-http-auth-digest/blob/master/htdigest.py

