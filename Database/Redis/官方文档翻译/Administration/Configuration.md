# Configuration #
http://www.redis.io/topics/config

## Configuring Redis as a cache ##
只需要配置两个参数就行, 同时需要关闭数据的持久化 persistence
maxmemory 2mb
maxmemory-policy allkeys-lru

## 运行时动态修改配置 ##
config get port
config set port "1234"
但是某些配置不一定会生效
Not all the configuration directives are supported in this way, but most are supported as expected. Please refer to the CONFIG SET and CONFIG GET pages for more information.
