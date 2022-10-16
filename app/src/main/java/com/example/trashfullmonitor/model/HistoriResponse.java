package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

public class HistoriResponse{

	@SerializedName("BERAT")
	private String bERAT;

	@SerializedName("LOKASI")
	private String lOKASI;

	@SerializedName("LOKASI_MOBIL_SAMPAH")
	private String lOKASIMOBILSAMPAH;

	@SerializedName("LONGITUDE")
	private String lONGITUDE;

	@SerializedName("ID_MOBIL_SAMPAH")
	private String iDMOBILSAMPAH;

	@SerializedName("TANGGAL")
	private String tANGGAL;

	@SerializedName("STATUS")
	private String sTATUS;

	@SerializedName("ID_TEMPAT_SAMPAH")
	private String iDTEMPATSAMPAH;

	@SerializedName("ID_LIST_TUGAS")
	private String iDLISTTUGAS;

	@SerializedName("NO_PLAT")
	private String nOPLAT;

	@SerializedName("NAMA_TEMPAT_SAMPAH")
	private String nAMATEMPATSAMPAH;

	@SerializedName("STATUS_JEMPUT")
	private String sTATUSJEMPUT;

	@SerializedName("ID_PENGGUNA")
	private String iDPENGGUNA;

	@SerializedName("MEREK")
	private String mEREK;

	@SerializedName("LATITUDE")
	private String lATITUDE;

	@SerializedName("STATUS_LIST")
	private String sTATUSLIST;

	public String getBERAT(){
		return bERAT;
	}

	public String getLOKASI(){
		return lOKASI;
	}

	public String getLOKASIMOBILSAMPAH(){
		return lOKASIMOBILSAMPAH;
	}

	public String getLONGITUDE(){
		return lONGITUDE;
	}

	public String getIDMOBILSAMPAH(){
		return iDMOBILSAMPAH;
	}

	public String getTANGGAL(){
		return tANGGAL;
	}

	public String getSTATUS(){
		return sTATUS;
	}

	public String getIDTEMPATSAMPAH(){
		return iDTEMPATSAMPAH;
	}

	public String getIDLISTTUGAS(){
		return iDLISTTUGAS;
	}

	public String getNOPLAT(){
		return nOPLAT;
	}

	public String getNAMATEMPATSAMPAH(){
		return nAMATEMPATSAMPAH;
	}

	public String getSTATUSJEMPUT(){
		return sTATUSJEMPUT;
	}

	public String getIDPENGGUNA(){
		return iDPENGGUNA;
	}

	public String getMEREK(){
		return mEREK;
	}

	public String getLATITUDE(){
		return lATITUDE;
	}

	public String getSTATUSLIST(){
		return sTATUSLIST;
	}
}