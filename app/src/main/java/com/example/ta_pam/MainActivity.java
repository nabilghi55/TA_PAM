package com.example.ta_pam;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.ta_pam.adapter.ProvinsiAdapter;
import com.example.ta_pam.adapter.KotaKabupatenAdapter;
import com.example.ta_pam.api.ApiService;

import com.example.ta_pam.auth.Login;
import com.example.ta_pam.model.Provinsi;
import com.example.ta_pam.model.kotaKabupaten;
import com.example.ta_pam.model.kotaKabupatenResponse;
import com.example.ta_pam.wilayah.KotaKabupatenActivity;
import com.example.ta_pam.wilayah.ProvinsiActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private ArrayList<Provinsi> provinsiArrayList;
    private RecyclerView recyclerViewProvinsi;
    private ProvinsiAdapter provinsiAdapter;
    private ArrayList<kotaKabupaten> kotaKabupatenArrayList;
    private RecyclerView recyclerViewkotaKabupaten;
    private KotaKabupatenAdapter KotaKabupatenAdapter;
    Button cariKotaKab;
    Button cariProv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cariKotaKab = findViewById(R.id.search_city);
        cariProv = findViewById(R.id.search_prov);
        cariProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProvinsiActivity.class);
                startActivity(intent);
            }
        });
        cariKotaKab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, KotaKabupatenActivity.class);
                startActivity(intent2);
            }
        });
    }
        @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda ingin logout?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        Toast.makeText(MainActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
