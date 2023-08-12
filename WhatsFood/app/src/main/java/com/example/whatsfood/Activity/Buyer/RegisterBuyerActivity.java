package com.example.whatsfood.Activity.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.AfterRegisterActivity;
import com.example.whatsfood.Activity.LoginActivity;
import com.example.whatsfood.R;
import com.example.whatsfood.Activity.SelectAccountTypeActivity;

public class RegisterBuyerActivity extends AppCompatActivity {
    ImageButton back_button;
    Button submit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);
        ((TextView)findViewById(R.id.header)).setText("Buyer Register");
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
        return true;
    }
}
