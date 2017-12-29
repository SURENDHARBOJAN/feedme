package com.choclate.dilipkumar.feedme.Model;

/**
 * Created by dilipkumar on 28-10-2017.
 */

public class Food {
   private String Name,Image,Discription,Price,Discount,MenuId;

    public Food() {

     }

    public Food(String name, String image, String discription, String price, String discount, String menuId) {
        Name = name;
        Image = image;
        Discription = discription;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
