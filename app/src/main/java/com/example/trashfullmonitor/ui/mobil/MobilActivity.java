package com.example.trashfullmonitor.ui.mobil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.model.MobilSampahResponse;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobilActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SwipeRefreshLayout swpGetData;
    ProgressBar pbGetData;
    MobilAdapter mobilAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MobilSampahResponse> mobilSampahResponseList = new ArrayList<>();
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
        setContentView(R.layout.activity_mobil);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.mobil_sampah);
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
        Call<Respon> mobilRespon = operationalApi.mobilResponse(api_key);
        mobilRespon.enqueue(new Callback<Respon>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<Respon> call, @NonNull Response<Respon> response) {
                pbGetData.setVisibility(View.INVISIBLE);
                assert response.body() != null;
                if (response.body().isStatus()){
                    mobilSampahResponseList = response.body().getMobilSampahResponses();
                    mobilAdapter = new MobilAdapter(MobilActivity.this, mobilSampahResponseList);
                    recyclerView.setAdapter(mobilAdapter);
                    mobilAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Respon> call, @NonNull Throwable t) {
                pbGetData.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView  = new SearchView(this);
        searchView.setQueryHint("Cari Sesuatu....");
        searchView.setBackgroundColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<MobilSampahResponse> dataFilter = new ArrayList<>();
                for(MobilSampahResponse data : mobilSampahResponseList){
                    String merek = data.getMEREK().toLowerCase();
                    String plat = data.getNOPLAT().toLowerCase();
                    String lokasi = data.getLOKASI().toLowerCase();
                    String status = data.getSTATUS().toLowerCase();
                    if(merek.contains(newText)
                            || lokasi.contains(newText)
                            || status.contains(newText)
                            || plat.contains(newText)
                    ){
                        dataFilter.add(data);
                    }
                }
                mobilAdapter.setFilter(dataFilter);
                return false;
            }
        });
        searchItem.setActionView(searchView);
        return true;
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