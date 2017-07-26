package com.kisita.uza.server.advertising;

import java.util.ArrayList;

public class Item {
	private static class Currency{
		public final static String EURO            = "EUR";
		public final static String DOLLAR          = "USD";
		public final static String FRANC_CONGOLAIS = "CDF";
	}
	
	
	public String title           = "";
	public String brand           = "";
	public String productTypeName = "";
	public String category        = "";
	
	public String size =  "";
	public String color=  "";
	
	public int height = 0;
	public int length = 0;
	public int weight = 0;
	public int width  = 0;
	
	public String currency = Currency.FRANC_CONGOLAIS;
	public int amount      = 0;
	public int offer       = 0;
	
	public ArrayList<String> features = null;
	public ArrayList<String> pictures = null;
	
	public String url = "";
	public String id  = "";
}
