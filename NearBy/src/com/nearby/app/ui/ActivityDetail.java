package com.nearby.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.db.DBhandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.image.utils.ImageLoader;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.model.Constants;
import com.model.PlaceDetail;
import com.nearby.app.R;
import com.util.Util;

public class ActivityDetail extends FragmentActivity {
	ImageLoader imageLoader;
	private PlaceDetail thumbs;
	GoogleMap googleMap;

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

		thumbs = (PlaceDetail) bundle.getSerializable("DETAILS");
		TextView title = (TextView) findViewById(R.id.textViewTitle);
		title.setText(thumbs.getName());
		TextView text1 = (TextView) findViewById(R.id.textViewHeader11);
		text1.setText(String.valueOf(thumbs.getOpenNow()));
		TextView text2 = (TextView) findViewById(R.id.textViewHeader21);
		text2.setText(String.valueOf(thumbs.getRating()));
		TextView text3 = (TextView) findViewById(R.id.textViewHeader31);
		String text = Util.getListInString(thumbs.getTypes());
		if (!text.equals("")) {
			text3.setText(text);
		} else {
			text3.setText(thumbs.getTypesText());
		}

		TextView text4 = (TextView) findViewById(R.id.textViewHeader41);
		text4.setText(thumbs.getVicinity());

		// set image
		ImageView Image = (ImageView) findViewById(R.id.image_header);
		String photoUrl = Constants.GetPhotoUrl + thumbs.getPhotoReference();
		System.out.println("Photo URL=" + photoUrl);
		imageLoader.DisplayImage(photoUrl, Image);

		//set map
		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.googleMap);
		googleMap = supportMapFragment.getMap();
		LatLng latLng = new LatLng(Double.parseDouble(thumbs.getLat()),
				Double.parseDouble(thumbs.getLng()));
		// googleMap.addMarker(new MarkerOptions().position(latLng));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		googleMap.addMarker(new MarkerOptions().title(thumbs.getName())
				.snippet(thumbs.getVicinity()).position(latLng)
				.anchor(0.0f, 1.0f)); // Anchors the marker on the bottom left
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_fav:
			DBhandler.getInstance(this).bulkInsertIntoDB(thumbs);
			Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT)
					.show();
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
