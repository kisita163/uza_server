package com.kisita.uza.server.advertising;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Uza {
	private static final String DATABASE_URL = "https://glam-afc14.firebaseio.com";
	
	public static void main(String[] args){
		// Instantiate database
		DatabaseReference database = initFirebase(args);

        //database.updateChildren(childUpdates);

		
		Request req = new Request(SearchIndex.LIGHTING,"lumière","Images,ItemAttributes,Offers");
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
			sendDataToFirebaseDatabase(parser,req,database);
			if(parser.getTotalPages() > 1){
				for(int k = 2 ; k <= 10 ; k++){
					
					if(k >=  parser.getTotalPages()){
						break;
					}
					InputStream xml_k = req.getXmlStream(k);
					AmazonXmlParser parser_k = new AmazonXmlParser(xml_k);
					sendDataToFirebaseDatabase(parser_k,req,database);
					System.out.println(req.getRequestUrl(k));
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Could not parse the received stream");
			e.printStackTrace();
		}
	    System.exit(0);
	}
	
	private static void sendDataToFirebaseDatabase(AmazonXmlParser parser,Request req,DatabaseReference database) {
		String item_path;
		String key;
		String pic_key;
		ArrayList<Item> items;
		items = parser.getItems();

		
		for(Item item : items){
			Map<String, Object> data = new HashMap<>();
			String description = "";
			key = database.child("items").push().getKey();
			item_path = "/items/" + key + "/";
			System.out.println("Path : "+item_path);
			
			for(String str : item.features){
				description += str;
				description += System.lineSeparator();
			}
			
			data.put(item_path+"brand",item.brand);
			data.put(item_path+"category","Electronic");
			data.put(item_path+"currency",item.currency); // EUR
			
			
			data.put(item_path+"description",description);
			data.put(item_path+"name",item.title);
			data.put(item_path+"price",String.valueOf(item.amount));
			data.put(item_path+"seller","Amazon");
			data.put(item_path+"type","Home");//item.productTypeName);
			data.put(item_path+"url",item.url);
			data.put(item_path+"id",item.id);
			
			for(String picture : item.pictures){
				pic_key = database.child(item_path+"pictures").push().getKey();
				data.put(item_path+"pictures/"+pic_key, picture);
			}	
			database.updateChildren(data);
			Date date = new Date();
			AmazonXmlParser.writeInFile("/home/ekisitac/advertising/log.txt", date.toString());
		}
		
		//System.out.println("Number of items on this page is  : "+items.size());
		
	}

	public static DatabaseReference initFirebase(String[] args){
		String path;
		if(args.length > 0){
			path = args[0];
		}else
		{
			//path = "/home/ekisitac/advertising/uza-server.json";
			path = "C:\\Users\\hugueski\\Desktop\\uza-server\\uza-server.json";
		}
		   // Initialize Firebase
        try {
            // [START initialize]
            FileInputStream serviceAccount = new FileInputStream(path);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
            // [END initialize]
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }catch (IOException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Google service account data not found");
        }
        
        return FirebaseDatabase.getInstance().getReference();
	}
}


