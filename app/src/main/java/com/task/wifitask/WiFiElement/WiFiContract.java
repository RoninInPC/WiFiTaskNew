package com.task.wifitask.WiFiElement;

//WiFiView имеет набор функций: получить информацию о сети, подключение к ней и брут,
// при этом презентер в процессе подключения/брута может обновить progressDialog и текстовое поле пароля
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
