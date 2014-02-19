package fr.gstraymond.biz;

import android.os.AsyncTask;
import fr.gstraymond.android.SplashScreen;

public class ApplicationLoader extends AsyncTask<Void, Void, Void> {

	private SplashScreen splashScreen;
	
	public ApplicationLoader(SplashScreen splashScreen) {
		super();
		this.splashScreen = splashScreen;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		splashScreen.getCustomApplication().init();
		return null;
	}
}
