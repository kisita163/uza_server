package com.kisita.uza.server.advertising.gui;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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
	private Label lblVolume;
	private Text volume;
	private Label lblWeight;
	private Text weight;
	private Button btnSave;
	private Composite composite_1;
	private Label lblCategory;
	private Label lblType_1;
	private Text volumeField;
	private Text weightField;
	private Group group;
	private Browser itemsBrowser;
	private Composite itemDetailComposite;
	private Label label;
	private Text itemAmountField;
	private Label label_1;
	private Text itemVolumeField;
	private Label label_2;
	private Text itemWeightField;
	private Label label_3;
	private Text itemTitleField;
	private Label label_5;
	private Text itemDescriptionField;
	private Button itemSaveButton;
	private Group grpItems;
	private Button itemDeleteButton;
	private Group grpItemsQuery;
	private Label llbItemsCategory;
	private Label lblNewLabel;
	private Combo itemsCategory;
	private Combo itemsType;
	private Button btnSearch;
	private Label lblNewLabel_1;
	private Text itemsMinPrice;
	private Label lblMaxPrice;
	private Text itemsMaxPrice;
	private List itemsList;
	private Label lblId;
	private Text iitemsIdField;
	private Button prevPicBtn;
	private Button nextPicBtn;
	private Button prvBtn;
	private Button nxtBtn;
	
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
		tabCommands.setText("Items");
		
		Composite amazonQuery = new Composite(tabFolder,SWT.NULL);
		amazonQuery.setFont(SWTResourceManager.getFont("Times New Roman TUR", 10, SWT.BOLD));
		tabAmazonQuery.setControl(amazonQuery);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = true;
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
		lblSeller.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSeller.setText("Volume(max m\u00B3)");
		
		volumeField = new Text(grpQuery, SWT.BORDER);
		volumeField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		// Type
		Label lblType = new Label(grpQuery, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont("Times New Roman Greek", 11, SWT.NORMAL));
		lblType.setText("Weight(max Kg)");
		
		weightField = new Text(grpQuery, SWT.BORDER);
		weightField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
		
		composite_1 = new Composite(grpResults, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(composite_1, SWT.NONE);
		
		lblCategory = new Label(composite_1, SWT.NONE);
		lblCategory.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCategory.setText("Category");
		
		categoryChoices = new Combo(composite_1, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		categoryChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		categoryChoices.setItems(SearchIndex.categories);
		
		lblType_1 = new Label(composite_1, SWT.NONE);
		lblType_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblType_1.setText("Type");
		
		typeChoices = new Combo(composite_1, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		typeChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		typeChoices.setEnabled(false);
		setCategoryAndType(categoryChoices,typeChoices);
	    
	    
	    firebase = new Button(composite_1, SWT.NONE);
	    firebase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    firebase.setText("Send");

		
        /////////////////////////END DETAILS  - START RESULTS////////////////////////////////////////////	
		
		Group grpDetails = new Group(amazonQuery, SWT.NONE);
		GridData detailsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		detailsGridData.verticalSpan = 2;
		detailsGridData.horizontalSpan = 1;
		grpDetails.setLayoutData(detailsGridData);
		grpDetails.setText("Details");
		
		GridLayout detailsLayout = new GridLayout();
		detailsLayout.numColumns = 2;
		//detailsLayout.makeColumnsEqualWidth = true;
		grpDetails.setLayout(detailsLayout);
		
		Composite browserComposite = new Composite(grpDetails, SWT.BORDER);
		GridData browserCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		
		browserComposite.setLayoutData(browserCompositeData);
		GridLayout browserLayout = new GridLayout();
		browserLayout.numColumns = 2;
		browserLayout.makeColumnsEqualWidth = true;
		browserComposite.setLayout(browserLayout);
		
		browser = new Browser(browserComposite, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true, true,2,1));
		
		prvBtn = new Button(browserComposite, SWT.NONE);
		prvBtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		prvBtn.setText("Previous");
		
		nxtBtn = new Button(browserComposite, SWT.NONE);
		nxtBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nxtBtn.setText("Next");
		
		detailsComposite = new Composite(grpDetails, SWT.NONE);
		detailsComposite.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.BOLD));
		detailsComposite.setLayout(new GridLayout(2, false));
		GridData detailsCompositeData =  new GridData();
		detailsCompositeData.grabExcessHorizontalSpace = true;
		detailsCompositeData.verticalAlignment = SWT.FILL;
		detailsCompositeData.horizontalAlignment = SWT.FILL;
		detailsCompositeData.grabExcessVerticalSpace = true;
		detailsCompositeData.horizontalSpan = 1;
		
		
		detailsComposite.setLayoutData(detailsCompositeData);
		
		lblAmount = new Label(detailsComposite, SWT.NONE);
		lblAmount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAmount.setText("Amount ");
		
		amount = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		amount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		lblVolume = new Label(detailsComposite, SWT.NONE);
		lblVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblVolume.setText("Volume");
		
		volume = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		volume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		lblWeight = new Label(detailsComposite, SWT.NONE);
		lblWeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblWeight.setText("Weight");
		
		weight = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		weight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblTitle = new Label(detailsComposite, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblTitle.setText("Title");
		
		title = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lblDescription = new Label(detailsComposite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblDescription.setText("Description");
		
		desription = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		desription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		btnSave = new Button(detailsComposite, SWT.NONE);
		
			btnSave.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
			btnSave.setText("Save");
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		Composite itemsQuery = new Composite(tabFolder,SWT.NULL);
		itemsQuery.setFont(SWTResourceManager.getFont("Times New Roman TUR", 10, SWT.BOLD));
		tabCommands.setControl(itemsQuery);
		
		GridLayout itemsQueryLayout = new GridLayout();
		itemsQueryLayout.numColumns = 2;
		//itemsQueryLayout.makeColumnsEqualWidth = true;
		itemsQuery.setLayout(itemsQueryLayout);
		
		grpItemsQuery = new Group(itemsQuery, SWT.NONE);
		grpItemsQuery.setLayout(new GridLayout(2, false));
		GridData  grpItemsQueryData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		grpItemsQuery.setLayoutData(grpItemsQueryData);
		grpItemsQuery.setText("Query");
		
		lblId = new Label(grpItemsQuery, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblId.setText("Id");
		
		iitemsIdField = new Text(grpItemsQuery, SWT.BORDER);
		iitemsIdField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		llbItemsCategory = new Label(grpItemsQuery, SWT.NONE);
		llbItemsCategory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		llbItemsCategory.setText("Category");
		
		itemsCategory = new Combo(grpItemsQuery, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		itemsCategory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblNewLabel = new Label(grpItemsQuery, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Type");
		
		itemsType = new Combo(grpItemsQuery, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		itemsType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		setCategoryAndType(itemsCategory,itemsType);
		
		lblNewLabel_1 = new Label(grpItemsQuery, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Min. Price");
		
		itemsMinPrice = new Text(grpItemsQuery, SWT.BORDER);
		itemsMinPrice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblMaxPrice = new Label(grpItemsQuery, SWT.NONE);
		lblMaxPrice.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxPrice.setText("Max. Price");
		
		itemsMaxPrice = new Text(grpItemsQuery, SWT.BORDER);
		itemsMaxPrice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpItemsQuery, SWT.NONE);
		
		btnSearch = new Button(grpItemsQuery, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSearch.setText("Search");
		
		group = new Group(itemsQuery, SWT.NONE);
		GridData groudData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		group.setLayoutData(groudData);
		group.setText("Details");
		GridLayout gl_group = new GridLayout();
		gl_group.numColumns = 2;
		gl_group.makeColumnsEqualWidth = true;
		group.setLayout(gl_group);
		
		Composite itemBrowserComposite = new Composite(group, SWT.BORDER);
		GridLayout browserGridLayout = new GridLayout();
		browserGridLayout.numColumns = 2;
		browserGridLayout.makeColumnsEqualWidth = true;
		itemBrowserComposite.setLayout(browserGridLayout);
		
		itemBrowserComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		itemsBrowser = new Browser(itemBrowserComposite, SWT.NONE);
		itemsBrowser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		prevPicBtn = new Button(itemBrowserComposite, SWT.NONE);
		prevPicBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		prevPicBtn.setText("Previous");
		
		nextPicBtn = new Button(itemBrowserComposite, SWT.NONE);
		nextPicBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		nextPicBtn.setText("Next");
		
		itemDetailComposite = new Composite(group, SWT.NONE);
		itemDetailComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		itemDetailComposite.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.BOLD));
		GridLayout detailsGridLayout = new GridLayout();
		detailsGridLayout.numColumns = 2;
		detailsGridLayout.makeColumnsEqualWidth = true;
		itemDetailComposite.setLayout(detailsGridLayout);
		
		label = new Label(itemDetailComposite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Amount ");
		
		itemAmountField = new Text(itemDetailComposite, SWT.BORDER );
		itemAmountField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label_1 = new Label(itemDetailComposite, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_1.setText("Volume");
		
		itemVolumeField = new Text(itemDetailComposite, SWT.BORDER | SWT.READ_ONLY);
		itemVolumeField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label_2 = new Label(itemDetailComposite, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_2.setText("Weight");
		
		itemWeightField = new Text(itemDetailComposite, SWT.BORDER | SWT.READ_ONLY);
		itemWeightField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		label_3 = new Label(itemDetailComposite, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		label_3.setText("Title");
		
		itemTitleField = new Text(itemDetailComposite, SWT.BORDER | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		itemTitleField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		label_5 = new Label(itemDetailComposite, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		label_5.setText("Description");
		
		itemDescriptionField = new Text(itemDetailComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		itemDescriptionField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		Label label_6 = new Label(itemDetailComposite, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(itemDetailComposite, SWT.NONE);
		
		itemSaveButton = new Button(itemDetailComposite, SWT.NONE);
		itemSaveButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		itemSaveButton.setText("Save");
		
		itemDeleteButton = new Button(itemDetailComposite, SWT.NONE);
		itemDeleteButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		itemDeleteButton.setText("Delete");
		
		grpItems = new Group(itemsQuery, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpItems.setText("Items");
		
		itemsList = new List(grpItems, SWT.V_SCROLL);
		
		parent.addListener(SWT.Resize, new Listener()
	    {
	        @Override
	        public void handleEvent(Event arg0)
	        {
	            Point size = parent.getSize();
	            browserCompositeData.widthHint = (int) (0.60 * (size.x)/2);
	            detailsCompositeData.widthHint = (int) (0.40 * (size.x)/2);
	            
	            groudData.widthHint = (int) (0.60 * (size.x)/2);
	            grpItemsQueryData.widthHint = (int) (0.40 * (size.x)/2);
	        }
	    });
	 }
	
	public void setCategoryAndType(Combo categoryChoices, final Combo typeChoices){
		categoryChoices.setItems(SearchIndex.categories);
				
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
		
		typeChoices.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		typeChoices.setEnabled(false);
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

		public Text getVolume() {
			return volume;
		}

		public Text getWeight() {
			return weight;
		}

		public Button getBtnSave() {
			return btnSave;
		}

		public Text getVolumeField() {
			return volumeField;
		}

		public Text getWeightField() {
			return weightField;
		}

		public Text getItemAmountField() {
			return itemAmountField;
		}

		public Text getItemVolumeField() {
			return itemVolumeField;
		}

		public Text getItemWeightField() {
			return itemWeightField;
		}

		public Text getItemTitleField() {
			return itemTitleField;
		}

		public Text getItemDescriptionField() {
			return itemDescriptionField;
		}

		public Button getItemSaveButton() {
			return itemSaveButton;
		}

		public Button getItemDeleteButton() {
			return itemDeleteButton;
		}

		public Combo getItemsCategory() {
			return itemsCategory;
		}

		public Combo getItemsType() {
			return itemsType;
		}

		public Text getItemsMinPrice() {
			return itemsMinPrice;
		}

		public Text getItemsMaxPrice() {
			return itemsMaxPrice;
		}
		
		public List getItemsList(){
			return itemsList;
		}

		public Button getBtnSearch() {
			return btnSearch;
		}
		
		public Browser getItemsBrowser(){
			return itemsBrowser;
		}

		public Text getIitemsIdField() {
			return iitemsIdField;
		}

		public Button getPrevPicBtn() {
			return prevPicBtn;
		}

		public Button getNextPicBtn() {
			return nextPicBtn;
		}

		public Button getPrvBtn() {
			return prvBtn;
		}

		public Button getNxtBtn() {
			return nxtBtn;
		}
		
		
}
