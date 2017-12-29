package com.choclate.dilipkumar.feedme.Viewholder;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choclate.dilipkumar.feedme.Interface.ItemClickListener;
import com.choclate.dilipkumar.feedme.R;

/**
 * Created by dilipkumar on 26-10-2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imagemenu;
    private ItemClickListener itemClickListener;
    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName =(TextView)itemView.findViewById(R.id.cardtxtid);
        imagemenu=(ImageView)itemView.findViewById((R.id.cardimgid));
        itemView.setOnClickListener(this);

    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
