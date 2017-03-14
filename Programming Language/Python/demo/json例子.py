import json

# python 里的 字典 列表 和其他基本数据类型 可以和json里的类型对应起来

user = {'name': 'xzc', 'age': 22}
# 对象 -> json
s = json.dumps(user)
print(s)
# json -> 对象
user = json.loads(s)
print(user)

a = [1, 2.2, '3', user]
s = json.dumps(a)
print(s)
a = json.loads(s)
print(a)
