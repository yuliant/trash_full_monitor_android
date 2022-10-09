package com.example.trashfullmonitor.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.ui.daftarlokasi.DaftarLokasiActivity;
import com.example.trashfullmonitor.ui.histori.HistoriActivity;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.ui.mobil.MobilActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

public class NewHomeActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager;
    String id, nama, email, api_key = BuildConfig.API_KEY;
    TextView tvNama, tvEmail;
    LinearLayout ll_daftar_lokasi;

    private void init(){
        id = sessionManager.getUserDetail().get(SessionManager.ID_PENGGUNA);
        nama = sessionManager.getUserDetail().get(SessionManager.NAMA_PENGGUNA);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL_PENGGUNA);

        tvNama = findViewById(R.id.tv_nama);
        tvEmail = findViewById(R.id.tv_email);
        ll_daftar_lokasi = findViewById(R.id.ll_daftar_lokasi);

        tvNama.setText(nama);
        tvEmail.setText(email);

        ll_daftar_lokasi.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_daftar_lokasi:
                Intent daftarlokasi = new Intent(this, DaftarLokasiActivity.class);
                startActivity(daftarlokasi);
                break;

            case R.id.ll_histori:
                Intent histori = new Intent(this, HistoriActivity.class);
                startActivity(histori);
                break;

            case R.id.ll_mobil:
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