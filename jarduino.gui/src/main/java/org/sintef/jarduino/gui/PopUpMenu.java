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

import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.sintef.jarduino.gui.panels.APanel;
import org.sintef.jarduino.gui.panels.AREFPanel;
import org.sintef.jarduino.gui.panels.AnalogPanel;
import org.sintef.jarduino.gui.panels.InterruptPanel1;
import org.sintef.jarduino.gui.panels.InterruptPanel2;

class PopUpMenu extends JPopupMenu {

	private static final long serialVersionUID = -8914052043434078823L;
	static JMenuItem  input, output, high, low, analogWrite, detachInterrupt, analogRead, digitalRead, tone, noTone
	, change, rising, falling, lowI, defaultAREF, internal, eksternal;
	JMenu pinMode, digitalWrite, attachInterrupt;
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private MenuListener l;
	private InteractiveJArduinoDataGUIClientAdvanced gui;

	public PopUpMenu(InteractiveJArduinoDataControllerClientAdvanced ijadcca, InteractiveJArduinoDataGUIClientAdvanced gui) {
		this.ijadcca = ijadcca;
		this.gui = gui;
		l = new MenuListener(this.ijadcca, this.gui);
	}

	public void buildMenu(MouseEvent e){
		APanel fromPanel = (APanel) e.getSource();
		l.setPanel(fromPanel);
		this.removeAll();
		if(!(fromPanel instanceof AREFPanel)){
			pinMode = new JMenu("Pin Mode");
			digitalWrite = new JMenu("Digital Write");
			digitalRead = new JMenuItem("Digital Read");

			input = new JMenuItem("INPUT");
			output = new JMenuItem("OUTPUT");
			high = new JMenuItem("HIGH");
			low = new JMenuItem("LOW");
			tone = new JMenuItem("Tone");
			noTone = new JMenuItem("No Tone");

			pinMode.add(input);
			pinMode.add(output);

			digitalWrite.add(high);
			digitalWrite.add(low);

			add(pinMode);
			add(digitalWrite);
			add(digitalRead);
			addSeparator();
			add(tone);
			add(noTone);
			if(fromPanel instanceof PWMPanel){
				addSeparator();
				analogWrite = new JMenuItem("Analog Write");
				analogWrite.addActionListener(l);
				add(analogWrite);
			}
			if(fromPanel instanceof InterruptPanel1 || fromPanel instanceof InterruptPanel2){
				addSeparator();
				attachInterrupt = new JMenu("Attach interrupt");
				detachInterrupt = new JMenuItem("Detach interrupt");
				change = new JMenuItem("CHANGE");
				rising = new JMenuItem("RISING");
				falling = new JMenuItem("FALLING");
				lowI = new JMenuItem("LOW");

				change.addActionListener(l);
				rising.addActionListener(l);
				falling.addActionListener(l);
				lowI.addActionListener(l);
				detachInterrupt.addActionListener(l);

				attachInterrupt.add(change);
				attachInterrupt.add(rising);
				attachInterrupt.add(falling);
				attachInterrupt.add(lowI);

				add(attachInterrupt);
				add(detachInterrupt);
			}
			if(fromPanel instanceof AnalogPanel){
				addSeparator();
				analogRead = new JMenuItem("Analog Read");
				analogRead.addActionListener(l);
				add(analogRead);
			}

			input.addActionListener(l);
			output.addActionListener(l);
			high.addActionListener(l);
			low.addActionListener(l);
			tone.addActionListener(l);
			noTone.addActionListener(l);
			digitalRead.addActionListener(l);
		}else{
			defaultAREF = new JMenuItem("DEFAULT");
			internal = new JMenuItem("INTERNAL");
			eksternal = new JMenuItem("EXTERNAL");
			
			defaultAREF.addActionListener(l);
			internal.addActionListener(l);
			eksternal.addActionListener(l);
			
			add(defaultAREF);
			add(internal);
			add(eksternal);
		}
	}
}