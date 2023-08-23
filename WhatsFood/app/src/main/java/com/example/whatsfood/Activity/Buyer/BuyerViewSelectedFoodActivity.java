package com.example.whatsfood.Activity.Buyer;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Adapter.CommentAdapter;
import com.example.whatsfood.Model.Buyer;
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Comment;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class BuyerViewSelectedFoodActivity extends AppCompatActivity {
    // ListView listView;
    ArrayList <Comment> comments=new ArrayList<>();
    CommentAdapter adapterComment;
    ArrayList<CartDetail> cartDetailList=new ArrayList<>();
    Food food;

    Buyer buyer;
    Dialog dialog;
    ImageView add_to_cart;
    Button comment;
    Button report;

    ImageView back;
    TextView foodName;
    TextView description;
    TextView price;

    ImageView imageFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_view_selected_food);
        dialog = new Dialog(this);
        Bundle extras = getIntent().getExtras();
        // listView=(ListView) findViewById(R.id.view_cart_food);
        add_to_cart= (ImageView) findViewById(R.id.add_to_cart);
        foodName=(TextView) findViewById(R.id.food_name);
        description=(TextView) findViewById(R.id.description);
        price=(TextView) findViewById(R.id.price);
        comment=(Button) findViewById(R.id.comment);
        report=(Button) findViewById(R.id.report);
        imageFood=(ImageView) findViewById(R.id.image_food);
        back= (ImageView) findViewById(R.id.back);

        String key = getIntent().getStringExtra("foodId");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String buyerId  = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                    food = dataSnapshot.getValue(Food.class);
                    Log.w("Food", food.getName());
                    foodName.setText((food.getName()));
                    price.setText(String.valueOf(food.getPrice()));
                    description.setText((food.getDescription()));
                    Picasso.get().load(food.getImageUrl()).into(imageFood);
                    ArrayList <String> commentArray= food.getComments();
                    for (String commentContent : commentArray) {
                        Comment comment = new Comment(commentContent);
                        comments.add(comment);
                    }
                    adapterComment=new CommentAdapter(BuyerViewSelectedFoodActivity.this,R.layout.comment_line,comments);

                    // listView.setAdapter(adapterComment);
                }

            }
        });

        mDatabase.child("Buyer").child(buyerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Map<String, Object>> rawCartDetailList = (ArrayList<Map<String, Object>>) snapshot.child("cartDetailList").getValue();

                if (rawCartDetailList != null) {
                    cartDetailList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                    for (Map<String, Object> rawCartItem : rawCartDetailList) {
                        String foodId = (String) rawCartItem.get("foodId");
                        String imageUrl = (String) rawCartItem.get("imageUrl");
                        String name = (String) rawCartItem.get("name");
                        String sellerId = (String) rawCartItem.get("sellerId");
                        int price = ((Long) rawCartItem.get("price")).intValue();
                        int number = ((Long) rawCartItem.get("number")).intValue();

                        CartDetail cartItem = new CartDetail(foodId, imageUrl, name, sellerId, price, number);
                        cartDetailList.add(cartItem);
                    }

                    // Tiếp tục xử lý dữ liệu sau khi đã tạo danh sách cartDetailList
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
            }

            private void showPopupLayout() {
                // Đặt layout cho Dialog bằng cách sử dụng inflate
                View popupView = LayoutInflater.from(BuyerViewSelectedFoodActivity.this).inflate(R.layout.activity_pop_up_enter_number_food, null);

                // Sử dụng biến dialog đã tạo ở phần khai báo trước đó
                dialog.setContentView(popupView);

                // Đặt kích thước cho Dialog
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                Button addCartButton = dialog.findViewById(R.id.addcart);
                EditText numberOfFoodEditText = dialog.findViewById(R.id.number_of_food); // Assume the ID is "number_of_food"

                addCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Lấy giá trị từ EditText number_of_food
                        String numberOfFoodStr = numberOfFoodEditText.getText().toString();

                        boolean foodExists = false;

                        // Kiểm tra xem numberOfFoodStr có phải là số tự nhiên hay không
                        try {
                            int numberOfFood = Integer.parseInt(numberOfFoodStr);
                            if (numberOfFood > 0) {
                                if(cartDetailList!=null) {
                                    // Kiểm tra xem foodId đã tồn tại trong giỏ hàng hay chưa
                                    for (CartDetail cartItem : cartDetailList) {
                                        if (cartItem.getFoodId().equals(key)) {
                                            // Cập nhật số lượng nếu foodId đã tồn tại
                                            cartItem.setNumber(cartItem.getNumber() + numberOfFood);
                                            foodExists = true;
                                            break;
                                        }
                                    }
                                }

                                if (!foodExists) {
                                    // Thêm món mới vào giỏ hàng
                                    CartDetail newCartItem = new CartDetail(key, food.getImageUrl(), food.getName(), food.getSellerId(), food.getPrice(), numberOfFood);
                                    cartDetailList.add(newCartItem);
                                }

                                // Cập nhật lại giỏ hàng trên Firebase
                                DatabaseReference buyerRef = mDatabase.child("Buyer").child(buyerId);
                                buyerRef.child("cartDetailList").setValue(cartDetailList);


                                Toast.makeText(BuyerViewSelectedFoodActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();

                                // Đóng Popup Dialog sau khi xử lý
                                dialog.dismiss();
                            } else {
                                // Số lượng không hợp lệ (phải là số tự nhiên dương)
                                // Hiển thị thông báo hoặc cảnh báo cho người dùng
                                // Ví dụ:
                                Toast.makeText(BuyerViewSelectedFoodActivity.this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            // Không thể chuyển đổi thành số, số lượng không hợp lệ
                            // Hiển thị thông báo hoặc cảnh báo cho người dùng
                            // Ví dụ:
                            Toast.makeText(BuyerViewSelectedFoodActivity.this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Hiển thị Dialog
                dialog.show();
            }
        });

    }
}
