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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.sintef.jarduino.gui.HintTextField;
import org.sintef.jarduino.gui.InteractiveJArduinoDataGUIClientAdvanced;
import org.sintef.jarduino.gui.LogObject;
import org.sintef.jarduino.gui.panels.ReuseLogPanel;


import java.awt.Font;

public class LogicalTestDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private InteractiveJArduinoDataGUIClientAdvanced ijdgca;
	private ReuseLogPanel reuseLogPanel;
	private LinkedList<LogObject> linkedList;
	private LinkedList<String> variables;
	private JComboBox comboBox, comboBox_1, comboBox_2;
	private ComboBoxFiller comboBoxFiller;
	private JPanel panel;
	private HintTextField textArea;
	private JLabel lblFirstArgument;
	private JLabel lblLogicalTest;
	private JLabel lblSecondArgument;
	private JLabel lblIfTest;
	private JButton btnOk, btnCancel;
	private ButtonListener buttonListener;
	private String testType;
	/**
	 * Create the frame.
	 * @param linkedList 
	 * @param reuseLogPanel 
	 * @param ijdgca 
	 */
	public LogicalTestDialog(InteractiveJArduinoDataGUIClientAdvanced ijdgca, ReuseLogPanel reuseLogPanel, LinkedList<LogObject> linkedList, String testType) {
		this.ijdgca = ijdgca;
		ijdgca.disable();
		this.reuseLogPanel= reuseLogPanel;
		this.linkedList = linkedList;
		this.testType = testType;
		variables = new LinkedList<String>();
		buttonListener = new ButtonListener();
		
		comboBoxFiller = new ComboBoxFiller();
		
		findVariables();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 239);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		comboBox = new JComboBox();
		comboBox.setBounds(123, 59, 142, 20);
		int i = 0;
		String s;
		comboBox.addItem("");
		while(i < variables.size()){
			s = variables.get(i);
			comboBox.addItem(s);
			i++;
		}
		comboBox.addActionListener(comboBoxFiller);
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(171, 90, 94, 20);
		comboBox_1.addActionListener(comboBoxFiller);
		contentPane.add(comboBox_1);
		
		panel = new JPanel();
		panel.setBounds(123, 121, 142, 20);
		panel.setLayout(null);
		contentPane.add(panel);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(36, 169, 89, 23);
		btnOk.addActionListener(buttonListener);
		contentPane.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(147, 169, 89, 23);
		btnCancel.addActionListener(buttonListener);
		contentPane.add(btnCancel);
		
		lblFirstArgument = new JLabel("First argument");
		lblFirstArgument.setBounds(10, 62, 103, 14);
		contentPane.add(lblFirstArgument);
		
		lblLogicalTest = new JLabel("Logical test");
		lblLogicalTest.setBounds(10, 93, 103, 14);
		contentPane.add(lblLogicalTest);
		
		lblSecondArgument = new JLabel("Second argument");
		lblSecondArgument.setBounds(10, 121, 103, 14);
		contentPane.add(lblSecondArgument);
		
		lblIfTest = new JLabel(testType + " test");
		lblIfTest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIfTest.setBounds(100, 11, 103, 37);
		contentPane.add(lblIfTest);
		
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	
	
	private void findVariables() {
		for(int i = 0; i < linkedList.size(); i++){
			if(linkedList.get(i).getMode().equals("digitalRead")){
				findVariableName("DigitalState", "digitalRead");
			}
			if(linkedList.get(i).getMode().equals("analogRead")){
				findVariableName("short", "analogRead");
			}
		}
	}


	private void findVariableName(String datatype, String method) {
		String var = method;
		if(method.equals("digitalRead")){
			if(variables.contains(var)){
				int i = 0;
				do{
					i++;
					var = method + "_" + i;
				}while(variables.contains(var));
				variables.add(var);
			}else{
				variables.add(var);
			}
		}
		
		if(method.equals("analogRead")){
			if(variables.contains(var)){
				int i = 0;
				do{
					i++;
					var = method + "_" + i;
				}while(variables.contains(var));
				variables.add(var);
			}else{
				variables.add(var);
			}
		}
	}
	

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOk){
				boolean ok = false;
				
				String type = (String) comboBox.getSelectedItem();
				String logical = (String) comboBox_1.getSelectedItem();
				if(type.startsWith("digital")){
					String test = (String) comboBox_2.getSelectedItem();
					reuseLogPanel.addLogicalTest(testType, type, logical, test);
					ok = true;
				}else{
					String temp = textArea.getText();
					try{
						int i = Integer.parseInt(temp);
						reuseLogPanel.addLogicalTest(testType, type, logical, "" + i);
						ok = true;
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(new JFrame(), "Please enter a numeric value", "Error: Invalid input", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(ok){
					ijdgca.enable();
					dispose();
				}
			}
			if(e.getSource() == btnCancel){
				ijdgca.enable();
				dispose();
			}
		}
	}
	private class ComboBoxFiller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == comboBox){
				String s = (String)comboBox.getSelectedItem();
				if(s.length() < 2){
					return;
				}
				if(s.startsWith("digital")){
					comboBox_1.removeAllItems();
					comboBox_1.addItem("==");
					comboBox_1.addItem("!=");
					comboBox_2 = new JComboBox();
					comboBox_2.addItem("HIGH");
					comboBox_2.addItem("LOW");
					try{
						panel.remove(0);
					}catch(Exception ex){
							
					}
					comboBox_2.setBounds(0, 0, 142, 20);
					panel.add(comboBox_2);
					
				}else{
					comboBox_1.removeAllItems();
					comboBox_1.addItem("==");
					comboBox_1.addItem("!=");
					comboBox_1.addItem("<");
					comboBox_1.addItem(">");
					comboBox_1.addItem("<=");
					comboBox_1.addItem(">=");
					try{
						panel.remove(0);
					}catch(Exception ex){
					
					}
					textArea = new HintTextField("Enter value");
					textArea.setBounds(0, 0, 142, 20);
					panel.add(textArea);
				}
				panel.repaint();
			}
		}
	}
}
