package fr.gstraymond.ui;

import static android.R.style.TextAppearance_DeviceDefault_Medium;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.gstraymond.biz.CastingCostImageGetter;
import fr.gstraymond.hearthstone.card.search.R;
import fr.gstraymond.search.model.response.Card;
import fr.gstraymond.tools.CastingCostFormatter;
import fr.gstraymond.tools.LanguageUtil;
import fr.gstraymond.tools.PowerToughnessFormatter;
import fr.gstraymond.tools.TypeFormatter;

public class CardArrayAdapter extends ArrayAdapter<Card> {

	private CastingCostImageGetter imagetGetter;
	private CastingCostFormatter ccFormatter;
	private PowerToughnessFormatter ptFormatter;
	private TypeFormatter typeFormatter;
	private boolean showFrenchTitle;

	public CardArrayAdapter(Context context, int resource,
			int textViewResourceId, List<Card> objects,
			CastingCostAssetLoader castingCostAssetLoader) {
		
		super(context, resource, textViewResourceId, objects);
		this.imagetGetter = new CastingCostImageGetter(castingCostAssetLoader);
		this.ccFormatter = new CastingCostFormatter();
		this.ptFormatter = new PowerToughnessFormatter();
		this.typeFormatter = new TypeFormatter(context);
		this.showFrenchTitle = LanguageUtil.showFrench(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		TextView text = (TextView) view;

		if (text == null) {
			text = new TextView(getContext());
			text.setEllipsize(TextUtils.TruncateAt.END);
			text.setSingleLine(true);
			text.setTextAppearance(getContext(), TextAppearance_DeviceDefault_Medium);
			text.setPadding(getTextPadding() * 2, 0, getTextPadding() * 2, getTextPadding());
		}

		text.setText(formatCard(getItem(position), position));
		return text;

	}

	private int getTextPadding() {
		return (int) getContext().getResources().getDimension(R.dimen.listTextPaddingBottom);
	}	

	private Spanned formatCard(Card card, int position) {

		String castingCost = "";
		if (card.getCastingCost() != null) {
			castingCost = ccFormatter.format(card.getCastingCost());
		}

		String line = (position + 1) + ". " + castingCost + " <b>" + getTitle(card) + "</b> — " + getPTorType(card);

		return Html.fromHtml(line, imagetGetter, null);
	}
	
	private String getPTorType(Card card) {
		String pt = ptFormatter.format(card);
		if (pt.length() > 0) {
			return pt;
		}
		
		return typeFormatter.formatFirst(card);
	}

	private String getTitle(Card card) {
		if (showFrenchTitle && card.getFrenchTitle() != null) {
			return card.getFrenchTitle();
		}
		return card.getTitle();
	}
}
