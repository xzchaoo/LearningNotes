import pathlib
import os.path

print(os.path.abspath('.'))  # 专程绝对路径
print(os.path.basename('c:/1/2.txt.jpg'))  # 获取文件名
print(os.path.basename('c:/1/2/'))  # 获取文件名 这里会获取到空!
print(os.path.commonpath(['d:/1/2.txt.jpg', 'd:/1/2.3.txt']))  # 找出公共前缀 不能将绝对和相对路径混合 返回值是平台规范化
print(os.path.commonprefix(['d:/1/2.txt.jpg', 'd:/1/2.3.txt']))  # 找出公共前缀 不能将绝对和相对路径混合 返回值是根据字符串里决定

print(os.path.dirname('c:/1/2/'))  # 目录名
print(os.path.exists('c:/'))  # 是否存在

print(os.path.expanduser('~'))  # 可以解析~

print(os.path.getsize('c:/1.txt'))
print(os.path.join('c:', 'a', 'b'))

print(os.path.normpath('./a/b/c/../..'))  # 结果是 ./a

print(os.path.split('c:/1/2/3.txt'))  # 拆成(目录,文件名)
print(os.path.splitext('c:/1/2/3.txt'))  # 拆成(除了扩展名外的部分,扩展名)

# print(os.path.getsize('c:/2.txt')) 文件不存在则抛异常
# isabs 是否绝对
# isfile 是否文件
# isdir 是否目录
# islink
# ismount

# getatime getmtime getctime 获取3种时间
