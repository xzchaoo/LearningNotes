# Slidr #
https://github.com/r0adkll/Slidr



要求style
```xml
<item name="android:windowIsTranslucent">true</item>  
<item name="android:windowBackground">@android:color/transparent</item>
```

最简单的使用

```java
//在setContentView之后调用
Slidr.attach(this);
默认的配置是 edge=LEFT,任何位置都可以滑动
```

完整配置
```java
SlidrConfig config = new SlidrConfig.Builder()
	.edge(true)//只能在边缘拉动
	.position(SlidrPosition.LEFT)//方向,只能指定一个
	.listener(new SlidrListener() {
		@Override
		public void onSlideStateChanged(int state) {

		}

		@Override
		public void onSlideChange(float percent) {
			Log.i("zas", "p=" + percent);
			View panel = findViewById(R.id.slidable_panel);
			View oldScreen = findViewById(R.id.slidable_content);
			//ViewCompat.setAlpha(panel, percent);
			//panel.setPivotX();
			ViewCompat.setScaleX(oldScreen, 0.5f+0.5f*percent);
			ViewCompat.setScaleY(oldScreen, 0.8f + 0.2f * percent);
		}

		@Override
		public void onSlideOpened() {

		}

		@Override
		public void onSlideClosed() {

		}
	})
		//.scrimColor(Color.YELLOW)//笼罩物的颜色 笼罩物就是改在底下的Activity之上的那层
		//.scrimStartAlpha(0.9f)//开始透明度
		//.distanceThreshold(0.75f)//当滑动超过 75%的时候 手放开 那么就关闭当前的Activity
		//.scrimEndAlpha(0.5f)//最终透明度

		//下面的好像不起作用啊
	.edgeSize(0.75f)
	.primaryColor(Color.RED)
	.secondaryColor(Color.GREEN)
	.build();
Slidr.attach(this, config);
```


