package com.task.wifitask;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class ScannerWiFiService extends Service {
    private List<ScanResult> list_;

    private final String tag_ = "SCANNER_SERVICE";

    private BroadcastReceiver broadcastReceiver_;

    @Override
    public void onCreate() {
        final WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);

        if (wifiManager.getWifiState() > 0) {

            broadcastReceiver_ = new BroadcastReceiver() {
                @SuppressLint("MissingPermission")
                public void onReceive(Context Context, Intent Intent) {
                    list_ =  wifiManager.getScanResults();

                    Log.e(tag_, ScannerWiFiService.this.list_.toString());

                    sendListToBroadcast(list_,new Intent(getResources().getString(R.string.action_find)));
                }
            };

            registerReceiver(broadcastReceiver_,
                    new IntentFilter("android.net.wifi.SCAN_RESULTS"));

            wifiManager.startScan();
        }
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver_);

        Log.e(tag_, "Служба распущена");
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(tag_, "Служба запущена");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void sendListToBroadcast(List<ScanResult> list, Intent intent){
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.serializable_tag), (Serializable)list);
        intent.putExtra(getResources().getString(R.string.bundle_tag), bundle);
        sendBroadcast(intent);

    }

}

