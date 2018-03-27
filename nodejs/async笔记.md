大多数情况下, 任何一步抛出异常, 则整个流程终止
但也有少数几个OP会继续传播err的, 比如compose/seq

一些OP会保持原有的顺序

和Rx相比, 学习成本比较低, 缺点, 不是基于"流"的

coll表示一个数组
it是一个异步函数, 通常形如

	function(item, callback){...}

fcb是最终的回调, 通常形如
	
	function(err, result){...}

# 映射型 #
concat(coll, it, fcb) 并发迭代元素, 异步函数返回一个list, 将所有list的结果累加在一起作为最终的结果   
我看结果似乎还是有序的, 但是官方文档说不保证

groupBy(coll,it,fcb) 并发迭代所有元素, 每个元素异步返回一个key, key相同的聚集在同一个数组里, 对于每个key, value数组是有序的

map(coll,it,fcb) 迭代所有元素, 异步进行map操作, 不保证发生map是有序的, 但是结果保持有序  
mapValues(obj, it, fcb) 和map类似, 第一个参数和最终回调结果都变成了字典

reduce(coll, memo, it, fcb) 异步版的reduce, memo是初始化参数
reduceRight(coll, memo, it, fcb) 反向版本reduce  
transform(coll, accumulatoropt, it, fcb) 类似reduce, reduce返回新的值, 而transform是不停更新一个已有的值


# ???型 #
each(coll,it,fcb) 并发迭代所有元素, it只需要回调通知执行完成就行, 不需要返回结果  
eachOf(coll,it,fcb) 和each类似, 但it会新增一个参数接收元素的索引 或 属性的key

parallel(tasks, fcb) 并发执行tasks, 任意一个失败则全部失败, 结果是 每个task执行结果的数组
series(tasks, fcb) 串行执行task, 但task不能取到之前task的执行结果, 结果是所有task的执行结果合并

race(tasks, callback) 并发执行tasks, 第一个返回(包含error)的tasks的结果作为最终结果


# 过滤型 #
detect(coll,it,fcb) 并发迭代元素, 第一个异步返回true的元素作为最终结果, 不保证一定是原始数组里的第一个! 因为是并发嘛!

every(coll,it,fcb) 迭代所有元素, 如果全部异步返回true, 则最终结果为true, 否则为false, 如果error则立即error  
some(coll, it, fcb) 迭代所有元素, 只要有一个异步返回true, 则最终结果为true

filter(coll,it,fcb) 并发迭代所有元素, 保留异步返回true的元素, 结果保持有序  
reject(coll, it, fcb) 和filter相反, 结果保持有序



# 流程编排型 #
auto(tasks, concurrency*, fcb) tasks是一个obj, 描述了依赖关系 concurrency 控制并发数, 如果不指定则是无限大  
autoInject auto的语法糖, 更好用

compose(...fns)返回一个新的函数 实现 f(g(h())) 逻辑, 注意顺序!  
seq(...fns) 和 compose 相反, 用于创建异步版的 h(g(f())), 支持传递多个参数给下一个异步函数  
waterfall(tasks, fcb) 串行执行每个task, 前面task的结果作为后面task的参数, 遇到错误会立即中断
和seq的区别在于 seq 返回的是一个包装后的函数


# 循环型 # 
during	当test函数异步返回true, 则继续异步执行内容  
whilst 当test函数返回true的时候则继续异步执行内容  
until ...  
doUntil/doWhilst/doDuring 就像 do-while和while的关系  
forever(fn, fcb) 无限串行异步执行fn  


# 杂 #
applyEach(fns, ...args, fcb) 并发将args运用到每个fn上
sortBy(coll,it,fcb) 对于每个item, 异步返回一个排序key

cargo(worker, payload) 每积累payload个元素, 就用worker函数来批量处理, 同一时刻只能有一个批处理正在进行, 多余的会被queue住


queue(worker, concurrency) 有一个处理队列, 可以设置并发处理数, 扔到该queue里的元素等待 worker 执行它, 执行完之后触发给定的回调
可以把queue想象成是模拟的线程池
concurrency控制并发数, 默认是1

priorityQueue 和 queue类似, 但是有优先级的概念

retry(opts, task, fcb) 重试执行一个给定的函数 opts用于配置重复执行多少次, 间隔时间之类, 注意, 这会导致task立即被执行  
retryable(opts, task) 将一个函数包装成具备重试能力的函数, 注意这是包装, 它会返回一个新的函数

times(n, it, fcb) 相当于 map([0,...,n-1], it, cb)

tryEach(tasks, fcb) 串行执行每个task, 遇到第一个成功的就立即返回


# 辅助函数 #
apply(fn, ...args) 创建偏函数
这个在async的大多数场景会很有效

用了之前:

	async.waterfall([
		callback => fs.readFile(filename,'utf-8', callback)
		...
	], callback);

用了之后:

	async.waterfall([
	    async.apply(fs.readFile, filename, "utf8"),
		...
	], callback);




asyncify(sync_func) 将一个同步的方法异步化, 猜测是这样实现的


	function asyncify(sync_func){
		return function(args){
			const cb = args[args.length-1]
			try{
				const ret = sync_func.apply(args[:args.length-1])	
				cb(null, ret)
			}catch(e){
				cb(e)
			}		
		}
	}

这里缺少一些精细的判断, 比如callback一般都是可选的, 这里没有这个假设1


constant(value) 将一个常数做成立即返回的异步函数

dir(fn, ...args) 辅助函数, 在异步函数fn执行完之后将其函数名和结果打印到控制台
log(fn, ...args) 辅助函数, 在异步函数fn执行完之后将其结果打印到控制台

ensureAsync(fn) 对一个异步函数进行封装, 保证它的回调不是同步的  
有一些异步函数它们其实是立即完成的, 如果这样的量很大的话, 会产生很大的调用栈, 或整个流程降级为同步的
利用这个方法就可以将回调放到下一次事件循环去执行


memoize(fn, hasher*) 可以缓存一个fn的结果, hasher用于对请求参数计算一个哈希值, 哈希值一样的会被用同一个结果
如果不提供hasher, 则调用的第一个参数作为hashKey
unmemoize

nextTick(cb, ...args) 辅助方法, 放到下次事件循环去执行
setImmediate(cb, ...args)

reflect(fn) 包装一个异步函数, 是的它没有error, value形如 {error:原本的error, value:原本的value}  
reflectAll(tasks) 对多个task做reflect

timeout(fn, mills, info*) info是可选的, 当发生超时时用info作为error

