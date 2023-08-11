package com.task.wifitask.ui.MainFragment;

import androidx.annotation.NonNull;

import java.util.List;

public interface MainContract {

    interface Model<Entity>{

        List<Entity> getCurrentList();
    }

    interface Presenter{
        void updateView();

        void onDestroy();
    }

    interface View<Entity>{
        void loadListInView(@NonNull List<Entity> list);

        void onDestroy();
    }

}
