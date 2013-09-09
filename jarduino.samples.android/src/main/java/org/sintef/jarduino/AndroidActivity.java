package org.sintef.jarduino;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 22/08/13
 * Time: 09:28
 */
public class AndroidActivity extends Activity {

   /*
    * Before using this example, please paire your Bluetooth device to your Android device.
    */

    private static String TAG = "arduino";
    private String mUUID = "00001101-0000-1000-8000-00805F9B34FB"; //Special code, do not
    //change unless you know what you're doing.

    private String deviceName = "FireFly-4101";
    private int REQUEST_ENABLE_BT = 2000; //What you want here.

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        BluetoothDevice mmDevice = null;

        List<String> mArray = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if(device.getName().equals(deviceName))
                    mmDevice = device;
                mArray.add(device.getName() + "\n" + device.getAddress());
            }
        }

        //Creating the socket.
        BluetoothSocket mmSocket;
        BluetoothSocket tmp = null;

        UUID myUUID = UUID.fromString(mUUID);
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) { }
        mmSocket = tmp;

        //socket created, try to connect

        try {
            mmSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // Run the example
        JArduino arduino = new BlinkAndAnalog(mmSocket);
        arduino.runArduinoProcess();

        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }

}
