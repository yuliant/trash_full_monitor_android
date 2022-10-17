package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

public class HomeResponse{

	@SerializedName("daftar_lokasi")
	private String daftarLokasi;

	@SerializedName("mobil")
	private String mobil;

	@SerializedName("histori")
	private String histori;

	public String getDaftarLokasi(){
		return daftarLokasi;
	}

	public String getMobil(){
		return mobil;
	}

	public String getHistori(){
		return histori;
	}
}