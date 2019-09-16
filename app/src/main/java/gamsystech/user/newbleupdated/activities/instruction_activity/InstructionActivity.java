package gamsystech.user.newbleupdated.activities.instruction_activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gamsystech.user.newbleupdated.DatabaseHelper.SharedPreferenceService;
import gamsystech.user.newbleupdated.R;
import gamsystech.user.newbleupdated.activities.home_activity.HomeActivity;
import gamsystech.user.newbleupdated.fragments.dashboard_fragment.DashBoardFragment;


public class InstructionActivity extends AppCompatActivity implements View.OnClickListener
{
    private LeDeviceListAdapter mLeDeviceListAdapter;

    Button starttretment;
    private boolean mScanning;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;

    //list bluetooth device, bounded devices

    private Spinner spn_left_hand_device_spinner,spn_right_hand_device_spinner;


   private TextView tv_left_hand_device_selected,tv_right_hand_device_selected;

    public String leftHandDeviceAddress;
    public String rightHandDeviceAddress;

    public String rhdn;
    public String lhdn;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    SharedPreferenceService mSharedPreferencesService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);


        Intilization();
    }
    //on create closed

    //initilization

    private void Intilization()
    {
        spn_left_hand_device_spinner = findViewById(R.id.spn_left_hand_device_spinner);
        spn_right_hand_device_spinner = findViewById(R.id.spn_right_hand_device_spinner);
        tv_left_hand_device_selected = findViewById(R.id.tv_left_hand_device_selected);
        tv_right_hand_device_selected = findViewById(R.id.tv_right_hand_device_selected);
        starttretment = findViewById(R.id.starttretment);
        getSupportActionBar().setTitle("DEVICE SETUP");

        mHandler = new Handler();
        // Use this check to determine whether BLE is supported on the device.  Then you can

        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        mSharedPreferencesService = new SharedPreferenceService(this);


        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter =  bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if(mBluetoothAdapter == null)
        {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        starttretment.setOnClickListener(this);
    }




    @Override
    protected void onResume()
    {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
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


        // Initializes list view adapter
        mLeDeviceListAdapter = new LeDeviceListAdapter();

        spn_left_hand_device_spinner.setAdapter(mLeDeviceListAdapter);
        spn_right_hand_device_spinner.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);

        tv_left_hand_device_selected.setText(leftHandDeviceAddress);
        tv_right_hand_device_selected.setText(rightHandDeviceAddress);

    }


    @Override
    protected void onPause()
    {
        super.onPause();
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }


    private void scanLeDevice(final boolean enable)
    {
        if(enable)
        {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable()
            {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run()
                {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            },SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        }
        else
        {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        if(!mScanning)
        {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        }
        else
        {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbarprogress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //on click listner




    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.starttretment);
        {
            try
            {
                leftHandDeviceAddress = ((BluetoothDevice) spn_left_hand_device_spinner.getSelectedItem()).getAddress();
                rightHandDeviceAddress = ((BluetoothDevice) spn_right_hand_device_spinner.getSelectedItem()).getAddress();

                 rhdn = ((BluetoothDevice) spn_right_hand_device_spinner.getSelectedItem()).getName();
                 lhdn = ((BluetoothDevice) spn_left_hand_device_spinner.getSelectedItem()).getName();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //left and right device if not empty and null
            if(leftHandDeviceAddress != null && !leftHandDeviceAddress.isEmpty() && rightHandDeviceAddress !=null && !rightHandDeviceAddress.isEmpty())
            {
                mSharedPreferencesService.setPreference(this,"LEFTHANDEVICE",leftHandDeviceAddress );
                mSharedPreferencesService.setPreference(this,"RIGHHANDDEVICE",rightHandDeviceAddress );


                /*get device name*/
                mSharedPreferencesService.setPreference(this,"LHDN",lhdn);
                mSharedPreferencesService.setPreference(this,"RHDN",rhdn);



                //same device selection not allowed
                if(!leftHandDeviceAddress.equals(rightHandDeviceAddress))
                {
                    Intent intent= new Intent(InstructionActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, "Same device selected for both hands",Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(this, "Please select devices for both the hands", Toast.LENGTH_LONG).show();
            }
        }
    }
    //closed on click listner

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter
    {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        //constructor for le device scan intilization
        public LeDeviceListAdapter()
        {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = InstructionActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device)
        {
            if(!mLeDevices.contains(device))
            {
                mLeDevices.add(device);
            }
        }


        public BluetoothDevice getDevice(int position)
        {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount()
        {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i)
        {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            ViewHolder viewHolder;          //instead of findview by id used for fast rendering

            // General ListView optimization code.
            if(view == null)
            {
                view = mInflator.inflate(R.layout.listitem_device,null);
                viewHolder = new ViewHolder();          //object created for view holder
                viewHolder.deviceAddress = view.findViewById(R.id.device_address);
                viewHolder.deviceName = view.findViewById(R.id.device_name);
                view.setTag(viewHolder);            //Tags can also be used to store data within a view without resorting to another data structure.
            }
            else
            {
                viewHolder = (ViewHolder) view.getTag();
            }
            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            final String deviceAddress = device.getAddress();

            /*pass device name to dashboard fragment*/
            Bundle bundle = new Bundle();
            bundle.putString("devicename",deviceName);
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            dashBoardFragment.setArguments(bundle);


            if(deviceName !=null && deviceName.length() >0)
            {
                viewHolder.deviceName.setText(deviceName);
                viewHolder.deviceAddress.setText(deviceAddress);
            }
            else
            {
                viewHolder.deviceName.setText("Unknown device");
                viewHolder.deviceAddress.setText("unknown device address");
            }

            return view;
        }
    }
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    static class ViewHolder
    {
        TextView deviceName;
        TextView deviceAddress;
    }
}
