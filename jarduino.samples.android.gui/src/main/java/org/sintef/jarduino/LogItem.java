package org.sintef.jarduino;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 29/08/13
 * Time: 12:05
 */
public class LogItem{

    LogObject mObject;
    String text;

    public LogItem(String txt, LogObject obj) {
        text = txt;
        mObject = obj;
    }

    public void setmObject(LogObject object){
        mObject = object;
    }

    public LogObject getmObject(){
        return mObject;
    }

    @Override
    public String toString(){
        return text;
    }
}
