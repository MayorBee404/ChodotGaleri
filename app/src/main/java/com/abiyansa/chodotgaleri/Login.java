package com.abiyansa.chodotgaleri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class Login extends AppCompatActivity {

    TextView btnCreateNewAccount;
    Button btnSignIn;
    EditText xemail, xpassWord;
    String email, password;
    ProgressDialog dialog;
    ProgressBar progressbar;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    public static final String userEmail = "";
    public static final String TAG = "LOGIN";
    public static final String emaillogin = "EMAILLOGIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        xemail = findViewById(R.id.edtemail);
        xpassWord = findViewById(R.id.edtpassword);
        btnCreateNewAccount = findViewById(R.id.btn_create_new_account);
        btnSignIn = findViewById(R.id.btn_login);
        progressbar = findViewById(R.id.xprogressbar);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent masuk = new Intent(Login.this, Dashboard.class);
                    masuk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(masuk);
                } else {

                    Log.d(TAG, "AuthStateChanged:Logout");

                }
            }
        };
        progressbar.setVisibility(View.GONE);

        //Goto RegisterOne
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent gotoregisterone = new Intent(Login.this, RegisterOne.class);
                startActivity(gotoregisterone);
            }
        });
        //Goto HomeAct
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        email = xemail.getText().toString().trim();
        password = xpassWord.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Login.this, "Enter the correct Username", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(Login.this, "Login not successfull", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    checkIfEmailVerified();
                    progressbar.setVisibility(View.GONE);

                }
            }
        });

    }

    private void checkIfEmailVerified() {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = users.isEmailVerified();
        if (!emailVerified) {
            Toast.makeText(this, "Verify the Email Id", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            finish();
        } else {
            xemail.getText().clear();

            xpassWord.getText().clear();
            Intent intent = new Intent(Login.this, Dashboard.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(userEmail, email);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out.
        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        Login.super.finish();
    }

}
