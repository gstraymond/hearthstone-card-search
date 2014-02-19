package fr.gstraymond.search.help;

import java.util.List;

public class HelpText {

	private String title;
	private List<String> descriptions;
	private List<String> items;
	private List<HelpText> texts;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public List<HelpText> getTexts() {
		return texts;
	}
	
	public void setTexts(List<HelpText> texts) {
		this.texts = texts;
	}
}
