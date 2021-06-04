package com.example.landmarknavigator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomepageFragment extends Fragment {
    public static final int LOCATION_ACCESS_CODE = 1;
    //Location variables
    private LocationService locationService;
    private LocationManager manager;
    //logging variable
    public static final String TAG = "HomepageFragment";
    //view variables
    TextView output;

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

        Button btnTest = view.findViewById(R.id.btnTest);
        output = view.findViewById(R.id.txtTestOutput);

        btnTest.setOnClickListener(testEvent);
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
        LocationListener locationListener = locationService.getLocationListener();
        if(checkLocationPermission()) manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10,locationListener);
        else requestLocationPermission();
    }

    private View.OnClickListener testEvent = v -> {
        Log.i(TAG, "lat " + LocationService.lat);
        Log.i(TAG, "lon " + LocationService.lon);
        if(LocationService.lat != 0.0  && LocationService.lon != 0.0){
            Log.i(TAG, "Making request");
            Webservice webservice = new Webservice();
            webservice.getLocations(LocationService.lat, LocationService.lon, "Bank", new IRootCallback() {
                @Override
                public void RootCallback(Root root) {
                    //TODO add the adapter for the recycler view
                    String outputString = "";
                    for(Root.Items item : root.getItems()){
                        outputString += item.getTitle() +"\n";
                    }
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    String finalOutputString = outputString;
                    mHandler.post(() -> {

                        output.setText(finalOutputString);

                    });
                }
            });
        }
    };





     /*
        Geolocation listener class
     */

    private class UserLocationListener implements LocationListener {
        public double lat;
        public double lon;

        @Override
        public void onLocationChanged(@NonNull Location location) {
            this.lat = location.getLatitude();
            this.lon = location.getLongitude();
            Log.i(TAG, "onLocationChanged");
            Log.i(TAG, "Lat is " + lat);
            Log.i(TAG, "Lon is " + lon);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(@NonNull String provider) { }

        @Override
        public void onProviderDisabled(@NonNull String provider) { }
    }

}