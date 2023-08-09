package com.task.wifitask.ui.BaseF;

import androidx.annotation.NonNull;

import java.util.List;

public interface BaseContract {

    interface Model<Entity> {
        List<Entity> getList();
        String getPath();
    }

    interface Presenter{

        void updateViewWithTakeIntent();

        void loadDataBase();

        void onDestroy();

    }

    interface View<Entity>{
        void loadDataBase();

        void loadListInView(@NonNull List<Entity> list);

        void viewLoadDialog();

        void onDestroy();

    }

}
