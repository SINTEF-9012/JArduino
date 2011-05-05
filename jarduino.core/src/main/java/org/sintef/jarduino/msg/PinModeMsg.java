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
package org.sintef.jarduino.msg;

import org.sintef.jarduino.*;

public class PinModeMsg extends JArduinoProtocolPacket {

	private DigitalPin pin;
	private PinMode mode;
	
	public PinModeMsg(DigitalPin pin, PinMode mode) {
		setCommandID(JArduinoProtocol.PIN_MODE);
		setByteValue(pin.getValue());
		this.pin = pin;
		setByteValue(mode.getValue());
		this.mode = mode;
	}
	
	public PinModeMsg(byte[] packet) {
		setPacketData(packet);
		pin = DigitalPin.fromValue(buffer.get());		
		mode = PinMode.fromValue(buffer.get());		
		
	}
	
	@Override
	public void acceptHandler(JArduinoMessageHandler v) {
		v.handlePinMode(this);
	}

	@Override
	public String toString(){
		String myString = "pinMode:";
		myString += " [pin: "+pin+"]";
		myString += " [mode: "+mode+"]";
		return myString;
	}

	public DigitalPin getPin() {
		return pin;
	}
	
	public PinMode getMode() {
		return mode;
	}
	
}