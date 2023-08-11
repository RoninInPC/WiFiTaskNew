package com.task.wifitask.WiFiElement.Brut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.task.wifitask.Connection.ConnectToWiFi;
import com.task.wifitask.Connection.ConnectToWiFiNew;
import com.task.wifitask.Connection.ConnectionTry;
import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.WiFiElement.WiFiContract;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class BrutThread extends Thread{

    private final WiFiInfo wiFiInfo_;

    private final Context context_;

    private final String fileName_;

    private final WiFiContract.View view_;

    private final WiFiContract.Model<WiFiInfo> model_;

    private InputStream inputStream_;

    private Scanner scanner_;

    @SuppressLint("HandlerLeak")
    private final Handler handler_ =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Log.e("updateProgressDialog", (String) msg.obj);
           view_.updateProgressDialog((String) msg.obj);
        }
    };

    public BrutThread(@NonNull WiFiInfo wiFiInfo,
                      @NonNull Context context,
                      @NonNull String fileName,
                      @NonNull WiFiContract.View view,
                      @NonNull WiFiContract.Model<WiFiInfo> model) {
        this.wiFiInfo_ = wiFiInfo;
        this.context_ = context;
        this.fileName_ = fileName;
        this.view_ = view;
        this.model_ = model;
    }


    @Override
    public void run() {
        ConnectionTry connectionTry = //android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ?
                new ConnectToWiFi(wiFiInfo_, context_); //:
                //new ConnectToWiFiNew(wiFiInfo_,context_);
        try {
            inputStream_ = context_.getApplicationContext().getAssets().open(fileName_);

            scanner_ = new Scanner(inputStream_);
            while (scanner_.hasNextLine()) {
                String[] arrayOfString = scanner_.nextLine().split(" ");

                for (String s : arrayOfString) {
                    //Log.e("runThread",  s);

                    Message message = handler_.obtainMessage();
                    message.obj = s;
                    handler_.sendMessage(message);

                    if (connectionTry.tryConnectWiFi(s)) {
                        model_.update(wiFiInfo_);
                        view_.updatePassword(wiFiInfo_.getPassword());
                    }
                }
            }
            scanner_.close();
            inputStream_.close();
        } catch (Exception e) {
        }

    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            scanner_.close();
            inputStream_.close();
        } catch (Exception e) {
        }
    }
}
