package com.example.trashfullmonitor.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trashfullmonitor.BuildConfig;
import com.example.trashfullmonitor.R;
import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.AuthApi;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.model.UserRespon;
import com.example.trashfullmonitor.ui.home.NewHomeActivity;
import com.example.trashfullmonitor.util.session.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AuthApi authApi;
    TextInputEditText etEmail, etPassword;
    Button btnMasuk;

    String Email, Password, api_key = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), NewHomeActivity.class));
            finish();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnMasuk = findViewById(R.id.btnMasuk);

        btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnMasuk) {
            Email = Objects.requireNonNull(etEmail.getText()).toString();
            Password = Objects.requireNonNull(etPassword.getText()).toString();

            if (Email.length() == 0) {
                etEmail.requestFocus();
                etEmail.setError("Kolom email tidak boleh kosong");

            } else if (Password.length() == 0) {
                etPassword.requestFocus();
                etPassword.setError("Kolom password tidak boleh kosong");

            } else if (!isValidEmail(Email)) {
                etEmail.requestFocus();
                etEmail.setError("Email harus valid");

            } else {
                login(Email, Password, api_key);
            }
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void login(String email, String password, String api_key){
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Data sedang diproses....");
        progressDoalog.setTitle("Mohon tunggu sebentar");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();

        authApi = ApiClient.getData().create(AuthApi.class);
        Call<Respon> loginCall = authApi.loginResponse(email, api_key, password);
        loginCall.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                progressDoalog.dismiss();
                if (response.body().isStatus()){

                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    UserRespon data = response.body().getUserRespon();
                    sessionManager.createLoginSesion(data);
                    Toast.makeText(LoginActivity.this, "Selamat datang "+ response.body().getUserRespon().getNAMAPENGGUNA(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, NewHomeActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}