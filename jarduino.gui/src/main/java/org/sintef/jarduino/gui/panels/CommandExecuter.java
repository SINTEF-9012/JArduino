package org.sintef.jarduino.gui.panels;

import java.util.LinkedList;

import javax.swing.JButton;

import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.PinMode;
import org.sintef.jarduino.gui.InteractiveJArduinoDataControllerClientAdvanced;
import org.sintef.jarduino.gui.LogObject;
import org.sintef.jarduino.gui.PWMPanel;

public class CommandExecuter extends Thread{
	private InteractiveJArduinoDataControllerClientAdvanced ijadcca;
	private LinkedList<LogObject> linkedList;
	private JButton b;
	public CommandExecuter(InteractiveJArduinoDataControllerClientAdvanced ijadcca){
		this.ijadcca = ijadcca;
	}

	
	public CommandExecuter(InteractiveJArduinoDataControllerClientAdvanced ijadcca, LinkedList<LogObject> linkedList, JButton b) {
		this.ijadcca = ijadcca;
		this.linkedList = linkedList;
		this.b = b;
	}


	public void run(){
		b.setEnabled(false);
			for(int i = 0; i < linkedList.size(); i++){
				executeObjectCommand(linkedList.get(i));
			}
			b.setEnabled(true);
	}
	
	
	void executeObjectCommand(LogObject o) {

		if(o.getMode().startsWith("delay")){
			long start = System.currentTimeMillis();
			while((System.currentTimeMillis() - start) <= o.getVal()){
			}
		}
		if(o.getMode().equals("input")){
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.sendpinMode(PinMode.INPUT, p.getPin());
		}
		if(o.getMode().equals("low") ){
			DigitalPanel p = (DigitalPanel) o.getP();
			o.getP().setDigitalStatusLow();
			ijadcca.senddigitalWrite(p.getPin(), DigitalState.LOW);
		}
		if(o.getMode().equals("analogRead")){
			AnalogPanel p = (AnalogPanel) o.getP();
			ijadcca.sendanalogRead(p.getAnalogPin());
		}
		if(o.getMode().equals("tone")){
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.sendtone( p.getPin(), o.getAddr(), o.getVal());
		}
		if(o.getMode().equals("output")){
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.sendpinMode(PinMode.OUTPUT, p.getPin());
		}
		if( o.getMode().equals("digitalRead")){
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.senddigitalRead(p.getPin());
		}
		if(o.getMode().equals("noTone")){
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.sendnoTone(p.getPin());
		}
		if(o.getMode().equals("high")){
			o.getP().setDigitalStatusHigh();
			DigitalPanel p = (DigitalPanel) o.getP();
			ijadcca.senddigitalWrite(p.getPin(), DigitalState.HIGH);
		}
		if(o.getMode().equals("analogWrite")){
			PWMPanel p = (PWMPanel) o.getP();
			o.getP().setAnalogWrite(o.getVal());
			ijadcca.sendanalogWrite(p.getPWMPin(), (byte)o.getVal());
		}
		//TODO implement running of while loops and if tests in emulator mode
	}
}
