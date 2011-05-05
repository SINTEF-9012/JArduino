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

public enum EAnalogPin {
	A_0((byte)14),
	A_1((byte)15),
	A_2((byte)16),
	A_3((byte)17),
	A_4((byte)18),
	A_5((byte)19);

	private final byte value;
	
	private EAnalogPin(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, EAnalogPin> map;
	
	static {
		map = new HashMap<Byte, EAnalogPin>();
		map.put((byte)14, EAnalogPin.A_0);
		map.put((byte)15, EAnalogPin.A_1);
		map.put((byte)16, EAnalogPin.A_2);
		map.put((byte)17, EAnalogPin.A_3);
		map.put((byte)18, EAnalogPin.A_4);
		map.put((byte)19, EAnalogPin.A_5);
	}
	
	public static EAnalogPin fromValue(byte b) {
		return map.get(b);
	}
	
}
