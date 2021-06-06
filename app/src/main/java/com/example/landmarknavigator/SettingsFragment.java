package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment {

    //Firebase variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference();
    //View variables
    Spinner spnrUnits, spnrLandmark;
    Button btnSave, btnLogout;
    ProgressBar pb;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //assigning view vars
        spnrUnits = view.findViewById(R.id.settingsUnitsSpinner);
        spnrLandmark = view.findViewById(R.id.settingsLandmarkSpinner);
        btnSave = view.findViewById(R.id.settingsSaveButton);
        btnLogout = view.findViewById(R.id.settingsLogoutButton);
        pb = view.findViewById(R.id.pb);

        // todo pull the data from the settings/database
        String[] unitArr = {"Imperial", "Metric"};
        String[] landmarkArr = {
                "Banks/ATM",
                "Coffee Shops",
                "Gas Stations",
                "Hospitals",
                "Movies",
                "Restaurants",
                "Bars",
                "Schools",
                "Tourist Attractions",
                "Shopping Center",
                "Post Office",
                "Churches",
                "Car Wash",
                "Fire Station",
                "Police Station",
                "Veterinarian",
                "Art Gallery",
                "Museum",
                "Fast Food",
                "Casino",
                "Embassy",
                "Prisons",
                "Hotel",
                "Zoo/Park",
                "Gyms",
                "Library",
                "Airport"
        };

        spnrUnits.setAdapter(getAdapter(unitArr));
        spnrLandmark.setAdapter(getAdapter(landmarkArr));

        btnSave.setOnClickListener(saveEvent);
        btnLogout.setOnClickListener(logoutEvent);
    }


    private View.OnClickListener saveEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //start progress
            pb.setVisibility(View.VISIBLE);

            String unit = spnrUnits.getSelectedItem().toString();
            String landmark = spnrLandmark.getSelectedItem().toString();

            Settings settings = new Settings(unit, landmark);
            mRef.child("users")
                    .child(mAuth.getUid())
                    .child("settings")
                    .setValue(settings)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //progress end
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Settings updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //progress end
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Failed with error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private ArrayAdapter<String> getAdapter(String[] items){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    };
    private View.OnClickListener logoutEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_settingsFragment_to_loginFragment);
        }
    };
}