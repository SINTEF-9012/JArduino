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

public enum DigitalPin {
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
	PIN_22((byte)22),
	PIN_23((byte)23),
	PIN_24((byte)24),
	PIN_25((byte)25),
	PIN_26((byte)26),
	PIN_27((byte)27),
	PIN_28((byte)28),
	PIN_29((byte)29),
	PIN_30((byte)30),
	PIN_31((byte)31),
	PIN_32((byte)32),
	PIN_33((byte)33),
	PIN_34((byte)34),
	PIN_35((byte)35),
	PIN_36((byte)36),
	PIN_37((byte)37),
	PIN_38((byte)38),
	PIN_39((byte)39),
	PIN_40((byte)40),
	PIN_41((byte)41),
	PIN_42((byte)42),
	PIN_43((byte)43),
	PIN_44((byte)44),
	PIN_45((byte)45),
	PIN_46((byte)46),
	PIN_47((byte)47),
	PIN_48((byte)48),
	PIN_49((byte)49),
	PIN_50((byte)50),
	PIN_51((byte)51),
	PIN_52((byte)52),
	PIN_53((byte)53),
	A_0((byte)14),
	A_1((byte)15),
	A_2((byte)16),
	A_3((byte)17),
	A_4((byte)18),
	A_5((byte)19);

	private final byte value;
	
	private DigitalPin(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, DigitalPin> map;
	
	static {
		map = new HashMap<Byte, DigitalPin>();
		map.put((byte)0, DigitalPin.PIN_0);
		map.put((byte)1, DigitalPin.PIN_1);
		map.put((byte)2, DigitalPin.PIN_2);
		map.put((byte)3, DigitalPin.PIN_3);
		map.put((byte)4, DigitalPin.PIN_4);
		map.put((byte)5, DigitalPin.PIN_5);
		map.put((byte)6, DigitalPin.PIN_6);
		map.put((byte)7, DigitalPin.PIN_7);
		map.put((byte)8, DigitalPin.PIN_8);
		map.put((byte)9, DigitalPin.PIN_9);
		map.put((byte)10, DigitalPin.PIN_10);
		map.put((byte)11, DigitalPin.PIN_11);
		map.put((byte)12, DigitalPin.PIN_12);
		map.put((byte)13, DigitalPin.PIN_13);
		map.put((byte)22, DigitalPin.PIN_22);
		map.put((byte)23, DigitalPin.PIN_23);
		map.put((byte)24, DigitalPin.PIN_24);
		map.put((byte)25, DigitalPin.PIN_25);
		map.put((byte)26, DigitalPin.PIN_26);
		map.put((byte)27, DigitalPin.PIN_27);
		map.put((byte)28, DigitalPin.PIN_28);
		map.put((byte)29, DigitalPin.PIN_29);
		map.put((byte)30, DigitalPin.PIN_30);
		map.put((byte)31, DigitalPin.PIN_31);
		map.put((byte)32, DigitalPin.PIN_32);
		map.put((byte)33, DigitalPin.PIN_33);
		map.put((byte)34, DigitalPin.PIN_34);
		map.put((byte)35, DigitalPin.PIN_35);
		map.put((byte)36, DigitalPin.PIN_36);
		map.put((byte)37, DigitalPin.PIN_37);
		map.put((byte)38, DigitalPin.PIN_38);
		map.put((byte)39, DigitalPin.PIN_39);
		map.put((byte)40, DigitalPin.PIN_40);
		map.put((byte)41, DigitalPin.PIN_41);
		map.put((byte)42, DigitalPin.PIN_42);
		map.put((byte)43, DigitalPin.PIN_43);
		map.put((byte)44, DigitalPin.PIN_44);
		map.put((byte)45, DigitalPin.PIN_45);
		map.put((byte)46, DigitalPin.PIN_46);
		map.put((byte)47, DigitalPin.PIN_47);
		map.put((byte)48, DigitalPin.PIN_48);
		map.put((byte)49, DigitalPin.PIN_49);
		map.put((byte)50, DigitalPin.PIN_50);
		map.put((byte)51, DigitalPin.PIN_51);
		map.put((byte)52, DigitalPin.PIN_52);
		map.put((byte)53, DigitalPin.PIN_53);
		map.put((byte)14, DigitalPin.A_0);
		map.put((byte)15, DigitalPin.A_1);
		map.put((byte)16, DigitalPin.A_2);
		map.put((byte)17, DigitalPin.A_3);
		map.put((byte)18, DigitalPin.A_4);
		map.put((byte)19, DigitalPin.A_5);
	}
	
	public static DigitalPin fromValue(byte b) {
		return map.get(b);
	}
	
}