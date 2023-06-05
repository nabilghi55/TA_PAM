package com.example.ta_pam;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.adapter.MyAdapter;
import com.example.ta_pam.database.Database;
import com.example.ta_pam.wilayah.KotaKabupatenActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Database> dataList;
    MyAdapter adapter;
    SearchView searchView;
    TextView namaUser;
    TextView namaLokasi;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        namaLokasi = findViewById(R.id.teksLokasi);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(DashboardActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        if (firebaseUser != null) {
            String namaPengguna = firebaseUser.getDisplayName();
            if (namaPengguna != null) {
                // Lakukan sesuatu dengan nama pengguna
                System.out.println(namaPengguna);
            }
            // Gunakan nilai namaPengguna untuk menampilkan nama pengguna di TextView atau komponen tampilan lainnya.
            TextView textViewNamaPengguna = findViewById(R.id.nama);
            textViewNamaPengguna.setText("Welcome, "+firebaseUser.getEmail().substring(0,5));
        }else{
            Log.d("Debug_gagal", "Data: " + namaUser);
        }

        KotaKabupatenActivity kotaKabupatenActivity = new KotaKabupatenActivity();
        String lokasi = getIntent().getExtras().getString("nama");
        namaLokasi.setText(lokasi);
        databaseReference = FirebaseDatabase.getInstance().getReference("Antara Application");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Database Database = itemSnapshot.getValue(Database.class);

                    Database.setKey(itemSnapshot.getKey());

                    dataList.add(Database);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

    }
    public void searchList(String text){
        ArrayList<Database> searchList = new ArrayList<>();
        for (Database database: dataList){
            if (database.getDataJudul().toLowerCase().contains(text.toLowerCase())){
                searchList.add(database);
            }
        }
        adapter.searchDataList(searchList);
    }
}
