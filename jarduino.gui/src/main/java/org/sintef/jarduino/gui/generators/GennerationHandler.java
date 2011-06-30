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

package org.sintef.jarduino.gui.generators;

import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;

import org.sintef.jarduino.gui.LogObject;

public class GennerationHandler {

	String filename;
	
	
	public GennerationHandler(){		
	}
	
	
	
	public void getFile(LinkedList<LogObject> linkedList){
		JFileChooser fc = new JFileChooser();
		File temp;
			if(filename != null){
				temp = new File(filename);
			}else{
				temp = new File("Gennerated");
			}
		fc.setSelectedFile(temp);
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile(); //This grabs the File you typed
			filename = file.getName();
			new CodeGenerator(linkedList, file);	
		}
	}
	
}
