package fr.gstraymond.tools;

import fr.gstraymond.search.model.response.Card;

public class PowerToughnessFormatter {

	public String format(Card card) {
		if (card.getPower() == null) {
			return "";
		}
		return card.getPower() + " / " + card.getToughness();
	}
}
