package com.example.trashfullmonitor.ui.mobil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

public class DetailMobilActivity extends AppCompatActivity {
    SessionManager sessionManager;
    EditText et_merek, et_no_plat, et_status, et_lokasi;
    String idMobilSampah, merek, noPlat, status, lokasi;

    private void init() {
        idMobilSampah = getIntent().getStringExtra("ID_MOBIL_SAMPAH");
        merek = getIntent().getStringExtra("MEREK");
        noPlat = getIntent().getStringExtra("NO_PLAT");
        status = getIntent().getStringExtra("STATUS");
        lokasi = getIntent().getStringExtra("LOKASI");

        et_merek = findViewById(R.id.et_merek);
        et_no_plat = findViewById(R.id.et_no_plat);
        et_status = findViewById(R.id.et_status);
        et_lokasi = findViewById(R.id.et_lokasi);

        if (status.equals("ready")){
            et_status.setTextColor(Color.GREEN);
        }else {
            et_status.setTextColor(Color.RED);
        }
        et_merek.setText(merek);
        et_no_plat.setText(noPlat);
        et_status.setText(status);
        et_lokasi.setText(lokasi);
        et_lokasi.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.detail_mobil_sampah);
        this.setTitle(title);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();
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