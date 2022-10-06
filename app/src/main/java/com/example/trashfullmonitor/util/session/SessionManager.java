package com.example.trashfullmonitor.util.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.trashfullmonitor.model.UserRespon;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_PENGGUNA = "ID_PENGGUNA";
    public static final String NAMA_PENGGUNA = "NAMA_PENGGUNA";
    public static final String EMAIL_PENGGUNA = "EMAIL_PENGGUNA";
    public static final String NOHP_PENGGUNA = "NOHP_PENGGUNA";
    public static final String JABATAN_PENGGUNA = "JABATAN_PENGGUNA";

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSesion(UserRespon user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(ID_PENGGUNA, user.getIDPENGGUNA());
        editor.putString(NAMA_PENGGUNA, user.getNAMAPENGGUNA());
        editor.putString(EMAIL_PENGGUNA, user.getEMAILPENGGUNA());
        editor.putString(NOHP_PENGGUNA, user.getNOHPPENGGUNA());
        editor.putString(JABATAN_PENGGUNA, user.getJABATANPENGGUNA());
        editor.commit();
    }

    public void updateProfil(UserRespon user){
        editor.remove(SessionManager.NAMA_PENGGUNA);
        editor.remove(SessionManager.NOHP_PENGGUNA);
        editor.remove(SessionManager.JABATAN_PENGGUNA);
        editor.apply();

        editor.putString(NAMA_PENGGUNA, user.getNAMAPENGGUNA());
        editor.putString(NOHP_PENGGUNA, user.getNOHPPENGGUNA());
        editor.putString(JABATAN_PENGGUNA, user.getJABATANPENGGUNA());
        editor.commit();
    }

    //simpan sesinya dengan method dibawah ini
    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID_PENGGUNA, sharedPreferences.getString(ID_PENGGUNA,null));
        user.put(NAMA_PENGGUNA, sharedPreferences.getString(NAMA_PENGGUNA,null));
        user.put(EMAIL_PENGGUNA, sharedPreferences.getString(EMAIL_PENGGUNA,null));
        user.put(NOHP_PENGGUNA, sharedPreferences.getString(NOHP_PENGGUNA,null));
        user.put(JABATAN_PENGGUNA, sharedPreferences.getString(JABATAN_PENGGUNA,null));
        return user;
    }

    public void logoutSession(){
        editor.remove(SessionManager.IS_LOGGED_IN);
        editor.remove(SessionManager.ID_PENGGUNA);
        editor.remove(SessionManager.NAMA_PENGGUNA);
        editor.remove(SessionManager.EMAIL_PENGGUNA);
        editor.remove(SessionManager.NOHP_PENGGUNA);
        editor.remove(SessionManager.JABATAN_PENGGUNA);
        editor.apply();
    }

    //memberikan kondisi dia sudah login
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
