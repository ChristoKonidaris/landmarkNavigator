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
        holder.txtTitle.setText(items.get(position).getTitle());
        holder.txtAddress.setText(items.get(position).getAddress().getStreet() + ", " + items.get(position).getAddress().getCity());
        holder.txtPost.setText(items.get(position).getAddress().getPostalCode());
//        // check for metric or imperial or check the api settings
        holder.txtDistance.setText("" + items.get(position).getDistance());
        String eta;
        double time = items.get(position).getDistance() / 1000.0 / 60;
        if(time < 0){
            eta ="Less than 1min";
        }else{
            eta = Math.round(time) + 1 + "min";
        }
        holder.txtEta.setText(eta);

        holder.itemView.setOnClickListener(v->{
            Log.i(TAG, "OnClick");
            Item item = new Item(
                    items.get(position).getTitle(),
                    items.get(position).getAddress().getStreet(),
                    items.get(position).getAddress().getPostalCode(),
                    items.get(position).getPosition().getLat(),
                    items.get(position).getPosition().getLng(),
                    items.get(position).getContacts().get(0).getPhone().get(0).getValue(),
                    items.get(position).getContacts().get(0).getWww().get(0).getValue()
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
        TextView txtTitle, txtAddress, txtPost, txtDistance, txtEta;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtPoiTitle);
            txtAddress = itemView.findViewById(R.id.txtPoiAddress);
            txtPost = itemView.findViewById(R.id.txtPoiPost);
            txtDistance = itemView.findViewById(R.id.txtPoiDistance);
            txtEta = itemView.findViewById(R.id.txtPoiEta);
        }
    }
}
