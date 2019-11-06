package com.hanakochan.doan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hanakochan.doan.activities.LoginActivity;
import com.hanakochan.doan.activities.MainActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String HOMETOWN = "HOMETOWN";
    public static final String GENDER = "GENDER";
    public static final String PHONE = "PHONE";
    public static final String ID = "ID";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.apply();

    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }
    public void checkLogin(){
        if (!this.isLoggin()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((MainActivity)context).finish();

        }
    }
    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(BIRTHDAY, sharedPreferences.getString(BIRTHDAY, null));
        user.put(HOMETOWN, sharedPreferences.getString(HOMETOWN, null));
        user.put(GENDER, sharedPreferences.getString(GENDER, null));
        user.put(PHONE, sharedPreferences.getString(PHONE, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((MainActivity)context).finish();
    }
}
