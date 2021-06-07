/*
 *Used Google Firebase documentation for read capabilities
 * https://firebase.google.com/docs/database/android/read-and-write
 */
package com.example.landmarknavigator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomepageFragment extends Fragment {
    //permission variables
    public static final int LOCATION_ACCESS_CODE = 1;
    //Location variables
    private LocationService locationService;
    private LocationManager manager;
    LocationListener locationListener;
    //logging variable
    public static final String TAG = "HomepageFragment";
    //firebase variables
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    //view variables
    RecyclerView recyclerView;
    boolean newCall;
    private ProgressBar pb;

    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = view.findViewById(R.id.pb);

        //start progress bar
        pb.setVisibility(View.VISIBLE);


        locationService = new LocationService();
        manager = LocationService.manager;
        newCall = Location.savedItems == null || Location.savedItems.size() == 0;
        assignLocationListener();
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private boolean checkLocationPermission(){
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission(){
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_ACCESS_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_ACCESS_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i(TAG, "onRequestPermissionResult:Granted");
                LocationService.locationAccess = true;
            }else{
                LocationService.locationAccess = false;
            }
        }
    }
    private void assignLocationListener(){
        if(!newCall){
            populateRecyclerView(Location.savedItems);
            return;
        }
        if(LocationService.lat != 0.0 && LocationService.lon != 0.0){
            fetchData(LocationService.lat, LocationService.lon);

        }else{
        locationListener = locationService.getLocationListener((double lat, double lon) -> {
                fetchData(lat, lon);
        });
        if(checkLocationPermission()) manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10, locationListener);
        else requestLocationPermission();
        }
    }

    private void fetchData(double lat, double lon){
        manager.removeUpdates(locationListener);

        if(newCall) {
            String landmark = userRuntimeConfig.userSettings.Landmark;
            Webservice webservice = new Webservice();
            webservice.getLocations(lat, lon, landmark, location -> {
                Location.savedItems = location.getItems();
                populateRecyclerView(location.getItems());

            });
        }
    }

    private void populateRecyclerView(List<Location.Items> items){
        //end progress
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(() -> {
            pb.setVisibility(View.INVISIBLE);
            LocationAdapter adapter = new LocationAdapter(getContext(), items);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

}