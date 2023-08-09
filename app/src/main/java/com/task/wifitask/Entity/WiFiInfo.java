package com.task.wifitask.Entity;


import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.Arrays;

public class WiFiInfo {
    private static final ArrayList<Integer> channelsFrequency = new ArrayList<Integer>(Arrays.asList(
            0, 2412, 2417, 2422, 2427,
            2432, 2437, 2442, 2447, 2452,
            2457, 2462, 2467, 2472, 2484));

    private String SSID_;

    private String BSSID_;

    private int channel_;

    private int level_;

    private String security_;

    private String operator_frendly_name_;

    private String venue_name_;

    private String password_ = "";

    public String getSSID() {
        return SSID_;
    }

    public void setSSID(String SSID) {
        this.SSID_ = SSID;
    }

    public String getBSSID() {
        return BSSID_;
    }

    public void setBSSID(String BSSID) {
        this.BSSID_ = BSSID;
    }

    public int getChannel() {
        return channel_;
    }

    public void setChannel(int channel) {
        this.channel_ = channel;
    }

    public int getLevel() {
        return level_;
    }

    public void setLevel(int level) {
        this.level_ = level;
    }

    public String getSecurity() {
        return security_;
    }

    public void setSecurity(String security) {
        this.security_ = security;
    }

    public String getOperatorFrendlyName() {
        return operator_frendly_name_;
    }

    public void setOperatorFrendlyName(String operator_frendly_name) {
        this.operator_frendly_name_ = operator_frendly_name;
    }

    public String getVenueName() {
        return venue_name_;
    }

    public void setVenueName(String venue_name) {
        this.venue_name_ = venue_name;
    }

    public String getPassword() {
        return password_;
    }

    public void setPassword(String password) {
        this.password_ = password;
    }

    public WiFiInfo() {}

    public WiFiInfo(ScanResult ScanResult) {
        SSID_ = ScanResult.SSID;
        BSSID_ = ScanResult.BSSID;
        channel_ = getChannelFromFrequency(ScanResult.frequency);
        level_ = WifiManager.calculateSignalLevel(ScanResult.level, 5);
        security_ = getSecurity(ScanResult.capabilities);
        operator_frendly_name_ = (String)ScanResult.operatorFriendlyName;
        venue_name_ = (String)ScanResult.venueName;
    }

    public static int getChannelFromFrequency(int Int) {
        return channelsFrequency.indexOf(Int);
    }

    public static String getSecurity(String String) {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("WEP","WPA","WPA2","IEEE8021X"));

        for (int i = arrayList.size() - 1; i >= 0; i--) {
            if (String.contains(arrayList.get(i)))
                return arrayList.get(i);
        }
        return "OPEN";
    }

    public String toString() {
        String string = "";
        String nope = "Не известно";
        if (SSID_ == null || SSID_.equals("")) {
            string += "Имя сети: " + nope+ "\n";
        } else {
            string += "Имя сети: " + SSID_ + "\n";
        }
        if (BSSID_ == null || BSSID_.equals("")) {
            string += "Адрес: " + nope+ "\n";
        } else {
            string += "Адрес: " + BSSID_ + "\n";
        }
        if (channel_ >= 0) {
            string += "Канал: " + channelsFrequency.get(channel_) + "\n";

        } else {
            string += "Канал: " + nope+ "\n";
        }
        string +="Уровень: " + level_ + "\n";
        string += "Тип безопасности: " + security_ + "\n";
        if (operator_frendly_name_ == null || operator_frendly_name_.equals("")) {
            string += "Имя оператора точки доступа: " + nope + "\n";
        } else {
            string += "Имя оператора точки доступа:"  + operator_frendly_name_ + "\n";
        }
        if (venue_name_ == null || venue_name_.equals("")) {
            string += "Место: " + nope + "\n";
        } else {
            string += "Место: " + venue_name_ + "\n";
        }
        if (password_ == null || password_.equals("")) {
            string += "Пароль: " + nope + "\n";
        } else {
            string += "Пароль: " + password_ + "\n";
        }
        return string;
    }

    public static class Builder {
        private final WiFiInfo result_ = new WiFiInfo();

        public Builder BSSID(String String) {
            result_.BSSID_ = String;
            return this;
        }

        public Builder Channel(int Int) {
            result_.channel_ = Int;
            return this;
        }

        public Builder Level(int Int) {
            result_.level_ = Int;
            return this;
        }

        public Builder Operator(String String) {
            result_.operator_frendly_name_ = String;
            return this;
        }

        public Builder Password(String String) {
            result_.password_ = String;
            return this;
        }

        public Builder SSID(String String) {
            result_.SSID_ = String;
            return this;
        }

        public Builder Security(String String) {
            result_.security_ = String;
            return this;
        }

        public Builder VenueName(String String) {
            result_.venue_name_ = String;
            return this;
        }

        public WiFiInfo build() {
            return result_;
        }
    }
}
