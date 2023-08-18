package com.example.whatsfood.Activity.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class SellerUpdateItemActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 3;
    ImageButton backBtn;
    AppCompatButton updateBtn;
    ImageView foodImageView;
    TextInputLayout foodNameTextInputLayout, descriptionTextInputLayout, priceTextInputLayout, quantityTextInputLayout;
    String foodName, description, price, quantity, sellerId, storeName;
    Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_update_item);

        Food food = (Food) getIntent().getSerializableExtra("Food");

        backBtn = (ImageButton) findViewById(R.id.back_button_seller_update_item);
        updateBtn = (AppCompatButton) findViewById(R.id.update_button_seller_update_item);
        foodImageView = (ImageView) findViewById(R.id.image_view_seller_update_item);
        foodNameTextInputLayout = (TextInputLayout) findViewById(R.id.food_name_text_input_layout_seller_update_item);
        descriptionTextInputLayout = (TextInputLayout) findViewById(R.id.description_text_input_layout_seller_update_item);
        priceTextInputLayout = (TextInputLayout) findViewById(R.id.price_text_input_layout_seller_update_item);
        quantityTextInputLayout = (TextInputLayout)  findViewById(R.id.quantity_text_input_layout_seller_update_item);

        foodNameTextInputLayout.getEditText().setText(food.getName());
        descriptionTextInputLayout.getEditText().setText(food.getDescription());
        priceTextInputLayout.getEditText().setText(food.getPrice());
        quantityTextInputLayout.getEditText().setText(food.getQuantity());

        refeshTextInputLayout(foodNameTextInputLayout);
        refeshTextInputLayout(descriptionTextInputLayout);
        refeshTextInputLayout(priceTextInputLayout);
        refeshTextInputLayout(quantityTextInputLayout);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().centerCrop().into(foodImageView);
        }
    }

    private boolean isValid() {
        foodNameTextInputLayout.setErrorEnabled(false);
        foodNameTextInputLayout.setError("");

        descriptionTextInputLayout.setErrorEnabled(false);
        descriptionTextInputLayout.setError("");

        priceTextInputLayout.setErrorEnabled(false);
        priceTextInputLayout.setError("");

        quantityTextInputLayout.setErrorEnabled(false);
        quantityTextInputLayout.setError("");

        boolean validFoodName = true, validDescription = true, validPrice = true, validQuantity = true;

        if (foodName.isEmpty()) {
            foodNameTextInputLayout.setErrorEnabled(true);
            foodNameTextInputLayout.setError("Food Name is required!");
            validFoodName = false;
        }

        if (description.isEmpty()) {
            descriptionTextInputLayout.setErrorEnabled(true);
            descriptionTextInputLayout.setError("Description is required!");
            validDescription = false;
        }

        if (price.isEmpty()) {
            priceTextInputLayout.setErrorEnabled(true);
            priceTextInputLayout.setError("Price is required!");
            validPrice = false;
        } else {
            if (!isNumeric(price)) {
                priceTextInputLayout.setErrorEnabled(true);
                priceTextInputLayout.setError("Your price is not valid!");
                validPrice = false;
            }
        }

        if (quantity.isEmpty()) {
            quantityTextInputLayout.setErrorEnabled(true);
            quantityTextInputLayout.setError("Quantity is required!");
            validQuantity = false;
        } else {
            if (!isNumeric(quantity)) {
                quantityTextInputLayout.setErrorEnabled(true);
                quantityTextInputLayout.setError("Your quantity is not valid!");
                validQuantity = false;
            }
        }

        return validFoodName && validDescription && validPrice && validQuantity;
    }

    private boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    private void refeshTextInputLayout(TextInputLayout input) {
        input.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                input.setErrorEnabled(true);
                input.setError("");
            }
        });
    }
}