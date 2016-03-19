RecyclerView + SwipeLayout的用法
RV1Activity.java
```java
package org.xzc.learn_20151001;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * 想要达到的效果:
 * 1. 可以侧滑出菜单
 * 2. 菜单里有一项叫做删除,点击之后删除这个条目
 * 3. 菜单里可以有其他项,只显示界面不提供功能
 * 4. 一次只能有1个项被打开
 * 5. 当滑动的时候项目必须要关闭
 */
public class RV1Activity extends AppCompatActivity {
	@Bind(R.id.rv)
	RecyclerView rv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rv1);
		ButterKnife.bind(this);
		final float density = getResources().getDisplayMetrics().density;
		rv.setLayoutManager(new LinearLayoutManager(this));
		rv.setAdapter(new Adapter());
		rv.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				int pos = parent.getChildLayoutPosition(view);
				if (pos == 0)
					super.getItemOffsets(outRect, view, parent, state);
				else
					outRect.set(0, (int) (4 * density + 0.5f), 0, 0);
			}
		});
		rv.setItemAnimator(new SlideInRightAnimator());
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.tv)
		TextView tv;

		@Bind(R.id.del)
		TextView del;

		@Bind(R.id.sl)
		SwipeLayout sl;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public static class Adapter extends RecyclerSwipeAdapter<ViewHolder> {
		private List<String> data = new ArrayList<>();

		public Adapter() {
			//假数据
			for (int i = 0; i < 100; ++i) {
				data.add("TEXT " + i);
			}
			setMode(Attributes.Mode.Single);
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.item_rv1, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {

			//closeItem(position);
			holder.tv.setText(data.get(position));
			holder.del.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {//删除被点击了 删除该条目
					//先移除该监听器 放置被点击多次 或者可以设置为disabled
					v.setOnClickListener(null);
					int currentPos = holder.getAdapterPosition();
					data.remove(currentPos);
					mItemManger.closeAllItems();
					notifyItemRemoved(currentPos);
				}
			});
			mItemManger.bindView(holder.itemView, position);
		}

		@Override
		public int getItemCount() {
			return data.size();
		}

		@Override
		public int getSwipeLayoutResourceId(int position) {
			return R.id.sl;
		}
	}
}

```

item_rv1.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.daimajia.swipe.SwipeLayout
	android:id="@+id/sl"
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto"
	android:layout_width="match_parent"
	android:layout_height="100dp"
	app:clickToClose="true"
	app:drag_edge="right"
	app:show_mode="pull_out"
	>

	<LinearLayout
		android:layout_width="wrap_content"
		android:layout_height="match_parent"
		android:layout_gravity="end"
		android:orientation="horizontal"
		>

		<TextView
			android:id="@+id/del"
			android:layout_width="100dp"
			android:layout_height="100dp"
			android:background="#f00"
			android:gravity="center"
			android:text="删除"
			android:textColor="#fff"
			android:textSize="22sp"
			/>

		<TextView
			android:layout_width="100dp"
			android:layout_height="100dp"
			android:background="#0f0"
			android:gravity="center"
			android:text="删除2"
			android:textColor="#fff"
			android:textSize="22sp"
			/>
	</LinearLayout>

	<TextView
		android:id="@+id/tv"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		android:background="#336699"
		android:gravity="center"
		android:text="TEXT"
		android:textColor="#fff"
		android:textSize="30sp"
		/>

</com.daimajia.swipe.SwipeLayout>

```