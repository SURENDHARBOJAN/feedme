package com.choclate.dilipkumar.feedme;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.choclate.dilipkumar.feedme.Commen.Commen;
import com.choclate.dilipkumar.feedme.Database.Database;
import com.choclate.dilipkumar.feedme.Model.Order;
import com.choclate.dilipkumar.feedme.Model.Request;
import com.choclate.dilipkumar.feedme.Viewholder.CartAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    private static final String TAG = "Cart";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalbtn;
    Button button_place;

    List<Order> cart;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalbtn = (TextView) findViewById(R.id.total);
        button_place = (Button) findViewById(R.id.place_order);

        loadlistFood();
        button_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //requests
                showAlertDialog();


            }
        });


    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter your address");

        final EditText edtaddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtaddress.setLayoutParams(lp);
        alertDialog.setView(edtaddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Commen.currentuser.getPhone(),
                        Commen.currentuser.getName(),
                        edtaddress.getText().toString(),
                        txtTotalbtn.getText().toString(),
                        cart
                );

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                //delete order

                Toast.makeText(Cart.this, "Thank you,Order Placed", Toast.LENGTH_SHORT).show();

                //Delete Cart From Firebase
                DatabaseReference mRef = database.getReference("User/"
                        + Commen.currentuser.getPhone()
                        + "/cart");
                mRef.setValue(null);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();

    }

   /* private void loadlistFood() {
        cart = new Database(this).getCart();
        Log.d(TAG, "loadlistFood: " + cart);
        adapter = new CartAdapter(cart, this);

        //cal tot price
        int total = 0;
        for (Order order : cart)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalbtn.setText(fmt.format(total));
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

    }*/

    //Load data from Firebase

    private int count = 0;

    private void loadlistFood() {
        cart = new ArrayList<>();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_preference", MODE_PRIVATE);
        String phone = preferences.getString("phone", null);
        DatabaseReference mRef = database.getReference("User/" + phone + "/cart");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order1 = dataSnapshot.getValue(Order.class);
                order1.setProductImage(String.valueOf(dataSnapshot.child("productImage").getValue()));
                order1.setCartId(dataSnapshot.getKey());
                cart.add(order1);
                Log.d(TAG, "onChildAdded: " + cart);
                count++;

                if (count >= 1) {
                    adapter = new CartAdapter(cart, getApplicationContext());

                    //cal tot price
                    int total = 0;
                    for (Order order : cart)
                        total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
                    Locale locale = new Locale("en", "US");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                    txtTotalbtn.setText(fmt.format(total));
                    adapter = new CartAdapter(cart, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
