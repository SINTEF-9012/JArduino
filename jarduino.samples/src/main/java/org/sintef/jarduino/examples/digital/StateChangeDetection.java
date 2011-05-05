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

import org.sintef.jarduino.EDigitalPin;
import org.sintef.jarduino.EDigitalState;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.EPinMode;


public class StateChangeDetection extends JArduino {

	// the number of the pushbutton pin
	final EDigitalPin buttonPin = EDigitalPin.PIN_2;
	// the number of the LED pin
	final EDigitalPin ledPin = EDigitalPin.PIN_9;

	// counter for the number of button presses
	int buttonPushCounter = 0;
	// current state of the button
	EDigitalState buttonState = EDigitalState.LOW;
	// previous state of the button
	EDigitalState lastButtonState = EDigitalState.LOW;

	public StateChangeDetection(String port) {
		super(port);
	}

	@Override
	protected void setup() {
		// initialize the LED pin as an output:
		pinMode(ledPin, EPinMode.OUTPUT);
		// initialize the pushbutton pin as an input:
		pinMode(buttonPin, EPinMode.INPUT);
	}

	@Override
	protected void loop() {

		// read the pushbutton input pin:
		buttonState = digitalRead(buttonPin);

		// compare the buttonState to its previous state
		if (buttonState != lastButtonState) {
			// if the state has changed, increment the counter
			if (buttonState == EDigitalState.HIGH) {
				// if the current state is HIGH then the button
				// wend from off to on:
				buttonPushCounter++;
				System.out.println("on");
				System.out.print("number of button pushes:  ");
				System.out.println(buttonPushCounter);
			} else {
				// if the current state is LOW then the button
				// wend from on to off:
				System.out.println("off");
			}

			// save the current state as the last state,
			// for next time through the loop
			lastButtonState = buttonState;
		}

		// turns on the LED every four button pushes by
		// checking the modulo of the button push counter.
		// the modulo function gives you the remainder of
		// the division of two numbers:
		if (buttonPushCounter % 4 == 0) {
			digitalWrite(ledPin, EDigitalState.HIGH);
		} else {
			digitalWrite(ledPin, EDigitalState.LOW);
		}
	}

	public static void main(String[] args) {
		JArduino arduino = new StateChangeDetection("COM21");
		arduino.runArduinoProcess();
	}

}
