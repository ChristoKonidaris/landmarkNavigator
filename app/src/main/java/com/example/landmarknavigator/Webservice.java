package com.example.landmarknavigator;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Webservice {
    public static final String TAG = "Webservice";
    // OkHttpClient
    OkHttpClient client = new OkHttpClient();

    private final String API_URL = "https://discover.search.hereapi.com/v1/discover";
    private final String PARAM_AT = "at";
    private final String PARAM_SEARCH = "q";
    private final String PARAM_API_KEY = "apiKey";
    private final String ROUTE_API = "https://image.maps.ls.hereapi.com/mia/1.6/routing";
    private final String PARAM_WAYPOINT0 = "waypoint0";
    private final String PARAM_WAYPOINT1 = "waypoint1";
    private final String PARAM_LINEC = "lc";
    private final String PARAM_LINEW = "lw";

    // todo move the the properties
    private final String apiKey = "SNouMi6cYPIlXxQfw3RYQQzCF0C2IOIry89blpc4vQQ";

    public URL getUrl(String at, String search) {
        Uri uri = Uri.parse(API_URL).buildUpon()
                .appendQueryParameter(PARAM_AT, at)
                .appendQueryParameter(PARAM_SEARCH, search)
                .appendQueryParameter(PARAM_API_KEY,apiKey).build();
        URL url = null;
        try{
            url = new URL(uri.toString());
            return url;
        }catch (Exception e){
            return null;
        }
    };

    public void getLocations(double lat, double lon, String search, ILocationCallback LocationCallback){
        String at = ""+lat+","+lon;
        URL url = getUrl(at, search);
        if(url == null){
            Log.w(TAG, "URL is null");
            return;
        }
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "Failed to fetch " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new GsonBuilder().create();
                Location location = gson.fromJson(body, Location.class);
                Log.i(TAG, body);
                Log.i(TAG, "Root count " + location.getItems().size());
                LocationCallback.LocationCallback(location);
            }
        });
    }

    private URL getRouteURL(String w0, String w1){
        Uri uri = Uri.parse(ROUTE_API)
                .buildUpon()
                .appendQueryParameter(PARAM_WAYPOINT0, w0)
                .appendQueryParameter(PARAM_WAYPOINT1, w1)
                .appendQueryParameter(PARAM_API_KEY,apiKey)
                .appendQueryParameter(PARAM_LINEC, "1652B4")
                .appendQueryParameter(PARAM_LINEW, "4").build();
        URL url = null;
        try{
            url = new URL(uri.toString());
            return url;
        }catch (Exception e){
            return null;
        }
    }

    public void getRouteImage(double lat0, double lon0, double lat1, double lon1){
        String w0 = lat0 + "" + lon0;
        String w1 = lat1 + "" + lon1;
        URL url = getRouteURL(w0, w1);
        if(url == null){
            Log.w(TAG, "URL is null");
            return;
        }

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "Failed to fetch " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                // Gson gson = new GsonBuilder().create();
                // Location location = gson.fromJson(body, Location.class);
                Log.i(TAG, body);
                // Log.i(TAG, "Root count " + location.getItems().size());
            }
        });

    }


}
