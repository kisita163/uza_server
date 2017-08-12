package com.kisita.uza.server.advertising.amazon;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;


public class Amazon implements IRunnableWithProgress{
	
	// The total sleep time
	 private static final int TOTAL_TIME = 10000;
	 
	// The increment sleep time
	  private static final int INCREMENT = 500;
	 
	 private boolean indeterminate;
	 
	 private Request req;
	 
	 private InputStream xml;
	 
	 private String searchIndex;
	 
	 private String keyWords;
	 
	 private ArrayList<Item> items;
	
	
	public Amazon(boolean indeterminate, String searchIndex, String keyWords) {
	    this.indeterminate = indeterminate;
	    this.searchIndex = searchIndex;
	    this.keyWords    = keyWords;
	  }


	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Loading data from Amazon ...",
		        indeterminate ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
		
		
		req = new Request(searchIndex,keyWords,"Images,ItemAttributes,Offers");
  	    xml = getDataFromAmazon(req);
  	    
  	    items = new ArrayList<>();
  	  
    	if(xml != null){
    	    try {
    			AmazonXmlParser parser = new AmazonXmlParser(xml);
    			items = parser.getItems();
    			if(parser.getTotalPages() > 1){
    				for(int k = 2 ; k <= 10 ; k++){
    					
    					if(k >=  parser.getTotalPages()){
    						break;
    					}
    					InputStream xml_k = req.getXmlStream(k);
    					AmazonXmlParser parser_k = new AmazonXmlParser(xml_k);
    					items.addAll(parser_k.getItems());
    					System.out.println(req.getRequestUrl(k));
    					monitor.worked(INCREMENT);
    				}
    			}		
    		} catch (Exception e) {
    			System.out.println("Could not parse the received stream");
    			e.printStackTrace();
    		}
      	}
	    monitor.done();
	    if (monitor.isCanceled())
	        throw new InterruptedException("Loading operation was cancelled");
	}
	
	private static InputStream getDataFromAmazon(Request req) {
		
		System.out.println(req.getRequestUrl(1));
	    
	    InputStream xml = req.getXmlStream(1);
	    
	    if(xml == null){
	    	System.out.println("Could not get xml stream from Amazon server");
	    }
	    return xml;
	}


	public ArrayList<Item> getItems() {
		return items;
	}
}
