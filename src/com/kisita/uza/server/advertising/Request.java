package com.kisita.uza.server.advertising;

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
	private String requestUrl    = null;
	/*
	 *  Returns a specific page of items from the available search results. Up to ten items are returned per page.
	 */
	private int    itemPage      = 1;
	
	public Request(){
		
	}
}
