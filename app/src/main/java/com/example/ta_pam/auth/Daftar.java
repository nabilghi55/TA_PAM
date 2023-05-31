package com.example.ta_pam.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_pam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Daftar extends AppCompatActivity {
    EditText inputUsername, inputEmail, inputPassword, inputNoTel;
    String username, email, password, noTel;
    Button btn_daftar;
    FirebaseAuth mAuth;
    TextView text_Masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        inputUsername = findViewById(R.id.edit_username);
        inputEmail = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        inputNoTel = findViewById(R.id.edit_telepon);
        btn_daftar = findViewById(R.id.btn_daftar);
        text_Masuk = findViewById(R.id.text_masuk);
        mAuth = FirebaseAuth.getInstance();

        text_Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Daftar.this, Login.class);
                startActivity(intent);
            }
        });



        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrasi();
            }
        });
    }

    private void registrasi() {
        email = inputEmail.getText().toString();
        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();
        noTel = inputNoTel.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Daftar.this, "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                             inputNoTel.getText().clear();
                             inputPassword.getText().clear();
                             inputEmail.getText().clear();
                             inputUsername.getText().clear();
                            Intent intent = new Intent(Daftar.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Daftar.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
