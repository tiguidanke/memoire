package com.example.memo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public static Context getAppContext() {
        return App.context;
    }

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }
}