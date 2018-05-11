# 3. 什么时候使用Rx #

## 3.使用Rx来编排异步和基于事件的操作 ##
处理 基于事件的或异步的操作 的代码 会随着操作数量的增加而容易变得越来越复杂, 你需要构建一个状态机来处理顺序问题.

> 自己的理解, 你需要处理很多的并发问题

此外, 在代码中, 处理error的情况也越来越多, 特别是基于回调的处理过程, 每个地方都要小心翼翼维护回调, 并且要记得回调, 因此存在大量的try/catch/finally
而导致代码不遵循普通的控制流程, 代码容易变得难懂和难以维护

在Rx的世界里, [基于事件的计算, 异步计算, 控制流, 异常传播] 是一等公民.

考虑一个例子

	  void asyncMethod(FooCallback callback) {
	    try {
	      int a = process1();
	      int b = process2();
	      bar.asyncMethod(a, b, new BarCallback() {
	        void onSuccess(int c, int d) {
	          try {
	            int result = processFinal(c, d);
	            callback.onSuccess(result);
	          } catch (Throwable e) {
	            callback.onError(e);
	          }
	        }
	
	        void onError(Throwable e) {
	          callback.onError(e);
	        }
	      });
	    } catch (Throwable e) {
	      callback.onError(e);
	    }
	  }
	
	  int syncMethod() {
	    int a = process1();
	    int b = process2();
	    int[] barResult = bar.syncMethod(a, b);
	    return processFinal(barResult[0], barResult[1]);
	  }
假设asyncMethod在异步的过程中出错了, 那么你外部的catch是无法捕获异常的, 所以异步代码不适用于普通的控制流

> TBD, 需要强调Rx在具备强大的编排能力

## 3.2 使用Rx来处理异步的数据流 ##
通常回调只能被调用一次, 当然你也可以加一些参数表示 终止值, 然后可以调用多次回调

Rx 定义了一个Publisher模型遵循如下的规范
  
1. 总是会 onSubscribe(Subscription) 方法
2. 之后可能调用0次或多次 onNext方法
3. 最后以 onError 或 onComplete 终结
	1. 在终结之前调用dispose, 后可能不会再触发 onError onComplete
		1. 我先称为过早死亡吧

这是一个非常强大的规范, 很多的概念可以往这个模型上靠.

1. 回调
2. Listener

# 4. Rx协议 #
Rx协议规定生产者()和消费者()需要遵循的协议  
因此在使用过程中, 或自定义OP过程中, 你可以假设所有的OP都是遵循这个协议的, 这可以加大地简化你的编码工作

## 4.1 ##
onSubscribe  
onNext*  
onError or onComplete

> 这里能否顺便介绍一下Rx图, 话说有没有好一点的办法可以辅助画Rx图的, 实在是太有用了!

## 4.2 observer会被串行调用 ##
生产者必须保证它是串行推送消息给消费者的  
消费者可以认为生产证是顺序推送给它消息的  
绝大部分的OP就是不需要考虑这个问题的, 只有少数几个设计并发, 异步的OP才需要


## 4.3 在onError或onComplete里释放资源 ##
也可以使用using操作符

## 4.4 生产者尽量响应dispose方法 ##
当一个生产者被d的时候, 它应该尽量快速地响应这个方法  
但这也只是尽量, 有一些操作时无法保证的  
比如下面的代码

	生产者:
	if(!isDisposed()){
		observer.onNext(1);
	}

	消费者
	subscription.dispose();

	假设现在生产者已经走进了 if 分支里, 在调用onNext方法之前, 消费者突然调用了dispose方法, 并且很快就执行完了
	然后生产者才  去调用onNext方法, 那么这时候调用者就会出现 调用了dispose方法之后还收到onNext消息
	目前除非加锁(用锁将onNext方法的调用, 和 dispose 方法整个包起来), 否则没有太好的解法

> 出现这种情况会导致什么严重的后果吗?

> 另外需要注意的是, 如果调用链上没有隐式或显式的线程切换的话, 那么所有的操作都是在当前线程上执行的

# 5. 使用Rx #
## 5.1 画 Marble-diagram ##
这个图非常有助于理解每个OP是如何工作的, 但对于总体是如何工作的似乎还需要动动脑去想
  
> 似乎只能用笔和纸, 目前没有太好的工具可以画这种图
> 练习到一定程度之后, 是可以摆脱这个图的
> 想更进一步的话最好去看源码

## 5.2 subscribe方法最好不要忽略error ##
subscribe方法可以只处理onNext消息, 而忽略其它消息(onError onComplete). 但这通常是不好的

> 除非你可保证上游一定不会抛异常  
> 如果上游是无限的, 那么也不适用这个场景

## 5.3 适用LINQ ##

## 5.4 了解隐式Scheduler ##
## 5.5 合理使用 observeOn ##
> subscribeOn呢?

## 5.6 使用限制buffer ##
在背压的时候会提到

## 5.7 显式使用do操作符来产生副作用 ##
尽量将副作用都放到这些操作符里去  
其它的操作尽量保持无状态性

### 什么时候可以违反 ###
很多情况, 特别是将结构挂到上下文的这种情况  
TODO rx规范似乎也没说不能这样做吧

## 5.8使用同步OP来修复那些不合规范的上游 ##
rx规范规定了说, 一定会先有 1次onSubscribe 再有0个或多个 onNext, 最后结束于1次 onComplete 或 onError
并且一定是串行调用, 但是可能会来自不同线程的

## 5.9 假设 消息会一直到来直到取消订阅完成 ##
当你调用dispose, 此时消息可能在生成的过程中, 并不一定保证一旦你调用了就不会有消息来  
因为这里涉及到一些原子性的操作, 有些情况下, 除非加锁, 否则无法保证这种情况  

我个人的理解是, 你可以将dispose等同于线程的中断, 你中断了一个线程, 这个线程可能依旧在执行(假设线程做一些计算, 而不是IO阻塞), 除非这个线程自己定期去检查中断标志位, 否则它不会知道自己被中断了

# 6. 操作符的实现 #

## 6.1 实现新的操作符 ##
在实现新的操作符之前, 我们需要先考虑: "我们真的需要自行实现一个新的操作符吗? 通过组合现有的操作符是否可以满足需求?"  
如果后者是成立的, 那么通常你应该优先选择组合模式

关键字: 组合 模板模式 继承 规范

## 6.4 在操作符里保护调用用户代码的部分 ##
当在一个OP例调用用户的代码(通过回调), 潜在地会发生异常, 必须进行try/catch

这一点在很多OP的实现代码里都可以看到, 所有用户传进来的都是不可信的
比如SingleMap的实现
有一点除外的是 Scheduler, 因为它的处理本身是异步的, 你try/catch它也没用

注意, 不要对 subscribe dispose onNext onError onComplete等回调方法进行保护, 这些方法
> TODO 原因?

## 6.5 ##
susbcribe方法的实现必须不能抛异常, 除非是 subscribe方法的参数为null(参数检查)

> 必须统一error的处理, 否则用户代码无法收到error回调

## 6.6 onError消息必须具有abort的语义 ##
一旦产生 onError 消息. 后续就不再允许调用 onNext 方法
> 这和传统的编程模型是相符合的

## 6.7 ##
自定义OP的实现, 必须要保证操作串行化
如果你的OP涉及到并发操作, 那么你需要自己保证回调串行化

## 6.8 避免串行化一个操作符 ##
可能是我RxJava2源码看得少, 目前我还没有看到哪个地方是加了锁的

## 6.9 对于需要控制并发的OP, 提供一个接受Scheduler参数的重载版本 ##
提供默认值

## 6.10 提供一个默认的Scheduler ##
## 6.11 Scheduler应该是最后一个参数 ##
## 6.12 避免引入并发 ##
默认不需要切线程,就不要切线程

> 如果实在需要呢? 在一些专门的OP里实现
注意 immediate 的Scheduler可能会引起阻塞

## 6.13 交出所有的disposable ##
1. 解释什么是d
2. 强调d的所用
3. 强调级联d
4. d是幂等的 可以被并发多次安全d

> Disposable 是上游返回给调用者的一个holder, 调用者可以用这个holder来cancel掉上游, 注意, 不保证dispose调用了之后就不再收到消息, 这里会有一个原子性问题, 因此即使对于一个实现良好的OP, 也有可能在dispose方法之后还继续调用onNext, 但通常时间不会太长
必要的时候需要有一些防御性代码. 这就类似于线程被中断之后也是有可能继续执行的, 除非这个线程自己去检测中断位

### 重要的类 ###
1. Disposables提供了很多辅助方法
	1. 包装一个Runnable/Action, 让它在dispose时执行
		1. 它可以保证run只被调用一次
	2. 包装一个future, 当被d的时候就cancel调这个f, 用f的isDone来判断是否已经被d了
	3. 包装一个Subscription(它和Disposble概念类似)
	4. 提供一个 empty 实现
	5. 提供一个 disposed 实现
2. ReferenceDisposable\<T\> 扩展了 AtomicReference
	1. 模板模式
	2. 可以保证 onDisposed 方法只被调用一次 
	3. 子类只需要重写onDisposed方法
3. RunnableDisposable/ActionDisposable 扩展了 ReferenceDisposable
	1. onDisposed方法里调用run方法
4. CompositeDisposable 这是非常重要的一个类, 下面会详细介绍
5. SerialDisposable 这是非常重要的一个类, 虽然直接使用不多, 但是它的模式很常用

#### SerialDisposable ####
内部封装了一个 ``AtomicReference<Disposable> resource``, 其实也可以通过继承的方式做到  
默认情况下, 引用的Disposable为null, 它提供了set和replace方法, 这两个方法都会委托给 DisposableHelper 的对应方法  
观察 DisposableHelper 的这两个方法, 它以原子性的方式替换AtomicReference里的Disposable元素

对于set方法, 它会原子性地 替换出旧的D并且加入新的D, 并且dispose旧的D
如果旧的Disposable已经被dispose了(通过一个特殊的Disposable实例判断), 那么就把新的Disposable也dispose掉
	
	 // 如果该D已经被d了, 那么就返回false, 否则原子性替换掉旧的D, 并且d掉旧的D
     public static boolean set(AtomicReference<Disposable> field, Disposable d) {
        for (;;) {
			//获取旧的D
            Disposable current = field.get();
			如果旧的D已经被dispose了, 那么它会被替换成一个特殊的实例 DISPOSED, 这里进行判断
            if (current == DISPOSED) {
				// 因为旧的D已经被dispose了, 因此新加入的这个D也要立即进行dipose
                if (d != null) {
                    d.dispose();
                }
                return false;
            }
			// 这里有竞争, 因此用cas保证安全
            if (field.compareAndSet(current, d)) {
				// d掉旧的D
                if (current != null) {
                    current.dispose();
                }
                return true;
            }
        }
    }


> 代码里似乎很少直接用到 SerialDisposable, 但是经常会有一个类, 它自己实现了类似 SerialDisposable 的功能, 这可能是RxJava2为了避免创建太多对象的做法

#### CompositeDisposable ####
简称cd  
从名字上可以猜测出来, 它是一个 D 的容器, 它可以容纳多个D, 如果它自己被D了, 那么它包含的所有D也会被d  
想要在并发环境下正确实现cd需要一些技巧, 光有线程安全是不够的  
想象一下, cd内部用了一个并发安全的Queue来存储多个Disposable, 线程A想要往cd里加入一个新的D, 线程B想要dispose掉整个cd  
你需要在不加锁的情况下, 保证不管哪个步骤先执行, 结果都是对的  
在没有接触到源代码之前, 我认为在不加锁的情况下是很难实现的, 大家可以自己试一试

> TODO cd用了隐式锁
add(D d)

其实可以用原子操作来代替的, 为什么不用呢? 是考虑到性能吗?
 
TODO 不是说dispose方法不能抛异常吗, 为何它还是抛异常了, 是允许在调用级联dispose之后抛异常吗?

通过学习 Disposable 的处理, 你可以掌握到很多无锁的操作方式, 真的是非常有趣


## 6.14 OP不应该阻塞 ##
除去用户提供的代码部分, Rx自己不应该阻塞

> 需要注意使用特殊的Scheduler时, 可能会造成阻塞

## 6.15 ##
避免太深的调用栈, 通常是不会的  
TODO rx是否有潜在导致调用站过深的问题?
> TODO 尾递归可以优化吗?

## 6.16 参数校验发生在OP之外 ##
OP参数异常应该抛异常
根据RxJava2的实现方式, 通常会提供一个Facade, 这个Facade 再根据参数去选择合适的OP实现  
校验参数合法性也是Facade的工作

> 通过查看OP的源代码可以发现, OP本身是一个不可变对象, 一旦构建出来, 它就是可重用的, 线程安全的, 因此你需要及时在外面抛异常, 防止构造出一个不合法的OP实例

## 6.17 dispose/cacnel是幂等的 ##
实现者需要保证dispose在任意时间可以被任意线程调用, 并且保持幂等性

> 通常需要借助一些原子操作 和 CompositeDisposable 来保证

## 6.18 dispose方法不应该抛异常 ##
dispose方法用于级联地取消任务, 因此不应该抛异常, 否则可能无法实现级联

> 其实想抛异常还是可以的, 但你必须推迟直到所有级联D的dispose方法都被调用过后才行

## 6.19 自定义的OP必须遵循Rx规范 ##
废话

## 6.20 自定义的OP应该参考Rx指南 ##

# 杂 #

## 关于取消订阅 ##
