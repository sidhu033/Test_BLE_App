package gamsystech.user.newbleupdated.activities.home_activity;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import gamsystech.user.newbleupdated.DatabaseHelper.SharedPreferenceService;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.instruction_activity.InstructionActivity;
import gamsystech.user.newbleupdated.activities.login_activity.LoginActivity;
import gamsystech.user.newbleupdated.activities.readingdemo_activity.ReadingDemo;
import gamsystech.user.newbleupdated.adapters.HomeFragmentPagerAdapter;
import gamsystech.user.newbleupdated.fragments.dashboard_fragment.DashBoardFragment;
import gamsystech.user.newbleupdated.model.TreatmentLogs;
import gamsystech.user.newbleupdated.services.BluetoothLeService;
import gamsystech.user.newbleupdated.services.RedoxerDeviceService;
import gamsystech.user.newbleupdated.session.UserSessionManager;
import gamsystech.user.newbleupdated.utils.AndyUtils;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DashBoardFragment.DashboardFragmentEvents {
    private static final String TAG = "HomeActivity";
    private String RIGHT_DEVICE_ADDRESS;
    private String LEFT_DEVICE_ADDRESS;


    public String INITIAL_BATTERY_LEVEL;

    public SharedPreferenceService mSharedPreferencesService;
    public RedoxerDeviceService mRedoxerDeviceService;
    HomeFragmentPagerAdapter fragmentPagerAdapter;
    DashBoardFragment mDashboardFragament;


    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothLeService mBluetoothLeService;
    private BroadcastReceiver mBatteryLevelReciever;

    ViewPager viewPager;
    LinearLayout homeTabview;
    RelativeLayout summaryTabview;

    private static final int REQUEST_ENABLE_BT = 1;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    UserSessionManager userSessionManager;

    TreatmentLogs treatmentLogs;

    ImageView summary_img;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    public static int position = 0;
    String lower_battery_limit = "3.55";        //minimum threahold battery

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("          REdoxer Treatment");

        // Prohibit screen sleeping while treatment is going on
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        mSharedPreferencesService = new SharedPreferenceService(this);

        //Get device addresses
        LEFT_DEVICE_ADDRESS = mSharedPreferencesService.getPreference(this, "", "LEFTHANDEVICE");
        RIGHT_DEVICE_ADDRESS = mSharedPreferencesService.getPreference(this, "", "RIGHHANDDEVICE");


        //TODO: Uncomment once testing is done
        if (LEFT_DEVICE_ADDRESS.isEmpty() && RIGHT_DEVICE_ADDRESS.isEmpty()) {
            Toast.makeText(this, "Please configure devices", Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomeActivity.this, InstructionActivity.class));
            finish();
            return;
        }

        //bind bluetooth le service to this activity
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);


        // Get device addresses
        initializeView();
        setUpPager();

        //initilize broadacst of battery level reciever
        mBatteryLevelReciever = new BatteryBroadcastReceiver();
    }


    @Override
    protected void onStart() {
        registerReceiver(mBatteryLevelReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }


    @Override
    protected void onStop() {
        unregisterReceiver(mBatteryLevelReciever);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect peripherals.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    private IntentFilter makeGattUpdateIntentFilter() {

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    // Code to manage Service lifecycle.
    //Interface for monitoring the state of an application service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        //if service is available
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(RIGHT_DEVICE_ADDRESS);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            mBluetoothLeService.close();
        }
    };

    //private ProgressDialog mProgressDialog;
    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read or notification operations.

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                AndyUtils.showToastMsg(getBaseContext(), "Gatt server connected successfully");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                AndyUtils.showToastMsg(getBaseContext(), "Gatt disconnected ");


                //start treatment once again
                if(mDashboardFragament !=null)
                {
                    //checkIfDisconnectedToRightDevice(RIGHT_DEVICE_ADDRESS);
                    stopTreatmentWithError("restart your device");
                }

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                AndyUtils.showToastMsg(getBaseContext(), "action discovered  successfully");
                try {

                    mRedoxerDeviceService = new RedoxerDeviceService(mBluetoothLeService, mBluetoothLeService.mBluetoothGatt);

                    Handler handler = new Handler();



                   //*to check battery level of device*//*
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean initialbatterylevel = false;
                            try {
                                initialbatterylevel = mRedoxerDeviceService.requestbatterylevel();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (initialbatterylevel == true)

                            {
                                String Batteryleve = String.valueOf(initialbatterylevel);
                                try {
                                    boolean readbattery = false;
                                        readbattery =      mRedoxerDeviceService.readdevice();

                                    if(readbattery == false)
                                    {
                                        Log.d(TAG,"batterylevel"+readbattery);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    },2000);

                        /*to start the both handed device*/
                      handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                        //  checkIfDisconnectedToRightDevice(RIGHT_DEVICE_ADDRESS);             //forcefully connect to right hand device if disconnected

                            if (mBluetoothLeService.isConnected(RIGHT_DEVICE_ADDRESS)) {


                                boolean devicecallback = false;
                                try {
                                    devicecallback = mRedoxerDeviceService.startdevice();           //start righ hand device
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (devicecallback == true) {
                                    try {
                                        mRedoxerDeviceService.readdevice();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (mBluetoothLeService.isConnected(LEFT_DEVICE_ADDRESS)) {
                                boolean devicecallback = false;
                                try {
                                    devicecallback = mRedoxerDeviceService.startdevice_second();            //start min after 1 min
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (devicecallback == true) {
                                    try {
                                        mRedoxerDeviceService.readdevice();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }, 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                    stopTreatmentWithError("Failed to start device! Please restart device and try again");
                }

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                handleresponse(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

            }
        }
    };

    private void checkIfDisconnectedToRightDevice(String RIGHT_DEVICE_ADDRESS) {

        if (!mBluetoothLeService.isConnected(RIGHT_DEVICE_ADDRESS))
        {
                mBluetoothLeService.connect(RIGHT_DEVICE_ADDRESS);
                AndyUtils.showToastMsg(this,"connect to right device");
        }

    }


    private void handleresponse(String stringarrayextra) {

        treatmentLogs = new TreatmentLogs();
        try {
            String errorMessage = "";
            String R_IB = "", L_IB = "";
            String R_map = "", L_map = "";
            String R_sys = "", L_sys = "";
            String R_dia = "", L_dia = "";
            String R_hr = "", L_hr = "";
            String R_hr_adc = "", L_hr_adc = "";


            if (stringarrayextra != null) {
                if (mDashboardFragament != null)
                {

                    if(stringarrayextra.contains(" Error please restart the your device."))
                    {
                        stopTreatmentWithError("Please restart your device ");
                    }

                    if (stringarrayextra.contains("Bat Lvl")) {



                        R_IB = stringarrayextra.substring(8, 15);
                        mSharedPreferencesService.setPreference(getApplicationContext(), "IBL", R_IB);


                        warninglowbattery(R_IB);            //low battery warning

                        //condition to stop the treatment if battery level is less than 3.55
                        if(R_IB.equals(lower_battery_limit) && R_IB.length() <= lower_battery_limit.length())
                        {
                            stopTreatmentWithError("please charge Redoxer Device ! battery low" + "If You want restart device press restart" + "");
                            AndyUtils.showToastMsg(this, "please charge Redoxer Device ! battery low " +
                                    "If You want restart device press restart");
                        }


                        if (stringarrayextra.contains("Battery low")) {
                            boolean low_battery = false;
                            try {
                                low_battery = mRedoxerDeviceService.requestbatterylevel();
                                if (low_battery == true) {
                                    stopTreatmentWithError("please charge Redoxer Device ! battery low" + "If You want restart device press restart" + "");
                                    AndyUtils.showToastMsg(this, "please charge Redoxer Device ! battery low " +
                                            "If You want restart device press restart");
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (stringarrayextra.contains("MAP")) {
                        R_map = stringarrayextra.substring(4, 8);

                        mSharedPreferencesService.setPreference(getApplicationContext(), "MAP", R_map);

                    } else if (stringarrayextra.contains("Sys") && stringarrayextra.contains("Dia") && stringarrayextra.contains("HR")) {

                        if (mDashboardFragament.currentCycle == mDashboardFragament.RIGHT_HAND_CYCLE_1) {
                            R_sys = stringarrayextra.substring(4, 8);
                            R_dia = stringarrayextra.substring(13, 17);
                            R_hr = stringarrayextra.substring(22, 25);
                            R_hr_adc = stringarrayextra.substring(34, 37);



                            savetreatmentlogs(R_IB, R_map, R_sys, R_dia, R_hr, R_hr_adc);
                            Log.d(TAG, "Treatment Data" + treatmentLogs);
                            mDashboardFragament.removepreferences();
                        }

                        if (mDashboardFragament.currentCycle == mDashboardFragament.LEFT_HAND_CYCLE_1) {

                            L_sys = stringarrayextra.substring(4, 8);
                            L_dia = stringarrayextra.substring(13, 17);
                            L_hr = stringarrayextra.substring(22, 25);
                            L_hr_adc = stringarrayextra.substring(34, 37);



                            savetreatmentlogs(L_IB, L_map, L_sys, L_dia, L_hr, L_hr_adc);
                            Log.d(TAG, "Treatment Data" + treatmentLogs);
                        }
                        if(mDashboardFragament.currentCycle == mDashboardFragament.RIGHT_HAND_CYCLE_2)
                        {
                            R_sys = stringarrayextra.substring(4, 8);
                            R_dia = stringarrayextra.substring(13, 17);
                            R_hr = stringarrayextra.substring(22, 25);
                            R_hr_adc = stringarrayextra.substring(34, 37);
                            savetreatmentlogs(R_IB, R_map, R_sys, R_dia, R_hr, R_hr_adc);

                        }
                        if(mDashboardFragament.currentCycle == mDashboardFragament.LEFT_HAND_CYCLE_2)
                        {
                            L_sys = stringarrayextra.substring(4, 8);
                            L_dia = stringarrayextra.substring(13, 17);
                            L_hr = stringarrayextra.substring(22, 25);
                            L_hr_adc = stringarrayextra.substring(34, 37);
                            savetreatmentlogs(L_IB, L_map, L_sys, L_dia, L_hr, L_hr_adc);
                        }

                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warninglowbattery(String r_IB) {

        if(r_IB.equals("3.61") &&  r_IB.length() <= lower_battery_limit.length()){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog_redoxer_warning);
            // set the custom dialog components - text, image and button

            TextView warning =  dialog.findViewById(R.id.tvWarning);
            Button ok =  dialog.findViewById(R.id.dbOk);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }
    }

    public void savetreatmentlogs(String r_ibl, String r_map, String r_sys, String r_dia, String r_hr, String r_hradc) {


        mDashboardFragament.displayBPReadings(r_ibl, r_sys, r_dia, r_hr);


        mDashboardFragament.startORResumeTreatment();                   //countdown 1 min start


    }

    private void stopTreatmentWithError(String message)
    {
        if (mDashboardFragament != null) {
            mDashboardFragament.stopTimer();
            mDashboardFragament.resetCycleValue();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("RESTART YOUR DEVICE");

        builder.setMessage(message);
        builder.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDashboardFragament != null) {
                    if (mDashboardFragament.currentCycle == mDashboardFragament.RIGHT_HAND_CYCLE_1) {
                        if (mBluetoothLeService.isConnected(RIGHT_DEVICE_ADDRESS)) {
                            try {
                                mRedoxerDeviceService.startdevice();

                            } catch (Exception e) {
                                stopTreatmentWithError("Failed to start right hand device! Please restart device and try again");
                                e.printStackTrace();
                            }

                        } else
                            {
                            mBluetoothLeService.connect(RIGHT_DEVICE_ADDRESS);
                        }
                    } else if (mDashboardFragament.currentCycle == mDashboardFragament.LEFT_HAND_CYCLE_1) {
                        if (mBluetoothLeService.isConnected(LEFT_DEVICE_ADDRESS)) {
                            try {
                                mRedoxerDeviceService.startdevice();
                            } catch (Exception e) {
                                stopTreatmentWithError("Failed to start LEFT hand device! Please restart device and try again");
                                e.printStackTrace();
                            }
                        } else {
                            mBluetoothLeService.connect(LEFT_DEVICE_ADDRESS);
                        }
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        }).show();

    }

    private void initializeView() {
        viewPager = (ViewPager) findViewById(R.id.dashboard_viewpager);
        homeTabview = (LinearLayout) findViewById(R.id.home_tabview);
        summaryTabview = (RelativeLayout) findViewById(R.id.summary_tabview);
        summary_img = findViewById(R.id.summary_img);

        /*initlize session object*/
        userSessionManager = new UserSessionManager(this);

        summary_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, ReadingDemo.class));

            }
        });


    }

    private void setUpPager() {

        fragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), HomeActivity.this);

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.getAdapter().notifyDataSetChanged();
        fragmentPagerAdapter.notifyDataSetChanged();


        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mDashboardFragament = (DashBoardFragment) fragmentPagerAdapter.dashBoardFragment;
        homeTabview.setOnClickListener(this);
        summaryTabview.setOnClickListener(this);


    }

    @Override
    public void onCycleOneDeviceOneFinished() {

        mBluetoothLeService.connect(LEFT_DEVICE_ADDRESS);
        mBluetoothLeService.mBluetoothGatt.getDevice();

      //  AndyUtils.showToastMsg(HomeActivity.this, "Connected to Left Hand device ");

    }

    @Override
    public void onCycleTwoDeviceTwoFinshed() {
        mBluetoothLeService.connect(RIGHT_DEVICE_ADDRESS);

      //  AndyUtils.showToastMsg(HomeActivity.this, "Connected to Right Hand device ");

    }

    //broadcast reciver for to check battery level of device on recieve
    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        private final static String BATTERY_LEVEL = "level";

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BATTERY_LEVEL, 0);
            if (level <= 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Battery Low! Please connect charger to your mobile...")

                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                /*redirect to login screen*/

                userSessionManager.logoutuser();
                Toast.makeText(getApplicationContext(), "User Login Status: " + userSessionManager.isUserLoggedIn(), Toast.LENGTH_LONG).show();
                userSessionManager.checkLogin();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.this.finish();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                HashMap<String, String> user = userSessionManager.getUserDetails();
                String name = user.get(UserSessionManager.KEY_NAME);
                String pass = user.get(UserSessionManager.KEY_PASS);

                Log.d(TAG, "" + name);
                Log.d(TAG, "" + pass);
                dialog.cancel();
                HomeActivity.super.onBackPressed();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tabview:
                viewPager.setCurrentItem(0, true);
                break;

            case R.id.summary_tabview:
                viewPager.setCurrentItem(1, true);
                break;


        }
    }


}
