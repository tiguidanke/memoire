package com.example.memo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.senlonase.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashScreen extends AppCompatActivity {
    @BindView(R.id.rl_retry)
    CardView rlRetry;
    @BindView(R.id.btn_retry)
    Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        new Utils().setStatusColor(getWindow());
        rlRetry.setVisibility(View.GONE);

        Thread chrono = new Thread() {
            public void run() {
                try{
                    sleep(4000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    checkInternetConnection();
                }
            }
        };
        chrono.start();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }
    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        assert cm != null;
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            this.rlRetry.setVisibility(View.VISIBLE);

        }
    }

}
