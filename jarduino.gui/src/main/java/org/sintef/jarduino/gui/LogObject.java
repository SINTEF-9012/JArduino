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

import org.sintef.jarduino.gui.panels.APanel;

public class LogObject {
	private APanel p;
	private String mode;
	private short addr, val;
	private byte b;
	
	public LogObject(APanel p, String mode, short addr, short val, byte b) {
		this.p = p;
		this.mode = mode;
		this.addr = addr;
		this.val = val;
		this.b = b;
	}

	public APanel getP() {
		return p;
	}

	public void setP(APanel p) {
		this.p = p;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public short getAddr() {
		return addr;
	}

	public void setAddr(short addr) {
		this.addr = addr;
	}

	public short getVal() {
		return val;
	}

	public void setVal(short val) {
		this.val = val;
	}

	public byte getB() {
		return b;
	}

	public void setB(byte b) {
		this.b = b;
	}
}
