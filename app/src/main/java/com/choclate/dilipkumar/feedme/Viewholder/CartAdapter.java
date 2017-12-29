package com.choclate.dilipkumar.feedme.Viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.choclate.dilipkumar.feedme.Commen.Commen;
import com.choclate.dilipkumar.feedme.Interface.ItemClickListener;
import com.choclate.dilipkumar.feedme.Model.Order;
import com.choclate.dilipkumar.feedme.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dilipkumar on 03-11-2017.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name, txt_price;
    public ImageView img_cart_count;
    public ImageView product_image;

    //Remove Product from Cart
    public ImageView removeProduct;

    private ItemClickListener itemClickListener;


    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.Cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.Cart_item_price);
        img_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
        product_image = itemView.findViewById(R.id.product_image);
        removeProduct = itemView.findViewById(R.id.remove_product);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> ListData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        ListData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + ListData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(ListData.get(position).getPrice()));
        int quantity = Integer.parseInt(ListData.get(position).getQuantity());

        if (quantity >= 1) {
            price = price * quantity;
        }
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(ListData.get(position).getProductName());

        Picasso.with(context)
                .load(ListData.get(position).getProductImage())
                .into(holder.product_image);
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mDatabase.getReference("User/"
                        + Commen.currentuser.getPhone()
                        + "/cart/"+ListData.get(position).getCartId());
                mRef.setValue(null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }
}
