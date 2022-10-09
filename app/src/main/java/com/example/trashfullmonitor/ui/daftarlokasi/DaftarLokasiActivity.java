package com.example.trashfullmonitor.ui.daftarlokasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLokasiActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SwipeRefreshLayout swpGetData;
    ProgressBar pbGetData;
    DaftarLokasiAdapter daftarLokasiAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DaftarLokasiResponse> daftarLokasiResponses = new ArrayList<>();
    RecyclerView recyclerView;

    String id, api_key = BuildConfig.API_KEY;


    private void init(){
        id = sessionManager.getUserDetail().get(SessionManager.ID_PENGGUNA);

        swpGetData = findViewById(R.id.swpGetData);
        pbGetData = findViewById(R.id.pbGetData);
        recyclerView = findViewById(R.id.rvData);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_lokasi);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.daftar_lokasi);
        this.setTitle(title);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        getData(api_key);

        swpGetData.setOnRefreshListener(() -> {
            swpGetData.setRefreshing(true);
            getData(api_key);
            swpGetData.setRefreshing(false);
        });
    }

    private void getData(String api_key){
        pbGetData.setVisibility(View.VISIBLE);

        OperationalApi operationalApi = ApiClient.getData().create(OperationalApi.class);
        Call<Respon> daftarLokasiResponse = operationalApi.daftarLokasiResponse(api_key);
        daftarLokasiResponse.enqueue(new Callback<Respon>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                pbGetData.setVisibility(View.INVISIBLE);
                if (response.body().isStatus()){
                    daftarLokasiResponses = response.body().getDaftarLokasiResponseList();

                    daftarLokasiAdapter = new DaftarLokasiAdapter(DaftarLokasiActivity.this, daftarLokasiResponses);
                    recyclerView.setAdapter(daftarLokasiAdapter);
                    daftarLokasiAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                pbGetData.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void moveToLogin() {
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
    }
}