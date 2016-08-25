1. 快速容器启动
2. 存储效率高
3. 使用内存少

但是某些linux不支持AUFS
AUFS is a unification filesystem. This means that it takes multiple directories on a single Linux host, stacks them on top of each other, and provides a single unified view. To achieve this, AUFS uses a union mount.

它可以井多个目录整合到一起, 一层叠着一层, 提供一个统一的view
但是这些目录需要在同一台 linux 主机上

AUFS的 copy-on-write 是文件级别的, 如果你修改了一个非常大的文件的一小部分, 那么这个很大的文件就要被复制到 读写层.
还好, 对于同一个容器的同一个文件 copy 动作只会发生一次
个人感觉应该有一种 copy-on-write 可以做到按"页"来 CoW 吧?

当你删除一个文件的时候, AUFS 会在  Container top layer(也就是读写层吧) 创建一个特殊的占位文件(whiteout file)
用它来作为标记说该文件已经被删除了, 否则当你删除了这个文件之后, 可能又要到上游层去找...

1. 当你使用PaaS 或其他 容器密度很大的场景的时候, AUFS就是不错的选择, 因为它可以高效地共享镜像, 容器懂速度也非常快, 内存和磁盘使用量也小.
2. AUFS底层 在镜像和容器之间共享文件的机制非常高效地使用了 系统页面花奴才能.

会带来比较显著的写延迟, 因为第一次写一个文件的时候, 很有可能会发生copy-on-write
数据卷可以提供接近本地磁盘IO的性能, 因为它绕过了 storage driver机制.

Because Btrfs 是在 文件系统 级别上工作的, 你可以到
形如 /var/lib/docker/btrfs/subvolumes/0a17decee4139b0de68478f149cc16346f5e711c5ae3bb969895f22dd6723751/ 的目录下
你就会发现一整套的:
```
drwxr-xr-x 1 root root 1372 Oct  9 08:39 bin
drwxr-xr-x 1 root root    0 Apr 10  2014 boot
drwxr-xr-x 1 root root  882 Oct  9 08:38 dev
drwxr-xr-x 1 root root 2040 Oct 12 17:27 etc
drwxr-xr-x 1 root root    0 Apr 10  2014 home
```
但他们实际上并不会占用磁盘的大小(或者说占用很少, 因为他们只是某一个文件的一个镜像而已)

并不会因为有很多层, 而导致在寻找一个文件的真实内容的时候就要跨很多层找
虽然一个 snapshot 可能是另外一个 snapshot 的 快照(似乎也叫snapshot) 但是前者拥有的内容和后者是完全一样的
前者可以直接引用到底层的文件, 而不用通过后者, 由后者去访问.

A container is a space-efficient snapshot of an image. Metadata in the snapshot points to the actual data blocks in the storage pool. This is the same as with a subvolume. Therefore, reads performed against a snapshot are essentially the same as reads performed against a subvolume. As a result, no performance overhead is incurred from the Btrfs driver.

它的 copy-on-write 跟AUFS理解起来也差不多.


With Btfs, writing and updating lots of small files can result in slow performance. More on this later.
不擅长写和更新大量的小文件

