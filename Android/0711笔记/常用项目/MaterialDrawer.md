# MaterialDrawer #
https://github.com/mikepenz/MaterialDrawer
minSDK 10
一个很棒的抽屉

compile('com.mikepenz:materialdrawer:4.4.5@aar') {
    transitive = true
}

# 几种类型 #
1. 和StatusBar上对齐
2. 在StatusBar之下, 只需要添加.withTranslucentStatusBar(false)就行了
3. 在Toolbar之下
4. 内嵌 调用Builder的buildView方法后,再使用getSlider()就可以获得一个View,而这个View就是抽屉的那个列表
5. 左右都有抽屉(少见) 只要是调用append方法
6. 支持Menu文件 调用Builder的inflateMenu方法即可,配合withOnDrawerItemClickListener使用
7. 迷你

# MiniDrawer #
跟往常一样把抽屉建立好,但是最后一步是:result = builder.buildView();
然后miniResult = new MiniDrawer().withDrawer(result).withInnerShadow(true).withAccountHeader(headerResult);


# 核心类 #

## 继承树 ##
1. DrawerBuilder Builder模式
2. Drawer
3. IDrawerItem 用于表示一个行样式,由具体的实现类来负责
	1. AbstractDrawerItem 对IDrawerItem的很多方法做了实现
		1. DividerDrawerItem 提供分隔符功能
		2. SectionDrawerItem 提供"节"的功能
		3. BaseDrawerItem 支持icon,name,textColor(多种状态),typeface
			1. BasePrimaryDrawerItem 支持description
				1. PrimaryDrawerItem 支持badge
				2. SwitchDrawerItem 提供Switch支持
				3. TogglePrimaryItem 提供Toggle功能,Toggle似乎没有很好支持,很丑
			2. BaseSecondaryDrawerItem 带Secondary就是比较小一点而已.并且默认颜色是灰色
				1. SecondarySwitchDrawerItem
				2. SecondaryToggleDrawerItem
				3. SecondaryDrawerItem
		4. ProfileDrawerItem (name,email,icon)
4. AccountHeaderBuilderAccountHeader


ProfileSettingDrawerItem


## AccountHeaderBuilder ##
可以设置的项: 背景,背景缩放方式, 简化模式(withCompactStyle) 
各种text的Typeface,各种text的颜色整个Header的高度
添加Profile 支持SavedInstance
AccountHeader还有一项功能就是支持 ProfileList(当前可用的用户的列表)
将List里的当前用户隐藏 withCurrentProfileHiddenInList
当只有单个用户的时候不显示List withSelectionListEnabledForSingleProfile
控制是否启动ProfileList withSelectionListEnabled
HEADER下的PADDING, withPaddingBelowHeader
HEADER下的Divider withDividerBelowHeader
是否要状态栏透明化(这样抽屉就会占用状态栏的位置) mTranslucentStatusBar
头像是否可见(默认情况下点击头像就会关闭抽屉) withProfileImagesVisible
是否只显示主头像(第二个和第三个头像会被隐藏)mOnlyMainProfileImageVisible
是否点击了ProfileList的某一项之后抽屉自动关闭 withCloseDrawerOnProfileListClick
强制覆盖NAME所在的位置 withSelectionFirstLineShown
强制覆盖EMAIL所在的位置 withSelectionSecondLineShown
头像是否可以点击 withProfileImagesClickable
显示3个小头像 withThreeSmallProfileImages
当点击在由(name,email)构成的那个View上时: 返回false 可以阻止List打开. withOnAccountHeaderSelectionViewClickListener
自定义view,要求结构和drawer.xml一样 withAccountHeader
当点击了头像的时候 返回false可以组织抽屉关闭: withOnAccountHeaderListener
不知道什么鬼:
withAlternativeProfileHeaderSwitching

## AccountHeader ##
可以拿到最终的View 拿到背景(是一个ImageView)
修改背景
让ProfileList显示/隐藏
很多在Builder里的选项都可以设置
添加/删除/获取/设置/更新的Profile
清空
保存状态 saveInstanceState



自定义View : withCustomView

感觉好像没有效果啊 :
withInnerShadow


## DrawerBuilder ##
设置AccountHeader,
是否要在ActionBar上生成汉堡包图标(菜单图标)
侧滑方向withDrawerGravity
与Toolbar整合,自动菜单
设置要容纳DrawerLayout的容器 rootView
withAccountHeader 可以将头固定住
addStickyDrawerItems可以添加固定在Footer处的DrawerItem
将SB透明化 withTranslucentStatusBar
将抽屉放在SB的Bottom之下 withDisplayBelowStatusBar(即使withTranslucentStatusBar被设置了)
这个不知道该怎么即使,设置为true之后,StatusBar会很明显地站在抽屉之上,虽然是半透明状态,可以去试一下就知道效果了. withTranslucentStatusBarShadow
修改Slider的背景 withSliderBackgroundColor/withSliderBackgroundDrawable
抽屉宽度 withDrawerWidth
抽屉位置(LEFT,RIGHT,START,END) withDrawerGravity
withActionBarDrawerToggleAnimated
withActionBarDrawerToggle
withScrollToTopAfterClick
withHeader/withFooter 之类的方法好像无效啊!?
初始化选中项 withSelectedItemByPosition/withSelectedItem
自定义RV withRecyclerView
withHasStableIds
withItemAnimator
addStickyDrawerItems/withStickyDrawerItems
addMenuItems/inflateMenu
withCloseOnClick/withDelayOnDrawerClose
各种Listener
初始化的时候就显示 withShowDrawerOnFirstLaunch
withSavedInstance buildForFragment buildView append

杂
withFullscreen
withSystemUIHidden
withDrawerLayout
withStatusBarColor


## Drawer ##
构建完的结果
可以设置选中项,修改项,删除,打开关闭抽屉
获得底层的DrawerLayout
全屏
可以重新设置很多选项
getScrimInsetsFrameLayout getMaterialize
keyboardSupportEnabled
可以获得产生的View : getSlider
可以获得Content getContent
更新Item/更新Badge/更新Name
重置 切换


显示成后退按钮
result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
显示成菜单按钮
getSupportActionBar().setDisplayHomeAsUpEnabled(false);
result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);





## IDrawerItem ##
可以附着一个tag,是否可用,是否选中,是否可选,类型,布局id,最终显示的view,拿到ViewHolder(本质是由RecyclerView支持的),bindView方法

### SectionDrawerItem ###
	提供了表示一个"节"的功能
	基本上只要提供 name textColor 是否有分隔线 即可
	
## PrimaryDrawerItem ##
```java
new PrimaryDrawerItem()
	.withName("项 1")
	.withDescription("描述1")
	.withBadge("Hot")
	.withBadgeStyle(new BadgeStyle()
		.withColor(Color.RED)
		.withTextColor(Color.WHITE)
	)
```




# 样式 #
有需要的话再去官网上看好了
