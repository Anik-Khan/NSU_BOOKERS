package com.anix.bookers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView TVBookers, TVTagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation();

        final Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
        final Intent intent2 = new Intent(SplashActivity.this, BookListActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    SharedPreferences preferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
                    String name = preferences.getString("UserUid", null);
                    if(name == null){
                        startActivity(intent);
                    }else{
                        startActivity(intent2);
                    }

                    finish();
                }
            }
        };
        timer.start();

    }

    public void Animation(){
        TVBookers = findViewById(R.id.TVbookers);
        TVTagline = findViewById(R.id.TVtagline);

        Animation myanime = AnimationUtils.loadAnimation(this,R.anim.transition);
        TVBookers.startAnimation(myanime);
        TVTagline.startAnimation(myanime);

    }


}

