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
 * Authors: Jan Ole Skotterud, Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino.examples.digital;

import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.comm.Serial4JArduino;

public class TonePitchFollower extends JArduino {

    public TonePitchFollower(String port) {
        super(port);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {
        // read the sensor:
        int sensorReading = analogRead(AnalogPin.A_0);
        // print the sensor reading so you know its range
        System.out.println(sensorReading);
        // map the pitch to the range of the analog input.
        // change the minimum and maximum input numbers below
        // depending on the range your sensor's giving:
        int thisPitch = map(sensorReading, 400, 1000, 100, 1000);

        // play the pitch:
        tone(DigitalPin.PIN_8, (short) thisPitch, (short) 10);
    }

    public static void main(String[] args) {
        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }

        JArduino arduino = new TonePitchFollower(serialPort);
        arduino.runArduinoProcess();
    }
}
