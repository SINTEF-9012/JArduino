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

import org.sintef.jarduino.observer.JArduinoClientObserver;
import org.sintef.jarduino.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

@SuppressWarnings({ "rawtypes", "unused", "unchecked"})
public class InteractiveJArduinoDataGUIClient implements ActionListener {
	
	private static boolean init = false;
	
	private static final InteractiveJArduinoDataGUIClient _this = new InteractiveJArduinoDataGUIClient();
	
	public static InteractiveJArduinoDataControllerClient handler;
	
	private static JFrame frame;
	private static JTextPane screen;
	private static JButton clearButton;
	private static JButton logOwnEventsButton;
	
	//Attributes related to pinMode
	private static JButton sendButtonpinMode;
	private static JComboBox fieldpinModePin;
	private static JComboBox fieldpinModeMode;
	
	public static JButton getSendButtonpinMode() {
		return sendButtonpinMode;
	}
	public static JComboBox getFieldpinModePin() {
		return fieldpinModePin;
	}
	public static JComboBox getFieldpinModeMode() {
		return fieldpinModeMode;
	}
		
	//Attributes related to digitalRead
	private static JButton sendButtondigitalRead;
	private static JComboBox fielddigitalReadPin;
	
	public static JButton getSendButtondigitalRead() {
		return sendButtondigitalRead;
	}
	public static JComboBox getFielddigitalReadPin() {
		return fielddigitalReadPin;
	}
		
	//Attributes related to digitalWrite
	private static JButton sendButtondigitalWrite;
	private static JComboBox fielddigitalWritePin;
	private static JComboBox fielddigitalWriteValue;
	
	public static JButton getSendButtondigitalWrite() {
		return sendButtondigitalWrite;
	}
	public static JComboBox getFielddigitalWritePin() {
		return fielddigitalWritePin;
	}
	public static JComboBox getFielddigitalWriteValue() {
		return fielddigitalWriteValue;
	}
		
	//Attributes related to analogReference
	private static JButton sendButtonanalogReference;
	private static JComboBox fieldanalogReferenceType;
	
	public static JButton getSendButtonanalogReference() {
		return sendButtonanalogReference;
	}
	public static JComboBox getFieldanalogReferenceType() {
		return fieldanalogReferenceType;
	}
		
	//Attributes related to analogRead
	private static JButton sendButtonanalogRead;
	private static JComboBox fieldanalogReadPin;
	
	public static JButton getSendButtonanalogRead() {
		return sendButtonanalogRead;
	}
	public static JComboBox getFieldanalogReadPin() {
		return fieldanalogReadPin;
	}
		
	//Attributes related to analogWrite
	private static JButton sendButtonanalogWrite;
	private static JComboBox fieldanalogWritePin;
	private static JTextField fieldanalogWriteValue;
	
	public static JButton getSendButtonanalogWrite() {
		return sendButtonanalogWrite;
	}
	public static JComboBox getFieldanalogWritePin() {
		return fieldanalogWritePin;
	}
	public static JTextField getFieldanalogWriteValue() {
		return fieldanalogWriteValue;
	}
		
	//Attributes related to tone
	private static JButton sendButtontone;
	private static JComboBox fieldtonePin;
	private static JTextField fieldtoneFrequency;
	private static JTextField fieldtoneDuration;
	
	public static JButton getSendButtontone() {
		return sendButtontone;
	}
	public static JComboBox getFieldtonePin() {
		return fieldtonePin;
	}
	public static JTextField getFieldtoneFrequency() {
		return fieldtoneFrequency;
	}
	public static JTextField getFieldtoneDuration() {
		return fieldtoneDuration;
	}
		
	//Attributes related to noTone
	private static JButton sendButtonnoTone;
	private static JComboBox fieldnoTonePin;
	
	public static JButton getSendButtonnoTone() {
		return sendButtonnoTone;
	}
	public static JComboBox getFieldnoTonePin() {
		return fieldnoTonePin;
	}
		
	//Attributes related to ping
	private static JButton sendButtonping;
	
	public static JButton getSendButtonping() {
		return sendButtonping;
	}
		
	//Attributes related to attachInterrupt
	private static JButton sendButtonattachInterrupt;
	private static JComboBox fieldattachInterruptInterrupt;
	private static JComboBox fieldattachInterruptMode;
	
	public static JButton getSendButtonattachInterrupt() {
		return sendButtonattachInterrupt;
	}
	public static JComboBox getFieldattachInterruptInterrupt() {
		return fieldattachInterruptInterrupt;
	}
	public static JComboBox getFieldattachInterruptMode() {
		return fieldattachInterruptMode;
	}
		
	//Attributes related to detachInterrupt
	private static JButton sendButtondetachInterrupt;
	private static JComboBox fielddetachInterruptInterrupt;
	
	public static JButton getSendButtondetachInterrupt() {
		return sendButtondetachInterrupt;
	}
	public static JComboBox getFielddetachInterruptInterrupt() {
		return fielddetachInterruptInterrupt;
	}
		
	//Attributes related to eeprom_read
	private static JButton sendButtoneeprom_read;
	private static JTextField fieldeeprom_readAddress;
	
	public static JButton getSendButtoneeprom_read() {
		return sendButtoneeprom_read;
	}
	public static JTextField getFieldeeprom_readAddress() {
		return fieldeeprom_readAddress;
	}
		
	//Attributes related to eeprom_sync_write
	private static JButton sendButtoneeprom_sync_write;
	private static JTextField fieldeeprom_sync_writeAddress;
	private static JTextField fieldeeprom_sync_writeValue;
	
	public static JButton getSendButtoneeprom_sync_write() {
		return sendButtoneeprom_sync_write;
	}
	public static JTextField getFieldeeprom_sync_writeAddress() {
		return fieldeeprom_sync_writeAddress;
	}
	public static JTextField getFieldeeprom_sync_writeValue() {
		return fieldeeprom_sync_writeValue;
	}
		
	//Attributes related to eeprom_write
	private static JButton sendButtoneeprom_write;
	private static JTextField fieldeeprom_writeAddress;
	private static JTextField fieldeeprom_writeValue;
	
	public static JButton getSendButtoneeprom_write() {
		return sendButtoneeprom_write;
	}
	public static JTextField getFieldeeprom_writeAddress() {
		return fieldeeprom_writeAddress;
	}
	public static JTextField getFieldeeprom_writeValue() {
		return fieldeeprom_writeValue;
	}
		

	public static void print(String id, String data){
		try {
        	StyledDocument doc = screen.getStyledDocument();
            doc.insertString(doc.getLength(), formatForPrint(data), doc.getStyle("receive"+id+"Style"));
            screen.setCaretPosition(doc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
	}
		
	public static void addListener(ActionListener l){
		sendButtonpinMode.addActionListener(l);
		sendButtondigitalRead.addActionListener(l);
		sendButtondigitalWrite.addActionListener(l);
		sendButtonanalogReference.addActionListener(l);
		sendButtonanalogRead.addActionListener(l);
		sendButtonanalogWrite.addActionListener(l);
		sendButtontone.addActionListener(l);
		sendButtonnoTone.addActionListener(l);
		sendButtonping.addActionListener(l);
		sendButtonattachInterrupt.addActionListener(l);
		sendButtondetachInterrupt.addActionListener(l);
		sendButtoneeprom_read.addActionListener(l);
		sendButtoneeprom_sync_write.addActionListener(l);
		sendButtoneeprom_write.addActionListener(l);
	}
	
	public static void init(){
		if (!init) {
			init = true;
			
			clearButton = new JButton("Clear Console");
			logOwnEventsButton = new JButton("Log Own Events (Click to Activate)");
			frame = new JFrame("Interactive JArduino Data Simulator");
			frame.setLayout(new GridBagLayout());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			GridBagConstraints c = new GridBagConstraints();
			c.gridwidth = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0,3,0,3);
			
			//GUI related to pinMode
			c.gridy = 0;
	 		c.gridx = 0;
			frame.add(createLabel("pinMode"), c);
			
			c.gridy = 1;
	 		c.gridx = 0;
			frame.add(createpinModePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 0;
	 		sendButtonpinMode = createSendButton("pinMode");
			frame.add(sendButtonpinMode, c);
			
			//GUI related to digitalRead
			c.gridy = 0;
	 		c.gridx = 1;
			frame.add(createLabel("digitalRead"), c);
			
			c.gridy = 1;
	 		c.gridx = 1;
			frame.add(createdigitalReadPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 1;
	 		sendButtondigitalRead = createSendButton("digitalRead");
			frame.add(sendButtondigitalRead, c);
			
			//GUI related to digitalWrite
			c.gridy = 0;
	 		c.gridx = 2;
			frame.add(createLabel("digitalWrite"), c);
			
			c.gridy = 1;
	 		c.gridx = 2;
			frame.add(createdigitalWritePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 2;
	 		sendButtondigitalWrite = createSendButton("digitalWrite");
			frame.add(sendButtondigitalWrite, c);
			
			//GUI related to analogReference
			c.gridy = 0;
	 		c.gridx = 3;
			frame.add(createLabel("analogReference"), c);
			
			c.gridy = 1;
	 		c.gridx = 3;
			frame.add(createanalogReferencePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 3;
	 		sendButtonanalogReference = createSendButton("analogReference");
			frame.add(sendButtonanalogReference, c);
			
			//GUI related to analogRead
			c.gridy = 0;
	 		c.gridx = 4;
			frame.add(createLabel("analogRead"), c);
			
			c.gridy = 1;
	 		c.gridx = 4;
			frame.add(createanalogReadPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 4;
	 		sendButtonanalogRead = createSendButton("analogRead");
			frame.add(sendButtonanalogRead, c);
			
			//GUI related to analogWrite
			c.gridy = 0;
	 		c.gridx = 5;
			frame.add(createLabel("analogWrite"), c);
			
			c.gridy = 1;
	 		c.gridx = 5;
			frame.add(createanalogWritePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 5;
	 		sendButtonanalogWrite = createSendButton("analogWrite");
			frame.add(sendButtonanalogWrite, c);
			
			//GUI related to tone
			c.gridy = 0;
	 		c.gridx = 6;
			frame.add(createLabel("tone"), c);
			
			c.gridy = 1;
	 		c.gridx = 6;
			frame.add(createtonePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 6;
	 		sendButtontone = createSendButton("tone");
			frame.add(sendButtontone, c);
			
			//GUI related to noTone
			c.gridy = 0;
	 		c.gridx = 7;
			frame.add(createLabel("noTone"), c);
			
			c.gridy = 1;
	 		c.gridx = 7;
			frame.add(createnoTonePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 7;
	 		sendButtonnoTone = createSendButton("noTone");
			frame.add(sendButtonnoTone, c);
			
			//GUI related to ping
			c.gridy = 0;
	 		c.gridx = 8;
			frame.add(createLabel("ping"), c);
			
			c.gridy = 1;
	 		c.gridx = 8;
			frame.add(createpingPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 8;
	 		sendButtonping = createSendButton("ping");
			frame.add(sendButtonping, c);
			
			//GUI related to attachInterrupt
			c.gridy = 0;
	 		c.gridx = 9;
			frame.add(createLabel("attachInterrupt"), c);
			
			c.gridy = 1;
	 		c.gridx = 9;
			frame.add(createattachInterruptPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 9;
	 		sendButtonattachInterrupt = createSendButton("attachInterrupt");
			frame.add(sendButtonattachInterrupt, c);
			
			//GUI related to detachInterrupt
			c.gridy = 0;
	 		c.gridx = 10;
			frame.add(createLabel("detachInterrupt"), c);
			
			c.gridy = 1;
	 		c.gridx = 10;
			frame.add(createdetachInterruptPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 10;
	 		sendButtondetachInterrupt = createSendButton("detachInterrupt");
			frame.add(sendButtondetachInterrupt, c);
			
			//GUI related to eeprom_read
			c.gridy = 0;
	 		c.gridx = 11;
			frame.add(createLabel("eeprom_read"), c);
			
			c.gridy = 1;
	 		c.gridx = 11;
			frame.add(createeeprom_readPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 11;
	 		sendButtoneeprom_read = createSendButton("eeprom_read");
			frame.add(sendButtoneeprom_read, c);
			
			//GUI related to eeprom_sync_write
			c.gridy = 0;
	 		c.gridx = 12;
			frame.add(createLabel("eeprom_sync_write"), c);
			
			c.gridy = 1;
	 		c.gridx = 12;
			frame.add(createeeprom_sync_writePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 12;
	 		sendButtoneeprom_sync_write = createSendButton("eeprom_sync_write");
			frame.add(sendButtoneeprom_sync_write, c);
			
			//GUI related to eeprom_write
			c.gridy = 0;
	 		c.gridx = 13;
			frame.add(createLabel("eeprom_write"), c);
			
			c.gridy = 1;
	 		c.gridx = 13;
			frame.add(createeeprom_writePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 13;
	 		sendButtoneeprom_write = createSendButton("eeprom_write");
			frame.add(sendButtoneeprom_write, c);
			
						
			c.gridy = 3;
			c.gridx = 0;
			c.gridwidth = 14;
			frame.add(createJTextPane(), c);
			
			c.gridy = 4;
			frame.add(clearButton, c);
			
			c.gridy = 5;
			frame.add(logOwnEventsButton, c);
			
			frame.pack();
			clearButton.addActionListener(_this);
			logOwnEventsButton.addActionListener(_this);
			frame.setVisible(true);
		}
	}
	
	public static JLabel createLabel(String name){
		return new JLabel(name);
	}
	
	public static JButton createSendButton(String name){
		return new JButton("send");
	}
	
	public static JPanel createpinModePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		DigitalPin[] valuespinModePin = {DigitalPin.PIN_0, DigitalPin.PIN_1, DigitalPin.PIN_2, DigitalPin.PIN_3, DigitalPin.PIN_4, DigitalPin.PIN_5, DigitalPin.PIN_6, DigitalPin.PIN_7, DigitalPin.PIN_8, DigitalPin.PIN_9, DigitalPin.PIN_10, DigitalPin.PIN_11, DigitalPin.PIN_12, DigitalPin.PIN_13, DigitalPin.A_0, DigitalPin.A_1, DigitalPin.A_2, DigitalPin.A_3, DigitalPin.A_4, DigitalPin.A_5};
		fieldpinModePin = new JComboBox(valuespinModePin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldpinModePin, c);
		JLabel labelmode = new JLabel();
		labelmode.setText("mode");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelmode, c);
		PinMode[] valuespinModeMode = {PinMode.INPUT, PinMode.OUTPUT};
		fieldpinModeMode = new JComboBox(valuespinModeMode);		
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldpinModeMode, c);
			
		return panel;	
	}
	
	public static JPanel createdigitalReadPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		DigitalPin[] valuesdigitalReadPin = {DigitalPin.PIN_0, DigitalPin.PIN_1, DigitalPin.PIN_2, DigitalPin.PIN_3, DigitalPin.PIN_4, DigitalPin.PIN_5, DigitalPin.PIN_6, DigitalPin.PIN_7, DigitalPin.PIN_8, DigitalPin.PIN_9, DigitalPin.PIN_10, DigitalPin.PIN_11, DigitalPin.PIN_12, DigitalPin.PIN_13, DigitalPin.A_0, DigitalPin.A_1, DigitalPin.A_2, DigitalPin.A_3, DigitalPin.A_4, DigitalPin.A_5};
		fielddigitalReadPin = new JComboBox(valuesdigitalReadPin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fielddigitalReadPin, c);
			
		return panel;	
	}
	
	public static JPanel createdigitalWritePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		DigitalPin[] valuesdigitalWritePin = {DigitalPin.PIN_0, DigitalPin.PIN_1, DigitalPin.PIN_2, DigitalPin.PIN_3, DigitalPin.PIN_4, DigitalPin.PIN_5, DigitalPin.PIN_6, DigitalPin.PIN_7, DigitalPin.PIN_8, DigitalPin.PIN_9, DigitalPin.PIN_10, DigitalPin.PIN_11, DigitalPin.PIN_12, DigitalPin.PIN_13, DigitalPin.A_0, DigitalPin.A_1, DigitalPin.A_2, DigitalPin.A_3, DigitalPin.A_4, DigitalPin.A_5};
		fielddigitalWritePin = new JComboBox(valuesdigitalWritePin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fielddigitalWritePin, c);
		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelvalue, c);
		DigitalState[] valuesdigitalWriteValue = {DigitalState.LOW, DigitalState.HIGH};
		fielddigitalWriteValue = new JComboBox(valuesdigitalWriteValue);		
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fielddigitalWriteValue, c);
			
		return panel;	
	}
	
	public static JPanel createanalogReferencePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labeltype = new JLabel();
		labeltype.setText("type");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labeltype, c);
		AnalogReference[] valuesanalogReferenceType = {AnalogReference.DEFAULT, AnalogReference.INTERNAL, AnalogReference.EXTERNAL};
		fieldanalogReferenceType = new JComboBox(valuesanalogReferenceType);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldanalogReferenceType, c);
			
		return panel;	
	}
	
	public static JPanel createanalogReadPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		AnalogPin[] valuesanalogReadPin = {AnalogPin.A_0, AnalogPin.A_1, AnalogPin.A_2, AnalogPin.A_3, AnalogPin.A_4, AnalogPin.A_5};
		fieldanalogReadPin = new JComboBox(valuesanalogReadPin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldanalogReadPin, c);
			
		return panel;	
	}
	
	public static JPanel createanalogWritePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		PWMPin[] valuesanalogWritePin = {PWMPin.PWM_PIN_3, PWMPin.PWM_PIN_5, PWMPin.PWM_PIN_6, PWMPin.PWM_PIN_9, PWMPin.PWM_PIN_10, PWMPin.PWM_PIN_11};
		fieldanalogWritePin = new JComboBox(valuesanalogWritePin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldanalogWritePin, c);
		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelvalue, c);
 		fieldanalogWriteValue = new JTextField();
		fieldanalogWriteValue.setText("value");
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldanalogWriteValue, c);
			
		return panel;	
	}
	
	public static JPanel createtonePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		DigitalPin[] valuestonePin = {DigitalPin.PIN_0, DigitalPin.PIN_1, DigitalPin.PIN_2, DigitalPin.PIN_3, DigitalPin.PIN_4, DigitalPin.PIN_5, DigitalPin.PIN_6, DigitalPin.PIN_7, DigitalPin.PIN_8, DigitalPin.PIN_9, DigitalPin.PIN_10, DigitalPin.PIN_11, DigitalPin.PIN_12, DigitalPin.PIN_13, DigitalPin.A_0, DigitalPin.A_1, DigitalPin.A_2, DigitalPin.A_3, DigitalPin.A_4, DigitalPin.A_5};
		fieldtonePin = new JComboBox(valuestonePin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldtonePin, c);
		JLabel labelfrequency = new JLabel();
		labelfrequency.setText("frequency");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelfrequency, c);
 		fieldtoneFrequency = new JTextField();
		fieldtoneFrequency.setText("frequency");
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldtoneFrequency, c);
		JLabel labelduration = new JLabel();
		labelduration.setText("duration");
 		c.gridx = 0;
		c.gridy = 2;
 		panel.add(labelduration, c);
 		fieldtoneDuration = new JTextField();
		fieldtoneDuration.setText("duration");
 		c.gridx = 1;
		c.gridy = 2;
 		panel.add(fieldtoneDuration, c);
			
		return panel;	
	}
	
	public static JPanel createnoTonePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelpin = new JLabel();
		labelpin.setText("pin");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelpin, c);
		DigitalPin[] valuesnoTonePin = {DigitalPin.PIN_0, DigitalPin.PIN_1, DigitalPin.PIN_2, DigitalPin.PIN_3, DigitalPin.PIN_4, DigitalPin.PIN_5, DigitalPin.PIN_6, DigitalPin.PIN_7, DigitalPin.PIN_8, DigitalPin.PIN_9, DigitalPin.PIN_10, DigitalPin.PIN_11, DigitalPin.PIN_12, DigitalPin.PIN_13, DigitalPin.A_0, DigitalPin.A_1, DigitalPin.A_2, DigitalPin.A_3, DigitalPin.A_4, DigitalPin.A_5};
		fieldnoTonePin = new JComboBox(valuesnoTonePin);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldnoTonePin, c);
			
		return panel;	
	}
	
	public static JPanel createpingPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

			
		return panel;	
	}
	
	public static JPanel createattachInterruptPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelinterrupt = new JLabel();
		labelinterrupt.setText("interrupt");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelinterrupt, c);
		InterruptPin[] valuesattachInterruptInterrupt = {InterruptPin.PIN_2_INT0, InterruptPin.PIN_3_INT1};
		fieldattachInterruptInterrupt = new JComboBox(valuesattachInterruptInterrupt);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldattachInterruptInterrupt, c);
		JLabel labelmode = new JLabel();
		labelmode.setText("mode");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelmode, c);
		InterruptTrigger[] valuesattachInterruptMode = {InterruptTrigger.CHANGE, InterruptTrigger.RISING, InterruptTrigger.FALLING, InterruptTrigger.LOW};
		fieldattachInterruptMode = new JComboBox(valuesattachInterruptMode);		
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldattachInterruptMode, c);
			
		return panel;	
	}
	
	public static JPanel createdetachInterruptPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelinterrupt = new JLabel();
		labelinterrupt.setText("interrupt");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelinterrupt, c);
		InterruptPin[] valuesdetachInterruptInterrupt = {InterruptPin.PIN_2_INT0, InterruptPin.PIN_3_INT1};
		fielddetachInterruptInterrupt = new JComboBox(valuesdetachInterruptInterrupt);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fielddetachInterruptInterrupt, c);
			
		return panel;	
	}
	
	public static JPanel createeeprom_readPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labeladdress = new JLabel();
		labeladdress.setText("address");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labeladdress, c);
 		fieldeeprom_readAddress = new JTextField();
		fieldeeprom_readAddress.setText("address");
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldeeprom_readAddress, c);
			
		return panel;	
	}
	
	public static JPanel createeeprom_sync_writePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labeladdress = new JLabel();
		labeladdress.setText("address");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labeladdress, c);
 		fieldeeprom_sync_writeAddress = new JTextField();
		fieldeeprom_sync_writeAddress.setText("address");
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldeeprom_sync_writeAddress, c);
		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelvalue, c);
 		fieldeeprom_sync_writeValue = new JTextField();
		fieldeeprom_sync_writeValue.setText("value");
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldeeprom_sync_writeValue, c);
			
		return panel;	
	}
	
	public static JPanel createeeprom_writePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labeladdress = new JLabel();
		labeladdress.setText("address");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labeladdress, c);
 		fieldeeprom_writeAddress = new JTextField();
		fieldeeprom_writeAddress.setText("address");
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldeeprom_writeAddress, c);
		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 1;
 		panel.add(labelvalue, c);
 		fieldeeprom_writeValue = new JTextField();
		fieldeeprom_writeValue.setText("value");
 		c.gridx = 1;
		c.gridy = 1;
 		panel.add(fieldeeprom_writeValue, c);
			
		return panel;	
	}
	
	
		
	public static JScrollPane createJTextPane(){
		screen = new JTextPane();
        screen.setFocusable(false);
        screen.setEditable(false);
        screen.setAutoscrolls(true);
  
  		JScrollPane editorScrollPane = new JScrollPane(screen);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(480, 240));
		editorScrollPane.setMinimumSize(new Dimension(320, 160));
        
        StyledDocument doc = screen.getStyledDocument();
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
            
        Random rnd = new Random();
		Style receivedigitalReadResultStyle = doc.addStyle("receivedigitalReadResultStyle", def);
        StyleConstants.setForeground(receivedigitalReadResultStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveanalogReadResultStyle = doc.addStyle("receiveanalogReadResultStyle", def);
        StyleConstants.setForeground(receiveanalogReadResultStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivepongStyle = doc.addStyle("receivepongStyle", def);
        StyleConstants.setForeground(receivepongStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveinterruptNotificationStyle = doc.addStyle("receiveinterruptNotificationStyle", def);
        StyleConstants.setForeground(receiveinterruptNotificationStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveeeprom_valueStyle = doc.addStyle("receiveeeprom_valueStyle", def);
        StyleConstants.setForeground(receiveeeprom_valueStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveeeprom_write_ackStyle = doc.addStyle("receiveeeprom_write_ackStyle", def);
        StyleConstants.setForeground(receiveeeprom_write_ackStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
        return editorScrollPane;
	}
	
	private static String formatForPrint(String text) {
            return (text.endsWith("\n") ? text : text + "\n");
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == clearButton){
			screen.setText("");
		} /*else if (ae.getSource() == logOwnEventsButton) {
			if (logOwnEventsButton.getText().equals("Log Own Events (Click to Activate)")){
				logOwnEventsButton.setText("Log Own Events (Click to De-activate)");
				handler.register((JArduinoClientObserver)handler);
			} else {
				logOwnEventsButton.setText("Log Own Events (Click to Activate)");
				handler.unregister((JArduinoClientObserver)handler);				
			}
		}*/
	}     
}