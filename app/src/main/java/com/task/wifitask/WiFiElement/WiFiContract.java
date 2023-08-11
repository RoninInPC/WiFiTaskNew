package com.task.wifitask.WiFiElement;

public interface WiFiContract {

    interface Model<Entity>{
        void update(Entity entity);
    }

    interface Presenter<Entity>{
        boolean connect(Entity entity, String password);

        void brut(Entity entity);

        void cancelBrut();
    }

    interface View{
        void updatePassword(String password);

        void updateProgressDialog(String title);
        boolean info();
        void connect();
        void brut();

    }

}
