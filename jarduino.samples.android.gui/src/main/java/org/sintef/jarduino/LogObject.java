package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 28/08/13
 * Time: 10:24
 */

/*
 *  Abstract class for the several pin types.
 */

abstract public class LogObject<T> {
    private String mode;
    private short addr, val;
    private byte b;

    public LogObject(){

    }

    public LogObject(String mode, short addr, short val, byte b) {
        this.mode = mode;
        this.addr = addr;
        this.val = val;
        this.b = b;
    }

    abstract public T getPin();

    abstract public void setPin(T pin);

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public short getAddr() {
        return addr;
    }

    public void setAddr(short addr) {
        this.addr = addr;
    }

    public short getVal() {
        return val;
    }

    public void setVal(short val) {
        this.val = val;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public String toLog(){
        return getMode()+": [pin: "+getPin()+"] [value: "+getVal()+"]";
    }
}
