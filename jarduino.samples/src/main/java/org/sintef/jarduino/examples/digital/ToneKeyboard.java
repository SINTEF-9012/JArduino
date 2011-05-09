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


public class ToneKeyboard extends JArduino implements Pitches {

    int threshold = 10;    // minimum reading of the sensors that generates a note
    // notes to play, corresponding to the 3 sensors:
    short notes[] = {NOTE_A4, NOTE_B4, NOTE_C3};

    public ToneKeyboard(String port) {
        super(port);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {
        //This is an easy "hack" to iterate through the AnalogPin enums
        for (byte b = 14; b < 17; b++) {
            // get a sensor reading:
            int sensorReading = analogRead(AnalogPin.fromValue(b));//get the AnalogPin from the byte
            // if the sensor is pressed hard enough:
            if (sensorReading > threshold) {
                // play the note corresponding to this sensor:
                //subtract b with 14 to get the right place in
                //the notes array
                tone(DigitalPin.PIN_8, notes[b - 14], (short) 20);
            }
        }
    }

    public static void main(String[] args) {

        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }
        
        JArduino arduino = new ToneKeyboard(serialPort);
        arduino.runArduinoProcess();
    }
}
