package com.example.ta_pam.wilayah;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.DashboardActivity;
import com.example.ta_pam.R;
import com.example.ta_pam.adapter.ProvinsiAdapter;
import com.example.ta_pam.api.ApiService;
import com.example.ta_pam.auth.Login;
import com.example.ta_pam.model.Provinsi;
import com.example.ta_pam.model.ProvinsiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinsiActivity extends AppCompatActivity implements  ProvinsiAdapter.ProvinsiClickListener {
    private ArrayList<Provinsi> provinsiArrayList;
    private RecyclerView recyclerViewProvinsi;
    private ProvinsiAdapter provinsiAdapter;
    private ArrayList<Provinsi> filteredProvinsiArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provinsi);

        provinsiArrayList = new ArrayList<>();
        recyclerViewProvinsi = findViewById(R.id.recyclerViewProvinsi);
        provinsiAdapter = new ProvinsiAdapter(provinsiArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProvinsi.setLayoutManager(layoutManager);
        recyclerViewProvinsi.setAdapter(provinsiAdapter);

        EditText textSearch;
        textSearch = findViewById(R.id.editTextSearch);

        filteredProvinsiArrayList = new ArrayList<>();
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = textSearch.getText().toString().trim();
                filterProvinsi(query);
                provinsiAdapter.notifyDataSetChanged();
                provinsiAdapter.setOnProvinsiClickListener(ProvinsiActivity.this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ApiService.service.getProvinsi().enqueue(new Callback<ProvinsiResponse>() {
            @Override
            public void onResponse(Call<ProvinsiResponse> call, Response<ProvinsiResponse> response) {
                if (response.isSuccessful()) {
                    ProvinsiResponse provinsiResponse = response.body();
                    if (provinsiResponse != null) {
                        List<Provinsi> provinsiList = provinsiResponse.getProvinsiList();
                        if (provinsiList != null) {
                            provinsiArrayList.addAll(provinsiList);
                            filterProvinsi(textSearch.getText().toString().trim());
                            provinsiAdapter.notifyDataSetChanged();
                            provinsiAdapter.setOnProvinsiClickListener(ProvinsiActivity.this);

                        } else {
                            // Handle jika provinsiList bernilai null
                            Log.e(TAG, "onResponse: Provinsi List Null");
                            Toast.makeText(ProvinsiActivity.this, "Provinsi List Null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle jika provinsiResponse bernilai null
                        Log.e(TAG, "onResponse: Provinsi Response Null");
                        Toast.makeText(ProvinsiActivity.this, "Provinsi Response Null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tampilkan pesan error jika gagal mendapatkan respon dari API
                    Log.e(TAG, "onResponse: Gagal response API");
                    Toast.makeText(ProvinsiActivity.this, "Gagal response API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProvinsiResponse> call, Throwable t) {
                // Tampilkan pesan error jika terjadi kegagalan koneksi atau pemrosesan
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(ProvinsiActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void filterProvinsi(String query) {
        filteredProvinsiArrayList.clear();
        if (query.isEmpty()) {
            filteredProvinsiArrayList.addAll(provinsiArrayList);
        } else {
            query = query.toLowerCase();
            for (Provinsi provinsi : provinsiArrayList) {
                if (provinsi.getNamaProvinsi().toLowerCase().contains(query)) {
                    filteredProvinsiArrayList.add(provinsi);
                }
            }
        }
        provinsiAdapter = new ProvinsiAdapter(filteredProvinsiArrayList); // Menggunakan referensi yang baru
        recyclerViewProvinsi.setAdapter(provinsiAdapter); // Mengatur adapter dengan referensi yang baru

        if (filteredProvinsiArrayList.size() == 0) {
            Toast.makeText(ProvinsiActivity.this, "Tidak ada provinsi yang cocok", Toast.LENGTH_SHORT).show();
        }
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
        Intent intent = new Intent(ProvinsiActivity.this, Login.class);
        Toast.makeText(ProvinsiActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();

    }
    public void onProvinsiClick(Provinsi provinsi) {
        Intent namaProvinsi = new Intent(ProvinsiActivity.this, DashboardActivity.class);
        namaProvinsi.putExtra("id",provinsi.getId().toString());
        namaProvinsi.putExtra("nama",provinsi.getNamaProvinsi());
        startActivity(namaProvinsi);
    }
}
