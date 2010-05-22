package com.weej.endroit;

import java.util.Map;
import java.util.HashMap;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import android.util.Log;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.PhoneNumberUtils;

public class Endroit extends Activity 
{
	// static vars
	private static final String TAG = "Endroit";
	
	// member vars
	private TextView textOut;
	private Map<String, Location> areaCodes;
	private EndroitPhoneStateListener listener;
	private TelephonyManager telephonyMgr;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get UI
        textOut = (TextView) findViewById(R.id.textOut);
        
        loadAreaCodes();
       
        // create PhoneStateListener and register it
        listener = new EndroitPhoneStateListener();
        telephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
        telephonyMgr.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    
    /**
     * Retrieves all area codes from local database and loads them into memory
     * @param codes	Map to store area codes and corresponding city/state
     */
    private void loadAreaCodes() {
    	if (areaCodes == null) {
    		areaCodes = new HashMap<String, Location>();
    	}
    	
    	// TODO pull area codes from database and load into memory
    	areaCodes.put("512", new Location("Austin", "TX"));
    	areaCodes.put("318", new Location("Lafayatte", "LA"));
    	
    	Log.i(TAG, "Loaded/initialized ["+areaCodes.size()+"] area code corresponding locations into memory");
    }
    
    /**
     * Parses phone number and extracts area code
     * @param phoneNumber	
     * @return Area code of corresponding phone number
     */
    private String extractAreaCode(String phoneNumber) {
    	String nbr = PhoneNumberUtils.stripSeparators(phoneNumber).trim();
    	return nbr.substring(0, 3);
    }
    
    /**
     * Looks up the corresponding area code's location
     * @param areaCode	Area code to search for
     * @return			Location of corresponding area code or null if none found
     */
    private Location lookup(String areaCode) {
    	return this.areaCodes.get(areaCode.trim());
    }
    
    public class EndroitPhoneStateListener extends PhoneStateListener 
    { 
        @Override 
        public void onCallStateChanged(int state, String incomingNumber)
        { 
        	String stateStr = "N/A";
        	
            switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
            	stateStr = "Idle";
              break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
            	stateStr = "Off Hook";
              break;
            case TelephonyManager.CALL_STATE_RINGING:
            	stateStr = "Ringing";
              break;
            }
            
            Log.d(TAG, "CallStateChanged to "+stateStr);
            
            if (state == TelephonyManager.CALL_STATE_RINGING) 
            {
            	/*
            	if (PhoneNumberUtils.isGlobalPhoneNumber(incomingNumber)) {
            		Log.w(TAG, "Is global phone number which is currently unsupported.");
            		return;
            	}
            	*/
            
            	Location loc = lookup(extractAreaCode(incomingNumber));
            	Log.i(TAG, "Corresponding location: " + loc);
            	textOut.append("\nonCallStateChanged: "+stateStr);
            	textOut.append("\nphoneNbr: "+incomingNumber);
            	textOut.append("\nlocation: "+loc);
            	// TODO display overlay image on incoming call screen
            }
        } 
    } 
}