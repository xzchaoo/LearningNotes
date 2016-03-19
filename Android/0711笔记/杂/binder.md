新建一个线程并使之成为Looper
new Thread(){
	public void run(){
		Looper.prepare();
		mHandler=new YourHandler();
		Looper.loop();
	}
}.start();
mHandler是外部的一个成员变量 这样你就可以获得
在另外一个线程的mHandler了
需要注意的是,mHandler要等线程执行后才会有值

Binder

进程间通信
进程间传递数据的载体 Parcel
Bundle Parcelable
Parcel.obtain()
AIDL
