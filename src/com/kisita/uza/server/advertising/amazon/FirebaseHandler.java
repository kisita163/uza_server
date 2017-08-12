package com.kisita.uza.server.advertising.amazon;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseHandler {
	
	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	
	public FirebaseHandler(String pathToJson) throws IOException {
		this.serviceAccount = new FileInputStream(pathToJson);
		this.options = new FirebaseOptions.Builder()
		  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
		  .setDatabaseUrl("https://glam-afc14.firebaseio.com")
		  .build();
		FirebaseApp.initializeApp(options);
	}
	
	public  DatabaseReference getDbReference(String table){
		DatabaseReference ref = FirebaseDatabase
			    .getInstance()
			    .getReference();
	
	
		return ref;
	}
}
