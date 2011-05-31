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
 * Authors: Jan Ole Skotterud, Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino.examples.advanced;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.comm.Serial4JArduino;

public class ToneKeyKeyboard extends JArduino {

    private KeyPressed keyPressed;
    private JPanel contentPane;
    private JTextField textField;
    //Connect your pieze element to digital pin 8
    private DigitalPin pin = DigitalPin.PIN_10;
    private Timer timer;
    private Timeout timeout;
    private JFrame frame;

    public ToneKeyKeyboard(String port) {
        super(port);
        timer = new Timer();

        //Initialize a key listener
        this.keyPressed = new KeyPressed();

        //Magic to create a nice but simple GUI
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 229, 115);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblEnterTextTo = new JLabel("Enter text to play");
        lblEnterTextTo.setBounds(10, 11, 136, 14);
        contentPane.add(lblEnterTextTo);

        textField = new JTextField();
        //add the Key listener
        textField.addKeyListener(this.keyPressed);
        textField.setBounds(10, 36, 183, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        frame.setPreferredSize(new Dimension(250, 100));
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {
    }

    //Starts the application
    //Remember to set the correct com port
    public static void main(String[] args) {
        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }

        JArduino arduino = new ToneKeyKeyboard(serialPort);
        arduino.runArduinoProcess();
    }

    //The keyEventListener class
    private class KeyPressed implements KeyListener {

        @Override
        public void keyPressed(KeyEvent arg0) {
            //release a current playing tone when a key is pressed
            noTone(pin);
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        @Override
        public void keyTyped(KeyEvent arg0) {
            //get the key value
            int tone = arg0.getKeyChar();
            //map the value to a tone
            tone = map(tone, 65, 229, 30, 5000);
            System.out.println(tone);
            //play the newly acquired tone on the Arduino board
            tone(pin, (short) tone, (short) 0);
            //create a new timeout that will end the tone after 1,3 seconds
            if (timeout != null) {
                timeout.cancel();
            }
            timer.purge();
            timeout = new Timeout();
            timer.schedule(timeout, 1300);
        }
    }

    //The timed tone ender class
    private class Timeout extends TimerTask {

        @Override
        public void run() {
            noTone(pin);
        }
    }
}
