package com.example.shopondoor.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shopondoor.R;

public class LogOutFragment extends Fragment {

    Button logoutButton;

    public LogOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        

        return inflater.inflate(R.layout.fragment_log_out, container, false);
    }
}