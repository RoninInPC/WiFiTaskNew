package com.task.wifitask.ui.MainFragment;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.Base.DatabaseWiFi;

import java.util.List;

public class MainModel implements MainContract.Model<WiFiInfo> {

    @Override
    public List<WiFiInfo> getCurrentList() {
        return DatabaseWiFi.INSTANSE.toCurrentList();
    }
}
