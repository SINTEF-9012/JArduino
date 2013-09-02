package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:27
 */
public class LogDigitalObject extends LogObject<DigitalPin> {
    DigitalPin pin;

    public LogDigitalObject(){

    }

    public LogDigitalObject(DigitalPin pin, String mode, short addr, short val, byte b) {
        super(mode, addr, val, b);
        this.pin = pin;
    }

    public DigitalPin getPin() {
        return pin;
    }

    public void setPin(DigitalPin p) {
        this.pin = p;
    }

    public String toString(){
        return String.valueOf("Digital[" + getPin() + "," +
                getAddr() + "," +
                getB() + "," +
                getMode() + "," +
                getVal() + "]");
    }
}
