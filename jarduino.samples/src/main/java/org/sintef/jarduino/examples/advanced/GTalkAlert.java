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
package org.sintef.jarduino.examples.advanced;

import java.util.Timer;
import java.util.TimerTask;

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.InterruptTrigger;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PinMode;

/**
 * The libraries located in lib/voiceclearly are property of VoiceClealy:
 * Copyright 2009 VoiceClearly.
 * Please visit http://voiceclearly.com/voiceClearlyApiPricing.jsp
 * before reusing these libraries into your application.
 */
import com.voiceclearly.api.VoiceClearlyService;
import com.voiceclearly.api.chat.Chat;
import com.voiceclearly.api.chat.Message;
import com.voiceclearly.api.listener.CallListener;
import com.voiceclearly.api.model.Call;
import java.util.Date;
import org.sintef.jarduino.InterruptPin;

/**
 * Run the main according to the instructions and ask your friends to send
 * you instant messages on GTalk. Enjoy!
 */
public class GTalkAlert extends JArduino implements CallListener {

    private DigitalPin led;
    private InterruptPin button;
    private String gmailOtherUser;
    private String gmailUser;
    private String gmailPassword;
    private Timer timer;
    private VoiceClearlyService service;

    public GTalkAlert(String port) {
        super(port);
    }

    public GTalkAlert(String port, String user, String pwd, String otherUser, DigitalPin led, InterruptPin button) {
        this(port);
        this.led = led;
        this.button = button;
        this.timer = new Timer();
        this.gmailUser = user;
        this.gmailPassword = pwd;
        this.gmailOtherUser = otherUser;
        
        attachInterrupt(this.button, InterruptTrigger.CHANGE);
        
        //Initialize the VoiceClearly Service with the listener
        service = new VoiceClearlyService(this);

        //Login with Google Talk credentials
        service.login(gmailUser, gmailPassword);
    }

    @Override
    protected void receiveInterruptNotification(InterruptPin interrupt) {
    	System.out.println("Interrupt "+interrupt);
        super.receiveInterruptNotification(interrupt);
        if (interrupt == button) {
            service.sendIM(gmailOtherUser, "Button pushed: "+new Date(System.currentTimeMillis()));
        }
    }

    public void turnOffLED() {
        digitalWrite(led, DigitalState.LOW);
        System.out.println("Hopefully, the LED should now be turned OFF");
    }

    @Override
    protected void setup() {
        pinMode(led, PinMode.OUTPUT);
    }

    @Override
    protected void loop() {
    }

    private class Timeout extends TimerTask {

        GTalkAlert gTalk;

        public Timeout(GTalkAlert gTalk) {
            this.gTalk = gTalk;
        }

        @Override
        public void run() {
            gTalk.turnOffLED();
        }
    }

    public void incomingCall(Call call) {
    }

    public void callAnswered(Call call) {
    }

    public void callDeclined(Call call, String reason) {
    }

    public void callDisconnected(Call call, String reason) {
    }

    public void incomingIm(Chat chat, Message message) {
        digitalWrite(led, DigitalState.HIGH);
        System.out.println("Hopefully, the LED should now be turned ON");
        timer.schedule(new Timeout(this), 10000);
    }

    /**
     * Please set the serial port with the proper port,
     * the credentials of your Google Account, as well as the PIN 
     * where your LED is connected on your Arduino board.
     * Disclaimer: We are not responsible for any troubles with your google account. 
     * The connection to your Google account is entirely delegated to the
     * VoiceClearly APIs: http://voiceclearly.com/googleTalkVoiceClearly.jsp
     */
    public static void main(String[] args) {
        String otherName = "yourfriend@gmail.com";
        String userName = "first.last";
        String password = "myNicePassword";
        String serialPort = "COM8";

        DigitalPin led = DigitalPin.PIN_7;
        InterruptPin button = InterruptPin.PIN_2_INT0;

        JArduino arduino = new GTalkAlert(serialPort, userName, password, otherName, led, button);
        arduino.runArduinoProcess();
    }
}
