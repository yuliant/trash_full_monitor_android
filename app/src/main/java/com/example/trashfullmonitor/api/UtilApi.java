package com.example.trashfullmonitor.api;

import com.example.trashfullmonitor.model.Respon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UtilApi {

    @FormUrlEncoded
    @POST("trash/mobile/mobil_sampah_ready")
    Call<Respon> mobilReadyResponse(
            @Field("API-KEY") String api_key
    );
}
