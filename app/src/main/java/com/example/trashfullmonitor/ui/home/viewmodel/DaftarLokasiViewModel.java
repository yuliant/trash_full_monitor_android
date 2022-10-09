package com.example.trashfullmonitor.ui.home.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trashfullmonitor.api.ApiClient;
import com.example.trashfullmonitor.api.OperationalApi;
import com.example.trashfullmonitor.model.DaftarLokasiResponse;
import com.example.trashfullmonitor.model.Respon;
import com.example.trashfullmonitor.ui.login.LoginActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLokasiViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DaftarLokasiResponse>> liveData = new MutableLiveData<>();


    public void setListDaftarLokasi(final Context context, final String api_key) {
        OperationalApi operationalApi = ApiClient.getData().create(OperationalApi.class);
        Call<Respon> daftarLokasiResponse = operationalApi.daftarLokasiResponse(api_key);

        daftarLokasiResponse.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                try {
                    if (response.body().isStatus()){

                        liveData.setValue(response.body().getDaftarLokasiResponseList());

                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<DaftarLokasiResponse>> getDaftarLokasi() {
        return liveData;
    }
}
