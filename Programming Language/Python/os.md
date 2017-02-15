os.environ 获得环境变量字典
os.getlogin() 当前登录用户
获得pid ppid

fstat
fsync
open

pipe()


os.getcwd() 获得当前工作目录

import os

# print(os.listdir('c:/')) 返回的是str

for d in os.scandir('c:/'):  # 返回的是 DirEntry
    print(dir(d))
    print(d.name)
    print(d.path)
    print(d.stat())
    print(d.inode())
    print(d.is_file())
    print(d.is_dir())
    break

# os.mkdir()
# os.makedirs()

# remove() 删除文件
# removedirs()
# renames/rename()重命名
# rmdir 目录为空才行

# truncate 截断一个文件 长度为固定值

# walk

# 将会遍历所有目录
for dirpath, dirnames, filenames in os.walk('c:/temp'):
    print(dirpath)
    print(dirnames)
    print(filenames)
    print()

