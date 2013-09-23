package org.sintef.jarduino;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:16
 */

/*
 * This class is used to replay the log list orders.
 * It takes a list of orders and executes them using the GUIController.
 */

public class CommandExecuter extends Thread{
    private GUIController mController;
    private List<LogObject> linkedList;
    private List<LogObject> setup;
    private boolean pause = false;
    private boolean stop = false;

    public CommandExecuter(GUIController c){
        this.mController = c;
    }


    public CommandExecuter(GUIController c, List<LogObject> linkedList, List<LogObject> setup) {
        this.mController = c;
        this.linkedList = linkedList;
        this.setup = setup;
    }


    public void run(){
        // Setup one time
        for(LogObject o: setup){
            executeObjectCommand(o);
        }
        // Then loop on orders
        int count = 0;
        while(!stop){
            for(LogObject o: linkedList){
                // If paused, do not forget to handle stop.
                while(pause){
                    if(stop){
                        AndroidJArduinoGUI.ME.setProgressBar(0);
                        this.interrupt();
                        return;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // catch stop if stopped.
                if(stop){
                    AndroidJArduinoGUI.ME.setProgressBar(0);
                    this.interrupt();
                    return;
                }
                executeObjectCommand(o);
                AndroidJArduinoGUI.ME.setProgressBar((int)((count/(float)linkedList.size())*100));
                count ++;
            }
            count = 0;
        }
        this.interrupt();
    }

    void executeObjectCommand(LogObject o) {
        GUIController.blinkButton(o);

        if(o.getMode().startsWith("delay")){
            long start = System.currentTimeMillis();
            while((System.currentTimeMillis() - start) <= o.getVal()){
            }
        }
        if(o.getMode().equals("input")){
            mController.sendpinMode(PinMode.INPUT, (DigitalPin)o.getPin(), false);
        }
        if(o.getMode().equals("low") ){
            mController.senddigitalWrite((DigitalPin)o.getPin(), DigitalState.LOW, false);
        }
        if(o.getMode().equals("analogRead")){
            mController.sendanalogRead((AnalogPin)o.getPin(), false);
        }
        if(o.getMode().equals("output")){
            mController.sendpinMode(PinMode.OUTPUT, (DigitalPin)o.getPin(), false);
        }
        if( o.getMode().equals("digitalRead")){
            mController.senddigitalRead((DigitalPin)o.getPin(), false);
        }
        if(o.getMode().equals("high")){
            mController.senddigitalWrite((DigitalPin)o.getPin(), DigitalState.HIGH, false);
        }
        if(o.getMode().equals("analogWrite")){
            mController.sendanalogWrite((PWMPin)o.getPin(), (byte) o.getVal(), false);
        }
        //TODO implement running of while loops and if tests in emulator mode
    }

    public void setPause(boolean b){
        pause = b;
    }

    public void setStop(boolean b){
        stop = b;
    }
}
