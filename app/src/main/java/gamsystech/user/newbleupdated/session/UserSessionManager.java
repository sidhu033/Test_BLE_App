package gamsystech.user.newbleupdated.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import gamsystech.user.newbleupdated.activities.login_activity.LoginActivity;

public class UserSessionManager
{
    // Shared Preferences reference
    SharedPreferences pref;
    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREFER_NAME = "LoginSharedPreference";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "pass";

    //constructor for user session manager
    public UserSessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //Create login session
    public void createUserLoginSession(String name, String pass)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in pref

        // Storing email in pref
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_PASS, pass);
        // commit changes
        editor.commit();
    }
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     ***/
    public boolean checkLogin()
    {
        // Check login status
        if(!this.isUserLoggedIn())
        {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
            return true;
        }
        return  false;
    }


    // Check for login
    public boolean isUserLoggedIn()
    {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails()
    {
        //user hashmap to store user creadential
        HashMap<String ,String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));
        user.put(KEY_PASS, pref.getString(KEY_PASS,null));

        // return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutuser()
    {
        //clearning all data from shared preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context,LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }



    /*user session*/

    public void setLoggedin(boolean loggedin)
    {
        editor.putBoolean("loggedInmode",loggedin);
        editor.commit();
    }

    public boolean loggedin()
    {
        return pref.getBoolean("loggeInmode",false);
    }


}
