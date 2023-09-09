package com.task.wifitask.ui.MainFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.task.wifitask.R;
import com.task.wifitask.Entity.WiFiInfo;

public class MainPresenter implements MainContract.Presenter{

    private final MainContract.View<WiFiInfo> view_;

    private final MainContract.Model<WiFiInfo> model_;

    private final Context context_;

    private final BroadcastReceiver broadcastReceiver_ = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateView();
        }
    };

    public MainPresenter(
            @NonNull MainContract.View<WiFiInfo> view,
            @NonNull MainContract.Model<WiFiInfo> model,
            @NonNull Context context) {
        this.view_ = view;
        this.model_ = model;
        this.context_ = context;

        updateView();//начальная инициализация, чтобы пользователь не видел белый экран

        context_.getApplicationContext().registerReceiver(
                broadcastReceiver_,
                new IntentFilter(context_.getResources().getString(R.string.action_base_update)));
    }

    @Override
    public void updateView() {
        view_.loadListInView(model_.getCurrentList());
    }

    @Override
    public void onDestroy() {
        context_.getApplicationContext().unregisterReceiver(broadcastReceiver_);
    }
}
