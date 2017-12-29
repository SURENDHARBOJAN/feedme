package com.choclate.dilipkumar.feedme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.choclate.dilipkumar.feedme.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilipkumar on 03-11-2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "EatItDB";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    List<Order> result;

    private final static String TAG = "Database";

    public List<Order> getCart() {
        result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Order order = new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")));
                Log.d(TAG, "getCart: " + order);
                result.add(order);
            } while (c.moveToNext());
        }
        return result;
    }

    /*public void addToCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }*/

    public void addToCart(Order order) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("ProductId", order.getProductId());
        values.put("ProductName", order.getProductName());
        values.put("Quantity", order.getQuantity());
        values.put("Price", order.getPrice());
        values.put("Discount", order.getDiscount());
        db.insert("OrderDetail", null, values);
    }

    public void CleanCart() {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table OrderDetail(ProductId text primary key, ProductName text, Quantity text, Price text, Discount text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table OrderDetail if exists");
        onCreate(db);
    }
}
