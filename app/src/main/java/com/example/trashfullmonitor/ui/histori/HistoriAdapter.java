package com.example.trashfullmonitor.ui.histori;

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
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.model.HistoriResponse;
import com.example.trashfullmonitor.ui.daftarlokasi.DaftarLokasiAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoriAdapter extends RecyclerView.Adapter<HistoriAdapter.HistoriHolder>{

    private Context _context;
    private List<HistoriResponse> historiResponseList;

    public HistoriAdapter(Context _context, List<HistoriResponse> historiResponseList) {
        this._context = _context;
        this.historiResponseList = historiResponseList;
    }

    @NonNull
    @Override
    public HistoriAdapter.HistoriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_histori, parent, false);
        HistoriAdapter.HistoriHolder holder = new HistoriAdapter.HistoriHolder(layout);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoriAdapter.HistoriHolder holder, int position) {
        final HistoriResponse historiResponse = historiResponseList.get(position);
        final String nama_tempah_sampah = historiResponse.getNAMATEMPATSAMPAH();
        final String status_list = historiResponse.getSTATUSLIST();
        final String tanggal = historiResponse.getTANGGAL();
        final String id_histori = historiResponse.getIDLISTTUGAS();

        if (Objects.equals(status_list, "angkut")){
            holder.tvStatusList.setText(status_list);
            holder.tvStatusList.setTextColor(Color.GREEN);
        }else {
            holder.tvStatusList.setText("OTW");
            holder.tvStatusList.setTextColor(Color.RED);
        }
        holder.tvNamaTempahSampah.setText(nama_tempah_sampah);
        holder.tvTanggal.setText(tanggal);
        holder.tvIdHistori.setText(id_histori);
    }

    @Override
    public int getItemCount() {
        return historiResponseList.size();
    }

    public class HistoriHolder extends RecyclerView.ViewHolder {
        TextView tvNamaTempahSampah, tvStatusList, tvTanggal, tvIdHistori;
        CardView item;
        public HistoriHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaTempahSampah = itemView.findViewById(R.id.tv_nama_tempah_sampah);
            tvStatusList = itemView.findViewById(R.id.tv_status_list);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvIdHistori = itemView.findViewById(R.id.tv_id_histori);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void setFilter(List<HistoriResponse> filterList){
        historiResponseList = new ArrayList<>();
        historiResponseList.addAll(filterList);
        notifyDataSetChanged();
    }
}
