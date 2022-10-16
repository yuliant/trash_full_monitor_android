package com.example.trashfullmonitor.ui.daftarlokasi;

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
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaftarLokasiAdapter extends RecyclerView.Adapter<DaftarLokasiAdapter.DaftarLokasiHolder>{

    private Context _context;
    private List<DaftarLokasiResponse> daftarLokasiResponseList;

    public DaftarLokasiAdapter(Context _context, List<DaftarLokasiResponse> daftarLokasiResponseList) {
        this._context = _context;
        this.daftarLokasiResponseList = daftarLokasiResponseList;
    }

    @NonNull
    @Override
    public DaftarLokasiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        DaftarLokasiHolder holder = new DaftarLokasiHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarLokasiAdapter.DaftarLokasiHolder holder, int position) {
        final DaftarLokasiResponse daftarLokasiResponse = daftarLokasiResponseList.get(position);
        final String nama_tempah_sampah = daftarLokasiResponse.getNAMATEMPATSAMPAH();
        final String berat = daftarLokasiResponse.getBERAT();
        final String lokasi = daftarLokasiResponse.getLOKASI();
        final String id_tempat_sampah = daftarLokasiResponse.getIDTEMPATSAMPAH();
        final String status_jemput = daftarLokasiResponse.getSTATUSJEMPUT();

        holder.tvNamaTempahSampah.setText(nama_tempah_sampah);

        if (Objects.equals(status_jemput, "1")){
            holder.tvBerat.setText("X");
            holder.tvBerat.setTextColor(Color.RED);
        }else {
            holder.tvBerat.setText(berat);
            if (Integer.parseInt(berat) < 80){
                holder.tvBerat.setTextColor(Color.GREEN);
            }else {
                holder.tvBerat.setTextColor(Color.RED);
            }
        }

        holder.tvLokasi.setText(lokasi);
        holder.tvIdDaftarLokasi.setText(id_tempat_sampah);

        holder.item.setOnClickListener(view -> {

            Intent KirimData = new Intent(_context, HomeActivity.class);
//            KirimData.putExtra("ID_TEMPAT_SAMPAH", idKunjungan);
//            KirimData.putExtra("NAMA_KUNJUNGAN", namaKunjungan);
//            KirimData.putExtra("ALAMAT_KUNJUNGAN", alamatKunjungan);
//            KirimData.putExtra("TELP_KUNJUNGAN", telpKunjungan);
//            KirimData.putExtra("KETERANGAN_KUNJUNGAN", keteranganKunjungan);
//            KirimData.putExtra("FOTO_KUNJUNGAN", fotoKunjungan);
            _context.startActivity(KirimData);
        });

    }

    @Override
    public int getItemCount() {
        return daftarLokasiResponseList.size();
    }

    public class DaftarLokasiHolder extends RecyclerView.ViewHolder {
        TextView tvNamaTempahSampah, tvBerat, tvLokasi, tvIdDaftarLokasi;
        CardView item;
        public DaftarLokasiHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaTempahSampah = itemView.findViewById(R.id.tv_nama_tempah_sampah);
            tvBerat = itemView.findViewById(R.id.tv_berat);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvIdDaftarLokasi = itemView.findViewById(R.id.tv_id_daftar_lokasi);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void setFilter(List<DaftarLokasiResponse> filterList){
        daftarLokasiResponseList = new ArrayList<>();
        daftarLokasiResponseList.addAll(filterList);
        notifyDataSetChanged();
    }
}
