package com.example.ta_pam.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ta_pam.MainActivity;
import com.example.ta_pam.R;
import com.example.ta_pam.wilayah.ProvinsiActivity;

public class AyoMulai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayo_mulai);

        Button btn_Daftar;
        Button btn_Login;

        btn_Login  = findViewById(R.id.login);
        btn_Daftar = findViewById(R.id.daftar);

        btn_Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AyoMulai.this,Daftar.class);
                startActivity(intent);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AyoMulai.this, ProvinsiActivity.class);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}