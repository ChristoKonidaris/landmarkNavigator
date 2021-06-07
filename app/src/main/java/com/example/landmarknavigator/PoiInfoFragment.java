/*
* Used Firebase for authentication and write capabilities
* https://firebase.google.com/docs/auth
* https://firebase.google.com/docs/database/android/read-and-write
 */

package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landmarknavigator.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PoiInfoFragment extends Fragment {
    //logging variable
    public static final String TAG = "PoiInfoFragment";
    //view variables
    private TextView txtTitle, txtAddress, txtPost, txtPhone, txtWeb;
    private Button btnAddFav, btnDirections;
    //firebase variables
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    //args variables
    Item item;


    public PoiInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PoiInfoFragment newInstance(String param1, String param2) {
        PoiInfoFragment fragment = new PoiInfoFragment();
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
        return inflater.inflate(R.layout.fragment_poi_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        item = PoiInfoFragmentArgs.fromBundle(getArguments()).getLocation();
        Log.i(TAG, "title is " + item.title);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        txtTitle = view.findViewById(R.id.txtPoiInfoTitile);
        txtAddress = view.findViewById(R.id.txtPoiInfoAddress);
        txtPost = view.findViewById(R.id.txtPoiInfoPost);
        txtPhone = view.findViewById(R.id.txtPoiInfoPhone);
        txtWeb = view.findViewById(R.id.txtPoiInfoWeb);
        btnAddFav = view.findViewById(R.id.btnAddFav);
        btnDirections = view.findViewById(R.id.btnDirections);

        txtTitle.setText(item.title);
        txtAddress.setText(item.street);
        txtPost.setText(item.post);
        txtPhone.setText(item.phone);
        txtWeb.setText(item.website);

        btnAddFav.setOnClickListener(addFavouriteEvent);
        btnDirections.setOnClickListener(navigateToDirections);
    }

    private View.OnClickListener addFavouriteEvent = v -> {
        mRef.child("users")
                .child(mAuth.getUid())
                .child("favourites")
                .push()
                .setValue(item).addOnSuccessListener(aVoid -> {
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to add", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "addFavEvent:Failed " + e.getMessage());
                });
    };

    private View.OnClickListener navigateToDirections = v -> {
        Navigation.findNavController(v).navigate(
                PoiInfoFragmentDirections.actionPoiInfoFragmentToDirectionsFragment(item)
        );

    };

}