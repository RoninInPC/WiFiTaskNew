package com.task.wifitask.ui.BaseFragment.LoadBase;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.ui.BaseFragment.BaseContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoadBaseThread extends Thread {
    private final BaseContract.Model<WiFiInfo> model_;
    private final BaseContract.View<WiFiInfo> view_;

    private final String fileNameNew_;

    @SuppressLint("HandlerLeak")
    private final Handler handler_ = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            view_.viewLoadDialog();
        }
    };

    public LoadBaseThread(BaseContract.Model<WiFiInfo> model, BaseContract.View<WiFiInfo> view, String fileNameNew) {
        model_ = model;
        view_ = view;
        fileNameNew_ = fileNameNew;
    }

    @Override
    public void run() {
        try {
            File file1 = new File(model_.getPath());
            Log.e("PATH",model_.getPath());
            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileNameNew_);
            FileInputStream fileInputStream = new FileInputStream(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] arrayOfByte = new byte[1024];
            while (true) {
                int size = fileInputStream.read(arrayOfByte);
                if (size > 0) {
                    fileOutputStream.write(arrayOfByte, 0, size);
                    continue;
                }else {
                    handler_.sendMessage(handler_.obtainMessage());
                    break;
                }

            }
        } catch (IOException iOException) {
            Log.e("MoveFileTask", "", iOException);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
