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
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.PinMode;

import java.awt.Adjustable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class ReuseLogPanel extends JPanel {

	private static final long serialVersionUID = 6309150943776482481L;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private JButton btnPing,btnRepeat;
	private JList list;
	private Listener l;
	private DefaultListModel listModel;
	private HashMap<Integer, LogObject> h;
	private JScrollPane listScrollPane;

	public ReuseLogPanel(InteractiveJArduinoDataControllerClientAdvanced ijadcca) {
		this.ijadcca = ijadcca;
		h = new HashMap<Integer, LogObject>();
		listModel = new DefaultListModel();
		l = new Listener();
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 200, 360);
		add(panel);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Repeat Log"));
		
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(10, 25, 180, 294);
		panel.add(listScrollPane);
		
		btnRepeat = new JButton("Repeat");
		btnRepeat.setBounds(10, 330, 180, 23);
		panel.add(btnRepeat);
		btnRepeat.addActionListener(l);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Ping"));
		panel_1.setBounds(0, 363, 200, 54);
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
			h.put(h.size(), new LogObject(p,mode,addr,val,b));
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
				LogObject o = h.get(index);
				
				if(o.getMode().equals("input")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.sendpinMode(PinMode.INPUT, p.getPin());
				}
				if(o.getMode().equals("low") ){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.senddigitalWrite(p.getPin(), DigitalState.LOW);
				}
				if(o.getMode().equals("analogRead")){
					AnalogPanel p = (AnalogPanel) o.getP();
					ijadcca.sendanalogRead(p.getAnalogPin());
				}
				if(o.getMode().equals("tone")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.sendtone( p.getPin(), o.getAddr(), o.getVal());
				}
				if(o.getMode().equals("output")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.sendpinMode(PinMode.OUTPUT, p.getPin());
				}
				if( o.getMode().equals("digitalRead")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.senddigitalRead(p.getPin());
				}
				if(o.getMode().equals("noTone")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.sendnoTone(p.getPin());
				}
				if(o.getMode().equals("high")){
					DigitalPanel p = (DigitalPanel) o.getP();
					ijadcca.senddigitalWrite(p.getPin(), DigitalState.HIGH);
				}
				if(o.getMode().equals("analogWrite")){
					PWMPanel p = (PWMPanel) o.getP();
					ijadcca.sendanalogWrite(p.getPWMPin(), (byte)o.getVal());
				}
			}
		}
	}
}