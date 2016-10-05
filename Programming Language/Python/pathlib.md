import pathlib

path = pathlib.Path('./../test/2.txt')
print(path.is_file())
print(path.is_dir())
print(path.exists())
print(path.stat())
print(path.absolute())
print(path.is_absolute())


一次性读写文本
print(path.read_text())
write_text()

print(path.resolve())  # 解析成最终的path 进行规范化
# print(path.drive)
# print(path.parent)
# print(path.parents)
print(path.resolve().as_uri())
print(path.resolve().match('C:/Users/Administrator/PycharmProjects/*/*.txt'))

cwd()
home()
expanduser()

Path.glob(pattern)

open()
mkdir
rmdir
touch()
