package com.example.shopify.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.shopify.R;
import com.example.shopify.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//this is the activity in which we manage our whole app with fragment navgraph
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottom_nav);
        activity = this;
        navController = Navigation.findNavController(this, R.id.fragment);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        //bottom navigation setup

        //here we set the nav graph ui of our app
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        //this is the bottomnavigation view when user see homepage it manages
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.favoritesFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.settingsFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });
    }
}