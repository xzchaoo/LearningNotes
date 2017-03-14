1. 创建一种类型的ac, 比如xml 或 annotation
2. 针对这种类型的ac进行初始化
	1. xml的需要提供文件
	2. annotation的需要提供配置类
3. 调用refresh方法
	1. 这个方法有可能在构造函数里已经调用了
	2. 这个方法只能被调用一次


# refresh #
1. prepareRefresh
	1. 主要负责初始化 PropertySources 和 Environment
	2. 创建一个数组用于记录早期的app事件, 因为此时app没完全初始化
2. 初始化Bean工厂
3. prepareBeanFactory
	1. 设置 ClassLoader
	2. 调整注册bean的顺序
	3. 注册一些spring级别的bean
4. postProcessBeanFactory 一个回调 目前没用
5. invokeBeanFactoryPostProcessors
6. registerBeanPostProcessors
7. onRefresh 回调子类

# Bean生命周期 #
@PostContruct
LifeCycle.start
SmartLifeCycle.start
SmartLifeCycle.stop
LifeCycle.stop
@Predestroy

LifeCycle 的 start 和 stop 需要手动调用 ac 的 start/stop 才能触发
而 SmartLifeCycle 则没有这个限制

