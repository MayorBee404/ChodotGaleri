package com.abiyansa.chodotgaleri;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    Animation   appSplash, btmToTop;
    ImageView   appLogo;
    TextView    appSubtitle;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       //Load animation
        appSplash   = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btmToTop    = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);

        // load element
        appLogo     = findViewById(R.id.app_logo);
        appSubtitle = findViewById(R.id.please_fill);

        //Run animation
        appLogo.startAnimation(appSplash);
        appSubtitle.startAnimation(btmToTop);

        // Memanggil getUsernameLocal
        getUsernameLocal();

    }

    public  void  getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (username_key_new.isEmpty()){
            //setting timer untuk 2 detik
            //jika username kosong maka direct dari halaman SplashAct ke GetStarted
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogetstarted = new Intent(Splash.this, GetStarted.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000); // 2000 millis =  2 detik

        }
        else {
            //setting timer untuk 2 detik
            //jika username tidak kosong maka direct dari halaman SplashAct ke HomeAct
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogethome = new Intent(Splash.this, Dashboard.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000); // 2000 millis =  2 detik
        }
    }
}
