package gamsystech.user.newbleupdated.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import gamsystech.user.newbleupdated.utils.AppUtil;


public class NetworkReceiver extends BroadcastReceiver
{

    private static final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {

        if (AppUtil.isNetworkAvailable(context))
        {
          //TODO:
        } else
            {
            Log.d(TAG, "Internet is not available");
        }
    }
}
