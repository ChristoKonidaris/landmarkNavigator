package com.example.landmarknavigator;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

public class LocationService {
    private final String TAG = "LocationService";
    public static boolean locationAccess = false;

    public static double lat;
    public static double lon;

    public static LocationManager manager;

    private ILocationServiceCallback locationServiceCallback;

    //empty constructor
    public LocationService(){}

    public LocationListener getLocationListener(ILocationServiceCallback locationServiceCallback){
        this.locationServiceCallback = locationServiceCallback;
        return new UserLocationListener();
    }





    /*
        User GPS location
     */
    private class UserLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            Log.i(TAG, "onLocationChanged");
            Log.i(TAG, "Lat is " + lat);
            Log.i(TAG, "Lon is " + lon);
            locationServiceCallback.LocationServiceCallback(lat, lon);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(@NonNull String provider) { }

        @Override
        public void onProviderDisabled(@NonNull String provider) { }
    }
}
