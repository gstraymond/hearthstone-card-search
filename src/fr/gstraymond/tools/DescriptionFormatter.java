package fr.gstraymond.tools;

import java.util.List;

import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import fr.gstraymond.search.model.response.Card;

public class DescriptionFormatter {

	public Spanned formatWithCapabilities(Card card) {
		String description = card.getDescription();
		List<String> capabilities = card.getCapabilities();
		if (description == null || capabilities == null) {
			String string = description != null ? description : "";
			return new SpannedString(string);
		}
		
		for (String capability : capabilities) {
			description = description.replaceAll(capability, "<b>" + capability + "</b>");
		}
		return Html.fromHtml(description, null, null);
	}

	public String formatNull(Card card) {
		if (card.getDescription() == null) {
			return "";
		}

		return card.getDescription();
	}

}
