package org.sintef.jarduino.comm;

import android.bluetooth.BluetoothSocket;
import org.sintef.jarduino.ProtocolConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 21/08/13
 * Time: 15:07
 */
public class AndroidBluetoothConfiguration extends ProtocolConfiguration {
    private BluetoothSocket mSocket;

    AndroidBluetoothConfiguration(BluetoothSocket socket){
        mSocket = socket;
    }

    public BluetoothSocket getmSocket(){
        return mSocket;
    }
}
