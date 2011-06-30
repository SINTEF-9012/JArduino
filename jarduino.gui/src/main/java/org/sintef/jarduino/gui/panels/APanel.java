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

import java.awt.Color;

import javax.swing.JPanel;

public class APanel extends JPanel{

	private static final long serialVersionUID = 1L;
	Color color = Color.BLACK;
	String mode = "O";
	JPanel digitalStatus;
	JPanel analogStatus;
	public APanel(){
		setLayout(null);
		
		digitalStatus = new JPanel();
		digitalStatus.setBounds(5, 0, 5, 5);
		digitalStatus.setBackground(Color.BLACK);
		add(digitalStatus);
		analogStatus = new JPanel();
		analogStatus.setBounds(5, 5, 5, 5);
		analogStatus.setBackground(Color.BLACK);
		add(analogStatus);
	}
	public void setColor(Color c){
		color = c;
		setBackground(color);
	}

	public void restoreColor(){
		setBackground(color);
		analogStatus.setOpaque(true);
		digitalStatus.setOpaque(true);
		revalidate();
	}

	public void changeColor(){
		if (color == Color.BLACK){
			color = Color.RED;
		}else {
			color = Color.BLACK;
		}
	}
	
	public void setDigitalStatusHigh(){
		digitalStatus.setBackground(Color.RED);

	}
	public void setDigitalStatusLow(){
		digitalStatus.setBackground(Color.BLACK);
	}
	
	public void setAnalogRead(int i){
		analogStatus.setBackground(new Color(i,i,0));
	}
	public void setAnalogWrite(int i){
		analogStatus.setBackground(new Color(0,map(i,0,1024,0,255),0));
	}
	
	public void setOpaque(){
		digitalStatus.setOpaque(false);
		analogStatus.setOpaque(false);
	}
	int map(int x, int in_min, int in_max, int out_min, int out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
}