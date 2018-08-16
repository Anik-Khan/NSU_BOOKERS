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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogInActivity extends AppCompatActivity {

    EditText ETEmail,ETPassword;
    RelativeLayout Layout;
    ProgressBar PBlogIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ETEmail = findViewById(R.id.ETemail);
        ETPassword = findViewById(R.id.ETpassword);
        Layout = findViewById(R.id.LOparent);
        PBlogIn = findViewById(R.id.progressbarlogin);
        mAuth = FirebaseAuth.getInstance();



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
                        
                       SharedPreferences preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("UserUid",currentFirebaseUser.getUid());
                        editor.commit();

                        Intent intent = new Intent(LogInActivity.this,BookListActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "LogIn Successful.", Toast.LENGTH_LONG).show();
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(), "Some Error Occured.", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

    public void TVsignup(View view) {

        Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
        startActivity(intent);


    }
}
