使用FragmentTabHost可以实现简单的Fragment + Tab的结构
一般来说不需要在Fragment之间滑动, 否则就该去使用ViewPager了

```java
package org.xzc.learnmaterialdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentTabHostActivity extends AppCompatActivity {

	@Bind(android.R.id.tabhost)
	FragmentTabHost fth;

	@Bind(R.id.fl)
	FrameLayout fl;
	private View t1;
	private View t2;
	private View t3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_tab_host);
		ButterKnife.bind(this);
		fth.setup(this, getSupportFragmentManager(), R.id.fl);

		t1 = getLayoutInflater().inflate(R.layout.indicator_1, null, false);
		fth.addTab(fth.newTabSpec("t1").setIndicator(t1), F1.class, null);
		t2 = getLayoutInflater().inflate(R.layout.indicator_1, null, false);
		fth.addTab(fth.newTabSpec("t2").setIndicator(t2), F2.class, null);
		t3 = getLayoutInflater().inflate(R.layout.indicator_1, null, false);
		fth.addTab(fth.newTabSpec("t3").setIndicator(t3), F1.class, null);


		TabWidget tw = fth.getTabWidget();
		//tw.setDividerDrawable(null);
		tw.setDividerDrawable(R.drawable.divider_tab_1);
		tw.setStripEnabled(false);


		fth.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				ViewCompat.setActivated(t1, "t1".equals(tabId));
				ViewCompat.setActivated(t2, "t2".equals(tabId));
				ViewCompat.setActivated(t3, "t3".equals(tabId));
			}
		});
	}

	public static abstract class BF extends Fragment {
		protected abstract int getLayoutId();

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(getLayoutId(), container, false);
		}
	}

	public static class F1 extends BF {
		@Override
		protected int getLayoutId() {
			return R.layout.activity_main;
		}
	}

	public static class F2 extends BF {
		@Override
		protected int getLayoutId() {
			return R.layout.activity_800;
		}
	}
}

```
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.app.FragmentTabHost
	android:id="@android:id/tabhost"
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="match_parent">

	<LinearLayout
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:orientation="vertical">
		<FrameLayout
			android:id="@android:id/tabcontent"
			android:layout_width="0dp"
			android:layout_height="0dp"
			android:layout_weight="0"/>
		<FrameLayout
			android:id="@+id/fl"
			android:layout_width="match_parent"
			android:layout_height="0dp"
			android:layout_weight="1"/>
		<TabWidget
			android:id="@android:id/tabs"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_weight="0"
			android:orientation="horizontal"/>
	</LinearLayout>
</android.support.v4.app.FragmentTabHost>

```