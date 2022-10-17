package com.example.trashfullmonitor.ui.daftarlokasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.api.UtilApi;
import com.example.trashfullmonitor.model.MobilSampahReadyResponse;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.login.LoginActivity;
import com.example.trashfullmonitor.util.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLokasiDetailActivity extends AppCompatActivity {
    
    SessionManager sessionManager;
    List<MobilSampahReadyResponse> mobilSampahReadyResponses = new ArrayList<>();
    ProgressBar pbGetData;

    String api_key = BuildConfig.API_KEY, id_tempat_sampah, id_mobil="", id_pengguna;

    @SuppressLint("SetTextI18n")
    private void init() {
        id_pengguna = sessionManager.getUserDetail().get(SessionManager.ID_PENGGUNA);
        id_tempat_sampah = getIntent().getStringExtra("ID_TEMPAT_SAMPAH");

        String nama_tempat_sampah = getIntent().getStringExtra("NAMA_TEMPAT_SAMPAH");
        String lng = getIntent().getStringExtra("LNG");
        String lat = getIntent().getStringExtra("LAT");
        String lokasi = getIntent().getStringExtra("LOKASI");
        String berat = getIntent().getStringExtra("BERAT");
        String status_jemput = getIntent().getStringExtra("STATUS_JEMPUT");

        pbGetData = findViewById(R.id.pbGetData);
        EditText et_nama_tempat_sampah = findViewById(R.id.et_nama_tempat_sampah)
                , et_koordinat = findViewById(R.id.et_koordinat)
                , et_lokasi = findViewById(R.id.et_lokasi)
                , et_berat = findViewById(R.id.et_berat);
        et_nama_tempat_sampah.setText(nama_tempat_sampah);
        et_koordinat.setText(lat + " , " + lng);
        et_lokasi.setText(lokasi);
        et_lokasi.setEnabled(false);
        et_berat.setText(berat);

        if (Objects.equals(status_jemput, "1")){
            LinearLayout ll_hidden = findViewById(R.id.ll_hidden);
            ll_hidden.setVisibility(View.GONE);
        }

        if (Integer.parseInt(berat) < 80){
            LinearLayout ll_hidden = findViewById(R.id.ll_hidden);
            ll_hidden.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_lokasi_detail);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getResources().getString(R.string.detail_lokasi);
        this.setTitle(title);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        init();

        //event search select in android
        ArrayList<String> arrayList =new ArrayList<>();
        TextView textview=findViewById(R.id.tv_mobil);
        getDataMobil(api_key, arrayList);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(DaftarLokasiDetailActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(650,800);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter=new ArrayAdapter<>(DaftarLokasiDetailActivity.this, android.R.layout.simple_list_item_1,arrayList);

                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        adapter.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        textview.setText(adapter.getItem(i));
                        id_mobil = mobilSampahReadyResponses.get(i).getIDMOBILSAMPAH();
                        dialog.dismiss();
                    }
                });

            }
        });

        Button btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_mobil.equals("")){
                    Toast.makeText(DaftarLokasiDetailActivity.this, "Kolom mobil belum diisi", Toast.LENGTH_SHORT).show();
                    textview.setError("Kolom tidak boleh kosong");
                }else {
                    buatTugas(api_key, id_tempat_sampah, id_mobil, id_pengguna);
                }
            }
        });
    }

    private void buatTugas(
            String api_key, String id_tempat_sampah,
            String id_mobil_sampah, String id_pengguna){

        OperationalApi operationalApi = ApiClient.getData().create(OperationalApi.class);
        Call<Respon> responCall = operationalApi.menujuLokasi(api_key,
                id_tempat_sampah, id_mobil_sampah, id_pengguna);
        responCall.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(@NonNull Call<Respon> call, @NonNull Response<Respon> response) {
                if (response.body().isStatus()){
                    Toast.makeText(DaftarLokasiDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DaftarLokasiDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Respon> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDataMobil(String api_key, ArrayList<String> arrayList){
        pbGetData.setVisibility(View.VISIBLE);
        UtilApi utilApi = ApiClient.getData().create(UtilApi.class);
        Call<Respon> mobilRespon = utilApi.mobilReadyResponse(api_key);
        mobilRespon.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(@NonNull Call<Respon> call, @NonNull Response<Respon> response) {
                pbGetData.setVisibility(View.INVISIBLE);
                assert response.body() != null;
                if (response.body().isStatus()){
                    mobilSampahReadyResponses = response.body().getMobilSampahReadyResponses();
                    for (int i = 0; i < mobilSampahReadyResponses.size(); i++){
                        arrayList.add(mobilSampahReadyResponses.get(i).getMEREK() + " (" +mobilSampahReadyResponses.get(i).getNOPLAT() + ")");
                    }
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