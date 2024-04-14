package com.app.qlns;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.app.qlns.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    TextView tvName;

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Drawable drawable = binding.appBarHome.toolbar.getNavigationIcon();
        if (drawable != null) {
            drawable.setTint(ContextCompat.getColor(this, R.color.red));
        }
        drawer = binding.drawerLayout;
        navigationView  = binding.navView;
        // Passing each menu ID as a set of Ids because each
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.toolbar.setNavigationIcon(R.drawable.ic_menu);
        TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvName.setText(MySharedPreferences.getUserName(this));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        checkAdmin();

    }
    void checkAdmin(){
        String name = MySharedPreferences.getUserName(this);
        if (!name.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_home).setVisible(false);
            menu.findItem(R.id.nav_gallery).setVisible(false);
            replaceFragment(R.id.nav_slideshow);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            replaceFragment(R.id.nav_home);
            changeTitle("Trang chủ");
        }
        if (id == R.id.nav_gallery) {
            replaceFragment(R.id.nav_gallery);
            changeTitle("Quản lý nhân viên");
        }
        if (id == R.id.nav_slideshow) {
            replaceFragment(R.id.nav_slideshow);
            changeTitle("Quản lý sản phẩm");
        }
        if (id == R.id.nav_logout) {
            MySharedPreferences.clearUserName(this);
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        drawer.close();
        return true;
    }

    void replaceFragment(int id) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        navController.navigate(id);
    }
    void changeTitle(String title) {
        TextView tvTitle = binding.appBarHome.toolbar.findViewById(R.id.tvToolbarTitle);
        tvTitle.setText(title);
    }
}