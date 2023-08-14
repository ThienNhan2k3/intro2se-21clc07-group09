package com.example.whatsfood.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.Admin.AdminBottomNavigationActivity;
import com.example.whatsfood.Activity.Admin.AdminHomeActivity;
import com.example.whatsfood.Activity.Buyer.BuyerBottomNavigationActivity;
import com.example.whatsfood.Activity.Buyer.BuyerHomeActivity;
import com.example.whatsfood.Activity.Seller.SellerBottomNavigationActivity;
import com.example.whatsfood.Activity.Seller.SellerHomeActivity;
import com.example.whatsfood.Model.User;
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
        username_edittext = (EditText)findViewById(R.id.username);
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
        username_edittext.setError(getString(R.string.empty_field));
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = username_edittext.getText().toString().trim();
            String password = password_edittext.getText().toString().trim();
            boolean flag = true;
            if (username.isEmpty()) {
                username_edittext.setError(getString(R.string.empty_field));
                flag = false;
            }
            if (password.isEmpty()) {
                password_edittext.setError(getString(R.string.empty_field));
                flag = false;
            }
            if (flag == true) {
                UI_Functions.EnableButton(login_button);
            } else {
                UI_Functions.DisableButton(login_button);
            }
        }
    };
    private void login() {
        String username_email = username_edittext.getText().toString();
        String password = password_edittext.getText().toString();
        if (username_email.isEmpty() || password.isEmpty()) {
            UI_Functions.CreatePopup(LoginActivity.this, "Missing Username/Email or Password");
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
                        } else {
                            // If sign in fails, display a message to the user.
                            UI_Functions.CreatePopup(LoginActivity.this, "Incorrect Password");
                        }
                    }
                });
        FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
        if (fb_user == null) {
            return;
        }
        //Navigate to corresponding home activtiy
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final User user = new User();
        mDatabase.child("User").child(fb_user.getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    return;
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
    }
}


