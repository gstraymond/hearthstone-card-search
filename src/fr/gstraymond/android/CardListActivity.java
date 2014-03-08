package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.CARD;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import fr.gstraymond.biz.SearchOptions;
import fr.gstraymond.biz.SearchProcessor;
import fr.gstraymond.biz.UIUpdater;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.ui.CardFragment;
import fr.gstraymond.ui.EndScrollListener;
import fr.gstraymond.ui.TextListener;

public class CardListActivity extends CustomActivity implements
		CardListFragment.Callbacks {

	private static final int DRAWER_DELAY = 1200;
	private static final String CURRENT_SEARCH = "currentSearch";
	public static final String CARD_RESULT = "result";

	private TextListener textListener;
	private EndScrollListener endScrollListener;
	private SearchView searchView;
	private Menu menu;

	private Card currentCard;
	private int totalCardCount;
	private SearchOptions currentSearch;
	
	private boolean isRestored = false;
	private boolean hasDeviceRotated = false;

	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	private Toast loadingToast;

	public CardListActivity() {
		super();

		this.textListener = new TextListener(this);
		this.endScrollListener = new EndScrollListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_list);

		replaceFragment(new CardParentListFragment(), R.id.parent_fragment);
		
		if (savedInstanceState != null) {
			SearchOptions savedSearch = savedInstanceState.getParcelable(CURRENT_SEARCH);
			if (savedSearch != null) {
				currentSearch = savedSearch;
				isRestored = true;
				Log.d(getClass().getName(), "Restored search : " + currentSearch);
			}
		}

		if (isSmartphone()) {
			drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        drawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                drawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                getActionBar().setTitle(R.string.drawer_open);
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                getActionBar().setTitle(R.string.drawer_close);
	            }
	        };
			
	        // Set the drawer toggle as the DrawerListener
	        drawerLayout.setDrawerListener(drawerToggle);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
		}

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(R.string.drawer_open);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
        // Sync the toggle state after onRestoreInstanceState has occurred.
		if (isSmartphone()) {
	        drawerToggle.syncState();

	        new Handler().postDelayed(openDrawerRunnable(), DRAWER_DELAY);	        
		}

		if (findViewById(R.id.search_input) != null) {
			searchView = (SearchView) findViewById(R.id.search_input);
			searchView.setOnQueryTextListener(textListener);
		}

		if (currentSearch == null) {
			currentSearch = new SearchOptions();
		}
		
		if (isRestored) {
			currentSearch.setAppend(false);
		}
		if (! hasDeviceRotated) {
			String resultAsString = getIntent().getStringExtra(CARD_RESULT);
			if (resultAsString != null && !isRestored) {
				new UIUpdater(this, resultAsString, getObjectMapper()).execute();
			} else {
				new SearchProcessor(this, currentSearch, R.string.loading_initial).execute();	
			}
		} else {
			hasDeviceRotated = false;
		}
	}
	
	private Runnable openDrawerRunnable() {
	    return new Runnable() {

	        @Override
	        public void run() {
	            drawerLayout.openDrawer(Gravity.START);
	        }
	    };
	}

	/**
	 * Callback method from {@link CardListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Parcelable card) {
		currentCard = (Card) card;
		if (isTablet()) {
			replaceFragment(new CardFragment(), R.id.card_detail_container, getCurrentCardBundle());
		} else {
			Intent intent = new Intent(this, CardDetailActivity.class);
			intent.putExtra(CARD, card);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;

		MenuInflater inflater = getMenuInflater();
		
		// FIXME : faire comme le layout (refs.xml)
		if (isTablet()) {
			inflater.inflate(R.menu.card_twopane_menu, menu);
		} else {
			inflater.inflate(R.menu.card_list_menu, menu);
		}

		if (isTablet()) {
			searchView = new SearchView(this);
			searchView.setIconifiedByDefault(false);
			searchView.setOnQueryTextListener(textListener);
			searchView.setQueryHint(getString(R.string.search_hint));
			menu.findItem(R.id.search_tab).setActionView(searchView);
		}

		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
        hasDeviceRotated = true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (isSmartphone() && drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		
		// FIXME : afficher le numéro de version
		/*case android.R.id.home:

	private TextView getTitleTextView() {
		return (TextView) findViewById(R.id.card_detail_title);
	}
			String version = "Version " + VersionUtils.getAppVersion(this);
			makeText(this, version, LENGTH_SHORT).show();
			return true;*/
			
		case R.id.clear_tab:
			resetSearchView();
			SearchOptions options = new SearchOptions();
			new SearchProcessor(this, options, R.string.loading_clear).execute();
			return true;

		case R.id.help_tab:
			Intent helpIntent = new Intent(this, HelpActivity.class);
			startActivity(helpIntent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private Bundle getCurrentCardBundle() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(CARD, currentCard);
		return bundle;
	}
	
	private void resetSearchView() {
		// buggy
        MenuItem menuItem = menu.findItem(R.id.search_tab);
        if (menuItem != null) {
        	menuItem.collapseActionView();
        }
		searchView.setIconified(true); 
        if (menuItem != null) {
        	menuItem.collapseActionView();
        }
		searchView.setIconified(true);
        searchView.setQuery("", false);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(CURRENT_SEARCH, currentSearch);
		Log.d(getClass().getName(), "onSaveInstanceState " + outState);
	}
	
	private void replaceFragment(Fragment fragment, int id) {
		replaceFragment(fragment, id, null);
	}
	
	private void replaceFragment(Fragment fragment, int id, Bundle bundle) {
		if (bundle != null) {
			fragment.setArguments(bundle);
		}
		getFragmentManager().beginTransaction().replace(id, fragment).commit();
	}
	
	public boolean isTablet() {
		CustomApplication application = (CustomApplication) getApplication();
		return application.isTablet();
	}

	public boolean isSmartphone() {
		return !isTablet();
	}

	public View getCardView() {
		return findViewById(R.id.card_list);
	}

	public TextListener getTextListener() {
		return textListener;
	}

	public void setTextListener(TextListener textListener) {
		this.textListener = textListener;
	}

	public EndScrollListener getEndScrollListener() {
		return endScrollListener;
	}

	public void setEndScrollListener(EndScrollListener endScrollListener) {
		this.endScrollListener = endScrollListener;
	}

	public SearchOptions getCurrentSearch() {
		return currentSearch;
	}

	public void setCurrentSearch(SearchOptions currentSearch) {
		this.currentSearch = currentSearch;
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public int getTotalCardCount() {
		return totalCardCount;
	}

	public void setTotalCardCount(int totalCardCount) {
		this.totalCardCount = totalCardCount;
	}

	public Toast getLoadingToast() {
		return loadingToast;
	}

	public void setLoadingToast(Toast loadingToast) {
		this.loadingToast = loadingToast;
	}
}
