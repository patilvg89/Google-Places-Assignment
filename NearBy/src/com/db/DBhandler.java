package com.db;

import java.util.ArrayList;
import java.util.List;

import com.model.Constants;
import com.model.PlaceDetail;
import com.util.Util;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DBhandler {
	private SQLiteDatabase db;

	public DBhandler(Context context) {
		db = SQLiteDatabase.openDatabase(context.getApplicationContext()
				.getCacheDir().getAbsolutePath()
				+ "/" + Constants.DBName, null, SQLiteDatabase.OPEN_READWRITE);

	}

	public void DBclose() {
		db.close();
	}

	static DBhandler dBhandler = null;

	public static DBhandler getInstance(Context context) {
		if (dBhandler == null) {
			dBhandler = new DBhandler(context);
		}
		return dBhandler;
	}

	public ArrayList<PlaceDetail> getFavPlaceList() {
		Log.i("TAG", "Inside get job list");
		ArrayList<PlaceDetail> new_return_List = new ArrayList<PlaceDetail>();
		Cursor cursor;
		// latest jobs has largest j_id
		cursor = db.query(Constants.TableName, null, null, null, null, null,null);
		if (cursor.moveToFirst()) {
			do {
				PlaceDetail details = new PlaceDetail();
				details.setLat(cursor.getString(cursor.getColumnIndex("lat")));
				details.setLng(cursor.getString(cursor.getColumnIndex("longi")));
				details.setName(cursor.getString(cursor.getColumnIndex("title")));
				details.setIconUrl(cursor.getString(cursor
						.getColumnIndex("icon_url")));
				details.setPhotoReference(cursor.getString(cursor
						.getColumnIndex("large_image_url")));
				details.setOpenNow(Boolean.getBoolean(cursor.getString(cursor
						.getColumnIndex("is_open"))));
				details.setRating(Double.parseDouble(cursor.getString(cursor
						.getColumnIndex("rating"))));
				details.setTypesText(cursor.getString(cursor
						.getColumnIndex("type")));
				details.setVicinity(cursor.getString(cursor
						.getColumnIndex("vicinity")));

				new_return_List.add(details);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return new_return_List;
	}

	public void bulkInsertIntoDB(PlaceDetail obj) {
		String sql = "INSERT INTO " + Constants.TableName
				+ " VALUES (?,?,?,?,?,?,?,?,?);";
		SQLiteStatement statement = db.compileStatement(sql);
		db.beginTransaction();
		try {
			statement.clearBindings();
			statement.bindString(1, obj.getLat());
			statement.bindString(2, obj.getLng());
			statement.bindString(3, obj.getName());
			statement.bindString(4, obj.getIconUrl());
			statement.bindString(5, obj.getPhotoReference());
			statement.bindString(6, String.valueOf(obj.getOpenNow()));
			statement.bindString(7, String.valueOf(obj.getRating()));
			statement.bindString(8, Util.getListInString(obj.getTypes()));
			statement.bindString(9, obj.getVicinity());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.setTransactionSuccessful();
		Log.i("TAG", "db size=" + db.getPageSize());
		db.endTransaction();
	}
}
