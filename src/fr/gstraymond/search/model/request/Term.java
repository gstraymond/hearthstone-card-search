package fr.gstraymond.search.model.request;

import java.util.Map;

public class Term {

	private Map<String, String> term;

	public Term(Map<String, String> term) {
		this.term = term;
	}

	public Map<String, String> getTerm() {
		return term;
	}

	public void setTerm(Map<String, String> term) {
		this.term = term;
	}
}
