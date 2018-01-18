package com.kelevnor.noteworthplaces;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.kelevnor.noteworthplaces.Models.UserPreferences;
import com.kelevnor.noteworthplaces.Utility.PermissionUtils;
import com.kelevnor.noteworthplaces.Utility.Utility;

import okhttp3.internal.Util;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, Switch.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int BUNDLE_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;



    Toolbar toolbar;
    Typeface fontAwesome, openSansRegular, openSansSemiBold;

    TextView hotandnewtv, opennowtv, radiustv, radiusactualtv, ssortbytitletv, bestmatchtv, distancetv, ratingstv, mostreviewedtv, generalfeaturestv, takesreservationstv, acceptscreditcardstv;

    SeekBar radiusseek;

    TextView fabestmatch, fadistance, faratings, famostreviewed;

    Switch hotandnewsw, opennowsw, takesreservationssw, acceptscreditcardssw;

    TextView cancel, filters, search;

    RelativeLayout bestmatchrl, distancerl, ratingrl, mostreviewedrl;
    android.support.v4.app.FragmentManager mFragmentManager;
    android.support.v4.app.FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtils.requestPemissions(FilterActivity.this, BUNDLE_PERMISSION_REQUEST_CODE);
        }

        setViews();


        hotandnewsw.setChecked(MainActivity.userPreferences.getHotAndNew());
        opennowsw.setChecked(MainActivity.userPreferences.getOpenNow());
        if(MainActivity.userPreferences.getSortBy().equals(Utility.best_match_alias)){
            fabestmatch.setText(getResources().getString(R.string.fa_circle));
            fadistance.setText(getResources().getString(R.string.fa_circle_empty));
            faratings.setText(getResources().getString(R.string.fa_circle_empty));
            famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));
        }
        else if(MainActivity.userPreferences.getSortBy().equals(Utility.distance_alias)){
            fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
            fadistance.setText(getResources().getString(R.string.fa_circle));
            faratings.setText(getResources().getString(R.string.fa_circle_empty));
            famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));
        }
        else if(MainActivity.userPreferences.getSortBy().equals(Utility.ratings_alias)){
            fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
            fadistance.setText(getResources().getString(R.string.fa_circle_empty));
            faratings.setText(getResources().getString(R.string.fa_circle));
            famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));
        }
        else{
            fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
            fadistance.setText(getResources().getString(R.string.fa_circle_empty));
            faratings.setText(getResources().getString(R.string.fa_circle_empty));
            famostreviewed.setText(getResources().getString(R.string.fa_circle));
        }

        radiusactualtv.setText(MainActivity.userPreferences.getPickedRadius().intValue()+" meters");
        radiusseek.setProgress(MainActivity.userPreferences.getPickedRadius().intValue());


    }



    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    private void setViews(){
        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        openSansRegular = Typeface.createFromAsset(getAssets(),"fonts/Open_Sans_Regular.ttf");
        openSansSemiBold = Typeface.createFromAsset(getAssets(),"fonts/Open_Sans_SemiBold.ttf");

        bestmatchrl = (RelativeLayout) findViewById(R.id.rl_bestmatch);
        distancerl = (RelativeLayout) findViewById(R.id.rl_distance);
        ratingrl = (RelativeLayout) findViewById(R.id.rl_rating);
        mostreviewedrl = (RelativeLayout) findViewById(R.id.rl_mostreviewed);
        hotandnewtv = (TextView) findViewById(R.id.tv_hotandnew);
        opennowtv = (TextView) findViewById(R.id.tv_opennow);
        radiustv = (TextView) findViewById(R.id.tv_radius);
        radiusactualtv = (TextView) findViewById(R.id.tv_radiusnumber);
        ssortbytitletv = (TextView) findViewById(R.id.tv_sortby);
        bestmatchtv = (TextView) findViewById(R.id.tv_bestmatch);
        distancetv = (TextView) findViewById(R.id.tv_distance);
        ratingstv = (TextView) findViewById(R.id.tv_rating);
        mostreviewedtv = (TextView) findViewById(R.id.tv_mostreviewed);

        generalfeaturestv = (TextView) findViewById(R.id.tv_generalfeatures);
        takesreservationstv = (TextView) findViewById(R.id.tv_takesreservations);
        acceptscreditcardstv = (TextView) findViewById(R.id.tv_acceptscreditcards);
        fabestmatch = (TextView) findViewById(R.id.tv_bestmatchcheck);
        fadistance = (TextView) findViewById(R.id.tv_distancecheck);
        faratings = (TextView) findViewById(R.id.tv_ratingcheck);
        famostreviewed = (TextView) findViewById(R.id.tv_mostreviewedcheck);
        radiusseek = (SeekBar) findViewById(R.id.sb_radius);

        hotandnewsw = (Switch) findViewById(R.id.sw_hotandnew);
        opennowsw = (Switch) findViewById(R.id.sw_opennow);
        takesreservationssw = (Switch) findViewById(R.id.sw_takesreservations);
        acceptscreditcardssw = (Switch) findViewById(R.id.sw_acceptscreditcards);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        filters = (TextView) toolbar.findViewById(R.id.tv_filters);
        search= toolbar.findViewById(R.id.tv_search);
        cancel= toolbar.findViewById(R.id.tv_cancel);

        filters.setTypeface(openSansSemiBold);
        search.setTypeface(openSansRegular);
        cancel.setTypeface(openSansRegular);

        hotandnewtv.setTypeface(openSansRegular);
        opennowtv.setTypeface(openSansRegular);
        radiustv.setTypeface(openSansRegular);
        radiusactualtv.setTypeface(openSansSemiBold);
        ssortbytitletv.setTypeface(openSansSemiBold);
        bestmatchtv.setTypeface(openSansRegular);
        distancetv.setTypeface(openSansRegular);
        ratingstv.setTypeface(openSansRegular);
        mostreviewedtv.setTypeface(openSansRegular);

        generalfeaturestv.setTypeface(openSansSemiBold);
        takesreservationstv.setTypeface(openSansRegular);
        acceptscreditcardstv.setTypeface(openSansRegular);
        fabestmatch.setTypeface(fontAwesome);
        fadistance.setTypeface(fontAwesome);
        faratings.setTypeface(fontAwesome);
        famostreviewed.setTypeface(fontAwesome);

        hotandnewsw.setOnCheckedChangeListener(this);
        bestmatchrl.setOnClickListener(this);
        distancerl.setOnClickListener(this);
        ratingrl.setOnClickListener(this);
        mostreviewedrl.setOnClickListener(this);
        cancel.setOnClickListener(this);
        search.setOnClickListener(this);
        radiusseek.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.sw_hotandnew:
                break;

            case R.id.sw_opennow:
                break;
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_cancel:
                Intent cancelInt = new Intent();
                setResult(Activity.RESULT_CANCELED, cancelInt);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.tv_search:

                MainActivity.userPreferences.setHotAndNew(hotandnewsw.isChecked());
                MainActivity.userPreferences.setOpenNow(opennowsw.isChecked());
                MainActivity.userPreferences.setPickedRadius((double)radiusseek.getProgress());
                Utility.saveStateInSharedPreferences(getApplicationContext(), MainActivity.userPreferences);

                Intent searchInt = new Intent();
                setResult(Activity.RESULT_OK, searchInt);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;

            case R.id.rl_bestmatch:
                fabestmatch.setText(getResources().getString(R.string.fa_circle));
                fadistance.setText(getResources().getString(R.string.fa_circle_empty));
                faratings.setText(getResources().getString(R.string.fa_circle_empty));
                famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));

                MainActivity.userPreferences.setSortBy(Utility.best_match_alias);
                break;
            case R.id.rl_distance:
                fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
                fadistance.setText(getResources().getString(R.string.fa_circle));
                faratings.setText(getResources().getString(R.string.fa_circle_empty));
                famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));

                MainActivity.userPreferences.setSortBy(Utility.distance_alias);
                break;

            case R.id.rl_rating:
                fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
                fadistance.setText(getResources().getString(R.string.fa_circle_empty));
                faratings.setText(getResources().getString(R.string.fa_circle));
                famostreviewed.setText(getResources().getString(R.string.fa_circle_empty));
                MainActivity.userPreferences.setSortBy(Utility.ratings_alias);
                break;

            case R.id.rl_mostreviewed:
                fabestmatch.setText(getResources().getString(R.string.fa_circle_empty));
                fadistance.setText(getResources().getString(R.string.fa_circle_empty));
                faratings.setText(getResources().getString(R.string.fa_circle_empty));
                famostreviewed.setText(getResources().getString(R.string.fa_circle));
                MainActivity.userPreferences.setSortBy(Utility.most_reviewed_alias);

                break;
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        radiusactualtv.setText(i+" meters");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
