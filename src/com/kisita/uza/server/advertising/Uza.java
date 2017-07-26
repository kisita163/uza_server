package com.kisita.uza.server.advertising;

import java.io.InputStream;

public class Uza {
	
	public static void main(String[] args) {
		Request req = new Request(SearchIndex.ELECTRONICS,"Solar","Images,ItemAttributes,Offers");
	    System.out.println(req.getRequestUrl(1));
	    
	    InputStream xml = req.getXmlStream(1);
	    /*
	     * Get stream from Amazon server
	     */  
	    if(xml == null){
	    	System.out.println("Could not get xml stream form Amazon server");
	    	return;
	    }
	    
	    /*
	     * Parse received stream
	     */
	    try {
			AmazonXmlParser parser = new AmazonXmlParser(xml);
			System.out.println("The number of results is : "+parser.getTotalResults());
			System.out.println("The number of pages is : "+parser.getTotalPages());
		} catch (Exception e) {
			System.out.println("Could not parse the received stream");
			e.printStackTrace();
		}
	}
}
