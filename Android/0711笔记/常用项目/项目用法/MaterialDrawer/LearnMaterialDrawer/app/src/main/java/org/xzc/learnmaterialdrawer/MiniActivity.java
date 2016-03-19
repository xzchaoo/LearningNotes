package org.xzc.learnmaterialdrawer;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialize.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public class MiniActivity extends AppCompatActivity {
	@Bind(R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mini);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		AccountHeader ah = new AccountHeaderBuilder()
			.withActivity(this)
			.withSelectionListEnabledForSingleProfile(false)
			.addProfiles(new ProfileDrawerItem().withName("xzchaoo").withEmail("70862045@qq.com").withIcon(R.mipmap.chino))
			.build();
		Drawer drawer = new DrawerBuilder()
			.withActivity(this)
			.withToolbar(toolbar)
			.withInnerShadow(true)
			.withAccountHeader(
				ah
			)
			.withSliderBackgroundColorRes(R.color.material_drawer_dark_background)
			.buildView();

		MiniDrawer md = new MiniDrawer().withAccountHeader(ah).withDrawer(drawer).withInnerShadow(true);

		int first = (int) UIUtils.convertDpToPixel(300, this);
		int second = (int) UIUtils.convertDpToPixel(72, this);

		final Crossfader crossFader = new Crossfader()
			.withContent(findViewById(R.id.content))
			.withFirst(drawer.getSlider(), first)
			.withSecond(md.build(this), second)
			.withSavedInstance(savedInstanceState)
			.build();

		md.withCrossFader(new ICrossfader() {
			@Override
			public void crossfade() {
				crossFader.crossFade();
			}

			@Override
			public boolean isCrossfaded() {
				return crossFader.isCrossFaded();
			}
		});

	}
}
