package org.xzc.learnmaterialdrawer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryToggleDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xzchaoo on 2015/11/5 0005.
 */
public abstract class BaseActivity extends AppCompatActivity {

	@Bind(R.id.toolbar)
	Toolbar toolbar;
	Drawer drawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		createDrawer();
	}

	protected int getLayoutId() {
		return R.layout.activity_main;
	}

	protected static final int IDENTIFIER_COMPACT_STYLE = 1;
	protected static final int IDENTIFIER_BELOW_ACTIONBAR = 2;
	protected static final int IDENTIFIER_LEFT_AND_RIGHT_DRAWER = 3;
	protected static final int IDENTIFIER_BELOW_STATUS_BAR = 4;
	protected static final int IDENTIFIER_INSERT = 5;
	protected static final int IDENTIFIER_MENU = 6;
	protected static final int IDENTIFIER_MINI = 7;
	protected static final int IDENTIFIER_ABOUT = 8;

	protected void createDrawerItems(DrawerBuilder db) {
		db.addDrawerItems(
			new SectionDrawerItem().withName("第一节"),
			new PrimaryDrawerItem().withName("CompactStyle的AccountHeader").withDescription("描述1").withBadge("Hot")
				.withBadgeStyle(new BadgeStyle().withColor(Color.RED).withTextColor(Color.WHITE)).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_COMPACT_STYLE),
			new SwitchDrawerItem().withName("在ActionBar之下").withDescription("牛逼功能").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_BELOW_ACTIONBAR),
			new ToggleDrawerItem().withName("左右都有抽屉").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_LEFT_AND_RIGHT_DRAWER),
			new ToggleDrawerItem().withName("在StatusBar之下").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_BELOW_STATUS_BAR),
			new ToggleDrawerItem().withName("内嵌").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_INSERT),
			new ToggleDrawerItem().withName("通过menu文件").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_MENU),
			new ToggleDrawerItem().withName("迷你").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_MINI),
			new ToggleDrawerItem().withName("关于").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher).withIdentifier(IDENTIFIER_ABOUT),

			new SectionDrawerItem().withName("第二节"),
			new SecondaryDrawerItem().withName("项 1").withDescription("描述1").withBadge("Cold")
				.withBadgeStyle(new BadgeStyle().withColor(Color.BLUE).withTextColor(Color.WHITE)),
			new SecondarySwitchDrawerItem().withName("项 2").withDescription("牛逼功能").withChecked(true),
			new SecondaryToggleDrawerItem().withName("项 3").withDescription("牛逼功能2").withChecked(true),

			new SectionDrawerItem().withName("第三节"),
			new PrimaryDrawerItem().withName("项 1").withDescription("描述1").withBadge("Hot")
				.withBadgeStyle(new BadgeStyle().withColor(Color.RED).withTextColor(Color.WHITE)).withIcon(R.mipmap.ic_launcher),
			new SwitchDrawerItem().withName("项 2").withDescription("牛逼功能").withChecked(true).withIcon(R.mipmap.ic_launcher),
			new ToggleDrawerItem().withName("项 3").withDescription("牛逼功能2").withChecked(true).withIcon(R.mipmap.ic_launcher)

		);
		db.addStickyDrawerItems(
			new PrimaryDrawerItem().withName("sticky1"), new PrimaryDrawerItem().withName("sticky2")
		);
		if (getClass() == MainActivity.class)
			db.withOnDrawerItemClickListener(onDrawerItemClickListener);
	}

	private Drawer.OnDrawerItemClickListener onDrawerItemClickListener = new Drawer.OnDrawerItemClickListener() {
		@Override
		public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
			switch (drawerItem.getIdentifier()) {
				case IDENTIFIER_COMPACT_STYLE:
					startActivity(new Intent(getBaseContext(), CompactStyleActivity.class));
					return true;
				case IDENTIFIER_BELOW_ACTIONBAR:
					startActivity(new Intent(getBaseContext(), UnderActionBarActivity.class));
					return true;
				case IDENTIFIER_LEFT_AND_RIGHT_DRAWER:
					startActivity(new Intent(getBaseContext(), LeftAndRightDrawerActivity.class));
					return true;
				case IDENTIFIER_BELOW_STATUS_BAR:
					startActivity(new Intent(getBaseContext(), UnderStatusBarActivity.class));
					return true;
				case IDENTIFIER_INSERT:
					startActivity(new Intent(getBaseContext(), InsetActivity.class));
					return true;
				case IDENTIFIER_MENU:
					startActivity(new Intent(getBaseContext(), MenuActivity.class));
					return true;
				case IDENTIFIER_MINI:
					startActivity(new Intent(getBaseContext(), MiniActivity.class));
					return true;
				case IDENTIFIER_ABOUT:
					//startActivity(new Intent(getBaseContext(), AboutActivity.class));
					new LibsBuilder()
						//provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
						.withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
						.withAboutAppName("关于...")
						.withAboutIconShown(true)
						.withAboutDescription("描述")
						.withAboutVersionShown(true)
						.withFields(R.string.class.getFields())
						.withSortEnabled(true)
							//start the activity
							//.withAboutIconShown()
						.start(BaseActivity.this);
					return true;
			}
			return false;
		}
	};


	protected DrawerBuilder createDrawerBuilder() {
		DrawerBuilder db = new DrawerBuilder();
		db.withActivity(this)
			//.withInnerShadow(true)
			//.withDisplayBelowStatusBar(true)
			//.withTranslucentStatusBarShadow(true)
			.withActionBarDrawerToggleAnimated(true)
				//.withHeader(R.layout.header_1)
				//.withStickyHeader(R.layout.header_1)
				//.withStickyFooter(R.layout.header_1)
				//.withHeader(R.layout.header_1)
				//.withStickyHeader(R.layout.header_1)
			.withToolbar(toolbar)
			.withAccountHeader(
				createAccountHeaderBuilder().build()//, false
			)
		;
		createDrawerItems(db);
		return db;
	}

	protected void createDrawer() {
		drawer = createDrawer(createDrawerBuilder());
		//drawer.setHeader(getLayoutInflater().inflate(R.layout.header_1, null, false));

		//System.out.println(drawer.getHeader());
	}

	protected Drawer createDrawer(DrawerBuilder db) {
		return db.build();
	}

	protected AccountHeaderBuilder createAccountHeaderBuilder() {
		return new AccountHeaderBuilder().
			withActivity(this)
			.withHeaderBackground(R.mipmap.kurumi)
			.withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
			.addProfiles(
				new ProfileDrawerItem().withName("xzchaoo").withEmail("70862045@qq.com").withIcon(R.mipmap.chino).withIdentifier(1),
				new ProfileDrawerItem().withName("ABC1").withEmail("abc@qq.com").withIcon(R.mipmap.ic_launcher).withIdentifier(2),
				new ProfileDrawerItem().withName("ABC2").withEmail("abc@qq.com").withIcon(R.mipmap.ic_launcher).withIdentifier(2),
				new ProfileDrawerItem().withName("ABC3").withEmail("abc@qq.com").withIcon(R.mipmap.ic_launcher).withIdentifier(2)
			)
				//.withThreeSmallProfileImages(true)
				//.withProfileImagesClickable(false)
			.withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
				@Override
				public boolean onClick(View view, IProfile profile) {
					Toast.makeText(BaseActivity.this, "你点击了(name,email)", Toast.LENGTH_SHORT).show();
					//返回true的话 消费掉此事件 将会导致List不会展开
					return true;
				}
			})
			.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
				@Override
				public boolean onProfileChanged(View view, IProfile profile, boolean current) {
					Toast.makeText(BaseActivity.this, "你点击了" + profile.getName(), Toast.LENGTH_SHORT).show();
					return false;
					//return true;
				}
			})
			//.withPaddingBelowHeader(false)
			//.withDividerBelowHeader(false)
			//.withOnlyMainProfileImageVisible(true)
			//.withSelectionFirstLine("FIRST")
			//.withSelectionSecondLine("SECOND")
			//.withCurrentProfileHiddenInList(true)
			//.withAlternativeProfileHeaderSwitching(false)
			//.withSelectionListEnabledForSingleProfile(false)
			;
	}

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen()) {
			drawer.closeDrawer();
		} else {
			super.onBackPressed();
		}
	}
}
