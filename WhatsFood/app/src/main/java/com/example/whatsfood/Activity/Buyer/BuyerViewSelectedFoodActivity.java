package com.example.whatsfood.Activity.Buyer;


import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.whatsfood.Activity.AfterRegisterActivity;
import com.example.whatsfood.Activity.LoginActivity;
import com.example.whatsfood.Adapter.CommentAdapter;
import com.example.whatsfood.Model.Comment;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class BuyerViewSelectedFoodActivity extends AppCompatActivity {
    ListView listView;
    ArrayList <Comment> comments;
    CommentAdapter adapterComment;

    Dialog dialog;
    ImageView add_to_cart;
    Button comment;
    Button report;

    ImageView back;
    TextView foodName;
    TextView description;
    TextView price;

    ImageView imageFood;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_view_selected_food);
        dialog = new Dialog(this);
        Bundle extras = getIntent().getExtras();
        listView=(ListView) findViewById(R.id.view_comment);
        add_to_cart= (ImageView) findViewById(R.id.add_to_cart);
        foodName=(TextView) findViewById(R.id.food_name);
        description=(TextView) findViewById(R.id.description);
        price=(TextView) findViewById(R.id.price);
        comment=(Button) findViewById(R.id.comment);
        report=(Button) findViewById(R.id.report);
        imageFood=(ImageView) findViewById(R.id.image_food);
        back= (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerViewSelectedFoodActivity.this, BuyerBottomNavigationActivity.class);
                startActivity(intent);
            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupLayout();
                /*Button addButton = (Button) findViewById(R.id.addcart);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("abdef","123456");
                    }
                });*/
            }

            private void showPopupLayout() {
                Dialog dialog = new Dialog(BuyerViewSelectedFoodActivity.this);

                // Đặt layout cho Dialog bằng cách sử dụng inflate
                View popupView = LayoutInflater.from(BuyerViewSelectedFoodActivity.this).inflate(R.layout.activity_pop_up_enter_number_food, null);
                dialog.setContentView(popupView);

                // Đặt kích thước cho Dialog
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Hiển thị Dialog
                dialog.show();

            }
        });
        String key = String.valueOf(extras);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Food").child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    DataSnapshot dataSnapshot = task.getResult(); // Lấy dữ liệu từ Firebase

                    // Sử dụng biến tạm để lưu trữ dữ liệu từ Firebase
                    Food food = dataSnapshot.getValue(Food.class);
                    Log.w("Food", food.getName());
                    foodName.setText((food.getName()));
                    price.setText(String.valueOf(food.getPrice()));
                    description.setText((food.getDescription()));
                    Picasso.get().load(food.getImageUrl()).into(imageFood);
                    ArrayList <String> commentArray= food.getComments();
                    /*for (String commentContent : commentArray) {
                        Comment comment = new Comment(commentContent);
                        comments.add(comment);
                    }
                    adapterComment=new CommentAdapter(BuyerViewSelectedFoodActivity.this,R.layout.comment_line,comments);

                    listView.setAdapter(adapterComment);*/
                }

            }
        });

    }
}
