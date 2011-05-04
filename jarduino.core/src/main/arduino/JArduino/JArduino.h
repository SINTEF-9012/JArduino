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
 
// ensure this library description is only included once
#ifndef JArduino_h
#define JArduino_h

// include types & constants of Wiring core API
#include "WConstants.h"
#undef abs
#undef round

#include <inttypes.h>

#define MAX_PACKET_SIZE 32

#define START_BYTE 0x12
#define STOP_BYTE 0x13
#define ESCAPE_BYTE 0x7D

#define RCV_WAIT 0
#define RCV_MSG 1
#define RCV_ESC 2

#define DigitalState_LOW 0
#define DigitalState_HIGH 1
#define InterruptPin_PIN_2_INT0 0
#define InterruptPin_PIN_3_INT1 1
#define DigitalPin_PIN_0 0
#define DigitalPin_PIN_1 1
#define DigitalPin_PIN_2 2
#define DigitalPin_PIN_3 3
#define DigitalPin_PIN_4 4
#define DigitalPin_PIN_5 5
#define DigitalPin_PIN_6 6
#define DigitalPin_PIN_7 7
#define DigitalPin_PIN_8 8
#define DigitalPin_PIN_9 9
#define DigitalPin_PIN_10 10
#define DigitalPin_PIN_11 11
#define DigitalPin_PIN_12 12
#define DigitalPin_PIN_13 13
#define DigitalPin_A_0 14
#define DigitalPin_A_1 15
#define DigitalPin_A_2 16
#define DigitalPin_A_3 17
#define DigitalPin_A_4 18
#define DigitalPin_A_5 19
#define PinMode_INPUT 0
#define PinMode_OUTPUT 1
#define AnalogReference_DEFAULT 1
#define AnalogReference_INTERNAL 3
#define AnalogReference_EXTERNAL 0
#define AnalogPin_A_0 14
#define AnalogPin_A_1 15
#define AnalogPin_A_2 16
#define AnalogPin_A_3 17
#define AnalogPin_A_4 18
#define AnalogPin_A_5 19
#define PWMPin_PWM_PIN_3 3
#define PWMPin_PWM_PIN_5 5
#define PWMPin_PWM_PIN_6 6
#define PWMPin_PWM_PIN_9 9
#define PWMPin_PWM_PIN_10 10
#define PWMPin_PWM_PIN_11 11
#define InterruptTrigger_CHANGE 1
#define InterruptTrigger_RISING 3
#define InterruptTrigger_FALLING 2
#define InterruptTrigger_LOW 0

// Operations which implements the logic to execute when messages are received.
extern void receivepinMode(uint8_t pin, uint8_t mode);
extern void receivedigitalRead(uint8_t pin);
extern void receivedigitalWrite(uint8_t pin, uint8_t value);
extern void receiveanalogReference(uint8_t type);
extern void receiveanalogRead(uint8_t pin);
extern void receiveanalogWrite(uint8_t pin, uint8_t value);
extern void receivetone(uint8_t pin, uint16_t frequency, uint16_t duration);
extern void receivenoTone(uint8_t pin);
extern void receiveping();
extern void receiveattachInterrupt(uint8_t interrupt, uint8_t mode);
extern void receivedetachInterrupt(uint8_t interrupt);
extern void receiveeeprom_read(uint16_t address);
extern void receiveeeprom_sync_write(uint16_t address, uint8_t value);
extern void receiveeeprom_write(uint16_t address, uint8_t value);

class JArduino
{
  public:
	// Constructor
    JArduino();
	// Standard init and pool operations to be called in setup and loop
	void init_JArduino();
	void poll_JArduino();
	// Operations for sending all messages
    void senddigitalReadResult(uint8_t value);
    void sendanalogReadResult(uint16_t value);
    void sendpong();
    void sendinterruptNotification(uint8_t interrupt);
    void sendeeprom_value(uint8_t value);
    void sendeeprom_write_ack();

  private:
	uint8_t state;
	uint8_t buffer[MAX_PACKET_SIZE];
	uint8_t payload[MAX_PACKET_SIZE];
	uint8_t buffer_idx;
	// private operation which parses incomming message and calls
	// the corresponding exten functions
    void parseIncommingMessage(uint8_t data[], uint8_t size);
    void sendOutgoingMessage(uint8_t data[], uint8_t size);
};

#endif
