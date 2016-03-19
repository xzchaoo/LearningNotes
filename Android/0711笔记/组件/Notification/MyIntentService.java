package com.example.learn0711;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super( "com.example.learn0711" );
	}

	private void fashe(Intent i, String msg) {
		Intent dissmissItent = new Intent( this, MyIntentService.class );
		dissmissItent.setAction( "dismiss" );
		PendingIntent disIntent = PendingIntent.getService( this, 0, dissmissItent, 0 );
		Intent snoozeIntent = new Intent( this, MyIntentService.class );
		snoozeIntent.setAction( "snooze" );
		PendingIntent snoopIntent = PendingIntent.getService( this, 0, snoozeIntent, 0 );
		
		final Notification.Builder b = new Notification.Builder(this);
		b.setContentTitle( "我是title" );
		b.setContentText( "text" );
		b.setSmallIcon( R.drawable.ic_launcher );
		b.setDefaults( Notification.DEFAULT_ALL );
		b.setStyle( new Notification.BigTextStyle().bigText( msg ) );
		b.addAction( android.R.drawable.ic_delete,"dismiss" ,disIntent);
		b.addAction( android.R.drawable.ic_delete ,"snooze",snoopIntent);
		//滑动不关闭
		b.setOngoing( true );
		//b.setProgress( 100, 77, true );
		Intent resultIntent=new Intent(this,E1Activity.class);
		PendingIntent resultPendingIntent=PendingIntent.getActivity( this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT );
		b.setContentIntent( resultPendingIntent );
		//要使用rv的话 其他一些东西必须关闭 style action 必须不能设置
		RemoteViews rv=new RemoteViews( getPackageName(), R.layout.notification_remote_views);
		rv.setViewVisibility( R.id.button1, View.VISIBLE );
		b.setContent( rv );
		
		new Thread(new Runnable(){
			public void run() {
				for(int i=0;i<100;++i){
					try{
						Thread.sleep( 100 );
					}catch(Exception e){}
					//确定性的进度条
					b.setProgress( 100, i, false );
					Notification n = b.build();
					//n.contentView=rv;
					nm.notify(ID,n);
				}
			}
		}).start();
	}

	private static final int ID = 77;
	private NotificationManager nm;

	@Override
	protected void onHandleIntent(Intent intent) {
		String msg = intent.getStringExtra( "msg" );
		String action = intent.getAction();
		nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
		if ("ping".equals( action )) {
			fashe( intent, msg );
		} else if ("snooze".equals( action )) {
			fashe( intent, "" );
		} else if ("dismiss".equals( action )) {
			nm.cancel( ID );
		}
		/*
		Builder b = new Builder( this );
		b.setSmallIcon( android.R.drawable.ic_delete ).setContentText( "你有条新的消息,请注意查收." );
		b.setContentTitle( "新消息." ).setDefaults( Notification.DEFAULT_ALL );
		b.setStyle( new Notification.BigTextStyle().bigText( "我是很大的msg" ) );
		b.setTicker( "有新消息" );
		//无法滑动关闭
		b.setOngoing( true );
		//发射时间 不设置就是马上b.setWhen( when )
		Intent dismissIntent = new Intent(this, PingService.class);
		dismissIntent.setAction(.ACTION_DISMISS);
		PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

		Intent snoozeIntent = new Intent(this, PingService.class);
		snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
		PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);
		
		b.addAction( android.R.drawable.sym_action_call ,"打开",null);
		b.addAction( android.R.drawable.ic_delete ,"关闭",null);
		
		Intent i = new Intent( this, E1Activity.class );
		PendingIntent pi = PendingIntent.getActivity( this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
		b.setContentIntent( pi );
		NotificationManager nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
		b.setNumber( 77 );
		nm.notify( 70862046, b.build() );*/
	}

}
