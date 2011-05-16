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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EEPROMPanel extends JPanel {

	private static final long serialVersionUID = -7373269760859525513L;
	private JTextField readAddress;
	private JTextField writeAddress;
	private JTextField writeValue;
	private JTextField writeSyncAddress;
	private JTextField writeSyncValue;
	private JButton btnSend, btnSend_1, btnSend_2;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private ButtonListener bl;
	
	public EEPROMPanel(InteractiveJArduinoDataControllerClientAdvanced ijadcca) {
		this.ijadcca = ijadcca;
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("EEPROM"));
		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 144, 90);
		panel.setBorder(BorderFactory.createTitledBorder("Read"));
		add(panel);
		panel.setLayout(null);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(10, 28, 76, 14);
		panel.add(lblAddress);
		
		readAddress = new JTextField();
		readAddress.setBounds(83, 25, 54, 20);
		panel.add(readAddress);
		readAddress.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(10, 56, 127, 23);
		panel.add(btnSend);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 122, 144, 111);
		panel_1.setBorder(BorderFactory.createTitledBorder("Write"));
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblAddress_1 = new JLabel("Address:");
		lblAddress_1.setBounds(10, 28, 78, 14);
		panel_1.add(lblAddress_1);
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setBounds(10, 53, 78, 14);
		panel_1.add(lblValue);
		
		writeAddress = new JTextField();
		writeAddress.setBounds(82, 25, 52, 20);
		panel_1.add(writeAddress);
		writeAddress.setColumns(10);
		
		writeValue = new JTextField();
		writeValue.setBounds(82, 50, 52, 20);
		panel_1.add(writeValue);
		writeValue.setColumns(10);
		
		btnSend_1 = new JButton("Send");
		btnSend_1.setBounds(10, 78, 124, 23);
		panel_1.add(btnSend_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 255, 144, 111);
		panel_2.setBorder(BorderFactory.createTitledBorder("Synchronized Write"));
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblAddress_2 = new JLabel("Address:");
		lblAddress_2.setBounds(10, 33, 76, 14);
		panel_2.add(lblAddress_2);
		
		JLabel lblValue_1 = new JLabel("Value:");
		lblValue_1.setBounds(10, 58, 76, 14);
		panel_2.add(lblValue_1);
		
		writeSyncAddress = new JTextField();
		writeSyncAddress.setBounds(82, 30, 54, 20);
		panel_2.add(writeSyncAddress);
		writeSyncAddress.setColumns(10);
		
		writeSyncValue = new JTextField();
		writeSyncValue.setBounds(82, 55, 54, 20);
		panel_2.add(writeSyncValue);
		writeSyncValue.setColumns(10);
		
		btnSend_2 = new JButton("Send");
		btnSend_2.setBounds(10, 83, 127, 23);
		panel_2.add(btnSend_2);
		
		bl = new ButtonListener();
		btnSend.addActionListener(bl);
		btnSend_1.addActionListener(bl);
		btnSend_2.addActionListener(bl);
		setMaximumSize(new Dimension(163, 417));
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSend){
				String res = readAddress.getText();
				try{
					short s = Short.parseShort(res);
					if(s <= 255 && s >= 0){
						ijadcca.sendeeprom_read(s);
					}
				}catch(NumberFormatException nfe){
					wrongAddress();
				}
			}
			if (e.getSource() == btnSend_1){
				String res = writeAddress.getText();
				short adr = 0;
				short val = 0;
				boolean ok = true;
				try{
					adr = Short.parseShort(res);
					if(adr > 255 || adr < 0){
						ok = false;
						wrongAddress();
					}
				}catch(NumberFormatException nfe){
					ok = false;
					wrongAddress();
				}
				res = writeValue.getText();
				try{
					val = Short.parseShort(res);
					if(val > 255 || val < 0){
						ok = false;
						wrongValue();
					}
				}catch(NumberFormatException nfe){
					wrongValue();
				}
				
				if(ok){
					ijadcca.sendeeprom_write(adr, (byte)val);
				}
			}	
			if (e.getSource() == btnSend_2){
				String res = writeSyncAddress.getText();
				short adr = 0;
				short val = 0;
				boolean ok = true;
				try{
					adr = Short.parseShort(res);
					if(adr > 255 || adr < 0){
						ok = false;
						wrongAddress();
					}
				}catch(NumberFormatException nfe){
					ok = false;
					wrongAddress();
				}
				res = writeSyncValue.getText();
				try{
					val = Short.parseShort(res);
					if(val > 255 || val < 0){
						ok = false;
						wrongValue();
					}
				}catch(NumberFormatException nfe){
					wrongValue();
				}
				
				if(ok){
					ijadcca.sendeeprom_sync_write(adr, (byte)val);
				}
			}
		}

		private void wrongValue() {
			JOptionPane.showMessageDialog(new JFrame(), "Please set the value to a legal value\nLegal values are between 0 and 255", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
		}

		private void wrongAddress() {
			JOptionPane.showMessageDialog(new JFrame(), "Please set the address to a legal value\nLegal Adresses are between 0 and 255", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
		}
	}
}
