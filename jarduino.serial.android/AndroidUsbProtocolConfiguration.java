package org.sintef.jarduino.comm;

import org.sintef.jarduino.ProtocolConfiguration;

import android.content.Context;

public class AndroidUsbProtocolConfiguration extends ProtocolConfiguration {
	private Context context;
	
	public Context getContext(){
		return this.context;
	}
	
	public AndroidUsbProtocolConfiguration(Context context){
		this.context = context;
	}
}
