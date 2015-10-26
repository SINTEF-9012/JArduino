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

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.JArduinoMessageHandler;
import org.sintef.jarduino.JArduinoProtocol;
import org.sintef.jarduino.JArduinoProtocolPacket;

public class UltrassonicMsg extends JArduinoProtocolPacket {

	private int value;

	public UltrassonicMsg(DigitalPin pinOut, DigitalPin pinIn) {
		setCommandID(JArduinoProtocol.ULTRASSONIC);
		setByteValue(pinOut.getValue());
		setByteValue(pinIn.getValue());
	}

	public UltrassonicMsg(byte[] packet) {
		setPacketData(packet);
		setValue(buffer.getInt());
	}
	
	@Override
	public void acceptHandler(JArduinoMessageHandler v) {
		v.handleUltrassonic(this);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}