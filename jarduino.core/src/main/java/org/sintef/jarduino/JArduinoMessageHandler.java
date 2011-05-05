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

public abstract class JArduinoMessageHandler implements IJArduinoMessageHandler{
	@Override public void handlePinMode(PinMode msg){ /* Nothing */ }
	@Override public void handleDigitalRead(DigitalRead msg){ /* Nothing */ }
	@Override public void handleDigitalWrite(DigitalWrite msg){ /* Nothing */ }
	@Override public void handleAnalogReference(AnalogReference msg){ /* Nothing */ }
	@Override public void handleAnalogRead(AnalogRead msg){ /* Nothing */ }
	@Override public void handleAnalogWrite(AnalogWrite msg){ /* Nothing */ }
	@Override public void handleTone(Tone msg){ /* Nothing */ }
	@Override public void handleNoTone(NoTone msg){ /* Nothing */ }
	@Override public void handlePing(Ping msg){ /* Nothing */ }
	@Override public void handleAttachInterrupt(AttachInterrupt msg){ /* Nothing */ }
	@Override public void handleDetachInterrupt(DetachInterrupt msg){ /* Nothing */ }
	@Override public void handleEeprom_read(Eeprom_read msg){ /* Nothing */ }
	@Override public void handleEeprom_sync_write(Eeprom_sync_write msg){ /* Nothing */ }
	@Override public void handleEeprom_write(Eeprom_write msg){ /* Nothing */ }
}
