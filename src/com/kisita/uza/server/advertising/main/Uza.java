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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import com.kisita.uza.server.advertising.amazon.AmazonXmlParser;
import com.kisita.uza.server.advertising.amazon.Item;
import com.kisita.uza.server.advertising.gui.Gui;


public class Uza {
	private static final String DATABASE_URL = "https://glam-afc14.firebaseio.com";
	
	private static DatabaseReference database = initFirebase();
	
	private ArrayList<Item> items = new ArrayList<>();
	
	private ArrayList<Item> firebaseItems = new ArrayList<>() ;
	
	private String category;
	
	private String type;
	
	private int index = -1;
	
	private int firebaseIndex = -1;
	
	private int amazonPicturesIndex = -1;
	
	private ArrayList<String> pictures;
	
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
	              String volumeField = gui.getVolumeField().getText();
	              String weightField = gui.getWeightField().getText();
	             
	              Amazon amazon      = new Amazon(true,searchIndex,keyWords,volumeField,weightField);            
	              if(checkInputData(shell,searchIndex,keyWords)){
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
	      		  if(items != null && items.size() > 0){
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
	        		amazonPicturesIndex = 0;
	        		pictures = firebaseItems.get(firebaseIndex).pictures;
		        	gui.getBrowser().setUrl(firebaseItems.get(firebaseIndex).pictures.get(amazonPicturesIndex));
		        	gui.getAmount().setText(String.valueOf(firebaseItems.get(firebaseIndex).formattedAmount));
		        	gui.getTitle().setText(firebaseItems.get(firebaseIndex).title);
		        	gui.getVolume().setText(AmazonXmlParser.formattedVolume(firebaseItems.get(firebaseIndex).height,firebaseItems.get(firebaseIndex).width,firebaseItems.get(firebaseIndex).length) + " m³");
		        	gui.getWeight().setText(AmazonXmlParser.formattedWeight(firebaseItems.get(firebaseIndex).weight)+ " Kg");    
		        	gui.getDesription().setText(firebaseItems.get(firebaseIndex).features);
	        	}
	        	gui.getTitle().setEditable(false);
	        	gui.getDesription().setEditable(false);
	          }
	        });
	    gui.getBrowser().addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				if(amazonPicturesIndex < pictures.size()-1){
					amazonPicturesIndex += 1;
				}else{
					amazonPicturesIndex = 0;
				}			
				gui.getBrowser().setUrl(pictures.get(amazonPicturesIndex));
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
	    });
  		gui.getAmazonList().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	index = gui.getAmazonList().getSelectionIndex();
	        	if(index < items.size()){
	        		amazonPicturesIndex = 0;
	        		pictures = items.get(index).pictures;
		        	gui.getBrowser().setUrl(items.get(index).pictures.get(amazonPicturesIndex));
		        	gui.getAmount().setText(String.valueOf(items.get(index).formattedAmount));
		        	gui.getTitle().setText(items.get(index).title);
		        	gui.getVolume().setText(AmazonXmlParser.formattedVolume(items.get(index).height,items.get(index).width,items.get(index).length) + " m³");
		        	gui.getWeight().setText(AmazonXmlParser.formattedWeight(items.get(index).weight)+ " Kg");    
		        	gui.getDesription().setText(items.get(index).features);
	        	}
	        	gui.getTitle().setEditable(true);
	        	gui.getDesription().setEditable(true);
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
				if(items != null && items.size() > 0){
					for(Item i : items){
						gui.getFirebaseList().add(i.id); // For visualization
					}	
					firebaseItems.addAll(items);
				}	
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
	            category           = gui.getCategoryChoices().getText();
	            type               = gui.getTypeChoices().getText();
	        	
        		MessageBox  mess =  new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
    			mess.setText("Info");
    			
	            if(checkInputData(shell,category,type)){
	            	if(firebaseItems.size() > 0){
		            	sendDataToFirebaseDatabase(firebaseItems);
		    			mess.setMessage("Data sent!");
		    			mess.open();
	            	}else{
	            		mess.setMessage("Nothing to send!");
		    			mess.open();
	            	}
	    			
	    			firebaseItems.clear();
		        	gui.getFirebaseList().removeAll();
		        	gui.getFirebaseList().update();
	            }
	        }
  		});
  		
  		gui.getBtnSave().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	// Get changes in description and title
	        	if(index != -1 && index < items.size()){
	        		MessageBox  mess =  new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK| SWT.CANCEL);
	    			mess.setText("Info");
	    			mess.setMessage("Saved");
	    			mess.open();
	        		items.get(index).title = gui.getTitle().getText();
	        		items.get(index).features = gui.getDesription().getText();
	        	}
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
		
		gui.getVolume().setText("");
		gui.getWeight().setText("");
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
			if(AmazonXmlParser.isPatternExists("ASIN_DB.txt",item.id)){
				continue; // Don't do anything with this MTF
			}
			key = database.child("items").push().getKey();
			item_path = "/items/" + key + "/";
			System.out.println("Path : "+item_path);
			
			data.put(item_path+"brand",item.brand);
			data.put(item_path+"category",category);
			data.put(item_path+"currency",item.currency); // EUR
			
			
			data.put(item_path+"description",item.features);
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
			AmazonXmlParser.writeInFile("ASIN_DB.txt",item.id);
		}
		
		//System.out.println("Number of items on this page is  : "+items.size());
		
	}

	public static DatabaseReference initFirebase(){
		String path = "uza-server.json";
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
	
	private static boolean checkInputData(Shell shell,String searchIndex, String keyWords){
		
		if(searchIndex.isEmpty() || keyWords.isEmpty()){
			MessageBox  mess =  new MessageBox(shell, SWT.ICON_ERROR | SWT.OK| SWT.CANCEL);
			mess.setText("Error");
			mess.setMessage("One or more fields not filled");
			mess.open();

			return false;
		}
		return true;
	}
}


