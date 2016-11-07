package org.sintef.jarduino.comm;

import org.sintef.jarduino.observer.JArduinoClientObserver;
import org.sintef.jarduino.observer.JArduinoObserver;
import org.sintef.jarduino.observer.JArduinoSubject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 17/04/13
 * Time: 20:40
 */

@SuppressWarnings({"deprecation", "unused"})
public class Udp4JArduino implements JArduinoClientObserver, JArduinoSubject, Runnable {

    public static final byte START_BYTE = 0x12;
    public static final byte STOP_BYTE = 0x13;
    public static final byte ESCAPE_BYTE = 0x7D;

    Set<JArduinoObserver> observers = new HashSet<JArduinoObserver>();
    private String ip = "";
    private Integer port = 0;

    private DatagramSocket datagramSocket = null;
    private InetAddress address = null;

    private Thread reader = null;

    public Udp4JArduino(String ip, Integer port) throws UnknownHostException, SocketException {
        this.ip = ip;
        this.port = port;
        address = InetAddress.getByName(this.ip);
        datagramSocket = new DatagramSocket();

        reader = new Thread(this);
        reader.start();
    }

	public void stop() {
        reader.stop();
    }

    @Override
    public void receiveMsg(byte[] msg) {
        //send data to UDP
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write((int) START_BYTE);
        // send data
        for (int i = 0; i < msg.length; i++) {
            // escape special bytes
            if (msg[i] == START_BYTE || msg[i] == STOP_BYTE || msg[i] == ESCAPE_BYTE) {
                out.write((int) ESCAPE_BYTE);
            }
            out.write((int) msg[i]);
        }
        // send the stop byte
        out.write((int) STOP_BYTE);
        byte[] datas = out.toByteArray();
        DatagramPacket p = new DatagramPacket(datas, datas.length, address, port);
        try {
            datagramSocket.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void register(JArduinoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(JArduinoObserver observer) {
        observers.remove(observer);
    }

    public static final int RCV_WAIT = 0;
    public static final int RCV_MSG = 1;
    public static final int RCV_ESC = 2;
	private byte[] buffer = new byte[256];
    protected int buffer_idx = 0;
    protected int state = RCV_WAIT;


    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                datagramSocket.receive(p);
                ByteArrayInputStream in = new ByteArrayInputStream(p.getData());
                int data;
                while ((data = in.read()) > -1) {
                    // we got a byte from the serial port
                    if (state == RCV_WAIT) { // it should be a start byte or we just ignore it
                        if (data == START_BYTE) {
                            state = RCV_MSG;
                            buffer_idx = 0;
                        }
                    } else if (state == RCV_MSG) {
                        if (data == ESCAPE_BYTE) {
                            state = RCV_ESC;
                        } else if (data == STOP_BYTE) {
                            // We got a complete frame
                            byte[] packet = new byte[buffer_idx];
                            for (int i = 0; i < buffer_idx; i++) {
                                packet[i] = buffer[i];
                            }
                            for (JArduinoObserver o : observers) {
                                o.receiveMsg(packet);
                            }
                            state = RCV_WAIT;
                        } else if (data == START_BYTE) {
                            // Should not happen but we reset just in case
                            state = RCV_MSG;
                            buffer_idx = 0;
                        } else { // it is just a byte to store
                            buffer[buffer_idx] = (byte) data;
                            buffer_idx++;
                        }
                    } else if (state == RCV_ESC) {
                        // Store the byte without looking at it
                        buffer[buffer_idx] = (byte) data;
                        buffer_idx++;
                        state = RCV_MSG;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
