package com.example.adamalbarisyi.kamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DictionaryPreference {
    SharedPreferences prefs;
    Context context;

    public DictionaryPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = "App First Run";
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key ="App First Run";
        return prefs.getBoolean(key,true);
    }
}
