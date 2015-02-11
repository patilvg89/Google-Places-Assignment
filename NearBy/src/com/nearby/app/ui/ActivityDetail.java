package com.nearby.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.image.utils.ImageLoader;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.model.Constants;
import com.model.PlaceDetail;
import com.nearby.app.R;
import com.util.Util;

public class ActivityDetail extends Activity {
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FadingActionBarHelper helper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.ab_background)
				.headerLayout(R.layout.header)
				.contentLayout(R.layout.activity_detail_layout);
		setContentView(helper.createView(this));
		helper.initActionBar(this);

		// initialize image loader
		imageLoader = new ImageLoader(this);
		// action bar setting
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
		getActionBar().setTitle(null);
		getActionBar().setSubtitle(null);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#EC3C87")));

		// get bundle
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();

		PlaceDetail thumbs = (PlaceDetail) bundle.getSerializable("DETAILS");
		TextView title = (TextView) findViewById(R.id.textViewTitle);
		title.setText(thumbs.getName());
		TextView text1 = (TextView) findViewById(R.id.textViewHeader11);
		text1.setText(String.valueOf(thumbs.getOpenNow()));
		TextView text2 = (TextView) findViewById(R.id.textViewHeader21);
		text2.setText(String.valueOf(thumbs.getRating()));
		TextView text3 = (TextView) findViewById(R.id.textViewHeader31);
		text3.setText(Util.getListInString(thumbs.getTypes()));
		TextView text4 = (TextView) findViewById(R.id.textViewHeader41);
		text4.setText(thumbs.getVicinity());

		// set image
		ImageView Image = (ImageView) findViewById(R.id.image_header);
		String photoUrl = Constants.GetPhotoUrl + thumbs.getPhotoReference();
		System.out.println("Photo URL=" + photoUrl);
		imageLoader.DisplayImage(photoUrl, Image);

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
}
