package com.example.whatsfood.Activity.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.AfterRegisterActivity;
import com.example.whatsfood.Activity.LoginActivity;
import com.example.whatsfood.Model.User;
import com.example.whatsfood.R;
import com.example.whatsfood.Activity.SelectAccountTypeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBuyerActivity extends AppCompatActivity {
    ImageButton back_button;
    Button submit_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);
        ((TextView)findViewById(R.id.header)).setText("Buyer Register");
        mAuth = FirebaseAuth.getInstance();
        back_button = (ImageButton)findViewById(R.id.back_button);
        submit_button = (Button)findViewById(R.id.submit_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify_information()) {
                    Intent intent = new Intent(RegisterBuyerActivity.this, AfterRegisterActivity.class);
                    intent.putExtra("popup_text", getString(R.string.buyer_register));
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verify_information() {
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        //Check empty fields
        //Try to create account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("User").child(user.getUid()).setValue(new User("buyer"));
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            FirebaseAuth.getInstance().signOut();
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }
}
