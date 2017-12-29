package com.choclate.dilipkumar.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.choclate.dilipkumar.feedme.Interface.ItemClickListener;
import com.choclate.dilipkumar.feedme.Model.Food;
import com.choclate.dilipkumar.feedme.Viewholder.FoodViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase Database;
    DatabaseReference foodlist;
    String categoryid="";
    FirebaseRecyclerAdapter<Food,FoodViewholder> adapter;
    FirebaseRecyclerAdapter<Food,FoodViewholder> searcAadapter;

    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Database = FirebaseDatabase.getInstance();
        foodlist=Database.getReference("Foods");
        recyclerView= (RecyclerView)findViewById(R.id.Recycler_food);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager=new LinearLayoutManager(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        if (getIntent()!=null)
            categoryid=getIntent().getStringExtra("CategoryId");
        if (!categoryid. isEmpty() && categoryid != null){
            LoadListFood(categoryid);
        }
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search for Food");

       // loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(8);



    }

//    private void loadSuggest() {
//        foodlist.orderByChild("MenuId".equals(categoryid))
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
//                        {
//                            Food item = postSnapshot.getValue(Food.class);
//                            suggestList.add(item.getName());
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//    }

    private void LoadListFood(String categoryid) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewholder>(Food.class,R.layout.food_item,FoodViewholder.class,
                foodlist.orderByChild("MenuId")
                        .equalTo(categoryid)) {
            @Override
            protected void populateViewHolder(FoodViewholder viewHolder, Food model, int position) {
               viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                viewHolder.food_image.setTag(model.getImage());
                final Food local =model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongclick) {

                        Intent fooddetails =new Intent(FoodList.this,FoodDetail.class);
                        fooddetails.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(fooddetails);


                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
