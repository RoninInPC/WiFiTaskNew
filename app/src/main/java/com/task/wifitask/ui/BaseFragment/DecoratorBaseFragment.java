package com.task.wifitask.ui.BaseFragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.task.wifitask.R;

public class DecoratorBaseFragment {

    public static final float BUTTON_SIZE_PERCENT = 0.1F;

    public static final float TEXT_VIEW_SIZE_PERCENT = 0.5F;

    public static final float TEXT_SIZE_COEF = 50;

    public static final float TOOLBAR_SIZE_PERCENT = 0.1F;


    public DecoratorBaseFragment(@NonNull Toolbar toolbar,
                                 @NonNull ScrollView scrollView,
                                 @NonNull AppCompatButton appCompatButton) {
        int heightPixels = (Resources.getSystem().getDisplayMetrics()).heightPixels;

        ViewGroup.LayoutParams layoutParams2 =toolbar.getLayoutParams();
        layoutParams2.height = (int)(heightPixels * TOOLBAR_SIZE_PERCENT);
        toolbar.setLayoutParams(layoutParams2);


        ViewGroup.LayoutParams layoutParams1 = scrollView.getLayoutParams();
        layoutParams1.height = (int)(heightPixels * (1- TOOLBAR_SIZE_PERCENT - BUTTON_SIZE_PERCENT));
        scrollView.setLayoutParams(layoutParams1);


        layoutParams1 = appCompatButton.getLayoutParams();
        layoutParams1.height = (int)(heightPixels * BUTTON_SIZE_PERCENT);
        appCompatButton.setLayoutParams(layoutParams1);
    }

    public static TextView makeTextView(@NonNull Context context,@NonNull String text) {
        TextView textView = new TextView(context);

        int heightPixels = (Resources.getSystem().getDisplayMetrics()).heightPixels;
        textView.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setBackgroundResource(R.drawable.text_view_restangle);

        int textSize = (int)(heightPixels * TEXT_VIEW_SIZE_PERCENT / TEXT_SIZE_COEF);

        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(textSize);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);

        return textView;
    }
}

