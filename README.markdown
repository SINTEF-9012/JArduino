Welcome to the JArduino project, powered by [ThingML](http://www.Thingml.org)!

Browse the dist folder to get the latest JArduino distribution, which you can import as an Eclipse project. 
People interested in the concepts behind JArduino can read our paper ["MDE to manage communications with and between resource-constrained systems"](http://www.fleurey.com/franck/uploads/Main/Models2011a.pdf). 
If you are more into the code, just try it out for real!

	$ git clone https://github.com/SINTEF-9012/JArduino.git


##Serial usage

Before using JArduino, please make sure your Arduino board contains the JArduino firmware:

1. Copy the JArduino folder (located in the distribution in org.sintef.jarduino.samples/arduino) into /libraries/
2. Launch your Arduino environment
3. File -> Examples -> JArduino -> JArduino firmware. It should open an Arduino program that you should upload to your board using the normal Arduino procedure.
4. Your Arduino board is now ready for Jarduino. You can exit the Arduino environment forever and launch Eclipse. Just run the Java/JArduino program.


To test samples using the current version of JArduino (requires Maven + the Arduino board connect on the serial port of the PC):

	$ cd JArduino	
	$ mvn clean install
	
To run some examples without IDE
	
	$ cd jarduino.samples
	$ mvn exec:java -Dexec.mainClass="org.sintef.jarduino.gui.JArduinoGUI"
	
Replace "org.sintef.jarduino.gui.JArduinoGUI" with your own main class


Please read our [2-minute tutorial] (https://github.com/SINTEF-9012/JArduino/wiki/2-Minute-Tutorial)


## Ethernet usage

1. Instead of JArduino folder, copy the JArduinoEthernet folder to Arduino IDE librairies folder
2. Launch Arduino environment
3. File -> Examples -> JArduinoEthernet -> JArduino firmware. It should open an Arduino program that you should 
	4. if you have associated the MAC address of your shield to a static IP in your router, please update the MAC address in the firmware ([line 84](https://github.com/SINTEF-9012/JArduino/blob/master/jarduino.core/src/main/arduino/JArduinoEthernet/examples/JArduinoFirmware/JArduinoFirmware.ino#L84)). Otherwise, DHCP should manage everything.
	5. upload to your board using the normal Arduino procedure.
6. Your Arduino board is now ready for JarduinoEthernet. You can exit the Arduino environment forever and launch Eclipse. Just run the Java/JArduino program.

> JArduinoEthernet does not need  JArduino.serial maven dependency, don't forget to remove it if you want to **use JArduino from an Android application** for instance.

You Can can now use the JArduino constructor to configure the IP of your device. Don't forget to set the communication module to ethernet:

```java
JArduino arduino = new BlinkEthernet(ip, JArduinoCom.Ethernet)
```

To run a sample using Ethernet:

```bash
mvn clean install exec:java -Dexec.mainClass="org.sintef.jarduino.examples.basic.BlinkEthernet" -Dexec.args="<IP-address-of-Arduino-board>"
```

	    

## Android and Bluetooth usage

To run these examples you need to have a recent android platform.
Then you just have to go in the org.sintef.jarduino.samples.android where you can find a basic android application that make a blink on the pin13 of the Arduino and read the value on the pinA0.
This application connects the android platform to the Arduino using a bluetooth adapter so you have to set the name of your bluetooth device into deviceName in the program.
```java
private String deviceName = "NameOfYourBluetoothDevice";
```

You can also see the Android GUI in the folder org.sintef.samples.android.gui. Here also, you must set the bluetooth device name in order to connect to the Arduino.
![Android Arduino GUI](docs/pics/AndroidJarduinoGUI.png?raw=true "Android Arduino GUI")

In the middle of the screen you can see the list of all the orders sent to the Arduino. You can also see the response of this one.

If you click the button Run, all of these orders will be sent again.
Clear: clear the list.
Delay: a pop-up will be open to set the delay you wish.
Save: save the list into th file specified in the settings (top right corner).
Load: load the specified file.
Reset: save an empty file on the save file specified.
Ping: send a ping to the Arduino.

When you click on a Pin Button (blue with white edges) a list of action is displayed and you can choose the one you want to perform.
If you want to make an Analog write, a pop-up will be open to allow you to set the value you want to write.

Make also sure that you are performing an action supported by the Arduino. Unattended action will result in unattended results.


_The ThingML/JArduino team._

## Build status, by Travis CI

<img src="https://api.travis-ci.org/SINTEF-9012/JArduino.png" alt="Build status"/>
