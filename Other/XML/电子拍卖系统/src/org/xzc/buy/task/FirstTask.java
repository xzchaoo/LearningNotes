package org.xzc.buy.task;

import java.util.TimerTask;

public class FirstTask extends TimerTask {
	public void run() {
		try {
			System.out.println( "开始睡觉" );
			Thread.sleep( 5000 );
			System.out.println( "睡觉结束" );
		} catch (Exception e) {
		}
	}
}
