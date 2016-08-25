隔离
内置的网络和用户自定义的网络

docker network ls

docker run --net=<NETWORK>

名为bridge的网络对应了主机上的docker0虚拟网卡

名none的网络表示不启动网络功能, 容器里只有localhost地址是可用的

使用 CTRL-p , CTRL-q 可以 detach 一个容器, 回到你的主机的控制台, 但依旧保持着容器是运行状态, 这个 Ctrl + D 是不一样的哦

名为host的网络表示要和主机共享一个网络栈(相当于容器的网络就是直接使用的主机的网络了)
容器里的ip地址甚至和主机的ip地址是完全一样的

这些默认的网络不能被移除
docker network inspect NETWORK 可以查看一个网络的具体配置信息

容器之间不要依赖于IP, 而要依赖名字, 因为IP容易变化.

docker提供了一些默认的网络驱动: bridge overlay
You can also create a network plugin or remote network written to your own specifications.

Docker’s overlay network driver supports multi-host networking natively out-of-the-box. This support is accomplished with the help of libnetwork, a built-in VXLAN-based overlay network driver, and Docker’s libkv library.

