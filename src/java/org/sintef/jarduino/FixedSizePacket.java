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

import java.nio.ByteBuffer;

public abstract class FixedSizePacket {

	protected byte[] data;
	protected ByteBuffer buffer;
	
	public byte[] getPacket() {
		return data;
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i< 16; i++) {
			result += Byte.toString(data[i]) + ' ';
		}
		return result;
	}
	
	public static String toString(byte msg[]) {
		String result = "";
		for (int i = 0; i< msg.length; i++) {
			result += Byte.toString(msg[i]) + ' ';
		}
		return result;
	}
	
	public FixedSizePacket() {
		data = new byte[16];
		buffer = ByteBuffer.wrap(data, 5, 10);
	}
	
	public void setPacketData(byte[] packet) {
		System.arraycopy(packet, 0, this.data, 0, packet.length);
		buffer = ByteBuffer.wrap(data, 5, 10);
	}
	
	public void setSourceAddress(byte address) {
		data[0] = address;
	}
	public byte getSourceAddress() {
		return data[0];
	}
	
	public void setTargetAddress(byte address) {
		data[1] = address;
	}
	public byte getTargetAddress() {
		return data[1];
	}
	
	public void setFrameNumber(byte frame) {
		data[2] = frame;
	}
	public byte getFrameNumber() {
		return data[2];
	}
	
	public void setLength(byte length) {
		if (length > 10) length = 10;
		data[3] = length;
	}
	public byte getLength() {
		return data[3];
	}
	
	public byte[] getRawData() {
		byte[] result = new byte[getLength()];
        System.arraycopy(this.data, 5, result, 0, getLength());
		return result;
	}
	
	public void setRawData(byte[] ndata) {
        int length = Math.min(10, ndata.length);
        System.arraycopy(ndata, 0, this.data, 5, length);
		setLength((byte)length);
	}
	
}
