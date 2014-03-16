package fr.gstraymond.ui.view.impl;

import android.content.Context;
import android.widget.ImageView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.tools.DrawableManager;
import fr.gstraymond.ui.view.CommonDisplayableView;

public class RarityView extends CommonDisplayableView {

	private DrawableManager drawableManager;

	public RarityView(Context context) {
		this.drawableManager = new DrawableManager(context);
	}

	@Override
	public void setValue(Card card) {
		String rarityCode = card.getRarityCode();
		ImageView view = (ImageView) getView();
		view.setImageResource(drawableManager.getDrawableId(rarityCode));
	}

	@Override
	public boolean display(Card card) {
		String rarityCode = card.getRarityCode();
		return super.display(! "free".equals(rarityCode));
	}

	@Override
	public int getId() {
		return R.id.array_adapter_rarity;
	}
}
