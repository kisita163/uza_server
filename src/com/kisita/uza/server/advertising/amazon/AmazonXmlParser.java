package com.kisita.uza.server.advertising.amazon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class AmazonXmlParser {
	
	private Element rootElement;
	private int totalResults;
	private int totalPages;
	private ArrayList<Item> items;
	
	
	public AmazonXmlParser(InputStream xml) throws Exception{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		// get the first element
        this.rootElement = doc.getDocumentElement();
        parseXml();
	}
	/*
	 * 
	 */
	private String getItemsChildValue(String childName, NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++) {
			//System.out.println("Node name is : "+nodes.item(i).getNodeName());
			if(nodes.item(i).getNodeName().equalsIgnoreCase("Items")){
				NodeList items = nodes.item(i).getChildNodes();
 				for(int k = 0 ; k < items.getLength() ; k++){
 					//System.out.println(" ---> Node name is : "+items.item(k).getNodeName());
 					if(items.item(k).getNodeName().equalsIgnoreCase(childName)){
 						//System.out.println(" ------> Value  is : "+items.item(k).getTextContent());
 						return items.item(k).getTextContent();
 					}
 				}
			}
		}
		return null;
	}
	
	private NodeList getNodeList(String tagName , NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++) {
			if(nodes.item(i).getNodeName().equalsIgnoreCase(tagName)){
				////System.out.println("*** Node is : "+nodes.item(i).getChildNodes());
				return  nodes.item(i).getChildNodes();
			}
		}
		return null;
	}
	
	private NodeList getNodeAtPosition(String tagName , int k ,  NodeList nodes){
		
		if(k > nodes.getLength()){
			//System.err.println("k should be <= "+nodes.getLength());
			return null;
		}
		
		for (int i = 0; i < nodes.getLength(); i++) {
			if(nodes.item(i).getNodeName().equalsIgnoreCase(tagName)){
				if((i + 1) == k){
					//System.out.println("*** Node at "+k+" : "+nodes.item(i).getChildNodes());
					return   nodes.item(i).getChildNodes();
				}
			}
		}
		
		return null;
	}
	
	private void parseXml(){
		NodeList nodes = this.rootElement.getChildNodes();
		String totalPages    = getItemsChildValue("TotalPages",nodes);
		String totalResults  = getItemsChildValue("TotalResults",nodes);
		
		// Get number of results
		if(totalResults != null){
			this.totalResults = Integer.valueOf(totalResults);
		}else{
			System.out.println("Could not get the number of results");
		}
		
		// Get number of pages
		if(totalPages != null){
			this.totalPages = Integer.valueOf(totalPages);
		}else{
			System.out.println("Could not get the number of pages");
		}
		this.items = getItemsList(nodes);
	}
	
	private  ArrayList<String>  getFeatures(NodeList node){
		ArrayList<String> features = new ArrayList<>();
		
		//System.out.println("item length is : "+node.getLength());
		for(int i = 0 ; i < node.getLength() ; i++){
			if(node.item(i).getNodeName().equalsIgnoreCase("Feature")){
				features.add(node.item(i).getTextContent());
			}
		}
		return features;
	}
	
	private  ArrayList<String>  getPictures(NodeList node){
		ArrayList<String> pictures = new ArrayList<>();
		//System.out.println("item length is : "+node.getLength());
		for(int i = 0 ; i < node.getLength() ; i ++){
			NodeList urls       = getNodeAtPosition("ImageSet", i + 1 ,node);
			NodeList largeImage = getNodeList("LargeImage",urls);
			Node     url        = (Node)getNodeList("URL",largeImage);
			//System.out.println("url is : "+url.getTextContent());
			pictures.add(url.getTextContent());
		}
		

		return pictures;
	}

	
	private ArrayList<Item> getItemsList(NodeList nodes){
		ArrayList<Item> list = new ArrayList<>();
		NodeList items = getNodeList("Items" , nodes);
			
		for(int i = 0 ; i < items.getLength() ; i ++){ // Get items in item
			Item article      = new Item();
			NodeList item     = getNodeAtPosition("Item" , i+1 , items);
			
			
			if(item != null){
				// Brand
				if(((Node) getNodeList("Brand",getNodeList("ItemAttributes",item))) != null){// There is a brand{
					//System.out.println("Brand is : " + ((Node) getNodeList("Brand",getNodeList("ItemAttributes",item))).getTextContent());
					article.brand = ((Node) getNodeList("Brand",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Title
				if(((Node) getNodeList("Title",getNodeList("ItemAttributes",item))) != null){
					//System.out.println("Title is : " + ((Node) getNodeList("Title",getNodeList("ItemAttributes",item))).getTextContent());
					article.title = ((Node) getNodeList("Title",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Size	
				if(((Node) getNodeList("Size",getNodeList("ItemAttributes",item))) != null){
					//System.out.println("Size is : " + ((Node) getNodeList("Size",getNodeList("ItemAttributes",item))).getTextContent());
					article.size = ((Node) getNodeList("Size",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Color
				if(((Node) getNodeList("Color",getNodeList("ItemAttributes",item))) != null){
					//System.out.println("Color is : " + ((Node) getNodeList("Color",getNodeList("ItemAttributes",item))).getTextContent());
					article.size = ((Node) getNodeList("Color",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Features
				article.features  = getFeatures(getNodeList("ItemAttributes",item));
				
				//for(String str :  article.features){
					//System.out.println("Feature : " + str);
				//}
				
				// Product Type
				if(((Node) getNodeList("ProductTypeName",getNodeList("ItemAttributes",item))) != null){
					//System.out.println("Type is : " + ((Node) getNodeList("ProductTypeName",getNodeList("ItemAttributes",item))).getTextContent());
					article.productTypeName = ((Node) getNodeList("ProductTypeName",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Category
				if(((Node) getNodeList("Department",getNodeList("ItemAttributes",item))) != null){
					//System.out.println("Category is : " + ((Node) getNodeList("Department",getNodeList("ItemAttributes",item))).getTextContent());
					article.category = ((Node) getNodeList("Department",getNodeList("ItemAttributes",item))).getTextContent();
				}
				// Price
				if(((Node) getNodeList("ListPrice",getNodeList("ItemAttributes",item))) != null){
					NodeList listPrice = getNodeList("ListPrice",getNodeList("ItemAttributes",item));
					Node amount   = (Node)getNodeList("Amount",listPrice);
					Node currency = (Node)getNodeList("CurrencyCode",listPrice);
					
					article.amount = Double.valueOf(formattedAmount(amount.getTextContent()));
					article.currency = currency.getTextContent();
					
					if(article.amount == 0){
						continue;
					}
					
					//System.out.println("Amount is : " +  article.amount);
					//System.out.println("Currency is : " +  article.currency);
				}
				// Offer
				if(getNodeList("OfferSummary",item) != null){
					if(((Node) getNodeList("LowestNewPrice",getNodeList("OfferSummary",item))) != null){
						NodeList lowestPrice = getNodeList("LowestNewPrice",getNodeList("OfferSummary",item));
						if(lowestPrice != null){
							Node amount = (Node) getNodeList("Amount",lowestPrice);
							//article.offer = Double.valueOf(formattedAmount(amount.getTextContent()));
							//System.out.println("Offer is : " + article.offer);
						}
					}
				}
				//Pictures
				if(getNodeList("ImageSets",item) != null){
					article.pictures =  getPictures(getNodeList("ImageSets",item));
					
					for(int j = 0 ; j < article.pictures.size() ; j ++){
						//System.out.println("Picure : " + article.pictures.get(j));
					}
				}else{
					continue;
				}
				// Dimensions
				if((getNodeList("PackageDimensions",getNodeList("ItemAttributes",item))) != null){
					NodeList packageDimensions = getNodeList("PackageDimensions",getNodeList("ItemAttributes",item));
					Node height = (Node)getNodeList("Height",packageDimensions); // hundredths-inches
					Node length = (Node)getNodeList("Length",packageDimensions);
					Node weight = (Node)getNodeList("Weight",packageDimensions); //hundredths-pounds
					Node width  = (Node)getNodeList("Width",packageDimensions);
					
					if(height != null ){
						////System.out.println("Height is  : " + height.getTextContent());
						article.height = Integer.valueOf(height.getTextContent());
					}
					if(length != null ){
						//System.out.println("Length is  : " + length.getTextContent());
						article.length = Integer.valueOf(length.getTextContent());
					}
					if(weight != null ){
						//System.out.println("Weight is  : " + weight.getTextContent());
						article.weight = Integer.valueOf(weight.getTextContent());
					}
					if(width != null )
					{
						//System.out.println("Width is   : " + width.getTextContent());
						article.width = Integer.valueOf(width.getTextContent());
					}				
				}
				
				// Link
				if(getNodeList("DetailPageURL",item) != null){			
					Node detailUrl = (Node)getNodeList("DetailPageURL",item);
					article.url = detailUrl.getTextContent();
					//System.out.println("URL : " + article.url);
				}
				
				// ID
				if(getNodeList("ASIN",item) != null){			
					Node asin = (Node)getNodeList("ASIN",item);
					//System.out.println("ASIN : " + asin.getTextContent());
					//"/home/ekisitac/advertising/ASIN_DB.txt"
					if(isPatternExists("ASIN_DB.txt",asin.getTextContent())){
						continue;
					}
					article.id = asin.getTextContent();
					//
					writeInFile("ASIN_DB.txt",article.id);
				}else{
					continue;
				}
				
				
				//System.out.println("------------------------------------------------------");
				list.add(article); // add the article into the list
			}
		}
		return list;
	}
	
	public int getTotalResults(){
		
		return this.totalResults;
	}
	
	public int getTotalPages(){
		
		return this.totalPages;
	}
	
	public ArrayList<Item> getItems(){
		return this.items;
	}
	
	public static void writeInFile(String path,String data){
		//Date date = new Date();  
		//"/home/ekisitac/advertising/uza-server.json/Log.txt"
        File file = new File(path);
        
        FileWriter writer;
		try {
	        // creates the file
	        file.createNewFile();
			writer = new FileWriter(file,true);
	        // Writes the content to the file
	        writer.write(data); 
	        writer.write(System.lineSeparator());
	        writer.flush();
	        writer.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static boolean isPatternExists(String path,String pattern){
		File file = new File(path);
		try {
	        // creates the file
	        file.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			       // process the line.
				if(line.indexOf(pattern) > -1){
					br.close();
					return  true;
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return false;
	}
	
	private static String formattedAmount(String x){
		System.out.println(x);
		if(x.length() == 2 )
			x = "00"+x;
		if(x.length() == 3 )
			x = "0"+x;
		return x.substring(0, x.length()-2) + "." + x.substring( x.length()-2, x.length());
		
	}
}
