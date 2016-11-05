/**
 * Author(s): Jainil Sutaria
 * Date Created: Nov. 4, 2016
 * Date Edited: Nov. 5, 2016
 */
package org.sintef.jarduino;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public enum Pin {
	PIN_0(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 0),
	PIN_1(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 1),
	PIN_2(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Interrupt);}}, (byte) 2),
	PIN_3(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM); add(PinType.Interrupt);}}, (byte) 3),
	PIN_4(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 4),
	PIN_5(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM);}}, (byte) 5),
	PIN_6(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM);}}, (byte) 6),
	PIN_7(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 7),
	PIN_8(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 8),
	PIN_9(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM);}}, (byte) 9),
	PIN_10(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM);}}, (byte) 10),
	PIN_11(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.PWM);}}, (byte) 11),
	PIN_12(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 12),
	PIN_13(new HashSet<PinType>() {{add(PinType.Digital);}}, (byte) 13),
	A_0(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 14),
	A_1(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 15),
	A_2(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 16),
	A_3(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 17),
	A_4(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 18),
	A_5(new HashSet<PinType>() {{add(PinType.Digital); add(PinType.Analog);}}, (byte) 19);
	
	private final Set<PinType> pinType;
	private final byte value;
	
	private Pin(Set<PinType> p, byte v) {
		this.pinType = p;
		this.value = v;
	}
	
	public byte getValue(){
		return value;
	}
	
	private static final Map<Byte, Pin> map;
	
	static {
		map = new HashMap<Byte, Pin>() {{
			put((byte)0, Pin.PIN_0);
			put((byte)1, Pin.PIN_1);
			put((byte)2, Pin.PIN_2);
			put((byte)3, Pin.PIN_3);
			put((byte)4, Pin.PIN_4);
			put((byte)5, Pin.PIN_5);
			put((byte)6, Pin.PIN_6);
			put((byte)7, Pin.PIN_7);
			put((byte)8, Pin.PIN_8);
			put((byte)9, Pin.PIN_9);
			put((byte)10, Pin.PIN_10);
			put((byte)11, Pin.PIN_11);
			put((byte)12, Pin.PIN_12);
			put((byte)13, Pin.PIN_13);
			put((byte)14, Pin.A_0);
			put((byte)15, Pin.A_1);
			put((byte)16, Pin.A_2);
			put((byte)17, Pin.A_3);
			put((byte)18, Pin.A_4);
			put((byte)19, Pin.A_5);
		}};
	}

	public static Pin fromValue(byte b) {
		return map.get(b);
	}
	
	public boolean isAnalog() {
		return this.pinType.contains(PinType.Analog);
	}
	
	public boolean isDigital() {
		return this.pinType.contains(PinType.Digital);
	}
	
	public boolean isPWM() {
		return this.pinType.contains(PinType.PWM);
	}
	
	public boolean isInterrupt() {
		return this.pinType.contains(PinType.Interrupt);
	}
	
}
