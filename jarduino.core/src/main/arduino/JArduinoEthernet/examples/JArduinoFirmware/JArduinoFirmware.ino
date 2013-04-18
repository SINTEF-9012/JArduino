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
#include <JArduinoEthernet.h>
#include <EEPROM.h>
#include <SPI.h>
#include <Ethernet.h>
#include <EthernetUdp.h>

JArduinoEthernet _JArduino = JArduinoEthernet();

void interrupt0() {
  _JArduino.sendinterruptNotification(0);
}

void interrupt1() {
   _JArduino.sendinterruptNotification(1);
}

void receivepinMode(uint8_t pin, uint8_t mode) {
  pinMode(pin, mode);
}
void receivedigitalRead(uint8_t pin) {
  int result = digitalRead(pin);
  _JArduino.senddigitalReadResult(result);
}
void receivedigitalWrite(uint8_t pin, uint8_t value) {
  digitalWrite(pin, value);
}
void receiveanalogReference(uint8_t type) {
  analogReference(type);
}
void receiveanalogRead(uint8_t pin) {
  int result = analogRead(pin);
  _JArduino.sendanalogReadResult(result);
}
void receiveanalogWrite(uint8_t pin, uint8_t value) {
  analogWrite(pin, value);
}
void receiveping() {
  _JArduino.sendpong();
}
void receiveattachInterrupt(uint8_t interrupt, uint8_t mode) {
  if (interrupt == 0) attachInterrupt(0, interrupt0, mode);
  else if (interrupt == 1) attachInterrupt(1, interrupt1, mode);
}
void receivedetachInterrupt(uint8_t interrupt) {
  detachInterrupt(interrupt);
}
void receivetone(uint8_t pin, uint16_t frequency, uint16_t duration) {
  if (duration  == 0) tone(pin, frequency);
  else tone(pin, frequency, duration);
}
void receivenoTone(uint8_t pin) {
  noTone(pin);
}
void receiveeeprom_read(uint16_t address) {
  uint8_t result = EEPROM.read(address);
  _JArduino.sendeeprom_value(result);
}
void receiveeeprom_sync_write(uint16_t address, uint8_t value) {
  EEPROM.write(address, value);
   _JArduino.sendeeprom_write_ack();
}
void receiveeeprom_write(uint16_t address, uint8_t value) {
  EEPROM.write(address, value);
}

EthernetUDP Udp;
byte mac[] = { 0x90, 0xA2, 0xDA, 0x0D, 0x94, 0x7A };

void setup()
{
        Ethernet.begin(mac);
        Udp.begin(4000);
	_JArduino.init_JArduino();
}

void loop()
{
	// check for incomming messages for the JArduino protocol
	_JArduino.poll_JArduino();
}

void sendOutgoingMessage(uint8_t data[], uint8_t size) {
  uint8_t i = 0;
  // send the start byte
  Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
  Udp.write(START_BYTE);
  // send data
  for(i=0; i<size; i++) {
    // escape special bytes
    if(data[i] == START_BYTE || data[i] == STOP_BYTE || data[i] == ESCAPE_BYTE) {
      Udp.write(ESCAPE_BYTE);
    }
    Udp.write(data[i]);
  }
  // send the stop byte
  Udp.write(STOP_BYTE);
  Udp.endPacket();
}

char packetBuffer[UDP_TX_PACKET_MAX_SIZE];
int readIndice = 0;
int readAvailable = 0;

uint8_t udpRead(){
    if(readAvailable > 0){
      readAvailable--;
      readIndice++;
      return packetBuffer[readIndice-1];
    }
    return -1;
}
int udpAvailable(){
  if(readAvailable == 0){
    int packetSize = Udp.parsePacket();
    if(packetSize){
        Udp.read(packetBuffer,UDP_TX_PACKET_MAX_SIZE);
        readAvailable = packetSize;
        readIndice = 0;
    }
  }
  return readAvailable;
}

