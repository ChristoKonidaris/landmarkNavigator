package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterSettingsFragment extends Fragment {
    //Logging Constant
    private final static String TAG = "RegistrationSettings";
    //Firebase variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference(mAuth.getUid());
    //View variables
    Spinner spnrUnits, spnrTheme, spnrLandmark;
    Button btnSubmit;

    public RegisterSettingsFragment() {
        // Required empty public constructor
    }

    public static RegisterSettingsFragment newInstance(String param1, String param2) {
        RegisterSettingsFragment fragment = new RegisterSettingsFragment();
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
        return inflater.inflate(R.layout.fragment_register_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //assigning view vars
        spnrUnits = view.findViewById(R.id.registrationSettingsUnitsSpinner);
        spnrTheme = view.findViewById(R.id.registrationSettingsThemeSpinner);
        spnrLandmark = view.findViewById(R.id.registrationSettingsLandmarkSpinner);
        btnSubmit = view.findViewById(R.id.registrationSettingsSubmitButton);

        // todo pull the data from the settings/database
        String[] unitArr = {"Imperial", "Metric"};
        String[] themeArr = {"Dark", "Light"};
        String[] landmarkArr = {"Restaurants", "Coffee Shops"};

        spnrUnits.setAdapter(getAdapter(unitArr));
        spnrTheme.setAdapter(getAdapter(themeArr));
        spnrLandmark.setAdapter(getAdapter(landmarkArr));

        btnSubmit.setOnClickListener(submitEvent);
    }

    private View.OnClickListener submitEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Todo submit to the firebase
            String unit = spnrUnits.getSelectedItem().toString();
            String theme = spnrTheme.getSelectedItem().toString();
            String landmark = spnrLandmark.getSelectedItem().toString();

            Settings settings = new Settings(unit, theme, landmark);
            mRef.setValue(settings, Settings.class);
        }
    };

    private ArrayAdapter<String> getAdapter(String[] items){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    };
}