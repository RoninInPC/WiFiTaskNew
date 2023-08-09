package com.task.wifitask.WiFiElement;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.task.wifitask.Connection.ConnectToWiFi;
import com.task.wifitask.Connection.ConnectToWiFiNew;
import com.task.wifitask.Connection.ConnectionTry;
import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.WiFiElement.Brut.BrutThread;

public class WiFiPresenter implements WiFiContract.Presenter<WiFiInfo> {

    private final WiFiContract.Model<WiFiInfo> model_;

    private final WiFiContract.View view_;

    private final Context context_;

    private BrutThread brutThread_;


    public WiFiPresenter(@NonNull WiFiContract.Model<WiFiInfo> model,
                         @NonNull WiFiContract.View view,
                         @NonNull Context context) {
        this.model_ = model;
        this.view_ = view;
        this.context_ = context;
    }

    @Override
    public boolean connect(WiFiInfo wiFiInfo, String password) {
        ConnectionTry connectionTry = android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.Q ?
        new ConnectToWiFi(wiFiInfo, context_) :
        new ConnectToWiFiNew(wiFiInfo,context_);

        boolean connect = connectionTry.tryConnectWiFi(password);
        if(connect)
            model_.update(wiFiInfo);
        return connect;
    }

    @Override
    public void brut(WiFiInfo wiFiInfo) {
        brutThread_ = new BrutThread(wiFiInfo,context_,"rockyou.txt",view_,model_);
        brutThread_.start();
    }

    @Override
    public void cancelBrut() {
        brutThread_.interrupt();
        brutThread_ = null;
    }


}
