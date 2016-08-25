docker 只能支持一个 storage driver
需要对 docker deamon 设置你所使用的 driver

```
Technology	Storage driver name
OverlayFS	overlay
AUFS	aufs
Btrfs	btrfs
Device Mapper	devicemapper
VFS*	vfs
ZFS	zfs
```

通过 docker info, 可以发现:
Storage Driver: aufs


有的 driver 可能和主机的文件系统(/var/lib/docker目录所在的文件系统)不兼容
You can set the storage driver by passing the --storage-driver=<name> option to the docker daemon command line, or by setting the option on the DOCKER_OPTS line in the /etc/default/docker file.

Several factors influence the selection of a storage driver. However, these two facts must be kept in mind:

No single driver is well suited to every use-case
Storage drivers are improving and evolving all of the time

