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

import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalPin;

public class AnalogPanel extends DigitalPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AnalogPin pin;
	public AnalogPanel(AnalogPin pin) {
		super(DigitalPin.fromValue(pin.getValue()));
		this.pin = pin;
	}

	public AnalogPin getAnalogPin(){
		return pin;
	}
	
	@Override
	public String toString(){
		return "AnalogPin " + pin.getValue();
	}
}
