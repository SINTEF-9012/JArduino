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
			case PIN_MODE: result = new PinMode(packet); break; 
			case DIGITAL_READ: result = new DigitalRead(packet); break; 
			case DIGITAL_WRITE: result = new DigitalWrite(packet); break; 
			case ANALOG_REFERENCE: result = new AnalogReference(packet); break; 
			case ANALOG_READ: result = new AnalogRead(packet); break; 
			case ANALOG_WRITE: result = new AnalogWrite(packet); break; 
			case TONE: result = new Tone(packet); break; 
			case NO_TONE: result = new NoTone(packet); break; 
			case PING: result = new Ping(packet); break; 
			case ATTACH_INTERRUPT: result = new AttachInterrupt(packet); break; 
			case DETACH_INTERRUPT: result = new DetachInterrupt(packet); break; 
			case EEPROM__READ: result = new Eeprom_read(packet); break; 
			case EEPROM__SYNC__WRITE: result = new Eeprom_sync_write(packet); break; 
			case EEPROM__WRITE: result = new Eeprom_write(packet); break; 
			case DIGITAL_READ_RESULT: result = new DigitalReadResult(packet); break; 
			case ANALOG_READ_RESULT: result = new AnalogReadResult(packet); break; 
			case PONG: result = new Pong(packet); break; 
			case INTERRUPT_NOTIFICATION: result = new InterruptNotification(packet); break; 
			case EEPROM__VALUE: result = new Eeprom_value(packet); break; 
			case EEPROM__WRITE__ACK: result = new Eeprom_write_ack(packet); break; 
			default: break;
		}
		return result;
	}

	public static FixedSizePacket createPinMode(EDigitalPin pin, EPinMode mode) {
		return new PinMode(pin, mode);
	}
	
	public static FixedSizePacket createDigitalRead(EDigitalPin pin) {
		return new DigitalRead(pin);
	}
	
	public static FixedSizePacket createDigitalWrite(EDigitalPin pin, EDigitalState value) {
		return new DigitalWrite(pin, value);
	}
	
	public static FixedSizePacket createAnalogReference(EAnalogReference type) {
		return new AnalogReference(type);
	}
	
	public static FixedSizePacket createAnalogRead(EAnalogPin pin) {
		return new AnalogRead(pin);
	}
	
	public static FixedSizePacket createAnalogWrite(EPWMPin pin, byte value) {
		return new AnalogWrite(pin, value);
	}
	
	public static FixedSizePacket createTone(EDigitalPin pin, short frequency, short duration) {
		return new Tone(pin, frequency, duration);
	}
	
	public static FixedSizePacket createNoTone(EDigitalPin pin) {
		return new NoTone(pin);
	}
	
	public static FixedSizePacket createPing() {
		return new Ping();
	}
	
	public static FixedSizePacket createAttachInterrupt(EInterruptPin interrupt, EInterruptTrigger mode) {
		return new AttachInterrupt(interrupt, mode);
	}
	
	public static FixedSizePacket createDetachInterrupt(EInterruptPin interrupt) {
		return new DetachInterrupt(interrupt);
	}
	
	public static FixedSizePacket createEeprom_read(short address) {
		return new Eeprom_read(address);
	}
	
	public static FixedSizePacket createEeprom_sync_write(short address, byte value) {
		return new Eeprom_sync_write(address, value);
	}
	
	public static FixedSizePacket createEeprom_write(short address, byte value) {
		return new Eeprom_write(address, value);
	}
	
	
	public static FixedSizePacket createDigitalReadResult(EDigitalState value) {
		return new DigitalReadResult(value);
	}
	
	public static FixedSizePacket createAnalogReadResult(short value) {
		return new AnalogReadResult(value);
	}
	
	public static FixedSizePacket createPong() {
		return new Pong();
	}
	
	public static FixedSizePacket createInterruptNotification(EInterruptPin interrupt) {
		return new InterruptNotification(interrupt);
	}
	
	public static FixedSizePacket createEeprom_value(byte value) {
		return new Eeprom_value(value);
	}
	
	public static FixedSizePacket createEeprom_write_ack() {
		return new Eeprom_write_ack();
	}
	
}
