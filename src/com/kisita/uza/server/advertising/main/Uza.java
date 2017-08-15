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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kisita.uza.server.advertising.amazon.Amazon;
import com.kisita.uza.server.advertising.amazon.AmazonXmlParser;
import com.kisita.uza.server.advertising.amazon.Item;
import com.kisita.uza.server.advertising.gui.Gui;


public class Uza {
	private static final String DATABASE_URL = "https://glam-afc14.firebaseio.com";
	
	private static DatabaseReference database = initFirebase();
	
	private ArrayList<Item> items             = new ArrayList<>();
	
	private ArrayList<Item> firebaseItems     = new ArrayList<>() ;
	
	private static ArrayList<Item> itemsFromFirebase = new ArrayList<>() ;
	
	private static ArrayList<Item> searchItemsFromFirebase = new ArrayList<>() ;
	
	private String category;
	
	private String type;
	
	private int index = -1;
	
	private int firebaseIndex = -1;
	
	private int amazonPicturesIndex = -1;
	
	private ArrayList<String> pictures;
	
	private static Gui gui;
	
	public static void main(String[] args) {
		startListeners();
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
	   
	    
	    gui = new Gui(shell);
	    
	    
	    shell.setText(Gui.getResourceString("Uza manager"));
	   		
	    gui.getQueryButton().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	cleanAmazonQueryWidget(gui);
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
	        	gui.getBrowser().setVisible(true);
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
	        	gui.getBrowser().setVisible(true);
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
  		
	    gui.getBtnSearch().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	if(itemsFromFirebase.size() > 0){
	        		gui.getItemsList().removeAll();
	        		for(Item item : itemsFromFirebase){
	        			// Control
	        			System.out.println("Category  = "+gui.getItemsCategory().getText() + "\n" + 
	        			                   "Type      = "+gui.getItemsType().getText() + "\n" +
	        			                   "Min price = "+gui.getItemsMinPrice().getText() + "\n" + 
	        			                   "Max price = "+gui.getItemsMaxPrice().getText() + "\n");
	        			// Check ID
	        			if(!gui.getIitemsIdField().getText().isEmpty()){
	        				if(!item.id.equalsIgnoreCase(gui.getIitemsIdField().getText()))
	        					continue;
	        			}
	        			// Check category
	        			if(!gui.getItemsCategory().getText().isEmpty()){
	        				if(!item.category.equalsIgnoreCase(gui.getItemsCategory().getText()))
	        					continue;
	        			}
	        			// Check type
	        			if(!gui.getItemsType().getText().isEmpty()){
	        				if(!item.productTypeName.equalsIgnoreCase(gui.getItemsType().getText()))
	        					continue;
	        			}
	        			//
	        			
	        			gui.getItemsList().add(item.id);
	        		}
	        		gui.getItemsList().update();
	        	}
	        }
  		});
	    
	    gui.getItemsList().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	gui.getItemsBrowser().setVisible(true);
	        	index = gui.getItemsList().getSelectionIndex();
	        	if(index < itemsFromFirebase.size()){
	        		amazonPicturesIndex = 0;
	        		pictures = itemsFromFirebase.get(index).pictures;
		        	gui.getItemsBrowser().setUrl(itemsFromFirebase.get(index).pictures.get(amazonPicturesIndex));
		        	gui.getItemAmountField().setText(String.valueOf(itemsFromFirebase.get(index).amount));
		        	gui.getItemTitleField().setText(itemsFromFirebase.get(index).title);
		        	gui.getItemVolumeField().setText(AmazonXmlParser.formattedVolume(itemsFromFirebase.get(index).height,itemsFromFirebase.get(index).width,itemsFromFirebase.get(index).length) + " m³");
		        	gui.getItemWeightField().setText(AmazonXmlParser.formattedWeight(itemsFromFirebase.get(index).weight)+ " Kg");    
		        	gui.getItemDescriptionField().setText(itemsFromFirebase.get(index).features);
	        	}
	          }
	        });  
	    
	    gui.getItemDeleteButton().addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
				MessageBox  mess =  new MessageBox(shell, SWT.ICON_WARNING | SWT.OK| SWT.CANCEL);
				mess.setText("Warning");
				mess.setMessage("Do you really want to delete this item ?");
	        	index = gui.getItemsList().getSelectionIndex();
	        	System.out.println("index is  "+index + " size = " + itemsFromFirebase.size());
	        	if(index > -1 && index < itemsFromFirebase.size()){
		        	if(mess.open() == SWT.OK){
			        	database.child("/items/" + itemsFromFirebase.get(index).key).removeValue();
			        	
			        	itemsFromFirebase.remove(index);
			        	gui.getItemsList().remove(index);
			        	gui.getItemsList().update();
			        	
			        	cleanItemsWidgets();
		        		
			        	mess = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						mess.setText("Information");
						mess.setMessage("Item deleted");
						mess.open();
						
			        	gui.getItemsList().forceFocus();
			        	gui.getItemsList().select(index);
		        	}	
	        	}
	        }
	    });
	    gui.getItemSaveButton().addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				Map<String, Object> childUpdates = new HashMap<>();
				String amount      = gui.getItemAmountField().getText();
				String title       = gui.getItemTitleField().getText();
				String description = gui.getItemDescriptionField().getText();
				
				
				
				MessageBox  mess =  new MessageBox(shell, SWT.ICON_WARNING | SWT.OK| SWT.CANCEL);
				mess.setText("Warning");
				mess.setMessage("Do you really want to update this item ?");
				
				if(index > -1 && index < itemsFromFirebase.size()){
					childUpdates.put("/items/" + itemsFromFirebase.get(index).key +"/price", amount);
					childUpdates.put("/items/" + itemsFromFirebase.get(index).key +"/name", title);
					childUpdates.put("/items/" + itemsFromFirebase.get(index).key +"/description", description);
					
					if(mess.open() == SWT.OK){
						 database.updateChildren(childUpdates);
						 mess = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						 mess.setText("Information");
						 mess.setMessage("Item updated");
						 mess.open();
					}	
				}			
			}
	    	
	    });
	    return shell;
	}
	
	private static void cleanItemsWidgets(){
		gui.getItemsBrowser().setVisible(false);
    	gui.getItemAmountField().setText("");
    	gui.getItemTitleField().setText("");
    	gui.getItemVolumeField().setText("");
    	gui.getItemWeightField().setText("");    
    	gui.getItemDescriptionField().setText("");
	}

	private static void cleanAmazonQueryWidget(Gui gui) {
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
		gui.getBrowser().setVisible(false);
	}

	protected static void populateResultsView(ArrayList<Item> items, List list) {
		for(Item item : items){
			
			list.add(item.id);
		}
	}
	
	/**
     * Start global listener for all Items.
     */
    public static void startListeners() {
        database.child("items").addChildEventListener(new ChildEventListener() {

            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildName) {
            	//System.out.println("items "+ prevChildName);    
            	extractDataFromSnapshot(dataSnapshot);
            }

			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildName) {}

            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildName) {}

            public void onCancelled(DatabaseError databaseError) {
              
            }
        });
    }
    
    private static void extractDataFromSnapshot(DataSnapshot dataSnapshot) {
		Item item = new Item();
		item.key             = dataSnapshot.getKey();
		item.title           = dataSnapshot.child("name").getValue().toString();
		item.amount          = Double.valueOf(dataSnapshot.child("price").getValue().toString());
		item.brand           = dataSnapshot.child("brand").getValue().toString();
		item.category        = dataSnapshot.child("category").getValue().toString();
		item.currency        = dataSnapshot.child("currency").getValue().toString();
		item.features        = dataSnapshot.child("description").getValue().toString();
		item.id              = dataSnapshot.child("id").getValue().toString();
		item.productTypeName = dataSnapshot.child("type").getValue().toString();
		item.url             = dataSnapshot.child("url").getValue().toString();
		
		for(DataSnapshot child : dataSnapshot.child("pictures").getChildren()){
			//System.out.println(child.getValue().toString());
			item.pictures.add(child.getValue().toString());
		}
		itemsFromFirebase.add(item);
        System.out.println("Number of items is  : "+itemsFromFirebase.size());
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
			//System.out.println("Path : "+item_path);
			
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


