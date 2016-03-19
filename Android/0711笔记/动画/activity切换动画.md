# Activity切换动画 #
	<!-- Base application theme. -->
	<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
		<!-- Customize your theme here. -->
		<item name="colorPrimary">@color/colorPrimary</item>
		<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
		<item name="colorAccent">@color/colorAccent</item>
		<item name="android:windowAnimationStyle">@style/ani</item>
	</style>

	<style name="ani">
		<item name="android:activityOpenEnterAnimation">@anim/activity_open_enter_animation_1</item>
		<item name="android:activityOpenExitAnimation">@anim/activity_open_exit_animation_1</item>
	</style>
>
主要是添加 **\<item name="android:windowAnimationStyle">@style/ani\</item>**
然后自己添加一个style,ani,里面的属性:

属性|作用
:-:|:-:
android:activityOpenEnterAnimation|因为**打开activity**,而进入的那个activity的动画
android:activityOpenExitAnimation|因为**打开activity**,而退出的那个activity的动画
android:activityCloseEnterAnimation|因为**关闭activity**,而进入的那个activity的动画
android:activityCloseEixtAnimation|因为**关闭activity**,而进入的那个activity的动画
除此之外还有其他很多的属性可以看[这里](https://developer.android.com/intl/zh-cn/reference/android/R.styleable.html#WindowAnimation),在里面搜索关键字`WindowAnimation`就可以找到其他相应的属性,对于浙西二属性基本上通过名字就可以知道他们的效果了

给几个动画的例子
### activity_open_enter_animation_1 ###
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
     android:duration="600">
	<scale
		android:fromXScale="0.6"
		android:fromYScale="0.6"
		android:pivotX="50%"
		android:pivotY="50%"
		android:toXScale="1"
		android:toYScale="1"
		/>
	<!--
	<translate
		android:fromYDelta="-100%"
		android:interpolator="@android:interpolator/bounce"
		android:toYDelta="0"
		/>

	<rotate
		android:fromDegrees="-90"
		android:interpolator="@android:interpolator/bounce"
		android:pivotX="0"
		android:pivotY="0"
		android:toDegrees="0"/>
		-->
	<alpha
		android:fromAlpha="0.5"
		android:toAlpha="1"/>
</set>
```
### activity_open_exit_animation_1 ###
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
     android:duration="600" android:zAdjustment="top">
	<rotate
		android:fromDegrees="0"
		android:pivotX="0"
		android:pivotY="0"
		android:toDegrees="90"
		/>
	<alpha
		android:fromAlpha="1"
		android:toAlpha="0.5"/>
</set>
```
