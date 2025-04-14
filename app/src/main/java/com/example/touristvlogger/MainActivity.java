package com.example.touristvlogger;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.touristvlogger.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth fAuth;


    DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    Menu menu;
    MenuItem logoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

//        MenuItem loginMenuItem = findViewById(R.id.nav_login);
//        loginMenuItem.setOnMenuItemClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//            return false;
//        });



        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_login, R.id.nav_register, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        this.fAuth = FirebaseAuth.getInstance();

        this.menu = navigationView.getMenu();


        this.logoutItem = menu.findItem(R.id.nav_logout);
        logoutItem.setOnMenuItemClickListener(item -> {
            this.fAuth.signOut();
            Toast.makeText(this, "Logged out.", Toast.LENGTH_SHORT).show();
            this.recreate();
            return true;
        });
        updateDrawer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrawer();
    }

    private void updateDrawer() {
        MenuItem loginItem = this.menu.findItem(R.id.nav_login);
        MenuItem registerItem = this.menu.findItem(R.id.nav_register);
        MenuItem profileItem = this.menu.findItem(R.id.nav_profile);

        if (this.fAuth.getCurrentUser() != null) {
            loginItem.setVisible(false);
            registerItem.setVisible(false);
            profileItem.setVisible(true);
            this.logoutItem.setVisible(true);

        } else {
            loginItem.setVisible(true);
            registerItem.setVisible(true);
            profileItem.setVisible(false);
            this.logoutItem.setVisible(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            fAuth.signOut();
            mDrawerLayout.refreshDrawableState();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}