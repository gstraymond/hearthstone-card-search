package fr.gstraymond.ui.view.impl;

import android.content.Context;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.tools.DrawableManager;
import fr.gstraymond.ui.view.CommonDisplayableView;

public class BackgroundView extends CommonDisplayableView {

	private DrawableManager drawableManager;

	public BackgroundView(Context context) {
		this.drawableManager = new DrawableManager(context);
	}

	@Override
	public void setValue(Card card) {
		getView().setBackgroundResource(getBackground(card));
	}

	@Override
	public boolean display(Card card) {
		return super.display(true);
	}

	@Override
	public int getId() {
		return R.id.array_adapter_background;
	}

	private int getBackground(Card card) {
		String backgroundId = "background_" + card.getClassCode() + "_bitmap";
		return drawableManager.getDrawableId(backgroundId);
	}
}
