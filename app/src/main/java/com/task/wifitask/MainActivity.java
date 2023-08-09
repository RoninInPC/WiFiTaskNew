package com.task.wifitask;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.task.wifitask.Base.BaseWiFiService;
import com.task.wifitask.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Permissoins.Builder(this)
                .addPermission("android.permission.ACCESS_COARSE_LOCATION")
                .addPermission("android.permission.ACCESS_FINE_LOCATION")
                .addPermission("android.permission.CHANGE_WIFI_STATE")
                .addPermission("android.permission.ACCESS_WIFI_STATE")
                .addPermission("android.permission.BIND_ACCESSIBILITY_SERVICE")
                .addPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                .addPermission("android.permission.MANAGE_EXTERNAL_STORAGE")
                .build()
                .check();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        startService(new Intent(this, ScannerWiFiService.class));
        startService(new Intent(this, BaseWiFiService.class));
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ScannerWiFiService.class));
        stopService(new Intent(this, BaseWiFiService.class));
    }
}