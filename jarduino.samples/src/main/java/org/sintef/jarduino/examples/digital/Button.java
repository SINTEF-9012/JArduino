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
package org.sintef.jarduino.examples.digital;

import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.InvalidPinTypeException;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.Pin;
import org.sintef.jarduino.comm.Serial4JArduino;

public class Button extends JArduino {

    // the number of the pushbutton pin
    final Pin buttonPin = p8;
    // the number of the LED pin
    final Pin ledPin = p9;
    // variable for reading the pushbutton status
    DigitalState buttonState = LOW;

    public Button(String port) {
        super(port);
    }

    @Override
    protected void setup() throws InvalidPinTypeException {
        // initialize the LED pin as an output:
        pinMode(ledPin, OUTPUT);
        // initialize the pushbutton pin as an input:
        pinMode(buttonPin, INPUT);
    }

    @Override
    protected void loop() throws InvalidPinTypeException {

        // read the state of the pushbutton value:
    	buttonState = digitalRead(buttonPin);
		// check if the pushbutton is pressed.
        // if it is, the buttonState is HIGH:
        if (buttonState == HIGH) {
            // turn LED on:    
            digitalWrite(ledPin, HIGH);
        } else {
            // turn LED off:
            digitalWrite(ledPin, LOW);
        }
        
    }

    public static void main(String[] args) {
        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }
        
        JArduino arduino = new Button(serialPort);
        arduino.runArduinoProcess();
    }
}
