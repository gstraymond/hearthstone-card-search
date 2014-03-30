package fr.gstraymond.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import fr.gstraymond.api.ui.view.DisplayableView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.ui.view.impl.AttackView;
import fr.gstraymond.ui.view.impl.AttackWeaponView;
import fr.gstraymond.ui.view.impl.BackgroundView;
import fr.gstraymond.ui.view.impl.CastingCostView;
import fr.gstraymond.ui.view.impl.DescriptionView;
import fr.gstraymond.ui.view.impl.DurabilityView;
import fr.gstraymond.ui.view.impl.HealthView;
import fr.gstraymond.ui.view.impl.RarityView;
import fr.gstraymond.ui.view.impl.TitleView;

public class CardArrayAdapter extends ArrayAdapter<Card> {
	private List<DisplayableView> displayableViews;

	public CardArrayAdapter(Context context, int resource,
			int textViewResourceId, List<Card> objects) {
		super(context, resource, textViewResourceId, objects);

		displayableViews = new ArrayList<DisplayableView>();
		displayableViews.add(new AttackView());
		displayableViews.add(new AttackWeaponView());
		displayableViews.add(new HealthView());
		displayableViews.add(new DurabilityView());
		displayableViews.add(new CastingCostView());
		displayableViews.add(new TitleView());
		displayableViews.add(new DescriptionView());
		displayableViews.add(new RarityView(context));
		displayableViews.add(new BackgroundView(context));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.array_adapter_card, null);
		}

		Card card = getItem(position);

		for (DisplayableView displayableView : displayableViews) {
			displayableView.setParentView(view);
			if (displayableView.display(card)) {
				displayableView.setValue(card);
			}
		}
		return view;
	}
}
