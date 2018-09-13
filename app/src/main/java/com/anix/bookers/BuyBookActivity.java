package com.anix.bookers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anix.bookers.Model.Book;
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
}
