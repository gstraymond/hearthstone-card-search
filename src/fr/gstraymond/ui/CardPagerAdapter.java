package fr.gstraymond.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.search.model.response.Publication;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
	
	private Card card;

	public CardPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Publication publication = card.getPublications().get(position);
		return new CardFragment().setCardUrl(publication.getImage());
	}

	@Override
	public int getCount() {
		return card.getPublications().size();
	}

	public CardPagerAdapter setCard(Card card) {
		this.card = card;
		return this;
	}
}
