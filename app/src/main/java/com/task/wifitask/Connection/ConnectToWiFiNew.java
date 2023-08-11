package com.task.wifitask.Connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.task.wifitask.Entity.WiFiInfo;

//при тестировании выяснилось, что WifiNetworkSpecifier выдаёт ошибку при работе, при разных настройках на API>=29 (android 10).
//Данный класс является ненадёжным при подключении и остаётся лишь для альтернативной реализации подключения к wifi.
public class ConnectToWiFiNew extends ConnectToWiFi { //используя WifiNetworkSpecifier
    public ConnectToWiFiNew(WiFiInfo wiFiInfo, Context context) {
        super(wiFiInfo, context);
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean connectOpen() {
        WifiNetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
                .setSsid(wiFiInfo_.getSSID())
                .setBssid(MacAddress.fromString(wiFiInfo_.getBSSID()))
                .build();
        return connected(specifier);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean connected(WifiNetworkSpecifier specifier){
        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(specifier)
                .build();

        ConnectivityManager connectivityManager = (ConnectivityManager) context_.
                getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        final boolean[] connect = {false};
        connectivityManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                connect[0] = true;
            }

            @Override
            public void onUnavailable() {
                connect[0] = false;
            }
        });
        return connect[0];
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean connectWPA() {
         WifiNetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
                .setSsid(wiFiInfo_.getSSID())
                .setWpa2Passphrase(wiFiInfo_.getPassword())
                .setBssid(MacAddress.fromString(wiFiInfo_.getBSSID()))
                .build();
        return connected(specifier);
    }
}
