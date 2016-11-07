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
package org.sintef.jarduino.sim;

import org.sintef.jarduino.*;
import org.sintef.jarduino.observer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings({"unused"})
public class InteractiveJArduinoDataControllerClient implements ActionListener, JArduinoObserver, JArduinoClientSubject {


	private List<JArduinoClientObserver> handlers;
	
	private SimpleDateFormat dateFormat;

	public InteractiveJArduinoDataControllerClient(){
		handlers = new LinkedList<JArduinoClientObserver>();
		dateFormat = new SimpleDateFormat("dd MMM yyy 'at' HH:mm:ss.SSS");
		InteractiveJArduinoDataGUIClient.init();
		InteractiveJArduinoDataGUIClient.addListener(this);
		InteractiveJArduinoDataGUIClient.handler = this;	
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

	public final void sendpinMode() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		DigitalPin pin = null;
		PinMode mode = null;
		try{
			pin = (DigitalPin)InteractiveJArduinoDataGUIClient.getFieldpinModePin().getSelectedItem();
			mode = (PinMode)InteractiveJArduinoDataGUIClient.getFieldpinModeMode().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createPinMode(pin, mode);
			doSend(fsp);
		}
	}

	public final void senddigitalRead() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		DigitalPin pin = null;
		try{
			pin = (DigitalPin)InteractiveJArduinoDataGUIClient.getFielddigitalReadPin().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createDigitalRead(pin);
			doSend(fsp);
		}
	}

	public final void senddigitalWrite() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		DigitalPin pin = null;
		DigitalState value = null;
		try{
			pin = (DigitalPin)InteractiveJArduinoDataGUIClient.getFielddigitalWritePin().getSelectedItem();
			value = (DigitalState)InteractiveJArduinoDataGUIClient.getFielddigitalWriteValue().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createDigitalWrite(pin, value);
			doSend(fsp);
		}
	}

	public final void sendanalogReference() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		AnalogReference type = null;
		try{
			type = (AnalogReference)InteractiveJArduinoDataGUIClient.getFieldanalogReferenceType().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createAnalogReference(type);
			doSend(fsp);
		}
	}

	public final void sendanalogRead() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		AnalogPin pin = null;
		try{
			pin = (AnalogPin)InteractiveJArduinoDataGUIClient.getFieldanalogReadPin().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createAnalogRead(pin);
			doSend(fsp);
		}
	}

	public final void sendanalogWrite() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		PWMPin pin = null;
		Byte value = null;
		try{
			pin = (PWMPin)InteractiveJArduinoDataGUIClient.getFieldanalogWritePin().getSelectedItem();
			value = Byte.parseByte(InteractiveJArduinoDataGUIClient.getFieldanalogWriteValue().getText());
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createAnalogWrite(pin, value);
			doSend(fsp);
		}
	}

	public final void sendtone() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		DigitalPin pin = null;
		Short frequency = null;
		Short duration = null;
		try{
			pin = (DigitalPin)InteractiveJArduinoDataGUIClient.getFieldtonePin().getSelectedItem();
			frequency = Short.parseShort(InteractiveJArduinoDataGUIClient.getFieldtoneFrequency().getText());
			duration = Short.parseShort(InteractiveJArduinoDataGUIClient.getFieldtoneDuration().getText());
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createTone(pin, frequency, duration);
			doSend(fsp);
		}
	}

	public final void sendnoTone() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		DigitalPin pin = null;
		try{
			pin = (DigitalPin)InteractiveJArduinoDataGUIClient.getFieldnoTonePin().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createNoTone(pin);
			doSend(fsp);
		}
	}

	public final void sendping() {
		FixedSizePacket fsp = null;
		fsp = JArduinoProtocol.createPing();	
		doSend(fsp);
	}

	public final void sendattachInterrupt() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		InterruptPin interrupt = null;
		InterruptTrigger mode = null;
		try{
			interrupt = (InterruptPin)InteractiveJArduinoDataGUIClient.getFieldattachInterruptInterrupt().getSelectedItem();
			mode = (InterruptTrigger)InteractiveJArduinoDataGUIClient.getFieldattachInterruptMode().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createAttachInterrupt(interrupt, mode);
			doSend(fsp);
		}
	}

	public final void senddetachInterrupt() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		InterruptPin interrupt = null;
		try{
			interrupt = (InterruptPin)InteractiveJArduinoDataGUIClient.getFielddetachInterruptInterrupt().getSelectedItem();
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createDetachInterrupt(interrupt);
			doSend(fsp);
		}
	}

	public final void sendeeprom_read() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		Short address = null;
		try{
			address = Short.parseShort(InteractiveJArduinoDataGUIClient.getFieldeeprom_readAddress().getText());
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createEeprom_read(address);
			doSend(fsp);
		}
	}

	public final void sendeeprom_sync_write() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		Short address = null;
		Byte value = null;
		try{
			address = Short.parseShort(InteractiveJArduinoDataGUIClient.getFieldeeprom_sync_writeAddress().getText());
			value = Byte.parseByte(InteractiveJArduinoDataGUIClient.getFieldeeprom_sync_writeValue().getText());
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createEeprom_sync_write(address, value);
			doSend(fsp);
		}
	}

	public final void sendeeprom_write() {
		FixedSizePacket fsp = null;
		boolean valid = true;
		Short address = null;
		Byte value = null;
		try{
			address = Short.parseShort(InteractiveJArduinoDataGUIClient.getFieldeeprom_writeAddress().getText());
			value = Byte.parseByte(InteractiveJArduinoDataGUIClient.getFieldeeprom_writeValue().getText());
		} catch (NumberFormatException nfe){
			JOptionPane.showMessageDialog(new JFrame(), "Please check that all the inputs have the right type", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (valid){
			fsp = JArduinoProtocol.createEeprom_write(address, value);
			doSend(fsp);
		}
	}


	public final void receiveMessage(byte[] packet){
		FixedSizePacket data = JArduinoProtocol.createMessageFromPacket(packet);
		if (data != null) {
			InteractiveJArduinoDataGUIClient.print(data.getClass().getSimpleName(), "["+dateFormat.format(new Date(System.currentTimeMillis()))+"]: "+data.toString()+" --> "+FixedSizePacket.toString(packet));
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		 if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonpinMode()) {
		 	sendpinMode();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtondigitalRead()) {
		 	senddigitalRead();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtondigitalWrite()) {
		 	senddigitalWrite();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonanalogReference()) {
		 	sendanalogReference();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonanalogRead()) {
		 	sendanalogRead();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonanalogWrite()) {
		 	sendanalogWrite();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtontone()) {
		 	sendtone();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonnoTone()) {
		 	sendnoTone();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonping()) {
		 	sendping();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtonattachInterrupt()) {
		 	sendattachInterrupt();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtondetachInterrupt()) {
		 	senddetachInterrupt();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtoneeprom_read()) {
		 	sendeeprom_read();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtoneeprom_sync_write()) {
		 	sendeeprom_sync_write();
		 }
		 else if ( ae.getSource() == InteractiveJArduinoDataGUIClient.getSendButtoneeprom_write()) {
		 	sendeeprom_write();
		 }
	}
	
	public static void main(String args[]){
		InteractiveJArduinoDataControllerClient controller = new InteractiveJArduinoDataControllerClient();
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
}