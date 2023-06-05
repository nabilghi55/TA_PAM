package com.example.ta_pam.wilayah;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.R;
import com.example.ta_pam.adapter.KotaKabupatenAdapter;
import com.example.ta_pam.adapter.ProvinsiAdapter;
import com.example.ta_pam.api.ApiService;
import com.example.ta_pam.auth.Login;
import com.example.ta_pam.model.Provinsi;
import com.example.ta_pam.model.kotaKabupaten;
import com.example.ta_pam.model.kotaKabupatenResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MainActivity.java
public class KotaKabupatenActivity extends AppCompatActivity {
    private ArrayList<Provinsi> provinsiArrayList;
    private RecyclerView recyclerViewProvinsi;
    private ProvinsiAdapter provinsiAdapter;
    private ArrayList<kotaKabupaten> kotaKabupatenArrayList;
    private RecyclerView recyclerViewkotaKabupaten;
    private KotaKabupatenAdapter KotaKabupatenAdapter;
    private ArrayList<kotaKabupaten> filteredKabKotArrayList;

    EditText textSearch;
    EditText textSearchID;
    Button btn_id;
    private String inputText;
    String hasil;
    String id_provinsi, getId_provinsi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotakabupateni);
        kotaKabupatenArrayList = new ArrayList<>();
        recyclerViewkotaKabupaten = findViewById(R.id.recyclerViewkotaKabupaten);
        KotaKabupatenAdapter = new KotaKabupatenAdapter(kotaKabupatenArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewkotaKabupaten.setLayoutManager(layoutManager);
        recyclerViewkotaKabupaten.setAdapter(KotaKabupatenAdapter);
        textSearch = findViewById(R.id.editTextSearch);
        textSearchID = findViewById(R.id.cariid);
        filteredKabKotArrayList = new ArrayList<>();
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = textSearch.getText().toString().trim();
                filterKabKota(query);
                KotaKabupatenAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textSearchID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                inputText = s.toString().trim();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        String id_prov = getIntent().getExtras().getString("id");
        String nama_prov = getIntent().getExtras().getString("nama");


        ApiService.service.getkotaKabupaten(id_prov).enqueue(new Callback<kotaKabupatenResponse>() {
            @Override
            public void onResponse(Call<kotaKabupatenResponse> call, Response<kotaKabupatenResponse> response) {
                if (response.isSuccessful()) {
                    kotaKabupatenResponse kotaKabupatenResponse = response.body();
                    if (kotaKabupatenResponse != null) {
                        List<kotaKabupaten> kotaKabupatenList = kotaKabupatenResponse.getKotaKabupatenModelList();
                        if (kotaKabupatenList != null) {
                            kotaKabupatenArrayList.addAll(kotaKabupatenList);
                            KotaKabupatenAdapter.notifyDataSetChanged();

                        } else {
                            // Handle jika kotaKabupatenList bernilai null
                            Log.e(TAG, "onResponse: kosong" + response.body());
                            Toast.makeText(KotaKabupatenActivity.this, "Kota Kabupaten List Null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle jika kotaKabupatenResponse bernilai null
                        Log.e(TAG, "onResponse: halo" + response.body());
                    }
                } else {
                    // Tampilkan pesan error jika gagal mendapatkan respon dari API
                    Log.e(TAG, "onResponse: halo" + response.body());
                }
            }

              @Override
              public void onFailure(Call<kotaKabupatenResponse> call, Throwable t) {

              }
          });
    }
    public void filterKabKota(String query){
        filteredKabKotArrayList.clear();
        if(query.isEmpty()){
            filteredKabKotArrayList.addAll(kotaKabupatenArrayList);
        }else{
            query = query.toLowerCase();
            for(kotaKabupaten kotaKabupaten : kotaKabupatenArrayList){
                if(kotaKabupaten.getNama().toLowerCase().contains(query)){
                    filteredKabKotArrayList.add(kotaKabupaten);
                }
            }
        }
        KotaKabupatenAdapter = new KotaKabupatenAdapter(filteredKabKotArrayList);
        recyclerViewkotaKabupaten.setAdapter(KotaKabupatenAdapter);

        if(filteredKabKotArrayList.size()==0){
            Toast.makeText(KotaKabupatenActivity.this, "data null",Toast.LENGTH_SHORT).show();
        }
    }
    public String panggilMethod(String result) {
        return result = getInputText();
        // Lakukan sesuatu dengan result
    }

    private String getInputText() {
        return inputText;
    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setTitle("Konfirmasi Logout")
//                .setMessage("Apakah Anda ingin logout?")
//                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        logout();
//                    }
//                })
//                .setNegativeButton("Tidak", null)
//                .show();
//    }

    private void logout() {
        Intent intent = new Intent(KotaKabupatenActivity.this, Login.class);
        Toast.makeText(KotaKabupatenActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
