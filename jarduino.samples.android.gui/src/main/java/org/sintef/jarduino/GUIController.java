/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Jan Ole Skotterud Franck Fleurey and Brice Morin
 * Company: SINTEF IKT, Oslo, Norway
 * Date: 2011
 */
package org.sintef.jarduino;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.ListView;
import ar.com.daidalos.afiledialog.FileChooserDialog;
import org.sintef.jarduino.comm.AndroidBluetooth4JArduino;
import org.sintef.jarduino.observer.JArduinoClientObserver;
import org.sintef.jarduino.observer.JArduinoClientSubject;
import org.sintef.jarduino.observer.JArduinoObserver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/*
 * This class is used to send orders into the Arduino.
 * It also updates the log list with new orders.
 */

public class GUIController implements JArduinoObserver, JArduinoClientSubject {

    private List<JArduinoClientObserver> handlers;
    private List<LogObject> orders;
    private SimpleDateFormat dateFormat;
    private String TAG = "GUIController";
    private ListView logList;
    private Activity mActivity;
    private boolean running = false;
    private CommandExecuter runner = null;
    static String file;

    public GUIController(ListView logger, Activity activity){
        orders = new ArrayList<LogObject>();
        this.logList = logger;
        mActivity = activity;
        handlers = new LinkedList<JArduinoClientObserver>();
        dateFormat = new SimpleDateFormat("dd MMM yyy 'at' HH:mm:ss.SSS");
    }

    class OneShotTask implements Runnable {
        String str;
        LogObject obj;
        LogAdapter adapter;
        OneShotTask(String s, LogObject o, LogAdapter a) {
            str = s; obj = o; adapter = a;
        }
        public void run() {
            adapter.add(str, obj);
            logList.invalidate();
            logList.setSelection(logList.getCount());
        }
    }

    private void addToLogger(String s, LogObject o, final LogAdapter adapter){
        mActivity.runOnUiThread(new OneShotTask(s, o, adapter));
    }

    static String reverseSearch(Map<String, ?> m, Object o){
        if(m.containsValue(o)){
            for(Map.Entry<String, ?> e: m.entrySet()){
                if(e.getValue() == o)
                    return e.getKey();
            }
        }
        return null;
    }

    static final int NONE = 0;
    static final int READ = 1;
    static final int WRITE = 2;

    //Blink buttons in the UI when order is sent.
    static void blinkButton(LogObject obj){
        if(obj.getMode().equals("delay"))
            return;

        Object pin = obj.getPin();
        String strPin = null;
        boolean read = false;
        if(pin instanceof PWMPin){
            strPin = reverseSearch(AndroidJArduinoGUI.analogOut, pin);
        }
        if(pin instanceof AnalogPin){
            strPin = reverseSearch(AndroidJArduinoGUI.analogIn, pin);
            read = true;
        }
        if(pin instanceof DigitalPin){
            strPin = reverseSearch(AndroidJArduinoGUI.digital, pin);
            if(obj.getMode().equals("digitalRead"))
                read = true;
        }
        final String finalStrPin = strPin;
        final boolean finalRead = read;

        if (finalRead){
            AndroidJArduinoGUI.changeButtonBackground(finalStrPin, READ);
        } else {
            AndroidJArduinoGUI.changeButtonBackground(finalStrPin, WRITE);
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AndroidJArduinoGUI.changeButtonBackground(finalStrPin, NONE);
    }

    private void doSend(FixedSizePacket data, LogObject obj){
        doSend(data);
        if(obj != null){
            addToLogger(data.toString(), obj, (LogAdapter)logList.getAdapter());
            blinkButton(obj);
        }
    }

    private void doSend(FixedSizePacket data){
        if (data != null) {
            for (JArduinoClientObserver h : handlers){
                h.receiveMsg(data.getPacket());
            }
        }
    }

    public final void delay(int value){
        LogObject obj = new LogDelayObject("delay", (short)-1, (short)value, (byte)-1);
        addToLogger(obj.toString(), obj, (LogAdapter)logList.getAdapter());
    }

    public final void sendpinMode(PinMode mode, DigitalPin pin, boolean tolog) {
        FixedSizePacket fsp = null;
        fsp = JArduinoProtocol.createPinMode(pin, mode);
        LogObject obj = null;
        if(tolog){
            if(mode == PinMode.INPUT)
                obj = new LogDigitalObject(pin, "input", (short)-1, (short)-1, (byte)-1);
            else
                obj = new LogDigitalObject(pin, "output", (short) -1, (short) -1, (byte) -1);
        }
        doSend(fsp, obj);
    }

    public final void senddigitalRead(DigitalPin pin, boolean tolog) {
        FixedSizePacket fsp = null;
        fsp = JArduinoProtocol.createDigitalRead(pin);
        LogObject obj = null;
        if(tolog){
            obj = new LogDigitalObject(pin, "digitalRead", (short)-1, (short)-1, (byte)-1);
        }
        doSend(fsp, obj);
    }

    public final void senddigitalWrite(DigitalPin pin, DigitalState value, boolean tolog) {
        FixedSizePacket fsp = null;
        fsp = JArduinoProtocol.createDigitalWrite(pin, value);
        LogObject obj = null;
        if(tolog){
            if(value == DigitalState.HIGH)
                obj = new LogDigitalObject(pin, "high", (short)-1, (short)-1, (byte)-1);
            else
                obj = new LogDigitalObject(pin, "low", (short) -1, (short) -1, (byte) -1);
        }
        doSend(fsp, obj);
    }

    public final void sendanalogRead(AnalogPin pin, boolean tolog) {
        FixedSizePacket fsp = null;
        fsp = JArduinoProtocol.createAnalogRead(pin);
        LogObject obj = null;
        if(tolog){
            obj = new LogAnalogObject(pin, "analogRead", (short)-1, (short)-1, (byte)-1);
        }
        doSend(fsp, obj);
    }

    public final void sendanalogWrite(PWMPin pin, byte value, boolean tolog) {
        FixedSizePacket fsp = null;
        fsp = JArduinoProtocol.createAnalogWrite(pin, value);
        LogObject obj = null;
        if(tolog){
            obj = new LogPWMObject(pin, "analogWrite", (short)-1, (short)value, (byte)-1);
        }
        doSend(fsp, obj);
    }

    public final void sendping() {
        doSend(JArduinoProtocol.createPing(), null);
    }

    public final void receiveMessage(byte[] packet){
        FixedSizePacket data = JArduinoProtocol.createMessageFromPacket(packet);
        if (data != null) {
            ((AndroidJArduinoGUI)mActivity).addToReadLog(data.toString());
        }
    }

    //Triggered when Run is pressed
    public void executeOrders(LogAdapter loop, LogAdapter setup){
        if(runner != null && runner.isAlive()){
            runner.setPause(false);
        } else {
            //Main loop list
            List<LogObject> toDo = new ArrayList<LogObject>();
            //Setup List
            List<LogObject> toSetup = new ArrayList<LogObject>();

            for(int i=0; i< loop.getCount(); i++){
                toDo.add(loop.getItem(i).getmObject());
            }
            for(int i=0; i< setup.getCount(); i++){
                toSetup.add(setup.getItem(i).getmObject());
            }

            runner = new CommandExecuter(this, toDo, toSetup);
            runner.start();
        }
    }

    public void pause(){
        runner.setPause(true);
    }

    public void stop(){
        if(runner != null){
            runner.setStop(true);
            runner = null;
        }
    }

    //Methods defined in the Observer pattern specific to JArduino
    public void receiveMsg(byte[] msg) {
        receiveMessage(msg);
    }

    public void register(JArduinoClientObserver observer) {
        handlers.add(observer);
    }

    public void unregister(JArduinoClientObserver observer) {
        handlers.remove(observer);
    }

    public void unregisterAll(){
        AndroidBluetooth4JArduino temp;
        for(JArduinoClientObserver handler: handlers){
            temp = (AndroidBluetooth4JArduino) handler;
            temp.close();
        }
        handlers.clear();
    }

    //To choose the file.
    private void showFileDialog(final String rw, final LogAdapter loop, final LogAdapter setup){
        FileChooserDialog dialog = new FileChooserDialog(AndroidJArduinoGUI.ME);
        dialog.setCanCreateFiles(true);
        dialog.addListener(new FileChooserDialog.OnFileSelectedListener() {
            //File is chosen
            public void onFileSelected(Dialog source, File file) {
                source.hide();

                if(rw.equals("read")){   //Read file ?
                    listsFromFile(file, loop, setup);
                } else {                 //Write file ?
                    listsToFile(file, loop, setup);
                }
            }

            //New file is created
            public void onFileSelected(Dialog source, File folder, String name) {
                source.hide();

                if(rw.equals("read")){   //Read ? (strange... but why not =)
                    listsFromFile(new File(folder, name), loop, setup);
                } else {                 //Write ?
                    listsToFile(new File(folder, name), loop, setup);
                }
            }
        });
        dialog.show();
    }

    public void toFile(LogAdapter loop, LogAdapter setup){
        showFileDialog("write", loop, setup);
    }

    private void listsToFile(File file, LogAdapter loop, LogAdapter setup){
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            AndroidJArduinoGUI.ME.showError("File Open Issue","This file or the folder may be protected.");
        }

        if(output == null){
            Log.d(TAG, "open issue: "+file.getPath());
            return;
        }

        try {
            output.write("{".getBytes());
            for(int i=0; i<loop.getCount(); i++){
                LogObject o = loop.getItem(i).getmObject();
                if(o != null)
                    output.write(o.toString().getBytes());
            }
            output.write("}{".getBytes());
            for(int i=0; i<setup.getCount(); i++){
                LogObject o = setup.getItem(i).getmObject();
                if(o != null)
                    output.write(o.toString().getBytes());
            }
            output.write("}".getBytes());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * The format of the file is this one:
     *
     * {word1[data(),data(1)...]word2[...]word3[...]...}{wordi[...]wordj[...]...}
     *
     */



    public void fromFile(LogAdapter loop, LogAdapter setup){
        showFileDialog("read", loop, setup);
    }

    private void listsFromFile(File file, LogAdapter loop, LogAdapter setup){

        StringBuilder fileContent = new StringBuilder("");
        byte[] buffer = new byte[1024];
        FileInputStream input = null;

        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(input == null){
            Log.d(TAG, "open issue: "+file.getAbsolutePath());
            return;
        }

        try {
            while (input.read(buffer) != -1) {
                fileContent.append(new String(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String word;
        int pointer = 0;

        //handle the { and }.
        LogAdapter adapter = loop;
        while(pointer < fileContent.lastIndexOf("]")){
            word = fileContent.substring(pointer);
            word = word.substring(0, word.indexOf("["));

            if(word.charAt(0) == '{'){
                //Beginning of the file
                word = word.substring(1);
            }
            if(word.charAt(0) == '}'){
                //We are between the two lists
                word = word.substring(2);
                //So we switch the adapter
                adapter = setup;
            }

            pointer += fileContent.substring(pointer).indexOf("[")+1;
            int finalPointer = fileContent.substring(pointer).indexOf("]") + pointer;
            String object = fileContent.substring(pointer, finalPointer);
            finalPointer ++;

            String data[] = object.split(",");
            LogObject o = null;

            if(word.equals("Delay")){
                o = new LogDelayObject();
            }
            if(word.equals("Digital")){
                o = new LogDigitalObject();
            }
            if(word.equals("Analog")){
                o = new LogAnalogObject();
            }
            if(word.equals("Pwm")){
                o = new LogPWMObject();
            }
            addOrder(o, data, adapter);
            pointer = finalPointer;
        }

        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addOrder(LogObject lo, String[] data, LogAdapter adapter){
        if(lo instanceof LogDelayObject){
            lo.setMode(data[0]);
            lo.setVal(Short.parseShort(data[1]));
            addToLogger(lo.toLog(), lo, adapter);
            return;
        }
        if(lo instanceof LogAnalogObject)
            ((LogAnalogObject)lo).setPin(AnalogPin.valueOf(data[0]));
        if(lo instanceof LogDigitalObject)
            ((LogDigitalObject)lo).setPin(DigitalPin.valueOf(data[0]));
        if(lo instanceof LogPWMObject)
            ((LogPWMObject)lo).setPin(PWMPin.valueOf(data[0]));
        lo.setAddr(Short.parseShort(data[1]));
        lo.setB(Byte.parseByte(data[2]));
        lo.setMode(data[3]);
        lo.setVal(Short.parseShort(data[4]));
        addToLogger(lo.toLog(), lo, adapter);
    }

    public void clearOrders(){
        orders.clear();
    }
}
