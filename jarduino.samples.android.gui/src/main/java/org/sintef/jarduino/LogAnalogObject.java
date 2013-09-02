package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:27
 */
public class LogAnalogObject extends LogObject<AnalogPin> {
    AnalogPin pin;

    LogAnalogObject(){

    }

    public LogAnalogObject(AnalogPin pin, String mode, short addr, short val, byte b) {
        super(mode, addr, val, b);
        this.pin = pin;
    }

    public AnalogPin getPin() {
        return pin;
    }

    public void setPin(AnalogPin p) {
        this.pin = p;
    }

    public String toString(){
        return String.valueOf("Analog[" + getPin() + "," +
                getAddr() + "," +
                getB() + "," +
                getMode() + "," +
                getVal() + "]");
    }
}
