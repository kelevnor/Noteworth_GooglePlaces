package com.kelevnor.noteworthplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.kelevnor.noteworthplaces.Models.UserPreferences;
import com.kelevnor.noteworthplaces.Models.places.PlacesObject;
import com.kelevnor.noteworthplaces.Rest.REST_getGooglePlaces;
import com.kelevnor.noteworthplaces.Utility.PermissionUtils;
import com.kelevnor.noteworthplaces.Utility.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int BUNDLE_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    public static PlacesObject placesResponse;
    public static UserPreferences userPreferences;


    Toolbar toolbar;
    Typeface fontAwesome, openSansRegular;
    TextView navi, naviSub, search, searchSub, plus, plusSub, activity, activitySub, menu, menuSub;

    LinearLayout navigationLL, searchLL, plusLL, activityLL, moreLL;

    TextView filter, go, searchFontIcon;
    EditText searchEt;

    android.support.v4.app.FragmentManager mFragmentManager;
    android.support.v4.app.FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_no_map);

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtils.requestPemissions(MainActivity.this, BUNDLE_PERMISSION_REQUEST_CODE);
        }

        userPreferences = Utility.getStateFromSharedPreferences(getApplicationContext());

        Location userLocation = Utility.getUserLocation(this);

        //Cannot get hold of device's Location
        if(userLocation.getLatitude()==0.0&&userLocation.getLongitude()==0.0){

        }
        //Device has Location
        else{
            //Retrieve data if internet established
            if(Utility.checkInternetAvailability(this)){
                REST_getGooglePlaces places = new REST_getGooglePlaces(MainActivity.this, userLocation.getLatitude(), userLocation.getLongitude(), userPreferences.getPickedRadius());
                places.setOnResultListener(asynResultPlaces);
                places.execute();
            }
            //No Internet, inform user appropriately
            else{

            }
        }



        setViews();

        Utility.setSelectedView(this, search, searchSub);

    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(MainActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_filter:
                Intent i = new Intent(this, FilterActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.ll_navi:

                Utility.setSelectedView(this, navi, naviSub);
                Utility.unSelectView(this, search, searchSub);
                Utility.unSelectView(this, activity, activitySub);
                Utility.unSelectView(this, menu, menuSub);


                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame,new Fragment_Navigation()).commit();
                break;

            case R.id.ll_search:

                Utility.unSelectView(this, navi, naviSub);
                Utility.setSelectedView(this, search, searchSub);
                Utility.unSelectView(this, activity, activitySub);
                Utility.unSelectView(this, menu, menuSub);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.frame,new Fragment_Search()).commit();
                break;

            case R.id.ll_plus:

                break;

            case R.id.ll_activity:
                Utility.unSelectView(this, navi, naviSub);
                Utility.unSelectView(this, search, searchSub);
                Utility.setSelectedView(this, activity, activitySub);
                Utility.unSelectView(this, menu, menuSub);
                break;

            case R.id.ll_more:
                Utility.unSelectView(this, navi, naviSub);
                Utility.unSelectView(this, search, searchSub);
                Utility.unSelectView(this, activity, activitySub);
                Utility.setSelectedView(this, menu, menuSub);
                break;
        }

    }
    private void setViews(){
        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        openSansRegular = Typeface.createFromAsset(getAssets(),"fonts/Open_Sans_Regular.ttf");

        //Main View
        navi=findViewById(R.id.tv_fa_navi);
        naviSub=findViewById(R.id.tv_fa_navi_sub);
        search=findViewById(R.id.tv_fa_search);
        searchSub=findViewById(R.id.tv_fa_search_sub);
        plus=findViewById(R.id.tv_fa_plus);
        plusSub=findViewById(R.id.tv_fa_plus_navi);
        activity=findViewById(R.id.tv_fa_activity);
        activitySub=findViewById(R.id.tv_fa_activity_sub);
        menu=findViewById(R.id.tv_fa_menu);
        menuSub=findViewById(R.id.tv_fa_menu_sub);

        navigationLL=findViewById(R.id.ll_navi);
        searchLL=findViewById(R.id.ll_search);
        plusLL=findViewById(R.id.ll_plus);
        activityLL=findViewById(R.id.ll_activity);
        moreLL=findViewById(R.id.ll_more);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        filter = toolbar.findViewById(R.id.tv_filter);
        go= toolbar.findViewById(R.id.tv_go);
        searchFontIcon= toolbar.findViewById(R.id.tv_search_icon);
        searchEt = toolbar.findViewById(R.id.et_search);
        //Set Fonts
        navi.setTypeface(fontAwesome);
        search.setTypeface(fontAwesome);
        plus.setTypeface(fontAwesome);
        activity.setTypeface(fontAwesome);
        menu.setTypeface(fontAwesome);
        searchFontIcon.setTypeface(fontAwesome);

        naviSub.setTypeface(openSansRegular);
        searchSub.setTypeface(openSansRegular);
        plusSub.setTypeface(openSansRegular);
        activitySub.setTypeface(openSansRegular);
        menuSub.setTypeface(openSansRegular);
        go.setTypeface(openSansRegular);
        filter.setTypeface(openSansRegular);
        searchEt.setTypeface(openSansRegular);

        navigationLL.setOnClickListener(this);
        searchLL.setOnClickListener(this);
        plusLL.setOnClickListener(this);
        activityLL.setOnClickListener(this);
        moreLL.setOnClickListener(this);
        filter.setOnClickListener(this);

    }

    REST_getGooglePlaces.OnAsyncResult asynResultPlaces = new REST_getGooglePlaces.OnAsyncResult() {
        @Override
        public void onResultSuccess(int resultCode, String result) {

            Gson gson = new Gson();
            placesResponse = new PlacesObject();
            placesResponse = gson.fromJson(result, PlacesObject.class);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.frame,new Fragment_Search()).commit();

            Log.e("size", String.valueOf(placesResponse.getResults().size()));
        }

        @Override
        public void onResultFail(int resultCode, String errorResult) {
            Log.e("FAIL_company", errorResult);
        }
    };

}
