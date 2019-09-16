package gamsystech.user.newbleupdated.DatabaseHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

public class SharedPreferenceService
{
    private static final String PREFS_NAME = "preferenceName";
    Context context;
    public SharedPreferenceService(Context context)
    {
        this.context = context;
    }

    public  boolean setPreference(Context context, String key, String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public  String getPreference(Context context, String righhanddevice, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }



    /*clear preferences*/

    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();

    }
    public void removePreference(Context context, String prefsName, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
