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

package org.sintef.jarduino.gui.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.gui.LogObject;
import org.sintef.jarduino.gui.PWMPanel;
import org.sintef.jarduino.gui.panels.AnalogPanel;
import org.sintef.jarduino.gui.panels.DigitalPanel;



public class CodeGenerator {
	private String progName = "Default";
	private Stack<String> stackJava, stackArduino;
	private HashMap<String, String> imports;
	private LinkedList<LogObject> linkedList;
	private String intendation = "\t\t";
	private HashMap<String, String[]> variables;
	private HashMap<DigitalPin, LogObject> pinModes;
	private File javaFile, arduinoFile;
	private FileWriter fwJava, fwArduino;
	private BufferedWriter bfwJava, bfwArduino;
	private int closedLoops = 0;
	
	public CodeGenerator(LinkedList<LogObject> linkedL, File file) {
		
		linkedList = new LinkedList<LogObject>();
		stackJava = new Stack<String>();
		stackArduino = new Stack<String>();
		linkedList.addAll(linkedL);
		pinModes = new HashMap<DigitalPin, LogObject>();
		imports = new HashMap<String, String>();
		variables = new HashMap<String, String[]>();
		
		if(file.getName().contains(".")){
			file = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(".")));
		}
		
		javaFile = new File(file.getAbsolutePath() + ".java");
		arduinoFile = new File(file.getAbsolutePath() + ".pde");
		
		
		progName = file.getName().replace(".java", "");
		
		try {
			fwJava = new FileWriter(javaFile);
			bfwJava = new BufferedWriter(fwJava);
			fwArduino = new FileWriter(arduinoFile);
			bfwArduino = new BufferedWriter(fwArduino);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addImports();
		findPinModes();
		parceSource();
		closeOpenLoops();
		writeSourceJava();
		writeSourceArduino();
		
		try{
			bfwJava.close();
			fwJava.close();
			bfwArduino.close();
			fwArduino.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Closing all open loops and tests
	private void closeOpenLoops() {
		while(closedLoops > 0){
			intendation = intendation.replaceFirst("\t", "");
			stackJava.add(intendation  +  "}");
			stackArduino.add(intendation.replaceFirst("\t", "") + "}");
			closedLoops--;
		}
		
	}

	private void addImports() {
		imports.put("Serial4Jarduino","import org.sintef.jarduino.comm.Serial4JArduino;");
		imports.put("JArduino", "import org.sintef.jarduino.JArduino;");
	}
	
	//Writing the Arduino source code
	public void writeSourceArduino(){
		writeCodeArduino("public void setup(){\n");
		for(LogObject o : pinModes.values()){
			DigitalPanel p = (DigitalPanel) o.getP();
			writeCodeArduino("\tpinMode(" + p.getPin().getValue() + ", PinMode." +  o.getMode().toUpperCase() + ");\n");
		}
		
		writeCodeArduino("}\n\n");
		
		writeCodeArduino("public void loop(){\n");
		while(stackArduino.size() > 0){
			writeCodeArduino(stackArduino.remove(0) + "\n");
		}
		writeCodeArduino("}");
	}
	
	//Writes theJArduino source code
	private void writeSourceJava() {
		//imports
		for(String s : imports.values()){
			writeCodeJava(s + "\n");
		}
		writeCodeJava("\n\n\n");
		
		//Class and constructor
		writeCodeJava("public class " + progName + " extends JArduino {\n");

		writeCodeJava("\tpublic "+ progName +"(String port) {\n");
		writeCodeJava("\t\tsuper(port);\n\t}\n\n");
	
		//setup
		writeCodeJava("\tprotected void setup(){\n");
		for(LogObject o : pinModes.values()){
			DigitalPanel p = (DigitalPanel) o.getP();
			writeCodeJava("\t\tpinMode(DigitalPin." + p.getPin() + ", PinMode." +  o.getMode().toUpperCase() + ");\n");
		}
		writeCodeJava("\t}\n\n");
		//loop
		writeCodeJava("\tprotected void loop(){\n");
		while(stackJava.size() > 0){
			writeCodeJava(stackJava.remove(0) + "\n");
		}
		writeCodeJava("\t}\n\n");
		writeCodeJava("\tpublic static void main(String[] args) {\n"+
	        "\t\tString serialPort;\n"+
	        "\t\tif (args.length == 1) {\n"+
	        "\t\t\tserialPort = args[0];\n"+
	        "\t\t} else {\n"+
	        "\t\t\tserialPort = Serial4JArduino.selectSerialPort();\n"+
	        "\t\t}\n"+
	        "\t\tJArduino arduino = new "+progName+"(serialPort);\n"+
	        "\t\tarduino.runArduinoProcess();\n"+
	    	"\t}\n");
		
		writeCodeJava("}");
	}
	
	/*
	 * Goes through the list of opperations and add each operation to
	 * bith the Arduino source and the JArduino source
	 */
	private void parceSource() {
		for(int i = 0; i < linkedList.size(); i++){
			if(linkedList.get(i).getMode().equals("input") || linkedList.get(i).getMode().equals("output")){
				LogObject o = linkedList.get(i);
				DigitalPanel p = (DigitalPanel) o.getP();
				stackJava.add(intendation + "pinMode(DigitalPin." + p.getPin() + ", PinMode." +  o.getMode().toUpperCase() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + "pinMode(" + p.getPin().getValue() + ", PinMode." +  o.getMode().toUpperCase() + ");");
			}
			
			if(linkedList.get(i).getMode().equals("high") || linkedList.get(i).getMode().equals("low")){
				imports.put("digitalstate", "import org.sintef.jarduino.DigitalState;");
				LogObject o = linkedList.get(i);
				DigitalPanel p = (DigitalPanel) o.getP();
				stackJava.add(intendation + "digitalWrite(DigitalPin." + p.getPin() + ", DigitalState." +  o.getMode().toUpperCase() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + "digitalWrite(" + p.getPin().getValue() + ", " +  o.getMode().toUpperCase() + ");");
			}
			
			if(linkedList.get(i).getMode().equals("tone")){
				LogObject o = linkedList.get(i);
				DigitalPanel p = (DigitalPanel) o.getP();
				stackJava.add(intendation + "tone(DigitalPin." + p.getPin() + ", (short)" +  o.getAddr() + ", (short)" + o.getVal() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + "tone(" + p.getPin().getValue() + ", " +  o.getAddr() + ", " + o.getVal() + ");");
			}
			if(linkedList.get(i).getMode().equals("noTone")){
				LogObject o = linkedList.get(i);
				DigitalPanel p = (DigitalPanel) o.getP();
				stackJava.add(intendation + "noTone(DigitalPin." + p.getPin() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + "noTone(" + p.getPin().getValue() + ");");
			}
			if(linkedList.get(i).getMode().equals("digitalRead")){
				imports.put("digitalstate", "import org.sintef.jarduino.DigitalState;");
				LogObject o = linkedList.get(i);
				DigitalPanel p = (DigitalPanel) o.getP();
				String[] var = findVariableName("DigitalState", "digitalRead", p.getPin());
				stackJava.add(intendation + var[0]  + " " + var[1] + " = digitalRead(DigitalPin." + p.getPin() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + var[2]  + " " + var[1] + " = digitalRead("+ p.getPin().getValue() + ");");
			}
			if(linkedList.get(i).getMode().equals("analogRead")){
				imports.put("analogpin", "import org.sintef.jarduino.AnalogPin;");
				LogObject o = linkedList.get(i);
				AnalogPanel p = (AnalogPanel) o.getP();
				String[] var = findVariableName("short", "analogRead", p.getPin());
				stackJava.add(intendation + var[0] + " " + var[1] + " = analogRead(AnalogPin." + p.getPin() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + var[2] + " "+ var[1] + " = analogRead("+ (p.getPin().getValue()-14) + ");");
			}
			if(linkedList.get(i).getMode().equals("analogWrite")){
				imports.put("pwmpin", "import org.sintef.jarduino.PWMPin;");
				LogObject o = linkedList.get(i);
				PWMPanel p = (PWMPanel) o.getP();
				stackJava.add(intendation + "analogWrite(PWMPin." + p.getPWMString() + ", " + o.getVal() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + " analogWrite("+ p.getPin().getValue() + ", " + o.getVal()+ ");");
			}
			if(linkedList.get(i).getMode().startsWith("delay")){
				LogObject o = linkedList.get(i);
				stackJava.add(intendation + "delay(" + o.getVal() + ");");
				stackArduino.add(intendation.replaceFirst("\t", "") + "delay(" +o.getVal()+ ");");
			}
			if(linkedList.get(i).getMode().startsWith("if")){
				LogObject o = linkedList.get(i);
				stackJava.add(intendation +  o.getMode());
				stackArduino.add(intendation.replaceFirst("\t", "") + o.getMode().replace("DigitalState.", ""));
				intendation += "\t";
				closedLoops++;
			}
			if(linkedList.get(i).getMode().startsWith("}else if")){
				LogObject o = linkedList.get(i);
				stackJava.add(intendation.replaceFirst("\t", "") +  o.getMode());
				stackArduino.add(intendation.replaceFirst("\t", "") + o.getMode().replace("DigitalState.", ""));
				intendation += "\t";
			}else if(linkedList.get(i).getMode().startsWith("}else")){ 
				LogObject o = linkedList.get(i);
				stackJava.add(intendation.replaceFirst("\t", "")  +  o.getMode());
				stackArduino.add(intendation.replaceFirst("\t", "") + o.getMode());
			}else if(linkedList.get(i).getMode().startsWith("}")){
				LogObject o = linkedList.get(i);
				intendation = intendation.replaceFirst("\t", "");
				stackJava.add(intendation  +  o.getMode());
				stackArduino.add(intendation.replaceFirst("\t", "") + o.getMode());
				closedLoops--;
			}
			//Handles special cases for the while loop
			if(linkedList.get(i).getMode().startsWith("while")){
				LogObject o = linkedList.get(i);
				String whileTest = o.getMode();
				String whileTestArduino = "";
				for(String s : variables.keySet()){
					if(whileTest.contains(s)){
						String[] arr = variables.get(s);
						if(whileTest.contains("digitalRead")){
							whileTestArduino = whileTest.replaceFirst(s, "(" + s + " = analogRead(" + arr[3] + "))");
							whileTest = whileTest.replaceFirst(s, "(" + s + " = digitalRead(DigitalPin." + arr[2] + "))");
						}else{
							whileTestArduino = whileTest.replaceFirst(s, "(" + s + " = analogRead(" + (Integer.parseInt(arr[3])-14) + "))");
							whileTest = whileTest.replaceFirst(s, "(" + s + " = analogRead(AnalogPin." + arr[2] + "))");
						}
					}
				}
				stackJava.add(intendation +  whileTest);
				stackArduino.add(intendation.replaceFirst("\t", "") + whileTestArduino.replace("DigitalState.", ""));
				intendation += "\t";
				closedLoops++;
			}
		}
	}
	/*
	 * This method find variable names, and checks if a varable name have been used before
	 */
	private String[] findVariableName(String datatype, String method, DigitalPin digitalPin) {
		String var;// = method;
		boolean test = false;
		String[] result = new String[4];
		result[2] = "int";
		var = checkIfUsedBefore(digitalPin);
		if(var != null){
			test = true;
			result[2] = "";
			result[0] = "";
		}
		String temp = var;
			String[] s = new String[4];
			s[0]= datatype;
			s[1] = var = method;
			s[2]="" + digitalPin + "";
			s[3] = "" + digitalPin.getValue();
			if(method.equals("digitalRead")){
				if(!test){
					if(variables.containsKey(var)){
						int i = 0;
						do{
							i++;
							var = method + "_" + i;
						}while(variables.containsKey(var));
					}
					s[1]= var;
					variables.put(var, s);
					result[1] = var;
					result[0] = "DigitalState"; 
				}
				
			}
			
			if(method.equals("analogRead")){
				if(!test){
					if(variables.containsKey(var)){
						int i = 0;
						do{
							i++;
							var = method + "_" + i;
						}while(variables.containsKey(var));
					}
					s[1]= var;
					variables.put(var, s);
					result[1] = var;
					result[0] = "short";
				}
				
			}
		
		if(test){
			result[1] = temp;
		}
		return result;
	}
	
	/*
	 * Quick check to see if a pin have been used for reading before.
	 * If so return the corresponding variable name.
	 * If the pin is not used before null is returned
	 */
	private String checkIfUsedBefore(DigitalPin digitalPin) {
		for (String[] s : variables.values()){
			if(s[2].equals("" + digitalPin)){
				return s[1];
			}
		}
		return null;
	}
	/*
	 * Meyhod that parces through the code to find occurences of setting input and output on pins.
	 * Pins with a single in/out statement will be placed in the setup loop.
	 * PinMode changes that are occuring more than once will be placed at their corresponding
	 * place in the program flow
	 */
	private void findPinModes() {
		for(int i = 0; i < linkedList.size(); i++){
			LogObject o = linkedList.get(i);
			if(o.getMode().equals("input") || o.getMode().equals("output")){
				imports.put("digitalpin", "import org.sintef.jarduino.DigitalPin;");
				imports.put("pinmode", "import org.sintef.jarduino.PinMode;");
				DigitalPanel p = (DigitalPanel) o.getP();
				if(pinModes.containsKey(p.getPin())){
					pinModes.remove(p.getPin());
				}else{
					pinModes.put(p.getPin(), o);
				}
			}
		}
		removeDuplicatedPinModes();
	}
	
	//TODO FIND AND REMOVE BUG
	private void removeDuplicatedPinModes() {
		if(pinModes.size() > 0){
			for(int i = 0; i < linkedList.size(); i++){
				if(linkedList.get(i).getMode().equals("input") || linkedList.get(i).getMode().equals("output")){
					for(LogObject o2 : pinModes.values()){
						if(linkedList.get(i) == o2){
							linkedList.remove(i);
							i = 0;
						}
					}
				}
			}
		}
	}
	//Two methods that writes the source code to file
	private void writeCodeArduino(String s){
		try {
			bfwArduino.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeCodeJava(String s){
		System.out.print(s);
		try {
			bfwJava.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
