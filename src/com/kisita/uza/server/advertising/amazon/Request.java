package com.kisita.uza.server.advertising.amazon;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Request {
	 /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAIFL34MSEGFP2YVCQ";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "umVu0eubKh+rl2mPip+n280DBv62E3GgL12JmwYI";

    /*
     * Use the end-point according to the region you are interested in.
     */
    private static final String ENDPOINT = "webservices.amazon.fr";
    
    /*
     * The product category to search. Some ItemSearch parameters are valid only with specific values of SearchIndex.
     */
	private String searchIndex   = null;
	/*
	 * A word or phrase that describes an item, including author, artist, description, manufacturer, title, and so on
	 */
	private String keyWord       = null;
	/*
	 * Specifies the types of values to return. Separate multiple response groups with commas
	 */
	private String responseGroup = null;
	
	/*
	 * Request URL
	 */
	private String requestUrl    = null;
	
	public Request(String searchIndex,String keyWord,String responseGroup){
		this.searchIndex   = searchIndex;
		this.keyWord       = keyWord;
		this.responseGroup = responseGroup;
	}
	
	public String  getRequestUrl(int itemPage){
		/*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", "AKIAIFL34MSEGFP2YVCQ");
        params.put("AssociateTag", "uza-21");
        params.put("SearchIndex", this.searchIndex); // Shoes 
        params.put("Keywords",this.keyWord);         //chaussures
        params.put("ItemPage", String.valueOf(itemPage));            //Returns a specific page of items from the available search results. Up to ten items are returned per page.
        params.put("ResponseGroup", this.responseGroup); // Images,ItemAttributes,Offers

        this.requestUrl = helper.sign(params);
		
		return this.requestUrl;
	}
	
	
	public InputStream getXmlStream(int itemPage) {
		URL url ;
		HttpURLConnection connection;
	
		try {
			// Get URL
			url = new URL(this.getRequestUrl(itemPage));
			// Open a connexion
			connection = (HttpURLConnection) url.openConnection();
			// Set connexion request method
			connection.setRequestMethod("GET");
			// Set connexion request property
			connection.setRequestProperty("Accept", "application/xml");
			InputStream xml = connection.getInputStream();
			
			return xml;
					
		} catch (MalformedURLException e) {
			System.out.println("Malformed url exception : " + e.getMessage());
			return null;
		}catch (ProtocolException e) {
			System.out.println("Protocol exception : " + e.getMessage());
			return null;
		}catch (IOException e) {
			System.out.println("IO exception : " + e.getMessage());
			return null;
		}
	}
}
