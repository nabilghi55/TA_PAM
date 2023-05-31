package com.example.ta_pam;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.example.ta_pam.api.ApiClient;
import com.example.ta_pam.api.ApiService;

import com.example.ta_pam.ProvinsiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private ArrayList<Provinsi> provinsiArrayList;
    private RecyclerView recyclerViewProvinsi;
    private ProvinsiAdapter provinsiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provinsiArrayList = new ArrayList<>();
        recyclerViewProvinsi = findViewById(R.id.recyclerViewProvinsi);
        provinsiAdapter = new ProvinsiAdapter(provinsiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProvinsi.setLayoutManager(layoutManager);
        recyclerViewProvinsi.setAdapter(provinsiAdapter);
        ApiService.service.getProvinsi().enqueue(new Callback<ProvinsiResponse>() {
            @Override
            public void onResponse(Call<ProvinsiResponse> call, Response<ProvinsiResponse> response) {
                if (response.isSuccessful()) {
                    ProvinsiResponse provinsiResponse = response.body();
                    if (provinsiResponse != null) {
                        List<Provinsi> provinsiList = provinsiResponse.getProvinsiList();
                        if (provinsiList != null) {
                            provinsiArrayList.addAll(provinsiList);
                            provinsiAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Datanya ada kok", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            // Handle jika provinsiList bernilai null
                            Log.e(TAG, "onResponse: kosong"+response.body() );
                            Toast.makeText(MainActivity.this, "Provinsi List Null", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        // Handle jika provinsiResponse bernilai null
                        Log.e(TAG, "onResponse: halo"+response.body() );
                        Toast.makeText(MainActivity.this, "Provinsi Response Null", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Tampilkan pesan error jika gagal mendapatkan respon dari API
                    Log.e(TAG, "onResponse: halo"+response.body() );
                    Toast.makeText(MainActivity.this, "Gagal respone api", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ProvinsiResponse> call, Throwable t) {
                // Tampilkan pesan error jika terjadi kegagalan koneksi atau pemrosesan
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
