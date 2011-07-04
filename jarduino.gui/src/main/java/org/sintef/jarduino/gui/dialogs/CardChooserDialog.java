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
package org.sintef.jarduino.gui.dialogs;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CardChooserDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	public static String selectCard() {
		ArrayList<String> possibilities = new ArrayList<String>();
		possibilities.add("Diecimila");
		possibilities.add("Duemilanove");
		possibilities.add("Lilypad");
		possibilities.add("Uno");

		String s = (String) JOptionPane.showInputDialog(
				null,
				"JArduino",
				"Select your Arduino card",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				possibilities.toArray(),
				possibilities.toArray()[0]);
		return s;
	}
}
