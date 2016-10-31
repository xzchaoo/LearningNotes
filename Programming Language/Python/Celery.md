http://www.celeryproject.org/

Eventlet


# 简介 #
Celery 是一个基于分布式消息的异步任务队列

redis://:70862045@127.0.0.1:6379/0

如果出错不抛出异常
result.get(propagate=False)

异常信息在这里
result.traceback


# 选择broker #
## redis ##
http://docs.celeryproject.org/en/latest/getting-started/brokers/redis.html#broker-redis
pip install -U celery[redis]
redis://:password@hostname:port/db_number

BROKER_URL = 'redis://localhost:6379/0'
CELERY_RESULT_BACKEND = 'redis://localhost:6379/0'



# worker #
celery worker 其他参数...
## 参数 ##
-A 指定 app
-l 日志级别 info 之类
-c/--concurrency = 10 并发数量(这里指的是进程数量) 默认是CPU核数, 没有推荐值 要根据你的场景调整 比如你是IO密集的 那么就高一点
-E 发送事件 这样可以被监视器捕获到
-Q QUEUES 指定该worker可以从哪些对列消费任务

-b 指定 broker
-q quite
--version


## 自动扩展 ##
--autoscale=10,3 最多启动 10个进程 最少保持3个进程
CELERYD_AUTOSCALER
```
--autoscale=AUTOSCALE
     Enable autoscaling by providing
     max_concurrency,min_concurrency.  Example:
       --autoscale=10,3 (always keep 3 processes, but grow to
      10 if necessary).
```

## 消费的对列 ##
默认情况下 worker 会消费所有由 CELERY_QUEUES 指定的queue
指定 -Q foo,bar 可以添加额外消费的queue(默认的queue依旧会消费)
如果 CELERY_QUEUES 不指定 那么默认会从 "celery" 这个queue进行消费\

http://docs.celeryproject.org/en/latest/userguide/workers.html 这里有详细的代码用于修改哪个worker消费哪个queue

下面的代码可以动态让一个worker消费一个queue
```
celery -A proj control add_consumer foo
```

当然也会有删除了
```
celery -A proj control cancel_consumer foo
```

列出活动的queue
```
celery -A proj inspect active_queues
```

可以使用-d 或 --destination 指定一个worker的名字 由它来处理该请求

自动重新导入
--autoreload 会观察文件系统的变化 然后重新导入模块 开发阶段使用

状态检查
```
# Inspect all nodes.
>>> i = app.control.inspect()

# Specify multiple nodes to inspect.
>>> i = app.control.inspect(['worker1.example.com',
                            'worker2.example.com'])

# Specify a single node to inspect.
>>> i = app.control.inspect('worker1.example.com')

已经注册的任务
>>> i.registered()
[{'worker1.example.com': ['tasks.add',
                          'tasks.sleeptask']}]
当前执行的任务
>>> i.active()
[{'worker1.example.com':
    [{'name': 'tasks.sleeptask',
      'id': '32666e9b-809c-41fa-8e93-5ae0c80afbbf',
      'args': '(8,)',
      'kwargs': '{}'}]}]

等待执行的任务
>>> i.scheduled()
[{'worker1.example.com':
    [{'eta': '2010-06-07 09:07:52', 'priority': 0,
      'request': {
        'name': 'tasks.sleeptask',
        'id': '1a7980ea-8b19-413e-91d2-0b74f3844c4d',
        'args': '[1]',
        'kwargs': '{}'}},
     {'eta': '2010-06-07 09:07:53', 'priority': 0,
      'request': {
        'name': 'tasks.sleeptask',
        'id': '49661b9a-aa22-4120-94b7-9ee8031d219d',
        'args': '[2]',
        'kwargs': '{}'}}]}]

查看废弃的任务

```

## 普通 ##
celery worker -A tasks -l info -c 4 -n sh-worker1
celery -A proj worker --loglevel=INFO -n worker1.%h

```
%h: Hostname including domain name.
%n: Hostname only.
%d: Domain name only.

worker1.%h -> worker1.george.example.com
worker1.%n -> worker1.george
worker1.%d -> worker1.example.com

```

ps auxww | grep 'celery worker' | awk '{print $2}' | xargs kill -9

停止
Ctrl + C


## 后台模式 ##
http://docs.celeryproject.org/en/latest/tutorials/daemonizing.html#daemonizing

默认会在当前目录下创建pid文件和日志文件, 为了防止冲突可能需要手动指定
--pidfile=/var/run/celery/worker1.pid
--logfile=/var/log/celery/worker1.log

启动
celery multi start worker1 -A proj -l info -c 4 --pidfile=/var/run/celery/worker1.pid
重启
celery multi restart worker1 -A proj -l info

停止
celery multi stop w1 -A proj -l info -c 4 这个是异步停止 
celery multi stopwait w1 -A proj -l info 这个是同步停止

启动
celery multi start 1 -A proj -l info -c4 --pidfile=/var/run/celery/%n.pid

重启
celery multi restart 1 --pidfile=/var/run/celery/%n.pid


参数要全部带上 否则不能正确标识...

# 调用任务 #
add.delay(2, 2) 是 add.apply_async((2, 2)) 的简写

10秒后执行 将该任务放到队列 lopri 里(只有那些会消费 lopri 对列的worker才会收到该任务 )
add.apply_async((2, 2), queue='lopri', countdown=10)

返回值是一个 AsyncResult

# AsyncResult #
r = add.delay(2,2)
r.id
r.ready() 是否执行完毕 不管成功或失败
r.get() 无限阻塞
r.get(timeout=1,propagate=False) 超时1秒 不传播异常
r.failed() 是否失败
res.successful() 是否成功
res.state 状态 PENDING -> STARTED -> SUCCESS 需要启动记录状态的选项才会有STARTED状态
> The started state is a special state that is only recorded if the CELERY_TRACK_STARTED setting is enabled, or if the @task(track_started=True) option is set for the task.

一个可能的状态改变顺序
> PENDING -> STARTED -> RETRY -> STARTED -> RETRY -> STARTED -> SUCCESS

# 建议的目录结构 #
```
proj/__init__.py 在这里导入 你创建的 Celery 实例
    /celery.py 在这里初始化你的 Celery 实例(假设叫做app)
    /tasks.py 任务放到这里, 这里的任务用 @app.task 修饰 要保证这个模块被 celery 引用到, 比如 include 初始化参数
```


# Subtask #
s()
si()

# 调用 #
## group ##
当让一系列任务并发执行

	print(group(add.s(1, i) for i in range(10))().get()) 返回一个数组 对应了每个任务的执行结果
	
	>>> g = group(add.s(i) for i in xrange(10))
	>>> g(10).get()
	[10, 11, 12, 13, 14, 15, 16, 17, 18, 19]


## chain ##
串联任务
si 表示这个任务不会被改变 因此上一个任务的返回值不会添加到该任务的参数上去

	print((add.si(1, 2) | add.s(3))().get())  # 可以利用表达式重载
	print(chain(add.si(1, 2), add.s(3))().get())  # 也可以用函数的形式


## chord ##
chork(任务列表,善后任务) = 利用group执行任务列表 然后chain, 将返回值扔给 善后任务

	print(chord((add.si(1, i) for i in range(10)), xsum.s())().get())

有点 map-reduce 的味道?

## map ##

## starmap ##

## chunks ##

apply_async


# 寻找app实例 #
With --app=proj:

an attribute named proj.app, or
an attribute named proj.celery, or
any attribute in the module proj where the value is a Celery application, or
If none of these are found it’ll try a submodule named proj.celery:

an attribute named proj.celery.app, or
an attribute named proj.celery.celery, or
Any atribute in the module proj.celery where the value is a Celery application.


# 定时任务 #
http://docs.celeryproject.org/en/latest/userguide/periodic-tasks.html

CELERYBEAT_SCHEDULE

调整时区 CELERY_TIMEZONE

from datetime import timedelta

CELERYBEAT_SCHEDULE = {
    'add-every-30-seconds': { 这只是一个名字而已 随便就好
        'task': 'tasks.add', 要要用的方法
        'schedule': timedelta(seconds=30),
        'args': (16, 16) 参数,
    },
}

CELERY_TIMEZONE = 'UTC'

```python3
from celery.schedules import crontab

CELERYBEAT_SCHEDULE = {
    # Executes every Monday morning at 7:30 A.M
    'add-every-monday-morning': {
        'task': 'tasks.add',
        'schedule': crontab(hour=7, minute=30, day_of_week=1),
        'args': (16, 16),
    },
}

```

支持的参数
task schedule
args kwargs 调用任务的参数
options 调用 apply_async的额外参数
relative 


## 心跳服务 ##
当你需要定时任务的时候就需要心跳服务了, 心跳会每隔一段时间监测一下当前是否有任务可以执行 如果有的话就帮你把它推入到队列里 然后就会有 worker 去消费它

celery -A proj beat

将心跳服务内嵌到worker里(不推荐)
celery -A proj worker -B

心跳服务需要存储 任务上次运行的时间 之类的信息到一个本地的数据库文件里 默认是在当前目录下的celerybeat-schedule文件 可以通过-s修改

django-celery 项目有一个 scheduler 将信息保存到 django 的数据库里
```
celery -A proj beat -S djcelery.schedulers.DatabaseScheduler
```

# http任务 #

# 路由 #
有以下几个地方可以指定任务呗放到哪个对列
```
CELERY_ROUTES = {
    'proj.tasks.add': {'queue': 'hipri'},
},

add.apply_async((2, 2), queue='hipri')
```

```
from kombu import Exchange, Queue

CELERY_DEFAULT_QUEUE = 'default'
CELERY_QUEUES = (
    Queue('default', Exchange('default'), routing_key='default'),
)
```


对 worker 使用-Q q1,q2 表示该worker从q1和q2消费任务, q1和q2的顺序不重要 目前他们的权重是一样的



# 远程控制 #

## 检查状态 ##
celery -A proj inspect COMMAND

-d 手动指定一个节点 否则就是对于所有节点了
-t/--timeout 5 5秒内没收到回复的话就忽略这个 worker 了

检查各个节点的正在做的任务
active

显示正在被消费的queue
active_queues

获得节点的逻辑时间
clock

获得节点的配置
conf

ping节点
ping

看状态
stats

## 控制 ##
celery -A proj control COMMAND

enable_events/disable_events

revoke <task_id> 取消该任务, 每个worker都会在内存或硬盘上保存已经被取消的任务的id们, 所以此时任务应该还没从对列里取出来?
如果任务正在执行那么不会取消 如果指定了 terminate =True 那么会强制杀死正在执行那个任务的进程
```
>>> result.revoke()

>>> AsyncResult(id).revoke()

>>> app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed')

>>> app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed',
...                    terminate=True)

>>> app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed',
...                    terminate=True, signal='SIGKILL')

>>> app.control.revoke([
...    '7993b0aa-1f0b-4780-9af0-c47c0858b3f2',
...    'f565793e-b041-4b2b-9ca4-dca22762a55d',
...    'd9d35e03-2997-42d0-a13e-64a66b88a618',
])

```

默认已经取消的任务id是保存再内存, 可以指定一个地方用户持久化存储
celery -A proj worker -l info --statedb=/var/run/celery/worker.state
celery multi start 2 -l info --statedb=/var/run/celery/%n.state

## events ##

## status ##
查看节点状态
celery -A proj status



celery -A proj control revoke <task_id>
当worker收到revoke命令之后 就会跳过该任务的执行 但如果任务已经开始它不会停止该任务 除非 terminate 选项被设置

> The terminate option is a last resort for administrators when a task is stuck. It’s not for terminating the task, it’s for terminating the process that is executing the task, and that process may have already started processing another task at the point when the signal is sent, so for this reason you must never call this programatically.

```
result.revoke()
AsyncResult(id).revoke()
app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed')
app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed', terminate=True)
app.control.revoke('d9078da5-9915-40a0-bfa1-392c7bde42ed', terminate=True, signal='SIGKILL')


app.control.revoke([
'7993b0aa-1f0b-4780-9af0-c47c0858b3f2',
'f565793e-b041-4b2b-9ca4-dca22762a55d',
'd9d35e03-2997-42d0-a13e-64a66b88a618',
])

```

默认情况下 worker 会将 被废弃的任务id保存在内存里, 如果重启之后这些就没了, 可以持久化到文件里
celery -A proj worker -l info --statedb=/var/run/celery/worker.state

celery multi start 2 -l info --statedb=/var/run/celery/%n.state



# Application #

# Task #
一个任务直到被某个worker ack 后才会消失
一个worker有可能会先ack一堆任务, 但这堆任务只是被ack了, 还不一定开始做, 如果之后这个worker挂了, 那么任务也丢了

通常worker 收到任务之后先发ack, 然后在执行, 这样一个任务最多被执行一次
如果任务是幂等的, 那么设置 acks_late=False, 会使得在执行完毕之后才会发送ack, 这样保证任务最多执行一次(极端情况下好像也不是这样)


使用 @app.task 修饰器 修饰你的任务
如果有多个的话, 必须确保 @app.task 是第一个

如果是django或老式环境则:
```
from celery import task

@task
def add(x, y):
    return x + y
```

通过修饰器你可以指定:
1. 任务的名字 默认会自动生成(全模块名.方法名) 你也可以强制修改
2. 尽量避免相对导入, 在python3应该不是什么问题
3. 是否bind, 如果bind=True 就可以访问self变量
4. 默认重试时间 default_retry_delay
5. 默认重试次数 max_retries
6. 

通过request你可以获得请求的上下文
http://docs.celeryproject.org/en/latest/userguide/tasks.html

```
@app.task(bind=True)
def dump_context(self, x, y):
    print('Executing task id {0.id}, args: {0.args!r} kwargs: {0.kwargs!r}'.format(
            self.request))
```

要bind之后才能使用self 是一个 Task 对象, 有如下属性:
1. name
2. reuqest
3. max_retries 重试次数
4. throws 可能会抛出的异常 一旦在这里声明 那么如果抛出这些异常就不会被额外的日志记录 并且 trackback 属性也不会有
5. rate_limit 调用频率 比如 100/m 100/s 100/h
6. time_limit soft_time_limit 软硬超时
7. ignore_result 标记这个任务不需要记录结果 可以节省资源
8. compression 指定压缩算法, 默认是全局设置 CELERY_MESSAGE_COMPRESSION
9. acks_late 如果是True 那么会在任务执行完毕之后才发送ack(可能导致任务重复执行), 如果是False则是在收到任务的时候就发送ack(可能导致任务丢失), 默认是全局设置 CELERY_ACKS_LATE
10. track_started 默认是全局设置 CELERY_TRACK_STARTED, 这个任务是否要报告 started 状态, 如果没有的话 那么状态常见只有: pending(任务不存在或未开始) finished(已经结束) waiting(等待重试中) 如果设置为True 就会有一个新的状态 started
11. base=另外一个任务的引用 表示要继承它的配置

重试
调用 raise self.retry(一些参数) 则默认会再3分钟后重试

通过下面的代码可以覆盖默认的重试时间
```
raise self.retry(exc=exc, countdown=60)
利用 countdown 或 eta
```

## 任务常见配置 ##
max_retries 最大重试次数 你需要手动调用 self.retry 方法才行
default_retry_delay 默认是180秒 重试的间隔时间
rate_limit 限制频率 100/s /m/ /h 都行
> Example: “100/m” (hundred tasks a minute). This will enforce a minimum delay of 600ms between starting two tasks on the same worker instance.
> 它有一个全局的默认值  CELERY_DEFAULT_RATE_LIMIT 默认是None

time_limit 硬超时
soft_time_limit 软超时
ignore_result 忽略结果
acks_late CELERY_ACKS_LATE 提前ack 还是做完再ack
track_started CELERY_TRACK_STARTED 是否要跟踪启动状态


# 状态 #
每个状态可以有一些源数据附着在它上面
PENDING 等待执行 或 未知
STARTED 已经开始执行了, 任务的 track_started 必须为 True
FAILURE 执行失败, result 里是发生的异常 traceback 是异常栈信息
SUCCESS 执行成功
RETRY 正在重试中
REVOKED 被取消

## 定制状态 ##
你所需要做得就是提供一个唯一的名字, 一旦是全大写的
调用 self.update_state(state='自定义的状态的名字',meta={一些额外数据})
```
@app.task(bind=True)
def upload_files(self, filenames):
    for i, file in enumerate(filenames):
        if not self.request.called_directly:
            self.update_state(state='PROGRESS',
                meta={'current': i, 'total': len(filenames)})
```

# Result Backends #
```
Database Result Backend
Keeping state in the database can be convenient for many, especially for web applications with a database already in place, but it also comes with limitations.

Polling the database for new states is expensive, and so you should increase the polling intervals of operations such as result.get().

Some databases use a default transaction isolation level that is not suitable for polling tables for changes.

In MySQL the default transaction isolation level is REPEATABLE-READ, which means the transaction will not see changes by other transactions until the transaction is committed. It is recommended that you change to the READ-COMMITTED isolation level.
```


## Ignore ##
忽略这个任务

```
from celery.exceptions import Ignore

@app.task(bind=True)
def some_task(self):
    if redis.ismember('tasks.revoked', self.request.id):
        raise Ignore()
```

## Reject ##
可以用于重新派发该任务
要求acks_late=False
```
import errno
from celery.exceptions import Reject

@app.task(bind=True, acks_late=True)
def render_scene(self, path):
    file = get_file(path)
    try:
        renderer.render_scene(file)

    # if the file is too big to fit in memory
    # we reject it so that it's redelivered to the dead letter exchange
    # and we can manually inspect the situation.
    except MemoryError as exc:
        raise Reject(exc, requeue=False)
    except OSError as exc:
        if exc.errno == errno.ENOMEM:
            raise Reject(exc, requeue=False)

    # For any other error we retry after 10 seconds.
    except Exception as exc:
        raise self.retry(exc, countdown=10)
```

## Retry ##

# 自定义Task类 #
默认情况下我们的任务都是被 Task 类进行包装的

# 事务 #
```
from django.db import transaction

@transaction.commit_on_success
def create_article(request):
    article = Article.objects.create(…)
    expand_abbreviations.delay(article.pk)
```

如果有需要可以手动控制
因为事务是整个方法执行完毕后才提交的
而执行 expand_abbreviations.delay(article.pk) 的时候 可能事务还没提交
导致 这个方法 找不到对应的记录

```
@transaction.commit_manually
def create_article(request):
    try:
        article = Article.objects.create(…)
    except:
        transaction.rollback()
        raise
    else:
        transaction.commit()
        expand_abbreviations.delay(article.pk)
```

# call #
link 可以用于回调
countdown eta 用于指定运行的时间
expires 过期的时间
retry=False 不重试
compression 压缩方式

```
retry_policy是一个dict{
	max_retries 默认3 如果是0或None则是无限
	interval_start 默认0
	interval_step 默认0.2
	interval_max 默认0.2
}


add.apply_async((2, 2), retry=True, retry_policy={
    'max_retries': 3,
    'interval_start': 0,
    'interval_step': 0.2,
    'interval_max': 0.2,
})

```



```
>>> # Task expires after one minute from now.
>>> add.apply_async((10, 10), expires=60)

>>> # Also supports datetime
>>> from datetime import datetime, timedelta
>>> add.apply_async((10, 10), kwargs,
...                 expires=datetime.now() + timedelta(days=1)
```


获取结果
r = AsyncResult('e7f991c5-4221-4fb9-8346-d0ff42cdbc7e')
r.state

# Signatures #

任务.subtask()
任务.s() 是 subtask 的一个简化版
任务.si() subtask 的一个简化版 immutable=True

如果任务是 immutable 的 那么它不支持 partial


# 超时 #
>The time limit is set in two values, soft and hard. The soft time limit allows the task to catch an exception to clean up before it is killed: the hard timeout is not catchable and force terminates the task.


软超时 超时的时候方法会抛出异常
```
from myapp import app
from celery.exceptions import SoftTimeLimitExceeded

@app.task
def mytask():
    try:
        do_work()
    except SoftTimeLimitExceeded:
        clean_up_in_a_hurry()
```

硬超时, 对应的进程会直接被回收

# 广播 #
broadcast()
用于向工作者发送信号
```
>>> app.control.broadcast('rate_limit',
...                          arguments={'task_name': 'myapp.mytask',
...                                     'rate_limit': '200/m'})#默认是异步的


>>> app.control.broadcast('rate_limit', {
...     'task_name': 'myapp.mytask', 'rate_limit': '200/m'}, reply=True)#表示需要阻塞到所有所有工作者都回复
[{'worker1.example.com': 'New rate limit set successfully'},
 {'worker2.example.com': 'New rate limit set successfully'},
 {'worker3.example.com': 'New rate limit set successfully'}]



命令只发给某个接受者 而不是全部
>>> app.control.broadcast('rate_limit', {
...     'task_name': 'myapp.mytask',
...     'rate_limit': '200/m'}, reply=True,
...                             destination=['worker1@example.com'])
[{'worker1.example.com': 'New rate limit set successfully'}]

```

# 运行时修改时间限制 #
```
>>> app.control.time_limit('tasks.crawl_the_web',
                           soft=60, hard=120, reply=True)
[{'worker1.example.com': {'ok': 'time limits set successfully'}}]
```

# 速率限制 #
CELERY_DISABLE_RATE_LIMITS 默认是 True
```
app.control.rate_limit('myapp.mytask', '200/m')

>>> app.control.rate_limit('myapp.mytask', '200/m',
...            destination=['celery@worker1.example.com'])

```

# 优化 #
Eventlet

# 信号 #
http://docs.celeryproject.org/en/latest/userguide/signals.html

# 配置 #
http://docs.celeryproject.org/en/latest/configuration.html

建议使用专门的配置模块, 然后
app.config_from_object('celeryconfig')

可以使用下面的而语句打印出配置
app.conf.humanize(with_defaults=False, censored=True)
app.conf.table(with_defaults=False, censored=True)


CELERY_RESULT_BACKEND 表明结果存放在哪里

```
BROKER_URL = 'amqp://'
CELERY_RESULT_BACKEND = 'rpc://'

BROKER_TRANSPORT_OPTIONS = {'fanout_prefix': True, 'fanout_patterns': True, 'visibility_timeout': 3600}
CELERY_TASK_SERIALIZER = 'json'
CELERY_RESULT_SERIALIZER = 'json'
CELERY_ACCEPT_CONTENT=['json']
CELERY_TIMEZONE = 'Europe/Oslo' 时区
CELERY_ENABLE_UTC = True
CELERY_TRACK_STARTED = False 默认不跟踪是否启动 有需要的任务自己
CELERY_ROUTES = {
    'tasks.add': 'low-priority',
}
CELERY_ANNOTATIONS = {
    'tasks.add': {'rate_limit': '10/m'}
} 
CELERY_IGNORE_RESULT = True #默认情况下不需要记录结果 有需要的任务自己覆盖设置
```

CELERYD_MAX_TASKS_PER_CHILD
对于 prefork, 执行了多少个任务之后就强制重启worker进程



# 与Django整合 #
其实celery并不依赖与django, 如果想要再django里使用celery, 那么跟在一个普通程序里使用celery是一样的

http://docs.celeryproject.org/en/latest/django/first-steps-with-django.html

pip install django-celery



# 超时 #
## 软超时 ##
CELERYD_TASK_SOFT_TIME_LIMIT
引发异常
```
@app.task
def mytask():
    try:
        do_work()
    except SoftTimeLimitExceeded:
        clean_up_in_a_hurry()
```
## 硬超时 ##
CELERYD_TASK_TIME_LIMIT
直接杀掉进程

# 杂 #
1. 可以重启pool



# 后台化 #
http://docs.celeryproject.org/en/latest/tutorials/daemonizing.html#daemonizing

# 监控和管理 #
http://docs.celeryproject.org/en/latest/userguide/monitoring.html

# 安全 #
http://docs.celeryproject.org/en/latest/userguide/security.html

# 并发 #
http://docs.celeryproject.org/en/latest/userguide/concurrency/index.html





celery worker -A bilibili -l info -n worker1 --pidfile=/var/run/celery/worker1.pid --logfile=/var/log/celery/worker1.log

celery worker -A bilibili -l info -n worker1 --pidfile=/var/run/celery/worker1.pid --logfile=/var/log/celery/worker1.log

--uid=celery --gid=celery 

celery multi start 1 -A bilibili -l info --pidfile=/var/run/celery/%n.pid --logfile=/var/log/celery/%n.log

celery multi stopwait 1 -A bilibili -l info --pidfile=/var/run/celery/%n.pid --logfile=/var/log/celery/%n.log

celery beat -A bilibili -l info --pidfile=/var/run/celery/beat.pid --logfile=/var/log/celery/beat.log > /dev/null &

stop_all

