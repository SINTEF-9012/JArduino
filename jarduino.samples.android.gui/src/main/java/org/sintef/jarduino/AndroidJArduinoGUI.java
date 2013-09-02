package org.sintef.jarduino;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.*;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import org.sintef.jarduino.comm.AndroidBluetooth4JArduino;
import org.sintef.jarduino.comm.AndroidBluetoothConfiguration;

import java.io.IOException;
import java.util.*;

public class AndroidJArduinoGUI extends Activity {

    private static String TAG = "android.gui";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    private String mUUID = "00001101-0000-1000-8000-00805F9B34FB"; //Special code, do not
    //change unless you know what you're doing.

    private String deviceName = "FireFly-4101";
    private int REQUEST_ENABLE_BT = 2000; //What you want here.
    private final static int MENU_DELETE_ID = Menu.FIRST + 1;
    List<Button> buttons = new ArrayList<Button>();
    Button ping, run, save, reset, clear, load, delete;
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;
    LogAdapter logger;
    SwipeListView logList;
    Dialog dialog = null;
    public static String loadFile;
    public static String saveFile;
    String[] menuContent = {
            "PinMode INPUT",
            "PinMode OUTPUT",
            "Digital HIGH",
            "Digital LOW",
            "Digital READ",
            "Analog READ",
            "Analog WRITE"
    };
    Map<String, AnalogPin> analogIn = new Hashtable<String, AnalogPin>(){{
        put("pinA0", AnalogPin.A_0);
        put("pinA1", AnalogPin.A_1);
        put("pinA2", AnalogPin.A_2);
        put("pinA3", AnalogPin.A_3);
        put("pinA4", AnalogPin.A_4);
        put("pinA5", AnalogPin.A_5);
    }};
    Map<String, DigitalPin> digital = new Hashtable<String, DigitalPin>(){{
        put("pin2", DigitalPin.PIN_2);
        put("pin3", DigitalPin.PIN_3);
        put("pin4", DigitalPin.PIN_4);
        put("pin5", DigitalPin.PIN_5);
        put("pin6", DigitalPin.PIN_6);
        put("pin7", DigitalPin.PIN_7);
        put("pin8", DigitalPin.PIN_8);
        put("pin9", DigitalPin.PIN_9);
        put("pin10", DigitalPin.PIN_10);
        put("pin11", DigitalPin.PIN_11);
        put("pin12", DigitalPin.PIN_12);
        put("pin13", DigitalPin.PIN_13);
        put("pinA0", DigitalPin.A_0);
        put("pinA1", DigitalPin.A_1);
        put("pinA2", DigitalPin.A_2);
        put("pinA3", DigitalPin.A_3);
        put("pinA4", DigitalPin.A_4);
        put("pinA5", DigitalPin.A_5);
    }};
    Map<String, PWMPin> analogOut = new Hashtable<String, PWMPin>(){{
        put("pin3", PWMPin.PWM_PIN_3);
        put("pin5", PWMPin.PWM_PIN_5);
        put("pin6", PWMPin.PWM_PIN_6);
        put("pin9", PWMPin.PWM_PIN_9);
        put("pin10", PWMPin.PWM_PIN_10);
        put("pin11", PWMPin.PWM_PIN_11);
    }};
    private String clickedButton = null;

    private GUIController mController = null;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_arduino);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        loadFile = sp.getString(getString(R.string.pref_loadfile), ".file");
        saveFile = sp.getString(getString(R.string.pref_savefile), ".file");

        setContentView(R.layout.mainnew);
        logList = (SwipeListView) findViewById(R.id.log);
        logger = new LogAdapter(getApplicationContext(), R.layout.logitem);
        logList.setAdapter(logger);
        logList.setVerticalScrollBarEnabled(true);
        registerForContextMenu(logList);
        ((LinearLayout)logList.getParent()).setVerticalScrollBarEnabled(true);

        if (Build.VERSION.SDK_INT >= 11) {
            logList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            logList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + logList.getCountSelected() + ")");
                }

                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_delete) {
                        logList.dismissSelected();
                        return true;
                    }
                    return false;
                }

                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_choice_items, menu);
                    return true;
                }

                public void onDestroyActionMode(ActionMode mode) {
                    logList.unselectedChoiceStates();
                }

                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        logList.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onListChanged() {
                logList.closeOpenedItems();
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    logger.remove(logger.getItem(position));
                }
                logger.notifyDataSetChanged();
            }
        });

        /* USELESS
        ViewHelper vh;*/

        initButtons();

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        BluetoothDevice mmDevice = null;

        //List<String> mArray = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if(device.getName().equals(deviceName))
                    mmDevice = device;
                //mArray.add(device.getName() + "\n" + device.getAddress());
            }
        }

        //Creating the socket.
        final BluetoothSocket mmSocket;
        BluetoothSocket tmp = null;

        UUID myUUID = UUID.fromString(mUUID);
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) { }
        mmSocket = tmp;

        //socket created, try to connect

        try {
            mmSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Thread mThread = new Thread(){
            @Override
            public void run() {
                super.run();

                mController = new GUIController(logList, AndroidJArduinoGUI.this);
                AndroidBluetooth4JArduino device = new AndroidBluetooth4JArduino(new AndroidBluetoothConfiguration(mmSocket));
                mController.register(device);
                device.register(mController);
            }
        };
        mThread.run();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_DELETE_ID, 0, "Delete from log");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case MENU_DELETE_ID:
                logger.remove(logger.getItem(info.position));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences:
                startActivity(new Intent(getApplicationContext(), Preferences.class));
                return true;
        }
        return false;
    }

    void initButtons(){
        buttons.add(((Button) findViewById(R.id.pin2)));
        buttons.add(((Button) findViewById(R.id.pin3)));
        buttons.add(((Button) findViewById(R.id.pin4)));
        buttons.add(((Button) findViewById(R.id.pin5)));
        buttons.add(((Button) findViewById(R.id.pin6)));
        buttons.add(((Button) findViewById(R.id.pin7)));
        buttons.add(((Button) findViewById(R.id.pin8)));
        buttons.add(((Button) findViewById(R.id.pin9)));
        buttons.add(((Button) findViewById(R.id.pin10)));
        buttons.add(((Button) findViewById(R.id.pin11)));
        buttons.add(((Button) findViewById(R.id.pin12)));
        buttons.add(((Button) findViewById(R.id.pin13)));
        buttons.add(((Button) findViewById(R.id.pinA0)));
        buttons.add(((Button) findViewById(R.id.pinA1)));
        buttons.add(((Button) findViewById(R.id.pinA2)));
        buttons.add(((Button) findViewById(R.id.pinA3)));
        buttons.add(((Button) findViewById(R.id.pinA4)));
        buttons.add(((Button) findViewById(R.id.pinA5)));
        ping = (Button)findViewById(R.id.ping);
        run = (Button)findViewById(R.id.run);
        save = (Button)findViewById(R.id.save);
        load = (Button)findViewById(R.id.load);
        clear = (Button)findViewById(R.id.clear);
        reset = (Button)findViewById(R.id.reset);
        delete = (Button)findViewById(R.id.delete);

        for(final Button b : buttons){
            b.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View arg0) {
                    clickedButton = b.getText().toString();
                    showDialog(CUSTOM_DIALOG_ID);
                }
            });
        }
        ping.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                mController.sendping();
            }
        });
        save.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                mController.toFile();
            }
        });
        load.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                mController.fromFile();
            }
        });
        run.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                mController.executeOrders();
            }
        });
        clear.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                ((LogAdapter) logList.getAdapter()).clear();
                ((LogAdapter) logList.getAdapter()).notifyDataSetChanged();
            }
        });
        reset.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                mController.resetFile();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialoglayout);

                final TextView tv = ((TextView) findViewById(R.id.textView));

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        /*Toast.makeText(AndroidJArduinoGUI.this,
                                "OnCancelListener",
                                Toast.LENGTH_LONG).show();*/
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    public void onDismiss(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        /*Toast.makeText(AndroidJArduinoGUI.this,
                                "OnDismissListener",
                                Toast.LENGTH_LONG).show();*/
                    }
                });

                //Prepare ListView in dialog
                dialog_ListView = (ListView)dialog.findViewById(R.id.dialoglist);
                ArrayAdapter<String> adapter
                        = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_item, menuContent);
                dialog_ListView.setAdapter(adapter);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String pin = clickedButton;
                        DigitalPin dPin;
                        AnalogPin aPin;
                        PWMPin pPin;
                        switch(position){
                            case 0:
                                dPin = digital.get(pin);
                                if(dPin != null)
                                    mController.sendpinMode(PinMode.INPUT, dPin);
                                break;
                            case 1:
                                dPin = digital.get(pin);
                                if(dPin != null)
                                    mController.sendpinMode(PinMode.OUTPUT, dPin);
                                break;
                            case 2:
                                dPin = digital.get(pin);
                                if(dPin != null)
                                    mController.senddigitalWrite(dPin, DigitalState.HIGH);
                                break;
                            case 3:
                                dPin = digital.get(pin);
                                if(dPin != null)
                                    mController.senddigitalWrite(dPin, DigitalState.LOW);
                                break;
                            case 4:
                                dPin = digital.get(pin);
                                if(dPin != null)
                                    mController.senddigitalRead(dPin);
                                break;
                            case 5:
                                aPin = analogIn.get(pin);
                                if(aPin != null)
                                    mController.sendanalogRead(aPin);
                                break;
                            case 6:
                                int analogValue = Integer.parseInt(tv.getText().toString());
                                if(analogValue>255){
                                    break;
                                }
                                pPin = analogOut.get(pin);
                                if(pPin != null)
                                    mController.sendanalogWrite(pPin, Integer.valueOf(analogValue).byteValue());
                                break;
                        }

                        dismissDialog(CUSTOM_DIALOG_ID);
                    }
                });

                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        // TODO Auto-generated method stub
        super.onPrepareDialog(id, dialog, bundle);
        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog.setTitle("Action on " + clickedButton);
                break;
        }

    }

}

