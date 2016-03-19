http://developer.android.com/intl/zh-cn/reference/android/support/v4/widget/SlidingPaneLayout.html#setCoveredFadeColor(int)

```java
package org.xzc.learnmaterialdrawer;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SlidingActivity extends AppCompatActivity {

	@Bind(R.id.spl)
	SlidingPaneLayout spl;

	@Bind(R.id.tv1)
	TextView tv1;
	@Bind(R.id.tv2)
	TextView tv2;
	@Bind(R.id.tv3)
	TextView tv3;

	private static final String TAG = "SlidingActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding);
		ButterKnife.bind(this);
		spl.setCoveredFadeColor(Color.RED);
		//spl.setParallaxDistance(100);
		spl.setSliderFadeColor(Color.BLUE);
		final float px = 120 * getResources().getDisplayMetrics().density;
		spl.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				Log.d(TAG, "onPanelSlide() called with: " + "panel = [" + panel + "], slideOffset = [" + slideOffset + "]");
				ViewCompat.setAlpha(tv1, 0.5f + 0.5f * slideOffset);
				ViewCompat.setTranslationX(tv1, -px / 2 + px / 2 * slideOffset);
				ViewCompat.setAlpha(tv2, 1.0f - slideOffset);
				ViewCompat.setScaleX(tv3,1.0f-0.2f*slideOffset);
				ViewCompat.setScaleY(tv3,1.0f-0.2f*slideOffset);
				//ViewCompat.setTranslationX(tv3, -px * slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				Log.d(TAG, "onPanelOpened() called with: " + "panel = [" + panel + "]");

			}

			@Override
			public void onPanelClosed(View panel) {
				Log.d(TAG, "onPanelClosed() called with: " + "panel = [" + panel + "]");

			}
		});
	}
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.SlidingPaneLayout
	android:id="@+id/spl"
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto"
	xmlns:tools="http://schemas.android.com/tools"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	tools:context="org.xzc.learnmaterialdrawer.SlidingActivity"
	>

	<FrameLayout
		android:layout_width="200dp"
		android:layout_height="match_parent">

		<TextView
			android:id="@+id/tv1"
			android:layout_width="200dp"
			android:layout_height="match_parent"
			android:background="#4f00"
			android:gravity="center"
			android:text="我是左边"
			android:textColor="#000"
			android:textSize="40sp"
			/>

		<TextView
			android:id="@+id/tv2"
			android:layout_width="80dp"
			android:layout_height="match_parent"
			android:background="#f00"
			android:gravity="center"
			android:text="LEFT"
			android:textColor="#fff"
			android:textSize="22sp"
			/>
	</FrameLayout>


	<TextView
		android:id="@+id/tv3"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:layout_marginLeft="80dp"
		android:background="#40ff"
		android:gravity="center"
		android:text="我是右边"
		android:textColor="#000"
		android:textSize="40sp"
		/>

</android.support.v4.widget.SlidingPaneLayout>
```

