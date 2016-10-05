# string模块 #
ascii_letters 大小写英文字母
ascii_lowercase 小写英文字母
ascii_uppercase 大写英文字母

digits='0123456789'
hexdigits='0123456789abcdefABCDEF'


# Template  #
t = string.Template('$a+$b=${c}')
print(t.substitute({'a': 1, 'b': 2, 'c': 3}))