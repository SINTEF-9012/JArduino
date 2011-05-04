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

public abstract class JArduinoProtocol {

	private JArduinoProtocol(){}

	public static final byte PIN_MODE = 2; 
	public static final byte DIGITAL_READ = 4; 
	public static final byte DIGITAL_WRITE = 3; 
	public static final byte ANALOG_REFERENCE = 6; 
	public static final byte ANALOG_READ = 7; 
	public static final byte ANALOG_WRITE = 9; 
	public static final byte TONE = 10; 
	public static final byte NO_TONE = 11; 
	public static final byte PING = 66; 
	public static final byte ATTACH_INTERRUPT = 21; 
	public static final byte DETACH_INTERRUPT = 22; 
	public static final byte EEPROM__READ = 31; 
	public static final byte EEPROM__SYNC__WRITE = 34; 
	public static final byte EEPROM__WRITE = 33; 
	public static final byte DIGITAL_READ_RESULT = 5; 
	public static final byte ANALOG_READ_RESULT = 8; 
	public static final byte PONG = 67; 
	public static final byte INTERRUPT_NOTIFICATION = 23; 
	public static final byte EEPROM__VALUE = 32; 
	public static final byte EEPROM__WRITE__ACK = 35; 
	
	public static FixedSizePacket createMessageFromPacket(byte[] packet) {
		byte packetType = packet[4];
		FixedSizePacket result = null;
		switch(packetType){
			case PIN_MODE: result = new pinMode(packet); break; 
			case DIGITAL_READ: result = new digitalRead(packet); break; 
			case DIGITAL_WRITE: result = new digitalWrite(packet); break; 
			case ANALOG_REFERENCE: result = new analogReference(packet); break; 
			case ANALOG_READ: result = new analogRead(packet); break; 
			case ANALOG_WRITE: result = new analogWrite(packet); break; 
			case TONE: result = new tone(packet); break; 
			case NO_TONE: result = new noTone(packet); break; 
			case PING: result = new ping(packet); break; 
			case ATTACH_INTERRUPT: result = new attachInterrupt(packet); break; 
			case DETACH_INTERRUPT: result = new detachInterrupt(packet); break; 
			case EEPROM__READ: result = new eeprom_read(packet); break; 
			case EEPROM__SYNC__WRITE: result = new eeprom_sync_write(packet); break; 
			case EEPROM__WRITE: result = new eeprom_write(packet); break; 
			case DIGITAL_READ_RESULT: result = new digitalReadResult(packet); break; 
			case ANALOG_READ_RESULT: result = new analogReadResult(packet); break; 
			case PONG: result = new pong(packet); break; 
			case INTERRUPT_NOTIFICATION: result = new interruptNotification(packet); break; 
			case EEPROM__VALUE: result = new eeprom_value(packet); break; 
			case EEPROM__WRITE__ACK: result = new eeprom_write_ack(packet); break; 
			default: break;
		}
		return result;
	}

	public static FixedSizePacket createpinMode(DigitalPin pin, PinMode mode) {
		return new pinMode(pin, mode);
	}
	
	public static FixedSizePacket createdigitalRead(DigitalPin pin) {
		return new digitalRead(pin);
	}
	
	public static FixedSizePacket createdigitalWrite(DigitalPin pin, DigitalState value) {
		return new digitalWrite(pin, value);
	}
	
	public static FixedSizePacket createanalogReference(AnalogReference type) {
		return new analogReference(type);
	}
	
	public static FixedSizePacket createanalogRead(AnalogPin pin) {
		return new analogRead(pin);
	}
	
	public static FixedSizePacket createanalogWrite(PWMPin pin, byte value) {
		return new analogWrite(pin, value);
	}
	
	public static FixedSizePacket createtone(DigitalPin pin, short frequency, short duration) {
		return new tone(pin, frequency, duration);
	}
	
	public static FixedSizePacket createnoTone(DigitalPin pin) {
		return new noTone(pin);
	}
	
	public static FixedSizePacket createping() {
		return new ping();
	}
	
	public static FixedSizePacket createattachInterrupt(InterruptPin interrupt, InterruptTrigger mode) {
		return new attachInterrupt(interrupt, mode);
	}
	
	public static FixedSizePacket createdetachInterrupt(InterruptPin interrupt) {
		return new detachInterrupt(interrupt);
	}
	
	public static FixedSizePacket createeeprom_read(short address) {
		return new eeprom_read(address);
	}
	
	public static FixedSizePacket createeeprom_sync_write(short address, byte value) {
		return new eeprom_sync_write(address, value);
	}
	
	public static FixedSizePacket createeeprom_write(short address, byte value) {
		return new eeprom_write(address, value);
	}
	
	
	public static FixedSizePacket createdigitalReadResult(DigitalState value) {
		return new digitalReadResult(value);
	}
	
	public static FixedSizePacket createanalogReadResult(short value) {
		return new analogReadResult(value);
	}
	
	public static FixedSizePacket createpong() {
		return new pong();
	}
	
	public static FixedSizePacket createinterruptNotification(InterruptPin interrupt) {
		return new interruptNotification(interrupt);
	}
	
	public static FixedSizePacket createeeprom_value(byte value) {
		return new eeprom_value(value);
	}
	
	public static FixedSizePacket createeeprom_write_ack() {
		return new eeprom_write_ack();
	}
	
}
