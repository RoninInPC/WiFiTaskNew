package com.task.wifitask.Base;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.task.wifitask.R;
import com.task.wifitask.Entity.WiFiInfo;

import java.util.List;

//Данный сервис принимает информацию о сетях и обновляет базу данных, при этом помечая текущие сети и остальные
public class BaseWiFiService extends Service {

    private final String tag_ = "BASE_SERVICE";

    private BroadcastReceiver broadcastReceiver_;

    @Override
    public void onCreate() {
        DatabaseWiFi.INSTANSE = new DatabaseWiFi(getApplicationContext());

        broadcastReceiver_ = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> list_ = (List<ScanResult>) intent.
                        getBundleExtra(getResources().getString(R.string.bundle_tag)).
                        getSerializable(getResources().getString(R.string.serializable_tag));

                DatabaseWiFi.INSTANSE.updateNotCurrentAll();

                updateDatabase(list_);

                sendBroadcast(new Intent(getResources().getString(R.string.action_base_update)));
            }
        };

        registerReceiver(broadcastReceiver_,
                new IntentFilter(getResources().getString(R.string.action_find)));

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateDatabase(@NonNull List<ScanResult> scanResultList){
        scanResultList.stream().allMatch( scanResult ->{
            WiFiInfo wiFiInfo = new WiFiInfo(scanResult);

            wiFiInfo.setPassword(
                    DatabaseWiFi.INSTANSE.getPassword(wiFiInfo.getSSID())
            );

            DatabaseWiFi.INSTANSE.cleverInsert(wiFiInfo);

            return true;
        });
    }
}
