/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Franck Fleurey, Brice Morin and Francois Fouqet
 * Company: SINTEF IKT, Oslo, Norway & INRIA
 * Date: 2011
 */
package org.sintef.jarduino.comm;

import android.bluetooth.BluetoothSocket;
import org.sintef.jarduino.observer.JArduinoClientObserver;
import org.sintef.jarduino.observer.JArduinoObserver;
import org.sintef.jarduino.observer.JArduinoSubject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class AndroidBluetooth4JArduino implements JArduinoClientObserver, JArduinoSubject {

    public static final byte START_BYTE = 0x12;
    public static final byte STOP_BYTE = 0x13;
    public static final byte ESCAPE_BYTE = 0x7D;
    protected BluetoothSocket mSocket;
    protected InputStream in;
    protected OutputStream out;
    Set<JArduinoObserver> observers = new HashSet<JArduinoObserver>();

    public AndroidBluetooth4JArduino() {
    }

    public AndroidBluetooth4JArduino(BluetoothSocket socket) {
        setAndroidBluetoothSocket(socket);
    }

    public void setAndroidBluetoothSocket(BluetoothSocket socket){
        mSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        in = tmpIn;
        out = tmpOut;
    }

    public void receiveMsg(byte[] msg) {
        sendData(msg);
    }

    public void register(JArduinoObserver observer) {
        observers.add(observer);
    }

    public void unregister(JArduinoObserver observer) {
        observers.remove(observer);
    }

    public static final int RCV_WAIT = 0;

    protected void sendData(byte[] payload) {
        try {
            // send the start byte
            out.write((int) START_BYTE);
            // send data
            for (int i = 0; i < payload.length; i++) {
                // escape special bytes
                if (payload[i] == START_BYTE || payload[i] == STOP_BYTE || payload[i] == ESCAPE_BYTE) {
                    out.write((int) ESCAPE_BYTE);
                }
                out.write((int) payload[i]);
            }
            // send the stop byte
            out.write((int) STOP_BYTE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}