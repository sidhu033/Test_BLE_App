package gamsystech.user.newbleupdated.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.pojo.Log;

import static android.support.v4.content.ContextCompat.getSystemService;

public class AndyUtils
{


    private static final String TAG = AndyUtils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    public static void showSimpleProgressDialog(Context context, String title, String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading...", false);
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //find IMEI no
    public static String findIMEI(Context context)
    {
        final int REQUEST_READ_PHONE_STATE = 10;
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) context,new String[]
                        {
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE
                        },REQUEST_READ_PHONE_STATE
                );
                return telephonyManager.getDeviceId();
            }

            String imei = telephonyManager.getDeviceId();

            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty())
            {
                return imei;
            }
            else
            {
                return android.os.Build.SERIAL;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "not found";
    }

    //show toast message
    public static void showToastMsg(Context context, String msg)
    {
        if (AndyUtils.isObjNotNull(context))
        {
            if (AndyUtils.isStringNotNull(msg)) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //to check is object not null
    public static boolean isObjNotNull(Object object)
    {
        boolean isValide = true;
        try {
            if (object == null) {
                isValide = false;
                Log.e(TAG, "In isObjNull()---1st");
            } else {
                if (object.equals("")) {
                    isValide = false;
                    Log.e(TAG, "In isObjNull()---2nd");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error In isObjNull()---" + e.getMessage());
        }
        Log.e(TAG, "In isObjNull()---3th");
        return isValide;

    }

    //to check is string not null
    public static boolean isStringNotNull(String object)
    {
        boolean isValide = true;
        try
        {
            if (object == null)
            {
                isValide = false;
                android.util.Log.d(TAG, "In isStringNull()---1st");
            } else {
                if (object.trim().equals("")) {
                    isValide = false;
                    Log.e(TAG, "In isStringNull()---2nd");
                }
                if (object.trim().equalsIgnoreCase("null")) {
                    isValide = false;
                    Log.e(TAG, "In isStringNull()---3rd");
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error In isStringNull()---" + e.getMessage());
        }
        Log.e(TAG, "In isStringNull()---4th");
        return isValide;
    }

    //email validation class
    public static boolean isEmail(String emailId)
    {

        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        // Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}"); how to
        // set currency format in string array.xml in android example
        Matcher m = p.matcher(emailId);
        boolean matchFound = m.matches();
        // System.out.println("EMAIL "+matchFound);
        // System.out.println("EMAIL OK");
// System.out.println("EMAIL ERROR");
        return matchFound;
    }

    //to get app version
    public static String getAppVersion(Context context)
    {
        String version = "";
        try
        {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            version = pinfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return version;
    }
    //To check is above kitkat
    public static  boolean iaAboveKitkat()
    {
        int currentapiVersion =Build.VERSION.SDK_INT;
        return currentapiVersion >= Build.VERSION_CODES.KITKAT_WATCH;

    }

    public static Boolean Number_Validate(String number)
    {
        return  !TextUtils.isEmpty(number) && (number.length()==10) && android.util.Patterns.PHONE.matcher(number).matches();
    }

    public static boolean Password_Validate(String password)
    {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static String getcurrentDateTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
//    public static String getcaldatetime()
//    {
//        //getting the current time for joining date
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String joiningDate = sdf.format(cal.getTime());
//        return  joiningDate;
//    }

    /*get current date time format yy-mm-dd*/
    public static String getcurrentDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }



}