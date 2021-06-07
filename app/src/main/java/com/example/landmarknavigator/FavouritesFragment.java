/*
 *Used Google Firebase documentation for write capabilities
 * https://firebase.google.com/docs/database/android/read-and-write
 */

package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.landmarknavigator.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {

    public static final String TAG = "FavouriteFragment";
    //view variables
    RecyclerView recyclerView;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    ProgressBar pb;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
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
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = view.findViewById(R.id.pb);

        //start progress bar
        pb.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference()
                .child("users")
                .child(mAuth.getUid())
                .child("favourites");

        recyclerView = view.findViewById(R.id.recyclerView);
        
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "onDataChange");
                List<Item> items = new ArrayList<>();
                List<String> itemKeys = new ArrayList<>();
                for (DataSnapshot fav : snapshot.getChildren()){
                    Log.i(TAG, fav.getKey());
                    items.add(fav.getValue(Item.class));
                    itemKeys.add(fav.getKey());
                }
                populateFavRecycler(items, itemKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled:ValueListener");
            }
        });

    }

    private void populateFavRecycler(List<Item> items, List<String> itemKeys){
        //end progress
        pb.setVisibility(View.INVISIBLE);
        FavouriteAdapter adapter = new FavouriteAdapter(getContext(), items, itemKeys);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}