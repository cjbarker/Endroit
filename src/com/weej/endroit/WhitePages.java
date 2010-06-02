package com.weej.endroit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class WhitePages extends ReverseLookup 
{
	// static vars
	private static final String TAG = "WhitePages";
	private static final WhitePages SINGLETON = new WhitePages();
	private static final String URL = "http://www.whitepages.com/search/ReversePhone?full_phone=";
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(".(location_only_unavailable\">\\s*<h3>)\\(?([0-9]{3})\\)?\\s?([0-9]{3}(-)?[0-9]{4}).");
	private static final Pattern CITY_STATE_PATTERN = Pattern.compile(".(location_unavailable\"><b>)(.*),\\s*(.*)(</b>).");
	
	// member vars
	
	private WhitePages() {}
	
	public static WhitePages getInstance() {
		return SINGLETON;
	}
	
	/**
	 * Does reverse lookup on given phone number by checking against WhitePages
	 * @param phoneNumber	Number to lookup
	 */
	public Location reverseLookup(String phoneNumber) {
		Log.d(TAG, "reverse lookup for phone number: "+ phoneNumber);
		
		Location loc = null;
		URL url = null;
		
		try {
			url = new URL(URL+phoneNumber);
			String content = connect(url);
			
			if (content != null) 
			{
				Log.d(TAG, "WhitePages content: \n"+content);
				
				loc = new Location();
				
				// parse phone number
				Matcher m1 = PHONE_NUMBER_PATTERN.matcher(content);
				if (m1.find() && m1.groupCount() >= 3) {
					loc.setAreaCode(m1.group(2));
					loc.setPrefixSuffix(m1.group(3));
				}
				
				// parse city, state
				Matcher m2 = CITY_STATE_PATTERN.matcher(content);
				if (m2.find() && m2.groupCount() >= 3) {
					loc.setCity(m2.group(2));
					loc.setState(m2.group(3));
				}
			}
		}
		catch (MalformedURLException mue) {
			Log.e(TAG, mue.getMessage());
		}
		
		return loc;
	}
}
