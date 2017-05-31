package com.encreddesign.grocery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Joshua on 31/05/2017.
 */

public class GroceryPreferences {

    private final SharedPreferences mPrefs;

    public GroceryPreferences (Activity activity) {
        this.mPrefs = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void putString (String key, String value) {
        this.mPrefs.edit().putString(key, value).apply();
    }

    public void putInt (String key, int value) {
        this.mPrefs.edit().putInt(key, value).apply();
    }

    public void putBoolean (String key, boolean value) {
        this.mPrefs.edit().putBoolean(key, value).apply();
    }

    public String getString (String key) {
        return this.mPrefs.getString(key, null);
    }

    public int getInt (String key) {
        return this.mPrefs.getInt(key, 0);
    }

    public boolean getBool (String key) {
        return this.mPrefs.getBoolean(key, false);
    }

    public void remove (String key) {
        this.mPrefs.edit().remove(key).apply();
    }

}
