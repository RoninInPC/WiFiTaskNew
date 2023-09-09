package com.task.wifitask.Connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.task.wifitask.Entity.WiFiInfo;

import java.util.List;

public class ConnectToWiFi implements ConnectionTry {
    protected WiFiInfo wiFiInfo_;

    protected Context context_;

    public ConnectToWiFi(WiFiInfo wiFiInfo, Context context) {
        this.wiFiInfo_ = wiFiInfo;
        this.context_ = context;
    }

    @Override
    @SuppressLint("MissingPermission")
    public boolean connectOpen() {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + wiFiInfo_.getSSID() + "\"";

            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfiguration.allowedAuthAlgorithms.clear();
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            WifiManager wifiManager = (WifiManager) context_.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.addNetwork(wifiConfiguration) != -1 &&
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    @SuppressLint("MissingPermission")
    public boolean connectWEP() {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + wiFiInfo_.getSSID() + "\"";
            wifiConfiguration.status = 2;
            wifiConfiguration.priority = 40;

            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            
            if (wiFiInfo_.getPassword().matches("^[0-9a-fA-F]+$")) {
                wifiConfiguration.wepKeys[0] = wiFiInfo_.getPassword();
            } else {
                wifiConfiguration.wepKeys[0] = "\"".concat(wiFiInfo_.getPassword()).concat("\"");
            }
            wifiConfiguration.wepTxKeyIndex = 0;
            WifiManager wifiManager = (WifiManager)context_.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.addNetwork(wifiConfiguration) != -1 &&
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    @SuppressLint("MissingPermission")
    public boolean connectWPA() {
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + wiFiInfo_.getSSID() + "\"";
            wifiConfiguration.status = 2;
            wifiConfiguration.priority = 40;

            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            
            wifiConfiguration.preSharedKey = "\"" + wiFiInfo_.getPassword() + "\"";
            WifiManager wifiManager = (WifiManager)context_.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.addNetwork(wifiConfiguration) != -1 &&
                    findConnect(wifiManager.getConfiguredNetworks(), wifiConfiguration.SSID, wifiManager);
        } catch (Exception exception) {
            return false;
        }
    }

    protected boolean connectWiFi() {
        switch (wiFiInfo_.getSecurity()) {
            case "WPA":
            case "WPA2":
                return connectWPA();
            case "WEP":
                return connectWEP();
            default:
                return connectOpen();
        }
    }

    private boolean findConnect(List<WifiConfiguration> list, String SSID, WifiManager wifiManager) {
        for (WifiConfiguration wifiConfiguration : list) {
            if (wifiConfiguration.SSID != null && wifiConfiguration.SSID.equals(SSID)) {
                String str= wifiConfiguration.toString();
                if (str.contains("NETWORK_SELECTION_DISABLED_BY_WRONG_PASSWORD"))
                    return false;
                wifiManager.disconnect();
                wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                return wifiManager.reconnect();
            }
        }
        return false;
    }

    @Override
    public boolean tryConnectWiFi(String passwordTry) {
        wiFiInfo_.setPassword(passwordTry);
        boolean connect = connectWiFi();
        if (!connect)
            wiFiInfo_.setPassword("");
        return connect;
    }


}
