package com.e.mobilebanking;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class TestApplication extends Application {

    public static final String APPLICATION_ID = "1B318336-3978-19F1-FF92-C04EC1B11C00";
    public static final String API_KEY = "6FA245E9-13D0-4961-81EF-3DED01603C55";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user;

    @Override
    public void onCreate(){

        super.onCreate();

        Backendless.setUrl(SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }


}
