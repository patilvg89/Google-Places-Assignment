package com.db;

import com.model.PlaceDetail;
import com.util.OnTaskCompleted;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class RestAsyncTask extends AsyncTask<Void, Void, Object> {
	String TAG = RestAsyncTask.class.getSimpleName();
	Activity context;
	PlaceDetail obj;
	OnTaskCompleted listener;

	public RestAsyncTask(Activity context, OnTaskCompleted listener,
			PlaceDetail respStr) {
		super();
		this.context = context;
		this.obj = respStr;
		this.listener = listener;
	}

	@Override
	protected Object doInBackground(Void... params) {

		try {
			DBhandler dbHandler = DBhandler.getInstance(context);
			Log.i(TAG, "In rest async task");
			dbHandler.bulkInsertIntoDB(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
