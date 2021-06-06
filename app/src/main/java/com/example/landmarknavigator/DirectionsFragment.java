package com.example.landmarknavigator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DirectionsFragment extends Fragment {

    public DirectionsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DirectionsFragment newInstance() {
        DirectionsFragment fragment = new DirectionsFragment();
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
        return inflater.inflate(R.layout.fragment_directions, container, false);

    }
}