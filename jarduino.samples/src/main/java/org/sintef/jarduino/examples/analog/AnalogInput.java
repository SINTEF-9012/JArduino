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
 * Authors: Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino.examples.analog;

import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PinMode;
import org.sintef.jarduino.comm.Serial4JArduino;

public class AnalogInput extends JArduino {

    // These constants won't change.  They're used to give names
    // to the pins used:
    final AnalogPin sensorPin = AnalogPin.A_1;  // Analog input pin that the potentiometer is attached to
    final DigitalPin ledPin = DigitalPin.PIN_9; // Analog output pin that the LED is attached to
    int sensorValue = 0;        // value read from the pot

    public AnalogInput(String port) {
        super(port);
    }

    @Override
    protected void setup() {
        pinMode(ledPin, PinMode.OUTPUT);
    }

    @Override
    protected void loop() {
        // read the value from the sensor:
        sensorValue = analogRead(sensorPin);
        // turn the ledPin on
        digitalWrite(ledPin, DigitalState.HIGH);
        // stop the program for <sensorValue> milliseconds:
        delay(sensorValue);
        // turn the ledPin off:        
        digitalWrite(ledPin, DigitalState.LOW);
        // stop the program for for <sensorValue> milliseconds:
        delay(sensorValue);
    }

    public static void main(String[] args) {
        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }

        JArduino arduino = new AnalogInput(serialPort);
        arduino.runArduinoProcess();
    }
}
