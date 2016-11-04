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
	
    private final DigitalState LOW = DigitalState.LOW;
    private final DigitalState HIGH = DigitalState.HIGH;
    private final Pin p0 = Pin.PIN_0;
    private final Pin p1 = Pin.PIN_1;
    private final Pin p2 = Pin.PIN_2;
    private final Pin p3 = Pin.PIN_3;
    private final Pin p4 = Pin.PIN_4;
    private final Pin p5 = Pin.PIN_5;
    private final Pin p6 = Pin.PIN_6;
    private final Pin p7 = Pin.PIN_7;
    private final Pin p8 = Pin.PIN_8;
    private final Pin p9 = Pin.PIN_9;
    private final Pin p10 = Pin.PIN_10;
    private final Pin p11 = Pin.PIN_11;
    private final Pin p12 = Pin.PIN_12;
    private final Pin p13 = Pin.PIN_13;
    private final Pin p14 = Pin.A_0;
    private final Pin pA0 = Pin.A_0;
    private final Pin p15 = Pin.A_1;
    private final Pin pA1 = Pin.A_1;
    private final Pin p16 = Pin.A_2;
    private final Pin pA2 = Pin.A_2;
    private final Pin p17 = Pin.A_3;
    private final Pin pA3 = Pin.A_3;
    private final Pin p18 = Pin.A_4;
    private final Pin pA4 = Pin.A_4;
    private final Pin p19 = Pin.A_5;
    private final Pin pA5 = Pin.A_5;





    @Override
    protected void receiveInterruptNotification(InterruptPin interrupt) {

        if (interrupt == InterruptPin.PIN_2_INT0) {
            interruptRoutineExecutor.submit(new Runnable() {
                public void run() {
                    interrupt0();
                }
            });
        } else if (interrupt == InterruptPin.PIN_3_INT1) {
            interruptRoutineExecutor.submit(new Runnable() {
                public void run() {
                    interrupt0();
                }
            });
        }
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

    protected abstract void setup();

    protected abstract void loop();

    // Operation to be redefined to handle interrupts
    protected void interrupt0() {
    }

    protected void interrupt1() {
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
            setup();
            while (!stop) {
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
