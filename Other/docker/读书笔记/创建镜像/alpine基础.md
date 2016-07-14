alpine是一个非常非常小的Linux, 只有个位数的MB
而ubuntu好像有50MB左右吧

而且很多镜像都提供了alpine版本, 比如 java:8 大小是200多MB, 而 java:8-alpine 大小只有50MB
不知道这样做, java本身有没有精简掉很多功能?

不过里面连 apt apt-get 都没有
想要安装软件的话可能需要额外的教程了

从 https://github.com/docker-library/openjdk/blob/b734af2a8ee4697604035d14064fb7f3b1d5f050/8-jdk/alpine/Dockerfile 可以看到:
```
RUN set -x \
	&& apk add --no-cache \
		openjdk8="$JAVA_ALPINE_VERSION" \
	&& [ "$JAVA_HOME" = "$(docker-java-home)" ]
```
实际上通过 apk 命令(alpine才有) 可以添加软件
apk add --no-cache openjdk8="8.77.03-r0"

