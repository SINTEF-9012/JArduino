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

package org.sintef.jarduino.gui.panels;

import java.awt.Adjustable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.PinMode;
import org.sintef.jarduino.gui.InteractiveJArduinoDataControllerClientAdvanced;
import org.sintef.jarduino.gui.InteractiveJArduinoDataGUIClientAdvanced;
import org.sintef.jarduino.gui.LogObject;
import org.sintef.jarduino.gui.PWMPanel;
import org.sintef.jarduino.gui.dialogs.DelayDialog;
import org.sintef.jarduino.gui.dialogs.LogicalTestDialog;
import org.sintef.jarduino.gui.generators.GennerationHandler;

public class ReuseLogPanel extends JPanel {

	private static final long serialVersionUID = 6309150943776482481L;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private InteractiveJArduinoDataGUIClientAdvanced ijdgca;
	private JButton btnPing,btnRepeat;
	private JList list;
	private Listener l;
	private DefaultListModel listModel;
	private LinkedList<LogObject> linkedList;
	private JScrollPane listScrollPane;
	private JButton btnRepeatAll, btnDelete, btnAddConstruct, btnDeleteAll;
	private JButton btnGenerateCode;
	private CodePopupMenu codemenu;
	private ReuseLogPanel reuseLogPanel;
	private GennerationHandler gennerationHandler;
	
	public ReuseLogPanel(InteractiveJArduinoDataControllerClientAdvanced ijadcca, InteractiveJArduinoDataGUIClientAdvanced ijdgca) {
		this.ijadcca = ijadcca;
		this.ijdgca = ijdgca;
		linkedList = new LinkedList<LogObject>();
		listModel = new DefaultListModel();
		l = new Listener();
		reuseLogPanel = this;
		codemenu = new CodePopupMenu();
		setLayout(null);
		
		
		gennerationHandler = new GennerationHandler();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 200, 558);
		add(panel);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Repeat Log"));
		
		
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(10, 25, 180, 368);
		panel.add(listScrollPane);
		
		btnRepeat = new JButton("Run");
		btnRepeat.setBounds(10, 404, 180, 23);
		panel.add(btnRepeat);
		btnRepeat.setEnabled(false);
		btnRepeat.addActionListener(l);
		
		btnRepeatAll = new JButton("Run All");
		btnRepeatAll.setBounds(10, 433, 180, 23);
		panel.add(btnRepeatAll);
		btnRepeatAll.addActionListener(l);
		btnRepeatAll.setEnabled(false);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(10, 524, 89, 23);
		panel.add(btnDelete);
		btnDelete.addActionListener(l);
		btnDelete.setEnabled(false);
		
		btnDeleteAll = new JButton("Delete All");
		btnDeleteAll.setBounds(101, 524, 89, 23);
		panel.add(btnDeleteAll);
		btnDeleteAll.addActionListener(l);
		btnDeleteAll.setEnabled(false);
		
		btnAddConstruct = new JButton("Add Other Functions");
		btnAddConstruct.setBounds(10, 462, 180, 23);
		panel.add(btnAddConstruct);
		btnAddConstruct.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                codemenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
		btnGenerateCode = new JButton("Generate Code");
		btnGenerateCode.setBounds(10, 490, 180, 23);
		panel.add(btnGenerateCode);
		btnGenerateCode.setEnabled(false);
		btnGenerateCode.addActionListener(l);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 569, 200, 61);
		panel_1.setBorder(BorderFactory.createTitledBorder("Ping"));
		add(panel_1);
		panel_1.setLayout(null);
		
		btnPing = new JButton("Ping");
		
		btnPing.addActionListener(l);
		btnPing.setBounds(10, 20, 180, 23);
		panel_1.add(btnPing);
		
		list.getSelectedIndex();
	}
	public void addLogEntry(APanel p , String mode, short addr, short val, byte b){
		boolean ok = false;
		Adjustable sb = listScrollPane.getVerticalScrollBar();

		if(mode.equals("input") || mode.equals("output") || mode.equals("digitalRead") || mode.equals("noTone")){
			DigitalPanel pp = (DigitalPanel) p;
			listModel.addElement(pp + " " +mode);
			ok = true;
		}
		if(mode.equals("low") || mode.equals("high")){
			DigitalPanel pp = (DigitalPanel) p;
			listModel.addElement(pp + " DW " +mode);
			ok = true;
		}
		if(mode.equals("analogRead")){
			AnalogPanel pp = (AnalogPanel) p;
			listModel.addElement(pp + " analogRead");
			ok = true;
		}
		if(mode.equals("tone")){
			DigitalPanel pp = (DigitalPanel) p;
			listModel.addElement(pp + " F["+addr+"] " + " D["+val+"]");
			ok = true;
		}
		if(mode.equals("analogWrite")){
			PWMPanel pp = (PWMPanel) p;
			listModel.addElement(pp + " " + val);
			ok = true;
		}
		
		if(ok){
			enableButtons();
			linkedList.add(new LogObject(p,mode,addr,val,b));
			sb.setValue(sb.getMaximum());
		}
	}
	
	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnPing){
				ijadcca.sendping();
			}
			
			if(e.getSource() == btnRepeat){
				int index = list.getSelectedIndex();
				LogObject o = linkedList.get(index);
				new CommandExecuter(ijadcca).executeObjectCommand(o);
				
				
			}
			if(e.getSource() == btnRepeatAll){
				//using threads in order to get UI to update on the fly
					CommandExecuter ex = new CommandExecuter(ijadcca, linkedList, btnRepeatAll);
					ex.start();
			}
			if(e.getSource() == btnDelete){
				int index = list.getSelectedIndex();
				linkedList.remove(index);
				listModel.remove(index);
				if(linkedList.size() == 0){
					disableButtons();
				}
			}
			if(e.getSource() == btnDeleteAll){
				linkedList.removeAll(linkedList);
				listModel.removeAllElements();
				disableButtons();
			}
			if(e.getSource() == btnGenerateCode){
				
				gennerationHandler.getFile(linkedList);
			}
		}
		

	}
	
	private class CodePopupMenu extends JPopupMenu{
		private JMenuItem delay, ifX, ifelse, elseX, endif, whileX;
		private MenuListener ml;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		CodePopupMenu(){
			delay = new JMenuItem("delay()");
			ifX = new JMenuItem("if(...){");
			ifelse = new JMenuItem("}else if(...){");
			elseX = new JMenuItem("}else{");
			endif = new JMenuItem("end if/else");
			whileX = new JMenuItem("while(...){");
			
			ml = new MenuListener();
			
			delay.addActionListener(ml);
			ifX.addActionListener(ml);
			ifelse.addActionListener(ml);
			elseX.addActionListener(ml);
			endif.addActionListener(ml);
			whileX.addActionListener(ml);
			
			add(delay);
			addSeparator();
			add(ifX);
			add(ifelse);
			add(elseX);
			add(whileX);
			add(endif);
		}
		
		private class MenuListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == delay){
					new DelayDialog(ijdgca, reuseLogPanel);
				}
				if(e.getSource() == ifX){
					new LogicalTestDialog(ijdgca, reuseLogPanel, linkedList, "if");
				}
				if(e.getSource() == whileX){
					new LogicalTestDialog(ijdgca, reuseLogPanel, linkedList, "while");
				}
				if(e.getSource() == ifelse){
					new LogicalTestDialog(ijdgca, reuseLogPanel, linkedList, "else if");
				}
				if(e.getSource() == endif){
					listModel.addElement("}");
				
					Adjustable sb = listScrollPane.getVerticalScrollBar();
					linkedList.add(new LogObject(null,"}",(short)0,(short)0,(byte)0));
					sb.setValue(sb.getMaximum());
				}
				if(e.getSource() == elseX){
					Adjustable sb = listScrollPane.getVerticalScrollBar();
					linkedList.add(new LogObject(null,"}else{",(short)0,(short)0,(byte)0));
					listModel.addElement("}else{");
					sb.setValue(sb.getMaximum());
				}
			}
		}
	}

	public void setDelay(int del) {
		listModel.addElement("delay(" +del+")");
		Adjustable sb = listScrollPane.getVerticalScrollBar();
		linkedList.add(new LogObject(null,"delay",(short)0,(short)del,(byte)0));
		sb.setValue(sb.getMaximum());
		enableButtons();
	}
	private void enableButtons() {
		btnRepeat.setEnabled(true);
		btnRepeatAll.setEnabled(true);
		btnDelete.setEnabled(true);
		btnDeleteAll.setEnabled(true);
		btnGenerateCode.setEnabled(true);
	}
	private void disableButtons() {
		btnRepeat.setEnabled(false);
		btnRepeatAll.setEnabled(false);
		btnDelete.setEnabled(false);
		btnDeleteAll.setEnabled(false);
		btnGenerateCode.setEnabled(false);
		
	}
	public void addLogicalTest(String testType, String type, String logical, String test) {
		Adjustable sb = listScrollPane.getVerticalScrollBar();
		if(testType.equals("if")){
			listModel.addElement("if(" + type +"" + logical + "" + test + "){");
		}else if(testType.equals("else if")){
			listModel.addElement("}else if(" + type +"" + logical + "" + test + "){");
		}else{//while
			
			
		
			listModel.addElement("while(" + type +"" + logical + "" + test + "){");
		}
		if(test.equalsIgnoreCase("HIGH") || test.equalsIgnoreCase("LOW")){
			test = "DigitalState." + test;
		}
		linkedList.add(new LogObject(null, testType + "(" + type + " " + logical + " " + test + "){",(short)0,(short)0,(byte)0));
		sb.setValue(sb.getMaximum());
	}
}
