package com.task.wifitask.WiFiElement;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.Base.DatabaseWiFi;

public class WiFiModel implements WiFiContract.Model<WiFiInfo> {

    @Override
    public void update(WiFiInfo wiFiInfo) {
        DatabaseWiFi.INSTANSE.update(wiFiInfo);
    }
}
