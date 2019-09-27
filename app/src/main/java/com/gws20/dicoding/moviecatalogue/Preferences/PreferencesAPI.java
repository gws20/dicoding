package com.gws20.dicoding.moviecatalogue.Preferences;

import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;

import java.util.HashMap;

public class PreferencesAPI {

    public static final HashMap<String, Object> DEFAULT_VALUE = getDefaultHashMap();

    private static HashMap<String, Object> getDefaultHashMap() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        hashMap.put(GWS20.getInstance().getString(R.string.key_daily), true);
        hashMap.put(GWS20.getInstance().getString(R.string.key_release), true);
        return hashMap;
    }

    public static HashMap<String, Integer> prefId (){
        HashMap<String, Integer> pref = new HashMap<>();

        pref.put(GWS20.getInstance().getString(R.string.key_daily),1);
        pref.put(GWS20.getInstance().getString(R.string.key_release),2);

        return pref;
    }
}
