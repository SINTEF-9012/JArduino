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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class ColorLegend extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ColorLegend() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("./res/images/colorlegend.png"));
			contentPane.setLayout(null);
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			picLabel.setBounds(5, 5, 745, 467);
			contentPane.add( picLabel );
			
			JButton btnClose = new JButton("Close");
			btnClose.setBounds(56, 421, 89, 23);
			btnClose.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			contentPane.add(btnClose);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setMinimumSize(new Dimension(771,515));
		setTitle("Color Legend");
	}
}
