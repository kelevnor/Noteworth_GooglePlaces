package com.kelevnor.noteworthplaces;

import android.app.Activity;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kelevnor.noteworthplaces.Adapters.Adapter_PlacesItem;
import com.kelevnor.noteworthplaces.Models.places.PlacesObject;
import com.kelevnor.noteworthplaces.Rest.REST_getGooglePlaces;
import com.kelevnor.noteworthplaces.Utility.Utility;

/**
 * Created by Admin on 04-06-2015.
 */
public class Fragment_Search extends Fragment implements View.OnClickListener{

    private TextView noStores;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Typeface openSansSemiBold;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search,container,false);

        openSansSemiBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Open_Sans_SemiBold.ttf");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        noStores = (TextView) v.findViewById(R.id.tv_nostores);

        noStores.setTypeface(openSansSemiBold);

//        if(MainActivity.placesResponse.getResults().size()==0){
//            mRecyclerView.setVisibility(View.GONE);
//        }
//        else{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            // use a linear layout manager
            mRecyclerView.setLayoutManager(mLayoutManager);

            Location userLocation = Utility.getUserLocation(getActivity());

            //Cannot get hold of device's Location
            if(userLocation.getLatitude()==0.0&&userLocation.getLongitude()==0.0){
            }
            //Device has Location
            else{

                Utility.preferredLatitude = userLocation.getLatitude();
                Utility.preferredLongitude = userLocation.getLongitude();

                //Retrieve data if internet established
                if(Utility.checkInternetAvailability(getActivity())){
                    REST_getGooglePlaces places = new REST_getGooglePlaces(getActivity(), Utility.preferredLatitude, Utility.preferredLongitude, MainActivity.userPreferences.getPickedRadius());
                    places.setOnResultListener(asynResultPlaces);
                    places.execute();
                }
                //No Internet, inform user appropriately
                else{

                }
            }

//        }

        return v;
    }


    @Override
    public void onClick(View view) {

    }
    REST_getGooglePlaces.OnAsyncResult asynResultPlaces = new REST_getGooglePlaces.OnAsyncResult() {
        @Override
        public void onResultSuccess(int resultCode, String result) {

            Gson gson = new Gson();
            MainActivity.placesResponse = new PlacesObject();
            MainActivity.placesResponse = gson.fromJson(result, PlacesObject.class);

            if(MainActivity.placesResponse.getResults().size()==0){
                mRecyclerView.setVisibility(View.GONE);
            }
            else{
                // specify an adapter (see also next example)
                mAdapter = new Adapter_PlacesItem(getActivity(), MainActivity.placesResponse.getResults());
                mRecyclerView.setAdapter(mAdapter);
                Log.e("size", String.valueOf(MainActivity.placesResponse.getResults().size()));
            }




        }

        @Override
        public void onResultFail(int resultCode, String errorResult) {
            Log.e("FAIL_company", errorResult);
        }
    };
}