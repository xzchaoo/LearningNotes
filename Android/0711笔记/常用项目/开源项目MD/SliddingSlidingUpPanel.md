SlidingUpPanelLayout
可以指定android:gravity top或bottom 从而允许下拉或上拉
只能有2个儿子,第一个儿子是主要内容,第二个儿子是要拉的内容
第一个而是一般宽高都是m,第二个儿子而已不一定
其他参数
umanoPanelHeight 初始化的时候面板的高度
umanoShadowHeight 阴影

参数|类型|说明
:---|:--|:--
umanoPanelHeight|dimension|面板收起来时的高度
umanoShadow|Heightdimension|阴影的高度
umanoParalaxOffset|dimension|视差
umanoFadeColor|color|主要内容fade的颜色
umanoFlingVelocity|integer|fling的速度
umanoDragView|reference|不太清楚
umanoScrollableView|reference|如果面板里有可以滚动的view,请设置它的引用
umanoOverlay|boolean|面板的内容是否会叠加在主面板上,因为面板默认是透明的
umanoClipPanel|boolean|不太清楚
umanoAnchorPoint|float,介于0-1|可以临时倚靠在屏幕的哪里,你可以设置成0.5,然后再去看看效果
umanoInitialState|enum:expanded,collapsed,anchored,hidden|状态
	
setEnabled:禁用
setTouchEnabled:禁用触摸,但是还是允许变成方式打开或关闭panel
get/setPanelState 
setAnchorPoint
PanelSlideListener
umanoScrollableView:如果你的panel里有可以管道工的view,那么请设置为true
setOverlayed/umanoOverlay overlay模式 这样默认面板是透明的,会覆盖在主要内容上面
umanoFadeColor

几个注意事项
如果你自定义umanoDragView,要确保它是clickable,否则将会点穿

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.sothree.slidinguppanel.SlidingUpPanelLayout
	android:id="@+id/sliding_layout"
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:sothree="http://schemas.android.com/apk/res-auto"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:gravity="bottom"
	sothree:umanoPanelHeight="68dp"
	sothree:umanoParalaxOffset="100dp"
	sothree:umanoShadowHeight="4dp"
    sothree:umanoOverlay="false"
	sothree:umanoDragView="@+id/dragView"
	>

	<!--这是主要内容-->
	<TextView
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:gravity="center"
		android:text="Main Content"
		android:textSize="16sp"/>

	<!--这是可以上拉的内容-->
	<LinearLayout
		android:id="@+id/dragView"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:orientation="vertical"
	    android:clickable="true"
		android:focusable="false"
		>

		<TextView
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:gravity="center|top"
			android:text="The Awesome Sliding Up Panel"
			android:textSize="16sp"/>

		<Button
			android:id="@+id/btn1"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:text="BTN1"
			/>

		<Button
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:text="BTN2"
			/>

		<Button
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:text="BTN3"
			/>
	</LinearLayout>
</com.sothree.slidinguppanel.SlidingUpPanelLayout>
```