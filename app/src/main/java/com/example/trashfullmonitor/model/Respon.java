package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Respon {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @SerializedName("data")
    private UserRespon userRespon;

    public UserRespon getUserRespon() {
        return userRespon;
    }

    @SerializedName("daftar_lokasi")
    private ArrayList<DaftarLokasiResponse> daftarLokasiResponseList;

    public ArrayList<DaftarLokasiResponse> getDaftarLokasiResponseList() {
        return daftarLokasiResponseList;
    }
}
