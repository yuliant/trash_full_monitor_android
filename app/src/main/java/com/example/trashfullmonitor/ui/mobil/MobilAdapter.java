package com.example.trashfullmonitor.ui.mobil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.model.MobilSampahResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.MobilHolder>{

    private Context _context;
    private List<MobilSampahResponse> mobilSampahResponseList;

    public MobilAdapter(Context _context, List<MobilSampahResponse> mobilSampahResponseList) {
        this._context = _context;
        this.mobilSampahResponseList = mobilSampahResponseList;
    }

    @NonNull
    @Override
    public MobilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_mobil, parent, false);
        MobilAdapter.MobilHolder holder = new MobilAdapter.MobilHolder(layout);
        return holder;
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MobilAdapter.MobilHolder holder, int position) {
        final MobilSampahResponse mobilSampahResponse = mobilSampahResponseList.get(position);
        final String merek = mobilSampahResponse.getMEREK();
        final String status = mobilSampahResponse.getSTATUS();
        final String lokasi = mobilSampahResponse.getLOKASI();
        final String id_mobil = mobilSampahResponse.getIDMOBILSAMPAH();
        final String no_plat = mobilSampahResponse.getNOPLAT();

        holder.tvMerek.setText(merek+" ("+no_plat+")");
        holder.tvStatus.setText(status);
        if (Objects.equals(status, "ready")){
            holder.tvStatus.setTextColor(Color.GREEN);
        }else {
            holder.tvStatus.setTextColor(Color.RED);
        }
        holder.tvLokasi.setText(lokasi);
        holder.tvIdMobil.setText(id_mobil);
    }

    @Override
    public int getItemCount() {
        return mobilSampahResponseList.size();
    }

    public class MobilHolder extends RecyclerView.ViewHolder {
        TextView tvMerek, tvStatus, tvLokasi, tvIdMobil;
        CardView item;

        public MobilHolder(@NonNull View itemView) {
            super(itemView);
            tvMerek = itemView.findViewById(R.id.tv_merek);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvIdMobil = itemView.findViewById(R.id.tv_id_mobil);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void setFilter(List<MobilSampahResponse> filterList){
        mobilSampahResponseList = new ArrayList<>();
        mobilSampahResponseList.addAll(filterList);
        notifyDataSetChanged();
    }
}
