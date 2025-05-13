package com.example.sensors;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class MainApplication extends Application {

    private final String MAPKIT_API_KEY = "911aa1cb-5105-4a35-84e9-958852b0a329";

    @Override
    public void onCreate() {
        super.onCreate();
        // Set the api key before calling initialize on MapKitFactory.
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}