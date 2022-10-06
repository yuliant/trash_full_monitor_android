package com.example.trashfullmonitor.api;

import com.example.trashfullmonitor.model.Respon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @FormUrlEncoded
    @POST("login")
    Call<Respon> loginResponse(
            @Field("email") String email,
            @Field("API-KEY") String api_key,
            @Field("password") String password
    );
}
