package org.sintef.jarduino;

import android.bluetooth.BluetoothSocket;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 21/08/13
 * Time: 15:07
 */
public class AndroidBluetoothConfiguration extends ProtocolConfiguration {
    private BluetoothSocket mSocket;

    public AndroidBluetoothConfiguration(BluetoothSocket socket){
        mSocket = socket;
    }

    public BluetoothSocket getmSocket(){
        return mSocket;
    }
}
