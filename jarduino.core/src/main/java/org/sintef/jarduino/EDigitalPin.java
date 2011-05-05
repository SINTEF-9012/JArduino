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

public enum EDigitalPin {
	PIN_0((byte)0),
	PIN_1((byte)1),
	PIN_2((byte)2),
	PIN_3((byte)3),
	PIN_4((byte)4),
	PIN_5((byte)5),
	PIN_6((byte)6),
	PIN_7((byte)7),
	PIN_8((byte)8),
	PIN_9((byte)9),
	PIN_10((byte)10),
	PIN_11((byte)11),
	PIN_12((byte)12),
	PIN_13((byte)13),
	A_0((byte)14),
	A_1((byte)15),
	A_2((byte)16),
	A_3((byte)17),
	A_4((byte)18),
	A_5((byte)19);

	private final byte value;
	
	private EDigitalPin(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, EDigitalPin> map;
	
	static {
		map = new HashMap<Byte, EDigitalPin>();
		map.put((byte)0, EDigitalPin.PIN_0);
		map.put((byte)1, EDigitalPin.PIN_1);
		map.put((byte)2, EDigitalPin.PIN_2);
		map.put((byte)3, EDigitalPin.PIN_3);
		map.put((byte)4, EDigitalPin.PIN_4);
		map.put((byte)5, EDigitalPin.PIN_5);
		map.put((byte)6, EDigitalPin.PIN_6);
		map.put((byte)7, EDigitalPin.PIN_7);
		map.put((byte)8, EDigitalPin.PIN_8);
		map.put((byte)9, EDigitalPin.PIN_9);
		map.put((byte)10, EDigitalPin.PIN_10);
		map.put((byte)11, EDigitalPin.PIN_11);
		map.put((byte)12, EDigitalPin.PIN_12);
		map.put((byte)13, EDigitalPin.PIN_13);
		map.put((byte)14, EDigitalPin.A_0);
		map.put((byte)15, EDigitalPin.A_1);
		map.put((byte)16, EDigitalPin.A_2);
		map.put((byte)17, EDigitalPin.A_3);
		map.put((byte)18, EDigitalPin.A_4);
		map.put((byte)19, EDigitalPin.A_5);
	}
	
	public static EDigitalPin fromValue(byte b) {
		return map.get(b);
	}
	
}
