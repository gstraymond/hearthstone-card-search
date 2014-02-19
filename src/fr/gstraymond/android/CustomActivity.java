package fr.gstraymond.android;

import android.app.Activity;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CustomActivity extends Activity {

	public CustomApplication getCustomApplication() {
		return (CustomApplication) getApplicationContext();
	}
	
	public ObjectMapper getObjectMapper() {
		return getCustomApplication().getObjectMapper();
	}
}
