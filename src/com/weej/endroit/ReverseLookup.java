package com.weej.endroit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public abstract class  ReverseLookup 
{
	/**
	 * Does reverse lookup on given phone number by checking against WhitePages
	 * @param phoneNumber	Number to lookup
	 * @return	Location of the number reversed looked up
	 */
	public abstract Location reverseLookup(String phoneNumber);
	
	/**
	 * HTTP GET connection for URL.
	 * @param url	URL to connect to
	 * @return		HTML source/content received or null if error occurred
	 */
	public String connect(URL url) 
	{
		StringBuffer result = new StringBuffer();
		
		URLConnection conn = null;
		BufferedReader in = null;
		
		try {
			conn = url.openConnection();
			conn.setRequestProperty("User-agent", "Mozilla/4.0");
	        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	result.append(inputLine+"\n");
	            //System.out.println(inputLine);
	        }
		}
		catch (IOException ioe) {
			System.err.println(ioe.getMessage());
			;; // TODO LOG
		}
		finally {
			try {
				if (in != null) in.close();
			}
			catch (IOException ioe2) {
				;;
			}
		}
        
		return (result.length() == 0) ? null : result.toString();
	}
}
