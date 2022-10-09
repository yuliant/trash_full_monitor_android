package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

public class DaftarLokasiResponse{

	@SerializedName("BERAT")
	private String bERAT;

	@SerializedName("LOKASI")
	private String lOKASI;

	@SerializedName("ID_TEMPAT_SAMPAH")
	private String iDTEMPATSAMPAH;

	@SerializedName("LONGITUDE")
	private String lONGITUDE;

	@SerializedName("NAMA_TEMPAT_SAMPAH")
	private String nAMATEMPATSAMPAH;

	@SerializedName("STATUS_JEMPUT")
	private String sTATUSJEMPUT;

	@SerializedName("LATITUDE")
	private String lATITUDE;

	public String getBERAT(){
		return bERAT;
	}

	public String getLOKASI(){
		return lOKASI;
	}

	public String getIDTEMPATSAMPAH(){
		return iDTEMPATSAMPAH;
	}

	public String getLONGITUDE(){
		return lONGITUDE;
	}

	public String getNAMATEMPATSAMPAH(){
		return nAMATEMPATSAMPAH;
	}

	public String getSTATUSJEMPUT(){
		return sTATUSJEMPUT;
	}

	public String getLATITUDE(){
		return lATITUDE;
	}
}