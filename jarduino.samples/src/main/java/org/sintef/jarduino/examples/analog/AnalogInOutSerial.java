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
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PWMPin;
import org.sintef.jarduino.comm.Serial4JArduino;

/*
Analog input, analog output, serial output

Reads an analog input pin, maps the result to a range from 0 to 255
and uses the result to set the pulsewidth modulation (PWM) of an output pin.
Also prints the results to the serial monitor.

The circuit:
 * potentiometer connected to analog pin 0.
Center pin of the potentiometer goes to the analog pin.
side pins of the potentiometer go to +5V and ground
 * LED connected from digital pin 9 to ground

created 29 Dec. 2008
Modified 4 Sep 2010
by Tom Igoe

Ported to JAVA by SINTEF

This example code is in the public domain.

 */

public class AnalogInOutSerial extends JArduino {

    // These constants won't change.  They're used to give names
    // to the pins used:
    final AnalogPin analogInPin = AnalogPin.A_1;  // Analog input pin that the potentiometer is attached to
    final PWMPin analogOutPin = PWMPin.PWM_PIN_9; // Analog output pin that the LED is attached to
    int sensorValue = 0;        // value read from the pot
    int outputValue = 0;        // value output to the PWM (analog out)

    public AnalogInOutSerial(String port) {
        super(port);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {
        // read the analog in value:
        sensorValue = analogRead(analogInPin);
        // map it to the range of the analog out:
        outputValue = map(sensorValue, 0, 1023, 0, 255);
        // change the analog out value:
        analogWrite(analogOutPin, (byte) outputValue);

        // print the results to the serial monitor:
        System.out.print("sensor = ");
        System.out.print(sensorValue);
        System.out.print("\t output = ");
        System.out.println(outputValue);

        // wait 10 milliseconds before the next loop
        // for the analog-to-digital converter to settle
        // after the last reading:
        delay(10);
    }

    public static void main(String[] args) {

        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }
        
        JArduino arduino = new AnalogInOutSerial(serialPort);
        arduino.runArduinoProcess();
    }
}
