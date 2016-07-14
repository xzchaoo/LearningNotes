有一个特殊的镜像 scratch, 它会告诉docker, 你的下一行指令就将决定 第一层的文件系统

FROM scratch
ADD hello /
CMD /hello


