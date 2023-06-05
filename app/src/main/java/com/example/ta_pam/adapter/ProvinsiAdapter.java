package com.example.ta_pam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.model.Provinsi;
import com.example.ta_pam.R;

import java.util.ArrayList;
import java.util.List;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder> {
    private List<Provinsi> provinsiList;
    private ArrayList<Provinsi> provinsiArrayList;

    public ProvinsiAdapter(ArrayList<Provinsi> provinsiArrayList) {
        this.provinsiArrayList = provinsiArrayList;
    }
    public interface ProvinsiClickListener {
        void onProvinsiClick(Provinsi provinsi);
    }
    private ProvinsiClickListener provinsiClickListener;
    public void setOnProvinsiClickListener(ProvinsiClickListener clickListener) {
        this.provinsiClickListener = clickListener;
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
        holder.textViewProvinsi.setText(provinsi.getNamaProvinsi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provinsiClickListener != null) {
                    provinsiClickListener.onProvinsiClick(provinsi);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinsiArrayList.size();
    }

    public void setProvinsiList(List<Provinsi> provinsiList) {
        this.provinsiList = provinsiList;
        notifyDataSetChanged();
    }

    static class ProvinsiViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProvinsi;

        ProvinsiViewHolder(View itemView) {
            super(itemView);
            textViewProvinsi = itemView.findViewById(R.id.textViewProvinsi);
        }
    }
}
