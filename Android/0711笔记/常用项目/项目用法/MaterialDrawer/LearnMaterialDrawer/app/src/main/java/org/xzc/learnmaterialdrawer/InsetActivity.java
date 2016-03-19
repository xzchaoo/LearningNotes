package org.xzc.learnmaterialdrawer;

import android.view.ViewGroup;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class InsetActivity extends BaseActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_inset;
	}

	@Override
	protected Drawer createDrawer(DrawerBuilder db) {
		Drawer drawer = db.buildView();
		((ViewGroup)findViewById(R.id.content)).addView(drawer.getSlider());
		return drawer;
	}
}
