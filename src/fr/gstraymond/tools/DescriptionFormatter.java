package fr.gstraymond.tools;

import java.util.List;

import android.text.Html;
import android.text.Spanned;
import fr.gstraymond.search.model.response.Card;

public class DescriptionFormatter {

	public Spanned formatWithCapabilities(Card card) {
		String description = card.getDescription();
		List<String> capabilities = card.getCapabilities();
		if (description == null || capabilities == null || capabilities.isEmpty()) {
			description = description != null ? "<br /><br />" + description : "";
			return Html.fromHtml(getFirstLine(card) + description);
		}
		
		for (String capability : capabilities) {
			description = description.replaceAll(capability, "<b>" + capability + "</b>");
		}
		
		return Html.fromHtml(getFirstLine(card) + "<br /><br />" + description);
	}
	
	private String getFirstLine(Card card) {
		String type = card.getMinionType() != null ? card.getMinionType() : card.getType();
		return type + " — " + card.getClazz();
	}

}
