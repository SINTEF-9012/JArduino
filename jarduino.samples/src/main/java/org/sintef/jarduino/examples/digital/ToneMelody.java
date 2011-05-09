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


public class ToneMelody extends JArduino implements Pitches {

    public ToneMelody(String port) {
        super(port);
    }
    short melody[] = {NOTE_C4, NOTE_G3, NOTE_G3, NOTE_A3, NOTE_G3, 0, NOTE_B3, NOTE_C4};
    // note durations: 4 = quarter note, 8 = eighth note, etc.:
    int noteDurations[] = {4, 8, 8, 4, 4, 4, 4, 4};

    @Override
    protected void setup() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void loop() {
        for (int thisNote = 0; thisNote < 8; thisNote++) {
            // to calculate the note duration, take one second 
            // divided by the note type.
            //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
            short noteDuration = (short) (1000 / noteDurations[thisNote]);
            tone(DigitalPin.A_0, melody[thisNote], noteDuration);

            // to distinguish the notes, set a minimum time between them.
            // the note's duration + 30% seems to work well:
            int pauseBetweenNotes = (int) (noteDuration * 1.30);
            delay(pauseBetweenNotes);
        }
    }

    public static void main(String[] args) {

        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }

        JArduino arduino = new ToneMelody(serialPort);
        arduino.runArduinoProcess();
    }
}
