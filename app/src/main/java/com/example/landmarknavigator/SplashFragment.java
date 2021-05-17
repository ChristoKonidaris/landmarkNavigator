package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Handler mainLooperHandler = new Handler(Looper.getMainLooper());
        mainLooperHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 2000);

    }
}