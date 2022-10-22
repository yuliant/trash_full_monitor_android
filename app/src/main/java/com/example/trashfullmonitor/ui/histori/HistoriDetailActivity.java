package com.example.trashfullmonitor.ui.histori;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriDetailActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String api_key = BuildConfig.API_KEY, id_histori;
    Button btn_simpan;
    ProgressBar pbGetData;

    @SuppressLint("SetTextI18n")
    private void init() {
        id_histori = getIntent().getStringExtra("ID_HISTORI");
        String nama_tempat_sampah = getIntent().getStringExtra("NAMA_TEMPAT_SAMPAH");
        String lng = getIntent().getStringExtra("LNG");
        String lat = getIntent().getStringExtra("LAT");
        String lokasi = getIntent().getStringExtra("LOKASI");
        String merek = getIntent().getStringExtra("MEREK");
        String no_plat = getIntent().getStringExtra("NO_PLAT");
        String status_list = getIntent().getStringExtra("STATUS_LIST");
        String tanggal = getIntent().getStringExtra("TANGGAL");

        pbGetData = findViewById(R.id.pbGetData);
        EditText et_nama_tempat_sampah = findViewById(R.id.et_nama_tempat_sampah)
                , et_koordinat = findViewById(R.id.et_koordinat)
                , et_lokasi = findViewById(R.id.et_lokasi)
                , et_mobil = findViewById(R.id.et_mobil)
                , et_tanggal = findViewById(R.id.et_tanggal)
                , et_status = findViewById(R.id.et_status);
        btn_simpan = findViewById(R.id.btn_simpan);

        et_nama_tempat_sampah.setText(nama_tempat_sampah);
        et_koordinat.setText(lat + " , " + lng);
        et_lokasi.setText(lokasi);
        et_lokasi.setEnabled(false);
        et_mobil.setText(merek + " (" + no_plat + ")");
        et_tanggal.setText(tanggal);
        if (Objects.equals(status_list, "angkut")){
            et_status.setText(status_list);
            et_status.setTextColor(Color.GREEN);
            btn_simpan.setVisibility(View.GONE);
        }else {
            et_status.setText("OTW");
            et_status.setTextColor(Color.RED);
        }
        pbGetData.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_detail);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.detail_histori);
        this.setTitle(title);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angkut(api_key, id_histori);
            }
        });
    }

    private void angkut(String api_key, String id_histori){
        OperationalApi operationalApi = ApiClient.getData().create(OperationalApi.class);
        Call<Respon> angkutRespon = operationalApi.angkut(api_key, id_histori);
        angkutRespon.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                if (response.body().isStatus()){
                    Toast.makeText(HistoriDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(HistoriDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
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