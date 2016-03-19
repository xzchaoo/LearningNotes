package org.xzc.learnmaterialdrawer;

import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class MenuActivity extends BaseActivity {
	@Override
	protected void createDrawerItems(DrawerBuilder db) {
		db.inflateMenu(R.menu.menu_1);
		//super.createDrawerItems(db);
	}
}
