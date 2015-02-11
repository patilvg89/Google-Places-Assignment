package com.model;

public class Constants {
	public static String SharedPref = "LatLongiPref";
	public static String KeyPrefLat = "lat";
	public static String KeyPrefLongi = "longi";

	public static int radius = 10000;// metre
	public static String GetPhotoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyAARgf2Wnnree5GLNuqAEz5rvpHD9Uco8Y&photoreference=";
	public static String GetFoodUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "key=AIzaSyAARgf2Wnnree5GLNuqAEz5rvpHD9Uco8Y&radius="
			+ radius
			+ "&";

}
