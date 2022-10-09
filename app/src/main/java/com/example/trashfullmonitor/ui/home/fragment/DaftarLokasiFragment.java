package com.example.trashfullmonitor.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.ui.home.adapter.DaftarLokasiAdapter;
import com.example.trashfullmonitor.ui.home.detail.DetailDaftarLokasiActivity;
import com.example.trashfullmonitor.ui.home.viewmodel.DaftarLokasiViewModel;

import java.util.ArrayList;
import java.util.List;

public class DaftarLokasiFragment extends Fragment {

    private DaftarLokasiAdapter daftarLokasiAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private DaftarLokasiViewModel daftarLokasiViewModel;
//    private SearchTvViewModel searchTvViewModel;

    private EditText edtSearchTv;
    String api_key = BuildConfig.API_KEY;

    public DaftarLokasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_lokasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        recyclerView = rootView.findViewById(R.id.tv_recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);
//        edtSearchTv = rootView.findViewById(R.id.edit_search_tv);

        addData();
//        searchData();
        showRecyclerList();
        showLoading(true);

        rootView.findViewById(R.id.btn_search_tv).setOnClickListener(myListener);
    }

    private void addData(){
        daftarLokasiViewModel = ViewModelProviders.of(this).get(DaftarLokasiViewModel.class);
        daftarLokasiViewModel.getDaftarLokasi().observe(getViewLifecycleOwner(), getDaftarLokasi);
        daftarLokasiViewModel.setListDaftarLokasi(getActivity(), api_key);
    }

    private void showRecyclerList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        daftarLokasiAdapter = new DaftarLokasiAdapter(this);
        recyclerView.setAdapter(daftarLokasiAdapter);
        recyclerView.setHasFixedSize(true);

        daftarLokasiAdapter.setOnItemClickCallback(new DaftarLokasiAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DaftarLokasiResponse data) {
                showSelectedHero(data);
            }
        });
    }

    private void showSelectedHero(DaftarLokasiResponse daftarLokasiResponse) {
        Intent intent = new Intent(getContext(), DetailDaftarLokasiActivity.class);
//        intent.putExtra(DetailDaftarLokasiActivity.DETAIL_TV_SHOW, daftarLokasiResponse);
        startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private Observer<ArrayList<DaftarLokasiResponse>> getDaftarLokasi = new Observer<ArrayList<DaftarLokasiResponse>>() {
        @Override
        public void onChanged(ArrayList<DaftarLokasiResponse> daftarLokasiResponses) {
            if (daftarLokasiResponses != null) {
                daftarLokasiAdapter.setmData(daftarLokasiResponses);
                showLoading(false);
            }
        }
    };

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String daftarlokasi = edtSearchTv.getText().toString();
            if (TextUtils.isEmpty(daftarlokasi)){
                daftarLokasiViewModel.setListDaftarLokasi(getActivity(), api_key);
                showLoading(true);
            }else{
                daftarLokasiViewModel.setListDaftarLokasi(getActivity(), daftarlokasi);
                showLoading(true);
            }
        }
    };
}