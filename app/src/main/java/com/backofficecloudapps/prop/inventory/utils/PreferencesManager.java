package com.backofficecloudapps.prop.inventory.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by "vaibhav upadhyay" on 10-Mar-17.
 */

public class PreferencesManager {

    private static final String PREF_NAME = "sPrefs";
    private static final String KEY_VALUE = "com.example.app.KEY_VALUE";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    public PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setValue(long value) {
        mPref.edit().putLong(KEY_VALUE, value).commit();
    }

    public long getValue() {
        return mPref.getLong(KEY_VALUE, 0);
    }

    public void remove(String key) {
        mPref.edit().remove(key).commit();
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }

    public void setStringValue(String Key , String value) {
        mPref.edit().putString(Key, value).commit();
    }

    public String getStringValue(String Key , String Error) {
        return mPref.getString(Key, ""+Error);
    }

    public void setBooleanValue(String Key , boolean value) {
        mPref.edit().putBoolean(Key, value).commit();
    }

    public boolean getBooleanValue(String Key ) {
        return mPref.getBoolean(Key, false);
    }

    public void setIntValue(String Key , int value) {
        mPref.edit().putInt(Key, value).commit();
    }

    public int getIntValue(String Key ) {
        return mPref.getInt(Key, 0);
    }
}
