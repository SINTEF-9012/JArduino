package org.sintef.jarduino.examples.basic;

import org.sintef.jarduino.JArduino;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 17/04/13
 * Time: 21:07
 */
public class PingEthernet extends JArduino {

    public PingEthernet(String ip,Integer port) throws SocketException, UnknownHostException {
        super(ip,port);
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop() {

        // store the current time
        long time = System.currentTimeMillis();
        // send the ping message to the arduino and wait for the pong message
        boolean alive = ping();
        // compute the time interval
        time = System.currentTimeMillis() - time;
        if (alive) {
            System.out.println("Reply from Arduino: time=" + time + "ms");
        } else {
            System.out.println("Request timed out (after " + time + "ms)");
        }
        delay(1000); // wait for a second
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        String ip;
        if (args.length == 1) {
            ip = args[0];
        } else {
            ip = "192.168.178.29";
        }
        JArduino arduino = new PingEthernet(ip,4000);
        arduino.runArduinoProcess();
    }

}
