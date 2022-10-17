package com.example.trashfullmonitor.ui.histori;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.model.HistoriResponse;

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
        final String lat = historiResponse.getLATITUDE();
        final String lng = historiResponse.getLONGITUDE();
        final String lokasi = historiResponse.getLOKASI();
        final String id_histori = historiResponse.getIDLISTTUGAS();
        final String status_list = historiResponse.getSTATUSLIST();
        final String tanggal = historiResponse.getTANGGAL();
        final String merek = historiResponse.getMEREK();
        final String no_plat = historiResponse.getNOPLAT();

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

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent KirimData = new Intent(_context, HistoriDetailActivity.class);
                KirimData.putExtra("ID_HISTORI", id_histori);
                KirimData.putExtra("NAMA_TEMPAT_SAMPAH", nama_tempah_sampah);
                KirimData.putExtra("LNG", lng);
                KirimData.putExtra("LAT", lat);
                KirimData.putExtra("LOKASI", lokasi);
                KirimData.putExtra("MEREK", merek);
                KirimData.putExtra("NO_PLAT", no_plat);
                KirimData.putExtra("STATUS_LIST", status_list);
                KirimData.putExtra("TANGGAL", tanggal);
                KirimData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(KirimData);
            }
        });
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
