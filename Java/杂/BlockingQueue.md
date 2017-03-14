常用的实现 LinkedBlockingQueue ArrayBlockingQueue

PriorityBlockingQueue


# Queue接口 #

null/boolean系列
offer(e) 返回进队是否成功
poll() 移除并返回下一个元素
peek() 偷看下一个元素

异常系列
add(e) 进队成功则true 否则异常
remove() 移除并返回下一个元素
element() 偷看下一个元素


# BlockingQueue #

阻塞系列
put(e) 进队
take() 阻塞直到有元素, 无限等待

超时系列
offer() 进队, 可以指定超时时间, 成功则true, 失败则false
poll() 阻塞直到有元素, 可以指定超时时间, 如果超时则null

drainTo() 移除本队列的所有元素, 把他们加入到一个集合里
如果将元素加入到集合的过程中失败, 可能会导致一个中间状态:一些元素在队列里, 一些在集合里


# LinkedBlockingQueue #
基于链表, 可以有最大个数限制

# ArrayBlockingQueue #
基于数组

# PriorityBlockingQueue #
具有优先级

# DelayQueue #

