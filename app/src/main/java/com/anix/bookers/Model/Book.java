package com.anix.bookers.Model;

/**
 * Created by Anix on 4/7/2018.
 */

public class Book {

    private String Name,Author,Price, UserId;

    public Book() {
    }

    public Book(String name, String author, String price, String userId) {
        Name = name;
        Author = author;
        Price = price;
        UserId = userId;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
