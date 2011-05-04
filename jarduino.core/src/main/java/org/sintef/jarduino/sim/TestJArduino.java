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
package org.sintef.jarduino.sim;

import org.sintef.jarduino.sim.InteractiveJArduinoDataController;
import org.sintef.jarduino.sim.InteractiveJArduinoDataControllerClient;

public class TestJArduino {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InteractiveJArduinoDataController controller1 = new InteractiveJArduinoDataController();
		InteractiveJArduinoDataControllerClient controller2 = new InteractiveJArduinoDataControllerClient();
		controller1.register(controller2);
		controller2.register(controller1);
	}
}