package fr.gstraymond.tools;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

import fr.gstraymond.search.model.response.Card;

public class DescriptionFormatter {
	
	private Pattern pattern;
	
	public DescriptionFormatter() {
		this.pattern = Pattern.compile("\\{(.*?)\\}");
	}
	
	public String format(Card card) {
		if (card.getDescription() == null) {
			return "";
		}
		
		String tmp = card.getDescription().replaceAll("--", "—");
		if (tmp.contains("{")) {

			Matcher matcher = pattern.matcher(card.getDescription());
			while (matcher.find()) {
				String match = matcher.group();
				String espacedMatch = match
						.replace("{", "\\{").replace("}", "\\}")
						.replace("(", "\\(").replace(")", "\\)");

				tmp = tmp.replaceFirst(espacedMatch, replace(match));
			}
		}
		
		return TextUtils.join("<br /><br />", tmp.split("\n"));
	}

	private String replace(String cost) {		
		// cas pour les LEVEL X-Y
		if (cost.contains("LEVEL")) {
			return cost;
		}
		
		String tempCost = cost.toUpperCase(Locale.ENGLISH);

		tempCost = tempCost.replace("{", "");
		tempCost = tempCost.replace("}", "");

		tempCost = tempCost.replace("(", "");
		tempCost = tempCost.replace(")", "");
		
		tempCost = tempCost.replace("/", "");

		return formatManaSymbol(tempCost);
	}

	private String formatManaSymbol(String tempCost) {
		return "<img src='" + tempCost + ".png' />";
	}

}
