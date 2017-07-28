# 介绍 #
用于一次性的任务
生命周期非常短
推荐java8

使用关系型数据库来存储执行任务的结果

@EnableTask 引入了 SimpleTaskConfiguration 配置
配置了一个 TaskRepository

TaskLifecyceListener  会记录任务的启动和结束到仓库里去

一个任务的生命周期被认为是
从 所有runner执行 开始
到 所有runner执行 结束

runner执行是串行的

通过 SmartLifecycle#start 方法在数据库里创建一条记录 表示任务开始 (当然了整个 ApplicationContext必须要启动成功, 否则是不会进入到这一步的)
此时所有的bean都是可用的
这个方法是在 XxxRunner#run 之前执行的

当所有Runner的run执行完之后, 就会更新数据库中的记录表示执行结束

默认任务执行结束之后就会关闭上下文
可以通过 spring.cloud.task.closecontext_enable=false 来禁止

# SimpleTaskConfiguration #

# TaskConfigurer #
用户可以使用这个接口对任务作出一些定制
默认是用 DefaultTaskConfigurer 

需要配置
TaskRepository 用于存储任务数据
TaskExplorer 用于查询任务情况
PlatformTransactionManager 数据库事务


任务名
默认情况下任务名就是spring boot程序的名字
默认可以通过 spring.cloud.task.name 修改

具体参考 TaskNameResolver

# TaskExecutionListener #
就是监听器

除了使用接口 还可以使用注解
@BeforeTask
@AfterTask
@FailedTask

# TaskRepository  #

# TaskProperties #
可以配置
任务表前缀

执行id, 貌似执行实体是执行开始之后才会生成的
因此 执行请求 和 真正开始执行的时候可能会有时差
用户可以先手动创建好 执行实体, 然后将实行的id传递过来
这样任务就不会再去创建执行了, 而是使用已有的执行

外部执行id:由外部提供的id 

是否自动初始化表
spring.cloud.task.initialize.enable=<true or false>

父执行
可以存储父执行的id


# TaskExecution #
long 执行id
Integer 退出码
任务名
启动时间
结束时间
退出消息
错误信息
参数
externalExecutionId
父执行id

## 退出状态码 ##
JVM正常结束会返回0
如果抛出异常 则会返回非0
但是平时我们并不好控制这个返回值

SCT提供了 ExitCodeExceptionMapper 用于映射异常为状态码
在任务结束之前 exitCode 一直都是null
 
