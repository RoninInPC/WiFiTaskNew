package com.task.wifitask.ui.MainFragment;

import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

public class DecoratorMainFragment {
    public final float TOOLBAR_SIZE_PERCENT = 0.1F;

    public DecoratorMainFragment(@NonNull Toolbar toolbar,@NonNull ScrollView scrollView) {
        int height = (Resources.getSystem().getDisplayMetrics()).heightPixels;
        
        ViewGroup.LayoutParams layoutParams2 = toolbar.getLayoutParams();
        layoutParams2.height = (int)(height * TOOLBAR_SIZE_PERCENT);
        toolbar.setLayoutParams(layoutParams2);

        
        
        ViewGroup.LayoutParams layoutParams1 = scrollView.getLayoutParams();
        layoutParams1.height = (int)(height * (1.0F - TOOLBAR_SIZE_PERCENT));
        scrollView.setLayoutParams(layoutParams1);

    }

 
}