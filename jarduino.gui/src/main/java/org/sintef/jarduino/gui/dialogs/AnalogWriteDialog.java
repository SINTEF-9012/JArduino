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
package org.sintef.jarduino.gui.dialogs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.sintef.jarduino.gui.InteractiveJArduinoDataControllerClientAdvanced;
import org.sintef.jarduino.gui.InteractiveJArduinoDataGUIClientAdvanced;
import org.sintef.jarduino.gui.PWMPanel;

public class AnalogWriteDialog extends JFrame {

	private static final long serialVersionUID = -3547739449934746980L;
	private JPanel contentPane;
	private JTextField value;
	private PWMPanel p;
	private InteractiveJArduinoDataGUIClientAdvanced gui;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private JButton btnSend, btnCancel;
	private JLabel lblAnalogValue;
	private Listener l;
	
	public AnalogWriteDialog(PWMPanel p, InteractiveJArduinoDataGUIClientAdvanced gui, InteractiveJArduinoDataControllerClientAdvanced ijadcca) {
		this.gui = gui;
		this.p = p;
		this.ijadcca = ijadcca;
		l = new Listener();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 270, 157);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Enter analog value to send to Pin #" + p.getPin().getValue());
		label.setBounds(30, 11, 195, 14);
		contentPane.add(label);
		
		lblAnalogValue = new JLabel("Analog value (0 - 255)");
		lblAnalogValue.setBounds(10, 41, 134, 14);
		contentPane.add(lblAnalogValue);
		
		value = new JTextField();
		value.setBounds(154, 38, 86, 20);
		contentPane.add(value);
		value.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(30, 74, 89, 23);
		contentPane.add(btnSend);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(139, 74, 89, 23);
		contentPane.add(btnCancel);
		
		btnCancel.addActionListener(l);
		btnSend.addActionListener(l);
		
		gui.disable();
		setVisible(true);
	}
	private class Listener implements ActionListener{
		boolean ok;
		@Override
		public void actionPerformed(ActionEvent e) {
			ok = true;
			int val = 0; 
			if(e.getSource() == btnSend){
				String res = value.getText();
				try{
					val = Short.parseShort(res);
					lblAnalogValue.setForeground(Color.black);
					if(0 > val || val > 255 ){
						wrongValue();
					}
				}catch(Exception ee){
					wrongValue();
					ee.printStackTrace();
				}
				
				if(ok){
					closeWindow();
					ijadcca.sendanalogWrite(p.getPWMPin(), (byte)val);
					p.setAnalogWrite(val);
					gui.addToRepeat(p, "analogWrite", (short) -1, (short)val, (byte)-1);
				}
			}
			if(e.getSource() == btnCancel){
				closeWindow();
			}
		}

		private void wrongValue() {
			lblAnalogValue.setForeground(Color.red);
			JOptionPane.showMessageDialog(new JFrame(), "Please set the value to a legal value", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
			ok = false;			
		}

		private void closeWindow() {
			dispose();
			gui.enable();
		}
	}
}
