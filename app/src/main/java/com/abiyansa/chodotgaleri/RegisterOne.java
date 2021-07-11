package com.abiyansa.chodotgaleri;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;


public class RegisterOne extends AppCompatActivity {

    Button btnContinue;
    TextView btnHasAccount;
    EditText userName, passWord, emailAddress;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private ProgressBar ProgressBar;
    DatabaseReference mdatabase;
    String username,emailaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        // [END initialize_auth]

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        emailAddress = findViewById(R.id.email_address);
        btnContinue = findViewById(R.id.btn_continue);
        btnHasAccount = findViewById(R.id.btn_has_account);
        ProgressBar = findViewById(R.id.progressBar);
        ProgressBar.setVisibility(View.GONE);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister();
                ProgressBar.setVisibility(View.VISIBLE);
            }
        });

        btnHasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hasAccount = new Intent(RegisterOne.this,Login.class);
                startActivity(hasAccount);
            }
        });
    }

    private void UserRegister() {
        final String username = userName.getText().toString().trim();
        final String password = passWord.getText().toString().trim();
        final String emailaddress = emailAddress.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
            ProgressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(emailaddress)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            ProgressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            ProgressBar.setVisibility(View.GONE);
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            ProgressBar.setVisibility(View.GONE);
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailaddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendEmailVerification();
                    OnAuth(task.getResult().getUser());
                    mAuth.signOut();
                } else {
                    Toast.makeText(RegisterOne.this, "error on creating user", Toast.LENGTH_SHORT).show();
                    ProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterOne.this, "Check your Email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        ProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        mdatabase.child(uid).setValue(user);
    }

    private User BuildNewuser() {
        return new User(
                getDisplayName(),
                getUserEmail(),
                new Date().getTime()
        );
    }

    public String getDisplayName() {
        return username;
    }

    public String getUserEmail() {
        return emailaddress;
    }

}