# 配置 #
添加下面代码到classpath下
太高的版本好像反而不行...
```gradle
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
```
添加下面代码到app的dependencies
```gradle
apply plugin:'android-apt'
apt {
    arguments {
        androidManifestFile variant.outputs[0].processResources.manifestFile
    }
}
apt "org.androidannotations:androidannotations:3.3.2"
compile "org.androidannotations:androidannotations-api:3.3.2"
```
接着还要注意在清单文件中的要带下划线MainActivity_
记得parentActivityName也要下划线!
就这样看来的话 侵略性还是蛮大的

https://github.com/excilys/androidannotations/wiki/AvailableAnnotations

# rest #
https://github.com/excilys/androidannotations/wiki/Rest-API
https://github.com/excilys/androidannotations/wiki/Authenticated-Rest-Client


# 首要注意事项 #
多数增强会产生一个类 比如 MainActivity_,这个类会新增很多的静态方法,让你操作起来更方便.
当我们在程序中需要直接new一个增强的对象的时候,一定要使用带下划线的,否则就没有被增强了!

# @EActivity #
可以接受一个参数表示layoutId或者留空(那你就要重载onCreate函数,然后自己setContentView)

# @EFragment #
支持v4包和普通包的Fragment,跟Activity差不多.
可以给一个layotuId,单子还有在onCreateView返回null的时候才会使用它
new MyFragment_();
MyFragment_.builder().xxx().build();

# @EBean #
对普通的类进行增强,要求这些Bean有一个无参的构造函数
在该增强的类中,可以使用AA的大部分注解,有些注解是无法使用的,这点很好想通.
@EBean(scope= EBean.Scope.Singleton)
通过scope可以指定为单例或者普通(就是每次都new一个)

# @Bean #
注入bean,可以接受一个参数,表示实现类
@Bean(MyImplementation.class)
MyInterface myInterface;

# 注入资源 #
@StringRes @ColorRes @AnimationRes
@DimensionPixelOffsetRes
@DimensionPixelSizeRes
@Integer Boolean ColorStateList
@Drawable IntArray Layout Moive TextArray StringArray
**@HtmlRes**
```
<string name="hello_html"><![CDATA[Hello <b>World</b>!]]></string>
@HtmlRes(R.string.hello_html)
Spanned myHelloString;
```
**@FromHtml**: 与ViewById一起使用,可以将其内容注入到一个TextView里
@ViewById(R.id.my_text_view)
@FromHtml(R.string.hello_html)
TextView textView;

# @ViewById #
可以加在View上,或List上(注入多个)
# @AfterViews #
	在ViewById注入完毕之后
# @AfterInject #
在注入完之后执行,如果有父类,并且父类也有AfterInect,最好函数别重名!

# OptionsMenu #
加在Activity或Fragment上
@OptionsMenu(R.menu.main)
 @OptionsMenuItem(R.id.search)
 MenuItem menuSearch;
```
@OptionsItem(R.id.menuShare)
boolean myMethod() {
  // You can specify the ID in the annotation, or use the naming convention
  return true;
}
@OptionsItem
void menu_add(MenuItem item) {
  // You can add a MenuItem parameter to access it
}
@InjectMenu
Menu menu;
```

# @RootContext #
这个比较特殊:**像下面这样写只有当RootContext是一个Activity的时候才会进行注入**
@RootContext Activity activity;

# @IntentService #
	要和@ServiceAction配合使用
	
# 线程操作 #
@UiThread
@Background(id="t1")
BackgroundExecutor.cancelAll("t1", mayInterruptIfRunning);
 UiThreadExecutor.cancelAll("cancellable_task");
@Backgroudn还可以接受一个参数,serial = "一个名字"
名字相同的background会被串行执行
还支持delay,延迟一段时间再执行
@SupposeBackground:如果不是由后台线程调用,那么就抛出异常
@SupposeUiThread: 类似上面
fork和join:
"https://github.com/excilys/androidannotations/wiki/Fork Join"
这里介绍了一个fork和join的例子
你在UiThread里,并行执行2个动作,然后这2个动作的结果要汇总起来
有了UiThread和Background就可以很容易做到AsyncTask的效果了

@NonConfigurationInstance
onRetainNonConfigurationInstance

@InstanceState
	这些东西会在onSaveInstanceState(Bundle)的时候被保存
@OnActivityResult(REQUEST_CODE)

# SharedPreference相关 #
https://github.com/excilys/androidannotations/wiki/SharedPreferencesHelpers
@SharedPref(value=SharedPref.Scope.UNIQUE) 确保这是唯一一个SP,否则对于不同的Activity来说,打开的SP是不一样的,因为生成名字的时候会带上activity的前缀?scope还有其他的值

@SharedPref @DefaultRes @Pref
@DefaultInt(42) @DefaultString("John")
```java
@SharedPref
public interface MyPrefs {
	// The field name will have default value "John"
	@DefaultString("John")
	String name();
	
	// The field age will have default value 42
	@DefaultInt(42)
	int age();
	
	// The field lastUpdated will have default value 0
	long lastUpdated();
}
```

# Preference相关 #
https://github.com/excilys/androidannotations/wiki/PreferenceHelpers
@PreferenceScreen @PreferenceByKey @AfterPreferences
@PreferenceChange @PreferenceClick @PreferenceHeaders

	
# 简单 #
@EService @Application @App @SystemService
@EProvider

# 事件 #
@Click @ItemClick @ItemLongClick @ItemSelect
@SeekBarProgressChange @SeekBarTouchStart @SeekBarTouchStop
@TextChange @BeforeTextChange @AfterTextChange @EditorAction @KeyDown @KeyUp @KeyLongPress @KeyMultiple
**去查看这些annotation的源代码,可以获得其支持的函数签名**

# 杂 #
https://github.com/excilys/androidannotations/wiki/AvailableAnnotations
@RestService @AfterExtras @WindowFeature @Fullscreen
@OnActivityResult @OnActivityResult.Extra

# 对OrmLite的支持 #
https://github.com/excilys/androidannotations/wiki/Ormlite
@OrmLiteDao

# @Receiver #
直接加在Activity或Service上,方便使用
```java
@Receiver(actions = "org.androidannotations.ACTION_1")
protected void onAction1() {
}
```  

# @EReceiver #
对广播接受者进行增强
@ReceiverAction
	加在方法上,默认情况下,方法名就是对应的要处理的action
	该方法可以有Context,intent,
```java
@ReceiverAction("BROADCAST_ACTION_NAME")
void mySimpleAction(Intent intent) {
    // ...
}
@ReceiverAction({"MULTI_BROADCAST_ACTION1", "MULTI_BROADCAST_ACTION2"})
void anotherAction(@ReceiverAction.Extra("specialExtraName") String valueString, @ReceiverAction.Extra long valueLong) {
    // ...
}
@ReceiverAction(actions = android.content.Intent.VIEW, dataSchemes = "http")
@ReceiverAction(actions = android.content.Intent.VIEW, dataSchemes = {"http", "https"})
```
