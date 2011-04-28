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
	public void handlepinMode(pinMode msg){ /* Nothing */ }
	public void handledigitalRead(digitalRead msg){ /* Nothing */ }
	public void handledigitalWrite(digitalWrite msg){ /* Nothing */ }
	public void handleanalogReference(analogReference msg){ /* Nothing */ }
	public void handleanalogRead(analogRead msg){ /* Nothing */ }
	public void handleanalogWrite(analogWrite msg){ /* Nothing */ }
	public void handletone(tone msg){ /* Nothing */ }
	public void handlenoTone(noTone msg){ /* Nothing */ }
	public void handleping(ping msg){ /* Nothing */ }
	public void handleattachInterrupt(attachInterrupt msg){ /* Nothing */ }
	public void handledetachInterrupt(detachInterrupt msg){ /* Nothing */ }
	public void handleeeprom_read(eeprom_read msg){ /* Nothing */ }
	public void handleeeprom_sync_write(eeprom_sync_write msg){ /* Nothing */ }
	public void handleeeprom_write(eeprom_write msg){ /* Nothing */ }
}
