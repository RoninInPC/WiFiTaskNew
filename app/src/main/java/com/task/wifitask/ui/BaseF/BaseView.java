package com.task.wifitask.ui.BaseF;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.Factories.DialogFactory;

import java.util.List;

public class BaseView implements BaseContract.View<WiFiInfo> {

    private LinearLayout linearLayout_;

    private AppCompatButton appCompatButton_;

    private Context context_;

    private BaseContract.Presenter presenter_;

    public BaseView(@NonNull LinearLayout linearLayout,
                    @NonNull AppCompatButton appCompatButton,
                    @NonNull Context context) {
        linearLayout_ = linearLayout;
        appCompatButton_ = appCompatButton;
        context_ = context;
        presenter_ = new BasePresenter(this,new BaseModel(),context);


        appCompatButton_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataBase();
            }
        });
    }


    @Override
    public void loadDataBase() {
        presenter_.loadDataBase();
    }

    @Override
    public void loadListInView(@NonNull List<WiFiInfo> list) {
        if (list == null)
            return;
        linearLayout_.removeAllViews();

        Log.e("RESULT", list.toString());

        list.stream().allMatch(wiFiInfo ->{
            linearLayout_.addView(DecoratorBaseFragment.makeTextView(context_, wiFiInfo.toString()));

            return true;
        });

    }

    @Override
    public void viewLoadDialog() {
        Dialog dialog = DialogFactory.createDialog("Выгружено в Downloads",context_);
    }

    @Override
    public void onDestroy() {
        presenter_.onDestroy();
    }


}
