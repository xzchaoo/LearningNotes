# SpringBoot初始化流程 #

1. SpringApplication.run(App.class, args)
2. new SpringApplication(sources)
	1. 判断是否web环境
	2. 加载 ApplicationContextInitializer 实现 , 但不执行
	3. 加载 ApplicationListener 实现, 但不执行
	4. 推断 mainApplicatoinClass
3. springApplication.run(args)
	1. 加载 SpringApplicationRunListener 们, 以下简称listeners
	2. listeners.starting(), 目前spring用它发射出事件 ApplicationStartedEvent
	3. 构建 ApplicationArguments
	4. 根据需要创建 Environment, 以下简称env, 有可能已经通过Builder提供默认环境了
	5. createApplicationContext
	6. createAndRefreshContext
		1. 反射new出一个实例而已
	2. prepareContext
		1. 给ac设置环境
		2. 
		1. configureEnvironment 根据 args 配置 env
			1. 添加默认属性(通过Builder指定)到最后
			2. 添加命令行上的参数到环境最前, 也可能不是添加而是替换(可能环境已存在)
			3. 根据env构造当前活跃的profiles
		2. listeners.environmentPrepared() 目前spring用它来发射出事件ApplicationEnvironmentPreparedEvent, 这里也是 spring cloud 配置的扩展点!
		3. 如果是不是web环境, 就将env换成标准env
		4. 打印banner
		5. createApplicationContext, 实现类可以指定
		6. postProcessApplicationContext
		7. applyInitializers, 调用 2.2 加载的 ApplicationContextInitializer
		8. listeners.contextPrepared(context); 目前spring用它来 registerApplicationEventMulticaster
		9. 打印启动日志
		10. 将 applicationArguments 注册到 BeanFactory里
		11. load(所有的source)
			1. 构造 BeanDefinitionLoader 加载所有 sources
			2. 调用 AnnotatedBeanDefinitionReader 解析 source
		12. listeners.contextLoaded(context);
			1. 注册 2.3 加载的 ApplicationListener 到上下文
			2. spring用它来发射出事件 ApplicationPreparedEvent
		13. refresh(context)
		14. 注册 shutdownHook
	6. afterRefresh
		1. 调用runner
	7. listeners.finished(context, null);
		1. spring用它来发射 ApplicationFailedEvent 或 ApplicationReadyEvent

扩展点
1. ApplicationContextInitializer
	1. 
2. ApplicationListener
	1. 比如 ConfigFileApplicationListener
3. EnableAutoConfiguration
	1. 各种的自动配置类
4. TemplateAvailabilityProvider
	1. 用途不大

其他的扩展点, 有的是上面这4个扩展点再引入的二级扩展点
TODO 扩展点整理



# CronSequenceGenerator #
根据cron表达式算出下一次的执行时间
```
CronSequenceGenerator csg = new CronSequenceGenerator("*/5 * * * * ?");
for (int i = 0; i < 100; ++i) {
	System.out.println(csg.next(new Date()));
	Thread.sleep(1000);
}
```

Spring Boot 启动顺序
获取 ApplicationContextInitializer
获取 ApplicationListener

1. 获得运行监听器 SpringApplicationRunListeners
2. SpringApplicationRunListener
	1. started
3. 准备环境
	1. 如果你自己提供环境了, 那么就用它, 否则根据是否web环境, 创建出一个新的环境
	2. 添加 defaultProperties 到 last
	3. 添加到first或替 换命令行properties
	4. 激活相关的 profiles
	3. SpringApplicationRunListener.environmentPrepared 此时传入的env是可以配置的env, 这里就是 启动配置扩展点!
4. 打印banner
5. 上下文
	1. 创建 这里可以定制创建的ac的类型, 一般不需要
	2. 准备
		1. 设置环境
		2. ApplicationContextInitializer 回调
		3. SpringApplicationRunListeners.contextPrepared
		4. SpringApplicationRunListeners.contextLoaded
	3. 刷新
		1. ac.refresh();
	4. 刷新之后
		1. 串行执行 ApplicationRunner CommandLineRunner
		2. SpringApplicationRunListeners.finished 
6. SpringApplicationRunListener
	1. finished 

AbstractApplicationContext.refresh()
1. 



Spring启动顺序
1. FactoryBean
	1. getObjectType
	2. @PostConstruct
	3. afterPropertiesSet
2. 普通bean
	1. @PostConstruct
3. SmartInitializingSingleton
	1. afterSingletonsInstantiated
4. FactoryBean
	1. isSingleton
5. SmartLifecycle
	1. getPhase
	2. start
6. ContextRefreshedEvent

