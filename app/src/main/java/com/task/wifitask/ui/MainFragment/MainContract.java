package com.task.wifitask.ui.MainFragment;

import androidx.annotation.NonNull;

import java.util.List;
//основной набор функций MainFragment заключается в том, чтобы получать сообщение от BaseWiFiService и обновлять View
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
