package fr.gstraymond.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
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
	
	private static final float ALPHA = 0.5f;
	
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
		TextView healthTextView = getTextView(view, R.id.array_adapter_card_health);
		TextView castingCostTextView = getTextView(view, R.id.array_adapter_card_casting_cost);
		TextView textTextView = getTextView(view, R.id.array_adapter_text);
		TextView descriptionTextView = getTextView(view, R.id.array_adapter_description);
		
		ImageView rarityImageView = getImageView(view, R.id.array_adapter_rarity);
		
		show(attackTextView, healthTextView, castingCostTextView, descriptionTextView, rarityImageView);

		Card card = getItem(position);

		attackTextView.setText(card.getAttack());
		healthTextView.setText(card.getHealth());
		castingCostTextView.setText(card.getCastingCost());
		
		textTextView.setText(formatCard(card));
		
		setRarityImageView(card, rarityImageView);
		
		descriptionTextView.setText(descFormatter.formatWithCapabilities(card));

		ImageView backgroundView = getImageView(view, R.id.array_adapter_background);
		backgroundView.setBackgroundColor(getBackgroundColor(card));
		backgroundView.setAlpha(ALPHA);
		
		hideIfNull(attackTextView, card.getAttack());
		hideIfNull(healthTextView, card.getHealth());
		hideIfNull(castingCostTextView, card.getCastingCost());
		hideIfNull(descriptionTextView, card.getDescription());
		
		return view;
	}

	private void setRarityImageView(Card card, ImageView imageView) {
		String rarityCode = card.getRarityCode();
		if ("common".equals(rarityCode)) {
			imageView.setImageResource(R.drawable.common);
			return;
		}
		if ("rare".equals(rarityCode)) {
			imageView.setImageResource(R.drawable.rare);
			return;
		}
		if ("epic".equals(rarityCode)) {
			imageView.setImageResource(R.drawable.epic);
			return;
		}
		if ("legendary".equals(rarityCode)) {
			imageView.setImageResource(R.drawable.legendary);
			return;
		}
		imageView.setVisibility(View.GONE);
	}

	private int getBackgroundColor(Card card) {
		String classCode = card.getClassCode();
		if ("warrior".equals(classCode)) {
			return Color.rgb(115, 36, 24);
		}
		if ("paladin".equals(classCode)) {
			return Color.rgb(186, 128, 41);
		}
		if ("hunter".equals(classCode)) {
			return Color.rgb(36, 96, 30);
		}
		if ("rogue".equals(classCode)) {
			return Color.rgb(57, 58, 64);
		}
		if ("priest".equals(classCode)) {
			return Color.rgb(177, 180, 185);
		}
		if ("shaman".equals(classCode)) {
			return Color.rgb(42, 49, 90);
		}
		if ("mage".equals(classCode)) {
			return Color.rgb(99, 114, 162);
		}
		if ("warlock".equals(classCode)) {
			return Color.rgb(88, 57, 96);
		}
		if ("druid".equals(classCode)) {
			return Color.rgb(108, 70, 36);
		}
		return Color.rgb(120, 102, 87);
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
		String type = card.getMinionType() != null ? card.getMinionType() : card.getType();
		
		String line = "<b>" + card.getTitle() + "</b> — " + type;
		return Html.fromHtml(line, null, null);
	}
}
