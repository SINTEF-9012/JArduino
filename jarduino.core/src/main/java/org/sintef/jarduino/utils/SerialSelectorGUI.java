/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sintef.jarduino.utils;

import java.util.Enumeration;
import gnu.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author bmori
 */
public class SerialSelectorGUI {

    public static String selectSerialPort() {

        ArrayList<String> possibilities = new ArrayList<String>();
        for (Enumeration enumeration = CommPortIdentifier.getPortIdentifiers(); enumeration.hasMoreElements();) {
            CommPortIdentifier commportidentifier = (CommPortIdentifier) enumeration.nextElement();
            possibilities.add(commportidentifier.getName());

        }


        String s = (String) JOptionPane.showInputDialog(
                null,
                "JArduino",
                "Select serial port",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities.toArray(),
                possibilities.toArray()[0]);

        return s;
    }
}
