/**
 * Author(s): Jainil Sutaria
 * Date Created: Nov. 4, 2016
 * Date Edited: Nov. 4, 2016
 */
package org.sintef.jarduino;

public class InvalidPinTypeException extends Exception {

	private static final long serialVersionUID = 2931083895533791194L;
	
	public InvalidPinTypeException() { super(); }
	public InvalidPinTypeException(String message) { super(message); }
	public InvalidPinTypeException(String message, Throwable cause) { super(message, cause); }
	public InvalidPinTypeException(Throwable cause) { super(cause); }
}
