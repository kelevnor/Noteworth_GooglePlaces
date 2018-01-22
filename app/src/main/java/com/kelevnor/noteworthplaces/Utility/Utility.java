package com.kelevnor.noteworthplaces.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kelevnor.noteworthplaces.UtilityLocation.FallbackLocationTracker;
import com.kelevnor.noteworthplaces.UtilityLocation.LocationTracker;
import com.kelevnor.noteworthplaces.UtilityLocation.ProviderLocationTracker;
import com.kelevnor.noteworthplaces.Models.UserPreferences;
import com.kelevnor.noteworthplaces.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by kelevnor on 1/16/18.
 */

public class Utility {

    public static Double preferredLatitude = 0.0;
    public static Double preferredLongitude = 0.0;

    public static String navi_alias = "navi";
    public static String search_alias = "search";
    public static String plus_alias = "plus";
    public static String activity_alias = "activity";
    public static String more_alias = "more";

    public static String best_match_alias = "best-match";
    public static String distance_alias = "distance";
    public static String ratings_alias = "ratings";
    public static String most_reviewed_alias = "most-reviewed";

    public static double pickedRadius = 150;
    public static boolean hotAndNew = false;
    public static boolean openNow = false;
    public static String sortBy = best_match_alias;





    public static void unSelectView(Activity act, TextView faview, TextView subview){
        faview.setTextColor(act.getResources().getColor(R.color.colorGreyMedium));
        subview.setTextColor(act.getResources().getColor(R.color.colorGreyMedium));
    }
    public static void setSelectedView(Activity act, TextView faview, TextView subview){
        faview.setTextColor(act.getResources().getColor(R.color.colorPrimaryDark));
        subview.setTextColor(act.getResources().getColor(R.color.colorPrimaryDark));
    }

    public static String GETurlConnectionErrorStream(HttpURLConnection urlConnection){

        byte [] buffer = new byte[8192];
        StringBuilder builder = new StringBuilder();
        String response = "";
        int read;

        try {
            InputStream is = new BufferedInputStream(urlConnection.getErrorStream());
            while ((read = is.read(buffer)) != -1)
            {
                builder.append(new String(buffer, 0, read, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("ERROR STREAM", builder.toString());

        response = builder.toString();

        return response;

    }
    public static String GETurlConnectionInputStream(HttpURLConnection urlConnection){
        byte [] buffer = new byte[8192];
        StringBuilder builder = new StringBuilder();
        String response = "";
        int read;

        try {
            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            while ((read = is.read(buffer)) != -1)
            {
                builder.append(new String(buffer, 0, read, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("INPUT STREAM", builder.toString());

        response = builder.toString();

        return response;
    }

    public static Location getUserLocation(Activity act){
        LocationTracker lt = new FallbackLocationTracker(act, ProviderLocationTracker.ProviderType.GPS);

        Location loc = null;
        if (lt.hasLocation()) {
            loc = lt.getLocation();
            Log.e("lt LAT/LONG", String.valueOf(loc.getLatitude()) + " / " + String.valueOf(loc.getLongitude()));
        } else {
            if (lt.hasPossiblyStaleLocation()) {
                loc = lt.getPossiblyStaleLocation();
                Log.e("STALE lt LAT/LONG", String.valueOf(loc.getLatitude()) + " / " + String.valueOf(loc.getLongitude()));
            } else {
                Log.e("STALE lt LAT/LONG", "ERR");
            }
        }

        return loc;
    }


    //Simple way to check the overall internet connection of the device
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static boolean checkInternetAvailability(Activity act) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }

    public static void saveStateInSharedPreferences(Context con, UserPreferences user){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(con);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(con.getResources().getString(R.string.user_preferences), json);
        prefsEditor.commit();
    }

    public static UserPreferences getStateFromSharedPreferences(Context con){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(con);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(con.getResources().getString(R.string.user_preferences), "");
        UserPreferences savedState = null;
        if(json.equals("")){
            savedState = initializeUserPreferences();
        }
        else {
            savedState = gson.fromJson(json, UserPreferences.class);
        }
        return savedState;
    }

    public static UserPreferences initializeUserPreferences(){
        UserPreferences user = new UserPreferences();
        user.setHotAndNew(hotAndNew);
        user.setOpenNow(openNow);
        user.setPickedRadius(pickedRadius);
        user.setSortBy(sortBy);
        return user;

    }
}
