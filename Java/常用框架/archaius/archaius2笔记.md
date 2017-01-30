# 常用的类 #

## Config ##
用于描述单个配置组
可以添加监听器

getXXX 方法
如果不提供默认值, 那么当值不存在的时候会抛异常 NoSuchElementException
解析失败会 ParseException

Config prefix = config.getPrefixedView("foo");

## MapConfig ##
用于构造基于Map类型的配置, 比如 Map 或 Properties
主要使用其静态构造方法


CompositeConfig 接口
用于复合多个Config

DefaultCompositeConfig
默认实现类

```
CompositeConfig cfg = DefaultCompositeConfig.builder()
	.withConfig("lib", lib)
	.withConfig("app", app)
	.withConfig("props", MapConfig.from(props))
	.build();
```

DefaultConfigLoader

```
DefaultConfigLoader loader = DefaultConfigLoader.builder()
	.withStrLookup(cfg)
	.withDefaultCascadingStrategy(ConcatCascadeStrategy.from("${env}"))
	.build();
```

SettableConfig


# 定期读取配置 #
URLConfigReader
PollingStrategy
PollingDynamicConfig

# 杂 #
com.sun.net.httpserver.HttpServer
仅仅在jvm内部可用
用于测试






# 容器模式 #
        DefaultSettableConfig dyn = new DefaultSettableConfig();

        CompositeConfig config = new DefaultCompositeConfig();
        
        config.addConfig("dyn", dyn);
        config.addConfig("map", (MapConfig.builder()
                        .put("env",    "prod")
                        .put("region", "us-east")
                        .put("c",      123)
                        .build()));
        
        
        PropertyFactory factory = DefaultPropertyFactory.from(config);
        
        Property<String> prop = factory.getProperty("abc").asString("defaultValue");
        
        prop.addListener(new DefaultPropertyListener<String>() {
            @Override
            public void onChange(String next) {
                System.out.println("Configuration changed : " + next);
            }
        });
        
        dyn.setProperty("abc", "${c}");
# 代理模式 #
https://github.com/Netflix/archaius/blob/2.x/archaius2-core/src/test/java/com/netflix/archaius/ProxyFactoryTest.java
