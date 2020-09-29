package com.example.memo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senlonase.databinding.ActivityDetailsActuBinding;
import com.example.senlonase.utils.Utils;

import java.util.Objects;

public class DetailsActuActivity extends AppCompatActivity {
    ActivityDetailsActuBinding binding;
    String mimeType = "text/html";
    String encoding = "UTF-8";
    String yTUrl;
    Utils utils;
    WebSettings webSettings;
    private int click = 0,fontSize;
    private static final int RECOVERY_DIALOG_REQUEST = 10;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsActuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        utils = new Utils();
        utils.setStatusColor(getWindow());
        binding.titletv.setText(getIntent().getStringExtra("titre"));
        binding.descwv.loadDataWithBaseURL("", Objects.requireNonNull(getIntent().getStringExtra("desc")), mimeType, encoding, "");
        binding.descwv.setInitialScale(1);
        webSettings = binding.descwv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDefaultFontSize(40);
        fontSize = webSettings.getDefaultFontSize();
        binding.toolbarMain.ivBack.setOnClickListener(v -> onBackPressed());

        binding.toolbarMain.typo.setOnClickListener(v -> {
            if (click == 0) {
                fontSizePlus();
                click=1;
            }else if (click == 1){
                fontSizePlus();
                click=2;
            }else if (click == 2){
                fontSizeMinus();
                click=3;
            }else if (click == 3){
                fontSizeMinus();
                click=0;
            }
        });
    }
    private void OpenShare(String title,String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, title+" | via DakarActu\n"+link);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Partagez via");
        startActivity(sendIntent);
    }
    private void fontSizePlus() {
        fontSize=fontSize+15;
        this.changeFontSize(fontSize);
    }

    private void fontSizeMinus() {
        fontSize=fontSize-15;
        this.changeFontSize(fontSize);
    }

    private void changeFontSize(int value) {
        webSettings.setDefaultFontSize(value);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
