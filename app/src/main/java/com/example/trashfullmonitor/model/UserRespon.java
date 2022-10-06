package com.example.trashfullmonitor.model;

import com.google.gson.annotations.SerializedName;

public class UserRespon{

	@SerializedName("EMAIL_PENGGUNA")
	private String eMAILPENGGUNA;

	@SerializedName("JABATAN_PENGGUNA")
	private String jABATANPENGGUNA;

	@SerializedName("NAMA_PENGGUNA")
	private String nAMAPENGGUNA;

	@SerializedName("ID_PENGGUNA")
	private String iDPENGGUNA;

	@SerializedName("NOHP_PENGGUNA")
	private String nOHPPENGGUNA;

	@SerializedName("PASSWORD_PENGGUNA")
	private String pASSWORDPENGGUNA;

	public void setEMAILPENGGUNA(String eMAILPENGGUNA){
		this.eMAILPENGGUNA = eMAILPENGGUNA;
	}

	public String getEMAILPENGGUNA(){
		return eMAILPENGGUNA;
	}

	public void setJABATANPENGGUNA(String jABATANPENGGUNA){
		this.jABATANPENGGUNA = jABATANPENGGUNA;
	}

	public String getJABATANPENGGUNA(){
		return jABATANPENGGUNA;
	}

	public void setNAMAPENGGUNA(String nAMAPENGGUNA){
		this.nAMAPENGGUNA = nAMAPENGGUNA;
	}

	public String getNAMAPENGGUNA(){
		return nAMAPENGGUNA;
	}

	public void setIDPENGGUNA(String iDPENGGUNA){
		this.iDPENGGUNA = iDPENGGUNA;
	}

	public String getIDPENGGUNA(){
		return iDPENGGUNA;
	}

	public void setNOHPPENGGUNA(String nOHPPENGGUNA){
		this.nOHPPENGGUNA = nOHPPENGGUNA;
	}

	public String getNOHPPENGGUNA(){
		return nOHPPENGGUNA;
	}

	public void setPASSWORDPENGGUNA(String pASSWORDPENGGUNA){
		this.pASSWORDPENGGUNA = pASSWORDPENGGUNA;
	}

	public String getPASSWORDPENGGUNA(){
		return pASSWORDPENGGUNA;
	}
}