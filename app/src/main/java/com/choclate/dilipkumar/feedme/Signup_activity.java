package com.choclate.dilipkumar.feedme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choclate.dilipkumar.feedme.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup_activity extends AppCompatActivity {

    EditText e1,e2,e3;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        e1=(EditText)findViewById(R.id.etmoid);
        e2=(EditText)findViewById(R.id.etnameid);
        e3=(EditText)findViewById(R.id.etpassid);
        register=(Button)findViewById(R.id.Registerid);

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tabel_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(e1.getText().toString()).exists()){
                            Toast.makeText(Signup_activity.this, "Phone no already registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            User user= new User(e2.getText().toString(),e3.getText().toString());
                            tabel_user.child(e1.getText().toString()).setValue(user);
                            Toast.makeText(Signup_activity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
