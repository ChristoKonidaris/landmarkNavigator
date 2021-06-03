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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationFragment extends Fragment {
    //Logging Constant
    private static final String TAG = "RegisterFragment";

    //Firebase variables
    private FirebaseAuth mAuth;

    // initializing view variables
    private EditText edtEmail, edtPassword, edtRepeat;
    private Button btnSubmit;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtEmail = view.findViewById(R.id.registrationEmailEdit);
        edtPassword = view.findViewById(R.id.registrationPasswordEdit);
        edtRepeat = view.findViewById(R.id.registrationPasswordRepeatEdit);
        btnSubmit = view.findViewById(R.id.registrationSubmitButton);

        btnSubmit.setOnClickListener(registrationEvent);
    }

    private View.OnClickListener registrationEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String repeat = edtRepeat.getText().toString();

            if(email.isEmpty() || password.isEmpty() || repeat.isEmpty()){
                Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            if(!password.equals(repeat)){
                Toast.makeText(getContext(), "Passwords do no match", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), registrationListener);
            Toast.makeText(getContext(), "Registering", Toast.LENGTH_SHORT).show();
        }
    };

    private OnCompleteListener<AuthResult> registrationListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Log.d(TAG, "CreateWithUsername&Password:success");
                Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_SHORT).show();
                navigateToRegistrationSettings();
            }else{
                Log.w(TAG, "CreateWithUsername&Password:failure");
                Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void navigateToRegistrationSettings(){
        Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_registerSettingsFragment);
    }

}