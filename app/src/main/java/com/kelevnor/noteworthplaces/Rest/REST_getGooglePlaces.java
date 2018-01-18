package com.kelevnor.noteworthplaces.Rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.kelevnor.noteworthplaces.R;
import com.kelevnor.noteworthplaces.Utility.Utility;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kelevnor on 1/16/18.
 */

public class REST_getGooglePlaces extends AsyncTask<Void, Integer, Void> {

    boolean completedCall = false;
    private static final String URI_PLACES = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    private static final String URI_PAR_1 = "&radius=";
    private static final String URI_PAR_2 = "&type=restaurant";
    private static final String URI_PAR_3 = "&keyword=cruise";
    private static final String URI_PAR_4 = "&key=";

    String response_passed = "";

    Activity act;
    double latitude;
    double longitude;
    double radius;

    OnAsyncResult onAsyncResult;

    public REST_getGooglePlaces(Activity act, double latitude, double longitude, double radius){
        this.act= act;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;

    }
    public void setOnResultListener(OnAsyncResult onAsyncResult) {
        if (onAsyncResult != null) {
            this.onAsyncResult = onAsyncResult;
        }
    }

    private String createBaseUrl(){
        Log.d("BASE_URI",URI_PLACES+String.valueOf(latitude)+","+String.valueOf(longitude)
                +URI_PAR_1
                + String.valueOf(radius)
                +URI_PAR_2+URI_PAR_3+URI_PAR_4+act.getResources().getString(R.string.google_maps_key));
        return URI_PLACES+String.valueOf(latitude)+","+String.valueOf(longitude)
                +URI_PAR_1
                + String.valueOf(radius)
                +URI_PAR_2+URI_PAR_3+URI_PAR_4+act.getResources().getString(R.string.google_maps_key);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("url", createBaseUrl());
        try {
            URL url = new URL(createBaseUrl());
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");


            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response_passed = Utility.GETurlConnectionInputStream(urlConnection);
                completedCall= true;
            }
            else{
                response_passed = Utility.GETurlConnectionErrorStream(urlConnection);
                completedCall = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.e("response", response_passed);
        if(completedCall){
            onAsyncResult.onResultSuccess(1, response_passed);
        }
        else {
            onAsyncResult.onResultFail(0, response_passed);
        }
    }




    public interface OnAsyncResult {
        void onResultSuccess(int resultCode, String message);
        void onResultFail(int resultCode, String errorMessage);
    }
}