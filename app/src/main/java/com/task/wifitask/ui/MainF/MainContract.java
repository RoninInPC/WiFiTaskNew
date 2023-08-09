package com.task.wifitask.ui.MainF;

import androidx.annotation.NonNull;

import java.util.List;

public interface MainContract {

    interface Model<Entity>{

        List<Entity> getCurrentList();
    }

    interface Presenter{
        void updateViewWithTakeIntent();

        void onDestroy();
    }

    interface View<Entity>{
        void loadListInView(@NonNull List<Entity> list);

        void onDestroy();
    }

}
