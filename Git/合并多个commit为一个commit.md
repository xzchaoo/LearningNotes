通常开发新功能的时候会从 release checkout 一个新的分支, 然后在新的分支上做开发
新的分支上会有非常频繁的commit, 意味着会有很多的commit日志
等功能开发完了之后, 要将分支代码合并到release上, 如果直接 git merge ...
那么所有的commit日志都会被合并过去, 导致你在release上执行 git log  ... 看到的是你的非常琐碎的commit

http://blog.csdn.net/rockrockwu/article/details/33740711
这里提到了2个解决方案

有一个缺点: 对应的 commit message 都丢失了
或许rebase可以解决这个问题

