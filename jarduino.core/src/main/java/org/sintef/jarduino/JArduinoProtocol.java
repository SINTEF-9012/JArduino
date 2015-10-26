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

import java.util.HashMap;
import java.util.Map;

import org.sintef.jarduino.msg.AnalogReadMsg;
import org.sintef.jarduino.msg.AnalogReadResultMsg;
import org.sintef.jarduino.msg.AnalogReferenceMsg;
import org.sintef.jarduino.msg.AnalogWriteMsg;
import org.sintef.jarduino.msg.AttachInterruptMsg;
import org.sintef.jarduino.msg.DetachInterruptMsg;
import org.sintef.jarduino.msg.DigitalReadMsg;
import org.sintef.jarduino.msg.DigitalReadResultMsg;
import org.sintef.jarduino.msg.DigitalWriteMsg;
import org.sintef.jarduino.msg.Eeprom_readMsg;
import org.sintef.jarduino.msg.Eeprom_sync_writeMsg;
import org.sintef.jarduino.msg.Eeprom_valueMsg;
import org.sintef.jarduino.msg.Eeprom_writeMsg;
import org.sintef.jarduino.msg.Eeprom_write_ackMsg;
import org.sintef.jarduino.msg.InterruptNotificationMsg;
import org.sintef.jarduino.msg.NoToneMsg;
import org.sintef.jarduino.msg.PinModeMsg;
import org.sintef.jarduino.msg.PingMsg;
import org.sintef.jarduino.msg.PongMsg;
import org.sintef.jarduino.msg.PulseInMsg;
import org.sintef.jarduino.msg.PulseInResultMsg;
import org.sintef.jarduino.msg.ToneMsg;
import org.sintef.jarduino.msg.UltrassonicMsg;

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
    public static final byte PULSE_IN = 12;
    public static final byte PING = 66;
	public static final byte ATTACH_INTERRUPT = 21; 
	public static final byte DETACH_INTERRUPT = 22; 
	public static final byte EEPROM__READ = 31; 
	public static final byte EEPROM__SYNC__WRITE = 34; 
	public static final byte EEPROM__WRITE = 33; 
	public static final byte DIGITAL_READ_RESULT = 5; 
	public static final byte ANALOG_READ_RESULT = 8;
    public static final byte PULSE_IN_RESULT = 13;
	public static final byte PONG = 67; 
	public static final byte INTERRUPT_NOTIFICATION = 23; 
	public static final byte EEPROM__VALUE = 32; 
	public static final byte EEPROM__WRITE__ACK = 35;
	public static final byte ULTRASSONIC = 99;
	private static final Map<Byte, FixedSizePacket> MESSAGES_MAP = new HashMap<Byte, FixedSizePacket>(); 
	
	public static FixedSizePacket createMessageFromPacket(byte[] packet) {
		byte packetType = packet[4];
		FixedSizePacket result = null;
		switch(packetType){
			case PIN_MODE: result = new PinModeMsg(packet); break; 
			case DIGITAL_READ: result = new DigitalReadMsg(packet); break; 
			case DIGITAL_WRITE: result = new DigitalWriteMsg(packet); break; 
			case ANALOG_REFERENCE: result = new AnalogReferenceMsg(packet); break; 
			case ANALOG_READ: result = new AnalogReadMsg(packet); break; 
			case ANALOG_WRITE: result = new AnalogWriteMsg(packet); break; 
			case TONE: result = new ToneMsg(packet); break; 
			case NO_TONE: result = new NoToneMsg(packet); break; 
			case PING: result = new PingMsg(packet); break; 
			case ATTACH_INTERRUPT: result = new AttachInterruptMsg(packet); break; 
			case DETACH_INTERRUPT: result = new DetachInterruptMsg(packet); break; 
			case EEPROM__READ: result = new Eeprom_readMsg(packet); break; 
			case EEPROM__SYNC__WRITE: result = new Eeprom_sync_writeMsg(packet); break; 
			case EEPROM__WRITE: result = new Eeprom_writeMsg(packet); break; 
			case DIGITAL_READ_RESULT: result = new DigitalReadResultMsg(packet); break; 
			case ANALOG_READ_RESULT: result = new AnalogReadResultMsg(packet); break; 
			case PONG: result = new PongMsg(packet); break; 
			case INTERRUPT_NOTIFICATION: result = new InterruptNotificationMsg(packet); break; 
			case EEPROM__VALUE: result = new Eeprom_valueMsg(packet); break; 
			case EEPROM__WRITE__ACK: result = new Eeprom_write_ackMsg(packet); break;
			case PULSE_IN_RESULT: result = new PulseInResultMsg(packet); break;
			case ULTRASSONIC: result = new UltrassonicMsg(packet); break;
			default: result = tryMapped(packetType);
		}
		return result;
	}
	
	public static final void addPacketMapping(byte code, FixedSizePacket packet) {
		MESSAGES_MAP.putIfAbsent(code, packet);
	}

	private static FixedSizePacket tryMapped(byte packetType) {
		if(MESSAGES_MAP.containsKey(packetType)) {
			return MESSAGES_MAP.get(packetType);
		}
		return null;
	}

	public static FixedSizePacket createPinMode(DigitalPin pin, PinMode mode) {
		return new PinModeMsg(pin, mode);
	}
	
	public static FixedSizePacket createDigitalRead(DigitalPin pin) {
		return new DigitalReadMsg(pin);
	}
	
	public static FixedSizePacket createDigitalWrite(DigitalPin pin, DigitalState value) {
		return new DigitalWriteMsg(pin, value);
	}
	
	public static FixedSizePacket createAnalogReference(AnalogReference type) {
		return new AnalogReferenceMsg(type);
	}
	
	public static FixedSizePacket createAnalogRead(AnalogPin pin) {
		return new AnalogReadMsg(pin);
	}
	
	public static FixedSizePacket createAnalogWrite(PWMPin pin, byte value) {
		return new AnalogWriteMsg(pin, value);
	}
	
	public static FixedSizePacket createTone(DigitalPin pin, short frequency, short duration) {
		return new ToneMsg(pin, frequency, duration);
	}
	
	public static FixedSizePacket createNoTone(DigitalPin pin) {
		return new NoToneMsg(pin);
	}

    public static FixedSizePacket createPulseIn(DigitalPin pin, DigitalState state) {
        return new PulseInMsg(pin, state);
    }

    public static FixedSizePacket createUltrassonic(DigitalPin pinOut, DigitalPin pinIn) {
        return new UltrassonicMsg(pinOut, pinIn);
    }
	
	public static FixedSizePacket createPing() {
		return new PingMsg();
	}
	
	public static FixedSizePacket createAttachInterrupt(InterruptPin interrupt, InterruptTrigger mode) {
		return new AttachInterruptMsg(interrupt, mode);
	}
	
	public static FixedSizePacket createDetachInterrupt(InterruptPin interrupt) {
		return new DetachInterruptMsg(interrupt);
	}
	
	public static FixedSizePacket createEeprom_read(short address) {
		return new Eeprom_readMsg(address);
	}
	
	public static FixedSizePacket createEeprom_sync_write(short address, byte value) {
		return new Eeprom_sync_writeMsg(address, value);
	}
	
	public static FixedSizePacket createEeprom_write(short address, byte value) {
		return new Eeprom_writeMsg(address, value);
	}
	
	
	public static FixedSizePacket createDigitalReadResult(DigitalState value) {
		return new DigitalReadResultMsg(value);
	}
	
	public static FixedSizePacket createAnalogReadResult(short value) {
		return new AnalogReadResultMsg(value);
	}
	
	public static FixedSizePacket createPong() {
		return new PongMsg();
	}
	
	public static FixedSizePacket createInterruptNotification(InterruptPin interrupt) {
		return new InterruptNotificationMsg(interrupt);
	}
	
	public static FixedSizePacket createEeprom_value(byte value) {
		return new Eeprom_valueMsg(value);
	}
	
	public static FixedSizePacket createEeprom_write_ack() {
		return new Eeprom_write_ackMsg();
	}
	
}
