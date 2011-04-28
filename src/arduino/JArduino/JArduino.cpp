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

// include core Wiring API
#include "WProgram.h"

// include this library's description file
#include "JArduino.h"

// Constructor /////////////////////////////////////////////////////////////////
JArduino::JArduino() {
	state = RCV_WAIT;
	buffer_idx = 0;
}

// Public Methods //////////////////////////////////////////////////////////////
void JArduino::init_JArduino(void) {
	// init the serial port
	Serial.begin(9600);
}

void JArduino::poll_JArduino(void) {	
  uint8_t data;
  while (Serial.available() > 0) {
    data = Serial.read();
    // we got a byte from the serial port
    if (state == RCV_WAIT) {
      // it should be a start byte or we just ignore it
      if (data == START_BYTE)  {
        state = RCV_MSG;
        buffer_idx = 0;
      }
    }
    else if (state == RCV_MSG) {
      if (data == ESCAPE_BYTE) {
        state = RCV_ESC;
      }
      else if (data == STOP_BYTE) {
        // We got a complete frame
        parseIncommingMessage(buffer, buffer_idx);
        state = RCV_WAIT;
      }
      else if (data == START_BYTE) {
        // Should not happen but we reset just in case
        state = RCV_MSG;
        buffer_idx = 0;
      }
      else { // it is just a byte to store
        buffer[buffer_idx] = data;
        buffer_idx++;
      }
    }
    else if (state == RCV_ESC) {
      // Store the byte without looking at it
      buffer[buffer_idx] = data;
      buffer_idx++;
      state = RCV_MSG;
    }
  }
}

void JArduino::senddigitalReadResult(uint8_t value) {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 1; // length of the params
   payload[4] = 5; // command code
   // set params here
   payload[5] = value;
   // send the message
   sendOutgoingMessage(payload, 16);
}
void JArduino::sendanalogReadResult(uint16_t value) {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 2; // length of the params
   payload[4] = 8; // command code
   // set params here
   payload[5] = value >> 8 & 0x00ff; payload[6] = value & 0x00ff;
   // send the message
   sendOutgoingMessage(payload, 16);
}
void JArduino::sendpong() {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 0; // length of the params
   payload[4] = 67; // command code
   // set params here
   // send the message
   sendOutgoingMessage(payload, 16);
}
void JArduino::sendinterruptNotification(uint8_t interrupt) {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 1; // length of the params
   payload[4] = 23; // command code
   // set params here
   payload[5] = interrupt;
   // send the message
   sendOutgoingMessage(payload, 16);
}
void JArduino::sendeeprom_value(uint8_t value) {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 1; // length of the params
   payload[4] = 32; // command code
   // set params here
   payload[5] = value;
   // send the message
   sendOutgoingMessage(payload, 16);
}
void JArduino::sendeeprom_write_ack() {
   payload[0] = 0x01; // source addr (not used)
   payload[1] = 0x00; // target addr (not used)
   payload[2] = 0x00; // frame num (not used)
   payload[3] = 0; // length of the params
   payload[4] = 35; // command code
   // set params here
   // send the message
   sendOutgoingMessage(payload, 16);
}

// Private Methods /////////////////////////////////////////////////////////////
void JArduino::parseIncommingMessage(uint8_t data[], uint8_t size) {

  if (size < 5) return; // ignore incomplete packets
	
  switch(data[4]) // command code
  {
   case 2: receivepinMode(data[5], data[6]); break;
   case 4: receivedigitalRead(data[5]); break;
   case 3: receivedigitalWrite(data[5], data[6]); break;
   case 6: receiveanalogReference(data[5]); break;
   case 7: receiveanalogRead(data[5]); break;
   case 9: receiveanalogWrite(data[5], data[6]); break;
   case 10: receivetone(data[5], (data[6] << 8) + data[7], (data[8] << 8) + data[9]); break;
   case 11: receivenoTone(data[5]); break;
   case 66: receiveping(); break;
   case 21: receiveattachInterrupt(data[5], data[6]); break;
   case 22: receivedetachInterrupt(data[5]); break;
   case 31: receiveeeprom_read((data[5] << 8) + data[6]); break;
   case 34: receiveeeprom_sync_write((data[5] << 8) + data[6], data[7]); break;
   case 33: receiveeeprom_write((data[5] << 8) + data[6], data[7]); break;
   default: break; 
  }
}

void JArduino::sendOutgoingMessage(uint8_t data[], uint8_t size) {
  uint8_t i = 0;
  // send the start byte
  Serial.write(START_BYTE);
  // send data
  for(i=0; i<size; i++) {
    // escape special bytes
    if(data[i] == START_BYTE || data[i] == STOP_BYTE || data[i] == ESCAPE_BYTE) {
      Serial.write(ESCAPE_BYTE);
    }
    Serial.write(data[i]);
  }
  // send the stop byte
  Serial.write(STOP_BYTE);
}
