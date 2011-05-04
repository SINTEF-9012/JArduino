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

	void handlepinMode(pinMode msg);
	void handledigitalRead(digitalRead msg);
	void handledigitalWrite(digitalWrite msg);
	void handleanalogReference(analogReference msg);
	void handleanalogRead(analogRead msg);
	void handleanalogWrite(analogWrite msg);
	void handletone(tone msg);
	void handlenoTone(noTone msg);
	void handleping(ping msg);
	void handleattachInterrupt(attachInterrupt msg);
	void handledetachInterrupt(detachInterrupt msg);
	void handleeeprom_read(eeprom_read msg);
	void handleeeprom_sync_write(eeprom_sync_write msg);
	void handleeeprom_write(eeprom_write msg);
	
	void handledigitalReadResult(digitalReadResult msg);
	void handleanalogReadResult(analogReadResult msg);
	void handlepong(pong msg);
	void handleinterruptNotification(interruptNotification msg);
	void handleeeprom_value(eeprom_value msg);
	void handleeeprom_write_ack(eeprom_write_ack msg);
}
