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

import org.sintef.jarduino.PWMPin;
import org.sintef.jarduino.DigitalPin;

class PWMPanel extends DigitalPanel{
	private static final long serialVersionUID = 5238934750408244536L;
	private PWMPin pin;
	public PWMPanel(PWMPin pwmPin) {
		super(DigitalPin.fromValue(pwmPin.getValue()));
		this.pin = pwmPin;
	}

	public PWMPin getPWMPin(){
		return pin;
	}
	
	@Override
	public String toString(){
		return "PWMPin " + pin.getValue();
	}
}