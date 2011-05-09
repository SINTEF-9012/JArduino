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

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.comm.Serial4JArduino;

public class ToneMultiple extends JArduino implements Pitches {

    private DigitalPin speakerOne = DigitalPin.PIN_6;
    private DigitalPin speakerTwo = DigitalPin.PIN_7;
    private DigitalPin speakerThree = DigitalPin.PIN_8;

    public ToneMultiple(String port) {
        super(port);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {
        // turn off tone function for pin 8:
        noTone(speakerThree);
        // play a note on pin 6 for 200 ms:
        tone(speakerOne, (short) 440, (short) 200);
        delay(200);

        // turn off tone function for pin 6:
        noTone(speakerOne);
        // play a note on pin 7 for 500 ms:
        tone(speakerTwo, (short) 494, (short) 500);
        delay(500);

        // turn off tone function for pin 7:
        noTone(speakerTwo);
        // play a note on pin 8 for 500 ms:
        tone(speakerThree, (short) 523, (short) 300);
        delay(300);
    }

    public static void main(String[] args) {
        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }

        JArduino arduino = new ToneMultiple(serialPort);
        arduino.runArduinoProcess();
    }
}
