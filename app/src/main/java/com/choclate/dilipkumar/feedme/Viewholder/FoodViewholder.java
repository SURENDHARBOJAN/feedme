package com.choclate.dilipkumar.feedme.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choclate.dilipkumar.feedme.Interface.ItemClickListener;
import com.choclate.dilipkumar.feedme.R;

/**
 * Created by dilipkumar on 28-10-2017.
 */

public class FoodViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewholder(View itemView) {
        super(itemView);
        food_name =(TextView)itemView.findViewById(R.id.foodtxtid);
        food_image=(ImageView)itemView.findViewById((R.id.foodimgid));
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
