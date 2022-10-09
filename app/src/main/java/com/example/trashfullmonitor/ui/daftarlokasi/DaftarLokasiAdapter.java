package com.example.trashfullmonitor.ui.daftarlokasi;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

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
//        final


        holder.tvNamaTempahSampah.setText(daftarLokasiResponse.getNAMATEMPATSAMPAH());
        holder.tvBerat.setText(daftarLokasiResponse.getBERAT());
        holder.tvLokasi.setText(daftarLokasiResponse.getLOKASI());
        holder.tvIdDaftarLokasi.setText(daftarLokasiResponse.getIDTEMPATSAMPAH());

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
}
