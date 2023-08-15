package com.example.whatsfood.Model;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Buyer extends Client {
    public String fullname;
    public Buyer() {
        super();
    }
    public Buyer(String username, String avatarUrl, String address, String phone, String fullname) {
        super(username, avatarUrl, address, phone);
        this.fullname = fullname;
    }

    public boolean UpdateDataToServer() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Buyer").child(user.getUid()).setValue(this);
        return true;
    }
}

