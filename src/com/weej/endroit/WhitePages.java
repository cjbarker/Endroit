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
	private static final String DOMAIN = "http://www.whitepages.com";
	private static final String URL = DOMAIN+"/search/ReversePhone?full_phone=";
	private static final Pattern FULL_LISTING = Pattern.compile(".(id=\"result_1\").");
	private static final Pattern RESULT_LINK = Pattern.compile(".(id=\"result_1\">\\s*<p class=\"name\">\\s*<a href=\")\\s*(.*)\">");
	private static final Pattern FULL_NAME = Pattern.compile(".(<h3 class=\"fn n listing_name\">\\s*<span style=\"position: relative;\">)\\s*(.*)\\s*</span>");
	private static final Pattern ADDRESS = Pattern.compile(".(<p class=\"address\">\\s*.*\\s*.*\\s*.*\\s*)<div>(.*)<br /></div>\\s*<div>(.*)</div>");
	private static final Pattern CITY_STATE_ZIP = Pattern.compile("(.*),\\s?([a-zA-Z]*)\\s?(.*)");
	private static final Pattern PHONE_NUMBER_PATTERN_MOBILE = Pattern.compile(".(location_only_unavailable\">\\s*<h3>)\\(?([0-9]{3})\\)?\\s?([0-9]{3}(-)?[0-9]{4}).");
	private static final Pattern CITY_STATE_PATTERN_MOBILE = Pattern.compile(".(location_unavailable\"><b>)(.*),\\s*(.*)(</b>).");
	
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
				
				Matcher m = FULL_LISTING.matcher(content);
				if (m.find()) {
					parseLoadFullListing(content, loc);
				}
				else {
					parseLoadPartialListing(content, loc);
				}
			}
		}
		catch (MalformedURLException mue) {
			Log.e(TAG, mue.getMessage());
		}
		
		return loc;
	}
	
	/**
	 * 
	 * @param content
	 * @param loc
	 */
	private void parseLoadFullListing(String content, Location loc) 
	{
		Matcher l = RESULT_LINK.matcher(content);
		
		// get link from result then grab content
		if (l.find()) 
		{
			String link = DOMAIN + l.group(2).replaceAll("&amp;", "&");
		    if (link != null && link.trim().length() > 0) 
		    {
		    	try {
		    		URL url = new URL(link);
		    		String newContent = connect(url);
		    		
		    		// get full name
		    		Matcher m = FULL_NAME.matcher(newContent);
		    		if (m.find() && m.groupCount() >= 2) {
		    			Person p = new Person(m.group(2));
		    			loc.setPerson(p);
		    		}
		    		
		    		// get address
		    		Matcher m1 = ADDRESS.matcher(newContent);
		    		if (m1.find() && m1.groupCount() >= 3) 
		    		{
		    			loc.setAddress(m1.group(2));
		    			
		    			// parse city, state zip
		    			Matcher m2 = CITY_STATE_ZIP.matcher(m1.group(3));
		    			if (m2.find() && m2.groupCount() >= 3) {
		    				loc.setCity(m2.group(1));
		    				loc.setState(m2.group(2));
		    				loc.setZipCode(m2.group(3));
		    			}
		    		}
		    	}
		    	catch (MalformedURLException mue) {
		    		Log.e(TAG, mue.getMessage());
		    	}
		    }
		}
	}
	
	/**
	 * 
	 * @param content
	 * @param loc
	 */
	private void parseLoadPartialListing(String content, Location loc) {
		// parse phone number
		Matcher m1 = PHONE_NUMBER_PATTERN_MOBILE.matcher(content);
		if (m1.find() && m1.groupCount() >= 3) {
			loc.setAreaCode(m1.group(2));
			loc.setPrefixSuffix(m1.group(3));
		}
		
		// parse city, state
		Matcher m2 = CITY_STATE_PATTERN_MOBILE.matcher(content);
		if (m2.find() && m2.groupCount() >= 3) {
			loc.setCity(m2.group(2));
			loc.setState(m2.group(3));
		}
	}
}
