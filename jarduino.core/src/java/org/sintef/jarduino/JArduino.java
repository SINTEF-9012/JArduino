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
package org.sintef.jarduino;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.sintef.jarduino.msg.*;
import org.sintef.jarduino.comm.*;
import org.sintef.jarduino.observer.*;

public abstract class JArduino extends AbstractJArduino{

	ExecutorService interruptRoutineExecutor = Executors.newSingleThreadExecutor();
	
	@Override
	protected void receiveinterruptNotification(InterruptPin interrupt) {
		
		if (interrupt == InterruptPin.PIN_2_INT0) {
			interruptRoutineExecutor.submit(new Runnable() {
				public void run() { interrupt0(); }});
		}
		else if(interrupt == InterruptPin.PIN_3_INT1) {
			interruptRoutineExecutor.submit(new Runnable() {
				public void run() { interrupt0(); }});
		}
	}

	public JArduino(String port) {
		super(port);
	}
	
	/* ******************************************************
	 * JAVA Version of common Arduino operations
	 ********************************************************/
	
	public void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int map(int value, int l, int h, int nl, int nh) {
		return nl + (value - l)*(nh - nl) / (h - l);
	}
	
	/* ******************************************************
	 * Operation to be implemented by the application
	 ********************************************************/

	protected abstract void setup();
	protected abstract void loop();
	
	// Operation to be redefined to handle interrupts
	protected void interrupt0(){}
	protected void interrupt1(){}
	
	/* ******************************************************
	 * Operation to manage the application execution
	 ********************************************************/
	
	public void runArduinoProcess() {
		if (process != null) process.stopArduino();
		process = new RemoteArduinoProcess();
		process.start();
	}
	
	public void stopArduinoProcess() {
		if (process != null) process.stopArduino();
		process = null;
	}
	
	private RemoteArduinoProcess process;
	private class RemoteArduinoProcess extends Thread {
		
		protected boolean stop = false;
		
		public void stopArduino() {
			stop = true;
		}
		
		public void run() {
			setup();
			while(!stop) {
				loop();
				try {
					// Just to avoid blocking everything else if the 
					// application loop does not have delays
					Thread.sleep(5); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
