# 学习url相关操作
# 构造一个url

from urllib.parse import *

# urlencode
print(urlencode([('a', 1), ('b', '2'), ('c', '1@3')]))
# 结果是 'a=1&b=2&c=1%403'

pr = urlparse('http://www.abc.com/index/ceshi?a=1&b=2&c=1%403')
print(pr.scheme)
print(pr.hostname)
print(pr.path)
print(pr.query)

print(urljoin('http://www.abc.com/1/2/3/4.jpg', '5.jpg'))

#传入的参数一定要是一个queryString 不包括其他部分
print(parse_qs('a=1&b=2&c=1%403'))

print(parse_qsl('a=1&b=2&c=1%403'))

print(quote('1@3'))  # 1%403
print(quote('1%403'))  # 1@3

