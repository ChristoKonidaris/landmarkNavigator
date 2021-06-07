package com.example.landmarknavigator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landmarknavigator.model.Item;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    public static final String TAG = "LocationAdapter";
    // instance variables
    Context ctx;
    List<Location.Items> items;

    private final double MILE = 0.621371;


    // LocationAdapter Constructor
    public LocationAdapter(Context ctx, List<Location.Items> items){
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.location_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = items.get(position).getTitle();
        String street = items.get(position).getAddress().getStreet();
        String city = items.get(position).getAddress().getCity();
        String address = "";

        if(street != null){
            address += street + ", ";
        }
        if(city != null){
            address += city;
        }

        String post = items.get(position).getAddress().getPostalCode();
        double distance = items.get(position).getDistance() / 1000;

        if(userRuntimeConfig.userSettings.Unit == "Imperial"){
            distance = distance * MILE;
        }

        String distanceString = "";
        if(userRuntimeConfig.userSettings.Unit == "Imperial"){
            distanceString = (Math.round(distance * 100.0) / 100.0) + "mi";
        }else{
            distanceString = (Math.round(distance * 100.0) / 100.0) + "km";
        }


        holder.txtTitle.setText(title);
        holder.txtAddress.setText(address);
        holder.txtPost.setText(post);
//        // check for metric or imperial or check the api settings
        holder.txtDistance.setText(distanceString);

        holder.itemView.setOnClickListener(v->{
            Log.i(TAG, "OnClick");
            String phone = "";
            String web = "";

            try{ phone = items.get(position).getContacts().get(0).getPhone().get(0).getValue(); }
            catch (Exception e){ Log.e(TAG, "Failed to get phone"); }
            try{ web = items.get(position).getContacts().get(0).getWww().get(0).getValue(); }
            catch (Exception e){ Log.e(TAG, "Failed to get web"); }

            Item item = new Item(
                    title,
                    street,
                    post,
                    items.get(position).getAccess().get(0).getLat(),
                    items.get(position).getAccess().get(0).getLng(),
                    phone,
                    web
            );

            Navigation.findNavController(v).navigate(
                    HomepageFragmentDirections.actionHomepageFragmentToPoiInfoFragment(item)
            );

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAddress, txtPost, txtDistance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtPoiTitle);
            txtAddress = itemView.findViewById(R.id.txtPoiAddress);
            txtPost = itemView.findViewById(R.id.txtPoiPost);
            txtDistance = itemView.findViewById(R.id.txtPoiDistance);
        }
    }
}
