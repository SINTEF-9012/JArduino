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

import org.sintef.jarduino.msg.*;

public interface IJArduinoMessageHandler {

	void handlePinMode(PinModeMsg msg);
	void handleDigitalRead(DigitalReadMsg msg);
	void handleDigitalWrite(DigitalWriteMsg msg);
	void handleAnalogReference(AnalogReferenceMsg msg);
	void handleAnalogRead(AnalogReadMsg msg);
	void handleAnalogWrite(AnalogWriteMsg msg);
	void handleTone(ToneMsg msg);
	void handleNoTone(NoToneMsg msg);
    void handlePulseIn(PulseInMsg msg);
	void handlePing(PingMsg msg);
	void handleAttachInterrupt(AttachInterruptMsg msg);
	void handleDetachInterrupt(DetachInterruptMsg msg);
	void handleEeprom_read(Eeprom_readMsg msg);
	void handleEeprom_sync_write(Eeprom_sync_writeMsg msg);
	void handleEeprom_write(Eeprom_writeMsg msg);
	
	void handleDigitalReadResult(DigitalReadResultMsg msg);
	void handleAnalogReadResult(AnalogReadResultMsg msg);
    void handlePulseInResult(PulseInResultMsg msg);
	void handlePong(PongMsg msg);
	void handleInterruptNotification(InterruptNotificationMsg msg);
	void handleEeprom_value(Eeprom_valueMsg msg);
	void handleEeprom_write_ack(Eeprom_write_ackMsg msg);
}
