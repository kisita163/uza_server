package com.kisita.uza.server.advertising.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class Details {
	
	private Composite composite;
	
	private Browser browser;
	
	private GridData layoutData;
	
	private Button browserNextButton;
	
	private Button browserPreviousButton;
	
	private Text amount;

	private Text volume;

	private Text weight;

	private Text title;

	private Text desription;

	private Button buttonSave;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Details(Composite composite,GridData layoutData){
		this.composite  = composite;
		this.layoutData = layoutData; 
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Composite  createDetailsGui(){
		
		Group grpDetails = new Group(composite, SWT.NONE);
		grpDetails.setLayoutData(layoutData);
		grpDetails.setText("Details");
		
		
		GridLayout detailsLayout = new GridLayout();
		detailsLayout.numColumns = 2;
		grpDetails.setLayout(detailsLayout);
		
		setBrowserComposite(grpDetails);
		
		setDetailsComposite(grpDetails);
		
		return grpDetails;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void setDetailsComposite(Group grpDetails) {
		Composite detailsComposite = new Composite(grpDetails, SWT.NONE);
		detailsComposite.setFont(SWTResourceManager.getFont("Times New Roman TUR", 9, SWT.BOLD));
		detailsComposite.setLayout(new GridLayout(2, false));
		GridData detailsCompositeData =  new GridData();
		detailsCompositeData.grabExcessHorizontalSpace = true;
		detailsCompositeData.verticalAlignment = SWT.FILL;
		detailsCompositeData.horizontalAlignment = SWT.FILL;
		detailsCompositeData.grabExcessVerticalSpace = true;
		detailsCompositeData.horizontalSpan = 1;
		
		
		detailsComposite.setLayoutData(detailsCompositeData);
		
		Label lblAmount = new Label(detailsComposite, SWT.NONE);
		lblAmount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAmount.setText("Amount ");
		
		amount = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		amount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblVolume = new Label(detailsComposite, SWT.NONE);
		lblVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblVolume.setText("Volume");
		
		volume = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		volume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblWeight = new Label(detailsComposite, SWT.NONE);
		lblWeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblWeight.setText("Weight");
		
		weight = new Text(detailsComposite, SWT.BORDER | SWT.READ_ONLY);
		weight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTitle = new Label(detailsComposite, SWT.NONE);
		lblTitle.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblTitle.setText("Title");
		
		title = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblDescription = new Label(detailsComposite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		lblDescription.setText("Description");
		
		desription = new Text(detailsComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		desription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		buttonSave = new Button(detailsComposite, SWT.NONE);		
	    buttonSave.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
	    buttonSave.setText("Save");
		
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void setBrowserComposite(Group group) {
		Composite browserComposite = new Composite(group, SWT.BORDER);
		GridData browserCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		
		browserComposite.setLayoutData(browserCompositeData);
		GridLayout browserLayout = new GridLayout();
		browserLayout.numColumns = 2;
		browserLayout.makeColumnsEqualWidth = true;
		browserComposite.setLayout(browserLayout);
		
		browser = new Browser(browserComposite, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true, true,2,1));
		
		browserPreviousButton = new Button(browserComposite, SWT.NONE);
		browserPreviousButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		browserPreviousButton.setText("Previous");
		
		browserNextButton = new Button(browserComposite, SWT.NONE);
		browserNextButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		browserNextButton.setText("Next");
	}
}
