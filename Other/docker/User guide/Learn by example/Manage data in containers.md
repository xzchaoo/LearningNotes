# keyword #
images network link containers "Data volumes" "Data volume containers"

# Data volumes #
简称dv
一个dv是一个特殊分配的目录, 一个或多个的容器可以使用它.
特性:
1. dv可以在多个容器之间被共享和复用
2. 可以直接修改dv里的文件, 其他的所有地方都会受到影响
3. 容器关闭之后, dv依然可以持久化
4. 当你应用打包的时候, dv并不会跟着被打包
5. changes to a data volume will not be included when you update an image.

dv被设计用于持久化数据, 独立于容器的生命周期.
Docker 因此 从不!!! 自动删除 volume, 当一个容器关闭的时候.
即使这个volume已经不在被任何容器引用了.

# 添加DV #
docker run -d -P --name web -v /webapp training/webapp python app.py
-v 参数可以用多次
这会在容器里创建一个目录 /webapp 作为DV
Dockerfile里也可以有VOLUME指令

通过 docker inspect web , 你就可以知道这个DV保存在你硬盘的哪个位置
```
...
"Mounts": [
    {
        "Name": "fac362...80535",
        "Source": "/var/lib/docker/volumes/fac362...80535/_data",
        "Destination": "/webapp",
        "Driver": "local",
        "Mode": "",
        "RW": true,
        "Propagation": ""
    }
]
...
```

# 显式指定DV的保存位置 #
$ docker run -d -P --name web -v /src/webapp:/opt/webapp training/webapp python app.py
使用 "-v 主机的目录:容器的目录" 的格式
如果容器的 /opt/webapp 本身就是已经存在了的话, 那么 这个目录将会被暂时完全用/src/webapp的内容替换掉
但实际上它的内容没有删除, 一旦你取消挂载 /src/webapp 它的内容又会出现
容器必须要使用绝对路径

>>
The container-dir must always be an absolute path such as /src/docs. The host-dir can either be an absolute path or a name value. If you supply an absolute path for the host-dir, Docker bind-mounts to the path you specify. If you supply a name, Docker creates a named volume by that name.
A name value must start with an alphanumeric character, followed by a-z0-9, _ (underscore), . (period) or - (hyphen). An absolute path starts with a / (forward slash).
For example, you can specify either /foo or foo for a host-dir value. If you supply the /foo value, Engine creates a bind-mount. If you supply the foo specification, Engine creates a named volume.

设置成只读模式docker run -d -P --name web -v /src/webapp:/opt/webapp:ro training/webapp python app.py
docker run -d -P --name web -v /src/webapp:/opt/webapp:ro training/webapp python app.py

>>The host directory is, by its nature, host-dependent. For this reason, you can’t mount a host directory from Dockerfile because built images should be portable. A host directory wouldn’t be available on all potential hosts.

你不能在 DockerFile 里使用 VOLUME 映射一个主机的文件路径, 因为他们是平台相关的

# Mount a shared-storage volume as a data volume #
需要使用第三方的插件做一个共享的存储系统
```
$ docker volume create -d flocker --name my-named-volume -o size=20GB
$ docker run -d -P \
  -v my-named-volume:/opt/webapp \
  --name web training/webapp python app.py
```

通过下面的语句 找出没有被任何容器引用的volume
docker volume ls -f dangling=true

移除一个volume
docker volume rm <volume name>

# Creating and mounting a data volume container #
似乎是创建一个container 用来保存数据, 但是这个container本身并不需要启动
$ docker create -v /dbdata --name dbstore training/postgres /bin/true

docker run -d --volumes-from dbstore --name db1 training/postgres
docker run -d --volumes-from dbstore --name db2 training/postgres
然后容器 db1 和 db2 上就会有一个目录 /dbdata(这个目录是在创建容器 dbstore 时 -v参数 指定的)
--volumes-from 可以指定多次

备份:
将 容器dbstore 上的 /dbdata目录备份到主机的当前目录下的backup.tar
docker run --rm --volumes-from dbstore -v $(pwd):/backup ubuntu tar cvf /backup/backup.tar /dbdata
--rm 容器运行结束时自动移除
---volumes-from dbstore 指定包含数据的容器

