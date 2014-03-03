package fr.gstraymond.tools;

import fr.gstraymond.search.model.response.Card;

public class AttackHealthFormatter {

	public String format(Card card) {
		if (card.getHealth() == null) {
			return card.getAttack() != null ? card.getAttack() : "";
		}
		return card.getAttack() + " / " + card.getHealth();
	}
}
