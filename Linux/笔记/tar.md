tar -zxf b.tar.gz
tar -zcf a/

-z 使用gzip
-x 解压
-c 压缩
-f 指定文件

因为-f需要指定文件 因此它要放在最后, 比如 -zxf

-C 用于解压到特定目录
