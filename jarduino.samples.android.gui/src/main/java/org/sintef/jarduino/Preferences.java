package org.sintef.jarduino;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Simple preference screen for general settings of the UI
 */
public class Preferences extends PreferenceActivity {

    static public class PreferencesFragment extends PreferenceFragment {
        @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            final SharedPreferences.Editor prefEditor = getPreferenceManager().getSharedPreferences().edit();

            final EditTextPreference bluetoothDevice = (EditTextPreference) findPreference(getString(R.string.pref_bt_device));

            bluetoothDevice.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object o) {
                    prefEditor.putString(getString(R.string.pref_bt_device), (String)o);
                    AndroidJArduinoGUI.deviceName = (String)o;
                    AndroidJArduinoGUI.ME.refreshConnection();
                    return true;
                }
            });
		}

        public void onDestroy(){
            super.onDestroy();
        }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
	}
}
