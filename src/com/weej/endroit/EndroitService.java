package com.weej.endroit;

import java.util.Map;
import java.util.HashMap;

import android.os.IBinder;
import android.content.Intent;

import android.app.Service;
import android.widget.Toast;
import android.util.Log;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.PhoneNumberUtils;

public class EndroitService extends Service 
{
	// static vars
	private static final String TAG = "EndroitService";
	
	// member vars
	private EndroitPhoneStateListener listener;
	private TelephonyManager telephonyMgr;
	private Map<String, Location> callHistory;
	
	@Override
    public IBinder onBind(Intent arg0) {
          return null;
    }
	
    @Override
    public void onCreate() {
    	super.onCreate();
    	Log.d(TAG, "Service created...started");
        Toast.makeText(this, "Endroit Caller-ID Started", Toast.LENGTH_LONG).show();
          
        // create PhoneStateListener and register it
        listener = new EndroitPhoneStateListener(this);
        telephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
        telephonyMgr.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        
        // cache of incoming calls
        callHistory = new HashMap<String, Location>();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "Service destroyed...stopped");
        Toast.makeText(this, "Endroit Caller-ID Stopped", Toast.LENGTH_LONG).show();
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
    
    public class EndroitPhoneStateListener extends PhoneStateListener 
    { 
    	private Service service;
    	
    	public EndroitPhoneStateListener(Service ser) {
    		this.service = ser;
    	}
    	
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
            	
            	// check if in cache
            	Location loc = callHistory.get(incomingNumber);
            	
            	if (loc == null) {
            		Log.d(TAG, "Number does not exist in cache - will perform reverse look-up");
            		loc = WhitePages.getInstance().reverseLookup(incomingNumber);
            		if (loc != null) {
            			callHistory.put(incomingNumber, loc);
            		}
            	}
            
            	Log.i(TAG, "Corresponding location: " + loc);            	
            	Toast.makeText(service, loc.toString(), Toast.LENGTH_LONG).show();        	// TODO create own custom Toaster     	
            }
        } 
    } 
}