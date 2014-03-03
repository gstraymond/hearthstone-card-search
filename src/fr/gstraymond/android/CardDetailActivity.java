package fr.gstraymond.android;

import android.os.Bundle;
import android.view.MotionEvent;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.ui.CardFragment;

public class CardDetailActivity extends CardCommonActivy {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_detail);

		CardFragment fragment = new CardFragment();
		fragment.setArguments(getBundle());
		replaceFragment(fragment, R.id.card_detail_container);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}
}
