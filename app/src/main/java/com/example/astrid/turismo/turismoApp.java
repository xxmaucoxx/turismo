package com.example.astrid.turismo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class turismoApp extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
