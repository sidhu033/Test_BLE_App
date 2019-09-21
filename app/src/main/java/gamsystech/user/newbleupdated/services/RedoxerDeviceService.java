package gamsystech.user.newbleupdated.services;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;



import java.util.UUID;

import gamsystech.user.newbleupdated.utils.AppLogger;
import gamsystech.user.newbleupdated.utils.GattAttributes;

public class RedoxerDeviceService {

    private BluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic mDevicewriteCharacteristic, mDevicereadCharacteristic;
    private BluetoothGattService mDeviceCommunicationService;
    private BluetoothGatt mBluetoothGatt;
    private final static String TAG = RedoxerDeviceService.class.getSimpleName();

    /*CONSTACT TO START DEVICE*/
    final  byte s = (byte)0x73;
    final  byte t = (byte) 0x74;
    final  byte a = (byte) 0x61;
    final  byte r = (byte) 0x72;

    final  byte No_of_cycle = (byte) 0x02;              //if (for Bp only no of cycle 00

    final  byte Time_of_cycle_min = (byte) 0x05;            //cycle of time in min
    final  byte Time_of_cycle_sec = (byte) 0x00;
    final  byte Break_btw_cycle_min = (byte) 0x05;          //after 1 cycle complete break
    final  byte Break_btw_cycle_sec = (byte) 0x00;

    final  byte Delay_of_cycle_device1 = (byte) 0x00;                   //for device start delay
    final  byte Delay_of_cycle_device2 = (byte) 0x05;                   //Time_of_cycle





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public RedoxerDeviceService(BluetoothLeService bluetoothLeService, BluetoothGatt bluetoothGatt)
    {
        mBluetoothLeService= bluetoothLeService;
        mBluetoothGatt= bluetoothGatt;
        mDeviceCommunicationService = mBluetoothGatt.getService(UUID.fromString(GattAttributes.SERVICE_MICROCHIP_ACCESS));      //microchip characterstics


        if (mDeviceCommunicationService != null)
        {
            mDevicewriteCharacteristic = mDeviceCommunicationService.getCharacteristic(UUID.fromString(GattAttributes.CHARACTERISTIC_MICROCHIP_ACCESS_WRITE));
            mDevicereadCharacteristic = mDeviceCommunicationService.getCharacteristic(UUID.fromString(GattAttributes.CHARACTERISTIC_MICROCHIP_ACCESS_READ));

            if (mDevicewriteCharacteristic == null && mDevicereadCharacteristic == null)
            {
                Log.e(TAG, "Communication read and write characteristic not discovered in the device!");
            }
        }
        else
        {
            Log.e(TAG, "Communication service not discovered in the device!");
        }
    }


    /*command start device with byte array*/
    public boolean startdevice() throws Exception
    {

        byte[] startdevice1 = new byte[]{s,t,a,r,t,No_of_cycle,Time_of_cycle_min,Time_of_cycle_sec, Break_btw_cycle_min,Break_btw_cycle_sec,Delay_of_cycle_device1};            //commnad start device

        System.out.print("byte value in string"+startdevice1.toString());

         mBluetoothLeService.setCharacteristicNotification(mDevicewriteCharacteristic,true);
         return mBluetoothLeService.writeCharacteristic(mDevicewriteCharacteristic,startdevice1);

    }
    public boolean startdevice_second() throws Exception
    {

        byte[] startdevice2 = new byte[] {s,t,a,r,t,No_of_cycle,Time_of_cycle_min,Time_of_cycle_sec, Break_btw_cycle_min,Break_btw_cycle_sec,Delay_of_cycle_device2};

        System.out.print("byte value in string"+startdevice2.toString());
        mBluetoothLeService.setCharacteristicNotification(mDevicewriteCharacteristic,true);
        return mBluetoothLeService.writeCharacteristic(mDevicewriteCharacteristic,startdevice2);
    }


    /*command  to request for battery level of device*/
    public  boolean requestbatterylevel() throws Exception
    {
        byte[] battery_level_check = new byte[] {(byte)114, (byte)101,(byte)113,(byte)98,(byte)50,(byte)10};

        System.out.print("battry level check : "+battery_level_check.toString());

        mBluetoothLeService.setCharacteristicNotification(mDevicewriteCharacteristic,true);
        return mBluetoothLeService.writeCharacteristic(mDevicewriteCharacteristic,battery_level_check);
    }


    /*command for request past history of device*/
    public boolean requestLastHisotryOfDevice()
    {

        byte[] request_Last_History = new byte[] {(byte)114, (byte)101,(byte)113,(byte)98,(byte)50,(byte)10};
        AppLogger.debug(TAG,"request Last HIstory of Device"+request_Last_History);

        mBluetoothLeService.setCharacteristicNotification(mDevicewriteCharacteristic,true);
        return mBluetoothLeService.writeCharacteristic(mDevicewriteCharacteristic,request_Last_History);
    }


    /*command for read device */
    public boolean readdevice() throws Exception
    {
        mBluetoothLeService.setCharacteristicNotification(mDevicereadCharacteristic, true);
        return  mBluetoothLeService.readCharacteristic(mDevicereadCharacteristic);
    }


}
