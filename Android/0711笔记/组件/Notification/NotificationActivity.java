package com.example.learn0711;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

//http://www.2cto.com/kf/201502/376750.html
@ContentView(R.layout.activity_notification)
public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		ViewUtils.inject( this );
	}

	@OnClick(R.id.button1)
	public void onClick1(View v) {
		Builder b = new Builder( this );
		b.setSmallIcon( android.R.drawable.ic_delete ).setContentText( "你有条新的消息,请注意查收." );
		b.setContentTitle( "新消息." );
		//当用户点击了这个notification 就让他打开我们的NotificationActivity程序
		//继承树
		Intent i = new Intent( this, NotificationActivity.class );

		//PendingIntent pi = PendingIntent.getActivity( this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
		//下面的这个pi 当你点击回退按钮的时候 它是进入你的继承树 而不是上一个页面(可能这个页面是由别人打开的,比如通过notification)
		PendingIntent pi = TaskStackBuilder.create( this )
		// add all of DetailsActivity's parents to the stack,
		// followed by DetailsActivity itself
				.addNextIntentWithParentStack( i ).getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );

		b.setContentIntent( pi );

		NotificationManager nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
		//70862045所在的位置要填写一个唯一的id
		//这个id用户标识你的这个notification
		//当有两个notification用同一个id进行notify的话后者会盖掉前者
		nm.notify( 70862045, b.build() );

		/*
		android:taskAffinity=""
		Combined with the FLAG_ACTIVITY_NEW_TASK flag that you set in code, this ensures that this Activity doesn't go into the application's default task. Any existing tasks that have the application's default affinity are not affected.
		android:excludeFromRecents="true"
		Excludes the new task from Recents, so that the user can't accidentally navigate back to it.

		 * */
	}

	@OnClick(R.id.button2)
	public void onClick2(View v) {
		Builder b = new Builder( this );
		b.setSmallIcon( android.R.drawable.ic_delete ).setContentText( "你有条新的消息,请注意查收." );
		b.setContentTitle( "新消息." );
		//当用户点击了这个notification 就让他打开我们的NotificationActivity程序
		//继承树
		Intent i = new Intent( this, E1Activity.class );
		i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

		PendingIntent pi = PendingIntent.getActivity( this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
		b.setContentIntent( pi );

		NotificationManager nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
		//70862045所在的位置要填写一个唯一的id
		//这个id用户标识你的这个notification
		//当有两个notification用同一个id进行notify的话后者会盖掉前者
		b.setNumber( 77 );
		nm.notify( 70862046, b.build() );
	}
	
	@OnClick(R.id.button3)
	public void onClick3(View v) {
		Toast.makeText( this, "大view", Toast.LENGTH_SHORT ).show();
		Intent i=new Intent(this,MyIntentService.class);
		i.setAction( "ping" );
		i.putExtra( "msg", "我是消息" );
		startService( i );
	}
}
