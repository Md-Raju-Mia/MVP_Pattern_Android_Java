package com.demo.androidmvparchitecture.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private final NetworkChangeListener listener;

    public NetworkChangeReceiver(NetworkChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            listener.onNetworkChanged(ConnectivityUtils.getInstance(context).isOnline());
        }
    }

    public interface NetworkChangeListener {
        void onNetworkChanged(boolean isConnected);
    }
}
