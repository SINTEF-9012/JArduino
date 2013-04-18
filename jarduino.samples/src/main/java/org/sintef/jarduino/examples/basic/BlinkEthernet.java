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
package org.sintef.jarduino.examples.basic;

import org.sintef.jarduino.*;

import java.net.SocketException;
import java.net.UnknownHostException;
/*
Blink
Turns on an LED on for one second, then off for one second, repeatedly.

This example code is in the public domain.
 */

public class BlinkEthernet extends JArduino {

    public BlinkEthernet(String ip, JArduinoCom ethernet) {
        super(ip, ethernet);
    }

    @Override
    protected void setup() {
        // initialize the digital pin as an output.
        // Pin 13 has an LED connected on most Arduino boards:
        pinMode(DigitalPin.PIN_9, PinMode.OUTPUT);
    }

    @Override
    protected void loop() {
        // set the LED on
        digitalWrite(DigitalPin.PIN_9, DigitalState.HIGH);
        delay(1000); // wait for a second
        // set the LED off
        digitalWrite(DigitalPin.PIN_9, DigitalState.LOW);
        delay(1000); // wait for a second
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        String ip;
        if (args.length == 1) {
            ip = args[0];
        } else {
            ip = "192.168.178.29";
        }
        JArduino arduino = new BlinkEthernet(ip, JArduinoCom.Ethernet);
        arduino.runArduinoProcess();
    }
}
