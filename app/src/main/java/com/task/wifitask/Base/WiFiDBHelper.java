package com.task.wifitask.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class WiFiDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wifiDB";

    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "WiFiInfo";

    public static final String KEY_ID = "_id";

    public static final String KEY_SSID = "SSID";

    public static final String KEY_BSSID = "BSSID";

    public static final String KEY_CHANNEL = "channel";

    public static final String KEY_LEVEL = "level";

    public static final String KEY_SECURITY = "security";

    public static final String KEY_OPERATOR = "operator";

    public static final String KEY_VENUE = "venue";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_CURRENT = "current";

    public static final int COLUMN_COUNT = 10;


    public WiFiDBHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +
                TABLE_NAME + "(" +
                KEY_ID + " integer primary key," +
                KEY_SSID + " text," +
                KEY_BSSID + " text," +
                KEY_CHANNEL + " integer," +
                KEY_LEVEL + " integer," +
                KEY_SECURITY + " text," +
                KEY_OPERATOR + " text," +
                KEY_VENUE + " text," +
                KEY_PASSWORD + " text," +
                KEY_CURRENT +" integer)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

