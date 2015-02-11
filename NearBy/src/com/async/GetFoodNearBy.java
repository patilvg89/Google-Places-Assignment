package com.async;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.model.PlaceDetail;
import com.util.OnTaskCompleted;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetFoodNearBy extends
        AsyncTask<String, Void, ArrayList<PlaceDetail>> {
    Context context;
    ProgressDialog mDialog;
    OnTaskCompleted listener;

    public GetFoodNearBy(Context context, OnTaskCompleted listner) {
        this.context = context;
        this.listener = listner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = ProgressDialog.show(context, "", "Please wait..");
    }

    @Override
    protected ArrayList<PlaceDetail> doInBackground(String... params) {
        ArrayList<PlaceDetail> placeList = new ArrayList<PlaceDetail>();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);

            // System.out.println("response:" + resp);

            JSONObject json = new JSONObject(resp);
            JSONArray jssArr = json.getJSONArray("results");

            for (int i = 0; i < jssArr.length(); i++) {
                JSONObject js1 = jssArr.getJSONObject(i);
                PlaceDetail place = new PlaceDetail();

                // location
                JSONObject loc1 = js1.getJSONObject("geometry");
                JSONObject loc = loc1.getJSONObject("location");
                place.setLat(String.valueOf(loc.getDouble("lat")));
                place.setLng(String.valueOf(loc.getDouble("lng")));

                place.setIconUrl(js1.getString("icon"));

                place.setName(js1.getString("name"));

                place.setPlace_id(js1.getString("place_id"));

                if(js1.has("rating")){
                place.setRating(js1.getDouble("rating"));}

                place.setReference(js1.getString("reference"));

                place.setVicinity(js1.getString("vicinity"));

                // open now
                if (js1.has("opening_hours")) {
                    JSONObject openObj = js1.getJSONObject("opening_hours");
                    place.setOpenNow(openObj.getBoolean("open_now"));
                }

                // place photos
                if (js1.has("photos")) {
                    JSONArray photoArray = js1.getJSONArray("photos");
                    for (int j = 0; j < photoArray.length(); j++) {
                        JSONObject photo = photoArray.getJSONObject(j);
                        place.setPhotoReference(photo
                                .getString("photo_reference"));
                    }
                }
                // types array
                JSONArray typesArr = js1.getJSONArray("types");
                String types[] = new String[typesArr.length()];
                for (int j = 0; j < typesArr.length(); j++) {
                    types[j] = typesArr.getString(j);
                }
                place.setTypes(types);

                placeList.add(place);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return placeList;
    }

    @Override
    protected void onPostExecute(ArrayList<PlaceDetail> result) {
        super.onPostExecute(result);
        mDialog.dismiss();
        System.out.println("added data=" + result.size());
        listener.onTaskCompleted(result);

    }

}
