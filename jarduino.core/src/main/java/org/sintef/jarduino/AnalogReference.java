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

public enum AnalogReference {
	DEFAULT((byte)1),
	INTERNAL((byte)3),
	EXTERNAL((byte)0);

	private final byte value;
	
	private AnalogReference(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, AnalogReference> map;
	
	static {
		map = new HashMap<Byte, AnalogReference>();
		map.put((byte)1, AnalogReference.DEFAULT);
		map.put((byte)3, AnalogReference.INTERNAL);
		map.put((byte)0, AnalogReference.EXTERNAL);
	}
	
	public static AnalogReference fromValue(byte b) {
		return map.get(b);
	}
	
}
