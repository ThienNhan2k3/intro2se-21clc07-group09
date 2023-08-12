package com.example.whatsfood.Activity.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatsfood.R;

public class AdminSellerRegisterRequestsActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_seller_register_requests, container);
        requireActivity().setTitle("Seller register requests");
        setHasOptionsMenu(true);
        return view;
    }
}
