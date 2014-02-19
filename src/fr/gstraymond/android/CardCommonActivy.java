package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.CARD;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.tools.LanguageUtil;

public abstract class CardCommonActivy extends Activity {

	private Card card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		card = getIntent().getParcelableExtra(CARD);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
	
		setTitle(getFullTitle(card));
	}

	protected Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(CARD, card);
		return bundle;
	}

	private String getFullTitle(Card card) {
		if (LanguageUtil.showFrench(this) && card.getFrenchTitle() != null) {
			return card.getFrenchTitle();
		}
		
		return card.getTitle();
	}
	
	protected void replaceFragment(Fragment fragment, int id) {
		getFragmentManager().beginTransaction().replace(id, fragment).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	protected Card getCard() {
		return card;
	}
}
