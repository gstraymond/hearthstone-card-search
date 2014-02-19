package fr.gstraymond.search.model.response.facet;

import java.util.List;

public class Facet {

	private List<Term> terms;

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
}
