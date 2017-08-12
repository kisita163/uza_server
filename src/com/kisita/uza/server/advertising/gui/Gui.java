package com.kisita.uza.server.advertising.gui;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import com.kisita.uza.server.advertising.amazon.SearchIndex;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.browser.Browser;
import org.eclipse.wb.swt.SWTResourceManager;

public class Gui {
	private TabFolder tabFolder;
	// Choosen keyword
	private Text keyWords;
	
	//search index
	private Combo searchIndexChoices;
	
	// Category
	private Combo categoryChoices;
	
	// Type
	private Combo typeChoices;
	
	// Button
	private Button queryButton;
	
	// List from Amazon
	private List fromAmazon;
	
	//List to send to firebase
	private List toFirebase;
	
	// Add
	private Button add;
	
	// Add All
	private Button addAll;
	
	// Remove
	private Button remove;
	
	// Remove all
	private Button removeAll;
	
	
	// Send to firebase button
	private Button firebase;
	
	// Picture Browser
	private Browser browser;
	private Composite detailsComposite;
	private Label lblAmount;
	private Text amount;
	private Label lblTitle;
	private Text title;
	private Label lblDescription;
	private Text desription;
	
	public Gui(Composite parent) {
		FillLayout parentLayout = new FillLayout();
		parent.setLayout(parentLayout);
		
		tabFolder = new TabFolder(parent, SWT.NULL);
		tabFolder.setFont(SWTResourceManager.getFont("Times New Roman TUR", 12, SWT.BOLD));
		GridData tabItemData = new GridData();
		tabFolder.setLayoutData(tabItemData);
		
		TabItem tabAmazonQuery = new TabItem(tabFolder, SWT.NONE);
		tabAmazonQuery.setText("Amazon Query");
		
		TabItem tabCommands = new TabItem(tabFolder, SWT.NONE);
		tabCommands.setText("Commands");
		
		Composite amazonQuery = new Composite(tabFolder,SWT.NULL);
		amazonQuery.setFont(SWTResourceManager.getFont("Times New Roman TUR", 10, SWT.BOLD));
		tabAmazonQuery.setControl(amazonQuery);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		amazonQuery.setLayout(gridLayout);
	
		///////////// QUERY /////////////////////		
		
		Group grpQuery = new Group(amazonQuery, SWT.NONE);
		grpQuery.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.NORMAL));
		GridData queryGridData = new GridData(GridData.FILL, GridData.CENTER, false, false);
		queryGridData.horizontalSpan = 1;
		grpQuery.setLayoutData(queryGridData);
		
		GridLayout queryLayout = new GridLayout();
		queryLayout.numColumns = 2;
		grpQuery.setLayout(queryLayout);
		grpQuery.setText("Query");
		
		// Search Index
		Label lblSearchIndex = new Label(grpQuery, SWT.NONE);
		lblSearchIndex.setFont(SWTResourceManager.getFont("Times New Roman Greek", 11, SWT.NORMAL));
		lblSearchIndex.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSearchIndex.setText("Search Index");
		
		searchIndexChoices = new Combo(grpQuery, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		searchIndexChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		searchIndexChoices.setItems(SearchIndex.indexes);
		
		
		
		// Key Words
		Label lblKeyWords = new Label(grpQuery, SWT.NONE);
		lblKeyWords.setFont(SWTResourceManager.getFont("Times New Roman Greek", 11, SWT.NORMAL));
		lblKeyWords.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblKeyWords.setText("Key Words");
		
		keyWords = new Text(grpQuery, SWT.BORDER);
		keyWords.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		// Category
		Label lblSeller = new Label(grpQuery, SWT.NONE);
		lblSeller.setFont(SWTResourceManager.getFont("Times New Roman Greek", 11, SWT.NORMAL));
		lblSeller.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSeller.setText("Category");
		
		categoryChoices = new Combo(grpQuery, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		categoryChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		categoryChoices.setItems(SearchIndex.categories);
		
		// Type
		Label lblType = new Label(grpQuery, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont("Times New Roman Greek", 11, SWT.NORMAL));
		lblType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Type");
		
		typeChoices = new Combo(grpQuery, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		typeChoices.setEnabled(false);
		typeChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		categoryChoices.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				typeChoices.setEnabled(true);
		        if (categoryChoices.getText().equals(SearchIndex.MEN) || categoryChoices.getText().equals(SearchIndex.WOMEN) ) {
		        	typeChoices.setItems(SearchIndex.men_women_types);
		          } else if (categoryChoices.getText().equals(SearchIndex.KIDS)) {
		        	typeChoices.setItems(SearchIndex.kids_types);
		          } else if (categoryChoices.getText().equals(SearchIndex.UZA_ELECTRONICS)){
		        	typeChoices.setItems(SearchIndex.electronics_types);
		          } else if (categoryChoices.getText().equals(SearchIndex.HOME)){
		        	typeChoices.setItems(SearchIndex.home_types);
		          }
			}	
		});
		new Label(grpQuery, SWT.NONE);
		// Send button
		queryButton = new Button(grpQuery, SWT.NONE);
		GridData gd_queryButton = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd_queryButton.widthHint = 91;
		queryButton.setLayoutData(gd_queryButton);
		queryButton.setText("Send");
		
		/////////////////////////END QUERY  - START DETAILS////////////////////////////////////////////
		
		Group grpResults= new Group(amazonQuery, SWT.NONE);
		grpResults.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.NORMAL));
		GridData resultsGridData = new GridData(GridData.FILL, SWT.FILL, true, false);
		resultsGridData.horizontalSpan = 1;
		resultsGridData.verticalSpan = 3;
		grpResults.setLayoutData(resultsGridData);
		grpResults.setText("Results");
		
		GridLayout resultsLayout = new GridLayout();
		resultsLayout.numColumns = 4;
		resultsLayout.makeColumnsEqualWidth = true;
		
		grpResults.setLayout(resultsLayout);
		
		fromAmazon = new List(grpResults, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_list = new GridData();
		gd_list.horizontalAlignment = SWT.FILL;
		gd_list.verticalAlignment = SWT.FILL;
		gd_list.grabExcessHorizontalSpace = true;
		gd_list.grabExcessVerticalSpace = true;
		gd_list.horizontalSpan = 1;
		fromAmazon.setLayoutData(gd_list);
		
		Composite composite = new Composite(grpResults, SWT.NONE);
		GridData compositeGridData = new GridData();
		compositeGridData.horizontalAlignment = SWT.CENTER;
		compositeGridData.horizontalSpan = 1;
		compositeGridData.grabExcessHorizontalSpace = false;
		composite.setLayoutData(compositeGridData);
		composite.setLayout(new GridLayout(1, false));
		
		add = new Button(composite, SWT.NONE);
		add.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		add.setText("Add");
		
		addAll = new Button(composite, SWT.NONE);
		addAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addAll.setText("Add All");
		
		remove = new Button(composite, SWT.NONE);
		remove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		remove.setText("Remove");
		
		removeAll = new Button(composite, SWT.NONE);
		removeAll.setText("Remove All");
		
		toFirebase = new List(grpResults, SWT.BORDER | SWT.V_SCROLL);
		GridData toFirebaseList = new GridData();
		toFirebaseList.horizontalAlignment = SWT.FILL;
		toFirebaseList.verticalAlignment = SWT.FILL;
		toFirebaseList.grabExcessHorizontalSpace = true;
		toFirebaseList.grabExcessVerticalSpace = true;
		toFirebaseList.horizontalSpan = 1;
		toFirebase.setLayoutData(toFirebaseList);
		
		firebase = new Button(grpResults, SWT.NONE);
		firebase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		firebase.setText("Firebase");
		GridData firebaseData = new GridData();
		firebaseData.horizontalAlignment = SWT.CENTER;
		firebaseData.horizontalSpan = 1;
		firebaseData.grabExcessHorizontalSpace = true;
		firebase.setLayoutData(firebaseData);

		
        /////////////////////////END DETAILS  - START RESULTS////////////////////////////////////////////	
		
		Group grpDetails = new Group(amazonQuery, SWT.NONE);
		GridData detailsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		detailsGridData.verticalSpan = 2;
		detailsGridData.horizontalSpan = 1;
		grpDetails.setLayoutData(detailsGridData);
		grpDetails.setText("Details");
		
		GridLayout detailsLayout = new GridLayout();
		detailsLayout.numColumns = 2;
		detailsLayout.makeColumnsEqualWidth = true;
		grpDetails.setLayout(detailsLayout);
		
		
		browser = new Browser(grpDetails, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true, true, 1, 1));
		
		detailsComposite = new Composite(grpDetails, SWT.NONE);
		detailsComposite.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.BOLD));
		detailsComposite.setLayout(new GridLayout(2, false));
		detailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lblAmount = new Label(detailsComposite, SWT.NONE);
		lblAmount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblAmount.setText("Amount ");
		
		amount = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		amount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblTitle = new Label(detailsComposite, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblTitle.setText("Title");
		
		title = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblDescription = new Label(detailsComposite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblDescription.setText("Description");
		
		desription = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		desription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	 }

	/**
	   * Grabs input focus.
	   */
	  public void setFocus() {
	    tabFolder.setFocus();
	  }

	  /**
	   * Disposes of all resources associated with a particular instance of the
	   * LayoutExample.
	   */
	  public void dispose() {
	    tabFolder = null;
	  }

	  //region Getter_setter
	
	  /**
	   * Gets a string from the resource bundle. We don't want to crash because of
	   * a missing String. Returns the key if not found.
	   */
	  public static String getResourceString(String key) {
	      return key;
	  }

	  /**
	   * Gets a string from the resource bundle and binds it with the given
	   * arguments. If the key is not found, return the key.
	   */
	  static String getResourceString(String key, Object[] args) {
	    try {
	      return MessageFormat.format(getResourceString(key), args);
	    } catch (MissingResourceException e) {
	      return key;
	    } catch (NullPointerException e) {
	      return "!" + key + "!";
	    }
	  }
	  

		/**
		 * @return the text
		 */
		public Text getKeyWords() {
			return keyWords;
		}
		/**
		 * @return the searchIndexChoices
		 */
		public Combo getSearchIndexChoices() {
			return searchIndexChoices;
		}
		/**
		 * @return the categoryChoices
		 */
		public Combo getCategoryChoices() {
			return categoryChoices;
		}
		/**
		 * @return the typeChoices
		 */
		public Combo getTypeChoices() {
			return typeChoices;
		}
		/**
		 * @return the list
		 */
		public List getAmazonList() {
			return fromAmazon;
		}
		
		/**
		 * @return the list
		 */
		public List getFirebaseList() {
			return toFirebase;
		}

		public TabFolder getTabFolder() {
			return tabFolder;
		}

		public Button getQueryButton() {
			return queryButton;
		}

		public Button getAdd() {
			return add;
		}

		public Button getAddAll() {
			return addAll;
		}

		public Button getRemove() {
			return remove;
		}

		public Button getRemoveAll() {
			return removeAll;
		}

		public Button getFirebase() {
			return firebase;
		}
		
		public Browser getBrowser() {
			return this.browser;
		}

		public Text getAmount() {
			return amount;
		}

		public Text getTitle() {
			return title;
		}

		public Text getDesription() {
			return desription;
		}
}
