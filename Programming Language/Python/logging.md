https://docs.python.org/3/library/logging.config.html

# logger的几个属性 #
propagate True/False 是否向上传播
level
各种日志方法

# 日志等级 #
```
Level	Numeric value
CRITICAL	50
ERROR	40
WARNING	30
INFO	20
DEBUG	10
NOTSET	0
```


# formatter #
用于格式化日志
几个常用功能的占位符:

%(asctime)s 当前时间
%(filename)s 所在的文件
%(funcName)s 所在的方法
%(levelname)s 日志级别
%(lineno)d 行号
%(module)s 模块名
%(message)s
%(name)s logger的名字
%(thread)d 线程id
%(threadName)s 线程名字

%(asctime)s %(levelname)s [%(threadName)s] %(name)s [%(funcName)s:%(lineno)d] %(message)s



# handler #
用于将格式化后的日志保存到文件或其他地方
有filters

常用的handler有:
1. StreamHandler 一般用于输出到标准输出
2. FileHandler 简单的输出到文件
3. NullHandler 空实现
4. TimedRotatingFileHandler 基于时间表达式的文件
5. 此外还可以输出到某个socket , http 接口, syslog,　Queue
6. 

# filter #
用于过滤

# logger #
有 filters



# logging #
```
import logging
logging.basicConfig(format='%(asctime)s %(message)s', datefmt='%m/%d/%Y %I:%M:%S %p')

logging.basicConfig(filename='example.log', level=logging.DEBUG)
#logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.DEBUG)
logging.debug('This message should go to the log file')
logging.info('So should this')
logging.warning('And this, too')
```

#--log=INFO


# 基于dict对象 #

```
LOGGING = {
    'version': 1,
    'disable_existing_loggers': False,
    'formatters': {
        'verbose': {
            'format': '%(asctime)s %(levelname)s [%(threadName)s] %(name)s [%(funcName)s:%(lineno)d] %(message)s',
			'datefmt': '%Y-%m-%d %H:%M:%S'
        },
        'simple': {
            'format': '%(levelname)s %(message)s'
        },
    },
    'handlers': {
        'console': {
            'class': 'logging.StreamHandler',
            'formatter': 'simple',
            # 'propagate': True
        },
        'rfile': {
            'class': 'logging.handlers.TimedRotatingFileHandler',
            'encoding': 'utf-8',
            'filename': 'tlog.log',
            'when': 'D',
            'formatter': 'verbose',
            # 'propagate': False
        }
    },
    'loggers': {
        'django': {
            'handlers': ['console'],
            'level': os.getenv('DJANGO_LOG_LEVEL', 'INFO'),
        },
        'a3': {
            'handlers': ['console', 'rfile'],
            'level': os.getenv('DJANGO_LOG_LEVEL', 'INFO'),
            'propagate': False
        }
    },
}

```

## 增量配置 ##
当 dict 对象的 incremental 为 True 的时候, 就是增量配置
会忽略 formatters filters 的定义
只处理 handlers的level配置



# 基于配置文件 #
```
[loggers]
keys=root,app

[handlers]
keys=consoleHandler,fileHandler,timedHandler,httpHandler

[formatters]
keys=simpleFormatter

[logger_root]
level=INFO
handlers=consoleHandler

[logger_app]
level=DEBUG
handlers=consoleHandler,fileHandler,timedHandler,httpHandler
qualname=app
propagate=0

[handler_consoleHandler]
class=StreamHandler
level=DEBUG
formatter=simpleFormatter
args=(sys.stdout,)

[handler_fileHandler]
class=FileHandler
level=DEBUG
formatter=simpleFormatter
args=('log.log',)

[handler_timedHandler]
class=handlers.TimedRotatingFileHandler
level=DEBUG
formatter=simpleFormatter
args=('tlog','D')#按照天进行划分

[handler_httpHandler]
class=handlers.HTTPHandler
level=DEBUG
formatter=simpleFormatter
args=('localhost:3344','/ce')


[formatter_simpleFormatter]
format=%(asctime)s - %(name)s - %(levelname)s - %(message)s
datefmt=%Y/%m/%d %I:%M:%S
```
