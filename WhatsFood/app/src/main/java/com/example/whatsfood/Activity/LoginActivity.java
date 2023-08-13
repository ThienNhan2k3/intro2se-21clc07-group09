package com.example.whatsfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.Admin.AdminHomeActivity;
import com.example.whatsfood.Activity.Buyer.BuyerHomeActivity;
import com.example.whatsfood.Activity.Seller.SellerHomeActivity;
import com.example.whatsfood.R;

public class LoginActivity extends AppCompatActivity {

    Button login_button;
    TextView register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = (Button)findViewById(R.id.login_button);
        register_button = (TextView)findViewById(R.id.register_text);
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
                String username = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                login(username, password);
            }
        });
    }
    private void login(String username, String password) {
        if (username == "seller") {
            Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
            startActivity(intent);
        }
        else if (username == "buyer") {
            Intent intent = new Intent(LoginActivity.this, BuyerHomeActivity.class);
            startActivity(intent);
        }
        else if (username == "admin") {
            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        }
    }
}


