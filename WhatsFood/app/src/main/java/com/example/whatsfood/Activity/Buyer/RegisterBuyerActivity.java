package com.example.whatsfood.Activity.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.whatsfood.FormatTextWatcher;
import com.example.whatsfood.Model.Buyer;
import com.example.whatsfood.Model.User;
import com.example.whatsfood.R;
import com.example.whatsfood.Activity.SelectAccountTypeActivity;
import com.example.whatsfood.UI_Functions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.EnumSet;

public class RegisterBuyerActivity extends AppCompatActivity {
    ImageButton back_button;
    Button submit_button;
    private FirebaseAuth mAuth;
    EditText username, password, confirm_password, email, fullname, address, phone;
    boolean valid_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);
        ((TextView)findViewById(R.id.header)).setText("Buyer Register");
        mAuth = FirebaseAuth.getInstance();
        //EditTexts
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        email = (EditText)findViewById(R.id.email);
        fullname = (EditText)findViewById(R.id.fullname);
        address = (EditText)findViewById(R.id.address);
        phone = (EditText)findViewById(R.id.phone);
        //Buttons
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
                verify_information();
            }
        });
        //Add TextWatchers
        FormatTextWatcher watcher1 = new FormatTextWatcher(username);
        watcher1.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY);

        FormatTextWatcher watcher2 = new FormatTextWatcher(password);
        watcher2.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY, FormatTextWatcher.mode.CHECK_HAS_LOWERCASE, FormatTextWatcher.mode.CHECK_HAS_UPPERCASE, FormatTextWatcher.mode.CHECK_HAS_NUMBER, FormatTextWatcher.mode.CHECK_NO_SPECIAL_CHARACTER);

        TextWatcher confirm_password_watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!confirm_password.getText().toString().equals(password.getText().toString())) {
                    confirm_password.setError("Password not match");
                }
                else {
                    confirm_password.setError(null);
                }
            }
        };
        confirm_password.addTextChangedListener(confirm_password_watcher);

        FormatTextWatcher watcher3 = new FormatTextWatcher(email);
        watcher3.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY);

        FormatTextWatcher watcher4 = new FormatTextWatcher(fullname);
        watcher4.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY, FormatTextWatcher.mode.CHECK_ONLY_ALPHABET);

        FormatTextWatcher watcher5 = new FormatTextWatcher(address);
        watcher5.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY);

        FormatTextWatcher watcher6 = new FormatTextWatcher(phone);
        watcher6.modes = EnumSet.of(FormatTextWatcher.mode.CHECK_EMPTY, FormatTextWatcher.mode.CHECK_ONLY_NUMBER);
    }

    private void verify_information() {
        submit_button.setEnabled(false);

        String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String str_confirm_password = confirm_password.getText().toString();
        String str_email = email.getText().toString();
        String str_fullname = fullname.getText().toString();
        String str_address = address.getText().toString();
        String str_phone = phone.getText().toString();

        //Check empty fields
        if (str_username.isEmpty() ||
                str_password.isEmpty() ||
                str_confirm_password.isEmpty() ||
                str_email.isEmpty() ||
                str_fullname.isEmpty() ||
                str_address.isEmpty()
        ) {
            UI_Functions.CreatePopup(RegisterBuyerActivity.this, getString(R.string.missing_information));
            submit_button.setEnabled(true);
            return;
        }

        //Check invalid fields
        if (username.getError() != null ||
                password.getError() != null ||
                confirm_password.getError() != null ||
                email.getError() != null ||
                fullname.getError() != null ||
                address.getError() != null ||
                phone.getError() != null) {
            UI_Functions.CreatePopup(RegisterBuyerActivity.this, getString(R.string.invalid_field));
            submit_button.setEnabled(true);
            return;
        }
        //Try to create account
        mAuth.createUserWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                // User is signed in
                                Log.w("Firebase signup", "UID:" + String.valueOf(user.getUid()));
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                Buyer buyer = new Buyer(str_username, "", str_address, str_phone, str_fullname);
                                mDatabase.child("User").child(user.getUid()).child("role").setValue("buyer");
                                buyer.UpdateDataToServer();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(RegisterBuyerActivity.this, AfterRegisterActivity.class);
                                intent.putExtra("popup_text", getString(R.string.buyer_register));
                                startActivity(intent);
                            } else {
                                // No user is signed in
                                submit_button.setEnabled(true);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            submit_button.setEnabled(true);
                        }
                    }
                });
    }
}
