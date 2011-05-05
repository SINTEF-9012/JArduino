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

	void handlePinMode(PinMode msg);
	void handleDigitalRead(DigitalRead msg);
	void handleDigitalWrite(DigitalWrite msg);
	void handleAnalogReference(AnalogReference msg);
	void handleAnalogRead(AnalogRead msg);
	void handleAnalogWrite(AnalogWrite msg);
	void handleTone(Tone msg);
	void handleNoTone(NoTone msg);
	void handlePing(Ping msg);
	void handleAttachInterrupt(AttachInterrupt msg);
	void handleDetachInterrupt(DetachInterrupt msg);
	void handleEeprom_read(Eeprom_read msg);
	void handleEeprom_sync_write(Eeprom_sync_write msg);
	void handleEeprom_write(Eeprom_write msg);
	
	void handleDigitalReadResult(DigitalReadResult msg);
	void handleAnalogReadResult(AnalogReadResult msg);
	void handlePong(Pong msg);
	void handleInterruptNotification(InterruptNotification msg);
	void handleEeprom_value(Eeprom_value msg);
	void handleEeprom_write_ack(Eeprom_write_ack msg);
}
