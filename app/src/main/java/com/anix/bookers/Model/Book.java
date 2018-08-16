package com.anix.bookers.Model;

/**
 * Created by Anix on 4/7/2018.
 */

public class Book {

    String Name,Author;
    int Price,PicId, UserId;

    public Book(int picId, String name, String author, int price, int userid) {
        PicId = picId;
        Name = name;
        Author = author;
        Price = price;
        UserId = userid;
    }

    public int getPicId() {
        return PicId;
    }

    public void setPicId(int picId) {
        PicId = picId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
