package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:27
 */

/*
 *  Object representing a Digital pin (Digital input/output) of the Arduino.
 *  The toString() method fixes the text displayed into the log.
 */

public class LogDelayObject extends LogObject<DigitalPin> {

    public LogDelayObject(){

    }

    public LogDelayObject(String mode, short addr, short val, byte b) {
        super(mode, addr, val, b);
    }

    public DigitalPin getPin() {
        return null;
    }

    public void setPin(DigitalPin p) {
    }

    public String toString(){
        return String.valueOf("Delay["+
                getMode() + "," +
                getVal() + "]");
    }

    public String toLog(){
        return String.valueOf("Delay["+
                getMode() + "," +
                getVal() + "]");
    }
}
