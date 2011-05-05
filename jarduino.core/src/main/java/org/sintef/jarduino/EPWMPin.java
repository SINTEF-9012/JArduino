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
 * Authors: Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino;

import java.util.HashMap;
import java.util.Map;

public enum EPWMPin {
	PWM_PIN_3((byte)3),
	PWM_PIN_5((byte)5),
	PWM_PIN_6((byte)6),
	PWM_PIN_9((byte)9),
	PWM_PIN_10((byte)10),
	PWM_PIN_11((byte)11);

	private final byte value;
	
	private EPWMPin(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, EPWMPin> map;
	
	static {
		map = new HashMap<Byte, EPWMPin>();
		map.put((byte)3, EPWMPin.PWM_PIN_3);
		map.put((byte)5, EPWMPin.PWM_PIN_5);
		map.put((byte)6, EPWMPin.PWM_PIN_6);
		map.put((byte)9, EPWMPin.PWM_PIN_9);
		map.put((byte)10, EPWMPin.PWM_PIN_10);
		map.put((byte)11, EPWMPin.PWM_PIN_11);
	}
	
	public static EPWMPin fromValue(byte b) {
		return map.get(b);
	}
	
}
