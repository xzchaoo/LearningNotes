# itertools #

count() 产生一个可以迭代的序列 可以指定step 可以用于做原子计数器 但没有get方法, 只能next得时候有返回值
cycle(某个数组) 循环
repeat(ele,n) 重复ele n次

chain(i1,i2) 串起来

accumulate()

# 顶级函数 #
map
```
print(list(map(len,['a','bb','ccc'])))
```

filter 返回 True 之类的值就保留

enumerate
```
for i, v in enumerate(['a', 'b', 'c']):
    print('[%d]=%s' % (i, v))
```

zip
```
for a, b in zip([1, 2, 3], ['a', 'b', 'c']):
    print(a, b)
```
