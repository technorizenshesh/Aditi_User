package lk.aditi.ecom.utils;

import android.app.Application;

public class BaseClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsManager.initialize(this);
    }
}
