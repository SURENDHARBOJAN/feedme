package com.choclate.dilipkumar.feedme;

import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.choclate.dilipkumar.feedme.Database.Database;
import com.choclate.dilipkumar.feedme.Model.Food;
import com.choclate.dilipkumar.feedme.Model.Order;
import com.choclate.dilipkumar.feedme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    private static final String TAG = "FoodDetail";
    TextView food_name, food_price, fooddescription;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        btncart = (FloatingActionButton) findViewById(R.id.btncart);

       /* btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Database db = new Database();
//                db.addToCart();
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()

                ));
                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()

                ));

            }
        });*/


        //New Onclick Listener for Firebase

        fooddescription = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.image_food);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.colasping);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanddedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollaspedAppbar);

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_preference", MODE_PRIVATE);
                String phone = preferences.getString("phone", null);
                DatabaseReference mRef = database.getReference("User/" + phone + "/cart");

                Order order = new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount());
                order.setProductImage(currentFood.getImage());
                //   mRef.setValue(order);
                mRef.push().setValue(order);
                Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });


        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty() && foodId != null) {
            getDetailFood(foodId);

        }
    }

    private void getDetailFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                fooddescription.setText(currentFood.getDiscription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
