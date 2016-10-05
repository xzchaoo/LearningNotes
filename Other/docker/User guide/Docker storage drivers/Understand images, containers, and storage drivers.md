https://docs.docker.com/engine/userguide/storagedriver/imagesandcontainers/

Each Docker image references a list of read-only layers that represent filesystem differences. Layers are stacked on top of each other to form a base for a container’s root filesystem. The diagram below shows the Ubuntu 15.04 image comprising 4 stacked image layers.

The Docker storage driver is responsible for stacking these layers and providing a single unified view.
docker storage driver 负责将这些层叠起来, 提供一个统一的视图.

copy-on-write

容器的id是一个UUID, 而每一层也有一个id, 它是该层的一个内容哈希
当容器运行的时候, 就被分配一个UUID作为容器id
