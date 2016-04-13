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

public enum DigitalState {
	LOW((byte)0),
	HIGH((byte)127);

	private final byte value;
	
	private DigitalState(byte value){
		this.value = value;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, DigitalState> map;
	
	static {
		map = new HashMap<Byte, DigitalState>();
		map.put((byte)0, DigitalState.LOW);
		map.put((byte)1, DigitalState.HIGH);
	}
	
	public static DigitalState fromValue(byte b) {
		return map.get(b);
	}
	
}
