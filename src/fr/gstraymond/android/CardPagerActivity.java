package fr.gstraymond.android;

import static fr.gstraymond.constants.Consts.POSITION;
import android.app.Fragment;
import android.os.Bundle;
import fr.gstraymond.hearthstone.card.search.R;

public class CardPagerActivity extends CardCommonActivy {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_pager);
		
		Fragment fragment = new CardPagerFragment();
		fragment.setArguments(getBundle());
		replaceFragment(fragment, R.id.card_pager_container);
	}

	@Override
	protected Bundle getBundle() {
		Bundle bundle = super.getBundle();
		bundle.putInt(POSITION, getPosition());
		return bundle;
	}
	
	private int getPosition() {
		return getIntent().getIntExtra(POSITION, 0);
	}

	@Override
	protected void replaceFragment(Fragment fragment, int id) {
		if (getFragmentManager().findFragmentById(id) == null) {
			super.replaceFragment(fragment, id);
		}
	}
}
