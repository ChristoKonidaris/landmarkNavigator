/*
* Used Google map API for the information on the implementation
* https://developers.google.com/maps/gmp-get-started
 */

package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.landmarknavigator.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        //return inflater.inflate(R.layout.fragment_map, container, false);

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(LocationService.lat, LocationService.lon);
                markerOptions.position(latLng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 10
                ));
                markerOptions.title(LocationService.lat + " : " + LocationService.lon);
                googleMap.addMarker(markerOptions);

                if(Location.savedItems != null && Location.savedItems.size() > 0){
                    for(Location.Items item : Location.savedItems){
                        MarkerOptions mo = new MarkerOptions();
                        mo.position(new LatLng(item.getPosition().getLat(), item.getPosition().getLng()));
                        googleMap.addMarker(mo);
                    }
                }




                //when map is loaded
                /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {


                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When click on map
                        //Initialize marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        //set position of marker
                        markerOptions.position(latLng);
                        //set title of marker
                        markerOptions.title(LocationService.lat + " : " + LocationService.lon);
                        //remove all markers
                        googleMap.clear();
                        //Animating zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        //add marker on map
                        googleMap.addMarker(markerOptions);
                    }

                });*/
            }
        });

        //return view
        return view;

    }
}