package com.kelevnor.noteworthplaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marios Sifalakis on 04-06-2015.
 */
public class Fragment_Navigation extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation,container,false);

        return v;
    }


    @Override
    public void onClick(View view) {

    }
}


