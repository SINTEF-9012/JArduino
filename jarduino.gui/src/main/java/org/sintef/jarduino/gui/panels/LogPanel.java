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
package org.sintef.jarduino.gui.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogPanel extends JPanel {

	private static final long serialVersionUID = 7378185316836867117L;
	private JTextArea textArea;
	private JScrollPane editorScrollPane;
	
	public LogPanel() {
		setLayout(null);
		setBorder(BorderFactory.createTitledBorder("Log"));
		textArea = new JTextArea();
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
        textArea.setAutoscrolls(true);
		
  		editorScrollPane = new JScrollPane(textArea);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setBounds(10, 21, 739, 149);
		add(editorScrollPane);
		
		JButton btnNewButton = new JButton("Clear Log");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnNewButton.setBounds(10, 181, 739, 23);
		add(btnNewButton);
	}
	
	public void writeToLog( String string){
		textArea.append( string + '\n');
		textArea.setCaretPosition( textArea.getDocument().getLength() );
	}
	
}
