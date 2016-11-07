/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June
 * 2007; you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Authors: Brice Morin Company: SINTEF IKT, Oslo, Norway
 * Date: 2013
 */
package org.sintef.jarduino.examples.advanced;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.InterruptTrigger;
import org.sintef.jarduino.InvalidPinTypeException;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.Pin;
import org.sintef.jarduino.PinMode;
import org.sintef.jarduino.InterruptPin;

/**
 * This class is largely inspired by
 * http://www.andrejkoelewijn.com/wp/2008/12/30/using-google-talk-from-java-example/
 * and realized with the Smack API provided by Jive Software
 * http://www.igniterealtime.org/index.jsp
 */
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * Run the main according to the instructions and ask your friends to send you
 * instant messages on GTalk. Click on the button to send him messages.
 * Enjoy!
 */
public class GTalkAlert extends JArduino {

    private Pin led;
    private Pin button;
    private String gmailOtherUser;
    private String gmailUser;
    private String gmailPassword;
    private Timer timer;
    private XMPPConnection connection;

    public GTalkAlert(String port) {
        super(port);
    }

    public GTalkAlert(String port, String user, String pwd, String otherUser,  Pin led, Pin button) throws InvalidPinTypeException {
        this(port);

        this.led = led;
        this.button = button;
        this.timer = new Timer();
        this.gmailUser = user;
        this.gmailPassword = pwd;
        this.gmailOtherUser = otherUser;

        attachInterrupt(this.button, InterruptTrigger.CHANGE);

        try {
            // connect to gtalk server
            ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
            connection = new XMPPConnection(connConfig);
            connection.connect();

            // login with username and password
            connection.login(gmailUser, gmailPassword);

            // set presence status info
            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);



            IncomingMessageHandler pl = new IncomingMessageHandler();
            connection.addPacketListener(pl, null);

        } catch (XMPPException ex) {
            Logger.getLogger(GTalkAlert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * When button is clicked, sends a message to your friend
     *
     * @param interrupt
     * @throws InvalidPinTypeException 
     */
    @Override
    protected void receiveInterruptNotification(Pin interrupt) throws InvalidPinTypeException {
        System.out.println("Interrupt " + interrupt);
        super.receiveInterruptNotification(interrupt);
        if (interrupt == button) {
            // send a message to somebody
            Message msg = new Message(gmailOtherUser, Message.Type.chat);
            msg.setBody("hello");
            connection.sendPacket(msg);
        }
    }

    public void turnOffLED() throws InvalidPinTypeException {
        digitalWrite(led, LOW);
        System.out.println("Hopefully, the LED should now be turned OFF");
    }

    public void turnOnLED() throws InvalidPinTypeException {
        digitalWrite(led, HIGH);
        System.out.println("Hopefully, the LED should now be turned ON");
    }

    public void turnOnLEDfor(long delay) throws InvalidPinTypeException {
        turnOnLED();
        timer.schedule(new Timeout(), delay);
    }

    @Override
    protected void setup() throws InvalidPinTypeException {
        pinMode(led, OUTPUT);
    }

    @Override
    protected void loop() {
    }

    private class Timeout extends TimerTask {

        @Override
        public void run() {
            try {
				turnOffLED();
			} catch (InvalidPinTypeException e) {
				e.printStackTrace();
			}
        }
    }

    private class IncomingMessageHandler implements PacketListener {
        
        @Override
        public void processPacket(Packet p) {
            System.out.println("Incoming message from " + p.getFrom() + ": " + p.toString());
            if (p instanceof Message) {
                Message msg = (Message) p;
                System.out.println(msg.getFrom() + ": " + msg.getBody());
                try {
					turnOnLEDfor(5000);
				} catch (InvalidPinTypeException e) {
					e.printStackTrace();
				}
            }
        }
        
    }

    /**
     * Please set the serial port with the proper port, the credentials of your
     * Google Account, as well as the PIN where your LED is connected on your
     * Arduino board. Disclaimer: We are not responsible for any troubles with
     * your google account. The connection to your Google account is entirely
     * delegated to the Smack API
     * @throws InvalidPinTypeException 
     */
    public static void main(String[] args) throws InvalidPinTypeException {
        String otherName = "your.friend@gmail.com";
        String userName = "first.last@gmail.com";
        String password = "yourPassword";
        String serialPort = "COM4";

        Pin led = Pin.PIN_9;
        Pin button = Pin.PIN_2;

        JArduino arduino = new GTalkAlert(serialPort, userName, password, otherName, led, button);
        arduino.runArduinoProcess();
    }
}
