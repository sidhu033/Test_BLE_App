package gamsystech.user.newbleupdated.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import gamsystech.user.newbleupdated.utils.GattAttributes;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service
{
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    public BluetoothGatt mBluetoothGatt;

    private int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED = "gamsystech.user.newbleupdated.services.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "gamsystech.user.newbleupdated.services.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "gamsystech.user.newbleupdated.services.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "gamsystech.user.newbleupdated.services.bluetooth.le.ACTION_DATA_AVAILABLE";

    public final static String ACTION_BATTERY_LEVEL_CHANGED = "gamsystech.user.newbleupdated.services.bluetooth.le.ACTION_BATTERY_LEVEL_CHANGED";

    public final static String EXTRA_DATA = "gamsystech.user.newbleupdated.services.bluetooth.le.EXTRA_DATA";

    /*uuid service*/
    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(GattAttributes.HEART_RATE_MEASUREMENT);
    public final static UUID UUID_MICROCHIP_SERVICE = UUID.fromString(GattAttributes.SERVICE_MICROCHIP_ACCESS);


    private Queue<BluetoothGattDescriptor> descriptorWriteQueue = new LinkedList<BluetoothGattDescriptor>();
    private Queue<BluetoothGattCharacteristic> readCharacteristicQueue = new LinkedList<BluetoothGattCharacteristic>();




    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and com.example.testble.services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback()
    {

        /*IF CONNECTION STATED CHANGED CONNECTED OR DISCONNECTED CALLBACK METHODS*/
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
        {

            Log.i(TAG, "onConnectionStateChange: status, newState: "+status+", "+newState);

            String intentAction;

            if (newState == BluetoothProfile.STATE_CONNECTED)
            {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");

                // Attempts to discover com.example.testble.services after successful connection.

                Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED)
            {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;

                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status)
        {
            if (status == BluetoothGatt.GATT_SUCCESS)
            {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else
            {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {
            if (status == BluetoothGatt.GATT_SUCCESS)
            {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
            Log.d(TAG, "onCharacteristicRead: characteristic="+characteristic.getUuid()+"| value="+characteristic.getValue().toString());
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
        {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                 Log.d(TAG, "onCharacteristicChanged: characteristic= "+characteristic.getUuid()+"| value="+characteristic.getValue().toString());
        }
    };

    private void broadcastUpdate(final String action)
    {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(action);
        final byte[] data = characteristic.getValue();                              //getting value in byte


        // intent.putExtra(EXTRA_DATA, stringdata);
        // sendBroadcast(intent);



        //byte value to string value
        if(data !=null && data.length >0)
        {
            final  StringBuilder stringBuilder = new StringBuilder(data.length);

            for(byte bytechar : data)
                stringBuilder.append(String.format("%02X",bytechar));
                final String stringchar = characteristic.getStringValue(0);
                intent.putExtra(EXTRA_DATA, stringchar);
                sendBroadcast(intent);


              final int flag = characteristic.getProperties();
             int format = -1;
             if((flag & 0*01) != 0)
            {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, " format UINT16."+format);
            }
            else
            {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "  UINT8."+format);
            }

           /* int batterylevel = characteristic.getValue()[0];
            Log.d(TAG, "  Battery level."+batterylevel);

            intent.putExtra("battery_status", String.valueOf(batterylevel));
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/


        }



            /*if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                String[] arr=new String[9];
                int i1=0;
                for(byte byteChar : data) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                    Log.d(TAG, "byte char . " + String.format("%02X ", byteChar));
                    arr[i1]=String.format("%02X ", byteChar);
                    i1=i1+1;

                }
                String hex=arr[4].trim();
                int decimal=Integer.parseInt(hex,16);
                Log.d(TAG, "hex to decimal  . " +String.valueOf(decimal));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());

                Log.d(TAG, "string builder." + stringBuilder.toString());
            }*/


    }

    public class LocalBinder extends Binder
    {
        public BluetoothLeService getService()
        {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize()
    {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null)
        {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null)
            {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address)
    {
        if (mBluetoothAdapter == null || address == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null)
        {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect())
            {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null)
        {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Check if already connected to device
     * @param address   Address of device
     * @return
     */
    public boolean isConnected(final String address)
    {
        if (mBluetoothAdapter == null || address == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null)
        {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect())
            {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else
            {
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     * @param DEVICEID
     */
    public boolean disconnect(String DEVICEID)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");

        }
        mBluetoothGatt.disconnect();
        return true ;
    }

    /**
     * DISCONNECTED TO PARTICULAR DEVICE
     *
     *
     */

    public void disconnect(BluetoothDevice device)
    {
        if(mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG,"BLuetooth not initililzed");
            return;
        }

    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
        if (descriptor != null)
        {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            mBluetoothGatt.readDescriptor(descriptor);
        }
        Log.d(TAG, "readCharacteristic: " + characteristic.getValue());

        /*check characteristic property*/
        final int properties =  characteristic.getProperties();
        if (characteristic.getProperties() > 0 && characteristic.PROPERTY_READ > 0)
        {
            Log.d(TAG, "does not return data for custom attributes");
            return false;
        }

        if (mBluetoothGatt.readCharacteristic(characteristic) == true)
        {
            Log.w(TAG, "characterstic read succesfuuly");
        }
        else
        {
            Log.w(TAG, "characterstic read Faild");
        }

        //put the characteristic into the read queue
        readCharacteristicQueue.add(characteristic);
        //if there is only 1 item in the queue, then read it.  If more than 1, we handle asynchronously in the callback above
        //GIVE PRECEDENCE to descriptor writes.  They must all finish first.
        if((readCharacteristicQueue.size() == 1) && (descriptorWriteQueue.size() == 0))
            return true;

        return  mBluetoothGatt.readCharacteristic(characteristic);

    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification. False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);


        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
        if(descriptor !=null)
        {
            if(enabled)
            {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);

            }
            else
            {
                descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            }
            mBluetoothGatt.writeDescriptor(descriptor);
            mBluetoothGatt.readDescriptor(descriptor);
        }

        characteristic.getDescriptors();
        Log.d(TAG, "characterstic of descriptors" + characteristic.getDescriptors());
    }



    /**
     * Retrieves a list of supported GATT com.example.testble.services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported com.example.testble.services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;
        return mBluetoothGatt.getServices();
    }

    /**
     * Request a write on a given {@code BluetoothGattCharacteristic}. The write result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to write on.
     */
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, byte[] value)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }

        Log.w(TAG, "Writing value: "+ bytesToHex(value) + " to "+ characteristic.getUuid());
        characteristic.setValue(value);
        return mBluetoothGatt.writeCharacteristic(characteristic);
    }


    //write string value
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic, String value)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");

            if (characteristic == null)
            {
                Log.w(TAG, "Characterstics Null");

            }
            return false;
        }
        characteristic.setValue(value);
        characteristic.getProperties();

        Log.w(TAG, "Characteristic property: " + value + " to " + characteristic.getProperties() + "" + characteristic.getStringValue(0));
        boolean status = mBluetoothGatt.writeCharacteristic(characteristic);
        System.out.print("Write status" + status);
        Log.w(TAG, "Writing value: " + value + " to " + characteristic.getUuid());
        return mBluetoothGatt.writeCharacteristic(characteristic);

    }


    /*Utility function for printing byte array to hex string*/
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /*byte to hexa*/
    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    /*byte to string*/
    public static String byteToString(byte[] bytes)
    {
        String stringchar = new String(bytes);
        for (int i = 0; i < stringchar.length(); i++)
        {
            System.out.print(stringchar);
        }
        return stringchar;
    }


    //string representation of byte
    public static String parse(final BluetoothGattCharacteristic characteristic)
    {
        final byte[] data = characteristic.getValue();
        if (data == null)
            return "";
        final int length = data.length;

        if (length == 0)
            return "";
        final char[] out = new char[length * 3 - 1];
        for (int j = 0; j < length; j++) {
            int v = data[j] & 0xFF;
            out[j * 3] = hexArray[v >>> 4];
            out[j * 3 + 1] = hexArray[v & 0x0F];
            if (j != length - 1)
                out[j * 3 + 2] = '-';
        }
        return new String(out);
    }


    /*hexa string to byte array*/
    public static byte[] hexStringToByteArray(String s)
    {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
