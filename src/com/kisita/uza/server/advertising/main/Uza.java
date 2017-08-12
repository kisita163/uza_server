package com.kisita.uza.server.advertising.main;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kisita.uza.server.advertising.amazon.Amazon;
import com.kisita.uza.server.advertising.amazon.Item;
import com.kisita.uza.server.advertising.gui.Gui;


public class Uza {
	private static final String DATABASE_URL = "https://glam-afc14.firebaseio.com";
	
	private static DatabaseReference database = initFirebase();
	
	private ArrayList<Item> items;
	
	private ArrayList<Item> firebaseItems = new ArrayList<>() ;
	
	private String category;
	
	private String type;
	
	private int index = -1;
	
	private int firebaseIndex = -1;
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Uza().createShell(display);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	
	public Shell createShell(final Display display){
	    final Shell shell = new Shell(display);
	   
	    
	    Gui gui = new Gui(shell);
	    
	    
	    shell.setText(Gui.getResourceString("Uza manager"));
		
	    gui.getQueryButton().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	cleanAllWidgets(gui);
	            switch (e.type) {
	            case SWT.Selection:
	              String searchIndex = gui.getSearchIndexChoices().getText();
	              String keyWords    = gui.getKeyWords().getText();
	              category           = gui.getCategoryChoices().getText();
	              type               = gui.getTypeChoices().getText();
	              items              = new ArrayList<Item>();
	              Amazon amazon      = new Amazon(true,searchIndex,keyWords);            
	              if(checkInputData(shell,searchIndex,keyWords,category,type)){
		              try {
		                  new ProgressMonitorDialog(shell).run(true, true,
		                      amazon);
		                } catch (InvocationTargetException ex) {
		                  MessageDialog.openError(shell, "Error", ex.getMessage());
		                } catch (InterruptedException ex) {
		                  MessageDialog.openInformation(shell, "Cancelled", ex.getMessage());
		                }
	              }
	              //longRunningOperation(shell,gui,searchIndex,keyWords );	
	              items = amazon.getItems();
	      		  if(items.size() > 0){
	      			  populateResultsView(items,gui.getAmazonList());
	      		  }
	              break;
	            }
	          }
	        });
	    
	    gui.getFirebaseList().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	firebaseIndex = gui.getFirebaseList().getSelectionIndex();
	        	if(firebaseIndex != -1 && firebaseIndex < firebaseItems.size()){
	        		String desription = "";
		        	gui.getBrowser().setUrl(firebaseItems.get(firebaseIndex).pictures.get(0));
		        	gui.getAmount().setText(String.valueOf(firebaseItems.get(firebaseIndex).formattedAmount));
		        	gui.getTitle().setText(firebaseItems.get(firebaseIndex).title);
		        	
		        	for(String s : firebaseItems.get(firebaseIndex).features){
		        		desription += s + System.lineSeparator();
		        	}	     
		        	gui.getDesription().setText(desription);
	        	}
	          }
	        });
	    
  		gui.getAmazonList().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	index = gui.getAmazonList().getSelectionIndex();
	        	if(index < items.size()){
	        		String desription = "";
		        	items.get(index).pictures.get(0);
		        	gui.getBrowser().setUrl(items.get(index).pictures.get(0));
		        	gui.getAmount().setText(String.valueOf(items.get(index).amount));
		        	gui.getTitle().setText(items.get(index).title);
		        	
		        	for(String s : items.get(index).features){
		        		desription += s + System.lineSeparator();
		        	}	     
		        	gui.getDesription().setText(desription);
	        	}
	          }
	        });
  	
  		gui.getAdd().addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				index = gui.getAmazonList().getSelectionIndex();
				if(index != -1 && index < items.size()){
					gui.getFirebaseList().add(items.get(index).id); // For visualization
					firebaseItems.add(items.get(index));
	        	}
			} 			
  		});
  		
  		
  		gui.getAddAll().addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				for(Item i : items){
					gui.getFirebaseList().add(i.id); // For visualization
				}	
				firebaseItems.addAll(items);
			}			
  		});
  		
  		
  		gui.getRemove().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	firebaseIndex = gui.getFirebaseList().getSelectionIndex();
	        	if(firebaseIndex != -1 && firebaseIndex < firebaseItems.size()){
	        		firebaseItems.remove(firebaseIndex);
	        		gui.getFirebaseList().remove(firebaseIndex);
	        		gui.getFirebaseList().update();
	        	}
	        }
  		});
  		
  		gui.getRemoveAll().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	firebaseItems.clear();
	        	gui.getFirebaseList().removeAll();
	        	gui.getFirebaseList().update();
	        }
  		});
  		
  		gui.getFirebase().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	sendDataToFirebaseDatabase(firebaseItems);
	        	gui.getFirebaseList().removeAll();
	        	gui.getFirebaseList().update();
	        }
  		});
  		  		
	    return shell;
	}

	private static void cleanAllWidgets(Gui gui) {
		// Clean all fields 
		gui.getFirebaseList().removeAll();
		gui.getFirebaseList().update();
		
		gui.getAmazonList().removeAll();
		gui.getAmazonList().update();
		
		gui.getAmount().setText("");
		gui.getTitle().setText("");
		gui.getDesription().setText("");
		
		// TODO BROWSER
	}


	protected static void populateResultsView(ArrayList<Item> items, List list) {
		for(Item item : items){
			
			list.add(item.id);
		}
	}
	      	
	private void sendDataToFirebaseDatabase(ArrayList<Item> items) {
		String item_path;
		String key;
		String pic_key;
		
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
			data.put(item_path+"category",category);
			data.put(item_path+"currency",item.currency); // EUR
			
			
			data.put(item_path+"description",description);
			data.put(item_path+"name",item.title);
			data.put(item_path+"price",String.valueOf(item.amount));
			data.put(item_path+"seller","Amazon");
			data.put(item_path+"type", type);//item.productTypeName);
			data.put(item_path+"url",item.url);
			data.put(item_path+"id",item.id);
			
			for(String picture : item.pictures){
				pic_key = database.child(item_path+"pictures").push().getKey();
				data.put(item_path+"pictures/"+pic_key, picture);
			}	
			database.updateChildren(data);
			//Date date = new Date();
			//AmazonXmlParser.writeInFile("/home/ekisitac/advertising/log.txt", date.toString());
		}
		
		//System.out.println("Number of items on this page is  : "+items.size());
		
	}

	public static DatabaseReference initFirebase(){
		//String path = "/home/kisita/kisita/uza-server.json";
		String path = "C:\\Users\\hugueski\\Desktop\\uza-server\\uza-server.json";
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
	
	private static boolean checkInputData(Shell shell,String searchIndex, String keyWords, String category, String type){
		
		if(searchIndex.isEmpty() || keyWords.isEmpty() || category.isEmpty() || type.isEmpty() ){
			MessageBox  mess =  new MessageBox(shell, SWT.ICON_ERROR | SWT.OK| SWT.CANCEL);
			mess.setText("Error");
			mess.setMessage("One or more fields not filled");
			mess.open();

			return false;
		}
		return true;
	}
}


