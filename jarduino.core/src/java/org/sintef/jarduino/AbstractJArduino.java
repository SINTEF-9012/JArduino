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
import org.sintef.jarduino.comm.*;
import org.sintef.jarduino.observer.*;

public abstract class AbstractJArduino {

	private JArduinoDriverMessageHandler messageHandler;
	private Serial4JArduino serial;

	public AbstractJArduino(String port) {
		serial = new Serial4JArduino(port);
		messageHandler = new JArduinoDriverMessageHandler();
		serial.register(messageHandler);
	}
	
	//*************************************************************************
	// Asynchronous remote calls. No expected result or ack. "Send and forget" 
	//*************************************************************************
	public void pinMode(DigitalPin pin, PinMode mode) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createpinMode(pin, mode);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void digitalWrite(DigitalPin pin, DigitalState value) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createdigitalWrite(pin, value);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void analogReference(AnalogReference type) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createanalogReference(type);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void analogWrite(PWMPin pin, byte value) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createanalogWrite(pin, value);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void tone(DigitalPin pin, short frequency, short duration) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createtone(pin, frequency, duration);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void noTone(DigitalPin pin) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createnoTone(pin);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void attachInterrupt(InterruptPin interrupt, InterruptTrigger mode) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createattachInterrupt(interrupt, mode);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void detachInterrupt(InterruptPin interrupt) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createdetachInterrupt(interrupt);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	public void eeprom_write(short address, byte value) {
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createeeprom_write(address, value);
		// Send the message on the serial line
		serial.receiveMsg(p.getPacket());
	}
	
	//*************************************************************************
	// Synchronous remote calls. Wait for the ack and return true if it comes
	//*************************************************************************
	private boolean ping_ack_available;
	private Object pingMonitor = "pingMonitor";
	public boolean ping() {
		ping_ack_available = false;
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createping();
		// Create message using the factory
		serial.receiveMsg(p.getPacket());
		try {
			synchronized(pingMonitor) {
				pingMonitor.wait(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (ping_ack_available) return true;
		else return false;
	}
	private boolean eeprom_sync_write_ack_available;
	private Object eeprom_sync_writeMonitor = "eeprom_sync_writeMonitor";
	public boolean eeprom_sync_write(short address, byte value) {
		eeprom_sync_write_ack_available = false;
		// Create message using the factory
		FixedSizePacket p = JArduinoProtocol.createeeprom_sync_write(address, value);
		// Create message using the factory
		serial.receiveMsg(p.getPacket());
		try {
			synchronized(eeprom_sync_writeMonitor) {
				eeprom_sync_writeMonitor.wait(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (eeprom_sync_write_ack_available) return true;
		else return false;
	}
	
	//*************************************************************************
	// Synchronous remote calls with result
	//*************************************************************************
	private DigitalState digitalRead_result;
	private boolean digitalRead_result_available;
	private Object digitalReadMonitor = "digitalReadMonitor";
	public DigitalState digitalRead(DigitalPin pin) {
		try {
			synchronized(digitalReadMonitor) {
				digitalRead_result_available = false;
				// Create message using the factory
				FixedSizePacket p = JArduinoProtocol.createdigitalRead(pin);
				// Create message using the factory
				serial.receiveMsg(p.getPacket());
				digitalReadMonitor.wait(500);
				if (digitalRead_result_available) return digitalRead_result;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// The exception alternative
		//throw new Error("JArduino: Timeout waiting for the result of digitalRead");
		// The error message alternative
		System.err.println("JArduino: Timeout waiting for the result of digitalRead");
		return null;
		
	}
	private short analogRead_result;
	private boolean analogRead_result_available;
	private Object analogReadMonitor = "analogReadMonitor";
	public short analogRead(AnalogPin pin) {
		try {
			synchronized(analogReadMonitor) {
				analogRead_result_available = false;
				// Create message using the factory
				FixedSizePacket p = JArduinoProtocol.createanalogRead(pin);
				// Create message using the factory
				serial.receiveMsg(p.getPacket());
				analogReadMonitor.wait(500);
				if (analogRead_result_available) return analogRead_result;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// The exception alternative
		//throw new Error("JArduino: Timeout waiting for the result of analogRead");
		// The error message alternative
		System.err.println("JArduino: Timeout waiting for the result of analogRead");
		return 0;
		
	}
	private byte eeprom_read_result;
	private boolean eeprom_read_result_available;
	private Object eeprom_readMonitor = "eeprom_readMonitor";
	public byte eeprom_read(short address) {
		try {
			synchronized(eeprom_readMonitor) {
				eeprom_read_result_available = false;
				// Create message using the factory
				FixedSizePacket p = JArduinoProtocol.createeeprom_read(address);
				// Create message using the factory
				serial.receiveMsg(p.getPacket());
				eeprom_readMonitor.wait(500);
				if (eeprom_read_result_available) return eeprom_read_result;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// The exception alternative
		//throw new Error("JArduino: Timeout waiting for the result of eeprom_read");
		// The error message alternative
		System.err.println("JArduino: Timeout waiting for the result of eeprom_read");
		return 0;
		
	}
	
	/* ******************************************************
	 * Handlers for the incoming messages
	 ********************************************************/
	/**
	 * Implement this method to handle the incoming message interruptNotification
	 */
	protected abstract void receiveinterruptNotification(InterruptPin interrupt);
	
	private class JArduinoDriverMessageHandler extends JArduinoMessageHandler implements JArduinoObserver {
		
		@Override
		// Messages from the JArduino device arrive here
		public void receiveMsg(byte[] msg) {
			JArduinoProtocolPacket p = (JArduinoProtocolPacket)JArduinoProtocol.createMessageFromPacket(msg);
			p.acceptHandler(messageHandler);
		}
		
		//*************************************************************************
		// Results of Synchronous remote calls with results
		//*************************************************************************
		@Override
		public void handledigitalReadResult(digitalReadResult msg) {
			digitalRead_result = msg.getValue();
			digitalRead_result_available = true;
			synchronized(digitalReadMonitor) {
				digitalReadMonitor.notify();
			}
		}
		@Override
		public void handleanalogReadResult(analogReadResult msg) {
			analogRead_result = msg.getValue();
			analogRead_result_available = true;
			synchronized(analogReadMonitor) {
				analogReadMonitor.notify();
			}
		}
		@Override
		public void handleeeprom_value(eeprom_value msg) {
			eeprom_read_result = msg.getValue();
			eeprom_read_result_available = true;
			synchronized(eeprom_readMonitor) {
				eeprom_readMonitor.notify();
			}
		}
		
		//*************************************************************************
		// Results of Synchronous remote calls with acks
		//*************************************************************************
		@Override
		public void handlepong(pong msg) {
			synchronized(pingMonitor) {
				ping_ack_available = true;
				pingMonitor.notify();
			}
		}
		@Override
		public void handleeeprom_write_ack(eeprom_write_ack msg) {
			synchronized(eeprom_sync_writeMonitor) {
				eeprom_sync_write_ack_available = true;
				eeprom_sync_writeMonitor.notify();
			}
		}
	
		//*************************************************************************
		// Asynchonous incoming messages
		//*************************************************************************
		@Override
		public void handleinterruptNotification(interruptNotification msg) {
			receiveinterruptNotification(msg.getInterrupt());
		}
	}
}
