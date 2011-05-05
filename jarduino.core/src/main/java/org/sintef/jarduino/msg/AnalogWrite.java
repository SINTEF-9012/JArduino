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

public class AnalogWrite extends JArduinoProtocolPacket {

	private EPWMPin pin;
	private byte value;
	
	public AnalogWrite(EPWMPin pin, byte value) {
		setCommandID(JArduinoProtocol.ANALOG_WRITE);
		setByteValue(pin.getValue());
		this.pin = pin;
		setByteValue(value);
		this.value = value;
	}
	
	public AnalogWrite(byte[] packet) {
		setPacketData(packet);
		pin = EPWMPin.fromValue(buffer.get());		
		value = buffer.get();
		
	}
	
	@Override
	public void acceptHandler(JArduinoMessageHandler v) {
		v.handleAnalogWrite(this);
	}

	@Override
	public String toString(){
		String myString = "analogWrite:";
		myString += " [pin: "+pin+"]";
		myString += " [value: "+value+"]";
		return myString;
	}

	public EPWMPin getPin() {
		return pin;
	}
	
	public byte getValue() {
		return value;
	}
	
}