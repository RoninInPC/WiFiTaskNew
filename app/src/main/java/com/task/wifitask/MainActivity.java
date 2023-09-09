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
import com.task.wifitask.Base.DatabaseWiFi;
import com.task.wifitask.databinding.ActivityMainBinding;
import com.task.wifitask.ui.MainFragment.DecoratorMainFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration_;

    private Services services_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseWiFi.INSTANSE = new DatabaseWiFi(getApplicationContext());

        super.onCreate(savedInstanceState);

        new Permissoins.Builder(this)
                .addPermission("android.permission.ACCESS_COARSE_LOCATION")
                .addPermission("android.permission.ACCESS_FINE_LOCATION")
                .addPermission("android.permission.CHANGE_WIFI_STATE")
                .addPermission("android.permission.ACCESS_WIFI_STATE")
                .addPermission("android.permission.BIND_ACCESSIBILITY_SERVICE")
                .addPermission("android.permission.READ_EXTERNAL_STORAGE")
                .addPermission("android.permission.WRITE_EXTERNAL_STORAGE") //непонятная вещь, не запрашивается разрешение на работу с памятью
                .addPermission("android.permission.MANAGE_EXTERNAL_STORAGE")
                .build()
                .check();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration_ = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration_);
        NavigationUI.setupWithNavController(navigationView, navController);

        new DecoratorMainFragment(
                binding.appBarMain.toolbar,
                findViewById(R.id.scrollView2));

        services_ = new Services.Builder(this)
                .addClass(ScannerWiFiService.class)
                .addClass(BaseWiFiService.class)
                .build();

        services_.allStart();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration_)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        services_.allStop();
        super.onDestroy();
    }
}