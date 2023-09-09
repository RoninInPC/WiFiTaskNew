package com.task.wifitask.ui.BaseFragment;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.Base.DatabaseWiFi;

import java.util.List;

public class BaseModel implements BaseContract.Model<WiFiInfo> {

    @Override
    public List<WiFiInfo> getList() {
        return DatabaseWiFi.INSTANSE.toList();
    }

    @Override
    public String getPath() {
        return DatabaseWiFi.INSTANSE.getPathDatabase();
    }
}
