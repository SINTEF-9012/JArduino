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
 * Authors: Jan Ole Skotterud Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino.gui;

import org.sintef.jarduino.*;
import org.sintef.jarduino.comm.Serial4JArduino;
import org.sintef.jarduino.gui.dialogs.CardChooserDialog;
import org.sintef.jarduino.observer.*;

import java.util.LinkedList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InteractiveJArduinoDataControllerClientAdvanced  implements JArduinoObserver, JArduinoClientSubject {

	private List<JArduinoClientObserver> handlers;
	private SimpleDateFormat dateFormat;
	private InteractiveJArduinoDataGUIClientAdvanced gui;
	public InteractiveJArduinoDataControllerClientAdvanced(){
		handlers = new LinkedList<JArduinoClientObserver>();
		dateFormat = new SimpleDateFormat("dd MMM yyy 'at' HH:mm:ss.SSS");
		String card = CardChooserDialog.selectCard();
		if(card == null){
			System.exit(1);
		}
		gui = new InteractiveJArduinoDataGUIClientAdvanced(this, card);
	}

	private void doSend(FixedSizePacket data){
		if (data != null) {
			System.out.println(data+" --> "+data.getPacket());
			for (JArduinoClientObserver h : handlers){
				h.receiveMsg(data.getPacket());
			}
		}
		else {
			System.out.println("Data is null");
		}
	}

	public final void sendpinMode(PinMode mode, DigitalPin pin) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createPinMode(pin, mode);
		doSend(fsp);
	}

	public final void senddigitalRead(DigitalPin pin) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createDigitalRead(pin);
		doSend(fsp);
	}

	public final void senddigitalWrite(DigitalPin pin, DigitalState value) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createDigitalWrite(pin, value);
		doSend(fsp);
	}

	public final void sendanalogReference(AnalogReference type) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createAnalogReference(type);
		doSend(fsp);
	}

	public final void sendanalogRead(AnalogPin pin) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createAnalogRead(pin);
		doSend(fsp);
	}

	public final void sendanalogWrite(PWMPin pin, byte value) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createAnalogWrite(pin, value);
		doSend(fsp);
	}

	public final void sendtone(DigitalPin pin, int freq, int dur) {
		FixedSizePacket fsp = null;
		Short frequency = (short) freq;
		Short duration = (short) dur;
		fsp = JArduinoProtocol.createTone(pin, frequency, duration);
		doSend(fsp);
	}

	public final void sendnoTone(DigitalPin pin) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createNoTone(pin);
		doSend(fsp);
	}

	public final void sendping() {
		doSend(JArduinoProtocol.createPing());
	}

	public final void sendattachInterrupt(InterruptPin interrupt, InterruptTrigger mode) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createAttachInterrupt(interrupt, mode);
		doSend(fsp);
	}

	public final void senddetachInterrupt(InterruptPin interrupt) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createDetachInterrupt(interrupt);
		doSend(fsp);
	}

	public final void sendeeprom_read(Short address) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createEeprom_read(address);
		doSend(fsp);
	}

	public final void sendeeprom_sync_write(Short address, Byte value) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createEeprom_sync_write(address, value);
		doSend(fsp);
	}

	public final void sendeeprom_write(Short address ,Byte value) {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createEeprom_write(address, value);
		doSend(fsp);
	}

	public final void receiveMessage(byte[] packet){
		FixedSizePacket data = JArduinoProtocol.createMessageFromPacket(packet);
		
		if (data != null) {
			gui.writeToLog( " ["+dateFormat.format(new Date(System.currentTimeMillis()))+"]: "+data.toString()+" --> "+FixedSizePacket.toString(packet));
			//TODO Add 
		}
	}

	//Methods defined in the Observer pattern specific to JArduino 
	@Override
	public void receiveMsg(byte[] msg) {
		receiveMessage(msg);
	}

	@Override
	public void register(JArduinoClientObserver observer) {
		handlers.add(observer);
	}

	@Override
	public void unregister(JArduinoClientObserver observer) {
		handlers.remove(observer);
	}
	
	public void unregisterAll(){
		Serial4JArduino temp;
		for (int i = 0; i < handlers.size(); i++){
			temp = (Serial4JArduino) handlers.get(i);
			System.out.println("Closer " + temp);
			temp.close();
		}
		handlers.clear();
		System.out.println("Size = " + handlers.size());
	}
}
