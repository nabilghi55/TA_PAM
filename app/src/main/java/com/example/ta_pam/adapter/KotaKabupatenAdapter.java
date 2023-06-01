package com.example.ta_pam.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_pam.R;
import com.example.ta_pam.model.kotaKabupaten;

import java.util.ArrayList;

public class KotaKabupatenAdapter extends RecyclerView.Adapter<KotaKabupatenAdapter.KotaKabupatenViewHolder> {

    private ArrayList<kotaKabupaten> kotaKabupatenArrayList;

    public KotaKabupatenAdapter(ArrayList<kotaKabupaten> kotaKabupatenArrayList) {
        this.kotaKabupatenArrayList = kotaKabupatenArrayList;
    }

    @NonNull
    @Override
    public KotaKabupatenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kotakabupaten, parent, false);
        return new KotaKabupatenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KotaKabupatenViewHolder holder, int position) {
        kotaKabupaten kotaKabupaten = kotaKabupatenArrayList.get(position);
        holder.textViewKotaKabupaten.setText(kotaKabupaten.getNama());
    }

    @Override
    public int getItemCount() {
        return kotaKabupatenArrayList.size();
    }

    static class KotaKabupatenViewHolder extends RecyclerView.ViewHolder {
        TextView textViewKotaKabupaten;

        KotaKabupatenViewHolder(View itemView) {
            super(itemView);
            textViewKotaKabupaten = itemView.findViewById(R.id.textviewkotakabupaten);
        }
    }
}
