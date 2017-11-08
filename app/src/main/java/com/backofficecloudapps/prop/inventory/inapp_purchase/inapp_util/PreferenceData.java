package com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class PreferenceData 
{
    static final String Current_date = "current_date";
    static final String PREF_FontSTYLE = "save_fontstyle";
    static final String PREF_COUNT = "save_count";
    static final String PREF_COUNT1 = "save_count1";
    public static SharedPreferences getSharedPreferences(Context ctx) 
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }





 
    
    
    public static void setCurrentDate(Context ctx, String dSelect) 
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Current_date, "");
        editor.commit();
    }

    @Nullable
    public static String getCurrentDate(Context ctx)
    {
        return getSharedPreferences(ctx).getString(Current_date,"");
    } 
  
}
