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
/**
 * In order to run this application, your project must include two libraries
 * org.kevoree.extra.osgi.rxtx-2.2.0.jar or newer and
 * org.sintef.jarduino.core-0.1.6.jar or newer
 * 
 * @author Jan Ole
 *
 */
public class JArduinoGUIAdvanced {

    public static void main(String[] args) {
        Serial4JArduino device = null;
        String serialPort = null;
        try {

            if (args.length == 1) {
                serialPort = args[0];
            } else {
                serialPort = Serial4JArduino.selectSerialPort();
            }

            InteractiveJArduinoDataControllerClientAdvanced controller = new InteractiveJArduinoDataControllerClientAdvanced();
            if (serialPort != null) {
                device = new Serial4JArduino(serialPort);
                device.register(controller);
                controller.register(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (device != null) {
                device.close();
            }
        }
    }
}
