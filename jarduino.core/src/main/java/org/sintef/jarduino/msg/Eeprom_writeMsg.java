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

public class Eeprom_writeMsg extends JArduinoProtocolPacket {

	private short address;
	private byte value;
	
	public Eeprom_writeMsg(short address, byte value) {
		setCommandID(JArduinoProtocol.EEPROM__WRITE);
		setShortValue(address);
		this.address = address;
		setByteValue(value);
		this.value = value;
	}
	
	public Eeprom_writeMsg(byte[] packet) {
		setPacketData(packet);
		address = buffer.getShort();
		value = buffer.get();
		
	}
	
	@Override
	public void acceptHandler(JArduinoMessageHandler v) {
		v.handleEeprom_write(this);
	}

	@Override
	public String toString(){
		String myString = "eeprom_write:";
		myString += " [address: "+address+"]";
		myString += " [value: "+value+"]";
		return myString;
	}

	public short getAddress() {
		return address;
	}
	
	public byte getValue() {
		return value;
	}
	
}