1. 使用一个 .dockerignore, 它的作用和语法和.gitignore一样
2. 避免安装无关的包, 比如mysql镜像里不包含vim
3. 每个容器只运行一个进程
4. 最小化层数
5. 参数多行化, 并且排序
6. build cache, 可以取消掉, 用 --no-cache
	1. 一开始, 检查你的FROM版本, 你的FROM版本肯定会在缓存里(如果没有也会被下载下来)
	2. 然后检查你的下一个指令(比如ADD,COPY等)
	3. 对于 ADD 和 COPY 指令, 相应的文件的 checksum 会被检查, 最后修改日期也会被检查

.dockerignore
```
*/temp*
*/*/temp*
temp?
```

FROM
MAINTAINER
RUN
CMD
LABEL 用于给该镜像增加元数据
EXPOSE
ENV
ADD
COPY
ENTRYPOINT
CMD
VOLUME
USER
WORKDIR 工作目录
ONBUILD
STOPSIGNAL
ARG key=defaultValue
ARG key
它可以达到的效果跟ENV基本差不多

1. FROM busybox
2. USER ${user:-some_user}
3. ARG user
4. USER $user
5. 
第二行的结果是 some_user 因为user是在第三行才定义的
你需要使用 --build-arg <varname>=<value> 在 build 的时候传入参数

docker build --build-arg CONT_IMG_VER=v2.0.1 Dockerfile









通常会写一个启动脚本
ENTRYPOINT ["/start.sh"]
CMD ["-h"]

```
#!/bin/bash
set -e

if [ "$1" = 'start' ]; then
	执行命令 start
elif [ "$1" = '-h' ]; then
	打印帮助信息
fi

exec "$@"
```

COPY hom* /mydir/
COPY hom?.txt /mydir/


可以一行多个额数或多个LABEL
LABEL "com.example.vendor"="ACME Incorporated"
LABEL com.example.label-with-value="foo"
LABEL version="1.0"
LABEL description="This text illustrates that label-values can span multiple lines."
LABEL multi.label1="value1" multi.label2="value2" other="value3"

