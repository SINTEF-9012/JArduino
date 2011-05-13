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

import org.sintef.jarduino.comm.Serial4JArduino;

public class JArduinoGUIAdvanced {
	public static void main(String[] args) {
		Serial4JArduino device = null;
		try {
			String serialPort = "";
	        if (args.length == 1) {
	            serialPort = args[0];
	        } else {
	            serialPort = Serial4JArduino.selectSerialPort();
	        }
			
			device = new Serial4JArduino(serialPort);
			InteractiveJArduinoDataControllerClientAdvanced controller = new InteractiveJArduinoDataControllerClientAdvanced();
			device.register(controller);
			controller.register(device);
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			device.close();
		}	
	}
}
