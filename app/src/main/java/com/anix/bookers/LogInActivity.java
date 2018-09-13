package com.anix.bookers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anix.bookers.Model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {

    EditText ETEmail,ETPassword;
    RelativeLayout Layout;
    ProgressBar PBlogIn;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ETEmail = findViewById(R.id.ETemail);
        ETPassword = findViewById(R.id.ETpassword);
        Layout = findViewById(R.id.LOparent);
        PBlogIn = findViewById(R.id.progressbarlogin);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        Layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                return false;
            }
        });
    }

    public void LogInBtn(View view) {
        PBlogIn.setVisibility(View.VISIBLE);
        final String Email,Password;
        Email = ETEmail.getText().toString().trim();
        Password = ETPassword.getText().toString().trim();
        if(Email.isEmpty()||Password.isEmpty()){
            ETEmail.setError("Invalid Input!!!");
            ETPassword.setError("Invalid Input!!!");
        }else{

            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    PBlogIn.setVisibility(View.GONE);
                    if (task.isSuccessful()){

                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                        GetUser(currentFirebaseUser.getUid());


                    }else {
                        Toast.makeText(getApplicationContext(), "Some Error Occured.", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

    public void GetUser(final String Uid){

        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children =  dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    if(child.getKey().contentEquals(Uid)){
                        Person user = child.getValue(Person.class);
                        SharedPreferences preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UserUid",Uid);
                        editor.commit();
                        editor.putString("Email",user.getEmail());
                        editor.commit();
                        editor.putString("Name",user.getName());
                        editor.commit();
                        editor.putString("Phone",user.getPhoneNo());
                        editor.commit();
                        editor.putString("NsuID",user.getNSUID());
                        editor.commit();
                        editor.putString("Gender",user.getGender());
                        editor.commit();
                        Intent intent = new Intent(LogInActivity.this,BookListActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "LogIn Successful.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void TVsignup(View view) {

        Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
        startActivity(intent);


    }
}
