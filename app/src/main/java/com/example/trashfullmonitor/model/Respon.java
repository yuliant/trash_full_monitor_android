package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

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

    public void setUserRespon(UserRespon userRespon) {
        this.userRespon = userRespon;
    }

    public UserRespon getUserRespon() {
        return userRespon;
    }

}
