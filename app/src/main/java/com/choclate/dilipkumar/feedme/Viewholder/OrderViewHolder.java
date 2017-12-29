package com.choclate.dilipkumar.feedme.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.choclate.dilipkumar.feedme.Interface.ItemClickListener;
import com.choclate.dilipkumar.feedme.R;

/**
 * Created by dilipkumar on 29-11-2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.Order_adress);
        txtOrderId = (TextView)itemView.findViewById(R.id.Order_id);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.Order_phone);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.Order_status);

        itemView.setOnClickListener(null);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
