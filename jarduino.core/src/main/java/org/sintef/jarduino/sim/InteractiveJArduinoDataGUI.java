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

import org.sintef.jarduino.observer.JArduinoObserver;
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

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class InteractiveJArduinoDataGUI implements ActionListener {
	
	private static boolean init = false;
	
	private static final InteractiveJArduinoDataGUI _this = new InteractiveJArduinoDataGUI();
	
	public static InteractiveJArduinoDataController handler;
	
	private static JFrame frame;
	private static JTextPane screen;
	private static JButton clearButton;
	private static JButton logOwnEventsButton;
	
	//Attributes related to digitalReadResult
	private static JButton sendButtondigitalReadResult;
	private static JComboBox fielddigitalReadResultValue;
	
	public static JButton getSendButtondigitalReadResult() {
		return sendButtondigitalReadResult;
	}
	public static JComboBox getFielddigitalReadResultValue() {
		return fielddigitalReadResultValue;
	}
		
	//Attributes related to analogReadResult
	private static JButton sendButtonanalogReadResult;
	private static JTextField fieldanalogReadResultValue;
	
	public static JButton getSendButtonanalogReadResult() {
		return sendButtonanalogReadResult;
	}
	public static JTextField getFieldanalogReadResultValue() {
		return fieldanalogReadResultValue;
	}
		
	//Attributes related to pong
	private static JButton sendButtonpong;
	
	public static JButton getSendButtonpong() {
		return sendButtonpong;
	}
		
	//Attributes related to interruptNotification
	private static JButton sendButtoninterruptNotification;
	private static JComboBox fieldinterruptNotificationInterrupt;
	
	public static JButton getSendButtoninterruptNotification() {
		return sendButtoninterruptNotification;
	}
	public static JComboBox getFieldinterruptNotificationInterrupt() {
		return fieldinterruptNotificationInterrupt;
	}
		
	//Attributes related to eeprom_value
	private static JButton sendButtoneeprom_value;
	private static JTextField fieldeeprom_valueValue;
	
	public static JButton getSendButtoneeprom_value() {
		return sendButtoneeprom_value;
	}
	public static JTextField getFieldeeprom_valueValue() {
		return fieldeeprom_valueValue;
	}
		
	//Attributes related to eeprom_write_ack
	private static JButton sendButtoneeprom_write_ack;
	
	public static JButton getSendButtoneeprom_write_ack() {
		return sendButtoneeprom_write_ack;
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
		sendButtondigitalReadResult.addActionListener(l);
		sendButtonanalogReadResult.addActionListener(l);
		sendButtonpong.addActionListener(l);
		sendButtoninterruptNotification.addActionListener(l);
		sendButtoneeprom_value.addActionListener(l);
		sendButtoneeprom_write_ack.addActionListener(l);
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
			
			//GUI related to digitalReadResult
			c.gridy = 0;
	 		c.gridx = 0;
			frame.add(createLabel("digitalReadResult"), c);
			
			c.gridy = 1;
	 		c.gridx = 0;
			frame.add(createdigitalReadResultPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 0;
	 		sendButtondigitalReadResult = createSendButton("digitalReadResult");
			frame.add(sendButtondigitalReadResult, c);
			
			//GUI related to analogReadResult
			c.gridy = 0;
	 		c.gridx = 1;
			frame.add(createLabel("analogReadResult"), c);
			
			c.gridy = 1;
	 		c.gridx = 1;
			frame.add(createanalogReadResultPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 1;
	 		sendButtonanalogReadResult = createSendButton("analogReadResult");
			frame.add(sendButtonanalogReadResult, c);
			
			//GUI related to pong
			c.gridy = 0;
	 		c.gridx = 2;
			frame.add(createLabel("pong"), c);
			
			c.gridy = 1;
	 		c.gridx = 2;
			frame.add(createpongPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 2;
	 		sendButtonpong = createSendButton("pong");
			frame.add(sendButtonpong, c);
			
			//GUI related to interruptNotification
			c.gridy = 0;
	 		c.gridx = 3;
			frame.add(createLabel("interruptNotification"), c);
			
			c.gridy = 1;
	 		c.gridx = 3;
			frame.add(createinterruptNotificationPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 3;
	 		sendButtoninterruptNotification = createSendButton("interruptNotification");
			frame.add(sendButtoninterruptNotification, c);
			
			//GUI related to eeprom_value
			c.gridy = 0;
	 		c.gridx = 4;
			frame.add(createLabel("eeprom_value"), c);
			
			c.gridy = 1;
	 		c.gridx = 4;
			frame.add(createeeprom_valuePanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 4;
	 		sendButtoneeprom_value = createSendButton("eeprom_value");
			frame.add(sendButtoneeprom_value, c);
			
			//GUI related to eeprom_write_ack
			c.gridy = 0;
	 		c.gridx = 5;
			frame.add(createLabel("eeprom_write_ack"), c);
			
			c.gridy = 1;
	 		c.gridx = 5;
			frame.add(createeeprom_write_ackPanel(), c);
			
			c.gridy = 2;
	 		c.gridx = 5;
	 		sendButtoneeprom_write_ack = createSendButton("eeprom_write_ack");
			frame.add(sendButtoneeprom_write_ack, c);
			
						
			c.gridy = 3;
			c.gridx = 0;
			c.gridwidth = 6;
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
	
	public static JPanel createdigitalReadResultPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelvalue, c);
		DigitalState[] valuesdigitalReadResultValue = {DigitalState.LOW, DigitalState.HIGH};
		fielddigitalReadResultValue = new JComboBox(valuesdigitalReadResultValue);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fielddigitalReadResultValue, c);
			
		return panel;	
	}
	
	public static JPanel createanalogReadResultPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelvalue, c);
 		fieldanalogReadResultValue = new JTextField();
		fieldanalogReadResultValue.setText("value");
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldanalogReadResultValue, c);
			
		return panel;	
	}
	
	public static JPanel createpongPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

			
		return panel;	
	}
	
	public static JPanel createinterruptNotificationPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelinterrupt = new JLabel();
		labelinterrupt.setText("interrupt");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelinterrupt, c);
		InterruptPin[] valuesinterruptNotificationInterrupt = {InterruptPin.PIN_2_INT0, InterruptPin.PIN_3_INT1};
		fieldinterruptNotificationInterrupt = new JComboBox(valuesinterruptNotificationInterrupt);		
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldinterruptNotificationInterrupt, c);
			
		return panel;	
	}
	
	public static JPanel createeeprom_valuePanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

		JLabel labelvalue = new JLabel();
		labelvalue.setText("value");
 		c.gridx = 0;
		c.gridy = 0;
 		panel.add(labelvalue, c);
 		fieldeeprom_valueValue = new JTextField();
		fieldeeprom_valueValue.setText("value");
 		c.gridx = 1;
		c.gridy = 0;
 		panel.add(fieldeeprom_valueValue, c);
			
		return panel;	
	}
	
	public static JPanel createeeprom_write_ackPanel(){

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		
		JPanel panel = new JPanel(new GridBagLayout());

			
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
		Style receivepinModeStyle = doc.addStyle("receivepinModeStyle", def);
        StyleConstants.setForeground(receivepinModeStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivedigitalReadStyle = doc.addStyle("receivedigitalReadStyle", def);
        StyleConstants.setForeground(receivedigitalReadStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivedigitalWriteStyle = doc.addStyle("receivedigitalWriteStyle", def);
        StyleConstants.setForeground(receivedigitalWriteStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveanalogReferenceStyle = doc.addStyle("receiveanalogReferenceStyle", def);
        StyleConstants.setForeground(receiveanalogReferenceStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveanalogReadStyle = doc.addStyle("receiveanalogReadStyle", def);
        StyleConstants.setForeground(receiveanalogReadStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveanalogWriteStyle = doc.addStyle("receiveanalogWriteStyle", def);
        StyleConstants.setForeground(receiveanalogWriteStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivetoneStyle = doc.addStyle("receivetoneStyle", def);
        StyleConstants.setForeground(receivetoneStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivenoToneStyle = doc.addStyle("receivenoToneStyle", def);
        StyleConstants.setForeground(receivenoToneStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivepingStyle = doc.addStyle("receivepingStyle", def);
        StyleConstants.setForeground(receivepingStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveattachInterruptStyle = doc.addStyle("receiveattachInterruptStyle", def);
        StyleConstants.setForeground(receiveattachInterruptStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receivedetachInterruptStyle = doc.addStyle("receivedetachInterruptStyle", def);
        StyleConstants.setForeground(receivedetachInterruptStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveeeprom_readStyle = doc.addStyle("receiveeeprom_readStyle", def);
        StyleConstants.setForeground(receiveeeprom_readStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveeeprom_sync_writeStyle = doc.addStyle("receiveeeprom_sync_writeStyle", def);
        StyleConstants.setForeground(receiveeeprom_sync_writeStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
		Style receiveeeprom_writeStyle = doc.addStyle("receiveeeprom_writeStyle", def);
        StyleConstants.setForeground(receiveeeprom_writeStyle, new Color(rnd.nextInt(176), rnd.nextInt(176), rnd.nextInt(176)));	
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
				handler.register((JArduinoObserver)handler);
			} else {
				logOwnEventsButton.setText("Log Own Events (Click to Activate)");
				handler.unregister((JArduinoObserver)handler);				
			}
		}*/
	}     
}