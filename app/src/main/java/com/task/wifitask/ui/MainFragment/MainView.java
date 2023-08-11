package com.task.wifitask.ui.MainFragment;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.task.wifitask.WiFiElement.WiFiView;
import com.task.wifitask.Entity.WiFiInfo;

import java.util.Collections;
import java.util.List;

public class MainView implements MainContract.View<WiFiInfo> {

    private LinearLayout linearLayout_;

    private Context context_;

    private MainContract.Presenter presenter_;

    public MainView(@NonNull LinearLayout linearLayout, @NonNull Context context) {
        linearLayout_ = linearLayout;
        context_ = context;

        presenter_ = new MainPresenter(this,new MainModel(),context_);

    }

    @Override
    public void loadListInView(@NonNull List<WiFiInfo> list) {
        linearLayout_.removeAllViews();

        list.stream().allMatch(wiFiInfo -> {
            WiFiView wiFiView = new WiFiView(context_);

            wiFiView.ChangeElementWithWifiInfo(wiFiInfo);

            linearLayout_.addView(wiFiView);
            return true;
        });
    }

    @Override
    public void onDestroy() {
        presenter_.onDestroy();
    }
}
