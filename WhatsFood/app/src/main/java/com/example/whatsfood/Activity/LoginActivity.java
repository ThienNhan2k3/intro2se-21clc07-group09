package com.example.whatsfood.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.Admin.AdminBottomNavigationActivity;
import com.example.whatsfood.Activity.Buyer.BuyerBottomNavigationActivity;
import com.example.whatsfood.Activity.Seller.SellerBottomNavigationActivity;
import com.example.whatsfood.FormatTextWatcher;
import com.example.whatsfood.R;
import com.example.whatsfood.UI_Functions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.EnumSet;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Dialog dialog;
    Button login_button;
    TextView register_button;
    EditText username_edittext;
    EditText password_edittext;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();
        login_button = (Button)findViewById(R.id.login_button);
        register_button = (TextView)findViewById(R.id.register_text);
        username_edittext = (EditText)findViewById(R.id.store_name);
        password_edittext = (EditText)findViewById(R.id.password);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectAccountTypeActivity.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        //Add TextWatchers
        FormatTextWatcher username_watcher = new FormatTextWatcher(username_edittext);
        username_watcher.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY);

        FormatTextWatcher password_watcher = new FormatTextWatcher(password_edittext);
        password_watcher.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY);

    }
    private void login() {
        login_button.setEnabled(false);
        String username_email = username_edittext.getText().toString();
        String password = password_edittext.getText().toString();
        if (username_email.isEmpty() || password.isEmpty()) {
            UI_Functions.CreatePopup(LoginActivity.this, getString(R.string.missing_information));
            login_button.setEnabled(true);
            return;
        }
        if (username_edittext.getError() != null || password_edittext.getError() != null) {
            UI_Functions.CreatePopup(LoginActivity.this, getString(R.string.invalid_field));
            login_button.setEnabled(true);
            return;
        }
        //Call sign in method from mAuth
        String email = username_email;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
                            if (fb_user == null) {
                                login_button.setEnabled(true);
                                return;
                            }
                            //Navigate to corresponding home activtiy
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("User").child(fb_user.getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        login_button.setEnabled(true);
                                    }
                                    else {
                                        String role = String.valueOf(task.getResult().getValue());
                                        if (Objects.equals(role, "seller")) {
                                            Intent intent = new Intent(LoginActivity.this, SellerBottomNavigationActivity.class);
                                            startActivity(intent);
                                        }
                                        else if (Objects.equals(role, "buyer")) {
                                            Intent intent = new Intent(LoginActivity.this, BuyerBottomNavigationActivity.class);
                                            startActivity(intent);
                                        }
                                        else if (Objects.equals(role, "admin")) {
                                            Intent intent = new Intent(LoginActivity.this, AdminBottomNavigationActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            UI_Functions.CreatePopup(LoginActivity.this, "Incorrect Password");
                            login_button.setEnabled(true);
                        }
                    }
                });
    }
}


