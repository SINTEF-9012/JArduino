package org.sintef.jarduino;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import org.sintef.jarduino.comm.AndroidBluetoothConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 22/08/13
 * Time: 09:19
 */
class BlinkAndAnalog extends JArduino {
    final AnalogPin analogInPin = AnalogPin.A_0;
    int sensorValue = 0;        // value read from the pot

    public BlinkAndAnalog(BluetoothSocket socket) {
        super(JArduinoCom.AndroidBluetooth, new AndroidBluetoothConfiguration(socket));
    }

    @Override
    protected void setup() {
        // initialize the digital pin as an output.
        // Pin 13 has an LED connected on most Arduino boards:
        pinMode(DigitalPin.PIN_13, PinMode.OUTPUT);
    }

    @Override
    protected void loop() {
        // set the LED on
        digitalWrite(DigitalPin.PIN_13, DigitalState.HIGH);
        delay(1000); // wait for a second
        // set the LED off
        digitalWrite(DigitalPin.PIN_13, DigitalState.LOW);
        delay(1000); // wait for a second

        // get data from analog sensor on pin analogPin.
        sensorValue = analogRead(analogInPin);
        Log.d("coucou", String.valueOf(sensorValue));
    }
}
