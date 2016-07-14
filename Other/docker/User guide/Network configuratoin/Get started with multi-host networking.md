创建网络的时候可以使用 --subnet 来指定网段

由于没有足够的设备就不学习 overlay 了

手动连接或断开网络 connect disconnect


自己主动提供 alias
docker run --net=isolated_nw -itd --name=container6 --net-alias app busybox

这样在同一个网络(isolated_nw)的其他机器, 就可以不用指定 --link, 也可以ping得通 app->对应的ip

同一个网络中的多个容器可以有相同的alias, 但是只有第一台的ip会被解析到, 当第一台stop了以后, 第二台就会被解析.
