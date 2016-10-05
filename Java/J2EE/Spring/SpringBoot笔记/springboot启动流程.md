# SpringBoot初始化流程 #

1. SpringApplication.run(App.class, args)
2. new SpringApplication(sources)
	1. 判断是否web环境
	2. 加载 ApplicationContextInitializer 实现 , 但不执行
	3. 加载 ApplicationListener 实现, 但不执行
	4. 推断 mainApplicatoinClass
3. springApplication.run(args)
	1. 加载 SpringApplicationRunListener 们, 以下简称listeners
	2. listeners.started(), 目前spring用它发射出事件 ApplicationStartedEvent
	3. 构建 ApplicationArguments
	4. 根据需要创建 Environment, 以下简称env, 有可能已经通过Builder提供默认环境了
	5. createAndRefreshContext
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

