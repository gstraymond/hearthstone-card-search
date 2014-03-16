package fr.gstraymond.ui.view.impl;

import android.widget.TextView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.ui.view.CommonDisplayableView;

public class CastingCostView extends CommonDisplayableView {

	@Override
	public void setValue(Card card) {
		TextView view = (TextView) getView();
		view.setText(card.getCastingCost());
	}

	@Override
	public boolean display(Card card) {
		return super.display(true);
	}

	@Override
	public int getId() {
		return R.id.array_adapter_card_casting_cost;
	}

}
