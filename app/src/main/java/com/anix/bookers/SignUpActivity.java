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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anix.bookers.Model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.util.Patterns.EMAIL_ADDRESS;

public class SignUpActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101 ;
    EditText Name, PhoneNo, Email, NSUID, Password, ConfirmPassword;
    RadioButton Male, Female;
    FirebaseDatabase database;
    DatabaseReference userRef;
    private FirebaseAuth mAuth;
    ProgressBar PBsignUp;
    RelativeLayout layout;
    int flag2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        PBsignUp = findViewById(R.id.progressbarsignup);
        Name = findViewById(R.id.ETSingupfullname);
        PhoneNo = findViewById(R.id.ETsignupphoneno);
        Email = findViewById(R.id.ETsignupemail);
        NSUID = findViewById(R.id.ETsignupnsuid);
        Password = findViewById(R.id.ETSignuppassword);
        ConfirmPassword = findViewById(R.id.ETSignupconpassword);
        Male = findViewById(R.id.Radiobtnmale);
        Female = findViewById(R.id.Radiobtnfemale);

        ClearInputField();
        layout = findViewById(R.id.RLsignuplayout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                return false;
            }
        });


    }

    public void ClearInputField() {
        Name.setText("");
        PhoneNo.setText("");
        Email.setText("");
        NSUID.setText("");
        Password.setText("");
        ConfirmPassword.setText("");
    }

    public void BtnSignUp(View view) {
        boolean flag = true;
        String name, phone, email, nsuid, password, conpassword, gender;

        name = Name.getText().toString().trim();
        if (name.isEmpty()) {
            Name.setError("Invalid Input!!!");
            Name.requestFocus();
            flag = false;
        }

        phone = PhoneNo.getText().toString().trim();
        if (phone.isEmpty()) {
            PhoneNo.setError("Invalid Input!!!");
            PhoneNo.requestFocus();
            flag = false;
        }

        email = Email.getText().toString().trim();
        if (email.isEmpty() || !EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Invalid Input!!!");
            Email.requestFocus();
            flag = false;
        }

        nsuid = NSUID.getText().toString().trim();
        if (nsuid.isEmpty() || nsuid.length() != 10) {
            NSUID.setError("Invalid Input!!!");
            NSUID.requestFocus();
            flag = false;
        }

        conpassword = ConfirmPassword.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (password.isEmpty()||password.length()<6) {
            Password.setError("Invalid Input!!!");
            Password.requestFocus();
            flag = false;
        }

        if (conpassword.isEmpty()) {
            ConfirmPassword.setError("Invalid Input!!!");
            ConfirmPassword.requestFocus();
            flag = false;
        }

        if (!conpassword.equals(password)) {
            ConfirmPassword.setError("Different Password!!");
            ConfirmPassword.requestFocus();
            Password.setError("Different Password!!");
            Password.requestFocus();
            flag = false;
        }
        if (!Male.isChecked() && !Female.isChecked()) {
            flag = false;
            Male.requestFocus();
            Female.requestFocus();
        }

        if (flag == true) {
            if (Male.isChecked()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
            PBsignUp.setVisibility(View.VISIBLE);


            SignUpNewUser(email, password);

                SignInNewUser(email, password, name, phone, nsuid, gender);



        }

    }

    public void SavetoFireBaseDB(Person person, String UserUid) {


        userRef.child("user").child(UserUid).setValue(person);
    }

    public void SignInNewUser(final String Email, final String Password,final String Name, final String Phone, final String Nsuid, final String Gender){

        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                    SharedPreferences preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("UserUid",currentFirebaseUser.getUid());
                    editor.commit();
                    String AuthKey = currentFirebaseUser.getUid();
                    Person person = new Person(Name, Email, Phone, Nsuid, Gender,AuthKey);
                    SavetoFireBaseDB(person,AuthKey);
                    Intent intent = new Intent(SignUpActivity.this,BookListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "LogIn Successful.", Toast.LENGTH_LONG).show();
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(), "Some Error Occured.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void SignUpNewUser(final String Email, String Password) {

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    flag2=1;
                    Toast.makeText(getApplicationContext(), "SignUp Successful.", Toast.LENGTH_LONG).show();
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already Registered.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some Error Occured.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }



}





















