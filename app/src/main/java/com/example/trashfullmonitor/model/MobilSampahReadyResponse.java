package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

public class MobilSampahReadyResponse{

	@SerializedName("STATUS")
	private String sTATUS;

	@SerializedName("LOKASI")
	private String lOKASI;

	@SerializedName("NO_PLAT")
	private String nOPLAT;

	@SerializedName("ID_MOBIL_SAMPAH")
	private String iDMOBILSAMPAH;

	@SerializedName("MEREK")
	private String mEREK;

	public String getSTATUS(){
		return sTATUS;
	}

	public String getLOKASI(){
		return lOKASI;
	}

	public String getNOPLAT(){
		return nOPLAT;
	}

	public String getIDMOBILSAMPAH(){
		return iDMOBILSAMPAH;
	}

	public String getMEREK(){
		return mEREK;
	}
}