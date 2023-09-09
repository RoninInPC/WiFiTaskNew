package com.task.wifitask.Factories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogFactory {
    public enum DialogType{
        CONNECT,
        NO_CONNECT,
        EMPTY
    };

    public static Dialog createDialog(DialogType dialogType, Context context){
        Dialog dialog = null;
        switch (dialogType){
            case CONNECT:
                dialog = connectDialog(context);
                break;
            case NO_CONNECT:
                dialog = unConnectDialog(context);
                break;
            case EMPTY:
                dialog = createMessageDialog(context,"Не известно");
                break;
        }
        return dialog;
    }
    public static Dialog createDialog(String message, Context context){
        return createMessageDialog(context, message);
    }
    private static Dialog connectDialog(Context context){
        return createMessageDialog(context, "Подключено");
    }

    private static Dialog unConnectDialog(Context context){
        return createMessageDialog(context, "Отклонено");
    }

    private static Dialog createMessageDialog(Context context, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton("Принято", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface DialogInterface, int Int) {
                DialogInterface.cancel();
            }
        }).setMessage(Message);
        return (Dialog) builder.create();
    }
}
