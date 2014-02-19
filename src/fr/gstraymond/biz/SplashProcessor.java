package fr.gstraymond.biz;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.android.SplashScreen;
import fr.gstraymond.search.model.response.SearchResult;

public class SplashProcessor extends AsyncTask<Void, Integer, SearchResult> {

	private SplashScreen activity;
	private ProgressBar progressBar;
	private SearchOptions options = new SearchOptions();

	public SplashProcessor(SplashScreen activity, SearchOptions options) {
		super();
		this.activity = activity;
		this.progressBar = getProgressBar();

		progressBar.setProgress(0);
		this.options = options;
	}

	@Override
	protected SearchResult doInBackground(Void... params) {
		long now = System.currentTimeMillis();
		SearchResult searchResult = getApplicationContext().getElasticSearchClient().process(options, progressBar);
		
		if (searchResult != null && searchResult.getHits() != null) {
			Log.i(getClass().getName(), searchResult.getHits().getTotal() + " cards found in " + searchResult.getTook() + " ms");
		}
		Log.i(getClass().getName(), "search took " + (System.currentTimeMillis() - now) + "ms");
		
		return searchResult;
	}

	private CustomApplication getApplicationContext() {
		return (CustomApplication) activity.getApplicationContext();
	}

	@Override
	protected void onPostExecute(SearchResult result) {
		activity.startNextActivity(result);
	}

	private ProgressBar getProgressBar() {
		return (ProgressBar) activity.findViewById(R.id.progress_bar);
	}
}
