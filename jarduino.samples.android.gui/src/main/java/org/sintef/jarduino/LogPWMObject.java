package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:27
 */

/*
 *  Object representing a PWM pin (Analog output) of the Arduino.
 *  The toString() method fixes the text displayed into the log.
 */

public class LogPWMObject extends LogObject<PWMPin> {
    PWMPin pin;

    public LogPWMObject(){

    }

    public LogPWMObject(PWMPin pin, String mode, short addr, short val, byte b) {
        super(mode, addr, val, b);
        this.pin = pin;
    }

    public PWMPin getPin() {
        return pin;
    }

    public void setPin(PWMPin p) {
        this.pin = p;
    }

    public String toString(){
        return String.valueOf("Pwm[" + getPin() + "," +
                getAddr() + "," +
                getB() + "," +
                getMode() + "," +
                getVal() + "]");
    }
}
