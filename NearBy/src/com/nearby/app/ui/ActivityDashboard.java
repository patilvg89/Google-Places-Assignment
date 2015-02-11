package com.nearby.app.ui;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.adapter.MyAdapter;
import com.async.GetFoodNearBy;
import com.model.Constants;
import com.model.PlaceDetail;
import com.nearby.app.R;
import com.util.OnTaskCompleted;
import com.util.Util;

public class ActivityDashboard extends Activity implements OnTaskCompleted {
	RecyclerView mRecyclerView;
	RecyclerView.Adapter mAdapter;
	RecyclerView.LayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__dashboard);
		// action bar setting
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
		getActionBar().setTitle(null);
		getActionBar().setSubtitle(null);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#EC3C87")));

		// findViewById(R.id.buttonFood).setOnClickListener(this);
		String loc = Util.getLatLong(this);
		String types = getIntent().getStringExtra("SEARCH_STRING");
		String Url = Constants.GetFoodUrl + "location=" + loc + "&types="
				+ types;
		new GetFoodNearBy(this, this).execute(Url);

		mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// slide from right to left
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onTaskCompleted(ArrayList<PlaceDetail> o) {
		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		System.out.println("data received=" + o.size());
		// specify an adapter (see also next example)
		mAdapter = new MyAdapter(o);
		mRecyclerView.setAdapter(mAdapter);
	}
}
