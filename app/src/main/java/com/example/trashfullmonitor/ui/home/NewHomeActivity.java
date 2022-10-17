package com.example.trashfullmonitor.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.daftarlokasi.DaftarLokasiActivity;
import com.example.trashfullmonitor.ui.daftarlokasi.DaftarLokasiAdapter;
import com.example.trashfullmonitor.ui.histori.HistoriActivity;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.ui.mobil.MobilActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHomeActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager;
    String id, nama, email, api_key = BuildConfig.API_KEY;
    TextView tvNama, tvEmail;
    CardView cv_daftar_lokasi, cv_histori, cv_mobil;
    ProgressBar pbGetData;
    SwipeRefreshLayout swpGetData;

    private void init(){
        id = sessionManager.getUserDetail().get(SessionManager.ID_PENGGUNA);
        nama = sessionManager.getUserDetail().get(SessionManager.NAMA_PENGGUNA);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL_PENGGUNA);

        tvNama = findViewById(R.id.tv_nama);
        tvEmail = findViewById(R.id.tv_email);
        cv_mobil = findViewById(R.id.cv_mobil);
        cv_daftar_lokasi = findViewById(R.id.cv_daftar_lokasi);
        cv_histori = findViewById(R.id.cv_histori);
        pbGetData = findViewById(R.id.pbGetData);
        swpGetData = findViewById(R.id.swpGetData);

        tvNama.setText(nama);
        tvEmail.setText(email);

        cv_daftar_lokasi.setOnClickListener(this);
        cv_histori.setOnClickListener(this);
        cv_mobil.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        init();
        swpGetData.setOnRefreshListener(() -> {
            swpGetData.setRefreshing(true);
            getData(api_key, id);
            swpGetData.setRefreshing(false);
        });

        getData(api_key, id);
    }

    private void getData(String api_key, String id){
        pbGetData.setVisibility(View.VISIBLE);
        OperationalApi operationalApi = ApiClient.getData().create(OperationalApi.class);
        Call<Respon> responCall = operationalApi.home(api_key, id);
        responCall.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(@NonNull Call<Respon> call, @NonNull Response<Respon> response) {
                pbGetData.setVisibility(View.INVISIBLE);
                assert response.body() != null;
                if (response.body().isStatus()){

                    TextView tv_daftar_lokasi_jumlah = findViewById(R.id.tv_daftar_lokasi_jumlah);
                    TextView tv_jumlah_histori = findViewById(R.id.tv_jumlah_histori);
                    TextView tv_jumlah_mobil = findViewById(R.id.tv_jumlah_mobil);

                    tv_daftar_lokasi_jumlah.setText(response.body().getHomeData().getDaftarLokasi());
                    tv_jumlah_histori.setText(response.body().getHomeData().getHistori());
                    tv_jumlah_mobil.setText(response.body().getHomeData().getMobil());

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_daftar_lokasi:
                Intent daftarlokasi = new Intent(this, DaftarLokasiActivity.class);
                startActivity(daftarlokasi);
                break;

            case R.id.cv_histori:
                Intent histori = new Intent(this, HistoriActivity.class);
                startActivity(histori);
                break;

            case R.id.cv_mobil:
                Intent mobil = new Intent(this, MobilActivity.class);
                startActivity(mobil);
                break;
        }
    }

    private void moveToLogin() {
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                AlertDialog.Builder alertUbah = new AlertDialog.Builder(this);
                alertUbah.setTitle("Apakah kamu yakin ingin keluar dari aplikasi ini?");
                alertUbah
                        .setMessage("Klik Ya untuk keluar!")
                        .setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sessionManager.logoutSession();
                                moveToLogin();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alertUbah.show();
                return true;
            default:
                return true;
        }
    }
}