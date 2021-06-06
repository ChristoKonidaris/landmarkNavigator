package com.example.landmarknavigator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landmarknavigator.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    public static final String TAG = "FavouriteAdapter";
    // instance variables
    Context ctx;
    List<Item> items;
    List<String> itemsKeys;

    // LocationAdapter Constructor
    public FavouriteAdapter(Context ctx, List<Item> items, List<String> itemKeys){
        this.ctx = ctx;
        this.items = items;
        this.itemsKeys = itemKeys;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.favourite_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // set holder.view
        holder.txtTitle.setText(items.get(position).title);
        holder.txtStreet.setText(items.get(position).street);
        holder.txtPost.setText(items.get(position).post);
        holder.btnDelete.setOnClickListener(v -> {
           Log.i(TAG, "Deleting item " + itemsKeys.get(position));
            FirebaseDatabase
                    .getInstance()
                    .getReference("users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("favourites")
                    .child(itemsKeys.get(position)).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtStreet, txtPost;
        Button btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtFavTitle);
            txtStreet = itemView.findViewById(R.id.txtFavStreet);
            txtPost = itemView.findViewById(R.id.txtFavPost);
            btnDelete = itemView.findViewById(R.id.btnDeleteFav);
        }
    }

}
