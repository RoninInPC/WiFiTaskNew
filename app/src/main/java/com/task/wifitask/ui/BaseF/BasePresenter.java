package com.task.wifitask.ui.BaseF;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.task.wifitask.R;
import com.task.wifitask.Entity.WiFiInfo;

public class BasePresenter implements BaseContract.Presenter{

    private final BaseContract.View<WiFiInfo> view_;

    private final BaseContract.Model<WiFiInfo> model_;

    private final Context context_;

    private final BroadcastReceiver broadcastReceiver_ = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateViewWithTakeIntent();
        }
    };

    public BasePresenter(@NonNull BaseContract.View<WiFiInfo> view,
                         @NonNull BaseContract.Model<WiFiInfo> model,
                         @NonNull Context context) {
        view_ = view;
        model_ = model;
        context_ = context;

        context_.getApplicationContext().registerReceiver(
                broadcastReceiver_,
                new IntentFilter(context_.getResources().getString(R.string.action_base_update))
                );
    }



    @Override
    public void updateViewWithTakeIntent() {
        view_.loadListInView(model_.getList());
    }

    @Override
    public void loadDataBase() {
       String path = model_.getPath();
    }

    @Override
    public void onDestroy() {
        context_.getApplicationContext().unregisterReceiver(broadcastReceiver_);
    }
}
