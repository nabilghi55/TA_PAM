package com.example.ta_pam.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.models.Provinsi;
import com.example.ta_pam.R;

import java.util.ArrayList;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder> {

    private ArrayList<Provinsi> provinsiArrayList;

    public ProvinsiAdapter(ArrayList<Provinsi> provinsiArrayList) {
        this.provinsiArrayList = provinsiArrayList;
    }

    @NonNull
    @Override
    public ProvinsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provinsi, parent, false);
        return new ProvinsiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinsiViewHolder holder, int position) {
        Provinsi provinsi = provinsiArrayList.get(position);
        holder.textViewProvinsi.setText(provinsi.getNama());
    }

    @Override
    public int getItemCount() {
        return provinsiArrayList.size();
    }

    static class ProvinsiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProvinsi;

        ProvinsiViewHolder(View itemView) {
            super(itemView);
            textViewProvinsi = itemView.findViewById(R.id.textViewProvinsi);
        }
    }
}
