package org.xzc.learnmaterialdrawer;

import android.view.Gravity;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class LeftAndRightDrawerActivity extends BaseActivity {

	private Drawer left;

	@Override
	protected DrawerBuilder createDrawerBuilder() {
		left = super.createDrawerBuilder().build();
		DrawerBuilder right = super.createDrawerBuilder().withDrawerGravity(Gravity.RIGHT);
		return right;
	}

	@Override
	protected Drawer createDrawer(DrawerBuilder db) {
		return db.append(left);
	}
}
