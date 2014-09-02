package org.sintef.jarduino.comm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.sintef.jarduino.ProtocolConfiguration;
import org.sintef.jarduino.observer.JArduinoClientObserver;
import org.sintef.jarduino.observer.JArduinoObserver;
import org.sintef.jarduino.observer.JArduinoSubject;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Build;

import com.hoho.android.usbserial.driver.*;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)

//default baudrate 9600

public class AndroidUsb4JArduino implements JArduinoClientObserver, JArduinoSubject, Runnable {
	public static final byte START_BYTE = 0x12;
    public static final byte STOP_BYTE = 0x13;
    public static final byte ESCAPE_BYTE = 0x7D;

    Set<JArduinoObserver> observers = new HashSet<JArduinoObserver>();
    
	UsbSerialDriver usbserial;
    private Thread reader = null;

	public AndroidUsb4JArduino(AndroidUsbProtocolConfiguration configuration) {
		// Get UsbManager from Android.
		Context context = configuration.getContext();
		UsbManager manager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
		// Find the first available driver.
		usbserial = UsbSerialProber.acquire(manager);
		try {
			if(usbserial != null){
				usbserial.open();
				usbserial.setBaudRate(9600);
				reader = new Thread(this);
				reader.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AndroidUsb4JArduino(ProtocolConfiguration myConf) {
        this(((AndroidUsbProtocolConfiguration)myConf));
    }
	
	@Override
	public void receiveMsg(byte[] msg) {
		// TODO Auto-generated method stub
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
        try {
			usbserial.write(datas, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final int RCV_WAIT = 0;
    public static final int RCV_MSG = 1;
    public static final int RCV_ESC = 2;
    protected int buffer_idx = 0;
    protected int state = RCV_WAIT;
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];
        while (true) {
            try {
                int length = usbserial.read(buffer, 0);
                ByteArrayInputStream in = new ByteArrayInputStream(buffer, 0, length);
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
	
	@Override
	public void register(JArduinoObserver observer) {
		// TODO Auto-generated method stub
        observers.add(observer);
	}

	@Override
	public void unregister(JArduinoObserver observer) {
		// TODO Auto-generated method stub
        observers.remove(observer);
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		try {
			if(usbserial != null){
				usbserial.close();
				usbserial = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        reader.stop();
    }
}
