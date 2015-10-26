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
package org.sintef.jarduino;

import java.util.HashMap;
import java.util.Map;

import org.sintef.jarduino.msg.*;

public abstract class JArduinoMessageHandler implements IJArduinoMessageHandler{
	
	protected static final Map<Byte, MessageHandler<JArduinoProtocolPacket>> MESSAGES_MAP = new HashMap<Byte, MessageHandler<JArduinoProtocolPacket>>();
	
	public static final void addMessageHandler(byte code, MessageHandler<JArduinoProtocolPacket> handler) {
		MESSAGES_MAP.putIfAbsent(code, handler);
	}
	
	@Override public void handlePinMode(PinModeMsg msg){ /* Nothing */ }
	@Override public void handleDigitalRead(DigitalReadMsg msg){ /* Nothing */ }
	@Override public void handleDigitalWrite(DigitalWriteMsg msg){ /* Nothing */ }
	@Override public void handleAnalogReference(AnalogReferenceMsg msg){ /* Nothing */ }
	@Override public void handleAnalogRead(AnalogReadMsg msg){ /* Nothing */ }
	@Override public void handleAnalogWrite(AnalogWriteMsg msg){ /* Nothing */ }
	@Override public void handleTone(ToneMsg msg){ /* Nothing */ }
	@Override public void handleNoTone(NoToneMsg msg){ /* Nothing */ }
    @Override public void handlePulseIn(PulseInMsg msg){ /* Nothing */ }
	@Override public void handlePing(PingMsg msg){ /* Nothing */ }
	@Override public void handleAttachInterrupt(AttachInterruptMsg msg){ /* Nothing */ }
	@Override public void handleDetachInterrupt(DetachInterruptMsg msg){ /* Nothing */ }
	@Override public void handleEeprom_read(Eeprom_readMsg msg){ /* Nothing */ }
	@Override public void handleEeprom_sync_write(Eeprom_sync_writeMsg msg){ /* Nothing */ }
	@Override public void handleEeprom_write(Eeprom_writeMsg msg){ /* Nothing */ }
	
	public static interface MessageHandler<T> {
		public void handle(T msg);
	}
}