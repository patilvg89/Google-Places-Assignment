package com.nearby.app.ui;

import com.model.Constants;
import com.nearby.app.R;
import com.nearby.service.AppLocationService;
import com.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ActivityLaunch extends Activity implements OnClickListener {
	AppLocationService appLocationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		appLocationService = new AppLocationService(this);

		findViewById(R.id.buttonFood).setOnClickListener(this);
		findViewById(R.id.buttonGym).setOnClickListener(this);
		findViewById(R.id.buttonSchool).setOnClickListener(this);
		findViewById(R.id.buttonHospital).setOnClickListener(this);
		findViewById(R.id.buttonSpa).setOnClickListener(this);
		findViewById(R.id.buttonRestaurent).setOnClickListener(this);

		locationAvailable();
		// copy database
		Util.copyDatabase(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_loc:
			getGPSLocation();
			break;
		case R.id.menu_fav:
			// slide from right to left
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
			startActivity(new Intent(ActivityLaunch.this, ActivityFav.class));
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void getGPSLocation() {
		Location gpsLocation = appLocationService
				.getLocation(LocationManager.GPS_PROVIDER);

		if (gpsLocation != null) {
			double latitude = gpsLocation.getLatitude();
			double longitude = gpsLocation.getLongitude();
			Toast.makeText(
					getApplicationContext(),
					"Mobile Location (GPS): \nLatitude: " + latitude
							+ "\nLongitude: " + longitude, Toast.LENGTH_LONG)
					.show();

			// save lat and longi in shared pref
			Util.storeToPref(Constants.KeyPrefLat, String.valueOf(latitude),
					this);
			Util.storeToPref(Constants.KeyPrefLongi, String.valueOf(longitude),
					this);

		} else {
			showSettingsAlert("GPS");
		}
	}

	public void showSettingsAlert(String provider) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog.setMessage(provider
				+ " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}

	@Override
	public void onClick(View v) {
		if (!latLongiNull) {
			switch (v.getId()) {

			case R.id.buttonFood:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"food"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			case R.id.buttonGym:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"gym"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			case R.id.buttonSchool:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"school"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			case R.id.buttonHospital:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"hospital"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			case R.id.buttonSpa:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"spa"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			case R.id.buttonRestaurent:
				startActivity(new Intent(ActivityLaunch.this,
						ActivityDashboard.class).putExtra("SEARCH_STRING",
						"restaurant"));
				// slide from right to left
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				break;
			default:
				break;
			}
		} else {
			getGPSLocation();
		}
	}

	String lat = "";
	String longi = "";
	boolean latLongiNull = true;

	boolean locationAvailable() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				lat = Util.getFromPrefs(Constants.KeyPrefLat,
						ActivityLaunch.this);
				longi = Util.getFromPrefs(Constants.KeyPrefLongi,
						ActivityLaunch.this);
				System.out.println("lat=" + lat + " longi=" + longi);
				if (!lat.equals("") && !longi.equals("")) {
					latLongiNull = false;
				}
			}
		}).start();
		return latLongiNull;
	}
}
