package com.demo.androidmvparchitecture.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.lang.ref.WeakReference;

public class ConnectivityUtils {

    private final WeakReference<Context> weakReference;
    private static ConnectivityUtils instance;

    public ConnectivityUtils(Context context) {
        this.weakReference = new WeakReference<>(context);
    }

    public static ConnectivityUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectivityUtils(context);
        }
        return instance;
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    weakReference.get().getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected();
        } catch (Throwable ex){
            ex.printStackTrace();
        }
        return false;
    }
}
