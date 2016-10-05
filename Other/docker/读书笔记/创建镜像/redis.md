https://pkgs.alpinelinux.org/packages?name=redis&branch=&repo=&arch=&maintainer=

mkdir redis-server && cd redis-server

vim Dockerfile
```
FROM apline:3.3

MAINTAINER xzchaoo 70862045@qq.com

RUN apk add -no-cache redis=3.0.5-r1

CMD ["/usr/bin/redis-server"]
```

运行的时候通过-v替换掉默认配置文件
docker run -v 你的redis配置文件:/etc/redis.conf ...其他参数



apk add -no-cache redis=3.0.5-r1

通过 find . -name redis 我们可以搜索到redis的默认配置文件是放在 /etc/redis.conf

如果我们想要修改redis的配置:

ADD redis.conf /etc/redis.conf
这样就可以覆盖掉文件了!
但是这样做的话, 在build的时候就需要提供redis.conf, 不太好

因此最终考虑使用 -v 参数

docker run -v 你的redis配置文件:/etc/redis.conf ...其他参数
否则的话redis会使用默认的配置


docker run -it --link some-redis:redis --rm redis redis-cli -h redis -p 6379

docker run -it redis/3.0.7-alpine /bin/sh



