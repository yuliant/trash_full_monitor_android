package com.example.trashfullmonitor.api;

import com.example.trashfullmonitor.model.Respon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OperationalApi {

    @FormUrlEncoded
    @POST("trash/mobile/tempat_sampah")
    Call<Respon> daftarLokasiResponse(
            @Field("API-KEY") String api_key
    );

    @FormUrlEncoded
    @POST("trash/mobile/mobil_sampah")
    Call<Respon> mobilResponse(
            @Field("API-KEY") String api_key
    );

    @FormUrlEncoded
    @POST("trash/mobile/histori_tugas")
    Call<Respon> historiTugas(
            @Field("API-KEY") String api_key,
            @Field("id_pengguna") String id_pengguna
    );
}
