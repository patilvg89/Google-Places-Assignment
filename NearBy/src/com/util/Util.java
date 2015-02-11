package com.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.model.Constants;

public class Util {

	public static void storeToPref(String key, String value, Context context) {
		SharedPreferences.Editor edit = context.getSharedPreferences(
				Constants.SharedPref, Context.MODE_PRIVATE).edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static String getFromPrefs(String key, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				Constants.SharedPref, Context.MODE_PRIVATE);
		return prefs.getString(key, "");
	}

	public static String getLatLong(Context context) {
		String lat = getFromPrefs(Constants.KeyPrefLat, context);
		String longi = getFromPrefs(Constants.KeyPrefLongi, context);
		return lat + "," + longi;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String getListInString(String[] arr) {
		String list = "";
		try {
			for (int i = 0; i < arr.length; i++) {
				list = list + "," + arr[i];
			}
			list = list.trim();
			if (list.endsWith(",")) {
				list = list.substring(0, list.length() - 1);
			}
			if (list.startsWith(",")) {
				list = list.substring(1);
			}
			System.err.println("string from arr:" + list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}
}
