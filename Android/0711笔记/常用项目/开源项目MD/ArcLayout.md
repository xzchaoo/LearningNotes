# 开源项目学习 #

# 布局 #
项目|简介|minSdk
:--|:--|:--
[Blurry](#Blurry)|View模糊|9
[CircularFloatingActionMenu](#CircularFloatingActionMenu)|可以Arc展开的一群ActionButton|15
[JumpingBeans](#JumpingBeans)|让文字跳动|15
[range-slider-view](#range-slider-view)|range-slider-view|14
[material-range-bar](#material-range-bar)|material-range-bar|12
[discreteSeekBar](#discreteSeekBar)|SeekBar|7
[ChatMessageView](#ChatMessageView)|类似QQ消息的View|15
[SmartTabLayout](#SmartTabLayout)|...|4
[TextDrawable](#TextDrawable)|将String做成Drawable|10
[CircleIndicator](#CircleIndicator)|用于创建各种指示器,流弊|11
[ViewPagerTransforms](#ViewPagerTransforms)|提供了很多种VPTransformer,流弊,建议自己建立所有的例子程序来看效果|13
[Dragger]|https://github.com/ppamorim/Dragger, 去查看例子程序,DraggerActivity.java|未知
SliddingSlidingUpPanel.md|给一个界面添加可以下滑或上拉的面板,详情见SliddingSlidingUpPanel.md|未知
[ShowcaseView](#ShowcaseView)|Showcase View|9
[MaterialShowcaseView](#MaterialShowcaseView)|Showcase View|14
[android-segmented-control](#android-segmented-control)|一种RadioGroup,其实这种东西完全可以自己自做...![](http://i.imgur.com/bdRlZWp.jpg)|8
[MaterialProgressBar](#MaterialProgressBar)|进度条|14
[materialish-progress](#materialish-progress)|进度条![](https://raw.githubusercontent.com/pnikosis/materialish-progress/master/spinningwheel_progress.gif)|9
[CircleProgressBar](#CircleProgressBar)|![](http://i.imgur.com/e4VAWqo.jpg)|9
[GridPasswordView](#GridPasswordView)|长度固定的密码输入框|11
[ShadowLayout](#ShadowLayout)|给View加上阴影|14
[DropDownMenu](#DropDownMenu)|DropDownMenu|8
[Droppy](#Droppy)|替换PopupMenu|13
[Context-Menu]()|Context-Menu|11
[ExpandableLayout](#ExpandableLayout)|可以展开的布局|11
[BottomSheet](#BottomSheet)|从底部弹出一个View|14
[百分比布局](#android-percent-support-extend)|百分比布局|7
[ArcLayout](#ArcLayout)|可以实现圆形的布局 ![](https://raw.githubusercontent.com/ogaclejapan/ArcLayout/master/art/icon.png)|4

## <a name="Blurry"></a>Blurry##
github: https://github.com/wasabeef/Blurry
用到了RenderScript
可以将某个View模糊之后塞给一个ImageView
图片:
![](http://i.imgur.com/Ue8Hm3r.jpg)


## <a name="CircularFloatingActionMenu"></a>CircularFloatingActionMenu ##
github: https://github.com/oguzbilgener/CircularFloatingActionMenu
图片:
![](http://i.imgur.com/tVtm7TK.jpg)
## <a name="JumpingBeans"></a>JumpingBeans ##
github: https://github.com/frakbot/JumpingBeans
笔记里有一个叫做WaitingDots的项目,它是一个TextView,本身的内容只能是3个圆圈在跳动,而这个项目可以让文字也跳动,只是目前文字的范围必须是连续的,否则需要用多个TextView来组成
用法:
```java
JumpingBeans.with((TextView)findViewById(R.id.loadding_2))
			//.appendJumpingDots()
			.setIsWave(true)
			.setLoopDuration(1000)
			.makeTextJump(0,8)
			.build();
```
## <a name="range-slider-view"></a>range-slider-view ##
github: https://github.com/channguyen/range-slider-view
图片:
![](https://raw.githubusercontent.com/channguyen/range-slider-view/master/screenshots/sc.png)
```xml
	<com.github.channguyen.rsv.RangeSliderView
		android:layout_width="match_parent"
		android:layout_height="50dp"
		app:emptyColor="#ccc"
		app:filledColor="#1A5F77"
		app:rangeCount="7"
		app:sliderRadiusPercent="0.3"
		app:slotRadiusPercent="0.15"
		app:barHeightPercent="0.1"
		/>
```
app:emptyColor 未选中部分的颜色
app:filledColor 选中部分的颜色
app:rangeCount 有几个站点
app:sliderRadiusPercent 用于控制大圆圈的大小
app:slotRadiusPercent 用于普通的圆圈的大小
app:barHeightPercent 用于控制bar的高度

		
## <a name="material-range-bar"></a>material-range-bar </a>
github: https://github.com/oli107/material-range-bar
几个概念:
selector就是那个大点,可以拖动的
bar:seekbar的横条
pin:弹出的,用于显示数值的圆圈
tick 刻度
如果设置了刻度,那么值将会被吸引到刻度附近
```xml
<com.appyvet.rangebar.RangeBar
	android:id="@+id/rangebar"
	android:layout_width="match_parent"
	android:layout_height="72dp"
	app:barWeight="1dp"
	app:selectorSize="2dp"
	app:tickEnd="90"
	app:rangeBar="true"
	app:tickHeight="1dp"
	app:tickInterval="10"
	app:tickStart="10"
	/>

```
## <a name="discreteSeekBar"></a>discreteSeekBar ##
github: https://github.com/AnderWeb/discreteSeekBar
图片:
![](https://camo.githubusercontent.com/b1c5e00bc9164c24b995a95942dbb731edd8d39e/68747470733a2f2f6c68362e676f6f676c6575736572636f6e74656e742e636f6d2f2d4a6a7678564d436d3175672f56485550575642667062492f41414141414141414874512f5450746f4f6a4849354d412f773633392d683335362f7365656b626172322e676966)
例子
```xml
<org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	app:dsb_allowTrackClickToDrag="true"
	app:dsb_indicatorColor="#00f"
	app:dsb_max="100"
	app:dsb_min="0"
	app:dsb_progressColor="#f00"
	app:dsb_trackColor="#0f0"
	app:dsb_value="30"
	/>
```
## <a name="ChatMessageView"></a>ChatMessageView ##
github: https://github.com/himanshu-soni/ChatMessageView
![](https://raw.githubusercontent.com/himanshu-soni/ChatMessageView/master/screenshot/screen2.jpg)

## <a name="SmartTabLayout"></a>SmartTabLayout ##
github: https://github.com/ogaclejapan/SmartTabLayout
有不错的样式,不过一般情况下没有必要用吧?

## <a name="TextDrawable"></a>TextDrawable ##
github: https://github.com/amulyakhare/TextDrawable
基本上只要去网上看看就行了
需要注意的是ImageView需要指定大小,当然你也可以不指定,那么你就要为你生成的TextDrawable手动指定大小,否则显示不了
```java
.beginConfig()
.width(60)  // width in px
.height(60) // height in px
.endConfig()
```
好像有复杂的效果,比如有2个图片拼凑在一起的,那个就不太清楚该怎么做了
可以去看看官方的例子程序
![](https://raw.githubusercontent.com/amulyakhare/TextDrawable/master/screens/screen7.png)




## <a name="CircleIndicator"></a>CircleIndicator ##
github: https://github.com/ongakuer/CircleIndicator
下面是切换过程中,矩形弹跳起来的状态
![](http://i.imgur.com/UPXADq6.jpg)
有空的话,我们可以不断想新的样式
例子:
```xml
	<me.relex.circleindicator.CircleIndicator
		android:id="@+id/ci"
		android:layout_width="match_parent"
		android:layout_height="40dp"
		android:background="#4000"
		app:ci_animator="@animator/ci_in"
		app:ci_animator_reverse="@animator/ci_out"
		app:ci_drawable="@drawable/ci_selected"
		app:ci_drawable_unselected="@drawable/ci_unselected"
		app:ci_height="10dp"
		app:ci_margin="8dp"
		app:ci_width="10dp"
		/>

	<me.relex.circleindicator.CircleIndicator
		android:id="@+id/ci2"
		android:layout_width="match_parent"
		android:layout_height="40dp"
		android:background="#4000"
		app:ci_animator="@animator/ci_in_2"
		app:ci_animator_reverse="@animator/ci_out_2"
		app:ci_drawable="@drawable/ci_selected_2"
		app:ci_drawable_unselected="@drawable/ci_unselected_2"
		app:ci_height="4dp"
		app:ci_margin="8dp"
		app:ci_width="10dp"
		/>
```
对应的drawable和animator,请到E:\Android\0711笔记\常用项目\开源项目MD\CircleIndicator去找

## <a name="ViewPagerTransforms"></a>ViewPagerTransforms ##
github: https://github.com/ToxicBakery/ViewPagerTransforms
## <a name="ShowcaseView"></a>ShowcaseView ##
github: https://github.com/amlcurran/ShowcaseView
代码这里就不贴了,感觉这个项目明明可以有更多的定制行为,但是作者并没有公开,所以需要自己再去摸索一下,如果想要一些好看一点的效果

## <a name="MaterialShowcaseView"></a>MaterialShowcaseView ##
github: https://github.com/deano2390/MaterialShowcaseView
使用起来相当简单:
```java
/*		// single example
new MaterialShowcaseView.Builder(this)
	.setTarget(run)
	.setDismissText("GOT IT")
	.setContentText("Click this button to run transition")
	.setDelay(100) // optional but starting animations immediately in onCreate can make them choppy
	.setDismissOnTouch(true)
	//.singleUse(1) // provide a unique ID used to ensure it is only shown once
	.show();
*/


ShowcaseConfig config = new ShowcaseConfig();
config.setDelay(500);
MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
//sequence.setConfig(config);
sequence.addSequenceItem(findViewById(R.id.run),
	"This is button one", "GOT IT");
sequence.addSequenceItem(findViewById(R.id.run2),
	"This is button two", "GOT IT");
sequence.addSequenceItem(findViewById(R.id.run3),
	"This is button three", "GOT IT");
sequence.start();
```
![](https://camo.githubusercontent.com/b72d79c013305ad20ef510af50abc6b12b721999/687474703a2f2f692e696d6775722e636f6d2f51494d59524a682e706e67)
![](https://camo.githubusercontent.com/0d29b7fb974a5e018a4d627c91d349fbbeadd57b/687474703a2f2f692e696d6775722e636f6d2f724648454e677a2e676966)





## <a name="android-segmented-control"></a>android-segmented-control ##
github: https://github.com/hoang8f/android-segmented-control
这个吧,我觉得完全可以自己做吧?
图片:
![](https://raw.githubusercontent.com/hoang8f/android-segmented-control/master/screenshot/screenshot3.png)





## <a name="MaterialProgressBar"></a>MaterialProgressBar ##
github: https://github.com/DreaminginCodeZH/MaterialProgressBar
既可以使用它提供的组件MaterialProgressBar也可以使用原生的PrograssBar,原生的ProgressBar要配合这个项目提供的Drawable进行使用
注意android:tint是用于配置进度条的颜色的
图片: ![](http://i.imgur.com/7z7JReO.jpg)
```xml
<me.zhanghai.android.materialprogressbar.MaterialProgressBar
	style="@style/Widget.MaterialProgressBar.ProgressBar.Horizontal"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:indeterminate="true"
	android:tint="#0f0"
	app:mpb_progressStyle="horizontal"
	app:mpb_tintMode="src_in"/>

<LinearLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:orientation="horizontal"
	>

	<me.zhanghai.android.materialprogressbar.MaterialProgressBar
		style="@style/Widget.MaterialProgressBar.ProgressBar"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		android:tint="#00f"
		app:mpb_progressStyle="circular"
		/>

	<me.zhanghai.android.materialprogressbar.MaterialProgressBar
		style="@style/Widget.MaterialProgressBar.ProgressBar.Large"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		app:mpb_progressStyle="circular"
		/>

	<me.zhanghai.android.materialprogressbar.MaterialProgressBar
		style="@style/Widget.MaterialProgressBar.ProgressBar.Large.NoPadding"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		app:mpb_progressStyle="circular"
		/>

	<me.zhanghai.android.materialprogressbar.MaterialProgressBar
		style="@style/Widget.MaterialProgressBar.ProgressBar.Small"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		app:mpb_progressStyle="circular"
		/>

	<me.zhanghai.android.materialprogressbar.MaterialProgressBar
		style="@style/Widget.MaterialProgressBar.ProgressBar.Small.NoPadding"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		app:mpb_progressStyle="circular"
		/>

</LinearLayout>

<LinearLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:orientation="horizontal"
	>

	<ProgressBar
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		/>
	<ProgressBar
		android:id="@+id/pb1"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:indeterminate="true"
		style="@style/Widget.MaterialProgressBar.ProgressBar"
		/>
</LinearLayout>
```

## <a name="materialish-progress"></a>materialish-progress ##
github: https://github.com/pnikosis/materialish-progress
图片 ![](http://i.imgur.com/C2gvS5g.jpg)
```xml
<com.pnikosis.materialishprogress.ProgressWheel
	android:id="@+id/progress_wheel"
	android:layout_width="200dp"
	android:layout_height="200dp"
	android:background="#4f00" 这里仅仅只是为了标出200*200的范围而已
	android:layout_centerHorizontal="true"
	android:layout_centerVertical="true"
	app:matProg_circleRadius="118dp" 就是圆环的半径,但是不太明确他具体是怎么算的,因为我这样设置并不会让圆环刚好填满
	app:matProg_barWidth="18dp" 就是有效部分的宽度
	app:matProg_rimColor="#0f0" 无效部分的颜色
	app:matProg_rimWidth="6dp" 无效部分的宽度
	app:matProg_fillRadius="false" 是否填充满整个布局
	app:matProg_barColor="#5588FF" 有效部分的颜色
	app:matProg_progressIndeterminate="true" />
```

## <a name="CircleProgressBar"></a>CircleProgressBar ##
github: https://github.com/lsjwzh/MaterialLoadingProgressBar
图片: ![](http://i.imgur.com/e4VAWqo.jpg)
```xml
<com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
	android:id="@+id/progressBar"
	android:layout_width="40dp"
	android:layout_height="40dp"
	app:mlpb_show_arrow="true"
	app:mlpb_arrow_height="4dp"
	app:mlpb_arrow_width="6dp"
	app:mlpb_enable_circle_background="true"
	app:mlpb_progress_stoke_width="2dp"
    app:mlpb_progress_color="#f00"
	/>
```

## <a name="GridPasswordView">GridPasswordView</a> ##
github: https://github.com/Jungerr/GridPasswordView
```xml
<com.jungly.gridpasswordview.GridPasswordView
    android:id="@+id/pswView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" 
    app:textColor="#2196F3"
    app:textSize="25sp"
    app:lineColor="#2196F3"
    app:lineWidth="1dp"
    app:gridColor="#ffffff"
    app:passwordLength="6"
    app:passwordTransformation="$"
    app:passwordType="numberPassword / textPassword / textVisiblePassword / textWebPassword"/>
```
## <a name="ShadowLayout"></a>ShadowLayout ##
github: https://github.com/dmytrodanylyk/shadow-layout
注意最终的大小会改变,因为阴影也被算在了最终的大小里
所以大小=Buttond额大小+阴影半径(shadowRadius)
至于cornerRadius是用于调整阴影本身的圆角的
你可以想象阴影本身是一个圆角矩形
```xml
<com.dd.ShadowLayout
	android:id="@+id/sl"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	app:sl_cornerRadius="10dp"
	app:sl_dx="0dp"
	app:sl_dy="0dp"
	app:sl_shadowColor="#4f00"
	app:sl_shadowRadius="4dp"
	>
	<Button
		android:id="@+id/btn4"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:text="GRID MENU"
		/>
</com.dd.ShadowLayout>
```
	

## <a name="DropDownMenu"></a>DropDownMenu ##
github: https://github.com/JayFang1993/DropDownMenu
<img src="https://raw.githubusercontent.com/JayFang1993/DropDownMenu/master/screenshot.gif" height="400"/>
用起来还是挺简单的,但是感觉稍微有点延迟,并且可以定制的东西不多

## <a name="Droppy"></a>Droppy ##
github: https://github.com/shehabic/Droppy
![](http://i.imgur.com/kJP2pt1.jpg)
不足:没有办法定制方向,不过好像一般都是向下吧!?
其实还是有办法可以设置弹出方向的,详情去看show方法,跟后一直跟踪,里面有一个地方是在进行定位的,然后可能的话需要重写这个方法,不过由于这个Popup是使用Builder方式建立的 所以...挺麻烦的看来
	
```java
@OnClick(R.id.btn3)
public void onClick3(View v) {
	DroppyMenuPopup.Builder b = new DroppyMenuPopup.Builder(this, v);
	b.triggerOnAnchorClick(false);//为false,不然会替换到anchor(在这里也就是v)的OnClickListener

	b.addMenuItem(new DroppyMenuItem("我是title"))
		.addMenuItem(new DroppyMenuItem2("item2", R.drawable.ic_launcher))
		.addSeparator();
	b.addMenuItem(new DroppyMenuCustomItem(R.layout.dialog_2).setClickable(true));//自定义的view
	b.setOnClick(new DroppyClickCallbackInterface() {
		@Override
		public void call(View v, int id) {
			Toast.makeText(MyDialogActivity.this, "CLICK", Toast.LENGTH_SHORT).show();
		}
	});
	DroppyMenuPopup dmp = b.build();
	dmp.show();
}
```


## <a name="Context-Menu"></a>Context-Menu ##
github: https://github.com/Yalantis/Context-Menu.Android


## <a name="ExpandableLayout"></a>ExpandableLayout ##
github: https://github.com/AAkira/ExpandableLayout
>有一些不足:
- 不能够知道进度
	- 但是可以自己根据ValueAnimator,当前高度,总的高度来判断进度
- 参数只有set方法,没有get方法...可以考虑自己clone一个进行修改

例子
```java
public static class F3 extends Fragment {
	private View mRootView;
	@Bind(R.id.expandableLayout)
	ExpandableRelativeLayout layout;
	@Bind(R.id.icon)
	ImageView icon;
	private Drawable closed;
	private Drawable opened;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_3, container, false);
			ButterKnife.bind(this, mRootView);
			closed = new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_arrow_drop_down).color(Color.BLACK).sizeDp(24);
			opened = new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_arrow_drop_up).color(Color.BLACK).sizeDp(24);
			icon.setImageDrawable(closed);
			layout.setListener(new ExpandableLayoutListener() {
				@Override
				public void onAnimationStart() {

				}

				@Override
				public void onAnimationEnd() {

				}

				@Override
				public void onPreOpen() {
					Log.d(TAG, "onPreOpen() called with: " + "");
					ObjectAnimator.ofFloat(icon, "rotation", 0, 180).setDuration(500).start();
				}

				@Override
				public void onPreClose() {
					Log.d(TAG, "onPreClose() called with: " + "");
					ObjectAnimator.ofFloat(icon, "rotation", 180, 360).setDuration(500).start();
				}

				@Override
				public void onOpened() {
					//icon.setImageDrawable(opened);
				}

				@Override
				public void onClosed() {
					//icon.setImageDrawable(closed);
				}
			});
		}
		return mRootView;
	}

	@OnClick(R.id.btn1)
	public void onBtn1Click() {
		layout.toggle();
	}
}
```
布局文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	android:id="@+id/ll"
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:orientation="vertical"
	>

	<LinearLayout
		android:layout_width="match_parent"
		android:layout_height="56dp"
		android:orientation="horizontal"
		>

		<Button
			android:id="@+id/btn1"
			android:layout_width="0dp"
			android:layout_height="56dp"
			android:layout_weight="1"
			android:text="toggle"
			/>

		<ImageView
			android:id="@+id/icon"
			android:layout_width="56dp"
			android:layout_height="56dp"
		    android:padding="16dp"
			/>
	</LinearLayout>

	<com.github.aakira.expandablelayout.ExpandableRelativeLayout
		android:id="@+id/expandableLayout"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		app:ael_duration="500"
		app:ael_expanded="true"
		app:ael_interpolator="linear"
		app:ael_orientation="vertical">

		<TextView
			android:id="@+id/text"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:text="sample"/>

		<ImageView
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_below="@id/text"
			android:adjustViewBounds="true"
			android:scaleType="centerCrop"
			android:src="@mipmap/kurumi"
			/>


	</com.github.aakira.expandablelayout.ExpandableRelativeLayout>
</LinearLayout>
```

## <a name="BottomSheet"></a>BottomSheet ##
github: https://github.com/Flipboard/bottomsheet
使用BottomSheetLayout(是一个FrameLayout)将你的内容包裹住
然后在需要展示BottomView的时候调用
bottomSheet.showWithSheetView(yourBottomView);
就OK了,这样就好像是从底部弹出一个对话框,屏幕会被盖上一层灰色,让你点击灰色部分,bottomView会自动消失
目前可以定制的东西并不多
例子代码:
```java
//自定义
@OnClick(R.id.btn1)
public void onBtn1Click() {
	bsl.showWithSheetView(getLayoutInflater(null).inflate(R.layout.bottom_1, bsl, false));
}

@OnClick(R.id.btn2)
public void onBtn2Click() {//弹出一个LIST形式的菜单
	MenuSheetView menuSheetView =
		new MenuSheetView(getContext(), MenuSheetView.MenuType.LIST, "Create...", new MenuSheetView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).
					show();
				if (bsl.isSheetShowing()) {
					bsl.dismissSheet();
				}
				return true;
			}
		});
	menuSheetView.inflateMenu(R.menu.menu_1);
	bsl.showWithSheetView(menuSheetView);
}

@OnClick(R.id.btn3)
public void onBtn3Click() {//弹出一个GRID形式的菜单
	MenuSheetView menuSheetView =
		new MenuSheetView(getContext(), MenuSheetView.MenuType.GRID, "Create...", new MenuSheetView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).
					show();
				if (bsl.isSheetShowing()) {
					bsl.dismissSheet();
				}
				return true;
			}
		});
	bsl.setShouldDimContentView(false);//不要变灰色
	menuSheetView.inflateMenu(R.menu.menu_1);
	bsl.showWithSheetView(menuSheetView, new BaseViewTransformer() {
		@Override
		public void transformView(float translation, float maxTranslation, float peekedTranslation, BottomSheetLayout parent, View view) {
			ViewCompat.setTranslationY(view, -translation / 2);
			float fraction = translation / maxTranslation;
			//ViewCompat.setAlpha(parent.getContentView(), 1 - 0.5f * fraction);
			ViewCompat.setScaleX(parent.getContentView(), 1 - 0.2f * fraction);
			ViewCompat.setScaleY(parent.getContentView(), 1 - 0.2f * fraction);
		}
	});
}

@OnClick(R.id.btn4)
public void onBtn4Click() {//intent
	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
	IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(getContext(), intent, "用浏览器打开...", new IntentPickerSheetView.OnIntentPickedListener() {
		@Override
		public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
			bsl.dismissSheet();
			Toast.makeText(getContext(), activityInfo.label, Toast.LENGTH_SHORT).show();
		}
	});
	intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
		@Override
		public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
			return lhs.componentName.getPackageName().compareTo(rhs.componentName.getPackageName());
		}
	});

	//过滤器
	intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
		@Override
		public boolean include(IntentPickerSheetView.ActivityInfo info) {
			//return info.componentName.getPackageName().toLowerCase().contains("tencent");
			return true;
		}
	});
	//bsl.showWithSheetView(intentPickerSheet);
	bsl.showWithSheetView(intentPickerSheet, new BaseViewTransformer() {
		@Override
		public void transformView(float translation, float maxTranslation, float peekedTranslation, BottomSheetLayout parent, View view) {
			float fraction = translation / maxTranslation;
			ViewCompat.setAlpha(parent.getContentView(), 1 - 0.5f * fraction);
			ViewCompat.setScaleX(parent.getContentView(), 1 - 0.2f * fraction);
			ViewCompat.setScaleY(parent.getContentView(), 1 - 0.2f * fraction);
			//Log.d(TAG, "transformView() called with: " + "translation = [" + translation + "], maxTranslation = [" + maxTranslation + "], peekedTranslation = [" + peekedTranslation + "], parent = [" + parent + "], view = [" + view + "]");
		}
	}, new OnSheetDismissedListener() {
		@Override
		public void onDismissed(BottomSheetLayout bottomSheetLayout) {

		}
	});
}
```





## <a name="android-percent-support-extend"></a>android-percent-support-extend ##
github: 	https://github.com/hongyangAndroid/android-percent-support-extend
百分比布局

















## <a name="ArcLayout"></a>ArcLayout ##
> github: https://github.com/ogaclejapan/ArcLayout
> 可以实现圆形的布局

### 对于ArcLayout有这些属性 ###
属性|描述
:--|:--
arc_origin|设置原点在哪里,有,top,bottom,left,right,center_vertical,center_horizontal,center,start,end
arc_color|绘制出的区域的颜色
arc_radius|半径的大小
arc_axisRadius|子元素的圆心所构成的圆的半径大小
arc_freeAngle|默认是false,一般来说子元素所在的位置都是平均分配的,如果设置了true,那你就可以自由分配每个子元素处于哪个角度
arc_reverseAngle|是否反向
### 对于其子元素有这些属性 ###
属性|描述
:--|:--
arc_origin|子元素的哪个点踩在axisRadius指定的圆上
arc_angle|首先这个角度是顺时针的,子元素所在的角度,注意这个角度会根据ArcLayout的arc_origin的变化而变化,比如如果ArcLayout的arc_orign为bottom,那么大小为0的位置对应了对应了数学坐标轴上的180°方向

这里有图可以帮助理解
![](https://raw.githubusercontent.com/ogaclejapan/ArcLayout/master/art/attrs.png)

下面是一个例子
```xml
	<RelativeLayout>
		<com.ogaclejapan.arclayout.ArcLayout
			android:layout_width="match_parent"
			android:layout_height="match_parent"
			android:layout_centerInParent="true"
			app:arc_origin="bottom"
			app:arc_color="#4D000000"
			app:arc_radius="168dp"
			app:arc_axisRadius="120dp"
			app:arc_freeAngle="true"
			app:arc_reverseAngle="false"
			>
	
			<Button
				android:text="A"
				app:arc_origin="center"
			    	app:arc_angle="30"
				/>
	
			<Button
				android:text="B"
				app:arc_origin="center"
				app:arc_angle="45"
				/>
	
			<Button
				android:text="C"
				app:arc_origin="bottom"
				app:arc_angle="60"
				/>
	
		</com.ogaclejapan.arclayout.ArcLayout>
	</RelativeLayout>
```
**它的结果是这样:**
![](http://i.imgur.com/IkKGZPa.jpg)
**注意:** 对于C,它的arc_origin为bottom,也就是说他的bottom和其他两个元素的center是在同一个圆上的


