package com.anix.bookers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anix.bookers.Model.Book;
import com.anix.bookers.Model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BuyBookActivity extends AppCompatActivity {
    TextView name, author, price;
    EditText delivery;
    Intent intent;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    SharedPreferences preferences;
    String index;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);
        name = findViewById(R.id.booknameid);
        author = findViewById(R.id.authornameid);
        price = findViewById(R.id.priceid);
        delivery = findViewById(R.id.ETdelivery);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        intent = getIntent();
        name.setText("Book Name: "+intent.getStringExtra("Name"));
        author.setText("Author Name: "+intent.getStringExtra("Author"));
        price.setText("Price: "+intent.getStringExtra("Price"));
        index = intent.getStringExtra("i");
        preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
    }

    public void BtnBuy(View view) {

        final int i = Integer.parseInt(index);
        //Log.e("index", String.valueOf(i));
        databaseReference.child("books").addValueEventListener(new ValueEventListener() {
            int count= -1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children =  dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    count++;
                    if(count==i){
                        Book book = child.getValue(Book.class);
                        sendEmail(book,child.getKey());

                        Log.e("count", String.valueOf(count));
                        child.getRef().removeValue();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        finish();
    }

    public void sendEmail(final Book book,String bookKey){

        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.setType("message/rfc822");
        intent2.putExtra(Intent.EXTRA_EMAIL  , new String[]{"monjuramanik@gmail.com"});
        intent2.putExtra(Intent.EXTRA_SUBJECT, "Book Request from NSUBookers");
        intent2.putExtra(Intent.EXTRA_TEXT   , "I would like order this book: "+book.getName()+". Userid: "+book.getUserId()+" . Book Key:  "+bookKey+" . MyId:"+preferences.getString("UserUid",null));
        try {
            startActivity(Intent.createChooser(intent2, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
