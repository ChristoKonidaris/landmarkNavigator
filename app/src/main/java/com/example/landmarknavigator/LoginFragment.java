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
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    //Logging constant
    private final static String TAG = "LoginFragment";

    //Firebase variables
    private FirebaseAuth mAuth;
    // initializing view variables
    private EditText edtEmail, edtPassword;
    private TextView txtRegistration;
    private Button btnSubmit;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Assign Firebase variable
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            navigateToHomePage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //assign view variables
        edtEmail = view.findViewById(R.id.loginEmailEdit);
        edtPassword = view.findViewById(R.id.loginPasswordEdit);
        txtRegistration = view.findViewById(R.id.loginRegistrationTextView);
        btnSubmit = view.findViewById(R.id.loginSubmitButton);

        txtRegistration.setOnClickListener(navigateToRegistrationEvent);
        btnSubmit.setOnClickListener(loginEvent);
    }

    private View.OnClickListener loginEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(getContext(), "Please complete all the fields",Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), loginListener);
            Toast.makeText(getContext(), "Login in", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener navigateToRegistrationEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registrationFragment);
        }
    };

    private void navigateToHomePage(){
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homepageFragment);
    }

    private OnCompleteListener<AuthResult> loginListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Log.d(TAG, "SignInWithEmail&Password:success");
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }else{
                Log.w(TAG, "SignInWithEmail&Password:failure");
                Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        }
    };
}