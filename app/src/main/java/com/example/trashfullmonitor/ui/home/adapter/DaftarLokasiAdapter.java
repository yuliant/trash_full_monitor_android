package com.example.trashfullmonitor.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.ui.home.fragment.DaftarLokasiFragment;

import java.util.ArrayList;
import java.util.List;

public class DaftarLokasiAdapter extends RecyclerView.Adapter<DaftarLokasiAdapter.DaftarLokasiViewHolder> {

    private ArrayList<DaftarLokasiResponse> mData = new ArrayList<>();
    private DaftarLokasiFragment context;

    public DaftarLokasiAdapter(DaftarLokasiFragment context) {
        this.context = context;
    }

    public void setmData(ArrayList<DaftarLokasiResponse> mData) {
        mData.clear();
        mData.addAll(mData);
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public DaftarLokasiViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new DaftarLokasiViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarLokasiViewHolder holder, int position) {
        DaftarLokasiResponse daftarLokasiResponse = mData.get(position);

        holder.tvNamaTempahSampah.setText(daftarLokasiResponse.getNAMATEMPATSAMPAH());
        holder.tvBerat.setText(daftarLokasiResponse.getBERAT());
        holder.tvLokasi.setText(daftarLokasiResponse.getLOKASI());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DaftarLokasiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaTempahSampah, tvBerat, tvLokasi;

        public DaftarLokasiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaTempahSampah = itemView.findViewById(R.id.tv_nama_tempah_sampah);
            tvBerat = itemView.findViewById(R.id.tv_berat);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(DaftarLokasiResponse data);
    }
}
