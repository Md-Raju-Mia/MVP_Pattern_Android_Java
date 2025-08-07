package com.demo.androidmvparchitecture.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.demo.androidmvparchitecture.R;
import com.demo.androidmvparchitecture.databinding.ActivityMainBinding;
import com.demo.androidmvparchitecture.databinding.NavHeaderMainBinding;
import com.demo.androidmvparchitecture.utils.NetworkChangeReceiver;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener{

    NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(this);
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        setSupportActionBar(binding.appBarMain.toolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.postFragment, R.id.todoFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Find the header view and bind it
        NavHeaderMainBinding headerBinding = DataBindingUtil.bind(binding.navView.getHeaderView(0));

        if (headerBinding != null) {
            headerBinding.textViewName.setText(getString(R.string.nav_header_title));
            headerBinding.textViewEmail.setText(getString(R.string.nav_header_subtitle));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        binding.appBarMain.contentMain.tvConnectionStatus.setVisibility(isConnected ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}