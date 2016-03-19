# App Widget 配置方式 #
https://developer.android.com/intl/zh-cn/guide/topics/appwidgets/index.html
1. 添加一个receiver
```xml
<receiver android:name="ExampleAppWidgetProvider" >
	<intent-filter>
		<action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
	</intent-filter>
	<meta-data android:name="android.appwidget.provider" android:resource="@xml/example_appwidget_info" />
</receiver>
```
2. 编写@xml/example_appwidget_info
```xml
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
	android:minWidth="40dp"
	android:minHeight="40dp"
	android:updatePeriodMillis="86400000"
	android:previewImage="@drawable/preview"
	android:initialLayout="@layout/example_appwidget"
	android:configure="com.example.android.ExampleAppWidgetConfigure" 
	android:resizeMode="horizontal|vertical"
	android:widgetCategory="home_screen">
</appwidget-provider>
```
3. @xml/example_appwidget_info里必填的为minWidth minheight initialLayout
4. initialLayout所指定的layout里只能用简单的view,这些view要用于RemoteViews

好的Layout应该带有margin和padding
 Well-designed widgets leave some margins between the edges of the bounding box and the frame, and padding between the inner edges of the frame and the widget's controls.
 SDK>=14的时候 自动有margin
 
