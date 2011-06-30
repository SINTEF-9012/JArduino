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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.sintef.jarduino.*;
import org.sintef.jarduino.gui.panels.APanel;
import org.sintef.jarduino.gui.panels.AREFPanel;
import org.sintef.jarduino.gui.panels.AnalogPanel;
import org.sintef.jarduino.gui.panels.DigitalPanel;
import org.sintef.jarduino.gui.panels.InterruptPanel1;
import org.sintef.jarduino.gui.panels.InterruptPanel2;

public class MainPanelDuemilanove extends JComponent{

	private static final long serialVersionUID = 1L;
	private Icon arduinoImage;
	private MouseEventListener mel;
	private PopUpMenu menu;
	private InteractiveJArduinoDataGUIClientAdvanced gui;

	public MainPanelDuemilanove(InteractiveJArduinoDataControllerClientAdvanced ijadcca, InteractiveJArduinoDataGUIClientAdvanced interactiveJArduinoDataGUIClientAdvanced) {
		this.gui = interactiveJArduinoDataGUIClientAdvanced;

		menu = new PopUpMenu(ijadcca, gui);
		mel = new MouseEventListener();

		APanel panel = new AREFPanel();
		panel.setBounds(232, 43, 10, 10);
		panel.addMouseListener(mel);
		panel.restoreColor();
		add(panel);

		APanel panel_1 = new APanel();
		panel_1.setBounds(251, 43, 10, 10);
		panel_1.restoreColor();
		add(panel_1);

		APanel panel_2 = new DigitalPanel(DigitalPin.PIN_13);
		panel_2.setBounds(269, 43, 10, 10);
		panel_2.addMouseListener(mel);
		panel_2.restoreColor();
		add(panel_2);

		APanel panel_3 = new DigitalPanel(DigitalPin.PIN_12);
		panel_3.setBounds(289, 43, 10, 10);
		panel_3.addMouseListener(mel);
		panel_3.restoreColor();
		add(panel_3);

		APanel panel_4 = new PWMPanel(PWMPin.PWM_PIN_11);
		panel_4.setBounds(306, 43, 10, 10);
		panel_4.addMouseListener(mel);
		panel_4.restoreColor();
		add(panel_4);

		APanel panel_5 = new PWMPanel(PWMPin.PWM_PIN_10);
		panel_5.setBounds(326, 43, 10, 10);
		panel_5.addMouseListener(mel);
		panel_5.restoreColor();
		add(panel_5);

		APanel panel_6 = new PWMPanel(PWMPin.PWM_PIN_9);
		panel_6.setBounds(342, 43, 10, 10);
		panel_6.addMouseListener(mel);
		panel_6.restoreColor();
		add(panel_6);

		APanel panel_7 = new DigitalPanel(DigitalPin.PIN_8);
		panel_7.setBounds(360, 43, 10, 10);
		panel_7.addMouseListener(mel);
		panel_7.restoreColor();
		add(panel_7);

		APanel panel_8 = new DigitalPanel(DigitalPin.PIN_7);
		panel_8.setBounds(392, 41, 10, 10);
		panel_8.addMouseListener(mel);
		panel_8.restoreColor();
		add(panel_8);

		APanel panel_9 = new PWMPanel(PWMPin.PWM_PIN_6);
		panel_9.setBounds(410, 41, 10, 10);
		panel_9.addMouseListener(mel);
		panel_9.restoreColor();
		add(panel_9);

		APanel panel_10 = new PWMPanel(PWMPin.PWM_PIN_5);
		panel_10.setBounds(428, 41, 10, 10);
		panel_10.addMouseListener(mel);
		panel_10.restoreColor();
		add(panel_10);

		APanel panel_11 = new DigitalPanel(DigitalPin.PIN_4);
		panel_11.setBounds(448, 41, 10, 10);
		panel_11.addMouseListener(mel);
		panel_11.restoreColor();
		add(panel_11);

		APanel panel_12 = new InterruptPanel2(InterruptPin.PIN_3_INT1);
		panel_12.setBounds(468, 41, 10, 10);
		panel_12.addMouseListener(mel);
		panel_12.restoreColor();
		add(panel_12);

		APanel panel_13 = new InterruptPanel1(InterruptPin.PIN_2_INT0);
		panel_13.setBounds(488, 41, 10, 10);
		panel_13.addMouseListener(mel);
		panel_13.restoreColor();
		add(panel_13);

		APanel panel_14 = new APanel();
		panel_14.setBounds(506, 41, 10, 10);
		panel_14.restoreColor();
		add(panel_14);

		APanel panel_15 = new APanel();
		panel_15.setBounds(524, 41, 10, 10);
		panel_15.restoreColor();
		add(panel_15);

		APanel panel_16 = new APanel();
		panel_16.setBounds(300, 392, 10, 10);
		panel_16.restoreColor();
		add(panel_16);

		APanel panel_17 = new APanel();
		panel_17.setBounds(318, 392, 10, 10);
		panel_17.restoreColor();
		add(panel_17);

		APanel panel_18 = new APanel();
		panel_18.setBounds(336, 392, 10, 10);
		panel_18.restoreColor();
		add(panel_18);

		APanel panel_19 = new APanel();
		panel_19.setBounds(356, 392, 10, 10);
		panel_19.restoreColor();
		add(panel_19);

		APanel panel_20 = new APanel();
		panel_20.setBounds(374, 392, 10, 10);
		panel_20.restoreColor();
		add(panel_20);

		APanel panel_21 = new APanel();
		panel_21.setBounds(392, 392, 10, 10);
		panel_21.restoreColor();
		add(panel_21);


		APanel panel_22 = new AnalogPanel(AnalogPin.A_0);
		panel_22.setBounds(432, 392, 10, 10);
		panel_22.addMouseListener(mel);
		panel_22.restoreColor();
		add(panel_22);

		APanel panel_23 = new AnalogPanel(AnalogPin.A_1);
		panel_23.setBounds(450, 392, 10, 10);
		panel_23.addMouseListener(mel);
		panel_23.restoreColor();
		add(panel_23);

		APanel panel_24 = new AnalogPanel(AnalogPin.A_2);
		panel_24.setBounds(468, 392, 10, 10);
		panel_24.addMouseListener(mel);
		panel_24.restoreColor();
		add(panel_24);

		APanel panel_25 = new AnalogPanel(AnalogPin.A_3);
		panel_25.setBounds(488, 392, 10, 10);
		panel_25.addMouseListener(mel);
		panel_25.restoreColor();
		add(panel_25);

		APanel panel_26 = new AnalogPanel(AnalogPin.A_4);
		panel_26.setBounds(507, 392, 10, 10);
		panel_26.addMouseListener(mel);
		panel_26.restoreColor();
		add(panel_26);

		APanel panel_27 = new AnalogPanel(AnalogPin.A_5);
		panel_27.setBounds(524, 392, 10, 10);
		panel_27.addMouseListener(mel);
		panel_27.restoreColor();
		add(panel_27);
		
		String dirname="./res/images/Duemilanove.jpg";// select directory
	    File f = new File(dirname);
	    
		arduinoImage = new ImageIcon(f.getPath());
		JButton btnLegend = new JButton("<html>Color<br />Legend</html>");
		btnLegend.setBounds(10, 11, 77, 43);
		btnLegend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ColorLegend();
			}
		});
		add(btnLegend);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (arduinoImage !=null){
			arduinoImage.paintIcon(this,g,0,0);
		}
		else {
			g.drawString("Could not draw image", 0, 0);
		}
	}

	private class MouseEventListener implements MouseListener{
		private APanel label;
		@Override
		public void mouseClicked(MouseEvent arg0) {
			label = (APanel) arg0.getSource();
			doPop(arg0);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			label = (APanel) arg0.getSource();
			label.setBackground(Color.YELLOW);
			label.setOpaque();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			label = (APanel) arg0.getSource();
			label.restoreColor();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		private void doPop(MouseEvent e){
			menu.buildMenu(e);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}
