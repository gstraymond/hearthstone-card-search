package fr.gstraymond.biz;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.android.CardListActivity;
import fr.gstraymond.android.CardListFragment;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.search.model.response.SearchResult;

public class SearchProcessor extends AsyncTask<Void, Void, SearchResult> {

	private CardListActivity activity;
	private ProgressBar progressBar;
	private TextView welcomeTextView;
	
	private SearchOptions options = new SearchOptions();

	public SearchProcessor(CardListActivity activity, SearchOptions options, int loadingText) {
		super();
		this.activity = activity;
		this.progressBar = getProgressBar();
		this.welcomeTextView = getWelcomeTextView();
		this.options = options;

		disableSearch();
		storeCurrentSearch(options);

		welcomeTextView.setText(activity.getString(loadingText));
		progressBar.setProgress(0);
	}

	private void storeCurrentSearch(SearchOptions options) {
		activity.setCurrentSearch(options);
	}

	private ProgressBar getProgressBar() {
		return (ProgressBar) activity.findViewById(R.id.progress_bar);
	} 

	private TextView getWelcomeTextView() {
		return (TextView) activity.findViewById(R.id.welcome_text_view);
	}
	
	@Override
	protected SearchResult doInBackground(Void... params) {
		long now = System.currentTimeMillis();
		SearchResult searchResult = launchSearch(options);
		Log.i(getClass().getName(), "search took " + (System.currentTimeMillis() - now) + "ms");
		return searchResult;
	}
	
	private void switchSearch(boolean _switch) {
		if (getActivity().getTextListener() != null) {
			getActivity().getTextListener().setCanSearch(_switch);
		}
		
		if (getActivity().getEndScrollListener() != null) {
			getActivity().getEndScrollListener().setCanLoadMoreItems(_switch);
		}
	}
	
	private void disableSearch() {
		switchSearch(false);
	}
	
	private void enableSearch() {
		switchSearch(true);
	}

	@Override
	protected void onPostExecute(SearchResult searchResult) {
		progressBar.setProgress(100);
		
		if (options.isAppend() && activity.isSmartphone()) {
			if (activity.getLoadingToast() != null) {
				activity.getLoadingToast().cancel();
			}
			makeText(activity, R.string.toasting_loading_finished, LENGTH_SHORT).show();
		}

		new UIUpdater(activity).onPostExecute(searchResult);
		enableSearch();
		// suppression du focus sur le search et fermeture du clavier
		getActivity().getSearchView().clearFocus();
	}

	private FragmentManager getFragmentManager() {
		return getActivity().getFragmentManager();
	}

	private CardListFragment getCardListFragment() {
		return (CardListFragment) getFragmentManager().findFragmentById(R.id.card_list);
	}

	private SearchResult launchSearch(SearchOptions options) {
		if (options.isAppend()) {
			options.setFrom(getCardListFragment().getCardListCount());
		}
		
		SearchResult searchResult = getApplicationContext().getElasticSearchClient().process(options, progressBar);
		
		if (searchResult != null && searchResult.getHits() != null) {
			Log.i(getClass().getName(), searchResult.getHits().getTotal() + " cards found in " + searchResult.getTook() + " ms");
		}
		
		return searchResult;
	}

	private CustomApplication getApplicationContext() {
		return (CustomApplication) getActivity().getApplicationContext();
	}

	public CardListActivity getActivity() {
		return activity;
	}

	public void setActivity(CardListActivity activity) {
		this.activity = activity;
	}
}
