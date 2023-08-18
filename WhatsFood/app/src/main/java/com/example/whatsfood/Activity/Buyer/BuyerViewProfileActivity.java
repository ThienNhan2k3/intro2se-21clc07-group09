package com.example.whatsfood.Activity.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsfood.Activity.Admin.RegisterDetailActivity;
import com.example.whatsfood.Activity.Buyer.BuyerBottomNavigationActivity;
import com.example.whatsfood.Activity.ChangePasswordActivity;
import com.example.whatsfood.Adapter.OrderAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.Model.Buyer;
import com.example.whatsfood.R;
import com.example.whatsfood.UI_Functions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class BuyerViewProfileActivity extends Fragment {

    Button ok_button;
    TextView popup_text;
    ImageButton back_button;
    Button change_password_button, edit_profile_button;
    String seller_id;
    Buyer buyer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_buyer_view_profile, null);
        setHasOptionsMenu(true);
        ((TextView)view.findViewById(R.id.header)).setText("Buyer Detail");
        ((ImageButton)view.findViewById(R.id.back_button)).setVisibility(View.GONE);
        change_password_button = (Button)view.findViewById(R.id.change_password_button);
        edit_profile_button = (Button)view.findViewById(R.id.edit_profile_button);
        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), BuyerEditProfileActivity.class);
                intent.putExtra("buyer", (Serializable) buyer);
                startActivity(intent);
            }
        });
        UpdateInformation(view);
        return view;
    }

    private void UpdateInformation(View view) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Buyer");
        FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child(fb_user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    buyer = task.getResult().getValue(Buyer.class);
                    ((TextView)view.findViewById(R.id.username)).setText("Username: " + buyer.username);
                    ((TextView)view.findViewById(R.id.fullname)).setText(buyer.fullname);
                    ((TextView)view.findViewById(R.id.address)).setText("Address: " + buyer.address);
                    ((TextView)view.findViewById(R.id.phone)).setText("Phone: " + buyer.phone);
                    ((TextView)view.findViewById(R.id.email)).setText("Email: " + fb_user.getEmail());
                    UI_Functions.LoadImageToImageView((ImageView)view.findViewById(R.id.avatar), buyer.avatarUrl);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateInformation(getView());
    }
}