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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;

import org.sintef.jarduino.comm.Serial4JArduino;
import org.sintef.jarduino.gui.dialogs.CardChooserDialog;
import org.sintef.jarduino.gui.panels.APanel;
import org.sintef.jarduino.gui.panels.EEPROMPanel;
import org.sintef.jarduino.gui.panels.LogPanel;
import org.sintef.jarduino.gui.panels.ReuseLogPanel;

public class InteractiveJArduinoDataGUIClientAdvanced {

	private JFrame frame;
	private JComponent comp;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private JPanel reuseLogPanel;
	private JPanel eepromPanel, logPanel;
	private JMenuBar menuBar;
	private JMenu file, options, helpMenu;
	private JMenuItem changeCard, changePort, help, about, exit;
	private MenuListener ml;
	
	public InteractiveJArduinoDataGUIClientAdvanced(InteractiveJArduinoDataControllerClientAdvanced interactiveJArduinoDataControllerClientAdvanced, String card){
		this.ijadcca = interactiveJArduinoDataControllerClientAdvanced;
		frame = new JFrame("Interactive Arduino data simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

		frame.getContentPane().setLayout(null);
		
		comp = getCardPanel(card);
		
		reuseLogPanel = new ReuseLogPanel(ijadcca, this);
		reuseLogPanel.setBounds(0, 0, 210, 851);
		frame.getContentPane().add(reuseLogPanel);
		
		comp.setBounds(210, 0, 652, 600);
		frame.getContentPane().add(comp);
		
		eepromPanel = new EEPROMPanel(ijadcca);
		eepromPanel.setBounds(802, 0, 965, 417);
		frame.getContentPane().add(eepromPanel);
		  
		logPanel = new LogPanel();
		logPanel.setBounds(211, 418, 759, 212);
		frame.getContentPane().add(logPanel);
		
		buildMenu();
		
		frame.setMinimumSize(new Dimension(993, 700));
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	
	private JComponent getCardPanel(String ca) {
		JComponent c = null;
		if(ca == null){
			ca = "Uno";
		}
		if(ca.equals("Uno")){
			c = new MainPanelUno(ijadcca, this);
		}else if(ca.equals("Diecimila")){
			c = new MainPanelDiecimila(ijadcca, this);
		}else if(ca.equals("Lilypad")){
			c = new MainPanelLilypad(ijadcca, this);
		}else if(ca.equals("Duemilanove")){
			c = new MainPanelDuemilanove(ijadcca, this);
		}
		return c;
	}

	private void buildMenu() {
		ml = new MenuListener();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		options = new JMenu("Options");
		helpMenu = new JMenu("Help");
		
		exit = new JMenuItem("Exit");
		changeCard = new JMenuItem("Change Board");
		changePort = new JMenuItem("Change Com Port");
		help = new JMenuItem("Help");
		about = new JMenuItem("About");
		
		file.add(exit);

		options.add(changeCard);
		options.add(changePort);
		
		helpMenu.add(help);
		helpMenu.add(about);
		
		menuBar.add(file);
		menuBar.add(options);
		menuBar.add(helpMenu);
		
		frame.setJMenuBar(menuBar);
		
		changeCard.addActionListener(ml);
		changePort.addActionListener(ml);
		exit.addActionListener(ml);
		about.addActionListener(ml);
		help.addActionListener(ml);
	}

	public void writeToLog(String string){
		((LogPanel) logPanel).writeToLog(string);
	}
	public void enable(){
		frame.setEnabled(true);
		frame.toFront();
	}

	public void disable() {
		frame.setEnabled(false);
	}

	public void addToRepeat(APanel panel, String input, short i, short j, byte k) {
		((ReuseLogPanel) reuseLogPanel).addLogEntry(panel, input, i, j, k);
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == exit){
				System.exit(0);
			}
			if(e.getSource() == changeCard){
				String s = CardChooserDialog.selectCard();
				if(s != null){
					frame.getContentPane().remove(comp);
					comp = null;
					comp = getCardPanel(s);
					comp.setBounds(210, 0, 652, 600);
					frame.getContentPane().add(comp);
					frame.getContentPane().repaint();
				}
			}
			if(e.getSource() == changePort){
				String serialPort = Serial4JArduino.selectSerialPort();
				if(serialPort != null){
					ijadcca.unregisterAll();
					Serial4JArduino t = new Serial4JArduino(serialPort);
					t.register(ijadcca);
					ijadcca.register(t);
				}
			}
			if(e.getSource() == about){
				JOptionPane.showMessageDialog(frame, "This application is developed and released under GNU LESSER\n" +
													 "GENERAL PUBLIC LICENSE, Version 3, 29 June 2007.\n" +
													 "The application is intended to be used to test and debug your\n" +
													 "Arduino card before writing your own code.\n" +
													 "This Grapichal user interface is created by Jan Ole Skotterud and\n" +
													 "is buildt upon the JArduino libraries created by Brice Morin and Franck Fleurey\n" +
													 "for SINTEF. ");
			}
			if(e.getSource() == help){
				JOptionPane.showMessageDialog(frame, "To use this application: Click on preffered port and select the action\n" +
													 "you want to perform. For example, click on digital pin 13 and select pinmode->output.\n" +
													 "Then click on digital pin 13 again and select digital write->high.\n" +
													 "Now you can se that the LED on your Arduino board is lighting up.\n" +
													 "By using the same approach you can read both analog and digital values, send\n" +
													 "tones, attach interrupts and so on. You can basicaly do almoast everything that you\n" +
													 "can program your Arduino to do.");
			}
		}
	}


}
