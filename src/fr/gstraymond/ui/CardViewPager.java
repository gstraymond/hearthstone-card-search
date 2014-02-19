package fr.gstraymond.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.android.CardPagerFragment;
import fr.gstraymond.android.CustomApplication;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.search.model.response.Publication;

public class CardViewPager extends ViewPager {

	private Card card;
	
	public CardViewPager(Context context) {
		super(context);
	}

	public CardViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void computeScroll() {
		int itemId = getCurrentItem();
		String itemIdDisplayed = (itemId + 1) + "";
		if (! getTitle().startsWith(itemIdDisplayed)) {
			Publication publication = card.getPublications().get(itemId);
			String text = publication.getEdition() + " — " + publication.getRarity();

			int count = getAdapter().getCount();
			if (count > 1) {
				text = itemIdDisplayed + "/" + count + " " + text;
			}
			
			if (isTablet()) {
				getTitleTextView().setText(text);
			} else {
				getActivity().setTitle(text);	
			}
			
			Fragment fragment = getActivity().getFragmentManager().findFragmentById(R.id.card_pager_container);
			if (fragment != null && fragment instanceof CardPagerFragment) {
				((CardPagerFragment) fragment).setPosition(itemId);
			}
		}
		super.computeScroll();
	}

	private TextView getTitleTextView() {
		return (TextView) getActivity().findViewById(R.id.card_detail_title);
	}

	private String getTitle() {
		if (isTablet()) {
			return getTitleTextView().getText().toString();
		}
		return getActivity().getTitle().toString();
	}
	
	private Activity getActivity() {
		return (Activity) getContext();
	}

	public CardViewPager setCard(Card card) {
		this.card = card;
		return this;
	}
	
	private boolean isTablet() {
		CustomApplication application = (CustomApplication) getActivity().getApplication();
		return application.isTablet();
	}
}
