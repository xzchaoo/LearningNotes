resp = request.get('地址',params={},headers={},cookies={})
resp.text
resp.content
resp.json()
resp.close()


import requests
import time

#http://cn.python-requests.org/zh_CN/latest/

# 是否跟进重订向
# cookie headers
# 超时

# post请求的话就携带 data=dict对象 这样是发送一个表单post请求
# 也可以使用json=dict对象 发送application/json!
# 上传文件目前不太需要, 有需要的话去看官方文档
res = requests.get('http://202.120.17.158:8080/api/video', headers={}, cookies={})
print(res.status_code)

print(res.headers['Content-Type'])
print(res.headers['content-type'])
print(res.encoding)
# 二进制数据 res.content
print(res.text)
print(res.content.decode('utf-8'))
print(res.json())

"""

payload = {'key1': 'value1', 'key2': ['value2', 'value3']}
r = requests.get('http://httpbin.org/get', params=payload)

r = requests.get('https://github.com/timeline.json', stream=True)
r.raw.read(10)
with open(filename, 'wb') as fd:
    for chunk in r.iter_content(chunk_size):
        fd.write(chunk)

"""

beg = time.time()
res = requests.get('http://api.bilibili.com/x/v2/reply?type=1&oid=4569&nohot=1&pn=1&ps=1', stream=False)
j = res.json()
end = time.time()
print(int((end - beg) * 1000))
res.close()

beg = time.time()
res = requests.get('http://api.bilibili.com/x/v2/reply?type=1&oid=4569&nohot=1&pn=1&ps=1', stream=False)
j = res.json()
end = time.time()
print(int((end - beg) * 1000))
res.close()

r = requests.Request('GET', 'http://api.bilibili.com/x/v2/reply?type=1&oid=4569&nohot=1&pn=1&ps=1',
                     cookies={}).prepare()
s = requests.session()
beg = time.time()
res = s.send(r)
j = res.json()
end = time.time()
res.close()
print(int((end - beg) * 1000))

beg = time.time()
res = s.send(r)
j = res.json()
end = time.time()
res.close()
print(int((end - beg) * 1000))

