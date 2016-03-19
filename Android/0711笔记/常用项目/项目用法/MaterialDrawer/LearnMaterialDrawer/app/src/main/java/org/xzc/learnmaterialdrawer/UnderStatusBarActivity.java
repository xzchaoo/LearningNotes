package org.xzc.learnmaterialdrawer;

import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class UnderStatusBarActivity extends BaseActivity {
	@Override
	protected DrawerBuilder createDrawerBuilder() {
		return super.createDrawerBuilder().withTranslucentStatusBar(false);
	}
}
