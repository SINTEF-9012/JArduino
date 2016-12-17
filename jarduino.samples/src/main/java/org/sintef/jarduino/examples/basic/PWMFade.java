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
 * Authors: Robert Huber
 * Company: iEthology.com
 * Date: 2016
 */
package org.sintef.jarduino.examples.basic;

import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PWMPin;
import org.sintef.jarduino.comm.Serial4JArduino;

/*
PWMFade
controls a PWMPin to fade from ON to OFF states

This example code is in the public domain.
 */

public class PWMFade extends JArduino {

	final PWMPin LEDPin_PWM = PWMPin.PWM_PIN_5;
	final int wait_mSecs = 25;
	final byte brightness_OFF = (byte)0;
	final byte brightness_ON = (byte)127;
	byte brightness;

	public PWMFade(String port) { super(port); }

	@Override
	protected void setup() {
		brightness = brightness_ON;
	}

	@Override
	protected void loop() {
		if (brightness>=0) {
		    analogWrite(LEDPin_PWM, brightness);
			System.out.println("LED Brightness: " + brightness);
			delay(wait_mSecs);
			brightness--;
		} else {
			brightness = brightness_ON;
		}
	}

	public static void main(String[] args) {
		String serialPort;
		if (args.length == 1) {
			serialPort = args[0];
		} else {
			serialPort = Serial4JArduino.selectSerialPort();
		}
		System.out.println(serialPort);
		JArduino arduino = new PWMFade(serialPort);
		arduino.runArduinoProcess();
	}
}
