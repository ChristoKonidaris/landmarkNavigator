/*
* Used Here documentation for referencing.
* https://developer.here.com/documentation/android-premium/3.18/dev_guide/topics/user-guide.html
 */

package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.landmarknavigator.model.Item;
import com.here.android.mpa.common.ApplicationContext;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.MapView;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.RoutingError;

import java.util.List;

public class DirectionsFragment extends Fragment {
    //logging variable
    public static final String TAG = "DirectionsFragment";
    // Routing variables
    private MapView mapView;
    private Map map;
    private CoreRouter router;
    private RoutePlan routePlan;
    private RouteOptions routeOptions;
    private MapRoute mapRoute;
    private MapEngine mapEngine;
    private AndroidXMapFragment hereMapFragment = null;
    //safeargs
    private Item item;

    public DirectionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DirectionsFragment newInstance(String param1, String param2) {
        DirectionsFragment fragment = new DirectionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directions, container, false);
        initialize();
        return view;
    }

    private void initialize(){
        hereMapFragment = (AndroidXMapFragment) getChildFragmentManager().findFragmentById(R.id.hereMapFragment);
        if(hereMapFragment != null){
            hereMapFragment.init(error -> {
                if(error == OnEngineInitListener.Error.NONE){
                    map = hereMapFragment.getMap();
                }else{
                    Log.e(TAG, "initialize onEngineInitError");
                }
            });
        }else{
            Log.i(TAG, "hereMapFragment is null");
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        item = PoiInfoFragmentArgs.fromBundle(getArguments()).getLocation();
        Log.i(TAG, "args lat " + item.lat + "| lon " + item.lon);
        mapEngine = MapEngine.getInstance();
        ApplicationContext applicationContext = new ApplicationContext(getContext());
        mapEngine.init(applicationContext, error -> {
            if(error == OnEngineInitListener.Error.NONE){
                Log.i(TAG,"onEngineInit:Success");
                router = new CoreRouter();
                routePlan = new RoutePlan();
                routePlan.addWaypoint(new RouteWaypoint(new GeoCoordinate(LocationService.lat, LocationService.lon)));
                routePlan.addWaypoint(new RouteWaypoint(new GeoCoordinate(item.lat, item.lon)));
                routeOptions = new RouteOptions();
                routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
                routeOptions.setRouteType(RouteOptions.Type.FASTEST);
                router.calculateRoute(routePlan, new RouteListener());
            }else{
                Log.e(TAG,"EngineInit failed "+ error.getDetails());
            }
        });

    }

    private class RouteListener implements CoreRouter.Listener{

        @Override
        public void onProgress(int i) {

        }

        @Override
        public void onCalculateRouteFinished(@NonNull List<RouteResult> list, @NonNull RoutingError routingError) {
            if(routingError == RoutingError.NONE){
                mapRoute = new MapRoute(list.get(0).getRoute());
                map.addMapObject(mapRoute);
                map.setCenter(new GeoCoordinate(LocationService.lat, LocationService.lon),Map.Animation.NONE);
            }else{
                Log.e(TAG, "Error while routing " + routingError.name());
            }
        }
    }
}