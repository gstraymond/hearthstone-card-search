package fr.gstraymond.tools;

import java.util.List;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import fr.gstraymond.biz.CastingCostImageGetter;
import fr.gstraymond.search.help.HelpText;

public class HelpFormatter {

	private CastingCostImageGetter imageGetter;

	public HelpFormatter(CastingCostImageGetter imageGetter) {
		this.imageGetter = imageGetter;
	}

	public Spanned format(HelpText helpText) {
		StringBuilder html = new StringBuilder();
		recursiveFormat(html, helpText, 1, "");
		return Html.fromHtml(html.toString(), imageGetter, null);
	}

	private void recursiveFormat(StringBuilder html, HelpText helpText,
			int level, String depth) {
		html.append(formatTitle(helpText, level, depth));
		html.append(formatDescription(helpText));
		html.append(formatItems(helpText));

		if (helpText.getTexts() != null) {
			int i = 1;
			for (HelpText subHelpText : helpText.getTexts()) {
				recursiveFormat(html, subHelpText, level + 1, depth + i + ".");
				i++;
			}
		}
	}

	private String formatTitle(HelpText helpText, int level, String depth) {
		String title = helpText.getTitle();
		if (title == null) {
			return "";
		}
		String sign = "h" + level + ">";
		StringBuilder levels = new StringBuilder();
		for (int i = 0; i < level - 1; i++) {
			levels.append("\t");
		}
		return "<" + sign + depth + " " + title + "</" + sign;
	}

	private String formatDescription(HelpText helpText) {
		List<String> descriptions = helpText.getDescriptions();
		if (descriptions == null) {
			return "";
		}
		
		String text = TextUtils.join("<br />", descriptions);
		return "<p>" + text + "</p>";
	}

	private String formatItems(HelpText helpText) {
		List<String> items = helpText.getItems();
		if (items == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder("<p>");
		for (String item : items) {
			builder.append("\t\t●\t" + item + "<br />");
		}
		return builder.toString() + "</p>";
	}
}
