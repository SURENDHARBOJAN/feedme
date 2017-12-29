package com.choclate.dilipkumar.feedme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choclate.dilipkumar.feedme.Commen.Commen;
import com.choclate.dilipkumar.feedme.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {

    Button login;
    EditText e1, e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        login = (Button) findViewById(R.id.loginid);
        e1 = (EditText) findViewById(R.id.etmoid);
        e2 = (EditText) findViewById(R.id.etpassid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabel_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(e1.getText().toString()).exists()) {

                            User user = dataSnapshot.child(e1.getText().toString()).getValue(User.class);
                            user.setPhone(e1.getText().toString());
                            if (user.getPassword().equals(e2.getText().toString())) {

                                Intent homeintent = new Intent(Login_activity.this, home.class);
                                Commen.currentuser = user;

                                loadUser(e1.getText().toString());

                                startActivity(homeintent);
                                finish();

                            } else {
                                Toast.makeText(Login_activity.this, "wrong password", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login_activity.this, "User not exits", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void loadUser(String phone) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone", phone);
        editor.apply();
    }
}
