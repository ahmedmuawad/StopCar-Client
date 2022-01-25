package com.stopgroup.stopcar.client.Helper;

import android.app.Activity;

public class CurrentActivity {
    private static Activity instance = null;
    public static Activity getInstance() {
        return instance;
    }
    public static void setInstance(Activity activity) {
        CurrentActivity.instance = activity;
    }
    public CurrentActivity(Activity activity) {
        CurrentActivity.instance = activity;
    }
}
