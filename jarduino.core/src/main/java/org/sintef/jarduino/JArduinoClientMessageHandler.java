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

public abstract class JArduinoClientMessageHandler implements IJArduinoMessageHandler{
	public void handledigitalReadResult(digitalReadResult msg){ /* Nothing */ }
	public void handleanalogReadResult(analogReadResult msg){ /* Nothing */ }
	public void handlepong(pong msg){ /* Nothing */ }
	public void handleinterruptNotification(interruptNotification msg){ /* Nothing */ }
	public void handleeeprom_value(eeprom_value msg){ /* Nothing */ }
	public void handleeeprom_write_ack(eeprom_write_ack msg){ /* Nothing */ }
}
