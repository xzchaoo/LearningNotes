package com.example.learn0711;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_swipe_refresh_layout)
public class SwipeRefreshLayoutActivity extends Activity {

	@ViewInject(R.id.swipeRefreshLayout)
	private SwipeRefreshLayout layout;

	@ViewInject(R.id.listView)
	private ListView listView;

	private ArrayAdapter<String> aa;

	private List<String> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );

		items = new ArrayList<String>();
		//这里ArrayAdapter保存了items
		//以后如果你对items进行修改 aa会受到影响
		aa = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, items );

		for (int i = 0; i < 5; ++i)
			items.add( "item" + i );

		ViewUtils.inject( this );

		listView.setAdapter( aa );

		layout.setOnRefreshListener( new OnRefreshListener() {
			public void onRefresh() {
				updateItems();
			}
		} );
	}

	private void updateItems() {
		for (int i = 0; i < items.size(); ++i) {
			String s = items.get( i );
			items.set( i, s + "新" );
		}
		aa.notifyDataSetChanged();
		//aa.add( "item"+aa.getCount() );
		//aa.notifyDataSetChanged();

		//更新完毕
		layout.setRefreshing( false );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate( R.menu.activity_swipe_refresh_menu, menu );
		return super.onCreateOptionsMenu( menu );
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.refresh) {
			layout.setRefreshing( true );
			updateItems();
			return true;
		}
		return super.onOptionsItemSelected( item );
	};
}
