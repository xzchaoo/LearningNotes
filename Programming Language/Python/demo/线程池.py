import time
from concurrent.futures import ThreadPoolExecutor


def work2():
    print('work2')


def work(i, tpe, a, b, c, d, e):
    time.sleep(1)
    print('%d %d %d %d %d %d' % (i, a, b, c, d, e))
    tpe.submit(work2)
    # 在这个场景下 这个调用无效 因为此时 线程池已经被关闭了 不再接受新的任务 只会将已有的任务执行完


with ThreadPoolExecutor(4) as e:
    for i in range(10):
        args = [i, e, 1, 2, 3]
        kwargs = {'d': 4, 'e': 5}
        e.submit(work, *args, **kwargs)

# 会阻塞直到 所有任务结束
print('finished')
# time.sleep(2)
