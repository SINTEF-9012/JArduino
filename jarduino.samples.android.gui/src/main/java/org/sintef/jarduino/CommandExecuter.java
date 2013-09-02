package org.sintef.jarduino;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:16
 */
public class CommandExecuter extends Thread{
    private GUIController mController;
    private List<LogObject> linkedList;
    public CommandExecuter(GUIController c){
        this.mController = c;
    }


    public CommandExecuter(GUIController c, List<LogObject> linkedList) {
        this.mController = c;
        this.linkedList = linkedList;
    }


    public void run(){
        for(int i = 0; i < linkedList.size(); i++){
            executeObjectCommand(linkedList.get(i));
        }
    }


    void executeObjectCommand(LogObject o) {

        if(o.getMode().startsWith("delay")){
            long start = System.currentTimeMillis();
            while((System.currentTimeMillis() - start) <= o.getVal()){
            }
        }
        if(o.getMode().equals("input")){
            mController.sendpinMode(PinMode.INPUT, (DigitalPin)o.getPin());
        }
        if(o.getMode().equals("low") ){
            mController.senddigitalWrite((DigitalPin)o.getPin(), DigitalState.LOW);
        }
        if(o.getMode().equals("analogRead")){
            mController.sendanalogRead((AnalogPin)o.getPin());
        }
        if(o.getMode().equals("output")){
            mController.sendpinMode(PinMode.OUTPUT, (DigitalPin)o.getPin());
        }
        if( o.getMode().equals("digitalRead")){
            mController.senddigitalRead((DigitalPin)o.getPin());
        }
        if(o.getMode().equals("high")){
            mController.senddigitalWrite((DigitalPin)o.getPin(), DigitalState.HIGH);
        }
        if(o.getMode().equals("analogWrite")){
            mController.sendanalogWrite((PWMPin)o.getPin(), (byte) o.getVal());
        }
        //TODO implement running of while loops and if tests in emulator mode
    }
}
