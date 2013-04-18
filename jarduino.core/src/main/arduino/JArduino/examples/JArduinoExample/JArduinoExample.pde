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
#include <JArduino.h>

JArduino _JArduino = JArduino();

void receivepinMode(uint8_t pin, uint8_t mode) {
	// TODO: Implement the handler for message reception of the message pinMode
}
void receivedigitalRead(uint8_t pin) {
	// TODO: Implement the handler for message reception of the message digitalRead
}
void receivedigitalWrite(uint8_t pin, uint8_t value) {
	// TODO: Implement the handler for message reception of the message digitalWrite
}
void receiveanalogReference(uint8_t type) {
	// TODO: Implement the handler for message reception of the message analogReference
}
void receiveanalogRead(uint8_t pin) {
	// TODO: Implement the handler for message reception of the message analogRead
}
void receiveanalogWrite(uint8_t pin, uint8_t value) {
	// TODO: Implement the handler for message reception of the message analogWrite
}
void receivetone(uint8_t pin, uint16_t frequency, uint16_t duration) {
	// TODO: Implement the handler for message reception of the message tone
}
void receivenoTone(uint8_t pin) {
	// TODO: Implement the handler for message reception of the message noTone
}
void receiveping() {
	// TODO: Implement the handler for message reception of the message ping
}
void receiveattachInterrupt(uint8_t interrupt, uint8_t mode) {
	// TODO: Implement the handler for message reception of the message attachInterrupt
}
void receivedetachInterrupt(uint8_t interrupt) {
	// TODO: Implement the handler for message reception of the message detachInterrupt
}
void receiveeeprom_read(uint16_t address) {
	// TODO: Implement the handler for message reception of the message eeprom_read
}
void receiveeeprom_sync_write(uint16_t address, uint8_t value) {
	// TODO: Implement the handler for message reception of the message eeprom_sync_write
}
void receiveeeprom_write(uint16_t address, uint8_t value) {
	// TODO: Implement the handler for message reception of the message eeprom_write
}

void setup()
{
	// initialize the JArduino protocol
	_JArduino.init_JArduino();
	
	// TODO: Add your own setup code here
}

void loop()
{
	// check for incomming messages for the JArduino protocol
	_JArduino.poll_JArduino();
	
	// TODO: Add any additional loop code here.
}
