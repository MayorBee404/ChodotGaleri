package com.abiyansa.chodotgaleri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted extends AppCompatActivity {
    Animation topToBottom,fromright;
    Button btnSignIn, btnNewAccount;
    TextView introApp;
    ImageView emblemWhite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        emblemWhite     = findViewById(R.id.ic_emblem_white);
        introApp        = findViewById(R.id.intro_app);
        btnNewAccount   = findViewById(R.id.btn_sign_up);
        btnSignIn  = findViewById(R.id.btn_login);

        //load animation
        fromright       = AnimationUtils.loadAnimation(this, R.anim.from_right);
        topToBottom     = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        //Run Animation
        emblemWhite.startAnimation(fromright);
        introApp.startAnimation(fromright);

        //Goto Sign In
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotosign = new Intent(GetStarted.this,Login.class);
                startActivity(gotosign);
            }
        });

        //Goto RegisterOne
        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregisterone = new Intent(GetStarted.this,RegisterOne.class);
                startActivity(gotoregisterone);
            }
        });
    }
}

