package com.anix.bookers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anix.bookers.Adapter.CustomAdapter;
import com.anix.bookers.Fragment.Profile;
import com.anix.bookers.Model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter cAdapter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String AuthKey = currentFirebaseUser.getUid();
        Toast.makeText(getApplicationContext(), "Anik  "+AuthKey, Toast.LENGTH_LONG).show();

        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.recycler_view);
        cAdapter = new CustomAdapter(bookList);
        RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(cLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cAdapter);

        input();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void input() {


        Book b1 = new Book(R.drawable.b2, "Programming with C", "Byron Gottfried", 250,675);
/*        Book b2 = new Book(R.drawable.intro_to_c,"Introduction to C", "Reema Thareja ",2250);
        Book b3 = new Book(R.drawable.b2,"Programming with C", "Byron Gottfried",250);
        Book b4 = new Book(R.drawable.intro_to_c,"Introduction to C", "Reema Thareja ",2250);
        Book b5 = new Book(R.drawable.b2,"Programming with C", "Byron Gottfried",250);
        Book b6 = new Book(R.drawable.intro_to_c,"Introduction to C", "Reema Thareja ",2250);
        Book b7 = new Book(R.drawable.b2,"Programming with C", "Byron Gottfried",250);
        Book b8 = new Book(R.drawable.intro_to_c,"Introduction to C", "Reema Thareja ",2250);*/

        bookList.add(b1);
 /*       aList.add(b2);
        aList.add(b3);
        aList.add(b4);
        aList.add(b5);
        aList.add(b6);
        aList.add(b7);
        aList.add(b8);*/
        cAdapter.notifyDataSetChanged();

    }

    public void BtnLogOut(MenuItem item) {

        SharedPreferences preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserUid",null);
        editor.commit();


        Intent intent = new Intent(BookListActivity.this,LogInActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(),"LOG OUT Successful.",Toast.LENGTH_LONG).show();

    }

    public void AddBook(View view) {
        Intent intent = new Intent(BookListActivity.this,AddBook.class);
        startActivity(intent);

    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();

    }

    public void BtnProfile(MenuItem item) {

        loadFragment(new Profile());



    }

    public void BtnPurchase(MenuItem item) {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
