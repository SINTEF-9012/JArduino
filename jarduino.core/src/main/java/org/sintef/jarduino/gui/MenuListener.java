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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.sintef.jarduino.AnalogReference;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.InterruptPin;
import org.sintef.jarduino.InterruptTrigger;
import org.sintef.jarduino.PinMode;

class MenuListener implements ActionListener{

	private APanel panel;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private InteractiveJArduinoDataGUIClientAdvanced gui;

	public MenuListener(InteractiveJArduinoDataControllerClientAdvanced ijadcca, InteractiveJArduinoDataGUIClientAdvanced gui){
		this.gui = gui;
		this.ijadcca = ijadcca;
	}

	public void setPanel(APanel panel){
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == PopUpMenu.input){
			DigitalPanel p = (DigitalPanel) panel;
			ijadcca.sendpinMode(PinMode.INPUT, p.getPin());
			gui.addToRepeat(panel, "input", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.output){
			DigitalPanel p = (DigitalPanel) panel;
			ijadcca.sendpinMode(PinMode.OUTPUT, p.getPin());
			gui.addToRepeat(panel, "output", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.high){
			DigitalPanel p = (DigitalPanel) panel;
			//ijadcca.sendpinMode(PinMode.OUTPUT, p.getPin());
			ijadcca.senddigitalWrite(p.getPin(), DigitalState.HIGH);
			System.out.println("Skal ha digital pin " + p);
			gui.addToRepeat(panel, "high", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.low){
			DigitalPanel p = (DigitalPanel) panel;
			//ijadcca.sendpinMode(PinMode.OUTPUT, p.getPin());
			ijadcca.senddigitalWrite(p.getPin(), DigitalState.LOW);
			gui.addToRepeat(panel, "low", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.analogWrite){
			PWMPanel p = (PWMPanel) panel;
			new AnalogWriteDialog(p, gui, ijadcca);
		}
		if(e.getSource() == PopUpMenu.change){
			attachInterrupt(InterruptTrigger.CHANGE, panel);
			gui.addToRepeat(panel, "change", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.rising){
			attachInterrupt(InterruptTrigger.RISING, panel);
			gui.addToRepeat(panel, "rising", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.falling){
			attachInterrupt(InterruptTrigger.FALLING, panel);	
			gui.addToRepeat(panel, "falling", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.lowI){
			attachInterrupt(InterruptTrigger.LOW, panel);
			gui.addToRepeat(panel, "lowI", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.detachInterrupt){
			InterruptPin pin;
			if(panel instanceof InterruptPanel1){
				InterruptPanel1 p = (InterruptPanel1) panel;
				pin = p.getInterruptPin();
			}else{
				InterruptPanel2 p = (InterruptPanel2) panel;
				pin = p.getInterruptPin();
			}
			ijadcca.senddetachInterrupt(pin);
			gui.addToRepeat(panel, "detachinterrupt", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.analogRead){
			AnalogPanel p = (AnalogPanel) panel;
			ijadcca.sendanalogRead(p.getAnalogPin());
			gui.addToRepeat(panel, "analogRead", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.digitalRead){
			DigitalPanel p = (DigitalPanel) panel;
			ijadcca.senddigitalRead(p.getPin());
			gui.addToRepeat(panel, "digitalRead", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.tone){
			DigitalPanel p = (DigitalPanel) panel;
			new ToneDialog(p, gui, ijadcca);
		}
		if(e.getSource() == PopUpMenu.noTone){
			DigitalPanel p = (DigitalPanel) panel;
			ijadcca.sendnoTone(p.getPin());
			gui.addToRepeat(panel, "noTone", (short) -1, (short)-1, (byte)-1);
		}
		if(e.getSource() == PopUpMenu.defaultAREF){
			ijadcca.sendanalogReference(AnalogReference.DEFAULT);
		}
		if(e.getSource() == PopUpMenu.internal){
			ijadcca.sendanalogReference(AnalogReference.INTERNAL);
		}
		if(e.getSource() == PopUpMenu.eksternal){
			ijadcca.sendanalogReference(AnalogReference.EXTERNAL);
		}
	}

	private void attachInterrupt(InterruptTrigger it, APanel panel2) {
		InterruptPin pin;
		if(panel2 instanceof InterruptPanel1){
			InterruptPanel1 pp = (InterruptPanel1) panel2;
			pin = (InterruptPin) pp.getInterruptPin();
		}else{
			InterruptPanel2 pp = (InterruptPanel2) panel2;
			pin = (InterruptPin) pp.getInterruptPin();
		}
		ijadcca.sendattachInterrupt(pin, it);
	}
}
