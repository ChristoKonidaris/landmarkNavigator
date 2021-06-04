package com.example.landmarknavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //logging variable
    public static final String TAG = "MainActivity";
    //bottom navigation view
    private BottomNavigationView bottomNav;
    // navigation controller
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        bottomNav = findViewById(R.id.bottomNavigationView);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        navController.addOnDestinationChangedListener(destinationChangedListener);
        bottomNav.setOnNavigationItemSelectedListener(navItemSelectListener);
    }

    private NavController.OnDestinationChangedListener destinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            // show bottom navigation when NOT on Splash, Login, Registration, RegistrationSettings Screens
            if(destination.getId() != R.id.splashFragment &&
                destination.getId() != R.id.loginFragment &&
                destination.getId() != R.id.registrationFragment &&
                destination.getId() != R.id.registerSettingsFragment){
                bottomNav.setVisibility(View.VISIBLE);
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    // navigate home
                    Log.i(TAG, "home clicked");
                    navController.navigate(R.id.homepageFragment);
                    break;
                case R.id.map:
                    // navigate to map
                    Log.i(TAG, "map clicked");
                    // navController.navigate(R.id.mapFragment);
                    break;
                case R.id.favourites:
                    // navigate to favs
                    Log.i(TAG, "favs clicked");
                    break;
                case R.id.settings:
                    // navigate to settings
                    Log.i(TAG, "settings clicked");
                    break;
            }
            return false;
        }
    };
}