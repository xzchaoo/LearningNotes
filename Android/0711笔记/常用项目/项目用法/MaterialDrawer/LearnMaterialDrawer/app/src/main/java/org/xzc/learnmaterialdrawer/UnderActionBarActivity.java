package org.xzc.learnmaterialdrawer;

import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class UnderActionBarActivity extends BaseActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_under_actionbar;
	}

	@Override
	protected DrawerBuilder createDrawerBuilder() {
		return super.createDrawerBuilder().withRootView(R.id.drawer_layout);
	}
}
