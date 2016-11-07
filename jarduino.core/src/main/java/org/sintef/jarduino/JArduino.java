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

@SuppressWarnings("unused")
public abstract class JArduino extends AbstractJArduino {

    ExecutorService interruptRoutineExecutor = Executors.newSingleThreadExecutor();

    public JArduino(String ID, JArduinoCom com) {
        super(ID, com, null);
    }

    public JArduino(JArduinoCom com) {
        super(null, com, null);
    }

    public JArduino(String ID, JArduinoCom com, ProtocolConfiguration prot) {
        super(ID, com, prot);
    }

    public JArduino(JArduinoCom com, ProtocolConfiguration prot) {
        super(null, com, prot);
    }

    public JArduino() {
        super(null, JArduinoCom.Serial, null); //Serial by default
    }

    public JArduino(String id) {
        super(id, JArduinoCom.Serial, null); //Serial by default
    }
    
    public JArduino(String id, ProtocolConfiguration prot) {
        super(id, JArduinoCom.Serial, prot); //Serial by default
    }
    
    protected final PinMode
    		OUTPUT = PinMode.OUTPUT,
    		INPUT = PinMode.INPUT;
	
    protected final DigitalState
    		LOW = DigitalState.LOW, 
    		HIGH = DigitalState.HIGH;
    
	protected final Pin
    	p0 = Pin.PIN_0,
		p1 = Pin.PIN_1,
	    p2 = Pin.PIN_2,
	    p3 = Pin.PIN_3,
	    p4 = Pin.PIN_4,
	    p5 = Pin.PIN_5,
	    p6 = Pin.PIN_6,
	    p7 = Pin.PIN_7,
	    p8 = Pin.PIN_8,
	    p9 = Pin.PIN_9,
	    p10 = Pin.PIN_10,
	    p11 = Pin.PIN_11,
	    p12 = Pin.PIN_12,
	    p13 = Pin.PIN_13,
	    p14 = Pin.A_0,
	    pA0 = Pin.A_0,
	    p15 = Pin.A_1,
	    pA1 = Pin.A_1,
	    p16 = Pin.A_2,
	    pA2 = Pin.A_2,
	    p17 = Pin.A_3,
	    pA3 = Pin.A_3,
	    p18 = Pin.A_4,
	    pA4 = Pin.A_4,
	    p19 = Pin.A_5,
	    pA5 = Pin.A_5;

    @Override
    protected void receiveInterruptNotification(InterruptPin interrupt) {

        if (interrupt == InterruptPin.PIN_2_INT0) {
            interruptRoutineExecutor.submit(new Runnable() {
                public void run() {
                    try {
						interrupt0();
					} catch (InvalidPinTypeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });
        } else if (interrupt == InterruptPin.PIN_3_INT1) {
            interruptRoutineExecutor.submit(new Runnable() {
                public void run() {
					try {
						interrupt0();
					} catch (InvalidPinTypeException e) {
						e.printStackTrace();
					}
                }
            });
        }
    }

    @Override
    protected void receiveInterruptNotification(Pin interrupt) throws InvalidPinTypeException {
		if(!interrupt.isInterrupt()) throw new InvalidPinTypeException();
		receiveInterruptNotification(InterruptPin.fromValue(interrupt.getValue()));
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
        return nl + (value - l) * (nh - nl) / (h - l);
    }

	/* ******************************************************
     * Operation to be implemented by the application
	 ********************************************************/

    protected abstract void setup() throws InvalidPinTypeException;

    protected abstract void loop() throws InvalidPinTypeException;

    // Operation to be redefined to handle interrupts
    protected void interrupt0() throws InvalidPinTypeException {
    }
    
    protected void interrupt1() throws InvalidPinTypeException {
    }
	
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
            try {
				setup();
			} catch (InvalidPinTypeException e1) {
				e1.printStackTrace();
			}
            while (!stop) {
                try {
					loop();
				} catch (InvalidPinTypeException e1) {
					e1.printStackTrace();
				}
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
