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
 * Authors: Jan Ole Skotterud, Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino.gui.dialogs;

import java.awt.BorderLayout;
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
import org.sintef.jarduino.gui.panels.DigitalPanel;

public class ToneDialog extends JFrame {

	private static final long serialVersionUID = -5761442707351633726L;
	private JPanel contentPane;
	private JTextField frequency, duration;
	private DigitalPanel p;
	private InteractiveJArduinoDataGUIClientAdvanced gui;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private JButton btnSend, btnCancel;
	private Listener l;
	private JLabel lblDuration, lblFreq, label;
	
	public ToneDialog(DigitalPanel p, InteractiveJArduinoDataGUIClientAdvanced gui, InteractiveJArduinoDataControllerClientAdvanced ijadcca) {
		this.p = p;
		this.gui = gui;
		this.ijadcca = ijadcca;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 262, 165);
		
		getContentPane().setLayout(null);
		
		lblFreq = new JLabel("Frequency :");
		lblFreq.setBounds(30, 36, 76, 14);
		getContentPane().add(lblFreq);
		
		lblDuration = new JLabel("Duration :");
		lblDuration.setBounds(30, 61, 128, 14);
		getContentPane().add(lblDuration);
		
		frequency = new JTextField();
		frequency.setBounds(132, 33, 86, 20);
		getContentPane().add(frequency);
		frequency.setColumns(10);
		
		duration = new JTextField();
		duration.setBounds(132, 58, 86, 20);
		getContentPane().add(duration);
		duration.setColumns(10);
		duration.setToolTipText("Miliseconds");
		
		btnSend = new JButton("Send");
		btnSend.setBounds(25, 94, 89, 23);
		getContentPane().add(btnSend);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(129, 94, 89, 23);
		getContentPane().add(btnCancel);
	
		l = new Listener();
		
		btnSend.addActionListener(l);
		btnCancel.addActionListener(l);
		
		label = new JLabel("");
		label.setBounds(48, 13, 170, 14);
		getContentPane().add(label);
		
		label.setText("Send tone to Digital Pin #" + p.getPin().getValue());
		gui.disable();
		setVisible(true);
	}

	private class Listener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = true;
			int freq = 0, dur = 0; 
			if(e.getSource() == btnSend){
				String res = frequency.getText();
				try{
					freq = Short.parseShort(res);
					lblFreq.setForeground(Color.black);
				}catch(Exception ee){
					lblFreq.setForeground(Color.red);
					JOptionPane.showMessageDialog(new JFrame(), "Please set the frequency to a legal value", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
					ok = false;
					ee.printStackTrace();
				}
				
				res = duration.getText();
				
				try{
					dur = Short.parseShort(res);
					lblDuration.setForeground(Color.black);
				}catch(Exception ee){
					lblDuration.setForeground(Color.red);
					JOptionPane.showMessageDialog(new JFrame(), "Please set the duration to a legal value", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
					ok = false;
					ee.printStackTrace();
				}
				
				if(ok){
					closeWindow();
					ijadcca.sendtone(p.getPin(), freq, dur);
					gui.addToRepeat(p, "tone", (short) freq, (short)dur, (byte)-1);
				}
			}
			if(e.getSource() == btnCancel){
				closeWindow();
			}
		}

		private void closeWindow() {
			dispose();
			gui.enable();
		}
	}
}
