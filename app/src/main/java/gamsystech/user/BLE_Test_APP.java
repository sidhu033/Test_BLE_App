package gamsystech.user;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import gamsystech.user.newbleupdated.DatabaseHelper.localstorage.SharedPreferenceManager;
import gamsystech.user.newbleupdated.retrofit.APIHelper;

public class BLE_Test_APP extends Application
{

    /*initlize when first application launch with the one instance for whole applicaiton*/
    @Override
    public void onCreate()
    {
        super.onCreate();

        Hawk.init(this).build();
        APIHelper.init();

    }
}
