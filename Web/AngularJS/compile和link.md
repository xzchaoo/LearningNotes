1. 指令的函数只会执行一次, 这个没什么问题
2. 每当你在html里用到了指令, 就会进行一次compile, 如果你和 ng-repeat 一起用, 那么也只会进行一次compile
	1. function($scope,$ele,attrs)
	2. 先进行父元素的compile 再进行子元素的compile
	3. compile 阶段你可以对元素进行修改, 但注意你此时的修改相当于是在修改 template, 所以不要绑定方法, 只能修改值
3. 接着进行 controller 的绑定
	1. 就是调用你的controller函数
4. preLink 处理子指令之前
5. 执行子指令的 controller pre post, 子指令又会去调用子子指令...
6. postLink, 这也就是普通的link方法
	1. 此时该元素及其子元素已经完全初始化完毕了, 你可以在这里访问dom元素, 绑定事件等
	2. 如果绑定了事件 记得销毁哦 在 scope 灭掉的时候
