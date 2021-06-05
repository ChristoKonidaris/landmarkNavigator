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

public class HomepageFragment extends Fragment {
    public static final int LOCATION_ACCESS_CODE = 1;
    //Location variables
    private LocationService locationService;
    private LocationManager manager;
    LocationListener locationListener;
    //logging variable
    public static final String TAG = "HomepageFragment";
    //view variables
    RecyclerView recyclerView;

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
        locationService = new LocationService();
        manager = LocationService.manager;
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
        locationListener = locationService.getLocationListener((double lat, double lon) -> {
            fetchData(lat, lon);
        });
        if(checkLocationPermission()) manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10, locationListener);
        else requestLocationPermission();
    }

//    private View.OnClickListener testEvent = v -> {
//        Log.i(TAG, "lat " + LocationService.lat);
//        Log.i(TAG, "lon " + LocationService.lon);
//        if(LocationService.lat != 0.0  && LocationService.lon != 0.0){
//            Log.i(TAG, "Making request");
//            Webservice webservice = new Webservice();
//            webservice.getLocations(LocationService.lat, LocationService.lon, "Bank", new ILocationCallback() {
//                @Override
//                public void RootCallback(Location location) {
//                    //TODO add the adapter for the recycler view
//                    String outputString = "";
//                    for(Location.Items item : location.getItems()){
//                        outputString += item.getTitle() +"\n";
//                    }
//                    Handler mHandler = new Handler(Looper.getMainLooper());
//                    String finalOutputString = outputString;
//                    mHandler.post(() -> {
//                        // display info
//
//                    });
//                }
//            });
//        }
//    };
//
    private void fetchData(double lat, double lon){
        // Log.i(TAG, "fetchData lat " + lat + " lon " + lon);
        manager.removeUpdates(locationListener);
        Webservice webservice = new Webservice();
        webservice.getLocations(lat, lon, "Bank", location -> {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(() -> {
                LocationAdapter adapter = new LocationAdapter(getContext(), location.getItems());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            });

        });
    }

}