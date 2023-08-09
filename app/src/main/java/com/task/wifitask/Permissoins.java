package com.task.wifitask;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissoins {

    private Activity activity_;
    private List<String> namesOfPermission_ = new ArrayList<>();

    private static int permissonCode_ = 100;

    public Permissoins(Activity activity) {
        this.activity_ = activity;
    }

    public void setNamesOfPermission(List<String> namesOfPermission) {
        namesOfPermission_.clear();
        namesOfPermission_ = namesOfPermission;
    }

    public void addPermisson(String permissionName){
        namesOfPermission_.add(permissionName);
    }

    public void check(){
        for(String permission : namesOfPermission_){
            checkPermission(permission, permissonCode_++);
        }
    }

    private void checkPermission(String permissionName, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity_.getApplicationContext(), permissionName) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(activity_, new String[] { permissionName }, requestCode);
    }
    public static class Builder{
        private Permissoins permissoins_;

        public Builder(Activity activity){
            permissoins_ = new Permissoins(activity);
        }

        public Builder addPermission(String permissionName){
            permissoins_.addPermisson(permissionName);
            return this;
        }

        public Permissoins build(){
            return permissoins_;
        }

    }
}
