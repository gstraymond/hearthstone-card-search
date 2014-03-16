package fr.gstraymond.ui;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.tools.DescriptionFormatter;

public class CardArrayAdapter extends ArrayAdapter<Card> {
	
	private DescriptionFormatter descFormatter;

	public CardArrayAdapter(Context context, int resource,
			int textViewResourceId, List<Card> objects) {
		super(context, resource, textViewResourceId, objects);
		descFormatter = new DescriptionFormatter();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.array_adapter_card, null);
		}

		TextView attackTextView = getTextView(view, R.id.array_adapter_card_attack);
		TextView attackWeaponTextView = getTextView(view, R.id.array_adapter_card_attack_weapon);
		TextView healthTextView = getTextView(view, R.id.array_adapter_card_health);
		TextView durabilityTextView = getTextView(view, R.id.array_adapter_card_durability);
		TextView castingCostTextView = getTextView(view, R.id.array_adapter_card_casting_cost);
		TextView textTextView = getTextView(view, R.id.array_adapter_text);
		TextView descriptionTextView = getTextView(view, R.id.array_adapter_description);
		ImageView rarityImageView = getImageView(view, R.id.array_adapter_rarity);
		View backgroundView = view.findViewById(R.id.array_adapter_background);
		
		show(attackTextView, attackWeaponTextView, healthTextView, durabilityTextView, descriptionTextView, castingCostTextView, descriptionTextView, rarityImageView);

		Card card = getItem(position);

		attackTextView.setText(card.getAttack());
		attackWeaponTextView.setText(card.getAttack());
		healthTextView.setText(card.getHealth());
		durabilityTextView.setText(card.getDurability());
		castingCostTextView.setText(card.getCastingCost());
		
		textTextView.setText(formatCard(card));
		
		setRarityImageView(card, rarityImageView);
		
		descriptionTextView.setText(descFormatter.formatWithCapabilities(card));
		
		hideIfNull(attackTextView, card.getDurability() != null ? null : card.getAttack());
		hideIfNull(attackWeaponTextView, card.getDurability());
		hideIfNull(healthTextView, card.getHealth());
		hideIfNull(durabilityTextView, card.getDurability());
		hideIfNull(castingCostTextView, card.getCastingCost());

		backgroundView.setBackgroundResource(getBackground(card));
		
		return view;
	}

	private int getBackground(Card card) {
		String backgroundId = "background_" + card.getClassCode() + "_bitmap";
		return getDrawableId(backgroundId);
	}

	private int getDrawableId(String drawableName) {
		String drawableId = getContext().getPackageName() + ":drawable/" + drawableName;
		return getContext().getResources().getIdentifier(drawableId, null, null);
	}

	private void setRarityImageView(Card card, ImageView imageView) {
		String rarityCode = card.getRarityCode();
		if ("free".equals(rarityCode)) {
			imageView.setVisibility(View.GONE);
			return;
		}
		imageView.setImageResource(getDrawableId(rarityCode));
	}

	private void show(View... views) {
		for (View view : views) {
			view.setVisibility(View.VISIBLE);	
		}
	}

	private void hideIfNull(TextView textView, Object property) {
		if (property == null) {
			textView.setVisibility(View.GONE);
		}
	}

	private TextView getTextView(View view, int id) {
		return (TextView) view.findViewById(id);
	}

	private ImageView getImageView(View view, int id) {
		return (ImageView) view.findViewById(id);
	}

	private Spanned formatCard(Card card) {
		return Html.fromHtml("<b>" + card.getTitle() + "</b>");
	}
}
