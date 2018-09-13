package com.anix.bookers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anix.bookers.Model.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBook extends AppCompatActivity {

    EditText ETbookName, ETauthorName, ETprice;
    SharedPreferences preferences;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ETbookName = findViewById(R.id.ETAddbookname);
        ETauthorName = findViewById(R.id.ETAddauthorname);
        ETprice = findViewById(R.id.ETAddprice);
        preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void BtnAddBook(View view) {
        Boolean Flag = true;
        String name, authorName, price;
        name = ETbookName.getText().toString();
        authorName = ETauthorName.getText().toString();
        price = ETprice.getText().toString();

        if(name.isEmpty()){
            ETbookName.setError("Empty Input!!");
            Flag = false;
        }
        if(authorName.isEmpty()){
            ETauthorName.setError("Empty Input!!");
            Flag = false;
        }
        if(price.isEmpty()){
            ETprice.setError("Empty Input!!");
            Flag = false;
        }

        if(Flag==true){
            name = name.trim();
            authorName = authorName.trim();
            price = price.trim();
            Book book = new Book(name,authorName,price,preferences.getString("UserUid",null));
            databaseReference.child("books").push().setValue(book);
            Toast.makeText(getApplicationContext(),"Book Added Successful.",Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
