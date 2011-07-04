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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2370359305703895919L;
	private final String hint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
        }
    }

    @Override
    public String getText() {
        String typed = super.getText();
        return typed.equals(hint) ? "" : typed;
    }
}
