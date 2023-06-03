package com.example.ta_pam.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_pam.Loading;
import com.example.ta_pam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {
    EditText inputEmail, inputPassword;
    Button btn_login, buttonTourguide;
    FirebaseAuth mAuth;
    TextView text_login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        inputEmail = findViewById(R.id.login_email);
        inputPassword = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.btn_login);
        buttonTourguide = findViewById(R.id.buttonTourguide);
        text_login = findViewById(R.id.text_masuk);
        mAuth = FirebaseAuth.getInstance();
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this, Daftar.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        buttonTourguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this, Login.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Login2.this, AyoMulai.class);
        startActivity(intent);
    }

    private void login() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login berhasil
                            Toast.makeText(Login2.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login2.this, Loading.class);
                            inputPassword.getText().clear();
                            inputEmail.getText().clear();
                            startActivity(intent);
                        } else {
                            // Login gagal
                            Toast.makeText(Login2.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
