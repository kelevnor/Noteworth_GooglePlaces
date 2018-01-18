package com.kelevnor.noteworthplaces.Rest;

import android.app.Activity;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kelevnor on 1/16/18.
 */

public class Retrofit_getGooglePlaces{

    private static final String URI_PLACES = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    private static final String URI_PAR_1 = "&radius=";
    private static final String URI_PAR_2 = "&type=restaurant";
    private static final String URI_PAR_3 = "&keyword=cruise";
    private static final String URI_PAR_4 = "&key=AIzaSyBr361yzsWQ_cZB0YdLwSjEauu8FQSQApU";

    Activity act;
    double latitude;
    double longitude;
    double radius;

    public void Retrofit_getGooglePlaces(Activity act, double latitude, double longitude, double radius){
        this.act= act;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;

    }

    public void getAllLocations() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(createBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private String createBaseUrl(){
        Log.d("BASE_URI",URI_PLACES+String.valueOf(latitude)+","+String.valueOf(longitude)
                +URI_PAR_1
                + String.valueOf(radius)
                +URI_PAR_2+URI_PAR_3+URI_PAR_4);
        return URI_PLACES+String.valueOf(latitude)+","+String.valueOf(longitude)
                +URI_PAR_1
                + String.valueOf(radius)
                +URI_PAR_2+URI_PAR_3+URI_PAR_4;
    }

}