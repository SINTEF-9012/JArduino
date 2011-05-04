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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PinMode;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


/*
 * This is a demonstration on how little code you need to connect your 
 * Arduino to Twitter and get one LED to light up when you receive 
 * a new tweet from someone you are following.
 * 
 * This example does not show how to register your application
 * with twitter, to do this follow the nice tutorial on 
 * http://twitter4j.org/en/code-examples.html
 */
public class Twitter4Arduino extends JArduino{
	
	private DigitalPin led = DigitalPin.PIN_13;
	private Twitter twitter;
	private Status last;
	private Timer timer;
	private String userName;
	
	public Twitter4Arduino(String port, String customerKey, String customerSecret, 
			String accessKey, String accessSecret, String userName) {
		super(port);
		this.userName = userName;
		this.twitter = new TwitterFactory().getInstance();
		this.twitter.setOAuthConsumer(customerKey, customerSecret); 
		AccessToken accessToken = new AccessToken(accessKey, accessSecret);
		this.twitter.setOAuthAccessToken(accessToken);
		
		this.timer = new Timer();
	}
	
	/*
	 * Turns off the LED
	 */
	public void turnOffLED() {
		digitalWrite(led, DigitalState.LOW);
	}	
	
	@Override
	public void setup() {	
		//connect pin 13 to LED
		pinMode(led, PinMode.OUTPUT);
		turnOffLED(); //turn it of in case it is on after running another application
	}

	@Override
	protected void loop() {
		List<Status> statuses;
		try {
			//Get status updates from your tweet feed
			statuses = twitter.getFriendsTimeline();
			//select the last tweet
			Status status = (Status)statuses.get(0);
			//check if it is a new tweet, or if you have it from before
			if(last == null || !status.getUser().getName().equals(last.getUser().getName()) 
					&& !status.getText().equals(last.getText())){
				//check that the tweet isn't written by yourself
				if(!status.getUser().getScreenName().equalsIgnoreCase(userName)) {
					System.out.println(status.getUser().getScreenName() + ":" +
							status.getText()); 	
					last = status;
					//light up the Arduino
					digitalWrite(led, DigitalState.HIGH);
					timer.schedule(new Timeout(this), 10000);
				}
			}
			//wait ten seconds before checking again
			//Twitter have a limit on how many times you can check per day
			delay(10000);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Here you can set all your variables before launching 
	 * your application
	 */
	public static void main(String[] args) {
		String customerKey = "your consumer key";
		String customerSecret = "your consumer secret";
		String accessKey = "your access key";
		String accessSecret = "your access secret";
		String twitterUserName = "your user name";
		
		JArduino arduino = new Twitter4Arduino("COM6", customerKey, customerSecret,
				accessKey, accessSecret, twitterUserName);
		arduino.runArduinoProcess();
	}	
	
	private class Timeout extends TimerTask {

		Twitter4Arduino t4a;

        public Timeout(Twitter4Arduino t4a) {
            this.t4a = t4a;
        }
        
		@Override
		public void run() {
			t4a.turnOffLED();
		}
	}
}