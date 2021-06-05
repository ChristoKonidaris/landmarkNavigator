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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationFragment extends Fragment {
    //Logging Constant
    private static final String TAG = "RegisterFragment";

    //Firebase variables
    private FirebaseAuth mAuth;

    // initializing view variables
    private EditText edtEmail, edtPassword, edtRepeat;
    private TextView txtlogin;
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
        txtlogin = view.findViewById(R.id.registrationTextView);

        btnSubmit.setOnClickListener(registrationEvent);
        txtlogin.setOnClickListener(navigateToLoginEvent);
    }

    private View.OnClickListener registrationEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            String repeat = edtRepeat.getText().toString();


            if(email.isEmpty()){
                edtEmail.setError("Please enter a valid email address.");
                return;
            }
            if(password.isEmpty()){
                edtPassword.setError("Please enter a password.");
                return;
            }
            if(repeat.isEmpty()){
                edtRepeat.setError("Please confirm your password.");
                return;
            }
            if(!password.equals(repeat)){
                edtPassword.setError("Passwords do no match.");
                return;
            }
            if (password.length() < 6) {
                edtPassword.setError("Password must be at least 6 characters long.");
                return;
            }

            Log.i(TAG, "Registration email "+ email);
            Log.i(TAG, "Registration password "+ password);

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
                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                Log.e(TAG, e.getMessage());
                Toast.makeText(getActivity(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                // Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void navigateToRegistrationSettings(){
        Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_registerSettingsFragment);
    }

    private View.OnClickListener navigateToLoginEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_loginFragment);
        }
    };

}