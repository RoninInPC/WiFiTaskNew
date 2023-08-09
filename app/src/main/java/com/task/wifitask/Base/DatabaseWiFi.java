package com.task.wifitask.Base;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.task.wifitask.Entity.WiFiInfo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWiFi implements RepositoryManipulation<WiFiInfo>{

    public static DatabaseWiFi INSTANSE;
    private static SQLiteDatabase sqLiteDatabase_;


    public DatabaseWiFi(@NonNull Context context) {

        sqLiteDatabase_ = new WiFiDBHelper(context).getReadableDatabase();
    }

    @Override
    public void cleverInsert(@NonNull WiFiInfo wiFiInfo) {
        if (contains(wiFiInfo)) {
            update(wiFiInfo);
            return;
        }
        insert(wiFiInfo);
    }

    @Override
    public boolean contains(@NonNull WiFiInfo wiFiInfo){
        return contains(WiFiDBHelper.KEY_SSID, wiFiInfo.getSSID());
    }

    public boolean contains(@NonNull String columnName,@NonNull String name) {
        Cursor cursor = sqLiteDatabase_.query(
                WiFiDBHelper.TABLE_NAME,
                new String[] { columnName },
                String.format("%s = ?",columnName),
                new String[] {name},
                null,
                null,
                null);
        boolean bool = cursor.moveToFirst();
        cursor.close();
        return bool;
    }

    public String getPassword(@NonNull String SSID) {
        Cursor cursor = sqLiteDatabase_.query(
                WiFiDBHelper.TABLE_NAME,
                null,
                String.format("%s = ?",WiFiDBHelper.KEY_SSID),
                new String[] { SSID },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int indexPassword = cursor.getColumnIndex(WiFiDBHelper.KEY_PASSWORD);
            String str = cursor.getString(indexPassword);
            cursor.close();
            return str;
        }
        cursor.close();
        return "";
    }

    public String getPathDatabase() {
        return sqLiteDatabase_.getPath();
    }

    @Override
    public void insert(@NonNull WiFiInfo wiFiInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WiFiDBHelper.KEY_SSID, wiFiInfo.getSSID());
        contentValues.put(WiFiDBHelper.KEY_BSSID, wiFiInfo.getBSSID());
        contentValues.put(WiFiDBHelper.KEY_CHANNEL, wiFiInfo.getChannel());
        contentValues.put(WiFiDBHelper.KEY_LEVEL, wiFiInfo.getLevel());
        contentValues.put(WiFiDBHelper.KEY_SECURITY, wiFiInfo.getSecurity());
        contentValues.put(WiFiDBHelper.KEY_OPERATOR, wiFiInfo.getOperatorFrendlyName());
        contentValues.put(WiFiDBHelper.KEY_VENUE, wiFiInfo.getVenueName());
        contentValues.put(WiFiDBHelper.KEY_PASSWORD, wiFiInfo.getPassword());
        contentValues.put(WiFiDBHelper.KEY_CURRENT, true);

        sqLiteDatabase_.insert(WiFiDBHelper.TABLE_NAME, null, contentValues);
    }

    @Override
    public List<WiFiInfo> toList() {
        Cursor cursor = sqLiteDatabase_.query(WiFiDBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        List<WiFiInfo> arrayList = getListFromCursor(cursor);
        cursor.close();
        return arrayList;
    }

    @Override
    public List<WiFiInfo> toCurrentList(){
        Cursor cursor = sqLiteDatabase_.query(WiFiDBHelper.TABLE_NAME,
                null,
                 String.format("%s = ?",WiFiDBHelper.KEY_CURRENT),
                 new String[]{String.valueOf(1)},
                null,
                null,
                null);
        List<WiFiInfo> arrayList = getListFromCursor(cursor);
        cursor.close();
        return arrayList;
    }

    private List<WiFiInfo> getListFromCursor(@NonNull Cursor cursor){
        if(cursor.getColumnCount()<WiFiDBHelper.COLUMN_COUNT)
            return null;

        ArrayList<WiFiInfo> arrayList = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                int indexSSID = cursor.getColumnIndex(WiFiDBHelper.KEY_SSID);
                int indexBSSID = cursor.getColumnIndex(WiFiDBHelper.KEY_BSSID);
                int indexChannel = cursor.getColumnIndex(WiFiDBHelper.KEY_CHANNEL);
                int indexLevel = cursor.getColumnIndex(WiFiDBHelper.KEY_LEVEL);
                int indexSecurity = cursor.getColumnIndex(WiFiDBHelper.KEY_SECURITY);
                int indexOperator = cursor.getColumnIndex(WiFiDBHelper.KEY_OPERATOR);
                int indexVenue = cursor.getColumnIndex(WiFiDBHelper.KEY_VENUE);
                int indexPassword = cursor.getColumnIndex(WiFiDBHelper.KEY_PASSWORD);

                do {
                    arrayList.add((new WiFiInfo.Builder()).
                            SSID(cursor.getString(indexSSID)).
                            BSSID(cursor.getString(indexBSSID)).
                            Channel(cursor.getInt(indexChannel)).
                            Level(cursor.getInt(indexLevel)).
                            Security(cursor.getString(indexSecurity)).
                            Operator(cursor.getString(indexOperator)).
                            VenueName(cursor.getString(indexVenue)).
                            Password(cursor.getString(indexPassword)).
                            build());
                } while (cursor.moveToNext());
            }
        }catch (RuntimeException exception){
            return null;
        }
        return arrayList;

    }

    @Override
    public void update(@NonNull WiFiInfo wiFiInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WiFiDBHelper.KEY_BSSID, wiFiInfo.getBSSID());
        contentValues.put(WiFiDBHelper.KEY_CHANNEL, wiFiInfo.getChannel());
        contentValues.put(WiFiDBHelper.KEY_LEVEL, wiFiInfo.getLevel());
        contentValues.put(WiFiDBHelper.KEY_SECURITY, wiFiInfo.getSecurity());
        contentValues.put(WiFiDBHelper.KEY_OPERATOR, wiFiInfo.getOperatorFrendlyName());
        contentValues.put(WiFiDBHelper.KEY_VENUE, wiFiInfo.getVenueName());
        contentValues.put(WiFiDBHelper.KEY_PASSWORD, wiFiInfo.getPassword());
        contentValues.put(WiFiDBHelper.KEY_CURRENT, true);

        sqLiteDatabase_.update(WiFiDBHelper.TABLE_NAME,
                contentValues,
                String.format("%s = ?",WiFiDBHelper.KEY_SSID),
                new String[] { wiFiInfo.getSSID() });
    }

    public void updateNotCurrentAll(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WiFiDBHelper.KEY_CURRENT,false);
        sqLiteDatabase_.update(WiFiDBHelper.TABLE_NAME,
                contentValues,
                String.format("%s = ?",WiFiDBHelper.KEY_CURRENT),
                new String[] {String.valueOf(1)});
    }
}
