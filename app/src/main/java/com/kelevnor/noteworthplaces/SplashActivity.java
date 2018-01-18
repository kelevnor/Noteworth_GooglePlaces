package com.kelevnor.noteworthplaces;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {
    Typeface openSansRegular, openSansSemiBold;
    TextView vs, centerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        vs = findViewById(R.id.tv_vs);
        centerview = findViewById(R.id.tv_center);

        openSansSemiBold = Typeface.createFromAsset(getAssets(),"fonts/Open_Sans_Regular.ttf");
        openSansRegular = Typeface.createFromAsset(getAssets(),"fonts/Open_Sans_Regular.ttf");

        vs.setTypeface(openSansRegular);
        centerview.setTypeface(openSansSemiBold);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

            }
        }, 1500);   //5 se
    }
}

