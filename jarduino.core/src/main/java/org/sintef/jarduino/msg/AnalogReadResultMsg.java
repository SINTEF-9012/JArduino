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

public class AnalogReadResultMsg extends JArduinoProtocolPacket {

	private short value;
	
	public AnalogReadResultMsg(short value) {
		setCommandID(JArduinoProtocol.ANALOG_READ_RESULT);
		setShortValue(value);
		this.value = value;
	}
	
	public AnalogReadResultMsg(byte[] packet) {
		setPacketData(packet);
		value = buffer.getShort();
		
	}
	
	@Override
	public void acceptHandler(JArduinoMessageHandler v) {
		v.handleAnalogReadResult(this);
	}

	@Override
	public String toString(){
		String myString = "analogReadResult:";
		myString += " [value: "+value+"]";
		return myString;
	}

	public short getValue() {
		return value;
	}
	
}